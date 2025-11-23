package pertemuan5_S3_KEYWORDTHIS;

public class User {
    // 1. Variabel Kelas (Atribut)
    String username;
    String password;

    // Konstruktor dengan parameter yang namanya sama dengan variabel kelas
    public User(String username, String password) {
        
        // 2. Penggunaan 'this' untuk membedakan
        // 'this.username' merujuk ke variabel kelas (poin 1)
        // 'username' tanpa 'this' merujuk ke variabel parameter konstruktor
        this.username = username;
        this.password = password;
        
        // 3. Penggunaan 'this' untuk memanggil method lain dari objek yang sama
        // this.tampilkanInfo(); 
    }
    
    // Method untuk menampilkan informasi
    public void tampilkanInfo() {
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
    }
    
    public static void main(String[] args) {
        // Membuat objek pertama
        User user1 = new User("admin", "rahasia");
        
        // Membuat objek kedua
        User user2 = new User("guest", "12345");
        
        System.out.println("--- Informasi User 1 ---");
        user1.tampilkanInfo();
        
        System.out.println("\n--- Informasi User 2 ---");
        user2.tampilkanInfo();
    }
}
