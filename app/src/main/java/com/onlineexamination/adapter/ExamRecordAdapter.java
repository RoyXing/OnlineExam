package com.onlineexamination.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.ExamRecordBean;
import com.onlineexamination.bean.ExamTypeBean;

import java.util.List;

/**
 * Created by 庞品 on 2017/3/4.
 */

public class ExamRecordAdapter extends ArrayAdapter<ExamRecordBean> {
    List<ExamRecordBean> list;
    Context context;

    public ExamRecordAdapter(Context context, int resource, List<ExamRecordBean> objects) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_exam_record, null);
        TextView name = (TextView) convertView.findViewById(R.id.text_exam_type);
        TextView score = (TextView) convertView.findViewById(R.id.text_exam_result);
        try {
            name.setText(list.get(position).getPaper_name());
            score.setText("考试成绩：" + list.get(position).getScore());
        } catch (Exception e) {
        }

        return convertView;
    }
}
