package main.java.assignments.assignment2;

public class Menu {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
        // TODO: buat constructor untuk class ini
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getNamaMakanan() {
        return namaMakanan;
    }

    // Setter for namaMakanan
    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    // Getter for harga
    public double getHarga() {
        return harga;
    }

    // Setter for harga
    public void setHarga(double harga) {
        this.harga = harga;
    }
}
