package pertemuan7_S3_INTERFACE;

public class Xiaomi implements Pun {
    private int volume;
    private boolean isPowerOn;

    // Konstruktor
    public Xiaomi() {
        // Volume awal
        this.volume = 50;
        this.isPowerOn = false; // Status awal HP mati
    }

    // Method Getter untuk mendapatkan volume saat ini
    public int getVolume() {
        return this.volume;
    }

    // Implementasi method dari interface Pun
    @Override
    public void powerOn() {
        isPowerOn = true;
        System.out.println("Handphone menyala");
        System.out.println("Selamat datang di Xiaomi Phone");
        System.out.println("Android version 100");
    }

    @Override
    public void powerOff() {
        isPowerOn = false;
        System.out.println("Handphone dimatikan");
    }

    @Override
    public void volumeUp() {
        if (isPowerOn) {
            if (this.getVolume() == MAX_VOLUME) { // MAX_VOLUME dari interface Pun
                System.out.println("Volume Full! " + this.getVolume() + "%");
            } else {
                volume += 10;
                System.out.println("Volume sekarang: " + this.getVolume() + "%");
            }
        } else {
            System.out.println("Nyalakan dulu hp-nya");
        }
    }

    @Override
    public void volumeDown() {
        if (isPowerOn) {
            if (this.getVolume() == MIN_VOLUME) { // MIN_VOLUME dari interface Pun
                System.out.println("Volume sudah mentok paling rendah! " + this.getVolume() + "%");
            } else {
                volume -= 10;
                System.out.println("Volume sekarang: " + this.getVolume() + "%");
            }
        } else {
            System.out.println("Nyalakan dulu hp-nya");
        }
    }
}
