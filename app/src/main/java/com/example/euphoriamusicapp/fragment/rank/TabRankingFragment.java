package com.example.euphoriamusicapp.fragment.rank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.NewReleaseAdapter;
import com.example.euphoriamusicapp.adapter.RankingMusicAdapter;
import com.example.euphoriamusicapp.adapter.SpinnerAdapter;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.NewReleaseMusic;
import com.example.euphoriamusicapp.data.RankingMusic;
import com.example.euphoriamusicapp.data.SpinnerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabRankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabRankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lvRanking;
    private View view;
    private Spinner spnCategory, spnChair;

    public TabRankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabRankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabRankingFragment newInstance(String param1, String param2) {
        TabRankingFragment fragment = new TabRankingFragment();
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
        view = inflater.inflate(R.layout.fragment_tab_ranking, container, false);
        lvRanking = view.findViewById(R.id.lvRanking);
        spnCategory = view.findViewById(R.id.spnCategory);
        spnChair = view.findViewById(R.id.spnChair);
        getRankingMusicList();
        SpinnerAdapter spinnerAdapterCategory = new SpinnerAdapter(getCategorySpinnerDataList());
        SpinnerAdapter spinnerAdapterChair = new SpinnerAdapter(getChairSpinnerDataList());
        spnCategory.setAdapter(spinnerAdapterCategory);
        spnChair.setAdapter(spinnerAdapterChair);
        return view;
    }

    private void getRankingMusicList() {
        List<MusicAndPodcast> list = new ArrayList<>();
        FirebaseDatabase   firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("songs");
        Query query =  databaseReference.orderByChild("count");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                for (DataSnapshot data: snapshot.getChildren()) {
                    MusicAndPodcast song = data.getValue(MusicAndPodcast.class);
                    list.add(0,song);
                }
                RankingMusicAdapter rankingMusicAdapter = new RankingMusicAdapter(list);
                lvRanking.setAdapter(rankingMusicAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private List<SpinnerData> getCategorySpinnerDataList() {
        List<SpinnerData> list = new ArrayList<>();
        list.add(new SpinnerData(R.drawable.grid_icon, "All categories"));
        return list;
    }

    private List<SpinnerData> getChairSpinnerDataList() {
        List<SpinnerData> list = new ArrayList<>();
        list.add(new SpinnerData(R.drawable.grid_icon, "All chairs"));
        return list;
    }
}