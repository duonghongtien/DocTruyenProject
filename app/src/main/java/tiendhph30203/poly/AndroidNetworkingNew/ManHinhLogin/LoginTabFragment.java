package tiendhph30203.poly.AndroidNetworkingNew.ManHinhLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tiendhph30203.poly.AndroidNetworkingNew.API.DataClient;
import tiendhph30203.poly.AndroidNetworkingNew.API.LoginClient;
import tiendhph30203.poly.AndroidNetworkingNew.MainActivity2;
import tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet.ManHinhChiTiet;
import tiendhph30203.poly.AndroidNetworkingNew.ManHinhChinh;
import tiendhph30203.poly.AndroidNetworkingNew.Model.User;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class LoginTabFragment extends Fragment {
    Button btnDangNhap;
    EditText edtTaiKhoan, edtMatKhau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_tab_fragment, container, false);
        edtTaiKhoan = root.findViewById(R.id.edtTaiKhoan);
        edtMatKhau = root.findViewById(R.id.edtMatKhau);
        btnDangNhap = root.findViewById(R.id.btnDangNhap);

        edtTaiKhoan.setTranslationX(800);
        edtMatKhau.setTranslationX(800);
        btnDangNhap.setTranslationY(0);
        edtTaiKhoan.setAlpha(0);
        edtMatKhau.setAlpha(0);
        btnDangNhap.setAlpha(0);
        edtTaiKhoan.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        edtMatKhau.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btnDangNhap.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String taiKhoan = edtTaiKhoan.getText().toString();
                String matKhau = edtMatKhau.getText().toString();

                // Check if fields are not empty
                if (!taiKhoan.isEmpty() && !matKhau.isEmpty()) {
                    // Call the login API
                    performLogin(taiKhoan, matKhau);
                } else {
                    Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public class APIUtils {
        public static final String BASE_URL = "http://10.24.33.149:3000/login/user/";
    }

    private void performLogin(String taiKhoan, String matKhau) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LoginClient loginClient = retrofit.create(LoginClient.class);

        Call<User> call = loginClient.login(taiKhoan, matKhau);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {

                        String IdUser = user.get_id();
                        Log.d("fff", "đfff" + IdUser);

                        Intent intent = new Intent(getContext(), ManHinhChinh.class);
                        intent.putExtra("IdUser", IdUser);
                        startActivity(intent);
//                        getActivity().finish();
                        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(response.errorBody().string());
                        String errorMessage = errorBody.getString("message");
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Lỗi", "Lỗi" + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
