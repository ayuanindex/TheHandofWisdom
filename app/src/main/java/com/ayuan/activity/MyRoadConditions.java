package com.ayuan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.utils.Sequence;
import com.ayuan.utils.SpUtils;
import com.ayuan.vo.TrafficLight_vo;

import java.util.ArrayList;

public class MyRoadConditions extends AppCompatActivity {

	private static final String TAG = "MyRoadConditions";
	private TextView et_one;
	private TextView et_two;
	private TextView et_three;
	private TextView et_four;
	private TextView et_five;
	private TextView et_red_one;
	private TextView et_red_two;
	private TextView et_red_three;
	private TextView et_red_four;
	private TextView et_red_five;
	private TextView et_green_one;
	private TextView et_green_two;
	private TextView et_green_three;
	private TextView et_green_four;
	private TextView et_green_five;
	private TextView et_yellow_one;
	private TextView et_yellow_two;
	private TextView et_yellow_three;
	private TextView et_yellow_four;
	private TextView et_yellow_five;
	private Spinner s_sort;
	private Button btn_query;
	private ArrayList<TrafficLight_vo> trafficLight_vos;
	private String[] strings = new String[]{"红灯升序", "绿灯降序", "路口降序", "绿灯升序", "黄灯升序", "路口升序", "红灯降序", "黄灯降序"};
	private TextView tv_back;
	private TextView[][] table;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myroadconditions);

		intiUI();
	}


	private void intiUI() {
		//路口
		et_one = (TextView) findViewById(R.id.et_one);
		et_two = (TextView) findViewById(R.id.et_two);
		et_three = (TextView) findViewById(R.id.et_three);
		et_four = (TextView) findViewById(R.id.et_four);
		et_five = (TextView) findViewById(R.id.et_five);
		//红灯
		et_red_one = (TextView) findViewById(R.id.et_red_one);
		et_red_two = (TextView) findViewById(R.id.et_red_two);
		et_red_three = (TextView) findViewById(R.id.et_red_three);
		et_red_four = (TextView) findViewById(R.id.et_red_four);
		et_red_five = (TextView) findViewById(R.id.et_red_five);
		//绿灯
		et_green_one = (TextView) findViewById(R.id.et_green_one);
		et_green_two = (TextView) findViewById(R.id.et_green_two);
		et_green_three = (TextView) findViewById(R.id.et_green_three);
		et_green_four = (TextView) findViewById(R.id.et_green_four);
		et_green_five = (TextView) findViewById(R.id.et_green_five);
		//黄灯
		et_yellow_one = (TextView) findViewById(R.id.et_yellow_one);
		et_yellow_two = (TextView) findViewById(R.id.et_yellow_two);
		et_yellow_three = (TextView) findViewById(R.id.et_yellow_three);
		et_yellow_four = (TextView) findViewById(R.id.et_yellow_four);
		et_yellow_five = (TextView) findViewById(R.id.et_yellow_five);
		//下拉列表
		s_sort = (Spinner) findViewById(R.id.s_sort);
		ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.vew_sort_item, strings);
		s_sort.setAdapter(stringArrayAdapter);
		//按钮
		btn_query = (Button) findViewById(R.id.btn_query);
		tv_back = (TextView) findViewById(R.id.tv_back);
		//textview二维数组
		table = new TextView[][]{
				{et_one, et_red_one, et_green_one, et_yellow_one},
				{et_two, et_red_two, et_green_two, et_yellow_two},
				{et_three, et_red_three, et_green_three, et_yellow_three},
				{et_four, et_red_four, et_green_four, et_yellow_four},
				{et_five, et_red_five, et_green_five, et_yellow_five}
		};

		s_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SpUtils.putString(MyRoadConditions.this, SpUtils.SORT, strings[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		btn_query.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initData();
			}
		});
		tv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initData() {
		try {
			trafficLight_vos = new ArrayList<TrafficLight_vo>();
			new Thread() {
				@SuppressLint("NewApi")
				@Override
				public void run() {
					super.run();
					for (int i = 0; i < 5; i++) {
						try {
							TrafficLight_vo trafficLight_vo = HttpRequest.httpGetTrafficLightConfigAction(i);
							if (trafficLight_vo != null) {
								trafficLight_vo.setRoad(i);
								trafficLight_vos.add(trafficLight_vo);
							} else {
								initData();
							}
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					setText();
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "捕捉到了异常");
		}
	}

	private void setText() {
		String sort = SpUtils.getString(this, SpUtils.SORT, "红灯升序");
		if (sort.equals(strings[0])) {
			if (trafficLight_vos != null) {
				new Thread() {
					@Override
					public void run() {
						super.run();
						Sequence.redLightAscending(trafficLight_vos);
						updateUI(trafficLight_vos);
					}
				}.start();
			}
		} else if (sort.equals("绿灯降序")) {

		}
	}

	private void updateUI(ArrayList<TrafficLight_vo> trafficLight_vos) {
		for (int i = 0; i < trafficLight_vos.size(); i++) {
			Integer road = trafficLight_vos.get(i).getRoad();
			Integer red = trafficLight_vos.get(i).getRed();
			Integer green = trafficLight_vos.get(i).getGreen();
			Integer yellow = trafficLight_vos.get(i).getYellow();
			if (table != null) {
				table[i][0].setText(road + "号路口");
				table[i][1].setText(red);
				table[i][2].setText(green);
				table[i][3].setText(yellow);
			}
		}
	}
}
