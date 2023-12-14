package com.example.euphoriamusicapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.ExploreAdapter;
import com.example.euphoriamusicapp.adapter.ForYouAdapter;
import com.example.euphoriamusicapp.adapter.HomeSliderAdapter;
import com.example.euphoriamusicapp.adapter.HotAlbumAdapter;
import com.example.euphoriamusicapp.adapter.NewReleaseAdapter;
import com.example.euphoriamusicapp.adapter.PodcastAdapter;
import com.example.euphoriamusicapp.adapter.RecentListenAdapter;
import com.example.euphoriamusicapp.adapter.TopicAndCategoryAdapter;
import com.example.euphoriamusicapp.adapter.TrendingArtistAdapter;
import com.example.euphoriamusicapp.data.HotAlbum;
import com.example.euphoriamusicapp.data.ImageOfMusic;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.NewReleaseMusic;
import com.example.euphoriamusicapp.data.TopicAndCategory;
import com.example.euphoriamusicapp.data.TrendingArtist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final long SONG = 1;
    public static final long PODCAST = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvRecentListen;
    private RecyclerView rvExplore;
    private RecyclerView rvForYou;
    private RecyclerView rvNewRelease;
    private RecyclerView rvPodcast;
    private RecyclerView rvTrendingArtist;
    private RecyclerView rvHotAlbum;
    private RecyclerView rvTopicCategory;
    private ViewPager2 vp2HomeSlider;

    private List<ImageOfMusic> homeSliderList;
    private Handler handler = new Handler();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private LinearLayout lLRecentListen;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (vp2HomeSlider.getCurrentItem() == homeSliderList.size() - 1) {
                vp2HomeSlider.setCurrentItem(0);
            } else {
                vp2HomeSlider.setCurrentItem(vp2HomeSlider.getCurrentItem() + 1);
            }
        }
    };
    FirebaseDatabase   firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvRecentListen = view.findViewById(R.id.rvRecentListen);
        vp2HomeSlider = view.findViewById(R.id.vp2HomeSlider);
        rvExplore = view.findViewById(R.id.rvExplore);
        rvForYou = view.findViewById(R.id.rvForYou);
        rvNewRelease = view.findViewById(R.id.rvNewRelease);
        rvPodcast = view.findViewById(R.id.rvPodcast);
        rvTrendingArtist = view.findViewById(R.id.rvTrendingArtist);
        rvHotAlbum = view.findViewById(R.id.rvHotAlbum);
        rvTopicCategory = view.findViewById(R.id.rvTopicCategory);
        lLRecentListen = view.findViewById(R.id.lLRecentListen);


        homeSliderList = getHomeSliderList();
        HomeSliderAdapter homeSliderAdapter = new HomeSliderAdapter(homeSliderList);
        vp2HomeSlider.setAdapter(homeSliderAdapter);
        vp2HomeSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Thực hiện truy vấn Firebase ở đây
            getRecentListenList();

            getExploreList();

            getForYouList();

            getNewReleaseList();

            getPodcastList();
        });



        LinearLayoutManager layoutManagerTrendingArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTrendingArtist.setLayoutManager(layoutManagerTrendingArtist);
        rvTrendingArtist.setHasFixedSize(true);
        rvTrendingArtist.setAdapter(new TrendingArtistAdapter(getTrendingArtistList()));

        LinearLayoutManager layoutManagerHotAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvHotAlbum.setLayoutManager(layoutManagerHotAlbum);
        rvHotAlbum.setHasFixedSize(true);
        rvHotAlbum.setAdapter(new HotAlbumAdapter(getHotAlbumList()));

        GridLayoutManager layoutManagerTopicCategory = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        rvTopicCategory.setLayoutManager(layoutManagerTopicCategory);
        rvTopicCategory.setHasFixedSize(true);
        rvTopicCategory.setAdapter(new TopicAndCategoryAdapter(getTopicCategoryList()));
        return view;
    }

    private List<ImageOfMusic> getHomeSliderList() {
        List<ImageOfMusic> list = new ArrayList<>();
        list.add(new ImageOfMusic(R.drawable.banner1_image));
        list.add(new ImageOfMusic(R.drawable.banner2_image));
        list.add(new ImageOfMusic(R.drawable.banner3_image));
        return list;
    }

    private void getRecentListenList() {
        LinearLayoutManager layoutManagerRecentListen = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvRecentListen.setLayoutManager(layoutManagerRecentListen);
        rvRecentListen.setHasFixedSize(true);
        List<MusicAndPodcast> listSong = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference();
        Query query = databaseReference.child("recent_music").child(user.getUid()).limitToLast(10);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!listSong.isEmpty()){
                    listSong.clear();
                }
                for (DataSnapshot data: snapshot.getChildren()
                ) {
                    MusicAndPodcast song_url = data.getValue(MusicAndPodcast.class);
                    song_url.setFeatured(true);
                    song_url.setLatest(true);
                    listSong.add(song_url);

                }

                if(listSong.isEmpty()){
                    lLRecentListen.setVisibility(View.GONE);
                }else{
                    listSong.get(0).setFeatured(false);
                    listSong.get(listSong.size() - 1).setLatest(false);
                    lLRecentListen.setVisibility(View.VISIBLE);
                    rvRecentListen.setAdapter(new RecentListenAdapter(getContext(),listSong));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void getExploreList() {
        List<MusicAndPodcast> list = new ArrayList<>();
        LinearLayoutManager layoutManagerExplore = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvExplore.setLayoutManager(layoutManagerExplore);
        rvExplore.setHasFixedSize(true);
        databaseReference = firebaseDatabase.getReference("songs");
        Query query =  databaseReference.orderByChild("count").startAt(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                for (DataSnapshot data: snapshot.getChildren()) {
                    MusicAndPodcast  song = data.getValue(MusicAndPodcast.class);
                    song.setFeatured(true);
                    song.setLatest(true);
                    list.add(song);
                };
                list.get(0).setFeatured(false);
                list.get(list.size() - 1).setLatest(false);
                rvExplore.setAdapter(new ExploreAdapter(getContext(),list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getForYouList() {
        List<MusicAndPodcast> list = new ArrayList<>();
        LinearLayoutManager layoutManagerForYou = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvForYou.setLayoutManager(layoutManagerForYou);
        rvForYou.setHasFixedSize(true);
        databaseReference = firebaseDatabase.getReference("songs");
        Query query =  databaseReference.limitToLast(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                for (DataSnapshot data: snapshot.getChildren()) {
                    MusicAndPodcast  song = data.getValue(MusicAndPodcast.class);
                    song.setFeatured(true);
                    song.setLatest(true);
                    list.add(song);
                }
                list.get(0).setFeatured(false);
                list.get(list.size() - 1).setLatest(false);
                rvForYou.setAdapter(new ForYouAdapter(getContext(),list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getNewReleaseList() {
        List<MusicAndPodcast> list = new ArrayList<>();
        GridLayoutManager layoutManagerNewRelease = new GridLayoutManager(getContext(), 3, LinearLayoutManager.HORIZONTAL, false);
        rvNewRelease.setLayoutManager(layoutManagerNewRelease);
        rvNewRelease.setHasFixedSize(true);
        databaseReference = firebaseDatabase.getReference("songs");
        Query query =  databaseReference.limitToLast(9);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                for (DataSnapshot data: snapshot.getChildren()) {
                    MusicAndPodcast  song = data.getValue(MusicAndPodcast.class);
                    song.setFeatured(true);
                    song.setLatest(true);
                    list.add(0,song);
                }
                list.get(0).setFeatured(false);
                list.get(list.size() - 1).setLatest(false);
                rvNewRelease.setAdapter(new NewReleaseAdapter(getContext(),list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getPodcastList() {
        LinearLayoutManager layoutManagerPodcast = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPodcast.setLayoutManager(layoutManagerPodcast);
        rvPodcast.setHasFixedSize(true);
        List<MusicAndPodcast> listPodcast = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("podcast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    MusicAndPodcast podcast = data.getValue(MusicAndPodcast.class);
                    if(podcast.getType() == PODCAST){
                        listPodcast.add(podcast);
                        podcast.setFeatured(true);
                        podcast.setLatest(true);
                    }
                }
                listPodcast.get(0).setFeatured(false);
                listPodcast.get(listPodcast.size() - 1).setLatest(false);
                rvPodcast.setAdapter(new PodcastAdapter(getContext(),listPodcast));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private List<TrendingArtist> getTrendingArtistList() {
        List<TrendingArtist> list = new ArrayList<>();
        list.add(new TrendingArtist(R.drawable.iu_image, "IU"));
        list.add(new TrendingArtist(R.drawable.taylor_swift_image, "Taylor Swift"));
        list.add(new TrendingArtist(R.drawable.bts_image, "BTS"));
        list.add(new TrendingArtist(R.drawable.hoa_minzy_image, "Hòa Minzy"));
        list.add(new TrendingArtist(R.drawable.westlife_image, "Westlife"));
        list.add(new TrendingArtist(R.drawable.blackpink_image, "BLACKPINK"));
        list.add(new TrendingArtist(R.drawable.amee_image, "Amee"));
        list.add(new TrendingArtist(R.drawable.ariana_grande_image, "Ariana Grande"));
        list.add(new TrendingArtist(R.drawable.adele_image, "Adele"));
        list.add(new TrendingArtist(R.drawable.nct_dream_image, "NCT Dream"));
        return list;
    }

    private List<HotAlbum> getHotAlbumList() {
        List<HotAlbum> list = new ArrayList<>();
        list.add(new HotAlbum(R.drawable.dangerous_woman_image, "Dangerous Woman", "Ariana Grande"));
        list.add(new HotAlbum(R.drawable.love_poem_image, "Love Poem", "IU"));
        list.add(new HotAlbum(R.drawable.born_pink_image, "BORN PINK", "BLACKPINK"));
        list.add(new HotAlbum(R.drawable.map_of_the_soul_7_image, "Map Of The Soul 7", "BTS"));
        list.add(new HotAlbum(R.drawable.spectrum_image, "Spectrum", "Westlife"));
        return list;
    }

    private List<TopicAndCategory> getTopicCategoryList() {
        List<TopicAndCategory> list = new ArrayList<>();
        list.add(new TopicAndCategory(R.drawable.lofi_image, "Lofi",1));
        list.add(new TopicAndCategory(R.drawable.hiphop_image, "Hip Hop",1));
        list.add(new TopicAndCategory(R.drawable.pop_image, "Pop",1));
        list.add(new TopicAndCategory(R.drawable.ballad_image, "Ballad",1));
        list.add(new TopicAndCategory(R.drawable.electronic_image, "Electronic",1));
        list.add(new TopicAndCategory(R.drawable.classical_music_image, "Classical Music",1));
        list.add(new TopicAndCategory(R.drawable.game_music_image, "Game Music",1));
        list.add(new TopicAndCategory(R.drawable.rock_image, "Rock",1));
        list.add(new TopicAndCategory(R.drawable.jazz_music, "Jazz",1));
        list.add(new TopicAndCategory(R.drawable.indie_music, "Indie",1));
        return list;
    }
}