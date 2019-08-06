package com.example.demo;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;



import java.awt.image.BufferedImage;
import java.io.*;


/**
  * 测试类
  */
public class TestOne {
    public static void main(String[] args) throws Exception {
        /*String img = "D:\\code\\1564751069080.gif";
        String code = getImgContent(img);
        System.out.println("验证码 = " + code);*/
        String img = "D:\\code\\data";
        File root = new File(img);
        ITesseract instance = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setLanguage("eng");//英文库识别数字比较准确
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        try {
            File[] files = root.listFiles();
            for (File file : files) {
                String result = instance.doOCR(file);
                String fileName = file.toString().substring(file.toString().lastIndexOf("\\")+1);
                System.out.println("图片名：" + fileName +" 识别结果："+result);
            }
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

    }


    protected static String getImgContent(String imgUrl) {
        String content = "";
        File imageFile = new File(imgUrl);
        //读取图片数字
        ITesseract instance = new Tesseract();


        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setLanguage("eng");//英文库识别数字比较准确
        instance.setDatapath(tessDataFolder.getAbsolutePath());


        try {
            content = instance.doOCR(imageFile).replace("\n", "");
            System.out.println(content);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return content;
    }

    private BufferedImage enlargement(BufferedImage image) {
        image = ImageHelper
                .convertImageToGrayscale(ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight())); // 对图片进行处理
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 5, image.getHeight() * 5); // 将图片扩大5倍
        return image;

    }
}
