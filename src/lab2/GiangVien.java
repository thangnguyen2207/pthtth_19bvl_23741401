package lab2;

public class GiangVien extends Nguoi {
	private String maGiangVien;
	private String chuyenMon;
	private double luongCoBan;
	private double heSoLuong;
	
	public GiangVien(String hoTen, int namSinh, String diaChi, String maGiangVien, String chuyenMon, double luongCoBan,
			double heSoLuong) {
		super(hoTen, namSinh, diaChi);
		this.maGiangVien = maGiangVien;
		this.chuyenMon = chuyenMon;
		this.luongCoBan = luongCoBan;
		this.heSoLuong = heSoLuong;
	}
	
	public double tinhLuong() {
		return luongCoBan * heSoLuong;
	}
	
	@Override
	public void hienThiThongTin() {
		super.hienThiThongTin();
		System.out.println("Ma giang vien: " + maGiangVien + "\nChuyen mon: " + chuyenMon + "\nLuong co ban: " + String.format("%,.0f", luongCoBan) + "\nHe so luong: " + heSoLuong + "\nTinh luong: " + String.format("%,.0f", tinhLuong()));
	}
}
