package pertemuan7_S3_POLIMORFISMEDINAMIS;

public class Lingkaran extends BangunDatar {
    
    private int r; // Jari-jari

    // Konstruktor
    public Lingkaran(int r) {
        this.r = r;
    }

    // Method luas() ditindih (Override)
    @Override
    public float luas() {
        // Rumus luas lingkaran: PI * r^2
        return (float) (Math.PI * this.r * this.r);
    }

    // Method keliling() ditindih (Override)
    @Override
    public float keliling() {
        // Rumus keliling lingkaran: 2 * PI * r
        return (float) (2 * Math.PI * this.r);
    }
}