package com.ayuan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.vo.AllSensors_vo;
import com.ayuan.vo.BusStation_vo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

	private static final String TAG = "HomeActivity";
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
	private DrawerLayout activity_na;
	private NavigationView nav;
	/*private ProgressDialog progressDialog;*/

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
		initData();
	}


	@SuppressLint("WrongViewCast")
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
		//侧边栏
		activity_na = (DrawerLayout) findViewById(R.id.activity_na);
		nav = (NavigationView) findViewById(R.id.nav);
		View headerView = nav.getHeaderView(0);//获取头布局

		nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				Toast.makeText(HomeActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
				activity_na.closeDrawer(nav);
				return true;
			}
		});

		//左上角图片的点击事件
		iv_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity_na.isDrawerOpen(nav)) {
					activity_na.closeDrawer(nav);
				} else {
					activity_na.openDrawer(nav);
				}
			}
		});
	}

	private void initData() {
		/*progressDialog = ProgressDialog.show(this, "正在加载", "请稍后");*/
		timer = new Timer();
		new Timer().scheduleAtFixedRate(new TimerTask() {
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
					tv_pm2_5.setText("pm2.5 : " + allSensors_vo.getPm2_5() + " μg/m3");
					tv_temperature.setText("温度 : " + allSensors_vo.getTemperature() + " ℃; ");
					tv_humidity.setText("湿度 : " + allSensors_vo.getHumidity() + " %");
					tv_co2.setText("CO2 : " + allSensors_vo.getCo2() + " ppm; ");
				}
			});
		}
	}

	/**
	 * 加载右侧的数据
	 */
	private void initRightData() {
		final ArrayList<BusStation_vo> busStation_vo = HttpRequest.httpGetBusStationInfo(1);
		if (busStation_vo != null) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					BusStation_vo bus1 = busStation_vo.get(0);
					BusStation_vo bus2 = busStation_vo.get(1);
					if (!busStation_vo.isEmpty()) {
						tv_bus1_one.setText(bus1.getBusId() + "号公交:" + bus1.getDistance() + "m; ");
						tv_bus2_one.setText(bus2.getBusId() + "号公交:" + bus2.getDistance() + "m");
					}
				}
			});
		}
		final ArrayList<BusStation_vo> busStation_vo2 = HttpRequest.httpGetBusStationInfo(2);
		if (busStation_vo != null) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					BusStation_vo bus1 = busStation_vo2.get(0);
					BusStation_vo bus2 = busStation_vo2.get(1);
					if (!busStation_vo.isEmpty()) {
						tv_bus1_two.setText(bus1.getBusId() + "号公交:" + bus1.getDistance() + "m; ");
						tv_bus2_two.setText(bus2.getBusId() + "号公交:" + bus2.getDistance() + "m");
					}
				}
			});
		}
	}
}
