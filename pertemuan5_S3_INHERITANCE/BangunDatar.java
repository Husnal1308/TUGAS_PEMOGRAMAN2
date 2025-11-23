package pertemuan5_S3_INHERITANCE;

public class BangunDatar {
    // Method Luas (akan ditimpa di kelas anak)
    public float luas() {
        System.out.println("Menghitung luas bangun datar");
        return 0; // Mengembalikan nilai default
    }
    
    // Method Keliling (akan ditimpa di kelas anak)
    public float keliling() {
        System.out.println("Menghitung keliling bangun datar");
        return 0; // Mengembalikan nilai default
    }
}
