package com.ayuan.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

	private static SharedPreferences sharedPreferences;
	private static String IP = "ip";

	public static void putBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences != null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putBoolean(key, defValue).commit();
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences != null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static void putString(Context context, String key, String defValue) {
		if (sharedPreferences != null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putString(key, defValue).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		if (sharedPreferences != null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
}
