package com.selenium.sdjubbs.common.util;

import com.google.zxing.*;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrUtil {
    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @throws Exception
     */
    public static void createQr(String content, int width, int height,String savePath,String imageName) throws Exception {
        //二维码参数基本设置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置编码为UTF-8
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        //设置二维码纠错等级
        // L:7%纠错率  M:15%纠错率 Q:25%纠错率 H:30纠错率   纠错率越高越容易识别出来,但是纠错率越高识别速度越慢
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码白边的范围(此值可能不生效)
        hints.put(EncodeHintType.MARGIN, 1);
        //设置生成的图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        //生成二维码对应的位矩阵对象
        BitMatrix matrix = new MultiFormatWriter().encode(content, format, width, height);
        //设置位矩阵对象转图片的参数(前景色,背景色)
        MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        //位矩阵对象转BufferedImage对象
        BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix, config);
        //将BufferedImage对象保存到本地
        ImageIO.write(qrcode, "png",new File(savePath+File.separator+imageName+".png"));
    }

    /**
     * 生成带logo的二维码
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param scale   logo所占二维码比例,如果scale设置太小,即logo占二维码比例太大会识别不出二维码
     * @throws Exception
     */
    public static void createQrWithLogo(String content, int width, int height, int scale) throws Exception {
        //二维码参数基本设置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置编码为UTF-8
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        //设置二维码纠错等级
        // L:7%纠错率  M:15%纠错率 Q:25%纠错率 H:30纠错率   纠错率越高越容易识别出来,但是纠错率越高识别速度越慢
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码白边的范围(此值可能不生效)
        hints.put(EncodeHintType.MARGIN, 1);
        //设置生成的图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        //生成二维码对应的位矩阵对象
        BitMatrix matrix = new MultiFormatWriter().encode(content, format, width, height);
        //设置位矩阵对象转图片的参数(前景色,背景色)
        MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        //位矩阵对象转BufferedImage对象
        BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix, config);
        //读取logo图片
        BufferedImage logo = ImageIO.read(new File("C:\\src\\idea\\qr-java\\logo.jpg"));
        //设置logo宽和高
        int logoWidth = qrcode.getWidth() / scale;
        int logoHeight = qrcode.getHeight() / scale;
        //设置返回logo的二维码图片的起始位置
        int x = (qrcode.getWidth() - logoWidth) / 2;
        int y = (qrcode.getHeight() - logoHeight) / 2;
        //新建空画板
        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //新建画笔
        Graphics2D g = (Graphics2D) canvas.getGraphics();
        //将二维码绘制到画板
        g.drawImage(qrcode, 0, 0, null);
        //设置不透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        //将logo绘制到画板上
        g.drawImage(logo, x, y, logoWidth, logoHeight, null);
        //将画板(BufferedImage)对象保存到本地
        ImageIO.write(canvas, "png", new File("C:\\src\\idea\\qr-java\\qrwithlogo.png"));
    }

    /**
     * 识别二维码
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NotFoundException
     */
    public static String recogQr(File file) throws IOException, NotFoundException {
        MultiFormatReader reader = new MultiFormatReader();
        //将图片文件转为BufferedImage对象
        BufferedImage bufferedImage = ImageIO.read(file);
        //生成BinaryBitmap对象
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //二维码参数基本设置
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        //识别二维码
        Result result = reader.decode(binaryBitmap, hints);
        bufferedImage.flush();
        return result.getText();
    }
}
