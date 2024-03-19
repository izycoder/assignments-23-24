package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private ArrayList<Menu> menu;
    // private static ArrayList<Restaurant> restoList = new ArrayList<>();


    public Restaurant(String nama){
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.menu = new ArrayList<>();
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public void tambahMenu(Menu menu) {
        this.menu.add(menu);
    }

    public void hapusMenu(Menu menu) {
        this.menu.remove(menu);
    }
    
}

