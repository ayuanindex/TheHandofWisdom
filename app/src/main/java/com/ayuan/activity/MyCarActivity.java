package com.ayuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.utils.SpUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MyCarActivity extends AppCompatActivity implements View.OnClickListener {

	private TextView tv_car1_speed;
	private TextView tv_car2_speed;
	private TextView tv_car3_speed;
	private TextView tv_car4_speed;
	private Button btn_start1_stop;
	private Button btn_start2_stop;
	private Button btn_start3_stop;
	private Button btn_start4_stop;
	private Button btn_recharge;
	private String TAG = "MyCarActivity";
	private TextView tv_back;
	private Timer timer;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_car);

		initUI();
		initData();
	}

	private void initUI() {
		//TextView
		tv_car1_speed = (TextView) findViewById(R.id.tv_car1_speed);
		tv_car2_speed = (TextView) findViewById(R.id.tv_car2_speed);
		tv_car3_speed = (TextView) findViewById(R.id.tv_car3_speed);
		tv_car4_speed = (TextView) findViewById(R.id.tv_car4_speed);
		tv_back = (TextView) findViewById(R.id.tv_back);
		//Button
		btn_start1_stop = (Button) findViewById(R.id.btn_start1_stop);
		btn_start2_stop = (Button) findViewById(R.id.btn_start2_stop);
		btn_start3_stop = (Button) findViewById(R.id.btn_start3_stop);
		btn_start4_stop = (Button) findViewById(R.id.btn_start4_stop);
		btn_recharge = (Button) findViewById(R.id.btn_recharge);//充值按钮
		//按钮点击事件
		btn_start1_stop.setOnClickListener(this);
		btn_start2_stop.setOnClickListener(this);
		btn_start3_stop.setOnClickListener(this);
		btn_start4_stop.setOnClickListener(this);
		btn_recharge.setOnClickListener(this);
		//textview设置点击事件
		tv_back.setOnClickListener(this);
	}

	private void initData() {
		//获取四辆小车的运行状态
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				carSpeed();
			}
		}, 2000, 3000);
	}

	private void carSpeed() {
		final int carSpeed1 = HttpRequest.httpGetCarSpeed(1);
		final int carSpeed2 = HttpRequest.httpGetCarSpeed(2);
		final int carSpeed3 = HttpRequest.httpGetCarSpeed(3);
		final int carSpeed4 = HttpRequest.httpGetCarSpeed(4);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (carSpeed1 > 0) {
					btn_start1_stop.setText("停止");
					tv_car1_speed.setText("车速: " + carSpeed1 + "KM/h");
					SpUtils.putBoolean(MyCarActivity.this, SpUtils.CAR1, true);
				}
				if (carSpeed2 > 0) {
					btn_start2_stop.setText("停止");
					tv_car2_speed.setText("车速: " + carSpeed2 + "KM/h");
					SpUtils.putBoolean(MyCarActivity.this, SpUtils.CAR2, true);
				}
				if (carSpeed3 > 0) {
					btn_start3_stop.setText("停止");
					tv_car3_speed.setText("车速: " + carSpeed3 + "KM/h");
					SpUtils.putBoolean(MyCarActivity.this, SpUtils.CAR3, true);
				}
				if (carSpeed4 > 0) {
					btn_start4_stop.setText("停止");
					tv_car4_speed.setText("车速: " + carSpeed4 + "KM/h");
					SpUtils.putBoolean(MyCarActivity.this, SpUtils.CAR4, true);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_start1_stop:
				myStart_Stop_Car(btn_start1_stop, SpUtils.CAR1, 1);
				break;
			case R.id.btn_start2_stop:
				myStart_Stop_Car(btn_start2_stop, SpUtils.CAR2, 2);
				break;
			case R.id.btn_start3_stop:
				myStart_Stop_Car(btn_start3_stop, SpUtils.CAR3, 3);
				break;
			case R.id.btn_start4_stop:
				myStart_Stop_Car(btn_start4_stop, SpUtils.CAR4, 4);
				break;
			case R.id.btn_recharge:
				Intent intent = new Intent(getApplicationContext(), QueryRecharge.class);
				startActivity(intent);
				break;
			case R.id.tv_back:
				if (timer != null) {
					timer.cancel();
				}
				finish();
				break;
		}
	}

	private void myStart_Stop_Car(final Button btn, final String CAR, final int CarId) {
		boolean aBoolean = SpUtils.getBoolean(this, CAR, false);
		if (aBoolean) {
			new Thread() {
				@Override
				public void run() {
					super.run();
					final String stop = HttpRequest.httpSetCarMove(CarId, "Stop");
					if (stop == null) {
						return;
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (stop.equals("ok")) {
								btn.setText("启动");
								Log.i(TAG, "嘿嘿:" + stop);
								SpUtils.putBoolean(MyCarActivity.this, CAR, false);
							}
						}
					});
				}
			}.start();
		} else {
			new Thread() {
				@Override
				public void run() {
					super.run();
					final String start = HttpRequest.httpSetCarMove(CarId, "Start");
					if (start == null) {
						return;
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (start.equals("ok")) {
								btn.setText("停止");
								Log.i(TAG, "嘿嘿:" + start);
								SpUtils.putBoolean(MyCarActivity.this, CAR, true);
							}
						}
					});
				}
			}.start();
		}
	}
}
