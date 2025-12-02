package pertemuan7_S3_POLIMORFISMEDINAMIS;

public class Main {
public static void main(String[] args) {
        
        // 1. Membuat objek dari Class Induk (Hanya untuk menunjukkan method default)
        BangunDatar bangunD = new BangunDatar();
        bangunD.luas(); 
        bangunD.keliling();
        
        System.out.println("--------------------------------");

        // 2. Membuat objek Persegi dan langsung mengisi nilai Sisi (Polimorfisme)
        Persegi persegi = new Persegi(5); // Sisi = 5
        
        // 3. Membuat objek Segitiga
        Segitiga segitiga = new Segitiga(5, 10); // Alas = 5, Tinggi = 10
        
        // 4. Membuat objek Lingkaran
        Lingkaran lingkaran = new Lingkaran(10); // Jari-jari = 10

        
        // Menampilkan Hasil
        System.out.println("Luas Persegi: " + persegi.luas());
        System.out.println("Keliling Persegi: " + persegi.keliling());
        
        System.out.println("Luas Segitiga: " + segitiga.luas());
        
        System.out.println("Luas Lingkaran: " + lingkaran.luas());
        System.out.println("Keliling Lingkaran: " + lingkaran.keliling());
    }
}
