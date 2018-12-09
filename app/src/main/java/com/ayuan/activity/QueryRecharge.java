package com.ayuan.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayuan.thehandofwisdom.R;
import com.ayuan.utils.HttpRequest;
import com.ayuan.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryRecharge extends Activity implements View.OnClickListener {

	private Spinner spinner;
	private Spinner spinner2;
	private Button btn_query;
	private Button btn_query2;
	private EditText et_money;
	private Integer[] carId = new Integer[]{1, 2, 3, 4};
	private TextView tv_back;
	private String TAG = "QueryRcharge";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queryrecharge);

		initUI();
	}

	private void initUI() {
		//下拉框
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		//按钮
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_query2 = (Button) findViewById(R.id.btn_query2);
		//输入框
		et_money = (EditText) findViewById(R.id.et_money);
		//textview
		tv_back = (TextView) findViewById(R.id.tv_back);

		//设置下拉列表设置数据适配器
		ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.view_carid_item, carId);
		spinner.setAdapter(integerArrayAdapter);
		spinner2.setAdapter(integerArrayAdapter);

		//给按钮设置点击事件
		btn_query.setOnClickListener(this);
		btn_query2.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SpUtils.putString(QueryRecharge.this, SpUtils.SPANNER1, carId[position].toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SpUtils.putString(QueryRecharge.this, SpUtils.SPANNER2, carId[position].toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_query:
				//查询操作
				query();
				break;
			case R.id.btn_query2:
				//充值操作
				recharge();
				break;
			case R.id.tv_back:
				finish();
				break;
		}
	}

	private void recharge() {
		try {
			final String id = SpUtils.getString(QueryRecharge.this, SpUtils.SPANNER2, "");
			final String trim = et_money.getText().toString().trim();
			if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(trim)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(QueryRecharge.this);
				builder.setIcon(R.drawable.rmb);
				builder.setTitle("小车账户充值");
				builder.setMessage("在" + getDate() + "将要给" + id + "号小车充值" + trim + "元");
				//忽略按钮
				builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				//确认按钮
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread() {
							@Override
							public void run() {
								super.run();
								String s = HttpRequest.httpSetCarAccountRecharge(Integer.parseInt(id), Double.parseDouble(trim));
								if (s.equals("ok")) {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(QueryRecharge.this, "充值成功", Toast.LENGTH_SHORT).show();
											et_money.setText("");
										}
									});
								}
							}
						}.start();
						dialog.dismiss();
					}
				});
				//取消按钮
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			} else {
				Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.i(TAG, "数字格式转换异常");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "捕捉到了异常");
		}
	}


	private void query() {
		try {
			new Thread() {
				@Override
				public void run() {
					super.run();
					String id = SpUtils.getString(QueryRecharge.this, SpUtils.SPANNER1, "");
					if (!TextUtils.isEmpty(id)) {
						final double balance = HttpRequest.httpGetCarAccountBalance(Integer.parseInt(id));
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (balance >= 0) {
									Toast.makeText(QueryRecharge.this, "您的账户余额:" + balance + "元", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(QueryRecharge.this, "查询失败", Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}.start();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.i(TAG, "数字格式化异常");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "捕捉到了异常");
		}
	}

	public String getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String format = simpleDateFormat.format(date);
		return format;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
