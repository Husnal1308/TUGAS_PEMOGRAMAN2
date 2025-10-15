/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pertemuan3_S3;

/**
 *
 * @author USER
 */
public class utama {
    public static void main(String[] args){
        mahasiswa m1 = new mahasiswa("Muhammad Haiqal Nabil", "2455201037", "Ilmu Komputer", 3.5);
        m1.tampilkanData();
        m1.updateipk(4.0);
        m1.tampilkanData();
        m1.CekSPP(false);
        System.out.println("Predikat yang diperoleh " + m1.Predikat());
        
        
    }
}
