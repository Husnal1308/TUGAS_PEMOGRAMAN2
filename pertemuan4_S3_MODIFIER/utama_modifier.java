/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pertemuan4_S3_MODIFIER;

/**
 *
 * @author USER
 */
class utama_modifier {
    public static void main(String[] args){
        mahasiswa_modifier m1 = new mahasiswa_modifier("Muhammad Haiqal Nabil", "2455201037", "Ilmu Komputer", 3.5);
        m1.tampilkanData();
        m1.updateipk(4.0);
        m1.tampilkanData();
        m1.CekSPP(true);
        System.out.println("Predikat yang diperoleh " + m1.Predikat());
    }
}

