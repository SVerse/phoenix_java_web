package com.lemon.cases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterCase extends Base{
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
	@DataProvider
	public Object[][] failCaseDatas(){
		Object [][] datas={
				{"","","","用户名不能为空"},
				{"15031216170","","","密码不能为空"},
				{"15031216170","123456","","重复密码不能为空"},
				{"15031216170","12345","12345","密码长度至少为6位"},
				{"15031216170","123456","1234567","密码不一致"},
				{"15021","123456","123456","非法的手机号"}
		};
		return datas;
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
	public Object [][] sucessCaseDatas(){
		Object [][] datas={
				
				{"15031216170","123456","123456"}
		};
		return datas;
	}
}
