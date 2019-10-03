package com.lemon.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lemon.pojo.Page;
import com.lemon.pojo.UIElement;

/**解析UI库，提供页面元素信息
 * @author lym
 *
 */
public class UILibraryUtil {
	//准备一个list集合保存所有的page对象
	public static List<Page> pageList=new ArrayList<Page>();
	private  static Logger logger=Logger.getLogger(UILibraryUtil.class);
	//通过静态代码块提前准备所有的元素数据，因为静态代码块最先执行
	static{
		parse();
	}
	
	/**从pageList里面取出满足条件的页面元素返回
	 * @param pageKeyword 页面关键字
	 * @param elementKeyword 元素关键字
	 * @return
	 */
	public static UIElement getUIElement(String pageKeyword,String elementKeyword){
		for (Page page : pageList) {
			if (pageKeyword.equals(page.getKeyword())) {
				List<UIElement> elements=page.getUiElements();
				for (UIElement uiElement : elements) {
					if (elementKeyword.equals(uiElement.getKeyword())) {
						return uiElement;
					}
				}
			}
		}
		return null;
	}
	
	
	/**读取UILibrary.xml配置文件
	 * 
	 */
	private static void parse(){
		//UI库文件路径
		String filePath="src/test/resources/UILibrary.xml";
		//创建解析器
		SAXReader reader=new SAXReader();
		try {
			//获取文档对象模型
			Document document=reader.read(new File(filePath));
			//获取根节点
			Element root=document.getRootElement();
			//获取根元素底下的所有page元素
			List<Element> pgList=root.elements("Page");
			//循环处理每一个page元素
			for (Element page : pgList) {
				//获取page的关键字信息
				String keyword=page.attributeValue("keyword");
				logger.info("获取页面关键字信息：【"+keyword+"】");
				//获取当前Page底下的所有UIElement
				logger.info("开始循环获取Page页面下所有的UIElement元素");
				List<Element> uiElements=page.elements("UIElement");
				List<UIElement> UIElements=new ArrayList<UIElement>();
				for (Element uiElement : uiElements) {
					String uiElementKwd=uiElement.attributeValue("keyword");
					logger.info("获取uiElement的关键字信息：【"+uiElementKwd+"】");
					String uiElementBy=uiElement.attributeValue("by");
					logger.info("获取uiElement的by信息：【"+uiElementBy+"】");
					String uiElementValue=uiElement.attributeValue("value");
					logger.info("获取uiElement的value信息：【"+uiElementValue+"】");
					//将得到的属性值封装成UIElement对象
					UIElement UIElement=new UIElement();
					UIElement.setKeyword(uiElementKwd);
					UIElement.setBy(uiElementBy);
					UIElement.setValue(uiElementValue);
					logger.info("封装为UIElement对象完成");
					//将UIElement对象添加到集合中
					UIElements.add(UIElement);
					logger.info("将UIElement对象添加到UIElements集合中完成");
					
				}
				//封装Page对象
				Page pg=new Page();
				pg.setKeyword(keyword);
				pg.setUiElements(UIElements);
				logger.info("封装页面Page对象完成");
				//将page对象添加到集合
				pageList.add(pg);
				logger.info("将页面page对象添加到pageList集合中完成");
			}
		} catch (Exception e) {
			logger.info("解析UILibrary.xml文件出错");
			e.printStackTrace();
		}
				
	}
}
