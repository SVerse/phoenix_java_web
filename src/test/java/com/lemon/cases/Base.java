package com.lemon.cases;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.lemon.pojo.UIElement;
import com.lemon.util.UILibraryUtil;

public class Base {
	private Logger logger=Logger.getLogger(Base.class);
	public static WebDriver driver;
	/**初始化驱动
	 * @param browserType
	 * @param driverPath
	 */
	@BeforeSuite
	@Parameters(value={"browserType","driverPath"})
	public void init(String browserType,String driverPath){
		logger.info("配置信息：浏览器类型：【"+browserType+"】,驱动文件路径：【"+driverPath+"】");
		if ("ie".equalsIgnoreCase(browserType)) {
			System.setProperty("webdriver.ie.driver", driverPath);
			DesiredCapabilities capabilities=new DesiredCapabilities();
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    driver=new InternetExplorerDriver(capabilities);
		    logger.info("*****创建IE驱动对象，打开IE浏览器，开始测试*****");
		}else if("firefox".equalsIgnoreCase(browserType)) {
			System.setProperty("webdriver.firefox.bin", "E:\\firefox\\firefox.exe");
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver=new FirefoxDriver();
			logger.info("*****创建火狐驱动对象，打开火狐浏览器，开始测试*****");
		}else if("chrome".equalsIgnoreCase(browserType)){
			System.setProperty("webdriver.chrome.driver", driverPath);
			driver=new ChromeDriver();
			logger.info("*****创建谷歌驱动对象，打开谷歌浏览器，开始测试*****");
		}
		logger.info("浏览器窗口最大化");
		driver.manage().window().maximize();
	}
	
	/**关闭浏览器
	 * @throws InterruptedException
	 */
	@AfterSuite
	public void tearDown() throws InterruptedException{
		Thread.sleep(2000);
		logger.info("*****关闭浏览器，测试结束*****");
		driver.quit();
	}
	
	/**显式等待（智能等待）定位元素
	 * @param locator
	 * @return
	 */
	public WebElement getElement(By locator){
		WebDriverWait wait=new WebDriverWait(driver, 30);
		try {
			WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			logger.info("定位元素成功");
			return webElement;
		} catch (Exception e) {
			logger.error("定位元素超时了");
		}
		return null;
	}
	
	/**根据页面关键字和页面元素关键字获取元素
	 * @param pageKeyword   页面关键字
	 * @param elementKeyword 元素关键字
	 * @return
	 */
	public WebElement getElement(String pageKeyword,String elementKeyword){
		//根据页面关键字+元素关键字得到UIElement对象
		UIElement uiElement=UILibraryUtil.getUIElement(pageKeyword, elementKeyword);
		//通过UIElement对象获取by和value属性的值
		String by=uiElement.getBy();
		String value=uiElement.getValue();
		logger.info("根据{by："+by+",value："+value+"}来定位【"+pageKeyword+"】页面的【"+elementKeyword+"】元素");
		By locator=null;
		if ("id".equalsIgnoreCase(by)) {
			locator=By.id(value);
		}else if ("name".equalsIgnoreCase(by)) {
			locator=By.name(value);
		}else if ("xpath".equalsIgnoreCase(by)) {
			locator=By.xpath(value);
		}else if ("linkText".equalsIgnoreCase(by)) {
			locator=By.linkText(value);
		}else if ("partialLinkText".equalsIgnoreCase(by)) {
			locator=By.partialLinkText(value);
		}else if ("className".equalsIgnoreCase(by)) {
			locator=By.className(value);
		}else if ("cssSelector".equalsIgnoreCase(by)) {
			locator=By.cssSelector(value);
		}else if ("tagName".equalsIgnoreCase(by)) {
			locator=By.tagName(value);
		}
		//return driver.findElement(locator);
		return getElement(locator);
	}
	
	
	/**判断当前页面URL是否包含某个字符串信息，用了延时等待
	 * @param part 要查找的包含字符串
	 * @return
	 */
	public boolean urlPresenceContent(String part){
		WebDriverWait wait=new WebDriverWait(driver, 30);
		try {
			boolean flag = wait.until(ExpectedConditions.urlContains(part));
			logger.info("找到了指定包含的字符串"+part);
			return flag;
			
		} catch (Exception e) {
			logger.error("定位元素超时了");
		}
		return false;
	}
	
	/**访问测试页面
	 * @param url 页面地址
	 */
	public void to(String url){
		logger.info("访问测试页面：【"+url+"】");
		driver.navigate().to(url);
	}
	
	/**访问测试页面
	 * @param url 测试页面地址
	 */
	public void getUrl(String url){
		logger.info("访问测试页面：【"+url+"】");
		driver.get(url);
	}
	
	/**写入数据
	 * 
	 */
	public void sendKeys(WebElement element,String value){
		logger.info("写入数据：【"+value+"】");
		element.sendKeys(value);
	}
	
	/**点击
	 * @param element
	 */
	public void click(WebElement element){
		logger.info("完成点击定位到的元素");
		element.click();
	}
	
	/**获取元素的文本值
	 * @param element
	 */
	public String getText(WebElement element){
		String value=element.getText();
		logger.info("获取元素的文本值【"+value+"】");
		return value;
	}
}
