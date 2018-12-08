package com.ayuan.utils;

import android.util.Log;

import com.ayuan.vo.AllSensors_vo;
import com.ayuan.vo.BusStation_vo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpRequest {

	public static String setIp() {
		File file = new File("/data/data/com.ayuan.thehandofwisdom/files", "ip.txt");
		if (!file.exists()) {
			Log.i(TAG, "哈哈:没有修改成功");
			return "192.168.1.101";
		}
		FileInputStream fileInputStream = null;
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			fileInputStream = new FileInputStream(file);
			int b;
			while ((b = fileInputStream.read()) != -1) {
				stringBuilder.append((char) b);
			}
			IP = stringBuilder.toString();
			Log.i(TAG, "我的ip" + IP);
			return IP;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "192.168.1.101";
	}

	private static String TAG = "HttpRequest";
	/**
	 * IP
	 */
	private static String IP;
	/**
	 * 服务器端口
	 */
	private static String PORT = "http://";
	/**
	 * 获取所有传感器
	 */
	private static String GET_ALL_SENSE = PORT + setIp() + ":8080/transportservice/type/jason/action/GetAllSense.do";
	/**
	 * 获取光照传感器
	 */
	private static String GET_LIGHT_SENSE_VALUE = PORT + setIp() + ":8080/transportservice/type/jason/action/GetLightSenseValve.do";
	/**
	 * 获取小车当前的速度
	 */
	private static String GET_CAR_SPEED = PORT + setIp() + ":8080/transportservice/type/jason/action/GetCarSpeed.do";
	/**
	 * 设置小车动作
	 */
	private static String SET_CAR_MOVE = PORT + setIp() + ":8080/transportservice/type/jason/action/SetCarMove.do";
	/**
	 * 查询小车账户余额
	 */
	private static String GET_CAR_ACCOUNT_BALANCE = PORT + setIp() + ":8080/transportservice/type/jason/action/GetCarAccountBalance.do";
	/**
	 * 小车账户充值
	 */
	private static String SET_CAR_ACCOUNT_RECHARGE = PORT + setIp() + ":8080/transportservice/type/jason/action/SetCarAccountRecharge.do";
	/**
	 * 道路状态查询
	 */
	private static String GET_ROAD_STATUS = PORT + setIp() + ":8080/transportservice/type/jason/action/GetRoadStatus.do";
	/**
	 * 费率设置
	 */
	private static String SET_PARK_RATE = PORT + setIp() + ":8080/transportservice/type/jason/action/SetParkRate.do";
	/**
	 * 查询当前费率
	 */
	private static String GET_PARK_RATE = PORT + setIp() + ":8080/transportservice/type/jason/action/GetParkRate.do";
	/**
	 * 查询当前空闲车位
	 */
	private static String GET_PARK_FREE = PORT + setIp() + ":8080/transportservice/type/jason/action/GetParkFree.do";
	/**
	 * 查询距离公交站台的距离
	 */
	private static String GET_BUS_STATION_INFO = PORT + setIp() + ":8080/transportservice/type/jason/action/GetBusStationInfo.do";
	/**
	 * 查询路口红绿灯状态
	 */
	private static String GET_TRAFFIC_LIGHT_CONFIG_ACTION = PORT + setIp() + ":8080/transportservice/type/jason/action/GetTrafficLightConfigAction.do";


	/**
	 * 请求小车所有传感器对的值
	 */
	public static AllSensors_vo httpGetAllSense() {
		return JsonAnalysis.GetAllSense(httpSetting(GET_ALL_SENSE, null));
	}

	/**
	 * 获取光照传感器的值
	 *
	 * @return
	 */
	public static HashMap<String, Integer> httpGetLightSenseValve() {
		HashMap<String, Integer> stringIntegerHashMap = JsonAnalysis.GetLightSenseValve(httpSetting(GET_LIGHT_SENSE_VALUE, null));
		return stringIntegerHashMap != null ? stringIntegerHashMap : null;
	}

	/**
	 * 获取小车的速度
	 *
	 * @param CarId 需要获取的小车编号  (1<=CarId<=15)
	 * @return
	 */
	public static int httpGetCarSpeed(int CarId) {
		int carSpeed = -1;
		try {
			JSONObject object = new JSONObject();
			object.put("CarId", CarId);
			if (object.toString() != null) {
				carSpeed = JsonAnalysis.GetCarSpeed(httpSetting(GET_CAR_SPEED, object.toString()));
				return carSpeed == -1 ? -1 : carSpeed;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	/**
	 * 设置小车的动作(start,stop)
	 *
	 * @param CarId     小车的编号 (1<=CarId<=15)
	 * @param CarAction 小车的动作(start,stop)
	 * @return
	 */
	public static String httpSetCarMove(int CarId, String CarAction) {
		try {
			JSONObject object = new JSONObject();
			object.put("CarId", CarId);
			object.put("CarAction", CarAction);
			if (object.toString() != null) {
				String s = JsonAnalysis.GetResult(httpSetting(SET_CAR_MOVE, object.toString()));
				return s.equals("ok") ? "ok" : "failed";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return "failed";
		} catch (Exception e) {
			return "failed";
		}
		return "failed";
	}

	/**
	 * 查询小车专户余额
	 *
	 * @param CarId 需要查询对的小车的ID
	 * @return 返回余额(- 1表示出现错误)
	 */
	public static double httpGetCarAccountBalance(int CarId) {
		try {
			JSONObject object = new JSONObject();
			object.put("CarId", CarId);
			return JsonAnalysis.GetCarAccountBalance(httpSetting(GET_CAR_ACCOUNT_BALANCE, object.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String httpSetCarAccountRecharge(int CarId, double money) {
		try {
			JSONObject object = new JSONObject();
			object.put("CarId", CarId);
			object.put("Money", money);
			return JsonAnalysis.GetResult(httpSetting(SET_CAR_ACCOUNT_RECHARGE, object.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	/**
	 * 获取道路状态
	 *
	 * @param RoadId 道路编号(1<=RoadId<=12)
	 * @return
	 */
	public static String httpGetRoadStatus(int RoadId) {
		try {
			JSONObject object = new JSONObject();
			object.put("RoadId", RoadId);
			return JsonAnalysis.GetRoadStatus(httpSetting(GET_ROAD_STATUS, object.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "查询失败";
	}

	/**
	 * 设置当前
	 *
	 * @param RateType 设置费率类型(Count:按次计费;Hour:按小时计费)
	 * @param Money    设置金额
	 * @return ok代表设置成功, fail表示设置失败
	 */
	public static String httpSetParkRate(String RateType, double Money) {
		try {
			JSONObject object = new JSONObject();
			object.put("RateType", RateType);
			object.put("Money", Money);
			return JsonAnalysis.GetResult(httpSetting(SET_PARK_RATE, object.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	/**
	 * 查询当前费率
	 *
	 * @return 返回一个map集合key:RateType表示费率类型 Money:表示金额
	 */
	public static HashMap<String, Object> httpGetParkRate() {
		return JsonAnalysis.GetParkRate(httpSetting(GET_PARK_RATE, null));
	}

	/**
	 * 查询当前空闲车位
	 *
	 * @return 返回一个List集合，里面放的是空闲车位的id编号
	 */
	public static ArrayList<Integer> httpGetParkFree() {
		ArrayList<Integer> integers = JsonAnalysis.GetParkFree(httpSetting(GET_PARK_FREE, null));
		if (!integers.isEmpty()) {
			return integers;
		}
		return null;
	}

	/**
	 * 查询距离公交站台距离
	 *
	 * @return
	 */
	public static ArrayList<BusStation_vo> httpGetBusStationInfo(int BusStationId) {
		try {
			JSONObject object = new JSONObject();
			object.put("BusStationId", BusStationId);
			if (object.toString() != null) {
				httpSetting(GET_BUS_STATION_INFO, object.toString());
				ArrayList<BusStation_vo> busStation_vos = JsonAnalysis.GetBusStationInfo(httpSetting(GET_BUS_STATION_INFO, object.toString()));
				if (busStation_vos != null && busStation_vos.size() == 2) {
					return busStation_vos;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 查询路口红绿灯状态
	 */
	public static ArrayList<Integer> httpGetTrafficLightConfigAction(int TrafficLightId) {
		try {
			JSONObject object = new JSONObject();
			object.put("TrafficLightId", TrafficLightId);
			ArrayList<Integer> integers = JsonAnalysis.GetTrafficLightConfigAction(httpSetting(GET_TRAFFIC_LIGHT_CONFIG_ACTION, object.toString()));
			if (integers != null) {
				return integers;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	//----------------------------------------------------------------------------------------------

	/**
	 * 获取连接的方法
	 *
	 * @param path       地址
	 * @param jsonString 请求的参数
	 * @return 返回一个连接
	 * @throws IOException 抛出异常
	 */
	public static String httpSetting(String path, String jsonString) {
		try {
			Log.i(TAG, "地址:" + path);
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			httpURLConnection.setRequestProperty("accept", "application/json");
			if (jsonString != null) {
				byte[] bytes = jsonString.getBytes();
				//设置文件长度
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(bytes);
				outputStream.flush();
				outputStream.close();
			}
			int code = httpURLConnection.getResponseCode();
			if (code == 200) {
				InputStream inputStream = httpURLConnection.getInputStream();
				if (inputStream != null) {
					String json = StreamUtils.StreamToString(inputStream);
					return json;
				} else {
					inputStream.close();
				}
			} else {
				return null;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
