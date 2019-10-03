package com.lemon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sun.jna.platform.win32.LMAccess.GROUP_USERS_INFO_0;

/**Excel解析工具类
 * @author lym
 *
 */
public class ExcelUtil {
	private static Logger logger=Logger.getLogger(ExcelUtil.class);
	/**从Excel文件读取数据
	 * @return
	 */
	public static Object[][] read(String sheetName,String filePath,int startRowNum,int endRowNum,int startCellNum,int endCellNum){
		//首先获取一个Workbook对象
		InputStream iStream = null;
		//先声明数组
		Object[][] datas=null;
		try {
			iStream = new FileInputStream(new File(filePath));
			Workbook workbook=WorkbookFactory.create(iStream);
			//拿到一个表单（Sheet）对象
			Sheet sheet=workbook.getSheet(sheetName);
			//拿到要操作的Row对象（行）
			//初始化数组
			datas=new Object[endRowNum-startRowNum+1][endCellNum-startCellNum+1];
			//循环取出行
			for(int i=startRowNum;i<=endRowNum;i++){
				Row row=sheet.getRow(i-1);
				//拿到要操作的Cell对象（列）循环取出列
				for (int j = startCellNum; j <=endCellNum; j++) {
					Cell cell=row.getCell(j-1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					//将列设置为字符串类型
					cell.setCellType(CellType.STRING);
					//取出当前列的值
					String value=cell.getStringCellValue();
					logger.info("第【"+i+"】行，第【"+j+"】列的值为：【"+value+"】");
					//将数据保存到数组中
					datas[i-startRowNum][j-startCellNum]=value;
					logger.info("数组data["+(i-startRowNum)+"]["+(j-startCellNum)+"]=【"+value+"】");
				}
			}
		} catch (Exception e) {
			logger.info("解析Excel用例文件出错");
		}finally {
			if (iStream!=null) {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return datas;
	}
	
	/**读取Excel中的数据
	 * @param sheetName 表单名
	 * @param filePath 要读取的文件路径
	 * @param cellNames  要读取的列名（数组）
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Object[][] read2(String sheetName,String filePath,String[] cellNames) {
		InputStream iStream=null;
		ArrayList<ArrayList<String>> groups=null;
		try {
		    iStream=new FileInputStream(new File(filePath));
			Workbook workbook=WorkbookFactory.create(iStream);
			Sheet sheet=workbook.getSheet(sheetName);
			//利用map保存列名与索引的映射关系
			Map<String,Integer>  cellNameAndCellNumMap=new HashMap<String, Integer>();
			//获取所有的标题数据以及每一个标题所在的列索引
			Row titleRow=sheet.getRow(0);
			//取出表单中列的个数
			int lastCellNum=titleRow.getLastCellNum();
			//循环取出标题行的每一列，即每一个标题
			for (int i = 0; i < lastCellNum; i++) {
				Cell cell=titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				//标题
				String title=cell.getStringCellValue();
				//取出当前标题列的索引
				int cellNum=cell.getAddress().getColumn();
				cellNameAndCellNumMap.put(title, cellNum);
				logger.info("将{标题列："+title+"，索引："+cellNum+"}添加到map集合成功");
			}
			//取出所有行，标题行除外
			//获取最后一行的索引
			int lastRowNum=sheet.getLastRowNum();
			//groups保存多组数据即多少行数据
			groups=new ArrayList<ArrayList<String>>();
			for (int i = 1; i <= lastRowNum; i++) {
				//每一行的数据放入一个集合中
				ArrayList<String> cellValuesPerRow=new ArrayList<String>();
				Row row=sheet.getRow(i);
				if (isEmpty(row)) {
					continue;
				}
				//取出此行上面对应的列数据
				for (int j = 0; j < cellNames.length; j++) {
					String cellName=cellNames[j];
					//根据列名，从map中获取列索引
					int cellNum=cellNameAndCellNumMap.get(cellName);
					logger.info("根据列名：【"+cellName+"】得到的列索引为：【"+cellNum+"】");
					Cell cell=row.getCell(cellNum, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String value=cell.getStringCellValue();
					logger.info("第"+i+"行第"+j+"列的单元格的值为：【"+value+"】");
					//将值放入到集合中
					cellValuesPerRow.add(value);
				}
				groups.add(cellValuesPerRow);
			}
		} catch (Exception e) {
			logger.info("解析Excel用例文件出错");
			e.printStackTrace();
		}finally {
			if (iStream!=null) {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return listToArray(groups);
		
	}
	
	/**将集合转换为数组
	 * @param groups
	 * @return
	 */
	private static Object[][] listToArray(ArrayList<ArrayList<String>> groups){
		int size1=groups.size();//取出所有组数
		int size2=groups.get(0).size();//每一组的数据个数
		Object[][] datas=new Object[size1][size2];
		for (int i = 0; i < size1; i++) {
			ArrayList<String> group=groups.get(i);
			for (int j = 0; j < size2; j++) {
				String value=group.get(j);
				datas[i][j]=value;
				logger.info("数组data["+i+"]["+j+"]=【"+value+"】");
			}
		}
		return datas;
	}
	
	/**判断是否为空行
	 * @param row
	 * @return
	 */
	private static boolean isEmpty(Row row){
		int lastCellNum=row.getLastCellNum();
		for (int i = 0; i<lastCellNum; i++) {
			Cell cell=row.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			String value=cell.getStringCellValue();
			if (value!=null&&value.trim().length()>0) {//"  "有空格情况
				return false;
			}
		}
		return true;
	}
}
