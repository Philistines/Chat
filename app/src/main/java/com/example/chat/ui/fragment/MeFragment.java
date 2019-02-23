package com.example.chat.ui.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.global.ActivityControler;
import com.example.chat.ui.activity.LoginActivity;
import com.example.chat.utils.ShareprefencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class MeFragment extends Fragment {


    private ImageView ivAvatar;
    private TextView tvNickname;
    private Button btnLogout;
    private ProgressDialog dialog;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_me, container, false);

        ivAvatar = (ImageView) layout.findViewById(R.id.iv_avatar);
        tvNickname = (TextView) layout.findViewById(R.id.tv_nickname);
        btnLogout = (Button) layout.findViewById(R.id.btn_logout);

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setUserInfo();
        setItemListener();

        return layout;
    }

    private void setItemListener() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        showDialog(true);
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                ShareprefencesUtils.saveAutoLogin(getActivity(),false);
                ActivityControler.finishAll();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                showDialog(false);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(getActivity(),"退出登录失败",Toast.LENGTH_SHORT).show();
                showDialog(false);
            }
        });
    }

    private void showDialog(final boolean show){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (show){
                    if (dialog==null){
                        dialog = ProgressDialog.show(getActivity(), null, "正在退出登录...");
                    }else{
                        dialog.show();
                    }
                }else{
                    if (null!=dialog){
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void setUserInfo() {

        String nickName;
        String avatar = null;

        String currentUser = EMClient.getInstance().getCurrentUser();
        nickName=currentUser;
        if (currentUser.equals("13141298979")){
            avatar="http://img2.woyaogexing.com/2019/02/21/ee42f06234eb43bbba854c7e7b56fd9b!400x400.jpeg";
        }else if (currentUser.equals("17310472324")){
            avatar="http://img2.woyaogexing.com/2019/02/20/1d9886cc318b499788180136784b04e6!400x400.jpeg";
        }else if (currentUser.equals("16619785294")){
            avatar="http://img2.woyaogexing.com/2019/02/21/70d790d6ad3a4971a72dbfefecae738c!400x400.jpeg";
        }

        Glide.with(getActivity())
                .load(avatar)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(ivAvatar);

        tvNickname.setText(nickName);
    }

}
