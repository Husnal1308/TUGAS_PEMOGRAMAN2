package pertemuan5_S3_OVERRIDING;

public class MobilMatic extends Mobil { 
    
    // Anotasi @Override untuk menandakan bahwa method ini menimpa method induk
    @Override 
    public void jalankan() {
        // Implementasi method yang spesifik untuk mobil matic
        System.out.println("Gigi di posisi D, tekan gas.");
    }
}
