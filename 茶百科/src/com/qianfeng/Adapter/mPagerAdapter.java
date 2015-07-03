package com.qianfeng.Adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class mPagerAdapter extends PagerAdapter {
	private List<View> mPagers;

	public mPagerAdapter(List<View> mPagers) {
		this.mPagers = mPagers;
	}

	@Override
	public int getCount() {
		return mPagers.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mPagers.get(position));

		return mPagers.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mPagers.get(position));
	}
}
