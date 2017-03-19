package com.onlineexamination.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.ExamTestNameBean;

import java.util.List;

/**
 * Created by 庞品 on 2017/3/4.
 */

public class ExamTestNameAdapter extends ArrayAdapter<ExamTestNameBean> {
    private Context context;
    private List<ExamTestNameBean> list;

    public ExamTestNameAdapter(Context context, int resource, List<ExamTestNameBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_exam_list,null);
        TextView name=(TextView) convertView.findViewById(R.id.text_name);
        TextView score=(TextView) convertView.findViewById(R.id.text_score);
        name.setText(list.get(position).getName());
        score.setText("分数："+list.get(position).getGrade());
        return convertView;
    }
}
