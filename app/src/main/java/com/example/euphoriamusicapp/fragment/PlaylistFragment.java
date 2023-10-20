package com.example.euphoriamusicapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.LibraryAdapter;
import com.example.euphoriamusicapp.adapter.ViewPagerPlaylistTabAdapter;
import com.example.euphoriamusicapp.data.Library;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView gvLibrary;
    private TabLayout tlPlaylist;
    private ViewPager vpPlaylist;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
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
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        gvLibrary = view.findViewById(R.id.gvLibrary);
        tlPlaylist = view.findViewById(R.id.tlPlaylist);
        vpPlaylist = view.findViewById(R.id.vpPlaylist);
        ViewPagerPlaylistTabAdapter viewPagerPlaylistTabAdapter = new ViewPagerPlaylistTabAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPlaylist.setAdapter(viewPagerPlaylistTabAdapter);
        tlPlaylist.setupWithViewPager(vpPlaylist);
        LibraryAdapter libraryAdapter = new LibraryAdapter(getLibraryList(), getContext());
        gvLibrary.setAdapter(libraryAdapter);
        return view;
    }

    private List<Library> getLibraryList() {
        List<Library> list = new ArrayList<>();
        list.add(new Library(R.drawable.musical_note_icon, "Bài hát", "100"));
        list.add(new Library(R.drawable.microphone_with_wire_icon, "Nghệ sĩ", null));
        list.add(new Library(R.drawable.download_icon, "Trên thiết bị", "130"));
        list.add(new Library(R.drawable.upload_icon, "Tải lên", null));
        return list;
    }
}