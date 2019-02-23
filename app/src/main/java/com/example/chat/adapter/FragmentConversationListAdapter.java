package com.example.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.utils.MessageUtils;
import com.example.chat.utils.SpannableStringUtil;
import com.example.chat.utils.TimeFormatUtils;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;


import java.util.List;

public class FragmentConversationListAdapter extends BaseAdapter {
    private List<EMConversation> conversationList;
    private Context mContext;
    public FragmentConversationListAdapter(Context context, List<EMConversation> conversationList) {
        this.conversationList=conversationList;
        mContext=context;
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public Object getItem(int i) {
        return conversationList.get(i);
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
            view=View.inflate(mContext, R.layout.fragment_conversation_list_item,null);
            holder.ivAvatar= (ImageView) view.findViewById(R.id.iv_avatar);
            holder.tvNickname= (TextView) view.findViewById(R.id.tv_nickname);
            holder.tvTime= (TextView) view.findViewById(R.id.tv_time);
            holder.tvContent= (TextView) view.findViewById(R.id.tv_content);
            holder.tvUnread= (TextView) view.findViewById(R.id.tv_unread);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }

        EMMessage lastMessage=conversationList.get(i).getLastMessage();
        EMConversation emConversation = conversationList.get(i);

        int unreadMsg=emConversation.getUnreadMsgCount();
        long time=lastMessage.getMsgTime();
        String strTime = TimeFormatUtils.getStrTime(String.valueOf(time));
        String replaceAll = strTime.replaceAll("年", "/").replaceAll("月", "/").replaceAll("日", "");
        String url = null;
        //TODO 写死头像
        if (emConversation.getUserName().equals("13141298979")){
            url="http://img2.woyaogexing.com/2019/02/21/ee42f06234eb43bbba854c7e7b56fd9b!400x400.jpeg";
        }else if (emConversation.getUserName().equals("17310472324")){
            url="http://img2.woyaogexing.com/2019/02/20/1d9886cc318b499788180136784b04e6!400x400.jpeg";
        }else if (emConversation.getUserName().equals("16619785294")){
            url="http://img2.woyaogexing.com/2019/02/21/70d790d6ad3a4971a72dbfefecae738c!400x400.jpeg";
        }
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.ivAvatar);

        holder.tvNickname.setText(emConversation.getUserName());
        holder.tvTime.setText(replaceAll);
        String content=MessageUtils.getMessageDigest(lastMessage,mContext);
        holder.tvContent.setText(SpannableStringUtil.getContent(mContext,holder.tvContent,content));

        if (unreadMsg!=0){
            holder.tvUnread.setText(unreadMsg+"");
            holder.tvUnread.setVisibility(View.VISIBLE);
        }else{
            holder.tvUnread.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    class ViewHolder{
        ImageView ivAvatar;
        TextView tvNickname;
        TextView tvTime;
        TextView tvContent;
        TextView tvUnread;
    }
}