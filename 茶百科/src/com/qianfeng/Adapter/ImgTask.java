package com.qianfeng.Adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImgTask extends AsyncTask<String, Void, Bitmap> {

	private String imgUrl;
	private ImageView imageView;
	private Context mContext;
	private final String ImageDir = "yyy";
	private String mCacheDir;

	public ImgTask(Context context, String imgUrl, ImageView imageView) {
		this.imgUrl = imgUrl;
		this.imageView = imageView;
		mContext = context;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// 建立一个缓存目录
		mCacheDir = mContext.getCacheDir().toString() + "/" + ImageDir;

		Bitmap bitmap = null;

		// 判断缓存目录是否存在不存的话新建
		File cachedir = new File(mCacheDir);
		if (!cachedir.exists()) {
			cachedir.mkdirs();
		}

		// 图片缓存的路径
		File file = new File(getCacheFilePath(imgUrl));
		// 判断本地是否已经缓存了图片。已经缓存了就读取本读图片。否则就网络下载图片
		if (file.exists()) {
			// 从本地磁盘加载图片
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			// 从网络下载
			bitmap = getBitmapFromNetwork(bitmap, imgUrl);
		}

		return bitmap;

	}

	// 网络下载并写入本地
	private Bitmap getBitmapFromNetwork(Bitmap bitmap, String imgUrl) {
		if (!imgUrl.equals("")) {

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(imgUrl);
			try {
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					InputStream is = response.getEntity().getContent();

					bitmap = BitmapFactory.decodeStream(is);

					// 图片存入的路径
					File file = new File(getCacheFilePath(imgUrl));
					FileOutputStream fos = new FileOutputStream(file);

					bitmap.compress(CompressFormat.JPEG, 100, fos);

					// byte[] buff = new byte[1024];
					// int count;
					// if ((count = (is.read(buff))) != -1) {
					// fos.write(buff);
					// }
					// fos.flush();
					// fos.close();
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	// 返回图片的绝对路径
	private String getCacheFilePath(String imgUrl) {
		return mCacheDir
				+ "/"
				+ imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length());
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			imageView.setImageBitmap(result);
		}
	}
}
