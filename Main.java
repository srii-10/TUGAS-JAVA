import java.text.DecimalFormat;
import java.util.Scanner;

class MenuItem {
    String nama;
    double harga;
    String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public String getKategori() {
        return kategori;
    }
}

public class Main {
    public static final MenuItem[] menuList = {
        // Penambahan item menu Makanan ke MenuItem
        new MenuItem("Nasi Putih", 7000, "Makanan"),
        new MenuItem("Ayam Goreng", 32000, "Makanan"),
        new MenuItem("Seafood", 65000, "Makanan"),
        new MenuItem("Pecel", 30000, "Makanan"),

        // Penambahan item menu Minuman ke MenuItem
        new MenuItem("Air Mineral", 8000, "Minuman"),
        new MenuItem("Jus Buah", 17000, "Minuman"),
        new MenuItem("Es Teh Manis", 9000, "Minuman"),
        new MenuItem("Kopi Latte", 25000, "Minuman"),
        new MenuItem("Lemon Tea", 12000, "Minuman"),
    };

    private static final DecimalFormat format = new DecimalFormat("#,##0.00");

    // Untuk menampilkan daftar menu makanan dan minuman
    public static void tampilkanMenuItem() {
        System.out.println("SELAMAT DATANG DI SAUNG RESTO! SELAMAT MENIKMATI.");
        System.out.println("== Daftar Menu Saung Resto ==");
        System.out.println("Makanan: ");
        for (MenuItem menu : menuList) {
            if (menu.kategori.equals("Makanan")) {
                System.out.println(menu.nama + " - Rp " + format.format(menu.harga));
            }
        }
        System.out.println("\nMinuman: ");
        for (MenuItem menu : menuList) {
            if (menu.kategori.equals("Minuman")) {
                System.out.println(menu.nama + " - Rp "  + format.format(menu.harga));
            }
        }
    }

    // Menghitung total harga pesanan
    public static double calculateTotalCost(String[] orderedItems, int[] quantities) {
        double totalCost = 0;
        for (int i = 0; i < orderedItems.length; i++) {
            if (orderedItems[i] != null) {
                for (MenuItem menu : menuList) {
                    if(menu.nama.equalsIgnoreCase(orderedItems[i])) {
                        totalCost += menu.harga * quantities[i];
                    }
                }
            }
        }
        return totalCost;
    }

    // Menghitung DISKON 10%  jika totalCost > 100.000
    public static double applyDiscount(double totalCost) {
        if(totalCost > 100000) {
            return totalCost * 0.1; // DISKON 10%
        }
        return 0;
    }

    // Menampilkan struk pesanan dan perhitungan keseluruhan harga
    public static void printReceipt(String[] orderedItems, int[] quantities, double totalCost) {
        double serviceFee = 20000;
        
        System.out.println("\n<< Struk Pesanan >>");
        for (int i = 0; i < orderedItems.length; i++) {
            if (orderedItems[i] != null) {
                for (MenuItem menu : menuList) {
                    if(menu.nama.equalsIgnoreCase(orderedItems[i])) {
                        System.out.println(menu.nama + " Rp " + format.format(menu.harga) + " x " + quantities[i] + " = Rp " + format.format(menu.harga * quantities[i]));
                    }
                }
            }
        }

        System.out.println("\nTotal Biaya Pesanan: Rp " + format.format(totalCost));

        // Hitung total biaya untuk Kopi Latte dengan promo
        int kopiLatteCount = 0;
        for (int i = 0; i < orderedItems.length; i++) {
            if (orderedItems[i] != null && orderedItems[i].equalsIgnoreCase("Kopi Latte")) {
                kopiLatteCount += quantities[i];
            }
        }

        // Terapkan promo Kopi Latte jika berlaku
        double kopiLatteDiscount = 0;
        if (kopiLatteCount > 1) {
            double kopiLattePrice = 25000;
            kopiLatteDiscount = kopiLattePrice * (kopiLatteCount / 2);
            System.out.println("Promo: Beli 1 Gratis 1 untuk Kopi Latte!");
            System.out.println("Potongan Promo Kopi Latte: Rp " + format.format(kopiLatteDiscount));
        }

        // Apply Diskon 10%
        double generalDiscount = applyDiscount(totalCost);
        if (generalDiscount > 0) {
            System.out.println("Diskon 10%: Potongan sebesar Rp " + format.format(generalDiscount));
        }

        // Perhitungan subtotal jika dapat Diskon + Promo
        double subtotalAfterDiscounts = totalCost - generalDiscount - kopiLatteDiscount;
        if(totalCost > 100000) {
            System.out.println("Total Setelah Diskon: Rp " + format.format(subtotalAfterDiscounts));
        }

        // Calculate PPN (10%) based on amount after discounts
        double ppn = subtotalAfterDiscounts * 0.1;
        System.out.println("PPN (10%): Rp " + format.format(ppn));

        System.out.println("Biaya Pelayanan: Rp " + format.format(serviceFee));

        // Calculate final total
        double grandTotal = subtotalAfterDiscounts + ppn + serviceFee;
        System.out.println("Total yang harus dibayar: Rp " + format.format(grandTotal));
    
        System.out.println("TERIMA KASIH TELAH BERKUNJUNG!");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            tampilkanMenuItem();

            System.out.println("\nMasukkan pesanan Anda (max. 4 item). Ketik Selesai untuk menyelesaikan pesanan");

            String[] orderedItems = new String[4];
            int[] quantities = new int[4];

            takeOrder(scanner, orderedItems, quantities, 0);
            
            double totalCost = calculateTotalCost(orderedItems, quantities);
            printReceipt(orderedItems, quantities, totalCost);
        }
    }

    private static void takeOrder(Scanner scanner, String[] orderedItems, int[] quantities, int itemCount) {
        if (itemCount >= 4) {
            return;
        }

        System.out.println("Pesanan " + (itemCount + 1) + ": ");
        String itemNama = scanner.nextLine();

        if (itemNama.equalsIgnoreCase("Selesai")) {
            return;
        }

        boolean itemFound = false;
        for (MenuItem menu : menuList) {
            if (menu.nama.equalsIgnoreCase(itemNama)) {
                orderedItems[itemCount] = menu.nama;
                System.out.println("Jumlah: ");
                quantities[itemCount] = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                itemCount++;
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
        }

        takeOrder(scanner, orderedItems, quantities, itemCount);
    }
};