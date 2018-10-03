package com.advisen.excelconverter;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelConverterTester {

	//private static final String FILE_NAME = "C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_EPL.xlsx";
	private static final String FILE_NAME = "C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_CY.xlsx";
	
    public static void main(String[] args) {

        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
          
            String uiSection= "";
            String uiFieldName= "";
            int num = workbook.getNumberOfSheets();
          List<Category> categories = new ArrayList<Category>();
            System.out.println("Number Of sheets : "+num);
            List<StgLossDataUIField> list = new ArrayList<StgLossDataUIField>();
            for(int x= 0; x < num; x++) {
            	  Sheet datatypeSheet = workbook.getSheetAt(x);
            	  Sheet sheet =  workbook.getSheetAt(x);
            	
            	  System.out.println(datatypeSheet.getSheetName());
            	 
            	  if(!datatypeSheet.getSheetName().equalsIgnoreCase("GUIDE")) {
                  int rowsCount = sheet.getLastRowNum();
                   Category category = null;
                  for (int rowSheet = 0; rowSheet <= rowsCount; rowSheet++) {
                      Row row = sheet.getRow(rowSheet);
                      int colCounts = row.getLastCellNum();
                      System.out.println("Total Number of Cols: " + colCounts);
                      
                      
                      
                      for (int columnSheet = 0; columnSheet < colCounts; columnSheet++) {                    	  
                    	 
                          Cell cell = row.getCell(columnSheet);
                          if (cell != null && cell.getCellTypeEnum() == CellType.STRING) {	
                        	  if(rowSheet == 0) {     
                        		  if(category != null) {                        			  
                        			  category.setLastRow(columnSheet);  
                        			  category = new Category();
                        		  }else {
                        			  category = new Category();
                        		  }
                        		                        		  
                        		  category.setFirstRow(columnSheet);
                        		  category.setName(cell.getStringCellValue());
                        		  category.setLastRow(colCounts + 1); 
                        		  category.setData(new ArrayList<StgLossDataUIField>());
                        		  categories.add(category);
                        		  
                        	  }else if(rowSheet == 1 && columnSheet > 1) {
                        		  for(Category cat : categories) {
                        			  Integer lastRow = cat.getLastRow() - 1;
                        			  Integer firstRow = cat.getFirstRow();
                        			  if(firstRow <= columnSheet && lastRow > columnSheet) {
                        				  StgLossDataUIField stgLossDataUIField = new StgLossDataUIField();
                        				  stgLossDataUIField.setRiskType(cell.getStringCellValue());
                        				  stgLossDataUIField.setColumn(columnSheet);
                        				  stgLossDataUIField.setFieldNameValue(new ArrayList<FieldNameValue>());
                        				  cat.getData().add(stgLossDataUIField);
                        				  break;
                        			  }
                                  	//System.out.println(cat.getName() +" ====== "+ cat.getFirstRow());
                                  }
                        	  }else if(cell != null && columnSheet >= 2 && cell.getStringCellValue() != null) {
                        		  for(Category cat : categories) {
                        			  Integer lastRow = cat.getLastRow() - 1;
                        			  Integer firstRow = cat.getFirstRow();
                        			  if(firstRow <= columnSheet && lastRow > columnSheet) {        
                        				  for(StgLossDataUIField stgLossDataUIField : cat.getData() ) {
                        					  if(stgLossDataUIField.getColumn() == columnSheet) {
                        						 FieldNameValue fieldNameValue = new FieldNameValue();
                                  				 fieldNameValue.setUiDisplayCode(cell.getStringCellValue());
                                  				 fieldNameValue.setUiSection(uiSection);
                                  				 fieldNameValue.setUiFieldName(uiFieldName); 
                                  				 stgLossDataUIField.getFieldNameValue().add(fieldNameValue);
                        						  break;
                        					  }
                        				  }
                        				
                        				  break;
                        			  }
                                  	//System.out.println(cat.getName() +" ====== "+ cat.getFirstRow());
                                  }
                        	  }else if(cell != null && rowSheet >= 2 && columnSheet == 0) {
                        		  if(!cell.getStringCellValue().equals("")) {
                        			  uiSection =cell.getStringCellValue();
                        		  }
                        		  
                        	  }else if(cell != null && rowSheet >= 2 && columnSheet == 1) {
                        		  if(!cell.getStringCellValue().equals("")) {
                        			  uiFieldName =cell.getStringCellValue();
                        		  }
                        		
                        	  }
                        	  
                        	  
                        	
		                       System.out.println("[" + rowSheet + "," + columnSheet + "]=" + cell.getStringCellValue());
		                    } else if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC) {		                        
		                        
		                    }else {
		                    	
		                    }
                        
                      }
                  }
                  
            	  }
            }
            
            for(Category cat : categories) {            
            	for(StgLossDataUIField stg : cat.getData()) {
            		for(FieldNameValue field : stg.getFieldNameValue()) {
                		System.out.print("Category : "+cat.getName());
                		System.out.print(", RiskType : "+stg.getRiskType() );
                		System.out.print(", UiSection :"+field.getUiSection());
                		System.out.print(", UiFieldName :"+field.getUiFieldName());
                		System.out.println(", UI COde :"+field.getUiDisplayCode());
                		
                		
                	}
            	}
            	///System.out.println("Count Data :" +cat.getData().size());
            	//System.out.println("Count Field :" +cat.getFieldNameValue().size());
            	
            	
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
