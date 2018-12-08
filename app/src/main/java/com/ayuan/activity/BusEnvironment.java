package com.ayuan.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ayuan.thehandofwisdom.R;

public class BusEnvironment extends AppCompatActivity implements View.OnClickListener {
	private Button btn_1;
	private Button btn_2;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busevironment);

		initUI();
	}

	private void initUI() {
		btn_1 = (Button) findViewById(R.id.btn_1);
		btn_2 = (Button) findViewById(R.id.btn_2);

		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.ll, new OneFragment());
		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_1:
				addFragment(new OneFragment());
				break;
			case R.id.btn_2:
				addFragment(new TwoFragment());
				break;
		}
	}

	private void addFragment(Fragment fragment) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.ll, fragment);
		fragmentTransaction.commit();
	}
}
