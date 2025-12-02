package pertemuan7_S3_INTERFACE;

public interface Pun {
    // Konstanta (Otomatis public static final)
    int MAX_VOLUME = 100;
    int MIN_VOLUME = 0;

    // Method (Otomatis public abstract)
    void powerOn();
    void powerOff();
    void volumeUp();
    void volumeDown();
}
