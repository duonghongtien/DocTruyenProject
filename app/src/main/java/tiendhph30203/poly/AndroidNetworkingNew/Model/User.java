package tiendhph30203.poly.AndroidNetworkingNew.Model;

public class User {

    private String _id;

    public User(String _id, String taikhoan, String matkhau, String email, String fullname) {
        this._id = _id;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.email = email;
        this.fullname = fullname;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String taikhoan;
    private String matkhau;
    private String email;
    private String fullname;

    public User() {
    }

    public User(String taikhoan, String matkhau, String email, String fullname) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.email = email;
        this.fullname = fullname;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
