package com.example.euphoriamusicapp.fragment.playlist.playlistTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.AddedPlaylistSongAdapter;
import com.example.euphoriamusicapp.data.MusicAndPodcast;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagePlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagePlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ListView lvPlaylistRecommendSong, lvAddedPlaylistSong;
    private ImageButton ibBack;
    private TextView tvPlaylistNumberOfSong;
    private Button btnRandomPlay;
    private AddedPlaylistSongAdapter addedPlaylistSongAdapter;
    private List<MusicAndPodcast> addedPlaylistSong = new ArrayList<>();

    public ManagePlaylistFragment() {
        // Required empty public constructor
    }

    public List<MusicAndPodcast> getAddedPlaylistSong() {
        return addedPlaylistSong;
    }

    public void setAddedPlaylistSong(List<MusicAndPodcast> addedPlaylistSong) {
        this.addedPlaylistSong = addedPlaylistSong;
    }

    public AddedPlaylistSongAdapter getAddedPlaylistSongAdapter() {
        return addedPlaylistSongAdapter;
    }

    public void setAddedPlaylistSongAdapter(AddedPlaylistSongAdapter addedPlaylistSongAdapter) {
        this.addedPlaylistSongAdapter = addedPlaylistSongAdapter;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManagePlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagePlaylistFragment newInstance(String param1, String param2) {
        ManagePlaylistFragment fragment = new ManagePlaylistFragment();
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
        view = inflater.inflate(R.layout.fragment_manage_playlist, container, false);
        lvAddedPlaylistSong = view.findViewById(R.id.lvAddedPlaylistSong);
        ibBack = view.findViewById(R.id.ibBack);
        btnRandomPlay = view.findViewById(R.id.btnRandomPlay);
        tvPlaylistNumberOfSong = view.findViewById(R.id.tvPlaylistNumberOfSong);

        addedPlaylistSongAdapter = new AddedPlaylistSongAdapter(getPlaylistSong());
        lvAddedPlaylistSong.setAdapter(addedPlaylistSongAdapter);

        tvPlaylistNumberOfSong.setText(String.valueOf(getPlaylistSong().size()));
        if (Integer.parseInt(tvPlaylistNumberOfSong.getText().toString()) > 0) {
            btnRandomPlay.setBackgroundResource(R.drawable.random_play_background);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment namePlaylistFragment = getActivity().getSupportFragmentManager().findFragmentByTag("namePlaylistFragment");
                fragmentTransaction.remove(ManagePlaylistFragment.this);
                fragmentTransaction.remove(namePlaylistFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private List<MusicAndPodcast> getPlaylistSong() {
        List<MusicAndPodcast> list = new ArrayList<>();
//        list.add(new BasicMusicInformation(R.drawable.cruel_summer_image, "Cruel Summer", "Taylor Swift"));
//        list.add(new BasicMusicInformation(R.drawable.chung_ta_cua_hien_tai_image, "Chúng ta của hiện tại", "Sơn Tùng MTP"));
//        list.add(new BasicMusicInformation(R.drawable.khoc_o_trong_club_image, "Khóc ở trong club", "Hiền Hồ"));
//        list.add(new BasicMusicInformation(R.drawable.kill_this_love_image, "Kill This Love", "BLACKPINK"));
//        list.add(new BasicMusicInformation(R.drawable.neu_luc_do_image, "Nếu lúc đó", "tlinh, 2pillz"));
//        list.add(new BasicMusicInformation(R.drawable.vai_cau_noi_co_khien_nguoi_thay_doi_image, "Vài câu nói có...", "GreyD, tlinh"));
        return list;
    }
}