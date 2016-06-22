package com.cjy.hometalk.ui;

import com.cjy.hometalk.R;
import com.cjy.hometalk.discovery.EX056;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class DiscoverFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, null);
		LinearLayout bt_discover = (LinearLayout)view.findViewById(R.id.discover_btn);
		LinearLayout bt_earthquake = (LinearLayout)view.findViewById(R.id.discover_earthquake);
		LinearLayout bt_weather = (LinearLayout)view.findViewById(R.id.discover_weather);
		LinearLayout bt_location = (LinearLayout)view.findViewById(R.id.discover_location);
		LinearLayout bt_random = (LinearLayout)view.findViewById(R.id.discover_random);
		LinearLayout bt_about = (LinearLayout)view.findViewById(R.id.discover_about);
		LinearLayout bt_birthday = (LinearLayout)view.findViewById(R.id.discover_birthday);
		bt_discover.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),EX056.class));
			}
		});
		bt_earthquake.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri =Uri.parse("http://www.ceic.ac.cn/speedsearch?time=3");
				Intent it = new Intent(Intent.ACTION_VIEW,uri); 

				startActivity(it); 
			}
		});
		bt_weather.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri =Uri.parse("https://m.baidu.com/s?word=天气");
				Intent it = new Intent(Intent.ACTION_VIEW,uri); 

				startActivity(it); 
			}
		});
		bt_location.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri =Uri.parse("http://map.baidu.com/mobile/webapp/index/index/");
				Intent it = new Intent(Intent.ACTION_VIEW,uri); 

				startActivity(it); 
			}
		});
		bt_random.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),RandomActivity.class));
			}
		});
		bt_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),AboutActivity.class));
			}
		});
		bt_birthday.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),BirthdayActivity.class));
			}
		});
		return view; 
	}
	
	
}
