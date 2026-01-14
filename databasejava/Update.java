/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasejava;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Update {

    Koneksi konek = new Koneksi();

//, String nama, String alamat, String jenis
    public void update(int nim, String nama, String alamat, String jenis) {

        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            String sql_alamat = "update mhs_ilkom set alamat='" + alamat + "'where nim='" + nim + "'";
            String sql_nama = "update mhs_ilkom set nama='" + nama + "'where nim='" + nim + "'";
             String sql_jenis = "update mhs_ilkom set jeniskelamin='" + jenis + "'where nim='" + nim + "'";
           
            
//String sql_nim = "update identitas set nim ='" + nim + "' where nim = '" + nim + "'";
            //    String sql = "update identitas set nim ='" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            statement.executeUpdate(sql_nama);
            statement.executeUpdate(sql_alamat);
            statement.executeUpdate(sql_jenis);
            statement.close();

            JOptionPane.showMessageDialog(null, "Berhasil Diubah");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
