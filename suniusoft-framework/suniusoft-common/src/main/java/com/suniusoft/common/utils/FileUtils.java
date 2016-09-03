package com.suniusoft.common.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @desc 文件帮助类
 * @company shufensoft
 * @author litu
 * @date 2015/4/8.
 * @version 1.0
 */
public class FileUtils {

    //定义可以上传文件的后缀数组，默认"*"，代表所有
    public static String[] filePostFix = { "*" };
    public static String[] typeImages = { "gif", "jpeg", "png", "jpg", "bmp" };

    //上传文件的最大长度
    public static long maxFileSize = 1024 * 1024 * 1024 * 2L;//2G
    //一次读取多少字节
    public static int bufferSize = 1024 * 8;

    private final static void init() {
        if (bufferSize > Integer.MAX_VALUE) {
            bufferSize = 1024 * 8;
        } else if (bufferSize < 8) {
            bufferSize = 8;
        }
        if (maxFileSize < 1) {
            maxFileSize = 1024 * 1024 * 1024 * 2L;
        } else if (maxFileSize > Long.MAX_VALUE) {
            maxFileSize = 1024 * 1024 * 1024 * 2L;
        }
    }

    /**
     * 获取文件后缀，没有“.”
     * @param fileName 文件名称
     * @return
     */
    public static String getType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);//后缀
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 传入一个文件名，得到这个文件名称的后缀 有"."
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index);//后缀
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 利用uuid产生一个随机的name
     * @param fileName 带后缀的文件名称
     * @return String 随机生成的name
     */
    public static String getRandomName(String fileName) {
        String randomName = UUID.randomUUID().toString();
        return getNewFileName(fileName, randomName, "jpg");
    }

    /**
     *  用当前日期、时间和1000以内的随机数组合成的文件名称
     * @param fileName 文件名称
     * @return 新文件名称
     */
    public static String getNumberName(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
        int rand = new Random().nextInt(1000);
        String numberName = format.format(new Date()) + rand;
        return getNewFileName(fileName, numberName, "jpg");
    }


    /**
     * 传递一个文件名称和一个新名称，组合成一个新的带后缀文件名
     * 当传递的文件名没有后缀，会添加默认的后缀
     * @param fileName 文件名称
     * @param newName 新文件名称
     * @param nullSuffix 为没有后缀的文件所添加的后缀;eg:txt
     * @return String 文件名称
     */
    public static String getNewFileName(String fileName, String newName, String nullSuffix) {
        String suffix = getSuffix(fileName);
        if (suffix != null) {
            newName += suffix;
        } else {
            newName = newName.concat(".").concat(nullSuffix);
        }
        return newName;
    }


    /**
     * 返回可用的文件名
     * @param fileName 文件名
     * @param path 路径
     * @return 可用文件名
     */
    public static String getBracketFileName(String fileName, String path) {
        return getBracketFileName(fileName, fileName, path, 1);
    }


    /**
     * 递归处理文件名称，直到名称不重复（对文件名、目录文件夹都可用）
     * eg: a.txt --> a(1).txt
     * 文件夹upload--> 文件夹upload(1)
     * @param fileName 文件名称
     * @param path 文件路径
     * @param num 累加数字，种子
     * @return 返回没有重复的名称
     */
    public static String getBracketFileName(String fileName, String bracketName, String path, int num) {
        boolean exist = isFileExist(bracketName, path);
        if (exist) {
            int index = fileName.lastIndexOf(".");
            String suffix = "";
            bracketName = fileName;
            if (index != -1) {
                suffix = fileName.substring(index);
                bracketName = fileName.substring(0, index);
            }
            bracketName += "(" + num + ")" + suffix;
            num++;
            bracketName = getBracketFileName(fileName, bracketName, path, num);
        }
        return bracketName;
    }


    /**
     * 判断该文件是否存在
     * @param fileName 文件名称
     * @param path 目录
     * @return 是否存在
     */
    public static boolean isFileExist(String fileName, String path) {
        File file = new File(getDoPath(path) + fileName);
        return file.exists();
    }

    /**
     * 处理后的系统文件路径
     * @param path 文件路径
     * @return 返回处理后的路径
     */
    public static String getDoPath(String path) {
        path = path.replace("\\", "/");
        String lastChar = path.substring(path.length() - 1);
        if (!"/".equals(lastChar)) {
            path += "/";
        }
        return path;
    }

    /**
     * @desc 文件上传
     *       前台传过来的文件名规则是:业务模块_业务ID_相关属性名(相关业务名称不确定或不唯一用时间戳)
     *       examples:
     *               1.会员头像:member_1000_head.jpg
     *               2.会员发帖图片:cirle_post_1000_1426239225834.jpg(时间搓)
     * @author litu
     * @param picture
     * @return
     */
    public static String uploadFile(MultipartFile picture)throws Exception{

        if(picture == null){
            return null;
        }
        String fileName = picture.getOriginalFilename();
        String filePath = null;
        String bizDir = "";
        if(StringUtils.isNotBlank(fileName)){
            String[] bizDirSplit = fileName.split("_");
            if(bizDirSplit!=null && bizDirSplit.length>1){
               for(int i=0 ; i<bizDirSplit.length-1 ; i++){
                   bizDir = bizDir+bizDirSplit[i]+"/";
               }

            }
        }
        if(StringUtils.isNotBlank(bizDir)){
            filePath = uploadFile(bizDir, picture);
        }


        return filePath;

    }

    /**
     * 文件上传
     * @author litu
     * @param bizDir 文件业务分类路径(业务模块/子业务模块/业务对象/)
     * @param picture
     * @return
     */
    public static String uploadFile(String bizDir, MultipartFile picture)
            throws Exception{

        String savePath = getDoPath(PropertyReader.getValue("uploadPath"))+bizDir;

        FileUtils.mkDir(savePath);//目录不存在创建
        FileUtils.mkDir(savePath);//目录不存在创建

        String fileName = picture.getOriginalFilename();
        //验证文件格式
        if (FileUtils.validTypeByName4Images(fileName)) {
            String newFileName = FileUtils.getRandomName(fileName);//获取随机文件名
            String path = FileUtils.getDoPath(savePath) + newFileName;
            picture.transferTo(new File(path));
            return "/upload/"+bizDir+ newFileName;
        }

        return null;
    }


    /**
     * 创建指定的path路径目录
     * @param path 目录、路径
     * @return 是否创建成功
     * @throws Exception
     */
    public static boolean mkDir(String path) throws Exception {
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            file = null;
        }
        return false;
    }

    /**
     * 验证当前文件名、文件类型是否是图片类型
     * typeImages 可以设置图片类型
     * @param fileName 验证文件的名称
     * @return 是否合法
     */
    public static boolean validTypeByName4Images(String fileName) {
        return validTypeByName(fileName, typeImages);
    }


    /**
     * 根据文件名和类型数组验证文件类型是否合法，flag是否忽略大小写
     * @param fileName 文件名
     * @param allowTypes 类型数组
     * @param flag 是否获得大小写
     * @return 是否验证通过
     */
    public static boolean validTypeByName(String fileName, String[] allowTypes, boolean flag) {
        String suffix = getType(fileName);
        boolean valid = false;
        if (allowTypes.length > 0 && "*".equals(allowTypes[0])) {
            valid = true;
        } else {
            for (String type : allowTypes) {
                if (flag) {//不区分大小写后缀
                    if (suffix != null && suffix.equalsIgnoreCase(type)) {
                        valid = true;
                        break;
                    }
                } else {//严格区分大小写
                    if (suffix != null && suffix.equals(type)) {
                        valid = true;
                        break;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * 根据文件名称和类型数组验证文件类型是否合法
     * @param fileName 文件名
     * @param allowTypes 文件类型数组
     * @return 是否合法
     */
    public static boolean validTypeByName(String fileName, String[] allowTypes) {
        return validTypeByName(fileName, allowTypes, true);
    }

    /**
     * 上传图片保存为指定目录下的文件
     * @return 保存的文件名
     * */
    public static String saveFile(FileItem imageItem, String savePath) throws Exception{
        String imageName = imageItem.getName();
        String newName = com.suniusoft.common.utils.StringUtils.getUUID() + getSuffix(imageName);
        File file = new File(savePath + newName);
        InputStream is = imageItem.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int i = 0;
        while((i = is.read(b)) >= 0){
            fos.write(b,0,i);
        }
        fos.close();
        is.close();
        return newName;
    }

    /**
     * 上传图片保存为指定目录下的文件
     *
     *
     * @return 保存的文件名
     * */
    public static String saveFile(MultipartFile multipartFile, String savePath) throws Exception{
        FileUtils.mkDir(savePath);
        String imageName = multipartFile.getOriginalFilename();
        String newName = com.suniusoft.common.utils.StringUtils.getUUID() + getSuffix(imageName);
        File file = new File(savePath + newName);
        InputStream is = multipartFile.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int i = 0;
        while((i = is.read(b)) >= 0){
            fos.write(b,0,i);
        }
        fos.close();
        is.close();
        return newName;
    }

}
