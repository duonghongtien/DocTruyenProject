package tiendhph30203.poly.AndroidNetworkingNew.API;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tiendhph30203.poly.AndroidNetworkingNew.Model.User;

public interface LoginClient {
    @FormUrlEncoded
    @POST("/login/user")
    Call<User> login(
            @Field("taikhoan") String taiKhoan, //lấy body
            @Field("matkhau") String matKhau
    );
}

