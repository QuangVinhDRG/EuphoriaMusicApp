package com.example.euphoriamusicapp.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.euphoriamusicapp.MainActivity;
import com.example.euphoriamusicapp.MainAppActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.LibraryAdapter;
import com.example.euphoriamusicapp.adapter.ViewPagerPlaylistTabAdapter;
import com.example.euphoriamusicapp.data.Library;
import com.example.euphoriamusicapp.fragment.playlist.SongFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private ImageButton ibAccount;
    private EditText etPlaylistSearch;

    public PlaylistFragment() {
        // Required empty public constructor
    }

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
        etPlaylistSearch = view.findViewById(R.id.etPlaylistSearch);
        ViewPagerPlaylistTabAdapter viewPagerPlaylistTabAdapter = new ViewPagerPlaylistTabAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpPlaylist.setAdapter(viewPagerPlaylistTabAdapter);
        tlPlaylist.setupWithViewPager(vpPlaylist);
        LibraryAdapter libraryAdapter = new LibraryAdapter(getLibraryList(), getContext());
        gvLibrary.setAdapter(libraryAdapter);
        gvLibrary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.playlistFragmentLayout, new SongFragment(), "songFragment");
                    fragmentTransaction.commit();
                } else if (position == 2) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PlayMusicOfflineActivity.class);
                    getActivity().startActivity(intent);

                }
            }
        });

        etPlaylistSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) etPlaylistSearch.getContext().getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etPlaylistSearch.getWindowToken(), 0);
                }
            }
        });

//        ibAccount = view.findViewById(R.id.ibAccount);
//        ViewGroup viewGroup = view.getRootView().findViewById(R.id.menuPlaylist);
//        ViewPager2 viewPager2 = this.getLayoutInflater().inflate(R.layout.activity_main_app, viewGroup).findViewById(R.id.viewPagerMain);
//        ibAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager2.setCurrentItem(4, false);
//                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    private List<Library> getLibraryList() {
        SongFragment songFragment = new SongFragment();
        List<Library> list = new ArrayList<>();
        list.add(new Library(R.drawable.musical_note_icon, "Bài hát", String.valueOf(songFragment.getFavouriteMusicList().size())));
        list.add(new Library(R.drawable.microphone_with_wire_icon, "Nghệ sĩ", null));
        list.add(new Library(R.drawable.download_icon, "Trên thiết bị", "130"));
        list.add(new Library(R.drawable.upload_icon, "Tải lên", null));
        return list;
    }
}