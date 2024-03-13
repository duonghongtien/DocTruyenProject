package tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen;

import android.content.Context;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.internal.LinkedTreeMap;

import java.io.InputStream;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import com.bumptech.glide.Registry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tiendhph30203.poly.AndroidNetworkingNew.API.ApiService;
import tiendhph30203.poly.AndroidNetworkingNew.ManHinhChinhFragment;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;
import tiendhph30203.poly.AndroidNetworkingNew.R;


public class DanhSachTruyenFragment extends Fragment {
    private RecyclerView recyclerViewDanhSachTruyen;
    private Adapter_DanhSachTruyen adapter;
    private static final String BASE_URL = "http://10.24.33.149:3000/products/";

    public DanhSachTruyenFragment() {

    }

    public static Fragment newInstance() {
        return new DanhSachTruyenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_truyen, container, false);
        recyclerViewDanhSachTruyen = view.findViewById(R.id.recycleViewDanhSachTruyen);
        adapter = new Adapter_DanhSachTruyen(getContext(), null);
        recyclerViewDanhSachTruyen.setAdapter(adapter);
        recyclerViewDanhSachTruyen.setLayoutManager(new LinearLayoutManager(getContext()));
        callApiAndUpdateData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String IdUser = bundle.getString("IdUser");
            Log.d("DanhSachTruyenFragment", "IdUser: " + IdUser);

            // Lưu IdUser vào SharedPreferences
            saveIdUserToSharedPreferences(IdUser);
        }

        return view;
    }
    private void saveIdUserToSharedPreferences(String IdUser) {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IdUser", IdUser);
        editor.apply();
    }

    private void callApiAndUpdateData() {
        // Gọi API để lấy danh sách sản phẩm
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    updateProductList(productList);
                } else {
                    Log.d("Lỗi", "Lỗi " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("Lỗi", "Lỗi " + t.getMessage());
            }
        });
    }

    // Phương thức để cập nhật danh sách sản phẩm
    public void updateProductList(List<Product> productList) {
        if (adapter != null) {
            adapter.setProductList(productList);
            adapter.notifyDataSetChanged();
        }
    }

}