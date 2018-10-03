package com.advisen.excelconverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelConverter {

	// private static final String FILE_NAME = "C:\\Users\\user\\Downloads\\New
	// Tool_Case Cat-Type_Relevant Fields_CY.xlsx";
	public List<List<Category>> getCategory() {

		String[] excelFiles = { "C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_EPL.xlsx",
				"C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_PC&SR.xlsx",
				"C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_ML.xlsx",
				"C:\\Users\\user\\Downloads\\New Tool_Case Cat-Type_Relevant Fields_CY.xlsx" 
				};
		List<List<Category>> items = new ArrayList<List<Category>>();
		for (String FILE_NAME : excelFiles) {
			try {

				FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
				Workbook workbook = new XSSFWorkbook(excelFile);
				FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
				String uiSection = "";
				String uiFieldName = "";
				int num = workbook.getNumberOfSheets();
				//System.out.println("Number Of sheets : " + num);	 		
				for (int x = 0; x < num; x++) {
					List<Category> categories = new ArrayList<Category>();
					Sheet datatypeSheet = workbook.getSheetAt(x);
					Sheet sheet = workbook.getSheetAt(x);
					String status = datatypeSheet.getSheetName();
					//System.out.println("Sheet Name : "+datatypeSheet.getSheetName());
					//System.out.println("File Name : "+FILE_NAME);
					

					if (!datatypeSheet.getSheetName().equalsIgnoreCase("GUIDE")) {
						int rowsCount = sheet.getLastRowNum();
						Category category = null;
						for (int rowSheet = 0; rowSheet <= rowsCount; rowSheet++) {
							Row row = sheet.getRow(rowSheet);
							int colCounts = row.getLastCellNum();
							//System.out.println("Total Number of Cols: " + colCounts);

							for (int columnSheet = 0; columnSheet < colCounts; columnSheet++) {

								Cell cell = row.getCell(columnSheet);
								if (cell != null && (cell.getCellTypeEnum() == CellType.STRING || cell.getCellType() == Cell.CELL_TYPE_FORMULA)) {
									String cellValue = "";
									if(cell.getCellTypeEnum() == CellType.STRING ) {
										cellValue = cell.getStringCellValue();
									}else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
										cellValue = evaluator.evaluate(cell).formatAsString().substring(1, evaluator.evaluate(cell).formatAsString().length() - 1);
									}
									
									if (rowSheet == 0) {
										if (category != null) {
											category.setLastRow(columnSheet);
											category = new Category();
										} else {
											category = new Category();
										}

										category.setFirstRow(columnSheet);
										category.setName(cellValue);
										category.setLastRow(colCounts + 1);
										category.setData(new ArrayList<StgLossDataUIField>());
										category.setStatus(status);
										categories.add(category);

									} else if (rowSheet == 1 && columnSheet > 1) {
										for (Category cat : categories) {
											Integer lastRow = cat.getLastRow() - 1;
											Integer firstRow = cat.getFirstRow();
											if (firstRow <= columnSheet && lastRow > columnSheet) {
												StgLossDataUIField stgLossDataUIField = new StgLossDataUIField();
												stgLossDataUIField.setRiskType(cellValue);
												stgLossDataUIField.setColumn(columnSheet);
												stgLossDataUIField.setFieldNameValue(new ArrayList<FieldNameValue>());
												cat.getData().add(stgLossDataUIField);
												break;
											}
										}
									} else if (cell != null && columnSheet >= 2 && cellValue != null) {
										for (Category cat : categories) {
											Integer lastRow = cat.getLastRow() - 1;
											Integer firstRow = cat.getFirstRow();
											if (firstRow <= columnSheet && lastRow > columnSheet) {
												for (StgLossDataUIField stgLossDataUIField : cat.getData()) {
													if (stgLossDataUIField.getColumn() == columnSheet) {
														FieldNameValue fieldNameValue = new FieldNameValue();
														fieldNameValue.setUiDisplayCode(cellValue);
														fieldNameValue.setUiSection(uiSection);
														fieldNameValue.setUiFieldName(uiFieldName);
														stgLossDataUIField.getFieldNameValue().add(fieldNameValue);
														break;
													}
												}

												break;
											}
										}
									} else if (cell != null && rowSheet >= 2 && columnSheet == 0 && cell.getCellType() != Cell.CELL_TYPE_FORMULA ) {
										if (!cellValue.equals("")) {
											uiSection = cellValue;
										}

									} else if (cell != null && rowSheet >= 2 && columnSheet == 1) {
										if (!cellValue.equals("")) {
											uiFieldName = cellValue;
										}

									}

								/*	System.out.println(
											"[" + rowSheet + "," + columnSheet + "]=" + cellValue);*/
								} else if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC) {

								}else if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
									
								/*
									System.out.println("eval "+evaluator.evaluate(cell).formatAsString());
									System.out.println(
											"[" + rowSheet + "," + columnSheet + "]=" + cell.getCellFormula());
					*/
								} else {

								}

							}

						}

					}
					if (categories.size() > 0) {
						items.add(categories);
					}
					/* for(Category cat : categories) { 
						 for(StgLossDataUIField stg : cat.getData())
					  {
						 for(FieldNameValue field : stg.getFieldNameValue()) {
					 System.out.print("Category : "+cat.getName());
					  System.out.print(", RiskType : "+stg.getRiskType() );
					  System.out.print(", UiSection :"+field.getUiSection());
					  System.out.print(", UiFieldName :"+field.getUiFieldName());
					  System.out.println(", UI COde :"+field.getUiDisplayCode());
					  
					  
					  } } ///System.out.println("Count Data :" +cat.getData().size());
					  //System.out.println("Count Field :" +cat.getFieldNameValue().size());
					  */
					  
					  //}

				}

				
				
				 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return items;
	}
}
