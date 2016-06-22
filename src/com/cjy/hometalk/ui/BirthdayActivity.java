package com.cjy.hometalk.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;





import android.widget.AdapterView.OnItemLongClickListener;

import com.cjy.hometalk.R;
import com.cjy.hometalk.db.BirthdayDbAdapter;
import com.cjy.hometalk.discovery.DiaryDbAdapter;
import com.cjy.hometalk.discovery.DiaryEdit;
import com.cjy.hometalk.discovery.EX056;
import com.nineoldandroids.view.ViewPropertyAnimator;


public class BirthdayActivity extends ListActivity {
	private List<String> data;
	private BaseAdapter adapter;
	private BirthdayDbAdapter mDbHelper;
	private Cursor mDiaryCursor;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birthday);
		mDbHelper = new BirthdayDbAdapter(this);
		mDbHelper.open();
		renderListView();
		initEvent();
		Button bt_birthday_add = (Button) findViewById(R.id.birthday_add);
		Button bt_birthday_back = (Button) findViewById(R.id.birthday_back);
		Button mom_birther = (Button) findViewById(R.id.bt_mom_1);
		Button dad_birther = (Button) findViewById(R.id.bt_dad);
		Button me_birther = (Button) findViewById(R.id.bt_me);
		Button birthday_click_cancel = (Button) findViewById(R.id.birthday_click_cancel);
		Button birthday_click_camera = (Button) findViewById(R.id.birthday_click_camera);
		Button birthday_click_local = (Button) findViewById(R.id.birthday_click_local);
		
		
		final RelativeLayout viewshow = (RelativeLayout) findViewById(R.id.birth_view);
		SharedPreferences sharedPreferences = this.getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
		String mom = sharedPreferences.getString("mombirthday", "未设置");
        String dad = sharedPreferences.getString("dadbirthday", "未设置");
        String me = sharedPreferences.getString("medbirthday", "未设置");
        mom_birther.setText(mom);
        dad_birther.setText(dad);
        me_birther.setText(me);
		
		//打开界面
		final Intent intent = new Intent(this,BirthdayAdd.class);
		bt_birthday_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ViewPropertyAnimator.animate(viewshow).translationY(50);
				
			}
		});
		//返回按钮
		bt_birthday_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//移除界面
		birthday_click_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ViewPropertyAnimator.animate(viewshow).translationY(-300);
			}
		});
		//增加父母生日
		birthday_click_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(intent);
				finish();
			}
		});
		birthday_click_local.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				createDiary();
				finish();
			}
		});
	}
	private void renderListView() {
		mDiaryCursor = mDbHelper.getAllNotes();
		startManagingCursor(mDiaryCursor);
		String[] from = new String[] { BirthdayDbAdapter.KEY_TITLE,
				BirthdayDbAdapter.KEY_BODY,BirthdayDbAdapter.KEY_CREATED};
		int[] to = new int[] { R.id.birthday_listview_guanxi,R.id.birthday_listview_name,R.id.birthday_listview_date };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.birthday_listview,
				mDiaryCursor, from, to);
		setListAdapter(notes);
	}
	private void createDiary() {
		Intent i = new Intent(this, BirthdayEdit.class);
		startActivity(i);
	}
	private void initEvent(){
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			private String name;
			private String id1;

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, 
                    int position, long id) {
				// TODO Auto-generated method stub
				BirthdayDbAdapter adapter = new BirthdayDbAdapter(getApplicationContext());
			
				if(mDiaryCursor.moveToPosition(position)){
					
					name = mDiaryCursor.getString(1);
					id1 = mDiaryCursor.getString(0);
					
				}
				
						
			
				showDeleteDialog(name,Integer.parseInt(id1));
				return true;
			}
		});
	}
@Override
protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, BirthdayEdit.class);
		i.putExtra(BirthdayDbAdapter.KEY_ROWID, id);
		i.putExtra(BirthdayDbAdapter.KEY_TITLE,
				c.getString(c.getColumnIndexOrThrow(BirthdayDbAdapter.KEY_TITLE)));
		i.putExtra(BirthdayDbAdapter.KEY_BODY,
				c.getString(c.getColumnIndexOrThrow(BirthdayDbAdapter.KEY_BODY)));
		i.putExtra(BirthdayDbAdapter.KEY_CREATED,
				c.getString(c.getColumnIndexOrThrow(BirthdayDbAdapter.KEY_CREATED)));
		startActivity(i);
		finish();
	}
private void showDeleteDialog(final String user, final int id) {
	new AlertDialog.Builder(BirthdayActivity.this)
			.setTitle("应用提示")
			.setMessage("确定删除  " + user + "这条数据吗？\n")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDbHelper.deleteDiary(id);
					renderListView();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			}).show();
}
}
