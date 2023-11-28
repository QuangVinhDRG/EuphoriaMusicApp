package com.example.euphoriamusicapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.MainActivity;
import com.example.euphoriamusicapp.MainAppActivity;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.WelcomeActivity;
import com.example.euphoriamusicapp.fragment.account.MemberListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private LinearLayout llLogout, llAboutUs;
    private ImageView ivLogout, ivAboutUs;
    private TextView tvLogout, tvAboutUs;
    private ImageView imgprofile;
    private TextView username;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        llLogout = view.findViewById(R.id.llLogout);
        tvLogout = view.findViewById(R.id.tvLogout);
        ivLogout = view.findViewById(R.id.ivLogout);
        llAboutUs = view.findViewById(R.id.llAboutUs);
        ivAboutUs = view.findViewById(R.id.ivAboutUs);
        tvAboutUs = view.findViewById(R.id.tvAboutUs);
        username = view.findViewById(R.id.edtUsername);
        imgprofile = view.findViewById(R.id.imgprofile);
        ShowInfor();
        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.rlAccountFragmentLayout, new MemberListFragment(), "memberlistFragment");
                fragmentTransaction.commit();
            }
        });
        tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.rlAccountFragmentLayout, new MemberListFragment(), "memberlistFragment");
                fragmentTransaction.commit();
            }
        });
        ivAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.rlAccountFragmentLayout, new MemberListFragment(), "memberlistFragment");
                fragmentTransaction.commit();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying())
                {
                    PlayMusicActivity.mediaPlayer.stop();
                }
                mAuth.signOut();
                Intent intent = new Intent();
                intent.setClass(getActivity(),MainActivity.class);
                getActivity().startActivity(intent);

            }
        });
        return view;
    }

    private void ShowInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();

            username.setText(user.getDisplayName());
            Glide.with(this).load(photoUrl).error(R.drawable.ic_app_background).into(imgprofile);
        }
    }


}