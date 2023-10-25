package com.example.euphoriamusicapp.fragment.playlist.playlistTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.fragment.PlaylistFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NamePlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NamePlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ImageButton ibBack;
    private Button btnCancelPlaylist, btnAddPlaylist;

    public NamePlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NamePlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NamePlaylistFragment newInstance(String param1, String param2) {
        NamePlaylistFragment fragment = new NamePlaylistFragment();
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
        view = inflater.inflate(R.layout.fragment_name_playlist, container, false);
        ibBack = view.findViewById(R.id.ibBack);
        btnCancelPlaylist = view.findViewById(R.id.btnCancelPlaylist);
        btnAddPlaylist = view.findViewById(R.id.btnAddPlaylist);

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(NamePlaylistFragment.this).commit();
            }
        });
        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.namePlaylistFragmentLayout, new ManagePlaylistFragment(), "managePlaylistFragment");
                fragmentTransaction.commit();
            }
        });
        btnCancelPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(NamePlaylistFragment.this).commit();
            }
        });
        return view;
    }
}