package com.qianfeng.tea_cyclopedia;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.qianfeng.Enum.ContentType;
import com.qianfeng.MyFragment.MyFragment;

public class HomeActivity extends FragmentActivity implements TabListener,
		OnPageChangeListener {
	private ActionBar.Tab tab_top;
	private ActionBar.Tab tab_cyclopedia;
	private ActionBar.Tab tab_message;
	private ActionBar.Tab tab_manage;
	private ActionBar.Tab tab_data;

	// private Fragment fragment_top;
	// private Fragment fragment_cyclopedia;
	// private Fragment fragment_consult;
	// private Fragment fragment_manage;
	// private Fragment fragment_data;
	private ViewPager mPager;
	private List<Fragment> fragments;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// fragments集合加数据
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < 5; i++) {
			// 新建一个带type的fragment对象
			Fragment fragment = MyFragment.getInstance(ContentType.values()[i]);

			fragments.add(fragment);
		}

		mPager = (ViewPager) findViewById(R.id.viewpager_home);
		MyAdapter pagerAdapter = new MyAdapter(getSupportFragmentManager(),
				fragments);
		mPager.setAdapter(pagerAdapter);

		mPager.setOnPageChangeListener(this);

		// fragment_top = new TopFregment();
		// fragment_cyclopedia = new CyclopediaFregment();
		// fragment_consult = new ConsultFregment();
		// fragment_manage = new ManageFregment();
		// fragment_data = new DataFregment();

		// 创建ActionbarTab 设置监听事件
		intiActionTab();

	}

	private void intiActionTab() {
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tab_top = actionBar.newTab().setText("头条");
		tab_cyclopedia = actionBar.newTab().setText("百科");
		tab_message = actionBar.newTab().setText("资讯");
		tab_manage = actionBar.newTab().setText("经营");
		tab_data = actionBar.newTab().setText("数据");

		tab_top.setTabListener(this);
		tab_cyclopedia.setTabListener(this);
		tab_message.setTabListener(this);
		tab_manage.setTabListener(this);
		tab_data.setTabListener(this);

		actionBar.addTab(tab_top);
		actionBar.addTab(tab_cyclopedia);
		actionBar.addTab(tab_message);
		actionBar.addTab(tab_manage);
		actionBar.addTab(tab_data);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	// 选中tab关联viewPager的item
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	// viewPager适配器
	private class MyAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments = null;

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyAdapter(FragmentManager supportFragmentManager,
				List<Fragment> fragments) {
			super(supportFragmentManager);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int postion) {
			return fragments.get(postion);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// viewpager 变化带动tab变化
	@Override
	public void onPageSelected(int position) {
		actionBar.setSelectedNavigationItem(position);

	}
}
