package lab8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            // B1. Ket noi SQLite
            Class.forName("org.sqlite.JDBC");

            String url = "jdbc:sqlite:nhanvien.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Ket noi SQLite thanh cong!");

            // B2. Tao cac bang
            taoBang(conn);

            // B3. Them san pham
            themSanPham(conn);

            // B4. Hien thi danh sach san pham
            hienThiSanPham(conn);

            // B5. Tim kiem san pham theo ten
            timKiemSanPham(conn, "Laptop");

            // B6. Cap nhat san pham bang Transaction
            capNhatSanPham(conn, "SP01", 18000000, 15);

            // B7. Hien thi lai danh sach sau khi cap nhat
            hienThiSanPham(conn);

            // B8. Xoa san pham
            xoaSanPham(conn, "SP03");

            // B9. Hien thi danh sach sau khi xoa
            hienThiSanPham(conn);

            // B10. Them nhan vien
            themNhanVien(conn);

            // B11. Thong ke luong nhan vien
            thongKeLuong(conn);

            // B12. Dem nhan vien theo chuc vu
            demNhanVienTheoChucVu(conn);

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay SQLite JDBC Driver!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Loi SQL: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Dong Connection
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("\nDa dong ket noi!");
                }
            } catch (SQLException e) {
                System.out.println("Loi dong Connection: " + e.getMessage());
            }
        }
    }

    // 1. Tao bang SanPham va NhanVien
    public static void taoBang(Connection conn) {

        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            String sqlSanPham =
                    "CREATE TABLE IF NOT EXISTS SanPham ("
                    + "maSP TEXT PRIMARY KEY,"
                    + "tenSP TEXT NOT NULL,"
                    + "gia REAL,"
                    + "soluong INTEGER"
                    + ")";

            String sqlNhanVien =
                    "CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "id INTEGER PRIMARY KEY,"
                    + "ten TEXT NOT NULL,"
                    + "chuc_vu TEXT,"
                    + "luong REAL DEFAULT 0"
                    + ")";

            stmt.executeUpdate(sqlSanPham);
            stmt.executeUpdate(sqlNhanVien);

            System.out.println("\nTao bang thanh cong!");

        } catch (SQLException e) {
            System.out.println("Loi tao bang: " + e.getMessage());

        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 2. Them 3 san pham bang PreparedStatement va Transaction
    public static void themSanPham(Connection conn) {

        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql =
                    "INSERT OR IGNORE INTO SanPham "
                    + "(maSP, tenSP, gia, soluong) "
                    + "VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // San pham 1
            pstmt.setString(1, "SP01");
            pstmt.setString(2, "Laptop Dell");
            pstmt.setDouble(3, 17000000);
            pstmt.setInt(4, 10);
            pstmt.executeUpdate();

            // San pham 2
            pstmt.setString(1, "SP02");
            pstmt.setString(2, "Ban phim Logitech");
            pstmt.setDouble(3, 500000);
            pstmt.setInt(4, 20);
            pstmt.executeUpdate();

            // San pham 3
            pstmt.setString(1, "SP03");
            pstmt.setString(2, "Chuot Gaming");
            pstmt.setDouble(3, 300000);
            pstmt.setInt(4, 30);
            pstmt.executeUpdate();

            conn.commit();

            System.out.println("Them san pham thanh cong!");

        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Them san pham that bai! Da rollback.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null) pstmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 3. Hien thi danh sach san pham
    public static void hienThiSanPham(Connection conn) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM SanPham";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("\n===== DANH SACH SAN PHAM =====");

            while (rs.next()) {
                System.out.println(
                        "Ma SP: " + rs.getString("maSP")
                        + " | Ten SP: " + rs.getString("tenSP")
                        + " | Gia: " + formatTien(rs.getDouble("gia"))
                        + " | So luong: " + rs.getInt("soluong")
                );
            }

        } catch (SQLException e) {
            System.out.println("Loi hien thi san pham: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 4. Tim kiem san pham theo ten
    public static void timKiemSanPham(Connection conn, String tenSP) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql =
                    "SELECT * FROM SanPham WHERE tenSP LIKE ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + tenSP + "%");

            rs = pstmt.executeQuery();

            System.out.println("\n===== KET QUA TIM KIEM =====");

            while (rs.next()) {
                System.out.println(
                        rs.getString("maSP") + " - "
                        + rs.getString("tenSP") + " - "
                        + formatTien(rs.getDouble("gia")) + " - "
                        + rs.getInt("soluong")
                );
            }

        } catch (SQLException e) {
            System.out.println("Loi tim kiem: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 5. Cap nhat gia va so luong bang Transaction
    public static void capNhatSanPham(
            Connection conn, String maSP, double gia, int soluong) {

        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql =
                    "UPDATE SanPham SET gia = ?, soluong = ? "
                    + "WHERE maSP = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, gia);
            pstmt.setInt(2, soluong);
            pstmt.setString(3, maSP);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                conn.commit();
                System.out.println("\nCap nhat san pham thanh cong!");
            } else {
                conn.rollback();
                System.out.println("\nKhong tim thay san pham!");
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("Cap nhat that bai! Da rollback.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null) pstmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 6. Xoa san pham theo maSP
    public static void xoaSanPham(Connection conn, String maSP) {

        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM SanPham WHERE maSP = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maSP);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("\nXoa san pham thanh cong!");
            } else {
                System.out.println("\nKhong tim thay san pham can xoa!");
            }

        } catch (SQLException e) {
            System.out.println("Loi xoa san pham: " + e.getMessage());

        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 7. Them nhan vien co muc luong
    public static void themNhanVien(Connection conn) {

        PreparedStatement pstmt = null;

        try {
            String sql =
                    "INSERT OR IGNORE INTO NhanVien "
                    + "(id, ten, chuc_vu, luong) "
                    + "VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // Nhan vien 1
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Nguyen Van An");
            pstmt.setString(3, "Giam doc");
            pstmt.setDouble(4, 30000000);
            pstmt.executeUpdate();

            // Nhan vien 2
            pstmt.setInt(1, 2);
            pstmt.setString(2, "Tran Thi Binh");
            pstmt.setString(3, "Ke toan");
            pstmt.setDouble(4, 15000000);
            pstmt.executeUpdate();

            // Nhan vien 3
            pstmt.setInt(1, 3);
            pstmt.setString(2, "Le Van Cuong");
            pstmt.setString(3, "Nhan vien");
            pstmt.setDouble(4, 12000000);
            pstmt.executeUpdate();

            System.out.println("\nThem nhan vien thanh cong!");

        } catch (SQLException e) {
            System.out.println("Loi them nhan vien: " + e.getMessage());

        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 8. Thong ke luong SUM, AVG, MIN, MAX
    public static void thongKeLuong(Connection conn) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql =
                    "SELECT SUM(luong) AS tongLuong, "
                    + "AVG(luong) AS luongTrungBinh, "
                    + "MIN(luong) AS luongThapNhat, "
                    + "MAX(luong) AS luongCaoNhat "
                    + "FROM NhanVien";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n===== THONG KE LUONG =====");

                System.out.println(
                        "Tong luong: " + formatTien(rs.getDouble("tongLuong")));

                System.out.println(
                        "Luong trung binh: " + formatTien(rs.getDouble("luongTrungBinh")));

                System.out.println(
                        "Luong thap nhat: " + formatTien(rs.getDouble("luongThapNhat")));

                System.out.println(
                        "Luong cao nhat: " + formatTien(rs.getDouble("luongCaoNhat")));
            }

        } catch (SQLException e) {
            System.out.println("Loi thong ke luong: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 9. Dem so luong nhan vien theo chuc vu
    public static void demNhanVienTheoChucVu(Connection conn) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql =
                    "SELECT chuc_vu, COUNT(*) AS soLuong "
                    + "FROM NhanVien "
                    + "GROUP BY chuc_vu";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("\n===== NHAN VIEN THEO CHUC VU =====");

            while (rs.next()) {
                System.out.println(
                        "Chuc vu: " + rs.getString("chuc_vu")
                        + " | So luong: " + rs.getInt("soLuong")
                );
            }

        } catch (SQLException e) {
            System.out.println("Loi thong ke nhan vien: " + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String formatTien(double tien) {
    	return String.format(java.util.Locale.GERMANY, "%,.0f", tien);
    }
}
