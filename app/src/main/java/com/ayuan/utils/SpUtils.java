package com.ayuan.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {


	private static SharedPreferences sharedPreferences;

	public static void putBoolean(Context context, String key, boolean defValue) {
		if (sharedPreferences != null) {
			sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
	}
}
