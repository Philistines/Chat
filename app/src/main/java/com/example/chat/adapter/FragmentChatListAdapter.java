package com.example.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.utils.MessageUtils;
import com.example.chat.utils.SpannableStringUtil;
import com.example.chat.utils.VUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class FragmentChatListAdapter extends BaseAdapter {
    private List<EMMessage> messageList;
    private Context mContext;
    private EMMessage emMessage;
    private HandleMessageInterface handleMessageInterface;

    public interface HandleMessageInterface{
        void onLongClick(int position);
        void onClick(int position);
    }

    public FragmentChatListAdapter(Context context, List<EMMessage> messageList,HandleMessageInterface handleMessageInterface) {
        this.messageList = messageList;
        mContext = context;
        this.handleMessageInterface=handleMessageInterface;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
//        ViewHolder holder;
        emMessage = messageList.get(i);
//        if (null==view){
//            holder=new ViewHolder();
//        view = View.inflate(mContext, emMessage.direct() == EMMessage.Direct.SEND ? R.layout.message_text_sent : R.layout.message_text_received, null);
//            holder.ivAvatar= (ImageView) view.findViewById(R.id.iv_avatar);
//            holder.tvContent= (TextView) view.findViewById(R.id.tv_content);
//            view.setTag(holder);
//        }else{
//            holder= (ViewHolder) view.getTag();
//        }
        if (emMessage.getType()== EMMessage.Type.TXT){
            view = View.inflate(mContext, emMessage.direct() == EMMessage.Direct.SEND ? R.layout.message_text_sent : R.layout.message_text_received, null);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            String content=MessageUtils.getMessageDigest(emMessage, mContext);
            tvContent.setText(SpannableStringUtil.getContent(mContext,tvContent,content));
        }else if (emMessage.getType()== EMMessage.Type.IMAGE){
            view = View.inflate(mContext, emMessage.direct() == EMMessage.Direct.SEND ? R.layout.message_image_sent : R.layout.message_image_received, null);
            ImageView ivImage= (ImageView) view.findViewById(R.id.iv_image);
            EMImageMessageBody body = (EMImageMessageBody) emMessage.getBody();
            Glide.with(mContext)
                    .load(emMessage.getFrom().equals(EMClient.getInstance().getCurrentUser())?body.getLocalUrl():body.getRemoteUrl())
                    .placeholder(R.drawable.default_image)
                    .centerCrop()
                    .override(VUtils.dip2px(mContext,160),VUtils.dip2px(mContext,160))
                    .into(ivImage);
        }
        ImageView ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);

        //处理消息的状态和显示
        handleMessageStatus(view);

        //TODO 写死了头像
        if (emMessage.getFrom().equals("13141298979")) {
            Glide.with(mContext)
                    .load("http://img2.woyaogexing.com/2019/02/21/ee42f06234eb43bbba854c7e7b56fd9b!400x400.jpeg")
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(ivAvatar);
        } else if (emMessage.getFrom().equals("17310472324")) {
            Glide.with(mContext)
                    .load("http://img2.woyaogexing.com/2019/02/20/1d9886cc318b499788180136784b04e6!400x400.jpeg")
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(ivAvatar);
        } else if (emMessage.getFrom().equals("16619785294")) {
            Glide.with(mContext)
                    .load("http://img2.woyaogexing.com/2019/02/21/70d790d6ad3a4971a72dbfefecae738c!400x400.jpeg")
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(ivAvatar);
        }

        RelativeLayout rlMessage= (RelativeLayout) view.findViewById(R.id.rl_message);
        rlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (handleMessageInterface!=null){
                    handleMessageInterface.onClick(i);
                }
            }
        });
        rlMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (handleMessageInterface!=null){
                    handleMessageInterface.onLongClick(i);
                }
                return true;
            }
        });

        return view;
    }

    private void handleMessageStatus(View view) {
        if (emMessage.direct()== EMMessage.Direct.SEND){
            ImageButton ibSendStatus= (ImageButton) view.findViewById(R.id.ib_send_status);
            ProgressBar pbMsgSend= (ProgressBar) view.findViewById(R.id.pb_msg_send);
            if (emMessage.status()== EMMessage.Status.SUCCESS){
                ibSendStatus.setVisibility(View.INVISIBLE);
                pbMsgSend.setVisibility(View.INVISIBLE);
            }else if (emMessage.status()== EMMessage.Status.FAIL){
                ibSendStatus.setVisibility(View.INVISIBLE);
                pbMsgSend.setVisibility(View.INVISIBLE);
            }else if (emMessage.status()== EMMessage.Status.INPROGRESS||emMessage.status()== EMMessage.Status.CREATE){
                ibSendStatus.setVisibility(View.INVISIBLE);
                pbMsgSend.setVisibility(View.VISIBLE);
            }

            ibSendStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    class ViewHolder {
        ImageView ivAvatar;
        TextView tvContent;
    }
}