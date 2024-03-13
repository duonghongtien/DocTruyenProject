package tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet;

import static java.security.AccessController.getContext;


import static tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet.ManHinhChiTiet.APIUtils.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tiendhph30203.poly.AndroidNetworkingNew.API.ApiService;
import tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen.Adapter_DanhSachTruyen;
import tiendhph30203.poly.AndroidNetworkingNew.DocTruyen.DocTruyen;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Comment;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class ManHinhChiTiet extends AppCompatActivity {
    CircleImageView imgAnhBia;
    Button btnDocTruyen, btnBinhLuan;
    TextView txtIdTruyenChiTiet, txtTenTruyenChiTiet, txtMoTaNganChiTiet, txtTenTacGiaChiTiet, txtNamXuatBanChiTiet;
    RecyclerView recycleViewBinhLuan;
    EditText edtBinhLuan;
    private Adapter_BinhLuan adapter;

    public class APIUtils2 {
        public static final String BASE_URL = "http://10.24.33.149:3000/comment/get/";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chi_tiet);
        imgAnhBia = findViewById(R.id.imgAnhBiaChiTiet);
        btnDocTruyen = findViewById(R.id.btnDocTruyen);
        txtIdTruyenChiTiet = findViewById(R.id.txtIdTruyenChiTiet);
        txtTenTruyenChiTiet = findViewById(R.id.txtTenTruyenChiTiet);
        txtMoTaNganChiTiet = findViewById(R.id.txtMoTaNganChiTiet);
        txtTenTacGiaChiTiet = findViewById(R.id.txtTenTacGiaChiTiet);
        txtNamXuatBanChiTiet = findViewById(R.id.txtNamXuatBanChiTiet);
        recycleViewBinhLuan = findViewById(R.id.recycleViewBinhLuan);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        edtBinhLuan = findViewById(R.id.edtBinhLuan);

        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the content of the EditText for the comment
                String noiDungBinhLuan = edtBinhLuan.getText().toString();

                // Check if the comment content is not empty
                if (!noiDungBinhLuan.isEmpty()) {
                    // Call the method to post the comment with the content
                    guiBinhLuan(noiDungBinhLuan);
                } else {
                    // Show a message indicating that the comment is empty
                    Toast.makeText(ManHinhChiTiet.this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT).show();
                }
            }
        });


        adapter = new Adapter_BinhLuan(this, null);
        recycleViewBinhLuan.setAdapter(adapter);
        recycleViewBinhLuan.setLayoutManager(new LinearLayoutManager(this));
        // Lấy IdUser từ SharedPreferences
        String IdUser = getIdUserFromSharedPreferences();
        Log.d("ManHinhChiTiet", "ID người dùng nè: " + IdUser);


        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("tentruyen")) {
                String objectId = intent.getStringExtra("_id");
                String tentruyen = intent.getStringExtra("tentruyen");
                String motangan = intent.getStringExtra("motangan");
                String tentacgia = intent.getStringExtra("tentacgia");
                String namxuatban = intent.getStringExtra("namxuatban");
                txtIdTruyenChiTiet.setText(objectId);
                txtTenTruyenChiTiet.setText(tentruyen);
                txtMoTaNganChiTiet.setText(motangan);
                txtTenTacGiaChiTiet.setText(tentacgia);
                txtNamXuatBanChiTiet.setText(namxuatban);

                callApiAndUpdateData(objectId);
                Log.d("ManHinhChiTiet", "ID: " + objectId);
                Log.d("ManHinhChiTiet", "Tên truyện: " + tentruyen);
                Log.d("ManHinhChiTiet", "Mô tả ngắn: " + motangan);
                Log.d("ManHinhChiTiet", "Tên tác giả: " + tentacgia);
                Log.d("ManHinhChiTiet", "Năm xuất bản: " + namxuatban);
                if (intent.hasExtra("anhbia")) {
                    String anhbiaUrl = intent.getStringExtra("anhbia");
                    Log.d("ManHinhChiTiet", "URL ảnh bìa: " + anhbiaUrl);
                    // Sử dụng Glide để hiển thị ảnh
                    Glide.with(ManHinhChiTiet.this)
                            .load(anhbiaUrl)
                            .into(imgAnhBia);
                }


                // Nhận danh sách các HashMap
                ArrayList<HashMap<String, String>> danhSachCacAnhNoiDungTruyen =
                        (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("danhsachcacanhnoidungtruyen");
                // Bạn có thể lặp qua danh sách và xử lý từng HashMap
                for (HashMap<String, String> anhNoiDungTruyen : danhSachCacAnhNoiDungTruyen) {
                    // Lấy thông tin từ HashMap
                    String fileName = anhNoiDungTruyen.get("filename");
                    Log.d("ManHinhChiTiet", "URL ảnh bìa: " + fileName);
                    // TODO: Xử lý thông tin từ mỗi HashMap tại đây
                }

                btnDocTruyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ManHinhChiTiet.this, DocTruyen.class);

                        // Truyền dữ liệu vào Intent
                        intent.putExtra("danhsachcacanhnoidungtruyen", danhSachCacAnhNoiDungTruyen);

                        // Khởi chạy màn hình DocTruyen
                        startActivity(intent);
                    }
                });

            }

        }


    }



    public static class APIUtils {
        public static final String BASE_URL = "http://10.24.33.149:3000/comment/post/";
        public static ApiService getApiService() {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
    }


    private void guiBinhLuan(String noiDungBinhLuan) {
        // Lấy IdUser từ SharedPreferences
        String idUser = getIdUserFromSharedPreferences();
        // Lấy thông tin từ Intent
        String idTruyen = getIntent().getStringExtra("_id");

        // Hiển thị dữ liệu trước khi gửi bình luận
        Log.d("Thông tin trước khi gửi bình luận", "IdUser: " + idUser);
        Log.d("Thông tin trước khi gửi bình luận", "idTruyen: " + idTruyen);
        Log.d("Thông tin trước khi gửi bình luận", "noiDungBinhLuan: " + noiDungBinhLuan);
        String ngayGioBinhLuan = getCurrentDateTime();
        Log.d("Thông tin trước khi gửi bình luận", "Ngày giờ bình luận: " + ngayGioBinhLuan);

        // Tạo đối tượng Comment
        Comment newComment = new Comment(idUser, idTruyen, noiDungBinhLuan, ngayGioBinhLuan);

        // Gọi API để gửi bình luận
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi phương thức postComment và truyền các tham số
        Call<Void> call = apiService.postComment(newComment.getIdnguoidung(), newComment.getIdtruyen(), newComment.getNoidung(), newComment.getNgaygiobinhluan());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Request URL", call.request().url().toString());
                if (response.isSuccessful()) {
                    callApiAndUpdateData(idTruyen);
                    // Xóa nội dung trong EditText sau khi gửi
                    edtBinhLuan.setText("");
                    Toast.makeText(ManHinhChiTiet.this, "Bình luận thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Lỗi11", "Lỗi " + response.code() + ": " + response.message());
                    // Log thêm thông tin nếu cần
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Lỗi222", "Lỗi " + t.getMessage());
            }
        });
    }


    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }





    private String getIdUserFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getString("IdUser", "");
    }
    private void callApiAndUpdateData(String idTruyen) {
        // Gọi API để lấy danh sách bình luận theo id truyện
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUtils2.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Comment>> call = apiService.getCommentsByTruyenId(idTruyen);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> commentList = response.body();
                    updateCommentList(commentList);
                } else {
                    Log.d("Lỗi111", "Lỗi " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.d("Lỗi222", "Lỗi " + t.getMessage());
            }
        });
    }

    // Phương thức để cập nhật danh sách sản phẩm
    public void updateCommentList(List<Comment> commentList) {
        if (adapter != null) {
            adapter.setCommentList(commentList);
            adapter.notifyDataSetChanged();
        }
    }
}
