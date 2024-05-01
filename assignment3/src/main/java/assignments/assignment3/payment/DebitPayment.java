package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    public double MINIMUM_TOTAL_PRICE = 50000.0;
    // Implementasi method dari interface DepeFoodPaymentSystem
    @Override
    public long processPayment(long saldo, double bill) {
        if (bill < MINIMUM_TOTAL_PRICE) {
            // Jika total harga pesanan kurang dari Rp50000
            System.out.println("Jumlah pesanan kurang dari Rp50000. Mohon menggunakan metode pembayaran yang lain.");
            return saldo; // Saldo tidak berubah
        } else if (saldo < bill) {
            // Jika saldo tidak mencukupi
            System.out.println("Saldo tidak mencukupi. Mohon menggunakan metode pembayaran yang lain.");
            return saldo; // Saldo tidak berubah
        } else {
            // Jika saldo mencukupi
            long newSaldo = saldo - (long) bill;
            System.out.println("Berhasil membayar bill sebesar Rp " + bill);
            System.out.println("Sisa saldo sebesar Rp " + newSaldo);
            return newSaldo; // Mengembalikan saldo baru
        }
    }
}
