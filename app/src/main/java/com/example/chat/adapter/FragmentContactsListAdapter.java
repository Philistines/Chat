package com.example.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.bean.User;
import com.hyphenate.chat.EMConversation;


import java.util.List;

public class FragmentContactsListAdapter extends BaseAdapter {
    private List<User> userList;
    private Context mContext;
    public FragmentContactsListAdapter(Context context, List<User> userList) {
        this.userList=userList;
        mContext=context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null==view){
            holder=new ViewHolder();
            view=View.inflate(mContext,R.layout.fragment_contacts_list_item,null);
            holder.ivAvatar= (ImageView) view.findViewById(R.id.iv_avatar);
            holder.tvNickname= (TextView) view.findViewById(R.id.tv_nickname);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        User user=userList.get(i);
        Glide.with(mContext)
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.ivAvatar);
        holder.tvNickname.setText(user.getNickname());

        return view;
    }

    class ViewHolder{
        ImageView ivAvatar;
        TextView tvNickname;
    }
}
