package com.ayuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingSpace extends AppCompatActivity implements View.OnClickListener {

	private TextView tv_rate;
	private EditText et_parkingrate;
	private Button btn_query;
	private Button btn_setting;
	private Button btn_query2;
	private EditText et_salesunit;
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_back;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parkingspace);

		initUI();
		initData(false);
	}


	private void initUI() {
		tv_rate = (TextView) findViewById(R.id.tv_rate);//费率
		tv_back = (TextView) findViewById(R.id.tv_back);
		et_parkingrate = (EditText) findViewById(R.id.et_parkingrate);//费率
		et_salesunit = (EditText) findViewById(R.id.et_salesunit);//计价单位
		iv_left = (ImageView) findViewById(R.id.iv_left);
		iv_right = (ImageView) findViewById(R.id.iv_right);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_query2 = (Button) findViewById(R.id.btn_query2);
		btn_setting = (Button) findViewById(R.id.btn_setting);

		btn_query.setOnClickListener(this);
		btn_query2.setOnClickListener(this);
		btn_setting.setOnClickListener(this);
	}

	private void initData(final boolean b) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					HashMap<String, Object> stringObjectHashMap = HttpRequest.httpGetParkRate();
					Thread.sleep(500);
					final ArrayList<Integer> integers = HttpRequest.httpGetParkFree();
					if (!stringObjectHashMap.values().isEmpty()) {
						final String rateType = (String) stringObjectHashMap.get("RateType");
						final double money = (double) stringObjectHashMap.get("Money");

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (!TextUtils.isEmpty(rateType) && money >= 0) {
									tv_rate.setText(money + "元/次");
									parkFree(integers, 0, iv_left);
									parkFree(integers, 1, iv_right);
									if (b) {
										Toast.makeText(ParkingSpace.this, "查询成功", Toast.LENGTH_SHORT).show();
									}
								}
							}
						});

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void parkFree(ArrayList<Integer> integers, int i, ImageView iv_right) {
		if (integers.get(i) > 0 && integers.get(i) == 2) {
			iv_right.setImageResource(R.drawable.bus);
		} else {
			iv_right.setImageResource(R.drawable.car);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_query:
				//查询费率
				initData(true);
				break;
			case R.id.btn_setting:
				//设置费率
				setting();
				break;
			case R.id.btn_query2:
				//查询空位
				new Thread() {
					@Override
					public void run() {
						super.run();
						final ArrayList<Integer> integers = HttpRequest.httpGetParkFree();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (!integers.isEmpty()) {
									parkFree(integers, 0, iv_left);
									parkFree(integers, 1, iv_right);
								}
							}
						});
					}
				}.start();
				break;
		}
	}

	public void setting() {
		final String trim = et_parkingrate.getText().toString().trim();
		final String trim1 = et_salesunit.getText().toString().trim();
		if (TextUtils.isEmpty(trim1)) {
			Toast.makeText(this, "请输入计价单位", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(trim)) {
			Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (trim1.equals("次")) {
					String s = HttpRequest.httpSetParkRate("Count", Double.parseDouble(trim));
					if (!TextUtils.isEmpty(s) && s.equals("ok")) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(ParkingSpace.this, "设置成功", Toast.LENGTH_SHORT).show();
							}
						});
					} else {
						Toast.makeText(ParkingSpace.this, "设置失败", Toast.LENGTH_SHORT).show();
					}
				} else if (trim1.equals("小时")) {
					String s = HttpRequest.httpSetParkRate("Hour", Double.parseDouble(trim));
					if (!TextUtils.isEmpty(s) && s.equals("ok")) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(ParkingSpace.this, "设置成功", Toast.LENGTH_SHORT).show();
							}
						});
					} else {
						Toast.makeText(ParkingSpace.this, "设置失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}.start();
	}
}
