package pertemuan7_S3_POLIMORFISMEDINAMIS;

public class Persegi extends BangunDatar {
    
    private int Sisi;
    
    // Konstruktor
    public Persegi(int Sisi) {
        this.Sisi = Sisi;
    }

    // Method luas() ditindih (Override)
    @Override
    public float luas() {
        return (float) (this.Sisi * this.Sisi);
    }

    // Method keliling() ditindih (Override)
    @Override
    public float keliling() {
        return (float) (4 * this.Sisi);
    }
}

