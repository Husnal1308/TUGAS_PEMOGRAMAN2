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
public class mahasiswa1 {
    String nama = " ";
    String NIM = " ";
    String Prodi = " ";
    
    //Konstruktor adalah method khusus yang dibuat saat objek baru dibuat untuk menginisialisaaikan objek tersebut.
    public mahasiswa1(String nama, String NIM, String Prodi) {
        this.nama = nama;
        this.NIM = NIM;
        this.Prodi = Prodi;
    }
        
// Method untuk menampilkan data mahasiswa1
    public void tampilkanData() {
        System.out.println("Nama : " + nama);
        System.out.println("NIM : " + NIM);
        System.out.println("Prodi : " + Prodi);
    }
}
