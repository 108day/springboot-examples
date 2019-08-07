import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.List;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.assertj.core.util.Lists;

import javax.imageio.ImageIO;


public class Test4 {

    // 图片地址
    static String filePath = "/home/holy/output/123456.gif";
    // 安装这个
    static String dataPath = "/home/holy/Tesseract-OCR/tessdata";
    static String fileDir = "/home/holy/";
    // 生成文件目录
    static String output = "/home/holy/output/";
    // 执行文件
    static String tesseractExe = "/usr/bin/tesseract";

    public static void main(String[] args) throws TesseractException {

        Test4 ocr = new Test4();
        String result11 = ocr.recognizeText(filePath, true);
        System.out.println(result11);


        List<BufferedImage> images = Lists.newArrayList();
        try {
            ITesseract instance = new Tesseract();
            File imgDir = new File(output);
            //对img_data文件夹中的每个验证码进行识别
            //文件名即正确的结果
            for (File imgFile : imgDir.listFiles()) {
                if (imgFile.toString().endsWith(".gif") || imgFile.toString().endsWith(".png") || imgFile.toString().endsWith(".jpg")) {
                    System.out.println("OCR imgFile: " + imgFile);
                    String result = ocr.recognizeText(imgFile.toString(), true);
                    System.err.println("result=====" + result);

                    File file = new File(imgFile.toString());
                    BufferedImage image = ImageIO.read(file);
                    images.add(image);
                    break;

                }

            }

            createNew(images);

//            List<String> list = getBin(images);
//            for (String _01 : list) {
//                System.out.println(_01);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String recognizeText(String imageFile, boolean isChinese) {
        String result = "";        // 保存读取到的识别内容并返回

        // 根据选项组装执行命令的字符串，注意参数之间需要加空格分隔开
        String command = tesseractExe + " " + imageFile + " " + output;
        if (isChinese) {
            // chi_tra chi_sim
            command += " -l chi_sim";
        } else {
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
                result = Pattern.compile("[^0-9]").matcher(result).replaceAll("");
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

    public static void createNew(List<BufferedImage> images) {

        BufferedImage image = images.get(0);

        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 1; i < width; i++) {
            Color colorFirst = new Color(image.getRGB(i, 1));
            int numFirstGet = colorFirst.getRed() + colorFirst.getGreen() + colorFirst.getBlue();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(image.getRGB(x, y));
                    System.out.println("red1:" + color.getRed() + " | green:" + color.getGreen() + " | blue:" + color.getBlue());
                    int num = color.getRed() + color.getGreen() + color.getBlue();
                    if ((color.getRed() == 204 && color.getGreen() == 213 && color.getBlue() == 204)
                            || (color.getRed() == 204 && color.getGreen() == 170 && color.getBlue() == 204)
                            || (color.getRed() == 153 && color.getGreen() == 170 && color.getBlue() == 204)
                            || (color.getRed() == 204 && color.getGreen() == 170 && color.getBlue() == 255)
                            || (color.getRed() == 255 && color.getGreen() == 213 && color.getBlue() == 255)
                            || (color.getRed() == 255 && color.getGreen() == 213 && color.getBlue() == 204)
                            || (color.getRed() == 204 && color.getGreen() == 255 && color.getBlue() == 255)
                            || (color.getRed() == 204 && color.getGreen() == 213 && color.getBlue() == 255)) {
                        image.setRGB(x, y, Color.WHITE.getRGB());
                    }
                    if (y < 10 || y > height - 10 || x < 10 || (x > width - 10 && x < width)) {
                        image.setRGB(x, y, Color.WHITE.getRGB());
                    }
                    if (y > 10 && y < height - 10 && x < width - 10) {
                        if (is204213204(new Color(image.getRGB(x + 1, y - 1)))
                                && is204213204(new Color(image.getRGB(x + 2, y - 1)))
                                && is204213204(new Color(image.getRGB(x + 1, y - 2)))
                                && is204213204(new Color(image.getRGB(x + 2, y - 2)))) {
                            image.setRGB(x, y, new Color(0, 170, 204).getRGB());
                            image.setRGB(x + 1, y - 1, new Color(0, 170, 204).getRGB());
                            image.setRGB(x + 2, y - 1, new Color(0, 170, 204).getRGB());
                            image.setRGB(x + 1, y - 2, new Color(0, 170, 204).getRGB());
                            image.setRGB(x + 2, y - 2, new Color(0, 170, 204).getRGB());

                        }
                    }


                }
            }
        }

        for (int i = 1; i < width; i++) {
            Color color1 = new Color(image.getRGB(i, 1));
            int num1 = color1.getRed() + color1.getGreen() + color1.getBlue();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(image.getRGB(x, y));
                    System.out.println("red2:" + color.getRed() + " | green:" + color.getGreen() + " | blue:" + color.getBlue());
                    int num = color.getRed() + color.getGreen() + color.getBlue();
                    if (num == num1) {
                        image.setRGB(x, y, Color.BLACK.getRGB());
                    } else {
                        image.setRGB(x, y, Color.WHITE.getRGB());
                    }
                }
            }
        }


        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageIO.write(image, "gif", file);
        } catch (Exception e) {

        }

    }

    public static List<String> getBin(List<BufferedImage> images) {
        List<String> list = new LinkedList<>();
        for (BufferedImage image : images) {
            StringBuilder reslut = new StringBuilder();
            for (int w = 0; w < image.getWidth(); w++) {
                for (int h = 0; h < image.getHeight(); h++) {
                    if ((image.getRGB(w, h) & 0xFFFFFF) == (new Color(204, 213, 204).getRGB() & 0xFFFFFF)) {
                        reslut.append("1");
                    } else {
                        reslut.append("0");
                    }
                }
            }
            list.add(reslut.toString());
        }
        return list;
    }


    public static boolean is204213204(Color color) {
        return (color.getRed() == 204 && color.getGreen() == 213 && color.getBlue() == 204);
    }


    public void WriteStringToFile2(String filePath, String str) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("在已有的基础上添加字符串");
            bw.write(str);// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}