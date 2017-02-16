package com.onlineexamination.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.PersonItemBean;

import java.util.List;

/**
 * Created by 庞品 on 2017/2/11.
 */

public class PersonAdapter extends ArrayAdapter<PersonItemBean> {
    private List<PersonItemBean> list;
    private Context context;

    public PersonAdapter(Context context, int resource, List<PersonItemBean> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_person, null);
        ImageView itemIcon = (ImageView) convertView.findViewById(R.id.item_img);
        TextView itemText = (TextView) convertView.findViewById(R.id.item_text);
        itemIcon.setImageResource(list.get(position).getItemImgId());
        itemText.setText(list.get(position).getItemName());
        return convertView;
    }
}
