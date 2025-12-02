package pertemuan6_S3_ENKAPSULASI;

public class Main {
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
