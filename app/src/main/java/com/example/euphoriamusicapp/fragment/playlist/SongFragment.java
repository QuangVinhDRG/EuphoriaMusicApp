package com.example.euphoriamusicapp.fragment.playlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.FavouriteSongAdapter;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.RankingMusic;

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
        tvFavouriteNumberOfSong = view.findViewById(R.id.tvFavouriteNumberOfSong);
        ibBack = view.findViewById(R.id.ibBack);
        FavouriteSongAdapter favouriteSongAdapter = new FavouriteSongAdapter(getFavouriteMusicList());
        tvFavouriteNumberOfSong.setText(String.valueOf(favouriteSongAdapter.getCount()));
        lvFavouriteSong.setAdapter(favouriteSongAdapter);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(SongFragment.this).commit();
            }
        });
        return view;
    }

    public List<BasicMusicInformation> getFavouriteMusicList() {
        List<BasicMusicInformation> list = new ArrayList<>();
        list.add(new BasicMusicInformation(R.drawable.cruel_summer_image,"Cruel Summer", "Taylor Swift"));
        list.add(new BasicMusicInformation(R.drawable.cat_doi_noi_sau_image, "Cắt Đôi Nỗi Sầu", "Tăng Duy Tân, Drum7"));
        list.add(new BasicMusicInformation(R.drawable.tat_ca_hoac_khong_la_gi_ca_image,  "Tất Cả Hoặc Không Là Gì Cả", "Cao Thái Sơn, Đông Thiên Đức"));
        list.add(new BasicMusicInformation(R.drawable.le_luu_ly_image, "Lệ Lưu Ly", "Vũ Phụng Tiên, DT Tập Rap"));
        list.add(new BasicMusicInformation(R.drawable.sao_troi_lam_gio_image,  "Sao Trời Làm Gió", "Nal, CT"));
        list.add(new BasicMusicInformation(R.drawable.anh_dau_muon_thay_em_buon_image,  "Anh Đâu Muốn Thấy Em Buồn", "Châu Khải Phong, ACV"));
        list.add(new BasicMusicInformation(R.drawable.can_tinh_nhu_the_image,  "Cạn Tình Như Thế", "DICKSON, Thành Đạt, Lê Chí Trung"));
        list.add(new BasicMusicInformation(R.drawable.dai_minh_tinh_image,"Đại Minh Tinh", "Văn Mai Hương, Hứa Kim Tuyền"));
        list.add(new BasicMusicInformation(R.drawable.ngay_mai_nguoi_ta_lay_chong_image, "Ngày Mai Người Ta Lấy Chồng", "Thành Đạt"));
        return list;
    }
}