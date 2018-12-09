package com.ayuan.utils;

import com.ayuan.vo.TrafficLight_vo;
/*import com.marliao.intelligenttransportation.db.dao.GetTrafficLightConfigAction;*/

import java.util.ArrayList;
import java.util.List;

public class Sequence {
	/**
	 * 路口降序
	 *//*
	public static void roadDescending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getRoadId() < mGetTrafficLightConfigActionList.get(j + 1).getRoadId()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}

	*//**
	 * 黄灯降序
	 *//*
	public static void yellowLightDescending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getYellowTime() < mGetTrafficLightConfigActionList.get(j + 1).getYellowTime()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}

	*//**
	 * 绿灯降序
	 *//*
	public static void greenLightDescending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getGreenTime() < mGetTrafficLightConfigActionList.get(j + 1).getGreenTime()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}

	*//**
	 * 红灯降序
	 *//*
	public static void redLightDescending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getRedTime() < mGetTrafficLightConfigActionList.get(j + 1).getRedTime()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}
*/
	/**
	 * 红灯升序
	 *
	 * @param trafficLight_vos
	 */
	public static void redLightAscending(ArrayList<TrafficLight_vo> trafficLight_vos) {
		List<TrafficLight_vo> trafficLight_voList = new ArrayList<TrafficLight_vo>();
		for (int i = 0; i < trafficLight_vos.size() - 1; i++) {
			for (int j = 0; j < trafficLight_vos.size() - 1 - i; j++) {
				if (trafficLight_vos.get(j).getRed() > trafficLight_vos.get(j).getRed()) {
					TrafficLight_vo trafficLight_vo = new TrafficLight_vo();
					trafficLight_vo = trafficLight_vos.get(j);
					trafficLight_vos.set(j, trafficLight_vos.get(j + 1));
					trafficLight_vos.set(j + 1, trafficLight_vo);
				}
			}
		}
	}

	/**
	 * 绿灯升序
	 *
	 * @param mGetTrafficLightConfigActionList
	 *//*
	public static void greenLightAscending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		List<GetTrafficLightConfigAction> getTrafficLightConfigActionList = new ArrayList<>();
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getGreenTime() > mGetTrafficLightConfigActionList.get(j).getGreenTime()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}

	*//**
	 * 黄灯升序
	 *
	 * @param mGetTrafficLightConfigActionList
	 *//*
	public static void yellowLightAscending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		List<GetTrafficLightConfigAction> getTrafficLightConfigActionList = new ArrayList<>();
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getYellowTime() > mGetTrafficLightConfigActionList.get(j).getYellowTime()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}

	*//**
	 * 路口升序
	 *
	 * @param mGetTrafficLightConfigActionList
	 *//*
	public static void roadLightAscending(List<GetTrafficLightConfigAction> mGetTrafficLightConfigActionList) {
		List<GetTrafficLightConfigAction> getTrafficLightConfigActionList = new ArrayList<>();
		for (int i = 0; i < mGetTrafficLightConfigActionList.size() - 1; i++) {
			for (int j = 0; j < mGetTrafficLightConfigActionList.size() - 1 - i; j++) {
				if (mGetTrafficLightConfigActionList.get(j).getRoadId() > mGetTrafficLightConfigActionList.get(j).getRoadId()) {
					GetTrafficLightConfigAction getTrafficLightConfigAction = new GetTrafficLightConfigAction();
					getTrafficLightConfigAction = mGetTrafficLightConfigActionList.get(j);
					mGetTrafficLightConfigActionList.set(j, mGetTrafficLightConfigActionList.get(j + 1));
					mGetTrafficLightConfigActionList.set(j + 1, getTrafficLightConfigAction);
				}
			}
		}
	}*/
}
