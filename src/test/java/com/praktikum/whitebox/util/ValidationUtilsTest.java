package com.praktikum.whitebox.util;

import com.praktikum.whitebox.model.Kategori;
import com.praktikum.whitebox.model.Produk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test ValidationUtils - White Box Testing Lengkap")
class ValidationUtilsTest {

    @Test
    @DisplayName("isValidKodeProduk - valid dan invalid cases")
    void testIsValidKodeProduk() {
        assertTrue(ValidationUtils.isValidKodeProduk("ABC123"));
        assertTrue(ValidationUtils.isValidKodeProduk("X9Z"));
        assertFalse(ValidationUtils.isValidKodeProduk(null));
        assertFalse(ValidationUtils.isValidKodeProduk(" "));
        assertFalse(ValidationUtils.isValidKodeProduk("AB")); // kurang dari 3
        assertFalse(ValidationUtils.isValidKodeProduk("ABCDEFGHIJK")); // lebih dari 10
        assertFalse(ValidationUtils.isValidKodeProduk("AB@#")); // karakter tidak valid
    }

    @Test
    @DisplayName("isValidNama - berbagai kondisi valid dan invalid")
    void testIsValidNama() {
        assertTrue(ValidationUtils.isValidNama("Laptop Gaming"));
        assertTrue(ValidationUtils.isValidNama("AB3"));
        assertFalse(ValidationUtils.isValidNama(null));
        assertFalse(ValidationUtils.isValidNama(" "));
        assertFalse(ValidationUtils.isValidNama("AB")); // terlalu pendek
        assertFalse(ValidationUtils.isValidNama("A".repeat(101))); // terlalu panjang
    }

    @Test
    @DisplayName("isValidHarga - harga positif dan non-positif")
    void testIsValidHarga() {
        assertTrue(ValidationUtils.isValidHarga(1000));
        assertFalse(ValidationUtils.isValidHarga(0));
        assertFalse(ValidationUtils.isValidHarga(-1));
    }
    @Test
    @DisplayName("isValidStok - stok non-negatif dan negatif")
    void testIsValidStok() {
        assertTrue(ValidationUtils.isValidStok(0));
        assertTrue(ValidationUtils.isValidStok(10));
        assertFalse(ValidationUtils.isValidStok(-1));
    }
    @Test
    @DisplayName("isValidStokMinimum - stok minimum valid dan invalid")
    void testIsValidStokMinimum() {
        assertTrue(ValidationUtils.isValidStokMinimum(0));
        assertTrue(ValidationUtils.isValidStokMinimum(5));
        assertFalse(ValidationUtils.isValidStokMinimum(-1));
    }
    @Test
    @DisplayName("isValidProduk - produk valid dan invalid")
    void testIsValidProduk() {
        Produk p = new Produk("P001", "Laptop", "Elektronik", 10000, 5, 1);
        assertTrue(ValidationUtils.isValidProduk(p));
        assertFalse(ValidationUtils.isValidProduk(null)); // produk null
        Produk invalidKode = new Produk("!!", "Laptop", "Elektronik", 10000, 5, 1);
        assertFalse(ValidationUtils.isValidProduk(invalidKode)); // kode invalid
        Produk invalidNama = new Produk("P002", "AB", "Elektronik", 10000, 5, 1);
        assertFalse(ValidationUtils.isValidProduk(invalidNama)); // nama invalid
        Produk invalidHarga = new Produk("P003", "Laptop", "Elektronik", -1, 5, 1);
        assertFalse(ValidationUtils.isValidProduk(invalidHarga)); // harga invalid
    }

    @Test
    @DisplayName("isValidProduk - stok atau stok minimum negatif")
    void testIsValidProdukStokNegatif() {
        Produk stokNegatif = new Produk("P010", "Laptop", "Elektronik", 1000, -1, 2);
        assertFalse(ValidationUtils.isValidProduk(stokNegatif));
        Produk stokMinNegatif = new Produk("P011", "Laptop", "Elektronik", 1000, 5, -3);
        assertFalse(ValidationUtils.isValidProduk(stokMinNegatif));
    }
    @Test
    @DisplayName("isValidKategori - kategori valid dan invalid")
    void testIsValidKategori() {
        Kategori k = new Kategori("K001", "Elektronik", "Deskripsi kategori");
        assertTrue(ValidationUtils.isValidKategori(k));
        assertFalse(ValidationUtils.isValidKategori(null)); // null
        Kategori invalidKode = new Kategori("!!", "Elektronik", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidKode)); // kode invalid
        Kategori invalidNama = new Kategori("K002", "AB", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidNama)); // nama invalid
    }

    @Test
    @DisplayName("isValidKategori - deskripsi terlalu panjang (>500)")
    void testIsValidKategoriDeskripsiTerlaluPanjang() {
        String longDesc = "A".repeat(501);
        Kategori kategori = new Kategori("K999", "Elektronik", longDesc);
        assertFalse(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("isValidPersentase - berbagai nilai")
    void testIsValidPersentase() {
        assertTrue(ValidationUtils.isValidPersentase(0));
        assertTrue(ValidationUtils.isValidPersentase(100));
        assertTrue(ValidationUtils.isValidPersentase(50.5));
        assertFalse(ValidationUtils.isValidPersentase(-1));
        assertFalse(ValidationUtils.isValidPersentase(101));
    }

    @Test
    @DisplayName("isValidKuantitas - valid dan invalid")
    void testIsValidKuantitas() {
        assertTrue(ValidationUtils.isValidKuantitas(1));
        assertTrue(ValidationUtils.isValidKuantitas(10));
        assertFalse(ValidationUtils.isValidKuantitas(0));
        assertFalse(ValidationUtils.isValidKuantitas(-5));
    }

    @Test
    @DisplayName("Test constructor private ValidationUtils untuk coverage")
    void testPrivateConstructor() throws Exception {
        var constructor = ValidationUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception ex = assertThrows(Exception.class, constructor::newInstance);
        assertTrue(ex.getCause() instanceof UnsupportedOperationException);
        assertEquals("Utility class - tidak boleh di-instantiate", ex.getCause().getMessage());
    }
    @Test
    @DisplayName("isValidProduk - kategori invalid (nama kategori pendek)")
    void testIsValidProdukKategoriInvalid() {
        Produk p = new Produk("P100", "Laptop", "AB", 10000, 5, 1);
        // kategori "AB" tidak valid karena < 3 karakter
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("isValidProduk - stok atau stok minimum valid (>=0) untuk branch true")
    void testIsValidProdukStokDanStokMinimumValid() {
        Produk p = new Produk("P101", "Laptop", "Elektronik", 10000, 0, 0);
        // mengecek branch true untuk stok >= 0 dan stokMinimum >= 0
        assertTrue(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("isValidKategori - deskripsi null untuk branch kiri (== null)")
    void testIsValidKategoriDeskripsiNull() {
        Kategori k = new Kategori("K100", "Elektronik", null);
        // deskripsi null memicu cabang kiri dari kondisi OR
        assertTrue(ValidationUtils.isValidKategori(k));
    }

    @Test
    @DisplayName("isValidKategori - deskripsi tepat 500 karakter (branch kanan true)")
    void testIsValidKategoriDeskripsiBatasMaks() {
        String desc500 = "A".repeat(500);
        Kategori k = new Kategori("K101", "Elektronik", desc500);
        assertTrue(ValidationUtils.isValidKategori(k));
    }

}
