package com.example.euphoriamusicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.Library;

import java.util.List;

public class LibraryAdapter extends BaseAdapter {
    private List<Library> libraryList;
    private Context context;

    public LibraryAdapter(List<Library> libraryList, Context context) {
        this.libraryList = libraryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return libraryList.size();
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.library_item, null);
        ImageView ivLibraryIcon = convertView.findViewById(R.id.ivLibraryIcon);
        TextView tvLibraryName = convertView.findViewById(R.id.tvLibraryName);
        TextView tvLibraryQuantity = convertView.findViewById(R.id.tvLibraryQuantity);
        Library library = libraryList.get(position);
        ivLibraryIcon.setImageResource(library.getResourceId());
        tvLibraryName.setText(library.getLibraryName());
        tvLibraryQuantity.setText(library.getLibraryQuantity());
        return convertView;
    }
}
