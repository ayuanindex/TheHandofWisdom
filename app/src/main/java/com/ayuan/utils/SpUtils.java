package com.ayuan.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

	public static final String SPANNER1 = "spinner1";
	public static final String SPANNER2 = "spinner2";
	public static final String SORT = "sort";
	private static SharedPreferences sharedPreferences;
	private static String IP = "ip";
	public static String CAR1 = "car1";
	public static String CAR2 = "car2";
	public static String CAR3 = "car3";
	public static String CAR4 = "car4";

	public static void putBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putBoolean(key, defValue).commit();
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static void putString(Context context, String key, String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putString(key, defValue).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
}
