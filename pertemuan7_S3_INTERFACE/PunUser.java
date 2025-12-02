package pertemuan7_S3_INTERFACE;

public class PunUser {
    private Pun pun; // Atribut untuk menyimpan objek HP (interface Pun)

    // Konstruktor
    public PunUser(Pun pun) {
        this.pun = pun;
    }

    // Method untuk mengoperasikan HP
    public void turnOnThePhone() {
        this.pun.powerOn();
    }

    public void turnOffThePhone() {
        this.pun.powerOff();
    }

    public void makePhoneLouder() {
        this.pun.volumeUp();
    }

    public void makePhoneSilent() {
        this.pun.volumeDown();
    }
}