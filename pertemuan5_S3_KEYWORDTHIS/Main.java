package pertemuan5_S3_KEYWORDTHIS;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        
        // Mengisi nilai atribut melalui method setter
        user.setUsername("admin"); 
        user.setPassword("rahasia123");
        
        // Menampilkan nilai yang telah diatur
        System.out.println("Username: " + user.getUsername()); 
        
        // Jika kode di User.java ditulis tanpa this (misal: username = username), 
        // maka output di atas akan kosong (null) karena atribut class tidak terisi dengan benar.
    }
}
