package com.example.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chat.R;
import com.example.chat.adapter.FragmentContactsListAdapter;
import com.example.chat.bean.User;
import com.example.chat.global.Constants;
import com.example.chat.ui.activity.ChatActivity;
import com.hyphenate.chat.EMClient;


import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {


    private ListView lvContacts;
    private List<User> userList;

    public ContactsFragment() {
        // Required empty public constructor
        userList=new ArrayList<>();
        String currentUser = EMClient.getInstance().getCurrentUser();
        // TODO 写死了联系人,需要从自己服务器获取
        if (currentUser.equals("13141298979")){
            userList.add(new User("17310472324","物归原主","http://img2.woyaogexing.com/2019/02/20/1d9886cc318b499788180136784b04e6!400x400.jpeg"));
            userList.add(new User("16619785294","民祥志民","http://img2.woyaogexing.com/2019/02/21/70d790d6ad3a4971a72dbfefecae738c!400x400.jpeg"));
        }else if (currentUser.equals("17310472324")){
            userList.add(new User("13141298979","庸人自扰","http://img2.woyaogexing.com/2019/02/21/ee42f06234eb43bbba854c7e7b56fd9b!400x400.jpeg"));
            userList.add(new User("16619785294","民祥志民","http://img2.woyaogexing.com/2019/02/21/70d790d6ad3a4971a72dbfefecae738c!400x400.jpeg"));
        }else if (currentUser.equals("16619785294")){
            userList.add(new User("17310472324","物归原主","http://img2.woyaogexing.com/2019/02/20/1d9886cc318b499788180136784b04e6!400x400.jpeg"));
            userList.add(new User("13141298979","庸人自扰","https://img2.woyaogexing.com/2019/02/21/ee42f06234eb43bbba854c7e7b56fd9b!400x400.jpeg"));
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_contacts, container, false);
        lvContacts = (ListView) layout.findViewById(R.id.lv_contacts);

        FragmentContactsListAdapter adapter=new FragmentContactsListAdapter(getActivity(),userList);
        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener(new MyItemClickListener());

        return layout;
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (userList!=null){
                User user=userList.get(i);
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                if (TextUtils.isEmpty(user.getUserId())){
                    return;
                }
                intent.putExtra(Constants.USER_ID,user.getUserId());
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);
            }
        }
    }



}
