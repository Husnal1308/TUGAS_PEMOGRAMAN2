package pertemuan7_S3_INTERFACE;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Deklarasi Scanner
        Scanner input = new Scanner(System.in);
        String aksi = "";

        // Bikin objek Xiaomi (sebagai implementasi interface Pun)
        // Objek dari interface Pun, di-instantiate dengan New Xiaomi
        Pun redmi3Pro = new Xiaomi(); 

        // Bikin objek PunUser untuk mengoperasikan HP
        // Objek user menerima objek HP Redmi3Pro
        PunUser caris = new PunUser(redmi3Pro); 

        // Nyalakan HP awal (opsional, di video dipanggil di awal)
        caris.turnOnThePhone(); 
        
        System.out.println("================================");

        while (true) {
            System.out.println("Aplikasi Interface");
            System.out.println("1. Nyalakan HP");
            System.out.println("2. Matikan HP");
            System.out.println("3. Perbesar Volume");
            System.out.println("4. Kecilkan Volume");
            System.out.println("0. Keluar");
            System.out.print("Pilih Aksi: ");
            
            aksi = input.nextLine();

            if (aksi.equals("1")) {
                caris.turnOnThePhone();
            } else if (aksi.equals("2")) {
                caris.turnOffThePhone();
            } else if (aksi.equals("3")) {
                caris.makePhoneLouder();
            } else if (aksi.equals("4")) {
                caris.makePhoneSilent();
            } else if (aksi.equals("0")) {
                System.out.println("Program selesai.");
                System.exit(0);
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
}

