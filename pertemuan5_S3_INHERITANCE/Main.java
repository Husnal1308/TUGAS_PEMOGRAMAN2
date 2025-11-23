package pertemuan5_S3_INHERITANCE;

public class Main{ 
    public static void main(String[] args) {
        // Membuat objek dari masing-masing kelas
        BangunDatar bangunDatar = new BangunDatar();
        Persegi persegi = new Persegi();
        Lingkaran lingkaran = new Lingkaran();
        
        // Mengisi atribut kelas anak
        persegi.sisi = 5;
        lingkaran.r = 22;

        System.out.println("--- Memanggil Method dari Kelas Induk ---");
        bangunDatar.luas();      // Output: Menghitung luas bangun datar
        
        System.out.println("\n--- Memanggil Method Warisan ---");
        // Meskipun tidak ada method 'luas' di kelas Persegi, ia mewarisinya dari BangunDatar
        persegi.luas();     // Output: Menghitung luas bangun datar
        lingkaran.keliling(); // Output: Menghitung keliling bangun datar
    }
}
