package com.lemon.cases;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.util.ExcelUtil;

public class RegisterCaseV2 extends Base {
	/**
	 * 测试失败的用例
	 */
	@Test(dataProvider="failCaseDatas")
	public void failCases(String username,String pwd,String confirmpwd,String expected){
		driver.navigate().to("http://39.108.136.60:8085/lmcanon_web_auto/mng/register.html");
		driver.findElement(By.id("mobilephone")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.id("pwdconfirm")).sendKeys(confirmpwd);
		driver.findElement(By.id("signup-button")).click();
		String errorMsg=driver.findElement(By.className("tips")).getText();
		Assert.assertEquals(errorMsg, expected);
	}

	/**
	 * 测试成功的用例
	 */
	@Test(dataProvider="sucessCaseDatas")
	public void sucessCase(String username,String pwd,String confrinpwd){
		driver.navigate().to("http://39.108.136.60:8085/lmcanon_web_auto/mng/register.html");
		driver.findElement(By.id("mobilephone")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(pwd);
		driver.findElement(By.id("pwdconfirm")).sendKeys(confrinpwd);
		driver.findElement(By.id("signup-button")).click();
		//拿地址的时候页面可能还没调整，需要时间
		Assert.assertTrue(urlPresenceContent("login.html"));
	}
	
	@DataProvider
	public Object[][] failCaseDatas(){
		String []cellNames={"用户名","密码","重复密码","期望值"};
		Object [][] datas=ExcelUtil.read2("register-fail","src/test/resources/register.xlsx",cellNames);

		return datas;
	}
	
	@DataProvider
	public Object [][] sucessCaseDatas(){
		String [] cellNames={"用户名","密码","重复密码"};
		Object [][] datas=ExcelUtil.read2("register-success","src/test/resources/register.xlsx",cellNames);
		return datas;
	}
	
}
