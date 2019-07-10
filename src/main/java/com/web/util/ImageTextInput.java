package com.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.entity.ImageText;

@Component
public class ImageTextInput {
	
	@Value("${movie.imageTextInputUrl}")
	private String imageTextInputUrl;
	
	public ImageText inputImageText() {
		File file = new File(imageTextInputUrl);
		ImageText imageText = new ImageText();
		try (
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis,"GBK");
			BufferedReader br = new BufferedReader(isr);
		){
			StringBuilder sb = new StringBuilder();
			String line = null;
			imageText.setId(br.readLine());
			imageText.setTitle(br.readLine());
			imageText.setBrief(br.readLine());
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			String content = sb.toString();
			imageText.setContent(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageText;
		
	}
}
