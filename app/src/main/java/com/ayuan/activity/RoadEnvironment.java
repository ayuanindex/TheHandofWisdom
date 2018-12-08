package com.ayuan.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.vo.AllSensors_vo;

import java.util.Timer;
import java.util.TimerTask;

public class RoadEnvironment extends AppCompatActivity implements View.OnClickListener {

	private static final String TAG = "RoadEnvironment";
	private VideoView vv_video;
	private Button btn_query;
	private Button btn_illumination;
	private TextView tv_result;
	private TextView tv_result2;
	private TextView tv_des_pm2_5;
	private TextView tv_des_co2;
	private TextView illumination;
	private Timer timer;
	private TextView tv_des_temperature;
	private TextView tv_des_humidity;
	private TextView tv_back;
	private TimerTask timerTask;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_road_environment);

		initUI();
		initData();
	}


	private void initUI() {
		vv_video = (VideoView) findViewById(R.id.vv_video);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_illumination = (Button) findViewById(R.id.btn_illumination);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_result = (TextView) findViewById(R.id.tv_result);
		tv_result2 = (TextView) findViewById(R.id.tv_result2);
		tv_des_pm2_5 = (TextView) findViewById(R.id.tv_des_pm2_5);
		tv_des_co2 = (TextView) findViewById(R.id.tv_des_co2);
		tv_des_temperature = (TextView) findViewById(R.id.tv_des_temperature);
		tv_des_humidity = (TextView) findViewById(R.id.tv_des_humidity);
		illumination = (TextView) findViewById(R.id.illumination);

		btn_illumination.setOnClickListener(this);
		btn_query.setOnClickListener(this);
		tv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_illumination:
				//光照
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				if (timerTask != null) {
					timerTask = null;
					timerTask = null;
				}
				initData();
				break;
			case R.id.btn_query:
				//空气
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				if (timerTask != null) {
					timerTask = null;
					timerTask = null;
				}
				initData();
				break;
			case R.id.tv_back:
				finish();
				break;
		}
	}


	private void initIllumination() {
		try {
			final AllSensors_vo allSensors_vo = HttpRequest.httpGetAllSense();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (allSensors_vo != null) {
						Integer co2 = allSensors_vo.getCo2();
						Integer humidity = allSensors_vo.getHumidity();
						Integer temperature = allSensors_vo.getTemperature();
						Integer pm2_5 = allSensors_vo.getPm2_5();
						Integer lightIntensity = allSensors_vo.getLightIntensity();
						tv_result.setText("PM2.5 : " + allSensors_vo.getPm2_5() + " μg/m3, 温度 : " + allSensors_vo.getTemperature() + " ℃, 湿度 : " + allSensors_vo.getHumidity() + " %, CO2: " + allSensors_vo.getCo2());
						tv_result2.setText("光照强度: " + allSensors_vo.getLightIntensity() + "lux");
						if (pm2_5 >= 200) {
							tv_des_pm2_5.setVisibility(View.VISIBLE);
							vv_video.setVisibility(View.VISIBLE);
							vv_video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/alarming"));
							vv_video.start();
						} else {
							if (vv_video.isPlaying()) {
								vv_video.pause();
							}
							vv_video.setVisibility(View.INVISIBLE);
							tv_des_pm2_5.setVisibility(View.GONE);
						}
						if (temperature >= 40) {
							tv_des_temperature.setVisibility(View.VISIBLE);
							tv_des_temperature.setText("温度过高,注意防晒");
						} else if (temperature <= 10) {
							tv_des_temperature.setVisibility(View.VISIBLE);
							tv_des_temperature.setText("温度过低，注意保暖");
						} else {
							tv_des_temperature.setVisibility(View.GONE);
							tv_des_temperature.setText("");
						}
						if (humidity >= 50) {
							tv_des_humidity.setVisibility(View.VISIBLE);
							tv_des_humidity.setText("湿度过大");
						} else if (humidity <= 0) {
							tv_des_humidity.setVisibility(View.VISIBLE);
							tv_des_humidity.setText("天气太干燥了");
						} else {
							tv_des_humidity.setText("");
							tv_des_humidity.setVisibility(View.GONE);
						}
						if (lightIntensity > 2500) {
							illumination.setVisibility(View.VISIBLE);
							illumination.setText("太刺眼了！");
						} else if (lightIntensity < 1000) {
							illumination.setVisibility(View.VISIBLE);
							illumination.setText("当前环境较暗，请开启照明！");
						} else {
							illumination.setVisibility(View.GONE);
							illumination.setText("");
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "捕捉到了异常");
			initData();
		}
	}

	private void initData() {
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				initIllumination();
			}
		};
		timer.schedule(timerTask, 500, 10000);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (timerTask != null) {
			timerTask = null;
			timerTask = null;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		initData();
	}
}
