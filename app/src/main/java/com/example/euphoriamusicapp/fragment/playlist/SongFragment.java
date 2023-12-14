package com.example.euphoriamusicapp.fragment.playlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.FavouriteSongAdapter;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.RankingMusic;
import com.example.euphoriamusicapp.fragment.PlaylistFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView tvFavouriteNumberOfSong;
    private ListView lvFavouriteSong;
    private ImageButton ibBack;
    RelativeLayout rlFov;

    public SongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongFragment newInstance(String param1, String param2) {
        SongFragment fragment = new SongFragment();
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
        view = inflater.inflate(R.layout.fragment_song, container, false);
        lvFavouriteSong = view.findViewById(R.id.lvFavouriteSong);
        rlFov = view.findViewById(R.id.rlFov);
        tvFavouriteNumberOfSong = view.findViewById(R.id.tvFavouriteNumberOfSong);
        ibBack = view.findViewById(R.id.ibBack);
        FavouriteSongAdapter favouriteSongAdapter = new FavouriteSongAdapter(getFavouriteMusicList(),getContext());
        tvFavouriteNumberOfSong.setText(String.valueOf(favouriteSongAdapter.getCount()));
        lvFavouriteSong.setAdapter(favouriteSongAdapter);
        getMusicFiles();
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(SongFragment.this).commit();
            }
        });
        return view;
    }

    public List<MusicAndPodcast> getFavouriteMusicList() {
        File[] listfile = getMusicFiles();
        List<MusicAndPodcast> list = new ArrayList<>();
        for ( File file: listfile
        ) {
            MusicAndPodcast p1 = new MusicAndPodcast() ;

            if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                String[] chuoi = file.getName().split("\\+");
                String songname ="";
                String songauthor="";
                if (chuoi.length >= 2) {
                    songname = chuoi[0];
                    songauthor = chuoi[1];
                    songauthor = songauthor.replace(".mp3","");
                }
                p1.setUrl(file.getAbsolutePath());
                p1.setAuthorName(songauthor);
                p1.setSongName(songname);
                p1.setResourceId(R.drawable.imgsong);
                p1.setFeatured(true);
                p1.setLatest(true);
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(file.getAbsolutePath());
                byte[] imageData = retriever.getEmbeddedPicture();

                if (imageData != null) {
                    // Chuyển đổi dữ liệu hình ảnh thành Bitmap và hiển thị nó trong ImageView
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    p1.setImage(bitmapToString(bitmap));
                }
                list.add(p1);
            }

        }
        list.get(0).setFeatured(false);
        list.get(list.size() - 1).setLatest(false);
        return list;
    }
    private static File[] getMusicFiles() {
        File musicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

        // Kiểm tra xem thư mục có tồn tại không
        if (musicDirectory.exists() && musicDirectory.isDirectory()) {
            return musicDirectory.listFiles();
        } else {
            return null;
        }
    }
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }



}
