package pertemuan5_S3_OVERRIDING;

public class Main {
    public static void main(String[] args) {
        MobilMatic matic = new MobilMatic(); // Membuat objek mobil matic
        
        // Ketika dipanggil, method yang dieksekusi adalah versi override dari MobilMatic,
        // BUKAN versi dari Class Mobil Induk.
        matic.jalankan(); 
    }
}
