package com.praktikum.whitebox.util;

import com.praktikum.whitebox.model.Kategori;
import com.praktikum.whitebox.model.Produk;

public class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class - tidak boleh di-instantiate");
    }

    public static boolean isValidKodeProduk(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            return false;
        }
        String kodeBersih = kode.trim();
        return kodeBersih.matches("^[A-Za-z0-9]{3,10}$");
    }

    public static boolean isValidNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            return false;
        }
        String namaBersih = nama.trim();
        return namaBersih.length() >= 3 && namaBersih.length() <= 100;
    }

    public static boolean isValidHarga(double harga) {
        return harga > 0;
    }

    public static boolean isValidStok(int stok) {
        return stok >= 0;
    }

    public static boolean isValidStokMinimum(int stokMinimum) {
        return stokMinimum >= 0;
    }

    public static boolean isValidProduk(Produk produk) {
        if (produk == null) {
            return false;
        }
        return isValidKodeProduk(produk.getKode()) &&
                isValidNama(produk.getNama()) &&
                isValidNama(produk.getKategori()) &&
                isValidHarga(produk.getHarga()) &&
                isValidStok(produk.getStok()) &&
                isValidStokMinimum(produk.getStokMinimum()) &&
                produk.getStok() >= 0 &&
                produk.getStokMinimum() >= 0;
    }

    public static boolean isValidKategori(Kategori kategori) {
        if (kategori == null) {
            return false;
        }
        return isValidKodeProduk(kategori.getKode()) &&
                isValidNama(kategori.getNama()) &&
                (kategori.getDeskripsi() == null ||
                        kategori.getDeskripsi().length() <= 500);
    }

    public static boolean isValidPersentase(double persentase) {
        return persentase >= 0 && persentase <= 100;
    }

    public static boolean isValidKuantitas(int kuantitas) {
        return kuantitas > 0;
    }
}
