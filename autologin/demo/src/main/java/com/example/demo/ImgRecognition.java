package com.example.demo;


import java.io.*;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 本教程由 http://bbs.datahref.com/ 提供
 * Tess4j验证码识别示例
 * 工程中tessdata文件夹包含了识别英文所需的数据
 * 需要识别其他语言课到https://github.com/tesseract-ocr/tessdata下载相关数据
 * 放到tessdata文件夹中
 */
public class ImgRecognition {

    static String filePath = "D:\\code\\7796.gif";

    static String output = "D:\\data";

    public static void main(String[] args) {

        ImgRecognition ocr = new ImgRecognition();
        String result = ocr.recognizeText(filePath, false);
        System.out.println(result);
    }


    public String recognizeText(String imageFile, boolean isChinese) {
        String result = "";        // 保存读取到的识别内容并返回
        String tesseractExe = "D:\\demo\\tessdata";

        // 根据选项组装执行命令的字符串，注意参数之间需要加空格分隔开
        String command = tesseractExe + " " + imageFile + " " + output;
        if (isChinese) {
            command += " -l chi_sim";
        } else{
            command += " -l eng";
        }
        System.out.println(command);    // 对输出命令进行确认，成功后该行代码 可删除

        try {
            // 使用Process来获取执行命令的结果，并对其结果进行判断
            Process process = Runtime.getRuntime().exec(command);
            int exeCode = process.waitFor();
            // 执行的结果代码如果为0，表示命令执行成功
            if (exeCode == 0) {
                // 读取到输出文件中的内容，并将其赋值给变量result
                InputStream fis = new FileInputStream(output + ".txt");
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                result = br.readLine();


                String line = null;
                while ((line = br.readLine()) != null && br.readLine() != null) {
                    System.out.println(line);
                }

//                String line = br.readLine();
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }


                br.close();
                fis.close();
            } else {
                System.out.println("本次识别操作命令未正常执行.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}