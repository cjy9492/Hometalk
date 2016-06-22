package com.cjy.hometalk.discovery;



import java.util.ArrayList;
import java.util.List;

import com.cjy.hometalk.R;
import com.cjy.hometalk.ui.DiscoverFragment;
import com.cjy.hometalk.ui.MainActivity;
import com.cjy.hometalk.ui.MeFragment;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemLongClickListener;

public class EX056 extends ListActivity {
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DiaryDbAdapter mDbHelper;
	private Cursor mDiaryCursor;
	private List<String> userList = new ArrayList<String>();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discovery_list);
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		renderListView();
		initEvent();
		
	}

	private void renderListView() {
		mDiaryCursor = mDbHelper.getAllNotes();
		startManagingCursor(mDiaryCursor);
		String[] from = new String[] { DiaryDbAdapter.KEY_TITLE,
				DiaryDbAdapter.KEY_CREATED };
		int[] to = new int[] { R.id.text1, R.id.created };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.discovery_row,
				mDiaryCursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		return true;
	}
	private void initEvent(){
		Button mAddBtn1 = (Button) findViewById(R.id.discovery_add);
		Button bt_fanhui=(Button) findViewById(R.id.discovery_fanhui);
		mAddBtn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				createDiary();
			}
		});
		//返回按钮
		bt_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			private String name;
			private String id1;

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, 
                    int position, long id) {
				// TODO Auto-generated method stub
				DiaryDbAdapter adapter = new DiaryDbAdapter(getApplicationContext());
			
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createDiary();
			return true;
		case DELETE_ID:
			
			renderListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createDiary() {
		Intent i = new Intent(this, DiaryEdit.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, DiaryEdit.class);
		i.putExtra(DiaryDbAdapter.KEY_ROWID, id);
		i.putExtra(DiaryDbAdapter.KEY_TITLE,
				c.getString(c.getColumnIndexOrThrow(DiaryDbAdapter.KEY_TITLE)));
		i.putExtra(DiaryDbAdapter.KEY_BODY,
				c.getString(c.getColumnIndexOrThrow(DiaryDbAdapter.KEY_BODY)));
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		renderListView();
	}
	private void showDeleteDialog(final String user, final int id) {
		new AlertDialog.Builder(EX056.this)
				.setTitle("应用提示")
				.setMessage("确定删除  " + user + "这条消息吗？\n")
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
