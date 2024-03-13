package tiendhph30203.poly.AndroidNetworkingNew;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ManHinhChinhFragment extends Fragment {



    public ManHinhChinhFragment() {

    }


    public static ManHinhChinhFragment newInstance(String param1, String param2) {
        ManHinhChinhFragment fragment = new ManHinhChinhFragment();

        return fragment;
    }

    public static Fragment newInstance() {
        ManHinhChinhFragment fragment = new ManHinhChinhFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_man_hinh_chinh, container, false);
    }
}