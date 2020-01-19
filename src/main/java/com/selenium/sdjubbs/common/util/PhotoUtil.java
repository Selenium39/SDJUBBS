package com.selenium.sdjubbs.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 *@author:wantao
 *@time:Jul 11, 2018 10:14:27 AM
 *@description:
 */
@Component
public class PhotoUtil {
    // 生成略缩图
    public static String createSmallPhoto(String photoPath) throws IOException {
        String[] smallPhotoPathTemp = photoPath.split("\\.");
        String smallPhotoPath = smallPhotoPathTemp[0] + "_small" + "." + smallPhotoPathTemp[1];
        File file = new File(photoPath);
        BufferedImage src = null;
        src = ImageIO.read(file);
        int width = 100;
        int height = 80;
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 绘制略缩图
        tag.getGraphics().drawImage(src, 0, 0, width, height, null);
        FileOutputStream out = new FileOutputStream(smallPhotoPath);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        out.close();
        return smallPhotoPath;

    }


    public String addWaterMark(String srcImgPath, String waterMarkContent) {
        String waterPhotoPath = null;
        try {
            String[] waterPhotoPathTemp = srcImgPath.split("\\.");
            waterPhotoPath = waterPhotoPathTemp[0] + "_water" + "." + waterPhotoPathTemp[1];
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);// 得到文件
            BufferedImage srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            Color markContentColor = Color.red;
            g.setColor(markContentColor); // 根据图片的背景设置水印颜色
            g.setFont(new Font("微软雅黑", Font.PLAIN, 35)); // 设置字体

            // 设置水印的坐标
            int x = srcImgWidth - 2 * getWatermarkLength(waterMarkContent, g);
            int y = srcImgHeight - 2 * getWatermarkLength(waterMarkContent, g);
            g.drawString(waterMarkContent, x, y); // 画出水印
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(waterPhotoPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return waterPhotoPath;
    }

    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /**
     * 功能描述 保存图片
     *
     * @param filedata 文件数据 返回图片位置
     */
    public static String saveFile(MultipartFile filedata, String pathval) {
        File fileDir = new File(pathval);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 上传的文件名
        String filename = System.currentTimeMillis() + "_" + filedata.getOriginalFilename();
        // 总的文件路径
        String filePath = pathval + "/" + filename;
        File file = new File(System.getProperty("user.dir") + "/" + filePath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
            return filePath;// 返回图片保存路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}