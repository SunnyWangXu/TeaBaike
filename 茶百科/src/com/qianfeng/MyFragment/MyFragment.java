package com.qianfeng.MyFragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.jack.pullrefresh.ui.PullToRefreshBase;
import com.jack.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.jack.pullrefresh.ui.PullToRefreshListView;
import com.qianfeng.Adapter.mListAdapter;
import com.qianfeng.Enum.ContentType;
import com.qianfeng.Task.MyFragmentTask;
import com.qianfeng.bean.DataNews;
import com.qianfeng.tea_cyclopedia.R;
import com.qianfeng.webview_activity.WebViewActivity;

public class MyFragment extends Fragment implements OnPageChangeListener,
		 OnItemClickListener {
	private ListView listView;

	// fragment展示的内容类型
	protected ContentType mType;

	public MyFragment(ContentType type) {
		mType = type;
	}

	public static MyFragment getInstance(ContentType type) {
		// 新建一个对象
		MyFragment myFragment = new MyFragment(type);
		return myFragment;
	}

	List<DataNews> datas = new ArrayList<DataNews>();

	private PullToRefreshListView mPullRefreshView;

	private ListView mListView;

	private ViewPager headlinePager;

	private RadioGroup group;

	private View pagerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);

		// 加载一个 ViewPager和RadioGroup的视图
		pagerView = LayoutInflater.from(getActivity()).inflate(
				R.layout.headviewpage_layout, null);

		headlinePager = (ViewPager) pagerView.findViewById(R.id.headline_Pager);
		HeadlineAdapter headAdapter = new HeadlineAdapter();
		headlinePager.setAdapter(headAdapter);
		
		headlinePager.setOnPageChangeListener(this);

		group = (RadioGroup) pagerView.findViewById(R.id.radio_group);
		// 默认选择第一个点
		group.check(R.id.point_1);

		return view;
	}

	// ViewPager适配器
	private class HeadlineAdapter extends PagerAdapter {
		int[] imgIDs = { R.drawable.page1, R.drawable.page2, R.drawable.page3 };

		@Override
		public int getCount() {
			return imgIDs.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView img = new ImageView(container.getContext());
			img.setImageResource(imgIDs[position % 3]);
			container.addView(img);
			return img;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}
	}

	// 下拉刷新
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		mPullRefreshView = (PullToRefreshListView) view
				.findViewById(R.id.items_listview);

		mPullRefreshView.setPullLoadEnabled(true);

		mPullRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {

					}
				});

		// 可下拉刷新
		mPullRefreshView.setPullRefreshEnabled(true);

		// 下拉刷新使用这个getRefreshableView方法可当作ListView使用
		mListView = mPullRefreshView.getRefreshableView();

		// 设置头条才使用ViewPager
		if (mType.equals(ContentType.TOUTIAO)) {
			// 把viewPager加到ListView的开头
			mListView.addHeaderView(pagerView);
		}

		mListAdapter adapter = new mListAdapter(datas);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(this);
		/**
		 * 异步任务加载解析数据
		 */
		MyFragmentTask myFragmentTask = new MyFragmentTask(mPullRefreshView, datas, adapter, mType);
		myFragmentTask.execute(new String());

		super.onViewCreated(view, savedInstanceState);
		// 下拉刷新之后收回加载条
		// mPullRefreshView.onPullDownRefreshComplete();
	}

	// ViewPager 滑动监听器
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		int[] pointIDs = { R.id.point_1, R.id.point_2, R.id.point_3 };
		group.check(pointIDs[position]);
	}

	// mListView点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String itemID = datas.get(position -1).getId();
		Intent intent = new Intent();
		intent.putExtra("id", itemID);
		intent.setClass(parent.getContext(), WebViewActivity.class);
		startActivity(intent);

	}

}
