package com.ayuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

	private TextView tv_json;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
		initData();
	}


	private void initUI() {
		tv_json = (TextView) findViewById(R.id.tv_json);
	}

	private void initData() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				final ArrayList<Integer> integers = HttpRequest.httpGetParkFree();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}
				});
			}
		}.start();
	}
}
