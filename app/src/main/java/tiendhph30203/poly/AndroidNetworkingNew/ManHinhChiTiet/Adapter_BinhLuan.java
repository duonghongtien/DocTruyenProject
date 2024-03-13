package tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tiendhph30203.poly.AndroidNetworkingNew.Model.Comment;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;
import tiendhph30203.poly.AndroidNetworkingNew.R;

public class Adapter_BinhLuan extends RecyclerView.Adapter<Adapter_BinhLuan.ViewHolder> {
    private Context context;
    private List<Comment> commentList;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Adapter_BinhLuan(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recyclebinhluan, parent, false);
        return new Adapter_BinhLuan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        Log.d(TAG, "onBindViewHolder: " + comment.getNgaygiobinhluan());
        holder.txtNgayGioBinhLuan.setText(comment.getNgaygiobinhluan());
        holder.txtIdTruyenBinhLuan.setText(comment.getIdtruyen());
        holder.txtIdNguoiDungBinhLuan.setText(comment.getIdnguoidung());
        holder.txtNoiDungBinhLuan.setText(comment.getNoidung());
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgayGioBinhLuan, txtIdTruyenBinhLuan, txtIdNguoiDungBinhLuan, txtNoiDungBinhLuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgayGioBinhLuan = itemView.findViewById(R.id.txtNgayGioBinhLuan);
            txtIdTruyenBinhLuan = itemView.findViewById(R.id.txtIdTruyenBinhLuan);
            txtIdNguoiDungBinhLuan = itemView.findViewById(R.id.txtIdNguoiDungBinhLuan);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
        }
    }

}
