package com.example.sanstoi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanstoi.adapter.ScenicAdapter;
import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.connet.ConnectToWebService;
import com.example.sanstoi.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListScenicFragment extends Fragment {

    private ListView scenicListView;
    private List<ParentScenic> list = new ArrayList<>();

    private ConnectToWebService connectToService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_scenic, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        scenicListView = (ListView)view.findViewById(R.id.scenicListView);
        getMethod();

        scenicListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putParcelable("ScenicDetail", list.get(position));
                intent.setClass(getActivity().getApplication(),ScenicDetails.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    private void getMethod() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        try {
            //请求数据
            connectToService = new ConnectToWebService();
            JSONArray obj = connectToService.queryAllParentScenic();
            //解析数据
            parsingJson(obj);
        } catch(Exception e){
           Toast.makeText(getActivity(), R.string.ask_information_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void parsingJson(JSONArray obj) {
        try{
            for(int i = 0; i < obj.length(); i++){
                JSONObject jsonObject = (JSONObject)obj.get(i);
                ParentScenic parentScenic = new ParentScenic();
                parentScenic.setParentScenicStartTime(jsonObject.getString("parentScenicStartTime"));
                parentScenic.setParentScenicEndTime(jsonObject.getString("parentScenicEndTime"));
                parentScenic.setParentScenicOpenDate(jsonObject.getString("parentScenicOpenDate"));
                parentScenic.setParentScenicPrice(jsonObject.getDouble("parentScenicPrice"));
                parentScenic.setParentScenicBrief(jsonObject.getString("parentScenicBrief"));
                parentScenic.setParentScenicID(jsonObject.getInt("parentScenicID"));
                parentScenic.setParentScenicCity(jsonObject.getString("parentScenicCity"));
                parentScenic.setParentScenicProvince(jsonObject.getString("parentScenicProvince"));
                parentScenic.setParentScenicName(jsonObject.getString("parentScenicName"));
                parentScenic.setImage(jsonObject.getString("image"));
                parentScenic.setLine1(jsonObject.getString("line1"));
                parentScenic.setLine2(jsonObject.getString("line2"));
                parentScenic.setLine3(jsonObject.getString("line3"));
                list.add(parentScenic);
            }
            ScenicAdapter adapter = new ScenicAdapter(getActivity(),list);
            scenicListView.setAdapter(adapter);
        } catch(Exception e){
            Toast.makeText(getActivity(), R.string.analyze_information_error, Toast.LENGTH_SHORT).show();
        }
    }
}
