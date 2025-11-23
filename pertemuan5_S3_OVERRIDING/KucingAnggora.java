package pertemuan5_S3_OVERRIDING;

// KucingAnggora mewarisi Kucing
public class KucingAnggora extends Kucing {
    
    // Overriding Method 'suara()' dari kelas induk
    @Override // Annotation ini opsional, tetapi disarankan
    public void suara() {
        System.out.println("Kucing Anggora bersuara Meong... Meong...");
    }
    
    // Method tambahan di kelas anak
    public void nama() {
        System.out.println("Ini adalah Kucing Anggora");
    }
}
