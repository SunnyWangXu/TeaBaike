package com.qianfeng.tea_Welcome_activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.qianfeng.Adapter.mPagerAdapter;
import com.qianfeng.tea_cyclopedia.R;
import com.qianfeng.tea_home_activity.HomeActivity;

public class WelcomeActivity extends Activity {
	private ViewPager mPager;
	private mPagerAdapter adpter;
	private List<View> mPagers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.welcome_layout);

		mPager = (ViewPager) findViewById(R.id.viewpager);
		initViews();

		adpter = new mPagerAdapter(mPagers);
		mPager.setAdapter(adpter);

	}

	private void initViews() {
		mPagers = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			View view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.viewpager_item, null);
			ImageView img = (ImageView) view.findViewById(R.id.img_viewPager);
			int draw = R.drawable.slide1;
			if (i == 0) {
				draw = R.drawable.slide1;
			}
			if (i == 1) {
				draw = R.drawable.slide2;
			}
			if (i == 2) {
				draw = R.drawable.slide3;
			}
			img.setImageResource(draw);

			if (i == 2) {
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						startActivity(new Intent(getApplicationContext(),
								HomeActivity.class));
						finish();
					}
				});
			}
			mPagers.add(view);
		}
	}
}