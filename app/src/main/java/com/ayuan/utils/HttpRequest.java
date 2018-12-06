package com.ayuan.utils;

import com.ayuan.vo.AllSensors_vo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class HttpRequest {

	private static String TAG = "HttpRequest";
	/**
	 * IP
	 */
	private static String IP = "192.168.1.101";
	/**
	 * 服务器端口
	 */
	private static String PORT = "http://192.168.1.101:8080/transportservice/type/jason/action/";
	/**
	 * 获取所有传感器
	 */
	private static String GET_ALL_SENSE = PORT + "GetAllSense.do";
	/**
	 * 获取光照传感器
	 */
	private static String GET_LIGHT_SENSE_VALUE = PORT + "GetLightSenseValve.do";
	/**
	 * 获取小车当前的速度
	 */
	private static String GET_CAR_SPEED = PORT + "GetCarSpeed.do";
	/**
	 * 设置小车动作
	 */
	private static String SET_CAR_MOVE = PORT + "SetCarMove.do";
	/**
	 * 查询小车账户余额
	 */
	private static String GET_CAR_ACCOUNT_BALANCE = PORT + "GetCarAccountBalance.do";
	/**
	 * 小车账户充值
	 */
	private static String SET_CAR_ACCOUNT_RECHARGE = PORT + "SetCarAccountRecharge.do";
	/**
	 * 道路状态查询
	 */
	private static String GET_ROAD_STATUS = PORT + "GetRoadStatus.do";
	/**
	 * 费率设置
	 */
	private static String SET_PARK_RATE = PORT + "SetParkRate.do";
	/**
	 * 查询当前费率
	 */
	private static String GET_PARK_RATE = PORT + "GetParkRate.do";
	/**
	 * 查询当前空闲车位
	 */
	private static String GET_PARK_FREE = PORT + "GetParkFree.do";

	/**
	 * 请求小车所有传感器对的值
	 */
	public static AllSensors_vo httpGetAllSense() {
		try {
			return JsonAnalysis.GetAllSense(httpSetting(GET_ALL_SENSE, null));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取光照传感器的值
	 *
	 * @return
	 */
	public static HashMap<String, Integer> httpGetLightSenseValve() {
		try {
			return JsonAnalysis.GetLightSenseValve(httpSetting(GET_LIGHT_SENSE_VALUE, null));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取小车的速度
	 *
	 * @param CarId 需要获取的小车编号  (1<=CarId<=15)
	 * @return
	 */
	public static Integer httpGetCarSpeed(int CarId) {
		try {
			JSONObject object = new JSONObject();
			object.put("CarId", CarId);
			if (object != null) {
				return JsonAnalysis.GetCarSpeed(httpSetting(GET_CAR_SPEED, object.toString()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return 404;
		}
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
			if (object != null) {
				return JsonAnalysis.GetResult(httpSetting(SET_CAR_MOVE, object.toString()));
			}
		} catch (JSONException e) {

			e.printStackTrace();
			return "解析错误";
		} catch (IOException e) {
			e.printStackTrace();
			return "json转换出错";
		} finally {
			return null;
		}
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
		} catch (IOException e) {
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
		} catch (IOException e) {
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
		} catch (IOException e) {
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
		} catch (IOException e) {
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
		try {
			return JsonAnalysis.GetParkRate(httpSetting(GET_PARK_RATE, null));
		} catch (IOException e) {
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
	public static String httpSetting(String path, String jsonString) throws IOException {
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setReadTimeout(3000);
		httpURLConnection.setConnectTimeout(3000);
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
			String json = StreamUtils.StreamToString(inputStream);
			return json;
		}
		return null;
	}
}
