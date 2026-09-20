package lab2;

public class SinhVien extends Nguoi {
	private String maSinhVien;
	private String nganhHoc;
	private double diemTrungBinh;
	
	public SinhVien(String hoTen, int namSinh, String diaChi, String maSinhVien, String nganhHoc,
			double diemTrungBinh) {
		super(hoTen, namSinh, diaChi);
		this.maSinhVien = maSinhVien;
		this.nganhHoc = nganhHoc;
		this.diemTrungBinh = diemTrungBinh;
	}

	@Override
	public void hienThiThongTin() {
		super.hienThiThongTin();
		System.out.println("Ma sinh vien: " + maSinhVien + "\nNganh hoc: " + nganhHoc + 
				"\nDiem trung binh: " + diemTrungBinh + "\nXep loai: " + xepLoai());
	}

	public String xepLoai() {
		if (diemTrungBinh >= 8.5) {
			return "Gioi";
		}
		if (diemTrungBinh >= 7) {
			return "Kha";
		}
		if (diemTrungBinh >= 5) {
			return "Trung binh";
		}
		
		return "Yeu";
	}
}
