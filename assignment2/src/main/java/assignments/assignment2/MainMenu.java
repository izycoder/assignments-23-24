package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;
    private static User userLoggedIn; 


    public static void main(String[] args) {
        boolean programRunning = true; // var boolean untuk while loop
        initUser(); // init user
        restoList = new ArrayList<>();
        boolean isLoggedIn = true; // is logged in untuk while loop
        while(programRunning){ // while loop
            printHeader();
            startMenu();
            int command = input.nextInt(); // input perintah
            input.nextLine();

            if(command == 1){
                userLoggedIn = null; 
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();
                
                // TODO: Validasi input login
                boolean nameExists = false;
                User pengguna = getUser(nama, noTelp);
                if (pengguna != null) {
                    System.out.println( "Selamat datang "+ nama + "!");
                    nameExists = true;
                }
                
                if (nameExists) {
                    // Proceed with login
                    userLoggedIn = new User(nama, noTelp, pengguna.getEmail(), pengguna.getLokasi(), pengguna.role); 
                    isLoggedIn = true;
                    if (userLoggedIn.role.equals("Customer")) {
                        while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan();
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
                    else{
                        isLoggedIn = true;
                        while (isLoggedIn){
                            menuAdmin();
                            int commandAdmin = input.nextInt();
                            input.nextLine();

                            switch(commandAdmin){
                                case 1 -> handleTambahRestoran();
                                case 2 -> handleHapusRestoran();
                                case 3 -> isLoggedIn = false;
                                default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                            }
                        }
                    }
                }
                else {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                }
                    
                }
                else if(command == 2){
                    programRunning = false;
                }
                else{
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                }
                
            }
            System.out.println ("\nTerima kasih telah menggunakan DepeFood ^___^");
        }
    

    public static User getUser(String nama, String nomorTelepon){
        // TODO: Implementasi method untuk mendapat user dari userList
        for (User user : userList) {
            if (user.getName().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)) {
                return user;
            }
        }
        return null; // Return null if user is not found
    }
    

    public static void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("\n--------------Buat Pesanan----------------");
        boolean pesananSukses = false;
        while (!pesananSukses) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restoAda = false;
            Restaurant restoraunt = null;
            // Memeriksa apakah restoran terdaftar
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    restoraunt = resto;
                    restoAda = true;
            }
            }
            if (restoAda){
                System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
                String tanggalPemesanan = input.nextLine();
                String[] parts = tanggalPemesanan.split("/");
                if (parts.length != 3) {
                    System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                    break;
                }
        
                for (String part : parts) {
                    if (!part.chars().allMatch(Character::isDigit)) {
                        System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                        break;
                    }
                }

                if(parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 4){
                    System.out.print("Jumlah Pesanan: ");
                    int jumlahPesanan = input.nextInt();
                    input.nextLine(); // Membersihkan buffer
                    System.out.println("Order:");
                    ArrayList<String> pesananList = new ArrayList<>();
                    for (int i = 0; i < jumlahPesanan; i++){
                        String namaMenu = input.nextLine();
                        pesananList.add(namaMenu); 
                    }
                    boolean semuaPesananTersedia = true;
                    int pesananBenar = 0;
                    boolean pesananTersedia = false;
                    for (String pesanan : pesananList) {
                        for (Menu menu : restoraunt.getMenu()) {
                            if (menu.getNamaMakanan().equalsIgnoreCase(pesanan)) {
                                pesananTersedia = true;
                                break;
                            }
                        }
                    }
                    if (pesananTersedia) {
                        semuaPesananTersedia = false;
                        String restaurantId = namaRestoran.substring(0, 4).toUpperCase(); // substring untuk id restoran
                        int jumlah = 0; // variabel jumlah bertipe int
                        String noTelepon = userLoggedIn.getNomorTelepon();
                        for (int i = 0; i < noTelepon.length() ; i++) { // for loop sepanjang notelp
                            int angka = Character.getNumericValue(noTelepon.charAt(i)); // mengambil char angka tiap notelp
                            jumlah += angka; // menjumlahkannya ke dalam jumlah
                        }
                        jumlah = jumlah % 100; // jumlah dimodulo 100
                        String kode = Integer.toString(jumlah); // kode adalah jumlah bertipe string
                        if (jumlah < 10){ // jika hasil modulo hanya 1 digit / kurang dari 10
                            kode = "0" + kode; // maka ditambahkan 0 di depan kode
                        }
                        String strChecksum = restaurantId + tanggalPemesanan.replace("/", "") + kode; // 14 kode pertama
                        String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // character set
                        int checkSum1 = 0; // variabel checksum1
                        int checkSum2 = 0; // variabel checksum2
                        for (int i = 0; i < strChecksum.length(); i ++){ // for loop sepanjang 14 kode pertama
                            char cek = strChecksum.charAt(i); // variabel cek untuk char ke i di 14 kode pertama
                            int index = characterSet.indexOf(cek); // index untuk mencari huruf di characterset sesuai index 
                            if (i % 2 == 0){ // jika for loop genap
                                checkSum1 += index; // checksum1 ditambahkan index
                            }
                            else{ // jika for loop ganjil
                                checkSum2 += index; // checksum2 ditambahkan index
                            } 
                        }
                        checkSum1 = checkSum1 % 36; // checksum 1 dimodulo 36
                        checkSum2 = checkSum2 % 36; // checksum 2 dimodulo 36
                        char cs1 = characterSet.charAt(checkSum1); // variabel char cs1 adalah index ke checksum1 di characterset
                        char cs2 = characterSet.charAt(checkSum2); // variabel char cs2 adalah index ke checksum2 di characterset
                    
                        String orderId = strChecksum + cs1 + cs2;
                        pesananSukses = true; 
                        String lokasi = userLoggedIn.getLokasi();
                        int biaya = 0;
                        switch (lokasi.toUpperCase()){ // switch case untuk lokasi
                            case "P": // ketika P 
                            biaya += 10000; // biaya 10000
                            break;
                            case "U": // ketika U
                            biaya += 20000; // biaya 20000
                            break;
                            case "T": // ketika T
                            biaya += 35000; // biaya 35000
                            break;
                            case "S": // ketika S
                            biaya += 40000; // biaya 40000
                            break;
                            case "B": // ketika B
                            biaya += 60000; // biaya 60000
                            break;
                        }
                        Menu[] pesananArray = new Menu[pesananList.size()];
                        for (int i = 0; i < pesananList.size(); i++) {
                            String namaMenu = pesananList.get(i);
                            // Assuming getMenuByName method is available in Restaurant class to get Menu object by name
                            for (Menu menu : restoraunt.getMenu()) {
                                if (menu.getNamaMakanan().equalsIgnoreCase(namaMenu)){
                                    // You need to implement this method in Restaurant class
                                    pesananArray[i] = menu;
                                    break;
                                }
                            }
                        }

                        // Create the Order object and add it to the orderList
                    semuaPesananTersedia = true;
                    Order order = new Order(orderId, tanggalPemesanan, biaya, restoraunt, pesananArray);
                    userLoggedIn.addToOrderHistory(order);
                    order.setLokasi(userLoggedIn.getLokasi());
                    System.out.print("Pesanan dengan ID "+ orderId + " diterima!\n");
                // Jika semua pesanan tidak tersedia di restoran, tampilkan pesan kesalahan
                if (!semuaPesananTersedia) {
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                }
    
                }
                else{
                    System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!");
                }
            }
            }
            else{
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
            }
        }
        
    }
    
    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("--------------Cetak Bill----------------");
        System.out.print("Masukkan Order ID: ");
        String orderId = input.nextLine();
        boolean orderAda = false;
        Order orderan = null;
        for (Order order : userLoggedIn.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                orderAda = true;
                orderan = order;
            }
        }
        if (orderAda){
            String lokasi = userLoggedIn.getLokasi();
            String biaya = "";
            String status = "";
            if (orderan.getOrderFinished()){
                status = "Finished";
            }
            else{
                status = "Not Finished";
            }
            // order = new Order(orderId, tanggal, biaya,resto, );
            System.out.println("\nBill:");
            System.out.println("Order ID: " + orderan.getOrderId());
            System.out.println("Tanggal Pemesanan: " + orderan.getTanggal());
            System.out.println("Restaurant: " + orderan.getResto().getNama());
            System.out.println("Lokasi Pengiriman: " + orderan.getLokasi());
            System.out.println("Status Pengiriman: " + status);
            System.out.println("Pesanan:");
            for (Menu item : orderan.getItems()) {
                if (item != null){
                System.out.println("- " + item.getNamaMakanan() + " Rp " + item.getHarga());}
            }
            System.out.println("Biaya Ongkos Kirim: Rp " + orderan.getOngkir());
            System.out.println("Total Biaya: Rp " + orderan.hitungTotal());
        }
        else {
            // If order is not found, display an error message
            System.out.println("Order dengan ID " + orderId + " tidak ditemukan.");
        }
    }
    
    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu----------------");
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
        boolean restoranAda = false;
        Restaurant restaurant = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                restoranAda = true;
                restaurant = resto;
            }
        if (restoranAda) {
            // Menampilkan menu restoran
            Menu[] sortedMenu = sortMenuByHarga(restaurant.getMenu());
            System.out.println("Menu:");
            for (int i = 0; i < sortedMenu.length; i++) {
                System.out.println((i + 1) + ". " + sortedMenu[i].getNamaMakanan() + " " + sortedMenu[i].getHarga());
            }
        } else {
            System.out.println("Restoran tidak terdaftar pada sistem.");
        }
    }

    }
    private static Menu[] sortMenuByHarga(ArrayList<Menu> menuList) {
        Menu[] sortedMenu = menuList.toArray(new Menu[0]);
        int n = sortedMenu.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Bandingkan harga
                if (sortedMenu[j].getHarga() > sortedMenu[j + 1].getHarga()) {
                    // Tukar posisi menu
                    Menu temp = sortedMenu[j];
                    sortedMenu[j] = sortedMenu[j + 1];
                    sortedMenu[j + 1] = temp;
                } else if (sortedMenu[j].getHarga() == sortedMenu[j + 1].getHarga()) {
                    // Jika harga sama, bandingkan nama menu secara alfabetis
                    if (sortedMenu[j].getNamaMakanan().compareToIgnoreCase(sortedMenu[j + 1].getNamaMakanan()) > 0) {
                        // Tukar posisi menu
                        Menu temp = sortedMenu[j];
                        sortedMenu[j] = sortedMenu[j + 1];
                        sortedMenu[j + 1] = temp;
                    }
                }
            }
        }
        return sortedMenu;
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        System.out.println("--------------Update Status Pesanan----------------");
        System.out.print("Order ID: ");
        String orderId = input.nextLine();
        
        boolean orderFound = false;
        for (Order order : userLoggedIn.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                orderFound = true;
                // Meminta input status baru
                System.out.print("Status: ");
                String newStatus = input.nextLine();
                String status = "";
                if (order.getOrderFinished()){
                    status = "Finished";
                }
                else{
                    status = "Not Finished";
                }
                // Memperbarui status pesanan
                if (status.equalsIgnoreCase(newStatus)) {
                    System.out.println("Status pesanan dengan ID " + orderId + " tidak berhasil diupdate!");
                } else {
                    order.setOrderFinished(true);
                    System.out.println("Status pesanan dengan ID " + orderId + " berhasil diupdate!");
                }
                
                break; // Keluar dari loop setelah menemukan pesanan
            }
        }
        
        // Menampilkan pesan jika order tidak ditemukan
        if (!orderFound) {
            System.out.println("Order ID tidak dapat ditemukan.");
        }
    }
    

    public static void handleTambahRestoran(){
    // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
    System.out.println("--------------Tambah Restoran----------------");
    String namaRestoran = null;
    boolean restoranValid = false;
    while (!restoranValid) {
    
    System.out.print("Nama: ");
    namaRestoran = input.nextLine();
    if (namaRestoran.replaceAll(" " , "").length() < 4) {
        System.out.println("Nama Restoran tidak valid!\n");
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
    if (restoranTerdaftar) {
        System.out.println("Restoran dengan nama " + namaRestoran + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n");
    } else {
        boolean inputBenar = false;
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            ArrayList<Menu> menuRestoran = new ArrayList<>();
            ArrayList<String> makananList = new ArrayList<>();
            for (int i = 0; i < jumlahMakanan; i++) {
                String makanan = input.nextLine();
                makananList.add(makanan);
            }
            inputBenar = true;
            for (String makanan : makananList) {
                int spaceIndex = makanan.lastIndexOf(' ');
                if (spaceIndex == -1){
                    System.out.println("Harga menu harus berupa bilangan bulat! \n");
                    inputBenar = false;
                    break;
                }
                String namaMakanan = makanan.substring(0, spaceIndex);
                try {
                    double hargaMakanan = Double.parseDouble(makanan.substring(spaceIndex + 1));
                    menuRestoran.add(new Menu(namaMakanan, hargaMakanan));
                } catch (NumberFormatException nfe) {
                    System.out.println("Harga menu harus berupa bilangan bulat! \n");
                    inputBenar = false;
                    break;
                }
            }
            if (inputBenar) {
                // Membuat objek Restaurant dan menambahkannya ke dalam daftar restoran
                Restaurant newRestoran = new Restaurant(namaRestoran);
                restoList.add(newRestoran);
                for (Menu menu : menuRestoran) {
                    newRestoran.tambahMenu(menu);
                }
                System.out.println("Restaurant " + namaRestoran + " berhasil terdaftar.");
                restoranValid = true;
            }
    }
}
}
}



    public static void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Hapus Restoran----------------");
        boolean sudahTerhapus = false;
        while (!sudahTerhapus){
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
        boolean restoranDitemukan = false;
        for (Restaurant resto : restoList){ {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                restoranDitemukan = true;
                // Hapus restoran dari sistem
                restoList.remove(resto);
                System.out.println("Restoran berhasil dihapus.");
                sudahTerhapus = true;
                break;
            }
        }

        if (!restoranDitemukan) {
            System.out.println("Restoran tidak terdaftar pada sistem.\n");
     
        }
    }
}
    }
    

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
