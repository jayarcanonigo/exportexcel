package com.advisen.excelconverter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.advisen.excelconverter.dao.LossDataUiFieldDao;

public class ExcelConverterApp {
	public static void main(String[] args) {
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("Spring-Module.xml");
	    	 
	        LossDataUiFieldDao lossDataUiFieldDao = (LossDataUiFieldDao) context.getBean("lossDataDAO");
	        ExcelConverter excel = new ExcelConverter();	    
	        lossDataUiFieldDao.insertLossData(excel.getCategory());
	        System.out.println();
	}
}
