package pertemuan6_S3_POLIMORFISME;

public class Lingkaran {
    // Method 1: Menghitung luas menggunakan jari-jari (tipe data float)
    public float luas(float r) {
        // Rumus Luas = Pi * r * r
        // Casting ke float diperlukan karena Math.PI bertipe double
        return (float) (Math.PI * r * r); 
    }

    // Method 2: Menghitung luas menggunakan diameter (tipe data double)
    // Ini adalah Method Overloading karena memiliki nama method yang sama tetapi tipe parameter berbeda.
    public double luas(double d) {
        // Rumus Luas = 1/4 * Pi * d * d
        // 0.25 sama dengan 1/4
        return Math.PI * d * d * 0.25; 
    }

    public static void main(String[] args) {
        // Membuat objek dari kelas Lingkaran
        Lingkaran l = new Lingkaran();
        
        // Inisialisasi data
        float jari_jari = 10;
        double diameter = 20;

        // Memanggil Method 1 (luas) dengan parameter 'float'
        // Compiler memilih method luas(float r)
        System.out.println("Luas Lingkaran dengan jari-jari " + jari_jari + " = " + l.luas(jari_jari));
        
        // Memanggil Method 2 (luas) dengan parameter 'double'
        // Compiler memilih method luas(double d)
        System.out.println("Luas Lingkaran dengan diameter " + diameter + " = " + l.luas(diameter));
    }
}
