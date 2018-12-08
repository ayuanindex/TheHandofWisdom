package com.ayuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;

public class ParkingSpace extends AppCompatActivity {

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
	}
}
