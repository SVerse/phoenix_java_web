package com.lemon.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.lemon.cases.Base;

public class ScreenUtil {
	private static Logger logger=Logger.getLogger(ScreenUtil.class);
	public static File takeScreenshot(String baseDir){
		WebDriver driver=Base.driver;
		File screenImg=null;
		if (driver instanceof FirefoxDriver) {
			FirefoxDriver firefoxDriver=(FirefoxDriver)driver;
			screenImg=firefoxDriver.getScreenshotAs(OutputType.FILE);
			
		}else if (driver instanceof ChromeDriver) {
			ChromeDriver chromeDriver=(ChromeDriver)driver;
			screenImg =chromeDriver.getScreenshotAs(OutputType.FILE);
		}else if (driver instanceof InternetExplorerDriver) {
			InternetExplorerDriver iExplorerDriver=(InternetExplorerDriver) driver;
			screenImg=iExplorerDriver.getScreenshotAs(OutputType.FILE);
		}
		Date date=new Date();
		long time=date.getTime();
		File destFile=new File(baseDir+File.separator+time+".jpg");
		try {
			FileUtils.copyFile(screenImg, destFile);
			logger.info("拷贝图片文件"+time+".jpg到【"+destFile+"】成功");
		} catch (Exception e) {
			logger.info("拷贝失败");
		}
		return destFile;
	}
}
