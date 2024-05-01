package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment2.Restaurant;
import assignments.assignment2.MainMenu;
import assignments.assignment2.Menu;

//TODO: Extends Abstract yang diberikan
public class AdminSystemCLI extends UserSystemCLI {

    private ArrayList<Restaurant> restoList;

    public AdminSystemCLI(ArrayList<Restaurant> restoList) {
        this.restoList = restoList;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    public boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Tambah Restoran----------------");
        String namaRestoran = null;
        boolean restoranValid = false;
        while (!restoranValid) { // ketika restoran valid
        
        System.out.print("Nama: ");
        namaRestoran = input.nextLine();
        if (namaRestoran.replaceAll(" " , "").length() < 4) { // menghilangkan spasi untuk input nama
            System.out.println("Nama Restoran tidak valid!\n"); // jika salah maka error
        }
        else{
        // Memeriksa apakah restoran sudah terdaftar sebelumnya
        boolean restoranTerdaftar = false;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                restoranTerdaftar = true;
                break;
            }
        }
        if (restoranTerdaftar) { // jika restoran sudah ada
            System.out.println("Restoran dengan nama " + namaRestoran + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n");
        } 
        else { // jika tidak lanjut input makanan
            boolean inputBenar = false;
                System.out.print("Jumlah Makanan: "); // input jumlah
                int jumlahMakanan = input.nextInt();
                input.nextLine(); // Membersihkan buffer
                ArrayList<Menu> menuRestoran = new ArrayList<>(); // inisiasi arraylist menurestoran
                ArrayList<String> makananList = new ArrayList<>(); // inisiasi arraylist makananlist
                for (int i = 0; i < jumlahMakanan; i++) {
                    String makanan = input.nextLine();
                    makananList.add(makanan); // memasukkan ke dalam arraylist
                }
                inputBenar = true; // input jadi benar
                for (String makanan : makananList) {
                    int index = makanan.lastIndexOf(' '); // mencari spasi terdekat
                    if (index == -1){ // ketika panjang string hanya 1
                        System.out.println("Harga menu harus berupa bilangan bulat! \n"); // jika harga salah
                        inputBenar = false;
                        break;
                    }
                    String namaMakanan = makanan.substring(0, index); // nama makanannya
                    try {
                        double hargaMakanan = Double.parseDouble(makanan.substring(index + 1)); // validasi harga
                        menuRestoran.add(new Menu(namaMakanan, hargaMakanan));
                    } catch (NumberFormatException nfe) {
                        System.out.println("Harga menu harus berupa bilangan bulat! \n"); // harga salah
                        inputBenar = false;
                        break;
                    }
                }
                if (inputBenar) { // jika input benar
                    // Membuat objek Restaurant dan menambahkannya ke dalam daftar restoran
                    Restaurant newRestoran = new Restaurant(namaRestoran);
                    restoList.add(newRestoran);
                    for (Menu menu : menuRestoran) {
                        newRestoran.tambahMenu(menu);
                    }
                    System.out.println("Restaurant " + namaRestoran + " berhasil terdaftar."); // berhasil
                    restoranValid = true;
                }
        }
        }
    }              
    }

    protected void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Hapus Restoran----------------");
        boolean sudahTerhapus = false;
        while (!sudahTerhapus){
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
        boolean restoranDitemukan = false;
        for (Restaurant resto : restoList){ {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) { // jika resto ada
                restoranDitemukan = true;
                // Hapus restoran dari sistem
                restoList.remove(resto); // remove resto 
                System.out.println("Restoran berhasil dihapus.");
                sudahTerhapus = true;
                break;
            }
        }
        }
        if (!restoranDitemukan) {
            System.out.println("Restoran tidak terdaftar pada sistem.\n"); // resto tidak ada 
        }
    }
    }
}
