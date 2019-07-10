package com.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.entity.Movie;

/**
 * 导入电影信息工具类
 */
@Component
public class TextInput {
	
	@Value("${movie.movieInfoInputUrl}")
	private String movieInfoInputUrl;
	
	public Movie inputMovie() {
		Movie movie = new Movie();
		File file = new File(movieInfoInputUrl);
		try (
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis,"GBK");
			BufferedReader br = new BufferedReader(isr);
		){
			
			int count = 1;
			String line = null;
			while((line = br.readLine())!=null){
				String [] str = line.trim().split(": ");
				switch(count) {
					case 1:movie.setId(str[1]);break;
					case 2:String[] nameAndSuffix = str[1].split(":");movie.setName(nameAndSuffix[0]);movie.setSuffix(nameAndSuffix[1]);break;
					case 3:movie.setScore(Double.parseDouble(str[1]));break;
					case 4:movie.setAssessNum(Integer.parseInt(str[1]));break;
					case 5:movie.setDirector(str[1]);break;
					case 6:movie.setScreenWriter(str[1]);break;
					case 7:movie.setPerformer(str[1]);break;
					case 8:movie.setType(str[1]);break;
					case 9:movie.setRegion(str[1]);break;
					case 10:movie.setLanguage(str[1]);break;
					case 11:movie.setReleaseDate(str[1]);break;
					case 12:movie.setFilmLength(str[1]);break;
					case 13:movie.setAlias(str[1]);break;
					case 14:movie.setSynopsis(str[1]);break;
					case 15:movie.setRelease(Integer.parseInt(str[1]));break;
					default:break;
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}
}
