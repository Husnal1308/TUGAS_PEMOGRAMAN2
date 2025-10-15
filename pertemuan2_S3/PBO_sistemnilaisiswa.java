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
public class PBO_sistemnilaisiswa {
   public static void main(String[] args) {
        // Membuat beberapa objek siswa
        Master_sistemnilaisiswa s1 = new Master_sistemnilaisiswa("Andi", "1001", 85);
        Master_sistemnilaisiswa s2 = new Master_sistemnilaisiswa("Budi", "1002", 65);
        Master_sistemnilaisiswa s3 = new Master_sistemnilaisiswa("Citra", "1003", 90);

        // Menampilkan data siswa
        s1.tampilkanData();
        s2.tampilkanData();
        s3.tampilkanData();
    }
}
       