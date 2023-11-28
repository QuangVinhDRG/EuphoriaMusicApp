package com.example.euphoriamusicapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.RecentSearchAdapter;
import com.example.euphoriamusicapp.data.RecentSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lvRecentSearch;
    private View view;
    private EditText etSearch;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        lvRecentSearch = view.findViewById(R.id.lvRecentSearch);
        RecentSearchAdapter recentSearchAdapter = new RecentSearchAdapter(getRecentSearchList());
        lvRecentSearch.setAdapter(recentSearchAdapter);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                }
            }
        });
        return view;
    }

    private List<RecentSearch> getRecentSearchList() {
        List<RecentSearch> list = new ArrayList<>();
//        list.add(new RecentSearch(R.drawable.cruel_summer_image, "Cruel Summer", "Bài hát", getString(R.string.middleDot) + "Taylor Swift"));
//        list.add(new RecentSearch(R.drawable.chung_ta_cua_hien_tai_image, "Chúng ta của hiện tại", "Bài hát", getString(R.string.middleDot) + "Sơn Tùng MTP"));
//        list.add(new RecentSearch(R.drawable.thuan_podcast_image, "Thuần", "Podcast", getString(R.string.middleDot) + "Thuần Podcast"));
//        list.add(new RecentSearch(R.drawable.a_loi_image, "À lôi", "Bài hát", getString(R.string.middleDot) + "Double2T, Masew"));
//        list.add(new RecentSearch(R.drawable.ariana_grande_image, "Ariana Grande", "Nghệ sĩ", null));
//        list.add(new RecentSearch(R.drawable.taylor_swift_image, "Taylor Swift", "Nghệ sĩ", null));
        return list;
    }
}