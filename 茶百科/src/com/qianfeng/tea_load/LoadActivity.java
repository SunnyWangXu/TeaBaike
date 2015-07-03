package com.qianfeng.tea_load;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.qianfeng.Welcome.WelcomeActivity;
import com.qianfeng.tea_config.Config;
import com.qianfeng.tea_cyclopedia.HomeActivity;
import com.qianfeng.tea_cyclopedia.R;

public class LoadActivity extends Activity {
	public static boolean isFirstIn = true;
	private Handler mHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.load_layout);

		SharedPreferences shared = getSharedPreferences(Config.LOAD,
				Context.MODE_PRIVATE);

		isFirstIn = shared.getBoolean("isFirstIn", true);

		if (isFirstIn) {
			init1();
		} else {
			init2();
		}

		mHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {

					Intent intent = new Intent();
					intent.setClass(getApplicationContext(),
							WelcomeActivity.class);
					startActivity(intent);

					isFirstIn = false;

					finish();
				} else if (msg.what == 1) {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), HomeActivity.class);
					startActivity(intent);

					finish();
				}
			}
		};

	}

	private void init1() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 0;
				mHandle.sendMessage(msg);

			}
		}).start();

	}

	private void init2() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 1;
				mHandle.sendMessage(msg);

			}
		}).start();
	}
}
