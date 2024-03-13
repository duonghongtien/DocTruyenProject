package tiendhph30203.poly.AndroidNetworkingNew.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataClient {
    @FormUrlEncoded
    @POST("/user/post")
    Call<Void> registerUser(
            @Field("taikhoan") String taiKhoan,
            @Field("matkhau") String matKhau,
            @Field("email") String email,
            @Field("fullname") String fullName
    );
}
