package com.lemon.util;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**断言工具类
 * @author lym
 *
 */
public class AssertionUtil {
	private static Logger logger=Logger.getLogger(AssertionUtil.class);
	/**断言两者一致的情况
	 * @param actual
	 * @param expected
	 */
	public static void assertTextEquals(String actual,String expected){
		logger.info("断言比较两者的值是否一致，实际值为：【"+actual+"】 期望值为：【"+expected+"】");
		Assert.assertEquals(actual, expected);
	}
	
	/**断言为真的情况
	 * @param actual
	 */
	public static void assertTrue(boolean actual){
		logger.info("断言是否为真，实际值：【"+actual+"】");
		Assert.assertTrue(actual);
	}
}
