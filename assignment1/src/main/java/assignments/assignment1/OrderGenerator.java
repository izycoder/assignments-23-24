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
        String restaurantId = namaRestoran.substring(0, 4).toUpperCase(); // substring untuk id restoran
        tanggalOrder = tanggalOrder.replace("/", ""); // membuang garis miring dari tanggal order
        int jumlah = 0; // variabel jumlah bertipe int
        for (int i = 0; i < noTelepon.length(); i++) { // for loop sepanjang notelp
            int angka = Character.getNumericValue(noTelepon.charAt(i)); // mengambil char angka tiap notelp
            jumlah += angka; // menjumlahkannya ke dalam jumlah
        }
        jumlah = jumlah % 100; // jumlah dimodulo 100
        String kode = Integer.toString(jumlah); // kode adalah jumlah bertipe string
        if (jumlah < 10){ // jika hasil modulo hanya 1 digit / kurang dari 10
            kode = "0" + kode; // maka ditambahkan 0 di depan kode
        }
        String strChecksum = restaurantId + tanggalOrder + kode; // 14 kode pertama
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
    
        String orderId = strChecksum + cs1 + cs2; // return hasil order ID
        return orderId; // return order ID
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
        String tanggal = OrderID.substring(4,12); // tanggal adalah substring dari rder id index ke 4-12
        String output = ""; // string kosong output
        String biaya = ""; // string kosong biaya
        switch (lokasi.toUpperCase()){ // switch case untuk lokasi
            case "P": // ketika P 
            biaya += "Rp 10.000"; // biaya 10000
            break;
            case "U": // ketika U
            biaya += "Rp 20.000"; // biaya 20000
            break;
            case "T": // ketika T
            biaya += "Rp 35.000"; // biaya 35000
            break;
            case "S": // ketika S
            biaya += "Rp 40.000"; // biaya 40000
            break;
            case "B": // ketika B
            biaya += "Rp 60.000"; // biaya 60000
            break;
        }
        output += "Bill:\n" + // output bill
                        "Order ID: " + OrderID + "\n" +
                        "Tanggal Pemesanan: " + tanggal.substring(0,2) + "/"+ tanggal.substring(2, 4)+ "/" + tanggal.substring(4, 8)+ "\n" +
                        "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n" + 
                        "Biaya Ongkos Kirim: " + biaya + "\n";
        return output; // return output

    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        boolean keluar = false; // boolean variabel
        boolean selesai; // boolean selesai
        showMenu(); // fungsi showmenu
        while (!keluar){ // while loop
        System.out.println("Pilih menu:"); // pilih menu
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
        int pilihan = input.nextInt(); // input pilihan menu
        input.nextLine(); // space eater
        switch (pilihan) { // switch casse pilihan
            case 1: // jika pilihannya 1
            selesai = false; // boolean selesai
            while (!selesai) { // while loop
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine(); // input nama restoran
            String cekResto = namaRestoran.replaceAll(" ", ""); // menghapus spasi dalam nama restoran
            if (cekResto.length() >= 4) { // cek panjang string restoran
                System.out.print("Tanggal Pemesanan: ");
                String tanggal = input.nextLine(); // input tanggal pemesanan
                String cekTanggal = tanggal.replaceAll("/", "");  // remove / dalam tanggal
                if (cekTanggal.length() == 8) { // cek panjang string tanggal
                    System.out.print("No. Telpon: ");
                    String noTelp = input.nextLine(); // input noTelp
                    if (!noTelp.matches("[0-9]+")) { // https://stackoverflow.com/questions/23905985/validation-input-to-be-string-only-and-numbers-only-java
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif. \n"); // jika input bukan angka 0-9
                    }
                    else { // jika input benar
                        String generateOrder = generateOrderID(cekResto, tanggal, noTelp); // masuk ke fungsi generator id
                        System.out.printf("Order ID %s diterima!\n", generateOrder); // print order diterima
                        System.out.println("--------------------------------------------");
                        selesai = true; // keluar while loop
                        break;
                    }
                } else {
                    System.out.println("Pemesanan dalam format DD/MM/YYYY! \n"); // pesan error
                }
            } else {
                System.out.println("Nama Restoran tidak valid! \n"); // pesan error
            }
            }
            break;
            case 2: // jika pilihan menu 2
            String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // string characterset
            selesai = false; // boolean variabel
            while (!selesai){ // while loop
            int checkSum1 = 0; // variabel checksum1
            int checkSum2 = 0; // variabel checksum2
            System.out.print("Order ID: ");
            String orderID = input.nextLine(); // input order id
            if (orderID.length() == 16){ // cek panjang order id
                String cekResto = orderID.substring(0, 14); // ambil 14 karakter pertama order id
                for (int i = 0; i < cekResto.length(); i++){ // for loop sepanjang cek resto
                    char cek = cekResto.charAt(i); // variabel cek adalah char ke i 
                    int index = characterSet.indexOf(cek); // index adalah index cek di character set
                    if (i % 2 == 0){ // ketika kelipatan genap
                        checkSum1 += index; // checksum 1 ditambahkan index
                    }
                    else{
                        checkSum2 += index; // checksum 2 ditambahkan index
                    }
                }
                checkSum1 = checkSum1 % 36; // checksum1 dimodulo 36
                checkSum2 = checkSum2 % 36; // checksum2 dimodulo 36
                char cs1 = characterSet.charAt(checkSum1); // char cs1 adalah karakter ke checksum1 di characterset
                char cs2 = characterSet.charAt(checkSum2); // char cs2 adalah karakter ke checksum2 di characterset
                if (orderID.charAt(14) == cs1 && orderID.charAt(15) == cs2) { // jika char ke 14 sesuai dengan cs1 dan char ke 15 sesuai dengan cs2
                    System.out.print("Lokasi Pengiriman: ");
                    String lokasi = input.nextLine(); // input lokasi
                    lokasi = lokasi.toUpperCase(); // uppercase lokasi
                    if (lokasi.equals("P") || lokasi == ("U") || lokasi == ("T") || lokasi == ("S") || lokasi == ("B")){
                        System.out.println("\n" + generateBill(orderID, lokasi) + "--------------------------------------------" ); // output 
                        selesai = true; // keluar
                    }
                    else{
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan! \n"); // pesan error
                    }
                }
                else{
                    System.out.println("Silahkan masukkan Order ID yang valid! \n"); // pesan error
                }
            }
            else{
                System.out.println("Order ID minimal 16 karakter \n"); // pesan error
            }
        }
            break;
            case 3:
            System.out.println("Terima kasih telah menggunakan DepeFood!"); // pesan terima kasih
            keluar = true; // keluar
            break;
            default:
            System.out.println("Pilih menu yang benar"); // pesan error
        }  
        }
    }
}
