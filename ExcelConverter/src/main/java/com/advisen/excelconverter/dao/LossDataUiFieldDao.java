package com.advisen.excelconverter.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import com.advisen.excelconverter.Category;
import com.advisen.excelconverter.FieldNameValue;
import com.advisen.excelconverter.StgLossDataUIField;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.CLOB;

public class LossDataUiFieldDao {
	private JdbcTemplate jdbcTemplate = null;
	
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public void insertLossData(List<List<Category>> items) {
		OracleCallableStatement cs = null;
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer str = new StringBuffer();
      		str.append("INSERT INTO STG_LOSS_DATA_UI_FIELDS(CATEGORY, RISK_TYPE, CASE_STATUS , UI_FIELD_NAME , UI_SECTION, UI_DISPLAY_CODE) VALUES ( ? , ? , ?, ?, ?, ?)");      	
			cs = (OracleCallableStatement) conn.prepareCall(str.toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long millis = System.currentTimeMillis();
		for(List<Category> categories : items) {
			  for(Category cat : categories) {            
	          	for(StgLossDataUIField stg : cat.getData()) {
	          		for(FieldNameValue field : stg.getFieldNameValue()) {
	              		
	              			//jdbcTemplate.update( str.toString(),cat.getName() ,  stg.getRiskType(), cat.getStatus(), field.getUiFieldName(), field.getUiSection(), field.getUiDisplayCode()  );
	              		try {
							
							cs.setString(1, cat.getName());
		              		cs.setString(2, stg.getRiskType());
		              		cs.setString(3, cat.getStatus());
		              		cs.setString(4,  field.getUiFieldName());
		              		cs.setString(5, field.getUiSection());
		              		cs.setString(6, field.getUiDisplayCode());
		        			cs.addBatch();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	              		
	              		
	              	}
	          	
	          	}
	        	
	          	///System.out.println("Count Data :" +cat.getData().size());
	          	//System.out.println("Count Field :" +cat.getFieldNameValue().size());
	          	
	          	
	          }
			  try {
        		//	System.out.print("Category : "+cat.getName());              		
            	//	System.out.println(", Status : "+cat.getStatus() );	              		
  				int[] count = cs.executeBatch();
  				
  				//System.out.println("result count : "+count.length);
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
			
			//jdbcTemplate.execute("INSERT INTO USER_SESSIONS(SESSION_ID, SESSION_HOST, SESSION_NUMBER , USER_ID , LOGIN_TIME) VALUES ('" + sessionId + "', 'webservice', '1', '" + userinfo.getUserId() + "', sysdate) ");
			
		}
		
		System.out.println("Insert Data Complete :" +  ( System.currentTimeMillis() - millis)/1000);
	}
	
}
