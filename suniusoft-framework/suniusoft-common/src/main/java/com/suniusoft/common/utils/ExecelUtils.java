package com.suniusoft.common.utils;

import com.suniusoft.common.utils.exception.ExecelException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 *   
 *  @ProjectName: icard  
 *  @Description: execel工具类
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/18 13:38  
 */
public class ExecelUtils {

    /**
     * 获取 Execel Workbook
     * @param fileInputStream
     * @return
     * @throws Exception
     */
    public static Workbook getExecelBook(FileInputStream fileInputStream) throws ExecelException {

        if (fileInputStream == null) {
            throw new ExecelException("execel 文件 为空");
        }
        Workbook book = null;
        try {
            try {
                // Excel 2007获取方法
                book = new XSSFWorkbook(fileInputStream);
            } catch (Exception ex) {
                try {
                    // Excel 2003获取方法
                    book = new HSSFWorkbook(fileInputStream);
                } catch (Exception e) {
                    book = WorkbookFactory.create(fileInputStream);
                }
            }

        } catch (Exception e) {
            throw new ExecelException(e);
        }

        return book;
    }

    /**
     * 获取 Execel Workbook
     * @param file
     * @return
     * @throws Exception
     */
    public static Workbook getExecelBook(File file) throws ExecelException {

        if (file == null) {
            throw new ExecelException("execel 文件 为空");
        }
        try {
            return getExecelBook(new FileInputStream(file));
        }catch (Exception e){
            throw new ExecelException(e);
        }
    }

    public static Sheet  getExecelSheet(FileInputStream file, int sheetIndex) throws ExecelException {

        Workbook workbook = getExecelBook(file);

        if(workbook!=null){
            return workbook.getSheetAt(sheetIndex);
        }

        return null;
    }

    /**
     * 获取 execel sheet
     * @param file
     * @param sheetIndex
     * @return
     * @throws Exception
     */
    public static Sheet  getExecelSheet(File file, int sheetIndex) throws ExecelException {

       return getExecelSheet(file, sheetIndex);
    }


    public  static void  main(String[] args) throws ExecelException{

        getExecelBook(new File("H:\\wywork\\code\\shufen\\sf_crm\\src\\main\\webapp\\orderTemplate\\test.xlsx"));
    }

}
