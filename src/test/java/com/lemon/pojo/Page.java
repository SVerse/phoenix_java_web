package com.lemon.pojo;

import java.util.List;

/**页面对象类型
 * @author lym
 *
 */
public class Page {
	private String keyword;
	private List<UIElement> uiElements;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<UIElement> getUiElements() {
		return uiElements;
	}
	public void setUiElements(List<UIElement> uiElements) {
		this.uiElements = uiElements;
	}
	
}
