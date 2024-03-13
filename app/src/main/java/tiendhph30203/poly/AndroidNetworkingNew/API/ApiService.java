package tiendhph30203.poly.AndroidNetworkingNew.API;




import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Comment;
import tiendhph30203.poly.AndroidNetworkingNew.Model.Product;

public interface ApiService {
    @GET("/products")
    Call<List<Product>> getProducts(); // Sử dụng List<Product> nếu bạn mong đợi nhận danh sách sản phẩm

    @GET("/comment/get/{idtruyen}")
    Call<List<Comment>> getCommentsByTruyenId(@Path("idtruyen") String idTruyen);


    @FormUrlEncoded
    @POST("/comment/post")
    Call<Void> postComment(
            @Field("idtruyen") String idtruyen,
            @Field("idnguoidung") String idnguoidung,
            @Field("noidung") String noidung,
            @Field("ngaygiobinhluan") String ngaygiobinhluan
    );

}
