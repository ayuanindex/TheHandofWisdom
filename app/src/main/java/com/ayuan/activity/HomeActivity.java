package com.ayuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.vo.AllSensors_vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

	private ImageView iv_menu;
	private TextView tv_pm2_5;
	/**
	 * 温度
	 */
	private TextView tv_temperature;
	/**
	 * 湿度
	 */
	private TextView tv_humidity;
	private TextView tv_co2;
	private Timer timer;
	private TextView tv_bus1_one;
	private TextView tv_bus2_one;
	private TextView tv_bus1_two;
	private TextView tv_bus2_two;
	/*private ProgressDialog progressDialog;*/

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
		initData();
	}


	private void initUI() {
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		tv_pm2_5 = (TextView) findViewById(R.id.tv_pm2_5);/*pm2.5*/
		tv_temperature = (TextView) findViewById(R.id.tv_temperature);/*温度*/
		tv_humidity = (TextView) findViewById(R.id.tv_humidity);/*湿度*/
		tv_co2 = (TextView) findViewById(R.id.tv_co2);
		//一号站台
		tv_bus1_one = (TextView) findViewById(R.id.tv_bus1_one);
		tv_bus2_one = (TextView) findViewById(R.id.tv_bus2_one);
		//二号站台
		tv_bus1_two = (TextView) findViewById(R.id.tv_bus1_two);
		tv_bus2_two = (TextView) findViewById(R.id.tv_bus2_two);

		//左上角图片的点击事件
		iv_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	private void initData() {
		/*progressDialog = ProgressDialog.show(this, "正在加载", "请稍后");*/
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				initLeftData();
				initRightData();
			}
		}, 3000, 5000);
	}

	/**
	 * 加载左侧的数据
	 */
	private void initLeftData() {
		final AllSensors_vo allSensors_vo = HttpRequest.httpGetAllSense();
		if (allSensors_vo != null) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					tv_pm2_5.setText("\npm2.5 : " + allSensors_vo.getPm2_5() + " μg/m3");
					tv_temperature.setText("\n温度 : " + allSensors_vo.getTemperature() + " ℃");
					tv_humidity.setText("\n湿度 : " + allSensors_vo.getHumidity() + " %");
					tv_co2.setText("\nCO2 : " + allSensors_vo.getCo2() + " ppm");
				}
			});
		} else {
			//TODO 弹出对话框是否需要继续加载，还是取消加载
			//取消定时器
			if (timer != null) {
				timer.cancel();
			}
		}
	}

	/**
	 * 加载右侧的数据
	 */
	private void initRightData() {
		ArrayList<HashMap<String, Integer>> hashMaps = HttpRequest.httpGetBusStationInfo(1);
		if (!hashMaps.isEmpty()) {

		}
	}
}
