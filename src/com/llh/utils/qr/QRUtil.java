package com.llh.utils.qr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码工具类
 * 
 * @author luolh
 */
public class QRUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static final String UTF_8 = "utf-8";

	private QRUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	private static BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();
		int height = matrix.getHeight();

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}

		return image;
	}

	private static BitMatrix getBitMatrix(String content, int width,
			int height, String charset) throws WriterException {

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		MultiFormatWriter mfw = new MultiFormatWriter();
		BitMatrix bitMatrix = mfw.encode(content, BarcodeFormat.QR_CODE, width,
				height, hints);

		return bitMatrix;
	}

	/**
	 * 生成二维，输出到流
	 * @param content 需要生成二维码的内容
	 * @param width  二维长
	 * @param height 二维码高
	 * @param suffix 二维码图后缀
	 * @param out 输出流
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void generateQR(String content, int width, int height,
			String suffix, OutputStream out) throws WriterException,
			IOException {

		BitMatrix bm = getBitMatrix(content, width, height, UTF_8);
		BufferedImage image = toBufferedImage(bm);
		if(!ImageIO.write(image, suffix, out)){
			throw new IOException("write image fail!");
		}
	}

	/**
	 * 生成二维文件
	 * @param content 需要生成二维码的内容
	 * @param width 二维长
	 * @param height 二维码高
	 * @param suffix 二维码图后缀
	 * @param file 生成的文件
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void generateQR(String content, int width, int height,
			String suffix, File file) throws WriterException, IOException {

		BitMatrix matrix = getBitMatrix(content, width, height, UTF_8);
		BufferedImage image = toBufferedImage(matrix);
		if(!ImageIO.write(image, suffix, file)){
			throw new IOException("write image fail!");
		}
	}

	/**
	 * 生成二维码
	 * @param content 需要生成二维码的内容
	 * @param width 二维长
	 * @param height 二维码高
	 * @param file 生成的文件，必须包含图片的后缀名称，否则抛出异常
	 * @throws Exception
	 */
	public static void generateQR(String content, int width, int height,
			File file) throws Exception {

		String fileName = file.getName();

		if (fileName.indexOf(".") == -1) {
			throw new Exception("文件名称必须包含图片的扩展名称");
		}

		String[] array = fileName.split("\\.");

		String suffix = array[array.length - 1];

		generateQR(content, width, height, suffix, file);
	}
	
	public static void main(String[] args) throws Exception {
		String[] text ={"http://www.baidu.com", "luolieh", "罗列何", "测试内容"};

		File dir = new File("D://QR"), imgFile = null;
		
		if(!dir.exists()){
			dir.mkdirs();
		}

		for (String context : text) {
			
			imgFile = new File(dir, System.currentTimeMillis()+".png");
			
			// 生成二维码
			QRUtil.generateQR(context, 300, 300, imgFile);
			
			System.out.println(imgFile);
		}
		
	}
	
}

