package pertemuan5_S3_INHERITANCE2;

public class Main{ 
    public static void main(String[] args) {
        // Membuat objek dari masing-masing kelas
        BangunDatar bangunDatar = new BangunDatar();
        
        Persegi persegi = new Persegi();
        persegi.sisi = 5; // Mengisi atribut kelas anak
        
        Lingkaran lingkaran = new Lingkaran();
        lingkaran.r = 22;
        
        PersegiPanjang persegiPanjang = new PersegiPanjang();
        persegiPanjang.lebar = 4;
        persegiPanjang.panjang = 8;
        
        Segitiga segitiga = new Segitiga();
        segitiga.alas = 12;
        segitiga.tinggi = 8;
       
        // Menunjukkan Inheritance
        bangunDatar.luas();
        bangunDatar.keliling();
        
        lingkaran.luas();
        lingkaran.keliling();
        
        persegi.luas();
        persegi.keliling();
        
        persegiPanjang.luas();
        persegiPanjang.keliling();
        
        segitiga.luas();
        segitiga.keliling();
    }
}
