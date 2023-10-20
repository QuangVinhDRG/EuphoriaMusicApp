package com.example.euphoriamusicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.SpinnerData;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private List<SpinnerData> spinnerDataList;

    public SpinnerAdapter(List<SpinnerData> spinnerDataList) {
        this.spinnerDataList = spinnerDataList;
    }

    @Override
    public int getCount() {
        return spinnerDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        ImageView ivSpinnerItem = view.findViewById(R.id.ivSpinnerItem);
        TextView tvSpinnerItem = view.findViewById(R.id.tvSpinnerItem);
        SpinnerData spinnerData = spinnerDataList.get(position);
        ivSpinnerItem.setImageResource(spinnerData.getResourceId());
        tvSpinnerItem.setText(spinnerData.getItemName());
        return view;
    }
}
