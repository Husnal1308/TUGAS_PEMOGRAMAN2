package pertemuan6_S3_KONSTRUKTOR;

public class User {
    // Atribut (Variabel Kelas)
    String username;
    String password;

    // Konstruktor dengan Parameter
    // Nama method sama dengan nama class (User)
    public User(String username, String password) {
        // 'this' merujuk ke atribut kelas
        this.username = username;
        // Variabel 'username' tanpa 'this' merujuk ke parameter
        
        this.password = password;
        
        System.out.println("Objek berhasil diinisialisasi menggunakan Konstruktor.");
    }

    // Main Method (untuk menjalankan kode)
    public static void main(String[] args) {
        // Membuat objek baru (instansiasi) dan otomatis memanggil Konstruktor.
        // Nilai "petanikode" dan "kopi" langsung diteruskan ke konstruktor.
        User petani = new User("petanikode", "kopi");
        
        // Menampilkan data yang telah diisi oleh konstruktor
        System.out.println("Username Objek: " + petani.username);
        System.out.println("Password Objek: " + petani.password);
    }
}

