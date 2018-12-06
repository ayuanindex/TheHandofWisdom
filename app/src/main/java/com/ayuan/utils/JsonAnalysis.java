package com.ayuan.utils;

import android.text.TextUtils;

import com.ayuan.vo.AllSensors_vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonAnalysis {
	private static String TAG = "JsonAnalysis";

	/**
	 * 解析所有传感器的状态
	 *
	 * @param json 需要解析的json字符串
	 * @return
	 */
	public static AllSensors_vo GetAllSense(String json) {
		try {
			/*String json = StreamUtils.StreamToString(inputStream);*/
			if (!TextUtils.isEmpty(json)) {
				JSONObject jsonObject = getJsonObject(json);
				int pm2_5 = jsonObject.getInt("pm2.5");
				int co2 = jsonObject.getInt("co2");
				int lightIntensity = jsonObject.getInt("LightIntensity");
				int humidity = jsonObject.getInt("humidity");
				int temperature = jsonObject.getInt("temperature");
				AllSensors_vo allSensors_vo = new AllSensors_vo(pm2_5, co2, lightIntensity, humidity, temperature);
				return allSensors_vo;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析光照强度
	 *
	 * @param json 需要解析的json字符串
	 * @return
	 */
	public static HashMap<String, Integer> GetLightSenseValve(String json) {
		try {
			JSONObject jsonObject = getJsonObject(json);
			int down = jsonObject.getInt("Down");
			int up = jsonObject.getInt("Up");
			HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
			stringIntegerHashMap.put("Down", down);
			stringIntegerHashMap.put("Up", up);
			return stringIntegerHashMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析小车速度
	 *
	 * @param json
	 */
	public static Integer GetCarSpeed(String json) {
		try {
			JSONObject jsonObject = getJsonObject(json);
			int carSpeed = jsonObject.getInt("CarSpeed");
			return carSpeed;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 解析小车动作和解析充值成功与否
	 *
	 * @param json
	 * @return 设置(充值)成功返回"ok", 设置(充值)失败返回"fail"
	 */
	public static String GetResult(String json) {
		try {
			JSONObject jsonObject = getJsonObject(json);
			String result = jsonObject.getString("result");
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	/**
	 * 解析账户余额
	 *
	 * @param json
	 */
	public static double GetCarAccountBalance(String json) {
		try {
			JSONObject jsonObject = getJsonObject(json);
			double balance = jsonObject.getDouble("Balance");
			return balance;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 解析道路状态
	 *
	 * @param json
	 * @return
	 */
	public static String GetRoadStatus(String json) {
		try {
			JSONObject jsonObject = getJsonObject(json);
			int status = jsonObject.getInt("Status");
			switch (status) {
				case 1:
					return "拥挤状态1";
				case 2:
					return "拥挤状态2";
				case 3:
					return "拥挤状态3";
				case 4:
					return "拥挤状态4";
				case 5:
					return "拥挤状态5";
				default:
					return "查询失败";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "查询失败";
	}

	/**
	 * 查询费率
	 *
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> GetParkRate(String json) {
		HashMap<String, Object> stringObjectHashMap = new HashMap<>();
		try {
			JSONObject jsonObject = getJsonObject(json);
			String rateType = jsonObject.getString("RateType");
			double money = jsonObject.getDouble("Money");
			stringObjectHashMap.put("RateType", rateType);
			stringObjectHashMap.put("Money", money);
			return stringObjectHashMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析当前车位
	 *
	 * @param json
	 */
	public static ArrayList<Integer> GetParkFree(String json) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		try {
			JSONObject object = new JSONObject(json);
			String serverinfo = object.getString("serverinfo");
			JSONArray jsonArray = new JSONArray(serverinfo);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int parkFreeId = jsonObject.getInt("ParkFreeId");
				integers.add(parkFreeId);
			}
			return integers;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return integers;
	}

	//------------------------------------------------------------------
	private static JSONObject getJsonObject(String json) throws JSONException {
		JSONObject object = new JSONObject(json);
		String serverinfo = object.getString("serverinfo");
		JSONObject jsonObject = new JSONObject(serverinfo);
		return jsonObject;
	}


}
