package com.cjy.hometalk.adapter;

import java.util.List;

import com.cjy.hometalk.R;
import com.cjy.hometalk.R.id;
import com.cjy.hometalk.R.layout;
import com.cjy.hometalk.model.ChatListData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	Context mContext;
	List<ChatListData> mListData;

	public ChatListAdapter(Context mContext, List<ChatListData> mListData) {
		super();
		this.mContext = mContext;
		this.mListData = mListData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View cView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		Holder holder;
		if (cView == null) {
			holder = new Holder();
			cView = LayoutInflater.from(mContext).inflate(
					R.layout.chat_listview_item, null);
			holder.rAvatar = (Button) cView
					.findViewById(R.id.listview_item_receive_avatar);
			holder.rContent = (TextView) cView
					.findViewById(R.id.listview_item_receive_content);
			holder.chatTime = (TextView) cView
					.findViewById(R.id.listview_item_time);
			holder.sContent = (TextView) cView
					.findViewById(R.id.listview_item_send_content);
			holder.sAvatar = (Button) cView
					.findViewById(R.id.listview_item_send_avatar);
			cView.setTag(holder);

		} else {
			holder = (Holder) cView.getTag();
		}
		
		holder.chatTime.setVisibility(View.GONE);

		if (mListData.get(index).getType() == 2) {
			holder.rAvatar.setVisibility(View.VISIBLE);
			holder.rContent.setVisibility(View.VISIBLE);
			holder.sContent.setVisibility(View.GONE);
			holder.sAvatar.setVisibility(View.GONE);
		} else if (mListData.get(index).getType() == 1) {
			holder.rAvatar.setVisibility(View.GONE);
			holder.rContent.setVisibility(View.GONE);
			holder.sContent.setVisibility(View.VISIBLE);
			holder.sAvatar.setVisibility(View.VISIBLE);
		}
		holder.chatTime.setText(mListData.get(index).getChatTime());
		holder.rContent.setText(mListData.get(index).getReceiveContent());
		holder.sContent.setText(mListData.get(index).getSendContent());
		
		return cView;
	}

	class Holder {
		Button rAvatar;
		TextView rContent;
		TextView chatTime;
		TextView sContent;
		Button sAvatar;
	}
}
