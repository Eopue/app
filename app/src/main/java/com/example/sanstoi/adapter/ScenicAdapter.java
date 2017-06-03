package com.example.sanstoi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.myapplication.R;
import com.example.sanstoi.util.PicassoUtil;

import java.util.List;

/**
 * Created by Sans toi on 2017/4/14.
 */

public class ScenicAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ParentScenic> mList;
    private ParentScenic parentScenic;
    private PicassoUtil picassoUtil = new PicassoUtil();

    public ScenicAdapter(Context mContext, List<ParentScenic> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.scenic_item, null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_name = (TextView)  convertView.findViewById(R.id.tv_name);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tv_province = (TextView) convertView.findViewById(R.id.tv_province);
            viewHolder.tv_opentime = (TextView) convertView.findViewById(R.id.tv_opentime);
            viewHolder.tv_starttime = (TextView) convertView.findViewById(R.id.tv_starttime);
            viewHolder.tv_endtime = (TextView) convertView.findViewById(R.id.tv_endtime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        parentScenic = mList.get(position);
        viewHolder.tv_name.setText(parentScenic.getParentScenicName());
        viewHolder.tv_money.setText(String.valueOf(parentScenic.getParentScenicPrice()));
        viewHolder.tv_province.setText(parentScenic.getParentScenicProvince()+parentScenic.getParentScenicCity());
        viewHolder.tv_opentime.setText(parentScenic.getParentScenicOpenDate());
        viewHolder.tv_starttime.setText(parentScenic.getParentScenicStartTime());
        viewHolder.tv_endtime.setText(parentScenic.getParentScenicEndTime());
        picassoUtil.loadImageView( mContext , parentScenic.getImage(), 400, 300, viewHolder.iv_img);

        return convertView;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_name;
        private TextView tv_money;
        private TextView tv_opentime;
        private TextView tv_starttime;
        private TextView tv_endtime;
        private TextView tv_province;
    }
}
