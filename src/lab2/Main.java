package lab2;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Demo bai: ?");
		int c = sc.nextInt();
		sc.close();
				
		switch (c) {
		case 1:
			demoBai1();
			break;
		case 2:
			demoBai2();
			break;
		default:
			break;
		}
	}

	public static void demoBai1() {
		SanPham sp1 = new SanPham("SP01", "But chi", 5000, 12);
		SanPham sp2 = new SanPham("SP02", "Phan mau", 8000, 7);
		
		sp1.hienThiThongTin();
		sp2.hienThiThongTin();
		
		sp1.nhapHang(3);
		sp1.hienThiThongTin();
		
		sp1.banHang(5);
		sp1.hienThiThongTin();
		
		sp2.banHang(1000);
		sp2.hienThiThongTin();
	}
	
	public static void demoBai2() {
		Nguoi sv1 = new SinhVien("Nguyen Van A", 2002, "263/2A Ap Dinh, xa Tan Xuan", "SV01", "Cong nghe thong tin", 7.7);
		Nguoi sv2 = new SinhVien("Tran Thi B", 2003, "43/8B Trung My Tay, Quan 12", "SV02", "Marketing", 8.9);
	
		Nguoi gv1 = new GiangVien("Phan Tan Trung", 1989, "Khong co", "GV01", "Cong nghe thong tin", 5000000, 2.34);
		Nguoi gv2 = new GiangVien("Nguyen Thi Le Van", 1993, "Khong co", "GV02", "Marketing", 5000000, 2.10);
		
		System.out.println("-- THONG TIN SINH VIEN --");
		sv1.hienThiThongTin();
		System.out.println();
		sv2.hienThiThongTin();
		
		System.out.println("\n-- THONG TIN GIANG VIEN --");
		gv1.hienThiThongTin();
		System.out.println();
		gv2.hienThiThongTin();
	}
	
	
}
