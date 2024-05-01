package assignments.assignment2;

import java.util.ArrayList;

public class User {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String name;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private ArrayList<Order> orderHistory;
    public String role;
   

    public User(String nama, String nomorTelepon, String email, String lokasi, String role) {
        // TODO: buat constructor untuk class ini
        this.name = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.orderHistory = new ArrayList<>();
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }
}