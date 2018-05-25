/**
 * Copyright (C) 2018 上海牵趣网络科技有限公司 版权所有
 */
package com.cqfq.ts.wy.qrcode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * <p>批量生成二维码</p>
 * @author Administrator
 * @Date 2018年5月22日 下午6:14:23
 */
public class QrCodeTest {
    
    @Test
    public void encode() throws IOException{
        int width = 300, height = 300;
        String zipFileName = "测试" + ".zip";
        String fileName = "demo";
        String realPath = "d:/qrcode/";
        for (int i = 0; i < 10; i++){
            String imgPath = realPath + fileName + i + ".png";
            Map<String, Object> content = Maps.newHashMap();
            content.put("bookId", i);
            content.put("copyright", "www.demo.com");
            content.put("date", System.currentTimeMillis());
            QrCodeTest.encode(JSON.toJSONString(content),BarcodeFormat.QR_CODE, width, height, imgPath); // 默认生成二维码
            System.out.println("Hi ,you have finished zxing encode:" + fileName + i);
        }
        File files = new File(realPath);
        if(!files.exists()){
            files.mkdir();
        }
        ZipOutputStream zipos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(realPath+zipFileName)));
        for(File imageFile : files.listFiles()){
            putZipFiles(imageFile, zipos);//加入压缩文件
            imageFile.delete();//删除已添加图片
        }
        zipos.close();//关闭输出流
        files.delete();
    }
    
    /**
    *  为指定内容生成码，并生成png文件到指定目录
    * @param content
    * @param format BarcodeFormat.QR_CODE- 二维码
    * @param width
    * @param height
    * @param imgPath
    */
    public static void encode(String content, BarcodeFormat format, int width, int height, String imgPath){
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);     // 指定纠错等级
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");   // 指定编码格式
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height, hints);
            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 将待压缩的文件 加入压缩文件中
     * @param imageFile
     * @param zos
     * @throws IOException
     * @author Jesse
     * @date 2018年5月3日上午11:07:34
     */
    public void putZipFiles(File imageFile,ZipOutputStream zos) throws IOException {  
            if(imageFile != null && imageFile.exists()){
                //加入压缩文件中
            zos.putNextEntry(new ZipEntry(imageFile.getName()));
            
                InputStream is = null;  
                try {  
                is = new FileInputStream(imageFile);  
                byte[] buffer = new byte[1024 * 5];   
                int len = -1;  
                while((len = is.read(buffer)) != -1) {  
                //把缓冲区的字节写入到ZipEntry  
                zos.write(buffer, 0, len);  
                }  
                zos.closeEntry();   
                zos.flush();
              
                }catch(Exception e) {  
                    throw new RuntimeException(e);  
                }finally {  
                if(is != null){
                    is.close();  
                }
            }
            }
    }
    
    
}
