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
class mahasiswa_modifier {
    private String nama = " ";
    private String NIM = " ";
    private String Prodi = " ";
    private double IPK = 0;
    private String predikat = " ";
    public boolean SPP = true;
    
    //Konstruktor adalah method khusus yang dibuat saat objek baru dibuat untuk menginisialisaaikan objek tersebut.
    public mahasiswa_modifier(String nama, String NIM, String Prodi, double IPK) {
        this.nama = nama;
        this.NIM = NIM;
        this.Prodi = Prodi;
        this.IPK = IPK;
        this.predikat = predikat;
    }
        
// Method untuk menampilkan data mahasiswa
    public void tampilkanData() {
        System.out.println("Nama : " + nama);
        System.out.println("NIM : " + NIM);
        System.out.println("Prodi : " + Prodi);
        System.out.println("IPK : " + IPK);
       
    }
    
    String Predikat(){
        if (IPK >= 3.5){
            predikat = "Cumloude";
        }else if(IPK >= 3.25){
            predikat = "Sangat Memuaskan"; 
        }
        return predikat;         
     }
    
    public void updateipk(double IPK){
        this.IPK = IPK;
    }
    
    boolean CekSPP(boolean SPP){
        if(SPP == true){
            System.out.println("lunas");
        }else {
            System.out.println("belum lunas");
        }return SPP;
    }
    
}
//modifier (tingkatan akses)--> class, method, variable 
//                          --> public, private, protected, No modifier