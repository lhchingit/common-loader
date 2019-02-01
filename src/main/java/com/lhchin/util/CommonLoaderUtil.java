package com.lhchin.util;

import java.util.Map;

/**
 * 
 * @author LHCHIN
 *
 */
public class CommonLoaderUtil {
    public String generateInsertionSqlStmt(String tableName, Map<String, Object> paramMap) {
    	StringBuffer columnNames = new StringBuffer();
    	StringBuffer namedParams = new StringBuffer();
    	for (String key : paramMap.keySet()) {
    		columnNames.append(key).append(",");
    		namedParams.append(":").append(key).append(",");
    	}
    	columnNames.setLength(columnNames.length()-1);
    	namedParams.setLength(namedParams.length()-1);

    	return String.format("INSERT INTO %s (%s) VALUES (%s)",
    			tableName, columnNames.toString(), namedParams.toString());
    }
}
