package tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet.ManHinhChiTiet;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class Adapter_DanhSachTruyen extends RecyclerView.Adapter<Adapter_DanhSachTruyen.ViewHolder> {
    private Context context;
    private List<Product> productList;
    public Adapter_DanhSachTruyen(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public Adapter_DanhSachTruyen.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycledanhsachtruyen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_DanhSachTruyen.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtTenTruyen.setText(product.getTentruyen());

        // Kiểm tra và xử lý trường anhbia nếu không phải kiểu String
        if (product.getAnhbia() instanceof LinkedTreeMap) {
            LinkedTreeMap<String, String> anhbiaMap = (LinkedTreeMap<String, String>) product.getAnhbia();
            if (anhbiaMap != null && anhbiaMap.containsKey("filename")) {
                String fileName = anhbiaMap.get("filename");
                String imageUrl = "http://10.24.33.149:3000/images/" + fileName;

                // Sử dụng Glide để hiển thị hình ảnh
                Glide.with(context)
                        .load(imageUrl)
                        .apply(new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)) // Chỉ định kích thước thực của ảnh
                        .into(holder.imgAnhBia);

                // Đặt sự kiện click cho item
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Xử lý sự kiện khi item được click, ví dụ chuyển sang màn hình mới
                        // Truyền dữ liệu cần thiết nếu cần
                        Intent intent = new Intent(context, ManHinhChiTiet.class);
                        intent.putExtra("_id", product.get_id());
                        intent.putExtra("tentruyen", product.getTentruyen());
                        intent.putExtra("motangan", product.getMotangan());
                        intent.putExtra("tentacgia", product.getTentacgia());
                        intent.putExtra("namxuatban", product.getNamxuatban());
                        intent.putExtra("anhbia", imageUrl);
                        List<Object> danhSachCanhNoiDungTruyen = product.getDanhsachcacanhnoidungtruyen();
                        ArrayList<Object> arrayListDanhSach = new ArrayList<>(danhSachCanhNoiDungTruyen);
                        intent.putExtra("danhsachcacanhnoidungtruyen", arrayListDanhSach);
                        context.startActivity(intent);
                    }
                });
            } else {
                // Xử lý khi không có trường "filename"
                Log.d("Adapter_DanhSachTruyen", "Không tìm thấy trường 'filename' trong anhbiaMap");
            }
        } else {
            // Xử lý khi trường anhbia không phải là kiểu String
            Log.d("Adapter_DanhSachTruyen", "Trường anhbia không phải là kiểu String.");
        }
    }



    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAnhBia;
        TextView txtTenTruyen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhBia = itemView.findViewById(R.id.imgAnhBia);
            txtTenTruyen = itemView.findViewById(R.id.txtTenTruyen);
        }
    }


}
