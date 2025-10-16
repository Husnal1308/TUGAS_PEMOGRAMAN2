/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pertemuan1_S3;

/**
 *
 * @author USER
 */
import java.util.Scanner;
public class prinsipdasar_JAVA {
   public static void main(String[] args) {
        // Membuat scanner
        Scanner input = new Scanner(System.in);

        // Data menu
        String[] menu = {"Nasi Goreng", "Mie Ayam", "Soto Ayam"};
        int[] harga = {15000, 12000, 10000};

        // Tampilkan daftar menu
        System.out.println("=== MENU MAKANAN ===");
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i+1) + ". " + menu[i] + " - Rp. " + harga[i]);
        }

        // Input pilihan user
        System.out.print("\nPilih menu (1-3): ");
        int pilihan = input.nextInt();

        // Input jumlah pesanan
        System.out.print("Masukkan jumlah: ");
        int jumlah = input.nextInt();

        // Proses pesanan
        if (pilihan >= 1 && pilihan <= 3) {
            int total = harga[pilihan-1] * jumlah;

            // Cetak struk
            System.out.println("\n=== STRUK PEMBELIAN ===");
            System.out.println("Menu     : " + menu[pilihan-1]);
            System.out.println("Harga    : Rp. " + harga[pilihan-1]);
            System.out.println("Jumlah   : " + jumlah);
            System.out.println("Total    : Rp. " + total);
            System.out.println("=======================");
        } else {
            System.out.println("Pilihan tidak valid!");
        }

        // Tutup scanner (good practice)
        input.close();
    }
}
