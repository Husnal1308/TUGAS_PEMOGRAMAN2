package pertemuan7_S3_POLIMORFISMEDINAMIS;

public class Segitiga extends BangunDatar {
    
    private int alas;
    private int tinggi;
    private int i0;

    // Konstruktor
    public Segitiga(int alas, int tinggi) {
        this.alas = alas;
        this.tinggi = tinggi;
    }

    /*Segitiga(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates
    }*/

    // Method luas() ditindih (Override)
    @Override
    public float luas() {
        // Rumus luas segitiga: 0.5 * alas * tinggi
        return (float) (0.5 * this.alas * this.tinggi); 
    }
    
    // Method keliling() tidak diimplementasikan (menggunakan default dari BangunDatar)
    // Dalam studi kasus ini, keliling Segitiga tidak dipanggil di MainClass.
}
