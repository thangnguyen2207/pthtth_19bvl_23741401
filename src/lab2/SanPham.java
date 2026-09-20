package lab2;

public class SanPham {
	private String maSP;
	private String tenSP;
	private double donGia;
	private int tonKho;
	
	public SanPham(String maSP, String tenSP, int donGia, int tonKho) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.donGia = donGia;
		this.tonKho = tonKho;
	}
	
	public double tinhThanhTien() {
		return donGia * tonKho;
	}
	
	public void nhapHang(int soLuongNhap) {
		if (soLuongNhap <= 0) {
			System.out.println("So luong nhap phai lon hon 0");
		} else {
			tonKho += soLuongNhap;
			System.out.println("Nhap hang thanh cong (" + soLuongNhap + ")");
		}
	}
	
	public boolean banHang(int soLuongBan) {
		if (soLuongBan <= 0) {
			System.out.println("So luong ban can lon hon 0");
			return false;
		} else if (soLuongBan > tonKho) {
			System.out.println("Khong duoc ban qua so luong ton kho");
			return false;
		} else {
			tonKho -= soLuongBan;
			System.out.println("Ban hang thanh cong (" + soLuongBan + ")");
		}
		return true;
	}
	
	public void hienThiThongTin() {
		System.out.println("MaSP: " + maSP + "\nTen SP: " + tenSP + 
				"\nDon gia: " + donGia + "\nTon kho: " + tonKho + "\nThanh tien: " + tinhThanhTien() + "\n");
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}

	public int getTonKho() {
		return tonKho;
	}

	public void setTonKho(int tonKho) {
		this.tonKho = tonKho;
	}
}

