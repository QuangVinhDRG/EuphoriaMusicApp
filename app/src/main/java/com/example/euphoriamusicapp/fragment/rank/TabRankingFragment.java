package com.example.euphoriamusicapp.fragment.rank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.RankingMusicAdapter;
import com.example.euphoriamusicapp.adapter.SpinnerAdapter;
import com.example.euphoriamusicapp.data.RankingMusic;
import com.example.euphoriamusicapp.data.SpinnerData;

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
        RankingMusicAdapter rankingMusicAdapter = new RankingMusicAdapter(getRankingMusicList());
        lvRanking.setAdapter(rankingMusicAdapter);
        SpinnerAdapter spinnerAdapterCategory = new SpinnerAdapter(getCategorySpinnerDataList());
        SpinnerAdapter spinnerAdapterChair = new SpinnerAdapter(getChairSpinnerDataList());
        spnCategory.setAdapter(spinnerAdapterCategory);
        spnChair.setAdapter(spinnerAdapterChair);
        return view;
    }

    private List<RankingMusic> getRankingMusicList() {
        List<RankingMusic> list = new ArrayList<>();
        list.add(new RankingMusic(R.drawable.dai_minh_tinh_image, 0, "Đại Minh Tinh", "Văn Mai Hương, Hứa Kim Tuyền"));
        list.add(new RankingMusic(R.drawable.cat_doi_noi_sau_image, 1, "Cắt Đôi Nỗi Sầu", "Tăng Duy Tân, Drum7"));
        list.add(new RankingMusic(R.drawable.tat_ca_hoac_khong_la_gi_ca_image, 2, "Tất Cả Hoặc Không Là Gì Cả", "Cao Thái Sơn, Đông Thiên Đức"));
        list.add(new RankingMusic(R.drawable.le_luu_ly_image, 3, "Lệ Lưu Ly", "Vũ Phụng Tiên, DT Tập Rap"));
        list.add(new RankingMusic(R.drawable.sao_troi_lam_gio_image, 4, "Sao Trời Làm Gió", "Nal, CT"));
        list.add(new RankingMusic(R.drawable.anh_dau_muon_thay_em_buon_image, 5, "Anh Đâu Muốn Thấy Em Buồn", "Châu Khải Phong, ACV"));
        list.add(new RankingMusic(R.drawable.can_tinh_nhu_the_image, 6, "Cạn Tình Như Thế", "DICKSON, Thành Đạt, Lê Chí Trung"));
        return list;
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