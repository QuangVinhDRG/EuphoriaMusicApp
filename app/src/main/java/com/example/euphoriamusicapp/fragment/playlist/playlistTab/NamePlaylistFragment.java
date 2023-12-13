package com.example.euphoriamusicapp.fragment.playlist.playlistTab;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.Playlist;
import com.example.euphoriamusicapp.fragment.PlaylistFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private EditText etPlaylistName;

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
        etPlaylistName = view.findViewById(R.id.etPlaylistName);
        etPlaylistName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) etPlaylistName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etPlaylistName.getWindowToken(), 0);
                }
            }
        });


        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(NamePlaylistFragment.this).commit();
            }
        });
        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etPlaylistName.getText().toString().isEmpty()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("playlist");
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean checkname = true;
                            for (DataSnapshot data :snapshot.getChildren()
                                 ) {

                                String namePlay = data.getKey();
                                Log.d("namePlay", "onDataChange: "+ namePlay);
                                if(namePlay.equals(etPlaylistName.getText().toString())){
                                    checkname = false;
                                    break;
                                }
                            }
                            if(checkname) {
                                databaseReference.child(user.getUid()).child(etPlaylistName.getText().toString()).setValue("null");
                                InputMethodManager inputMethodManager = (InputMethodManager) btnAddPlaylist.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(btnAddPlaylist.getWindowToken(), 0);
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.namePlaylistFragmentLayout, new ManagePlaylistFragment(), "managePlaylistFragment");
                                fragmentTransaction.commit();
                            }else{
                                Toast.makeText(getContext(), "Tên playlist đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Vui lòng nhập tên Playlist", Toast.LENGTH_SHORT).show();
                }

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