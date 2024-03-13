package tiendhph30203.poly.AndroidNetworkingNew.Model;

import java.io.Serializable;
import java.util.List;

import tiendhph30203.poly.AndroidNetworkingNew.MainActivity2;

public class Product implements Serializable {
    // Các trường khác
    private String _id; // Sử dụng kiểu String

    public String get_id() {
        return _id;
    }
    private String tentruyen;
    private String motangan;
    private String  tentacgia ;
    private String namxuatban;
    private Object anhbia; // Thay đổi kiểu dữ liệu từ String sang Object
    private List<Object> danhsachcacanhnoidungtruyen; // Có thể thay đổi kiểu dữ liệu tùy thuộc vào cấu trúc thực tế của dữ liệu từ server

    public Product(String _id, String tentruyen, String motangan, String tentacgia, String namxuatban, Object anhbia, List<Object> danhsachcacanhnoidungtruyen) {
        this._id = _id;
        this.tentruyen = tentruyen;
        this.motangan = motangan;
        this.tentacgia = tentacgia;
        this.namxuatban = namxuatban;
        this.anhbia = anhbia;
        this.danhsachcacanhnoidungtruyen = danhsachcacanhnoidungtruyen;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product() {
    }


    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getMotangan() {
        return motangan;
    }

    public void setMotangan(String motangan) {
        this.motangan = motangan;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public String getNamxuatban() {
        return namxuatban;
    }

    public void setNamxuatban(String namxuatban) {
        this.namxuatban = namxuatban;
    }

    public Object getAnhbia() {
        return anhbia;
    }

    public void setAnhbia(Object anhbia) {
        this.anhbia = anhbia;
    }

    public List<Object> getDanhsachcacanhnoidungtruyen() {
        return danhsachcacanhnoidungtruyen;
    }

    public void setDanhsachcacanhnoidungtruyen(List<Object> danhsachcacanhnoidungtruyen) {
        this.danhsachcacanhnoidungtruyen = danhsachcacanhnoidungtruyen;
    }
}
