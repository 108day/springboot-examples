package com.example.demo;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestTess4j {
    public static void main(String[] args) {
        //图片所在文件夹
        testTess4j("D:\\code\\1564751069080.gif");
    }
    public static void testTess4j(String filePath){
        File file = new File(filePath);
        ITesseract instance = new Tesseract();
        try {
            String result = instance.doOCR(file);
            String fileName = file.toString().substring(file.toString().lastIndexOf("\\")+1);
            System.out.println("图片名：" + file.toString() +" 识别结果："+result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}