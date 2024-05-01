package assignments.assignment2;

public class Order {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant resto;
    private Menu[] items;
    private String lokasi;
    private boolean orderFinished = false;


    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        // TODO: buat constructor untuk class ini
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.resto = resto;
        this.items = items;
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
     // Getter untuk orderId
     public String getOrderId() {
        return orderId;
    }

    // Getter untuk tanggal
    public String getTanggal() {
        return tanggal;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    // Getter for lokasi
    public String getLokasi() {
        return lokasi;
    }

    // Getter untuk ongkir
    public int getOngkir() {

        return ongkir;
    }

    // Getter untuk resto
    public Restaurant getResto() {
        return resto;
    }

    // Getter untuk items
    public Menu[] getItems() {
        return items;
    }

    public double hitungTotal() {
        double total = 0;
        for (Menu item : items) {
            total += item.getHarga();
        }
        total += ongkir; // Menambahkan ongkir ke total harga
        return total;
    }
    public boolean getOrderFinished(){
        return orderFinished;
    }
    public boolean setOrderFinished(boolean orderFinished){
        return this.orderFinished = orderFinished;
    }
}
