package com.cjy.hometalk.ui;

import java.util.ArrayList;
import java.util.List;

import com.cjy.hometalk.MyListView;
import com.cjy.hometalk.MyListView.OnRefreshListener;
import com.cjy.hometalk.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



public class RandomActivity extends Activity{
	private List<String> data;
	private BaseAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random);
		Button bt_random = (Button) findViewById(R.id.roundButton);
		Button bt_random_back = (Button) findViewById(R.id.random_back);
		data = new ArrayList<String>();
		

		final MyListView listView = (MyListView) findViewById(R.id.random);
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				View view=View.inflate(getApplicationContext(), R.layout.random_listview, null);
				TextView random_listview_name = (TextView) view.findViewById(R.id.random_listview_name);
				random_listview_name.setTextColor(Color.BLACK);
				random_listview_name.setText(data.get(position));
				return view;
			}

			public long getItemId(int position) {
				return 0;
			}

			public Object getItem(int position) {
				return null;
			}

			public int getCount() {
				return data.size();
			}
		};
		listView.setAdapter(adapter);

		listView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						data.add("第"+listView.jishu()+"位掷出了"+(int)(Math.random()*100));
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

				}.execute();
			}
		});
		bt_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				data.add("第"+listView.jishu()+"位掷出了"+(int)(Math.random()*100));
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
			}
		});
		bt_random_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
