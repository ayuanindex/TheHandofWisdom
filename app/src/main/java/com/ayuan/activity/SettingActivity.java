package com.ayuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
	private String TAG = "SettingActivity ";
	private EditText et_one;
	private EditText et_two;
	private EditText et_three;
	private EditText et_four;
	private Button btn_ok;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUI();
	}

	private void initUI() {
		et_one = (EditText) findViewById(R.id.et_one);
		et_two = (EditText) findViewById(R.id.et_two);
		et_three = (EditText) findViewById(R.id.et_three);
		et_four = (EditText) findViewById(R.id.et_four);
		btn_ok = (Button) findViewById(R.id.btn_ok);

		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.et_one:
				break;
			case R.id.et_two:
				break;
			case R.id.et_three:
				break;
			case R.id.et_four:
				break;
			case R.id.btn_ok:
				buttonOk();
				break;
		}
	}


	private void buttonOk() {
		try {
			File filesDir = getFilesDir();
			File file = new File(filesDir, "ip.txt");
			String one = et_one.getText().toString().trim();
			String two = et_two.getText().toString().trim();
			String three = et_three.getText().toString().trim();
			String four = et_four.getText().toString().trim();
			String ip = one + "." + two + "." + three + "." + four;
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] bytes = ip.getBytes();
			fileOutputStream.write(bytes);
			HttpRequest.setIp();
			Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
