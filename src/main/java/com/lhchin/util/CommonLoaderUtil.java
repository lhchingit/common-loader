package com.lhchin.util;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;

/**
 * 
 * @author LHCHIN
 *
 */
public class CommonLoaderUtil {
    public static String generateInsertionSqlStmt(String tableName, Map<String, Object> paramMap) {
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

    public static String generateUpdateSqlStmt(String tableName, Map<String, Object> paramMap, String[] primaryKeys) {
    	List<Object> primaryKeyList = Arrays.asList(primaryKeys);

    	StringBuffer columnNames = new StringBuffer();
    	StringBuffer namedParams = new StringBuffer();
    	for (String key : paramMap.keySet()) {
    		columnNames.append(key).append(" = :").append(key).append(",");

    		if (primaryKeyList.contains(key)) {
    			namedParams.append("AND ").append(key).append(" = :").append(key).append(",");
    		}
    	}
    	columnNames.setLength(columnNames.length()-1);
    	namedParams.setLength(namedParams.length()-1);

    	return String.format("UPDATE %s SET %s WHERE 1 = 1 %s",
    			tableName, columnNames.toString(), namedParams.toString());
    }
}
