package com.example.euphoriamusicapp.fragment.playlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.PlaylistTabAdapter;
import com.example.euphoriamusicapp.data.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabPlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabPlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lvPlaylist;
    private View view;

    public TabPlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabPlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabPlaylistFragment newInstance(String param1, String param2) {
        TabPlaylistFragment fragment = new TabPlaylistFragment();
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
        view = inflater.inflate(R.layout.fragment_tab_playlist, container, false);
        lvPlaylist = view.findViewById(R.id.lvPlaylist);
        lvPlaylist.setAdapter(new PlaylistTabAdapter(getPlaylists()));
        return view;
    }

    private List<Playlist> getPlaylists() {
        List<Playlist> list = new ArrayList<>();
        list.add(new Playlist(R.drawable.playlist_1, "Top bài hát hay nhất của Văn Mai Hương", "Văn Mai Hương"));
        return list;
    }
}