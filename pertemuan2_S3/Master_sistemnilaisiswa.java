/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pertemuan2_S3;

/**
 *
 * @author USER
 */
public class Master_sistemnilaisiswa {
    private String nama;
    private String nis;
    private double nilai;

    // Konstruktor 
    public Master_sistemnilaisiswa(String nama, String nis, double nilai) {
        this.nama = nama;
        this.nis = nis;
        this.nilai = nilai;
    }

    // Method untuk menampilkan data siswa
    public void tampilkanData() {
        System.out.println("Nama : " + nama);
        System.out.println("NIS  : " + nis);
        System.out.println("Nilai: " + nilai);
    }
}