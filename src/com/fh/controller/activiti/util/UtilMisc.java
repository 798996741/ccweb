package com.fh.controller.activiti.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明：UtilMisc
 * 作者：FH Admin fh3135967 90qq(青苔)
 * 官网：www.fhadmin.org
 */
public class UtilMisc {
	
	public static <V, V1 extends V, V2 extends V> Map<String, String> toMap(String name1, V1 value1, String name2, V2 value2) {
		return (Map<String, String>) populateMap(new HashMap<String, V>(), name1, value1, name2, value2);
	}

	@SuppressWarnings("unchecked")
	private static <K, V> Map<String, V> populateMap(Map<String, V> map, Object... data) {
		for (int i = 0; i < data.length;) {
			map.put((String) data[i++], (V) data[i++]);
		}
		return map;
	}
	
}