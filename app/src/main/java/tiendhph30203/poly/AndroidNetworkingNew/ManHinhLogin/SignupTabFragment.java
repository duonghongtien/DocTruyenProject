package tiendhph30203.poly.AndroidNetworkingNew.ManHinhLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tiendhph30203.poly.AndroidNetworkingNew.API.DataClient;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class SignupTabFragment extends Fragment {

    EditText edtTaiKhoandk, edtMatKhaudk, edtEmaildk, edtFullnamedk;
    Button btnDangKy;
    float alpha = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        edtTaiKhoandk = root.findViewById(R.id.edtTaiKhoandk);
        edtMatKhaudk = root.findViewById(R.id.edtMatKhaudk);
        edtEmaildk = root.findViewById(R.id.edtEmaildk);
        edtFullnamedk = root.findViewById(R.id.edtFullnamedk);
        btnDangKy = root.findViewById(R.id.btnDangKy);

        edtTaiKhoandk.setTranslationX(800);
        edtMatKhaudk.setTranslationX(800);
        edtEmaildk.setTranslationX(800);
        edtFullnamedk.setTranslationX(800);
        btnDangKy.setTranslationY(0);

        edtTaiKhoandk.setAlpha(0);
        edtMatKhaudk.setAlpha(0);
        edtEmaildk.setAlpha(0);
        edtFullnamedk.setAlpha(0);
        btnDangKy.setAlpha(0);

        edtTaiKhoandk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        edtMatKhaudk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        edtEmaildk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        edtFullnamedk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        btnDangKy.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String taiKhoan = edtTaiKhoandk.getText().toString();
                String matKhau = edtMatKhaudk.getText().toString();
                String email = edtEmaildk.getText().toString();
                String fullName = edtFullnamedk.getText().toString();

                // Check if fields are not empty
                if (!taiKhoan.isEmpty() && !matKhau.isEmpty() && !email.isEmpty() && !fullName.isEmpty()) {
                    // Call the registration API
                    performRegistration(taiKhoan, matKhau, email, fullName);
                } else {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }
    public class APIUtils {
        public static final String BASE_URL = "http://10.24.33.149:3000/user/post/";
    }

    private void performRegistration(String taiKhoan, String matKhau, String email, String fullName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataClient dataClient = retrofit.create(DataClient.class);

        Call<Void> call = dataClient.registerUser(taiKhoan, matKhau, email, fullName);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Registration successful
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Registration failed
                    Toast.makeText(getContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
