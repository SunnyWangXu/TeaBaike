package com.qianfeng.Task;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.jack.pullrefresh.ui.PullToRefreshListView;
import com.qianfeng.Adapter.mListAdapter;
import com.qianfeng.Enum.ContentType;
import com.qianfeng.bean.DataNews;
import com.qianfeng.tea_config.Config;

/**
 * 解析每个Fragment的网络数据
 * 
 * @author Administrator
 *
 */
public class MyFragmentTask extends AsyncTask<String, Void, List<DataNews>> {

	private List<DataNews> datas;
	private mListAdapter adapter;
	private PullToRefreshListView mPullRefreshView;
	private ContentType mType;

	public MyFragmentTask(PullToRefreshListView mPullRefreshView, List<DataNews> datas,
			mListAdapter adapter, ContentType mType) {
		this.adapter = adapter;
		this.datas = datas;
		this.mPullRefreshView = mPullRefreshView;
		this.mType = mType;
	}


	// 通过传过来的Type 返回type的数值
	private String getTypeValue(ContentType mType) {
		if (mType == ContentType.BAIKE)
			return "16";
		if (mType == ContentType.ZIXUN)
			return "52";
		if (mType == ContentType.SHUJU)
			return "54";
		if (mType == ContentType.JINGYING)
			return "53";
		return null;
	}

	// 通过传过来的Type 拼接Url
	@Override
	protected List<DataNews> doInBackground(String... params) {
		String url = null;
		if (mType == ContentType.TOUTIAO) {
			url = Config.URL_HEAD + "&page=0&rows=20";
		} else {
			url = Config.URL_OTHER + "&page=0&rows=20&type="
					+ getTypeValue(mType);
		}

		HttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				// 拿到数据 新建数据对象
				JSONObject jsonObj = new JSONObject(result);

				String resultState = jsonObj.getString("errorMessage");
				// 如果返回码成功则开始解析数据
				if (resultState.equals("success")) {

					JSONArray jsonArray = jsonObj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						// 数据组 对象
						DataNews dataNew = new DataNews();

						JSONObject item = jsonArray.getJSONObject(i);

						dataNew.setId(item.getString("id"));
						dataNew.setCreate_time(item.getString("create_time"));
						dataNew.setDescription(item.getString("description"));
						dataNew.setNickname(item.getString("nickname"));
						dataNew.setSource(item.getString("source"));
						dataNew.setTitle(item.getString("title"));
						dataNew.setWap_thumb(item.getString("wap_thumb"));

						// 拿到数据组 集合
						datas.add(dataNew);

					}
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datas;

	}

	@Override
	protected void onPostExecute(List<DataNews> result) {
		datas.addAll(result);
		adapter.notifyDataSetChanged();

		// 下拉刷新之后收回加载条
		mPullRefreshView.onPullDownRefreshComplete();
	}
}
