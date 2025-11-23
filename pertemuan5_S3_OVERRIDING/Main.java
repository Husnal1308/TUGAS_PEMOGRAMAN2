package pertemuan5_S3_OVERRIDING;

public class Main {
    public static void main(String[] args) {
        // Membuat objek dari kelas induk
        Kucing kucing = new Kucing();
        
        // Membuat objek dari kelas anak
        KucingAnggora anggora = new KucingAnggora();

        System.out.println("--- Objek Kucing (Kelas Induk) ---");
        kucing.suara(); // Output: Kucing bersuara meong...

        System.out.println("\n--- Objek KucingAnggora (Kelas Anak) ---");
        // Memanggil method yang sudah di-override
        anggora.suara(); // Output: Kucing Anggora bersuara Meong... Meong...
        anggora.nama(); 
    }
}
