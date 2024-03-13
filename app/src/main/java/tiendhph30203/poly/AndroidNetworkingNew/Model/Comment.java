package tiendhph30203.poly.AndroidNetworkingNew.Model;

public class Comment {
    String idtruyen;
    String idnguoidung;
    String noidung;
    String ngaygiobinhluan;

    public Comment(String idtruyen, String idnguoidung, String noidung, String ngaygiobinhluan) {
        this.idtruyen = idtruyen;
        this.idnguoidung = idnguoidung;
        this.noidung = noidung;
        this.ngaygiobinhluan = ngaygiobinhluan;
    }

    public String getNgaygiobinhluan() {
        return ngaygiobinhluan;
    }

    public void setNgaygiobinhluan(String ngaygiobinhluan) {
        this.ngaygiobinhluan = ngaygiobinhluan;
    }

    public Comment() {
    }


    public String getIdtruyen() {
        return idtruyen;
    }

    public void setIdtruyen(String idtruyen) {
        this.idtruyen = idtruyen;
    }

    public String getIdnguoidung() {
        return idnguoidung;
    }

    public void setIdnguoidung(String idnguoidung) {
        this.idnguoidung = idnguoidung;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

}
