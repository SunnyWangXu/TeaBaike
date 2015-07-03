package com.qianfeng.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.bean.DataNews;
import com.qianfeng.tea_cyclopedia.R;

public class mAdapter extends BaseAdapter {

	private List<DataNews> datas;

	public mAdapter(List<DataNews> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public DataNews getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = null;
		Context mContext = parent.getContext();
		if (convertView == null) {
			Holder holder = new Holder();

			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.listview_layout, parent,
					false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.source = (TextView) convertView.findViewById(R.id.tv_source);
			holder.name = (TextView) convertView.findViewById(R.id.tv_nickname);
			holder.time = (TextView) convertView
					.findViewById(R.id.tv_create_time);

			holder.img = (ImageView) convertView
					.findViewById(R.id.img_wap_thumb);

			convertView.setTag(holder);
		}
		Holder holder = (Holder) convertView.getTag();

		holder.title.setText(getItem(position).getTitle());
		holder.name.setText(getItem(position).getNickname());
		holder.source.setText(getItem(position).getSource());
		holder.time.setText(getItem(position).getCreate_time());

		// 图片的Url；
		String imgUrl = getItem(position).getWap_thumb();

		ImageView imageView = holder.img;

		// 如果没有图片数据图片就不显示，有图片数据才显示
		// if (imgUrl.length() == 0 || imgUrl == null) {
		// imageView.setVisibility(View.GONE);
		// } else {
		// 防止重用
		imageView.setImageResource(R.drawable.defaultcovers);
		// }
		// 创建异步任务下载图片
		ImgTask imgTask = new ImgTask(mContext, imgUrl, imageView);
		imgTask.execute(imgUrl);

		return convertView;
	}

	private class Holder {
		private TextView title;
		private TextView source;
		private TextView name;
		private TextView time;
		private ImageView img;
	}
}