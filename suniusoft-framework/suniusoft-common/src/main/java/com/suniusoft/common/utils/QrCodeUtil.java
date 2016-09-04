package com.suniusoft.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.apache.log4j.Logger;

import java.util.Hashtable;

/**
 * Created by Administrator on 2015/12/19 0019.
 */
public class QrCodeUtil {
    private final static Logger logger = Logger.getLogger(QrCodeUtil.class);

    public static void main(String[] args) throws Exception{
        generateQrFile("www.baidu.com");
    }

    public static String generateQrFile(String content){
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 544, 544,hints);
        } catch (WriterException e) {
            logger.error("生成二维码过程出错", e);
        }
        try {
            return FileUtils.uploadQrFile(bitMatrix);
        } catch (Exception e) {
            logger.error("生成二维码过程出错", e);
            return null;
        }
    }
}
