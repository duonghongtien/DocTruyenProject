package tiendhph30203.poly.AndroidNetworkingNew;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tiendhph30203.poly.AndroidNetworkingNew.API.ApiService;
import tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen.Adapter_DanhSachTruyen;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class MainActivity2 extends AppCompatActivity {
    private static final String BASE_URL = "http://10.24.33.149:3000/products/";
    private Handler handler = new Handler();
    private TextView tentruyenTextView;
    private TextView motanganTextView;
    private TextView tentacgiaTextView;
    private TextView namxuatbanTextView;
    private ImageView anhbiaImageView;
    private PDFView pdfView;

    private static final long REFRESH_INTERVAL = 30000; // 30 giây
    private Timer timer;
    private long lastUpdatedTimestamp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        // Khai báo các TextView, ImageView và PDFView
        tentruyenTextView = findViewById(R.id.tentruyen);
        motanganTextView = findViewById(R.id.motangan);
        tentacgiaTextView = findViewById(R.id.tentacgia);
        namxuatbanTextView = findViewById(R.id.namxuatban);
        anhbiaImageView = findViewById(R.id.anhbia);
        pdfView = findViewById(R.id.pdfView2);
        refreshData();

    }

    private void refreshData() {
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
                    long currentTimestamp = System.currentTimeMillis();
                    if (currentTimestamp > lastUpdatedTimestamp) {
                        lastUpdatedTimestamp = currentTimestamp;
                    List<Product> productList = response.body();
                    for (Product product : productList) {
                        tentruyenTextView.setText(product.getTentruyen());
                        motanganTextView.setText(product.getMotangan());
                        tentacgiaTextView.setText(product.getTentacgia());
                        namxuatbanTextView.setText(product.getNamxuatban());
                        String objectId = product.get_id();
                        if (objectId != null) {
                            Log.d("ProductInfo", "_id: " + objectId);
                        }
                        Log.d("ProductInfo", "Tên truyện: " + product.getTentruyen());
                        Log.d("ProductInfo", "Mô tả ngắn: " + product.getMotangan());
                        Log.d("ProductInfo", "Năm xuất bn: " + product.getNamxuatban());
                        Log.d("ProductInfo", "Tác giả: " + product.getTentacgia()); // Log giá trị của trường anhbia
                        Log.d("ProductInfo", "Ảnh bìa: " + product.getAnhbia());
                        Log.d("ProductInfo", "Kiểu dữ liệu của trường anhbia: " + product.getAnhbia().getClass().getSimpleName());
                        // Kiểm tra và xử lý trường anhbia nếu không phải kiểu String
                        // Kiểm tra xem trường anhbia có giá trị không
                        if (product.getAnhbia() != null) {
                            // Ép kiểu trường anhbia thành LinkedTreeMap
                            LinkedTreeMap<String, String> anhbiaMap = (LinkedTreeMap<String, String>) product.getAnhbia();

                            // Kiểm tra xem có trường "filename" không
                            if (anhbiaMap != null && anhbiaMap.containsKey("filename")) {
                                // Lấy tên file từ trường "filename"
                                String fileName = anhbiaMap.get("filename");
                                // Tạo URL của hình ảnh
                                String imageUrl = "http://10.24.33.149:3000/images/" + fileName;
                                // Sử dụng Glide để hiển thị hình ảnh
                                Glide.with(MainActivity2.this)
                                        .load(imageUrl)
                                        .into(anhbiaImageView);
                            } else {
                                // Xử lý khi không có trường "filename"
                                Log.d("ProductInfo", "Không tìm thấy trường 'filename' trong anhbiaMap");
                            }
                        } else {
                            // Xử lý khi trường anhbia là null
                            Log.d("ProductInfo", "Trường anhbia là null");
                        }

                        if (product.getAnhbia() instanceof String) {
                            // Sử dụng Glide để hiển thị ảnh từ URL vào ImageView
                            String imageUrl = (String) product.getAnhbia();
                            Glide.with(MainActivity2.this)
                                    .load(imageUrl)
                                    .into(anhbiaImageView);
                        } else {
                            // Xử lý khi trường anhbia không phải là String
                            Log.d("ProductInfo", "Trường anhbia không phải là kiểu String.");
                        }
                        // Log giá trị của trường danhsachcacanhnoidungtruyen
                        // Log giá trị của trường danhsachcacanhnoidungtruyen
                        List<Object> danhSachCanhNoiDungTruyen = product.getDanhsachcacanhnoidungtruyen();
                        if (danhSachCanhNoiDungTruyen != null && danhSachCanhNoiDungTruyen.size() > 0) {
                            Object pdfObject = danhSachCanhNoiDungTruyen.get(0); // Giả sử chỉ có một PDF

                            // Xem định dạng của đối tượng
                            String objectType = pdfObject.getClass().getSimpleName();
                            Log.d("ProductInfo", "Định dạng của đối tượng PDF: " + objectType);

                            if (pdfObject instanceof LinkedTreeMap) {
                                LinkedTreeMap<String, Object> pdfMap = (LinkedTreeMap<String, Object>) pdfObject;

                                // Trích xuất tên file PDF từ thuộc tính "filename"
                                String pdfFileName = (String) pdfMap.get("filename");

                                // Tạo URL hoặc đường dẫn đầy đủ của file PDF
                                String pdfUrl = "http://10.24.33.149:3000/pdf/" + pdfFileName; // Thay thế URL thực tế của bạn
                                // Sử dụng AsyncTask để hiển thị PDF
                                new RetrievePdfStream().execute(pdfUrl);
                            } else {
                                Log.d("ProductInfo", "Không thể hiển thị PDF: Định dạng không hợp lệ.");
                            }
                        }
                    }

                } else {
                    // Xử lý phản hồi lỗi ở đây
                    Toast.makeText(MainActivity2.this, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("Lỗi", "Lỗi " + response.message());
                }
            }}

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Xử lý sự cố
                Toast.makeText(MainActivity2.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Lỗi", "Lỗi " + t.getMessage());
            }
        });
    }

    public class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } else {
                    Log.e("PDFLoad", "Lỗi khi tải PDF, mã phản hồi: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream != null) {
                Log.d("PDFLoad", "InputStream không null, đang tải PDF...");
                pdfView.fromStream(inputStream).load();

                // Kiểm tra xem PDF đã được tải thành công hay không
                if (pdfView.getPageCount() > 0) {
                    Log.d("PDFLoad", "PDF đã được tải và hiển thị thành công.");
                } else {
                    Log.d("PDFLoad", "Không thể hiển thị PDF: Định dạng không hợp lệ hoặc có lỗi khác.");
                }
            } else {
                Log.e("PDFLoad", "InputStream null, có lỗi khi tải PDF.");
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Hủy Timer khi Activity bị hủy
//        if (timer != null) {
//            timer.cancel();
//            timer.purge();
//        }
//    }





}
