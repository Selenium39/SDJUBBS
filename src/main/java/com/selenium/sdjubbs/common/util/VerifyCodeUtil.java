package com.selenium.sdjubbs.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 随机生成验证码
 * 
 * @author selenium
 *
 */
public class VerifyCodeUtil {

	public static String drawVerifyCode(int width, int height,String savePath,String imageName) throws IOException {
		StringBuffer verifyCode = new StringBuffer();
		Random random = new Random();
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Font font = new Font("微软雅黑", Font.BOLD, 40);
		int degree = random.nextInt() % 30; // 旋转角度小于30度
		int x = 10; // 旋转原点的 x 坐标
		int ROTATE_Y = 45;// 旋转原点的 y 坐标
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setColor(Color.WHITE);// 设置画笔颜色,也是验证码的背景色
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(font);

		// 绘制验证码
		for (int i = 0; i < 4; i++) {
			graphics.setColor(getRandomColor());
			// 正向旋转
			graphics.rotate(degree * Math.PI / 180, x, ROTATE_Y);
			String ch = getRandomChar();
			verifyCode.append(ch);
			graphics.drawString(ch, x, ROTATE_Y);
			// 反向旋转
			graphics.rotate(-degree * Math.PI / 180, x, ROTATE_Y);
			x += 48;
		}

		// 画干扰线
		for (int i = 0; i < 6; i++) {
			graphics.setColor(getRandomColor());
			graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width),
					random.nextInt(height));

		}

		// 添加噪点
		for (int i = 0; i < 30; i++) {
			graphics.setColor(getRandomColor());
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			graphics.fillRect(x1, y1, 2, 2);

		}
		File dir =new File(savePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		ImageIO.write(bufferedImage, "png",new File(savePath+File.separator+imageName+".png"));
		return verifyCode.toString();
	}

	/**
	 * 随机取色
	 */
	private static Color getRandomColor() {
		Random random = new Random();
		Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		return color;

	}

	/**
	 * 随机生成每个验证码
	 * 
	 * @return
	 */
	private static String getRandomChar() {
		String baseString = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
		Random random = new Random();
		String ch = baseString.charAt(random.nextInt(baseString.length())) + "";
		return ch;
	}

}
