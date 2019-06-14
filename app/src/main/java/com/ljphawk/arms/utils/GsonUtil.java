package com.ljphawk.arms.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *@创建者       L_jp
 *@创建时间     2017/3/8 15:17.
 *@描述         gson的二次封装
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */
public class GsonUtil {

	/**
	 * 把一个map变成json字符串
	 * @param map
	 * @return
	 */
	public static String parseMapToJson(Map<?, ?> map) {
		try {
			Gson gson = new Gson();
			return gson.toJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String parseBeanToJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

	/**
	 * 把一个json字符串变成对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return t;
	}

	//解析json数组
	public static <T> List<T> fromJsonArray(String json, Class<T> clazz)  {
		if (json == null) {
			return null;
		}
		List<T> lst = null;
		try {
			lst = new ArrayList<T>();

			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for(final JsonElement elem : array){
                lst.add(new Gson().fromJson(elem, clazz));
            }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return lst;
	}


	/**
	 * 把json字符串变成map
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> parseJsonToMap(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();
		HashMap<String, Object> map = null;
		try {
			map = gson.fromJson(json, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

}
