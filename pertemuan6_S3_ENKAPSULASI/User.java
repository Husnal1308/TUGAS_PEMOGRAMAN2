package pertemuan6_S3_ENKAPSULASI;

public class User {
    // Atribut dibuat 'private' (dienkapsulasi) untuk meningkatkan keamanan data.
    private String username;
    private String password;

    // --- Setter Methods (Untuk Mengisi/Mengubah Nilai) ---

    // Setter untuk username (menggunakan 'void' karena tidak mengembalikan nilai)
    public void setUsername(String username) {
        this.username = username;
    }

    // Setter untuk password
    public void setPassword(String password) {
        this.password = password;
    }

    // --- Getter Methods (Untuk Mengambil/Membaca Nilai) ---

    // Getter untuk username (menggunakan 'String' karena mengembalikan nilai String)
    public String getUsername() {
        return this.username;
    }

    // Getter untuk password
    public String getPassword() {
        return this.password;
    }
    
    // Main Method (Contoh implementasi)
    public static void main(String[] args) {
        User user = new User(); // Membuat objek
        
        // 1. Menggunakan Setter untuk mengisi data
        System.out.println("Mengisi data menggunakan Setter...");
        user.setUsername("Charis Fauzan");
        user.setPassword("rahasia");
        
        // 2. Menggunakan Getter untuk mengambil dan mencetak data
        System.out.println("\nMengambil data menggunakan Getter:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
    }
}
