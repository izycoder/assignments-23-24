package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan
        String restaurantId = namaRestoran.substring(0, 4).toUpperCase();
        tanggalOrder = tanggalOrder.replace("/", "");
        int jumlah = 0;
        for (int i = 0; i < noTelepon.length(); i++) {
            int angka = Character.getNumericValue(noTelepon.charAt(i));
            jumlah += angka;
        }
        jumlah = jumlah % 100;
        String kode = Integer.toString(jumlah);
        if (jumlah < 10){
            kode = "0" + kode;
        }
        String strChecksum = restaurantId + tanggalOrder + kode;
        String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int checksum1 = 0;
        int checksum2 = 0;
        for (int i = 0; i < strChecksum.length(); i ++){
            char cek = strChecksum.charAt(i);
            int index = characterSet.indexOf(cek);
            if (i % 2 == 0){
                checksum1 += index;
            }
            else{
                checksum2 += index;
            }
        }
        checksum1 = checksum1 % 36;
        checksum2 = checksum2 % 36;
        char cs1 = characterSet.charAt(checksum1);
        char cs2 = characterSet.charAt(checksum2);
    
        String orderID = strChecksum + cs1 + cs2;
        return orderID;
    }

    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        String tanggal = OrderID.substring(4,12);
        String Output = "";
        String biaya = "";
        switch (lokasi.toUpperCase()){
            case "P":
            biaya += "Rp 10.000";
            break;
            case "U":
            biaya += "Rp 20.000";
            break;
            case "T":
            biaya += "Rp 35.000";
            break;
            case "S":
            biaya += "40.000";
            break;
            case "B":
            biaya += "Rp 60.000";
            break;
        }
        Output += "Bill:\n" +
                        "Order ID: " + OrderID + "\n" +
                        "Tanggal Pemesanan: " + tanggal.substring(0,2) + "/"+ tanggal.substring(2, 4)+ "/" + tanggal.substring(4, 8)+ "\n" +
                        "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n" + 
                        "Biaya Ongkos Kirim: Rp " + biaya + "\n";
        return Output;

    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        boolean keluar = false;
        boolean selesai;
        showMenu();
        while (!keluar){
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
        int pilihan = input.nextInt();
        input.nextLine();
        switch (pilihan) {
            case 1:
            selesai = false;
            while (!selesai) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            String cekResto = namaRestoran.replaceAll(" ", "");
            if (cekResto.length() >= 4) {
                System.out.print("Tanggal Pemesanan: ");
                String tanggal = input.nextLine();
                String cekTanggal = tanggal.replaceAll("/", ""); 
                if (cekTanggal.length() == 8) {
                    System.out.print("No. Telpon: ");
                    String noTelp = input.nextLine();
                    if (!noTelp.matches("[0-9]+")) { // https://stackoverflow.com/questions/23905985/validation-input-to-be-string-only-and-numbers-only-java
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif. \n");
                    }
                    else {
                        String generateOrder = generateOrderID(cekResto, tanggal, noTelp);
                        System.out.printf("Order ID %s diterima!\n", generateOrder);
                        System.out.println("--------------------------------------------");
                        selesai = true; 
                    }
                } else {
                    System.out.println("Pemesanan dalam format DD/MM/YYYY! \n");
                }
            } else {
                System.out.println("Nama Restoran tidak valid! \n");
            }
            }
            break;
            case 2:
            int checksum1 = 0;
            int checksum2 = 0;
            String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            selesai = false;
            String biaya = "";
            while (!selesai){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();
            if (orderID.length() == 16){
                String cekResto = orderID.substring(0, 14);
                for (int i = 0; i < cekResto.length(); i++){
                    char cek = cekResto.charAt(i);
                    int index = characterSet.indexOf(cek);
                    if (i % 2 == 0){
                        checksum1 += index;
                    }
                    else{
                        checksum2 += index;
                    }
                }
                checksum1 = checksum1 % 36;
                checksum2 = checksum2 % 36;
                char cs1 = characterSet.charAt(checksum1);
                char cs2 = characterSet.charAt(checksum2);
                if (orderID.charAt(14) == cs1 && orderID.charAt(15) == cs2) {
                    System.out.print("Lokasi Pengiriman: ");
                    String lokasi = input.nextLine();
                    lokasi = lokasi.toUpperCase();
                    switch (lokasi){
                        case "P":
                        break;
                        case "U":
                        break;
                        case "T":
                        break;
                        case "S":
                        break;
                        case "B":
                        break;
                        default:
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                    }
                    System.out.println("\n" + generateBill(orderID, lokasi) + "--------------------------------------------" );
                    // System.out.print("--------------------------------------------\n");
                    break;
                }
                else{
                    System.out.println("Silahkan masukkan Order ID yang valid! \n");
                }
            }
            else{
                System.out.println("Order ID minimal 16 karakter");
            }
        }
            // generateBill(orderID, lokasi);2
            break;
            case 3:
            System.out.println("Terima kasih telah menggunakan DepeFood!");
            keluar = true;
            break;
            default:
            System.out.println("Pilih menu yang benar");
        }  
        }
    }
}
