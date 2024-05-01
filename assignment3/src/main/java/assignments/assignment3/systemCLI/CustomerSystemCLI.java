package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment2.Menu;
import assignments.assignment2.Order;
import assignments.assignment2.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.MainMenu;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI{
    private ArrayList<Restaurant> restoList;
    public User userLoggedIn;

    public CustomerSystemCLI(ArrayList<Restaurant> restoList, User userLoggedIn) {
        this.restoList = restoList;
        this.userLoggedIn = userLoggedIn;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("\n--------------Buat Pesanan----------------");
        userLoggedIn = MainMenu.userLoggedIn;
        boolean pesananSukses = false; // boolean untuk while loop
        while (!pesananSukses) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restoAda = false;
            Restaurant restoraunt = null;
            for (Restaurant resto : restoList) { // Memeriksa apakah restoran terdaftar
                if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                    restoraunt = resto;
                    restoAda = true;
            }
            }
            if (restoAda){ // jika resto ada
                System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
                String tanggalPemesanan = input.nextLine();
                String[] parts = tanggalPemesanan.split("/"); // cek format tanggal
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
                    System.out.print("Jumlah Pesanan: "); // jika tanggal benar lanjut input jumlah pesanan
                    int jumlahPesanan = input.nextInt();
                    input.nextLine(); // Membersihkan buffer
                    System.out.println("Order:");
                    ArrayList<String> pesananList = new ArrayList<>(); // arraylist pesanan
                    for (int i = 0; i < jumlahPesanan; i++){
                        String namaMenu = input.nextLine();
                        pesananList.add(namaMenu); // masukkan namameu kedalam arraylist
                    }
                    boolean menuSesuai = true;
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
                        menuSesuai = false;
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
                        Menu[] pesananArray = new Menu[pesananList.size()]; // array pesanan
                        for (int i = 0; i < pesananList.size(); i++) {
                            String namaMenu = pesananList.get(i);
                            // Assuming getMenuByName method is available in Restaurant class to get Menu object by name
                            for (Menu menu : restoraunt.getMenu()) {
                                if (menu.getNamaMakanan().equalsIgnoreCase(namaMenu)){ 
                                    pesananArray[i] = menu; // jika nama sesuai masuk ke dalam array
                                    break;
                                }
                            }
                        }
                    menuSesuai = true;
                    Order order = new Order(orderId, tanggalPemesanan, biaya, restoraunt, pesananArray); // menambahkn ke dalam objek order
                    userLoggedIn.addToOrderHistory(order); // menambahkan history
                    order.setLokasi(userLoggedIn.getLokasi());
                    System.out.print("Pesanan dengan ID "+ orderId + " diterima!\n");
                // Jika semua pesanan tidak tersedia di restoran, tampilkan pesan kesalahan
                if (!menuSesuai) { // jika tidak sesuai
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

    void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("--------------Cetak Bill----------------");
        System.out.print("Masukkan Order ID: ");
        userLoggedIn = MainMenu.userLoggedIn;
        String orderId = input.nextLine();
        boolean orderAda = false;
        Order orderan = null;
        for (Order order : userLoggedIn.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                orderAda = true;
                orderan = order;
            }
        }
        if (orderAda){ // jika order ada
            String status = ""; // cek status
            if (orderan.getOrderFinished()){
                status = "Finished";
            }
            else{
                status = "Not Finished";
            }
            System.out.println("\nBill:"); // print bill
            System.out.println("Order ID: " + orderan.getOrderId());
            System.out.println("Tanggal Pemesanan: " + orderan.getTanggal());
            System.out.println("Restaurant: " + orderan.getResto().getNama());
            System.out.println("Lokasi Pengiriman: " + orderan.getLokasi());
            System.out.println("Status Pengiriman: " + status);
            System.out.println("Pesanan:");
            for (Menu item : orderan.getItems()) {
                if (item != null){
                System.out.println("- " + item.getNamaMakanan() + " Rp " + String.format("%.0f", item.getHarga()));}
            }
            System.out.println("Biaya Ongkos Kirim: Rp " + orderan.getOngkir());
            System.out.println("Total Biaya: Rp " +  String.format("%.0f", orderan.hitungTotal()));
        }
        else {
            System.out.println("Order dengan ID " + orderId + " tidak ditemukan."); // jika order id salah
        }
    }

    void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu----------------");
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
        boolean restoranAda = false;
        Restaurant restaurant = null;
        for (Restaurant resto : restoList) { // cek resto
            if (resto.getNama().equalsIgnoreCase(namaRestoran)){
                restoranAda = true;
                restaurant = resto;
            }
        if (restoranAda) { // jika ada
            // Menampilkan menu restoran
            Menu[] sortedMenu = sortMenuByHarga(restaurant.getMenu()); // sorting dengan BUBBLE SORT
            System.out.println("Menu:");
            for (int i = 0; i < sortedMenu.length; i++) {
                System.out.println((i + 1) + ". " + sortedMenu[i].getNamaMakanan() + " " + sortedMenu[i].getHarga());
            }
        } else {
            System.out.println("Restoran tidak terdaftar pada sistem.");
        }
    }
    }

    void handleBayarBill(){
    // TODO: Implementasi method untuk handle ketika customer ingin membayar tagihan
    System.out.println("--------------Bayar Bill----------------");
    System.out.print("Masukkan Order ID: ");
    String orderId = input.nextLine();
    boolean orderAda = false;
    Order orderan = null;
    for (Order order : userLoggedIn.getOrderHistory()) {
        if (order.getOrderId().equals(orderId)) {
            orderAda = true;
            orderan = order;
            break;
        }
    }
    if (orderAda){ // jika order ada
        if (orderan.getOrderFinished()) { // cek apakah pesanan sudah selesai
            System.out.println("Pesanan dengan ID " + orderId + " telah selesai.");
            return;
        }
        
        double totalBiaya = orderan.hitungTotal();
        System.out.println("\nDetail Tagihan:");
        System.out.println("Order ID: " + orderan.getOrderId());
        System.out.println("Total Biaya: Rp " + totalBiaya);

        // Menampilkan opsi pembayaran
        System.out.println("\nOpsi Pembayaran:");
        System.out.println("1. Kartu Kredit");
        System.out.println("2. Debit");

        System.out.print("Pilihan Metode Pembayaran: ");
        int metodePembayaran = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        User user = (User) userLoggedIn;
        switch (metodePembayaran) {
            case 1:
                // Proses pembayaran dengan kartu kredit
                if (user.getPayment() instanceof CreditCardPayment) { 
                    CreditCardPayment creditCardPayment = (CreditCardPayment) user.getPayment();
                    user.setSaldo(creditCardPayment.processPayment(user.getSaldo(), totalBiaya));
                    handleUpdateStatusPesanan(orderId);
                }
                else{
                    System.out.println("User belum memiliki metode pembayaran ini!");
                }
                break;
            case 2:
                // Proses pembayaran dengan debit
                if (user.getPayment() instanceof DebitPayment) {
                    DebitPayment debitPayment = (DebitPayment) user.getPayment();
                    user.setSaldo(debitPayment.processPayment(user.getSaldo(), totalBiaya));
                    handleUpdateStatusPesanan(orderId);
                }
                else{
                    System.out.println("User belum memiliki metode pembayaran ini!");
                }
                break;
            default:
                System.out.println("Metode pembayaran tidak valid.");
                break;
        }
    }
    else {
        System.out.println("Order dengan ID " + orderId + " tidak ditemukan.");
    }
}

    

    void handleUpdateStatusPesanan(String orderId){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        boolean orderFound = false;
        for (Order order : userLoggedIn.getOrderHistory()) { // cek order id
            if (order.getOrderId().equals(orderId)) {
                orderFound = true;
                System.out.print("Status: "); // minta status baru
                String status = "Finished"; // mengganti status lama
                order.setOrderFinished(true);
                
                break; // Keluar dari loop setelah menemukan pesanan
            }
        }
        
        // Menampilkan pesan jika order tidak ditemukan
        if (!orderFound) {
            System.out.println("Order ID tidak dapat ditemukan.");
        }
    }

    void handleCekSaldo(){
        // TODO: Implementasi method untuk handle ketika customer ingin mengecek saldo yang dimiliki
        System.out.println("Sisa saldo sebesar Rp " + userLoggedIn.getSaldo());
    }

    private static Menu[] sortMenuByHarga(ArrayList<Menu> menuList) { // sorting dengan bubble sort ide dari https://www.javatpoint.com/bubble-sort-in-java
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

}
