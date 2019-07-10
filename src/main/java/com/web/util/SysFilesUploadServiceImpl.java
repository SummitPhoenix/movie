package com.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

public class SysFilesUploadServiceImpl {

	public static String upload(List<MultipartFile> files) {

//		File path = null;
//		try {
//			path = new File(ResourceUtils.getURL("classpath:").getPath());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		System.out.println(path.getAbsolutePath());
//
//		File upload = new File(path.getAbsoluteFile(), "static/img/imageText/");
//		String STORAGE_PATH = upload.getAbsolutePath() + "\\";
		String STORAGE_PATH = "D:\\workspace-spring-tool-suite-4-4.0.1.RELEASE\\Movie\\src\\main\\resources\\static\\img\\imageText\\";
		System.out.println("upload url:"+STORAGE_PATH);

		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;

		// 判断存储的文件夹是否存在
		File file = new File(STORAGE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			// 遍历文件夹
			for (MultipartFile mf : files) {
				if (!mf.isEmpty()) {
					String originalFilename = mf.getOriginalFilename();

					String fileName = originalFilename.substring(originalFilename.lastIndexOf('/') + 1);

					System.out.println(fileName);
					// 读取文件
					bis = new BufferedInputStream(mf.getInputStream());
					// 指定存储的路径
					fos = new FileOutputStream(STORAGE_PATH + fileName);
					bos = new BufferedOutputStream(fos);
					
					int len = 0;
					byte[] buffer = new byte[10240];
					while ((len = bis.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					
					// 刷新此缓冲的输出流，保证数据全部都能写出
					bos.flush();
					bos.close();
					fos.close();
					bis.close();
				}
			}

			return "ok";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

}