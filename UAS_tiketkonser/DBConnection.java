package tiketkonser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL tanpa timezone (karena versi 5.1 tidak memerlukannya)
    private static final String URL = "jdbc:mysql://localhost:3306/war_tiket_seru";
    private static final String USER = "root";
    private static final String PASS = ""; // ganti jika pakai password

    public static Connection getConnection() {
        try {
            // Gunakan driver lama untuk versi 5.1.x
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL tidak ditemukan!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("❌ Gagal terhubung ke database!");
            e.printStackTrace();
            return null;
        }
    }

    // === UJI KONEKSI ===
    public static void main(String[] args) {
        System.out.println("🔍 Menguji koneksi ke MySQL...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Koneksi BERHASIL!");
            try {
                conn.close();
                System.out.println("🔌 Koneksi ditutup.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Koneksi GAGAL.");
        }
    }
}