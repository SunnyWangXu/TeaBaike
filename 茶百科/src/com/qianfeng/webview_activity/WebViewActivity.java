package com.qianfeng.webview_activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import com.qianfeng.tea_cyclopedia.R;

public class WebViewActivity extends Activity {

	private WebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);

		Intent intent = getIntent();
		String ID = intent.getStringExtra("id");
		String url = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=";
		listItemTask mListItemTask = new listItemTask(getApplicationContext(), ID);
		mListItemTask.execute(url);
		web = (WebView) findViewById(R.id.webview);
		
	}

	private String data;
	private String baseUrl;

	private class listItemTask extends AsyncTask<String, Void, String> {

		private String mID;
		private Context mContext;

		public listItemTask(Context context, String itemID) {
			mID = itemID;
			mContext = context;
		}

		// 请求解析网页接口数据
		@Override
		protected String doInBackground(String... params) {
			String JSONStr = null;
			baseUrl = params[0] + mID;
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(baseUrl);
			try {
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					JSONStr = EntityUtils.toString(response.getEntity());
				}

			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				JSONObject obj = new JSONObject(JSONStr);
				String result = obj.getString("errorMessage");
				if (result.equals("success")) {
					JSONObject dataObj = obj.getJSONObject("data");
					data = dataObj.getString("wap_content");

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			// 用webView的方法 打开网页显示数据
			web.loadDataWithBaseURL(baseUrl, result, "text/html", "utf_8", null);
		}
	}
}
