package com.ayuan.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.vo.AllSensors_vo;
import com.ayuan.vo.BusStation_vo;

import java.util.ArrayList;
import java.util.Timer;

public class TwoFragment extends Fragment {
	private static final String TAG = "TwoFragment";
	private TextView bus1;
	private TextView bus2;
	private TextView environment;
	private Timer timer;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					BusStation_vo busStation_vo = busStation_vos.get(0);
					BusStation_vo busStation_vo1 = busStation_vos.get(1);
					bus1.setText(busStation_vo.getBusId() + "号公交:" + busStation_vo.getDistance() + "m");
					bus2.setText(busStation_vo1.getBusId() + "号公交" + busStation_vo1.getDistance() + "m");
					break;
				case 2:
					environment.setText("PM2.5 : " + allSensors_vo.getPm2_5() + " μg/m3, 温度 : " + allSensors_vo.getTemperature() + " ℃, 湿度 : " + allSensors_vo.getHumidity() + " %, CO2: " + allSensors_vo.getCo2());
					break;
			}
		}
	};
	private ArrayList<BusStation_vo> busStation_vos;
	private AllSensors_vo allSensors_vo;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_two, null);
		intUI(inflate);
		initData();
		return inflate;
	}


	private void intUI(View inflate) {
		bus1 = (TextView) inflate.findViewById(R.id.bus1);
		bus2 = (TextView) inflate.findViewById(R.id.bus2);
		environment = (TextView) inflate.findViewById(R.id.environment);
	}

	private void initData() {
		try {
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						busStation_vos = HttpRequest.httpGetBusStationInfo(1);
						Thread.sleep(200);
						if (busStation_vos != null) {
							Message message = Message.obtain();
							message.what = 1;
							mHandler.sendMessage(message);
						}
						allSensors_vo = HttpRequest.httpGetAllSense();
						if (allSensors_vo != null) {
							Message message = Message.obtain();
							message.what = 2;
							mHandler.sendMessage(message);
						}
					} catch (InterruptedException e) {
						initData();
						e.printStackTrace();
					} catch (Exception e) {
						initData();
						Log.i(TAG, "捕捉到了异常");//次
					}

				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "扑捉到了异常");
		}
	}
}
