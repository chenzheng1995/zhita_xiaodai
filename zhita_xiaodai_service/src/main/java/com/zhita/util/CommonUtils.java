package com.zhita.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class CommonUtils {
	
	/**
	 * 把request转为map
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map<?, ?> properties = request.getParameterMap();
		// 返回值Map 
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<?> entries = properties.entrySet().iterator();
		
		Map.Entry<String, String> entry;
		String name = "";
		String value = "";
		Object valueObj =null;
		while (entries.hasNext()) {
			entry = (Map.Entry<String, String>) entries.next();
			name = (String) entry.getKey();
			valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		} 
		return returnMap;
	}

}
