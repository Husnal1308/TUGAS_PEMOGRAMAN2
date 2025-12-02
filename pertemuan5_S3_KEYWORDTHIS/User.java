package pertemuan5_S3_KEYWORDTHIS;

public class User {
    
    // Atribut Class
    private String username; 
    private String password;

    /**
     * Method setUsername() dengan parameter yang namanya sama (username) dengan atribut class.
     */
    public void setUsername(String username) {
        // this.username merujuk pada atribut class (private String username;)
        // = username merujuk pada parameter method (String username)
        this.username = username; 
    }
    
    public void setPassword(String password) {
        // Tanpa 'this', Java akan bingung antara atribut class atau parameter
        this.password = password; 
    }
    
    // Method untuk menampilkan nilai (Getter)
    public String getUsername() {
        return this.username;
    }
}
