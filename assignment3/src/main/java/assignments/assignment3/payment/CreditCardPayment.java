package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    public double TRANSACTION_FEE_PERCENTAGE = 0.02;

    // Implementasi method dari interface DepeFoodPaymentSystem
    @Override
    public long processPayment(long saldo, double bill) {
        // Logika untuk memproses pembayaran dengan kartu kredit
        if (bill > saldo) {
            // Jika total harga pesanan kurang dari Rp50000
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return saldo; // Saldo tidak berubah
        }
        else{
        double transactionFee = countTransactionFee(bill);
        double totalAmount = bill + transactionFee;
        String formattedBill = String.format("%.0f", bill);
        String formattedFee = String.format("%.0f", transactionFee);
        System.out.println("Berhasil Membayar Bill sebesar Rp "+ formattedBill + " dengan biaya transaksi sebesar Rp " + formattedFee);
        return saldo - (long)totalAmount;
    }
    }
    
    // Implementasi method untuk menghitung biaya transaksi
    private double countTransactionFee(double amount) {
        // Hitung biaya transaksi (2% dari jumlah pembayaran)
        return TRANSACTION_FEE_PERCENTAGE * amount;
    }
}
