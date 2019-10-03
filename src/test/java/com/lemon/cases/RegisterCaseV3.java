package com.lemon.cases;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.util.AssertionUtil;
import com.lemon.util.ExcelUtil;

public class RegisterCaseV3 extends Base {
	private Logger logger=Logger.getLogger(RegisterCaseV3.class);
	/**
	 * 测试失败的用例
	 */
	@Test(dataProvider="failCaseDatas")
	public void failCases(String username,String pwd,String confirmpwd,String expected){
		to("http://39.108.136.60:8085/lmcanon_web_auto/mng/register.html");
		sendKeys(getElement("注册页", "用户名"),username);
		sendKeys(getElement("注册页", "密码"),pwd);
		sendKeys(getElement("注册页", "重复密码"),confirmpwd);
		click(getElement("注册页", "注册按钮"));
		String errorMsg=getText(getElement("注册页", "错误提示"));
		AssertionUtil.assertTextEquals(errorMsg, expected);
	}

	/**
	 * 测试成功的用例
	 */
	@Test(dataProvider="sucessCaseDatas",enabled=true)
	public void sucessCase(String username,String pwd,String confrinpwd){
		to("http://39.108.136.60:8085/lmcanon_web_auto/mng/register.html");
		sendKeys(getElement("注册页", "用户名"),username);
		sendKeys(getElement("注册页", "密码"),pwd);
		sendKeys(getElement("注册页", "重复密码"),confrinpwd);
		click(getElement("注册页", "注册按钮"));
		//拿地址的时候页面可能还没调整，需要时间
		AssertionUtil.assertTrue(urlPresenceContent("login.html"));
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
