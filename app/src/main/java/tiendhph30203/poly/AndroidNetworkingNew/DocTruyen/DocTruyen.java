package tiendhph30203.poly.AndroidNetworkingNew.DocTruyen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tiendhph30203.poly.AndroidNetworkingNew.R;

public class DocTruyen extends AppCompatActivity {
    private PDFView pdfView;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doc_truyen);
        pdfView = findViewById(R.id.docPdf);
        progressBar = findViewById(R.id.progressBar);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("danhsachcacanhnoidungtruyen")) {
            List<Object> danhSachCanhNoiDungTruyen = (List<Object>) intent.getSerializableExtra("danhsachcacanhnoidungtruyen");
            if (danhSachCanhNoiDungTruyen != null && danhSachCanhNoiDungTruyen.size() > 0) {
                Object pdfObject = danhSachCanhNoiDungTruyen.get(0); // Giả sử chỉ có một PDF

                // Xem định dạng của đối tượng
                String objectType = pdfObject.getClass().getSimpleName();
                Log.d("ProductInfo", "Định dạng của đối tượng PDF: " + objectType);

                if (pdfObject instanceof HashMap) {
                    HashMap<String, Object> pdfHashMap = (HashMap<String, Object>) pdfObject;
                    // Trích xuất thông tin từ HashMap
                    if (pdfHashMap.containsKey("filename")) {
                        String pdfFileName = (String) pdfHashMap.get("filename");
                        String pdfUrl = "http://10.24.33.149:3000/pdf/" + pdfFileName;
                        // Hiển thị ProgressBar trước khi bắt đầu tải

                        // Sử dụng AsyncTask để hiển thị PDF
                        progressBar.setVisibility(View.VISIBLE);
                        new RetrievePdfStream().execute(pdfUrl);
                    } else {
                        Log.d("ProductInfo", "Không tìm thấy trường 'filename' trong HashMap.");
                    }
                } else {
                    Log.d("ProductInfo", "Không thể hiển thị PDF: Định dạng không hợp lệ.");
                }
            }
        }




    }

    private class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
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
                progressBar.setVisibility(View.GONE);
                pdfView.fromStream(inputStream)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .pageFitPolicy(FitPolicy.WIDTH)
                        .load();

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


}