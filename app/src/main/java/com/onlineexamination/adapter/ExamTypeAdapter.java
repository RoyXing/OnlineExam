package com.onlineexamination.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.ExamTypeBean;

import java.util.List;

/**
 * Created by 庞品 on 2017/3/4.
 */

public class ExamTypeAdapter extends ArrayAdapter<ExamTypeBean> {
    List<ExamTypeBean> list;
    Context context;

    public ExamTypeAdapter(Context context, int resource, List<ExamTypeBean> objects) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_exam,null);
        TextView name=(TextView) convertView.findViewById(R.id.text_exam_type);
        name.setText(list.get(position).getName());
        return convertView;
    }
}
