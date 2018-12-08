package com.ayuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
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

		//回显ip地址到控件的hint属性中
		String s = HttpRequest.setIp();
		String[] split = s.split("\\.");
		if (split.length >= 4) {
			et_one.setHint(split[0]);
			et_two.setHint(split[1]);
			et_three.setHint(split[2]);
			et_four.setHint(split[3]);
		}

		btn_ok.setOnClickListener(this);
		et_one.setOnFocusChangeListener(this);
		et_two.setOnFocusChangeListener(this);
		et_three.setOnFocusChangeListener(this);
		et_four.setOnFocusChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_ok:
				buttonOk();
				break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
			case R.id.et_one:
				if (!hasFocus) {
					judgment(et_one);
				}
				break;
			case R.id.et_two:
				if (!hasFocus) {
					judgment(et_two);
				}
				break;
			case R.id.et_three:
				if (!hasFocus) {
					judgment(et_three);
				}
				break;
			case R.id.et_four:
				if (!hasFocus) {
					judgment(et_four);
				}
		}
	}

	public void anim(View view) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(5));
		translateAnimation.setDuration(200);
		view.startAnimation(translateAnimation);
	}

	private void buttonOk() {
		String trim = et_one.getText().toString().trim();
		String trim1 = et_two.getText().toString().trim();
		String trim2 = et_three.getText().toString().trim();
		String trim3 = et_four.getText().toString().trim();
		boolean b = TextUtils.isEmpty(trim) && TextUtils.isEmpty(trim1) && TextUtils.isEmpty(trim2) && TextUtils.isEmpty(trim3);
		if (b) {
			Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
			return;
		}
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
			startActivity(new Intent(getApplicationContext(), HomeActivity.class));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断ip地址的方法
	 *
	 * @param editText 需要判断的控件
	 */
	private void judgment(EditText editText) {
		String patter = "(\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]){3}";
		String trim = editText.getText().toString().trim();
		if (!trim.matches(patter)) {
			editText.setText("");
			anim(editText);
			Toast.makeText(this, "格式有误", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
