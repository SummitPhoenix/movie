package com.web.util;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {

	public static final String QRCODE_CHARSET = "UTF-8";

	public static final int QRCODE_HEIGHT = 150;

	public static final int QRCODE_WIDTH = 150;

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BufferedImage createQRCode(String data) {
		String charset = QRCODE_CHARSET;
		int width = QRCODE_HEIGHT;
		int height = QRCODE_WIDTH;
		Map hint = new HashMap();
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hint.put(EncodeHintType.CHARACTER_SET, charset);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BitMatrix matrix = null;
		try {
			matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE,width, height, hint);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static String getQRCode(String data, String ticketId) {
		BufferedImage image = createQRCode(data);
		String qrCodeFileUrl = "D://QRCode"+ticketId+".png";
		try {
			FileOutputStream fos = new FileOutputStream(qrCodeFileUrl);
			ImageIO.write(image, "png", fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return qrCodeFileUrl; 
	}
	
}
