package com.praktikum.whitebox.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Class Produk - White Box Testing")
public class ProdukTest {
    private Produk produk;
    @BeforeEach
    void setUp() {
        produk = new Produk("PROD001",
                "Laptop Gaming",
                "Elektronik",
                15000000,
                10,
                5);
    }
    @Test
    @DisplayName("Test status stok - stok aman")
    void testStokAman() {
        produk.setStok(10);
        produk.setStokMinimum(5);
        assertTrue(produk.isStokAman());
        assertFalse(produk.isStokMenipis());
        assertFalse(produk.isStokHabis());
    }
    @Test
    @DisplayName("Test status stok - stok menipis")
    void testStokMenipis() {
        produk.setStok(5);
        produk.setStokMinimum(5);
        assertFalse(produk.isStokAman());
        assertTrue(produk.isStokMenipis());
        assertFalse(produk.isStokHabis());
    }
    @Test
    @DisplayName("Test status stok - stok habis")
    void testStokHabis() {
        produk.setStok(0);
        produk.setStokMinimum(5);
        assertFalse(produk.isStokAman());
        assertFalse(produk.isStokMenipis());
        assertTrue(produk.isStokHabis());
    }
    //Anotasi untuk menjalankan tes dengan berbagai parameter
    @ParameterizedTest
    @DisplayName("Test kurangi stok dengan berbagai nilai")
//Anotasi untuk menyediakan data parameterized test dari sumber CSV
    @CsvSource({
            "5, 5", // kurangi 5 dari 10, sisa 5
            "3, 7", // kurangi 3 dari 10, sisa 7
            "10, 0" // kurangi semua stok
    })
    void testKurangiStokValid(int jumlah, int expectedStok) {
        produk.kurangiStok(jumlah);
        assertEquals(expectedStok, produk.getStok());
    }
    @Test
    @DisplayName("Test kurangi stok - jumlah negatif")
    void testKurangiStokNegatif() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    produk.kurangiStok(-5);
                });
        assertEquals("Jumlah harus positif", exception.getMessage());
    }
    @Test
    @DisplayName("Test kurangi stok - stok tidak mencukupi")
    void testKurangiStokTidakMencukupi() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    produk.kurangiStok(15);
                });
        assertEquals("Stok tidak mencukupi", exception.getMessage());
    }
    @Test
    @DisplayName("Test tambah stok valid")
    void testTambahStokValid() {
        produk.tambahStok(5);
        assertEquals(15, produk.getStok());
    }
    @Test
    @DisplayName("Test tambah stok - jumlah negatif")
    void testTambahStokNegatif() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    produk.tambahStok(-5);
                });
        assertEquals("Jumlah harus positif", exception.getMessage());
    }
    //Anotasi untuk menjalankan tes dengan berbagai parameter
    @ParameterizedTest
    @DisplayName("Test hitung total harga")
//Anotasi untuk menyediakan data parameterized test dari sumber CSV
    @CsvSource({
            "1, 15000000",
            "2, 30000000",
            "5, 75000000"
    })
    void testHitungTotalHarga(int jumlah, double expectedTotal) {
        double total = produk.hitungTotalHarga(jumlah);
        assertEquals(expectedTotal, total, 0.001);
    }
    @Test
    @DisplayName("Test hitung total harga - jumlah negatif")
    void testHitungTotalHargaNegatif() {
        Exception exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    produk.hitungTotalHarga(-1);
                });
        assertEquals("Jumlah harus positif", exception.getMessage());
    }
    @Test
    @DisplayName("Test equals dan hashCode")
    void testEqualsAndHashCode() {
        Produk produk1 = new Produk("PROD001", "Laptop", "Elektronik",
                1000000, 5, 2);
        Produk produk2 = new Produk("PROD001", "Laptop Baru",
                "Elektronik", 1200000, 3, 1);
        Produk produk3 = new Produk("PROD002", "Mouse", "Elektronik",
                50000, 10, 5);
        assertEquals(produk1, produk2); // kode sama
        assertNotEquals(produk1, produk3); // kode berbeda
        assertEquals(produk1.hashCode(), produk2.hashCode());
    }
    @Test
    @DisplayName("Test constructor tanpa argumen menghasilkan nilai default")
    void testConstructorNoArgs() {
        Produk p = new Produk();
        assertNull(p.getKode());
        assertNull(p.getNama());
        assertNull(p.getKategori());
        assertEquals(0.0, p.getHarga());
        assertEquals(0, p.getStok());
        assertEquals(0, p.getStokMinimum());
        assertFalse(p.isAktif());
    }
    @Test
    @DisplayName("Test setter dan getter berfungsi dengan benar")
    void testSetterGetter() {
        Produk p = new Produk();
        p.setKode("X001");
        p.setNama("Keyboard");
        p.setKategori("Elektronik");
        p.setHarga(250000.0);
        p.setStok(20);
        p.setStokMinimum(5);
        p.setAktif(true);
        assertEquals("X001", p.getKode());
        assertEquals("Keyboard", p.getNama());
        assertEquals("Elektronik", p.getKategori());
        assertEquals(250000.0, p.getHarga());
        assertEquals(20, p.getStok());
        assertEquals(5, p.getStokMinimum());
        assertTrue(p.isAktif());
    }

    @Test
    @DisplayName("Test equals - objek sama (referensi sama)")
    void testEqualsSameReference() {
        assertTrue(produk.equals(produk));
    }
    @Test
    @DisplayName("Test equals - dibandingkan dengan null")
    void testEqualsNull() {
        assertFalse(produk.equals(null));
    }
    @Test
    @DisplayName("Test equals - dibandingkan dengan objek kelas lain")
    void testEqualsDifferentClass() {
        assertFalse(produk.equals("string bukan produk"));
    }
    @Test
    @DisplayName("Test toString mengandung semua field utama")
    void testToStringContains() {
        String str = produk.toString();
        assertTrue(str.contains("kode='PROD001'"));
        assertTrue(str.contains("nama='Laptop Gaming'"));
        assertTrue(str.contains("kategori='Elektronik'"));
        assertTrue(str.contains("stok=10"));
        assertTrue(str.contains("stokMinimum=5"));
        assertTrue(str.contains("aktif=true"));
    }
    @Test
    @DisplayName("Test toString format harga fleksibel")
    void testToStringRegex() {
        String str = produk.toString();
        assertTrue(str.matches(
                "Produk\\{kode='PROD001', nama='Laptop Gaming', kategori='Elektronik', harga=.*?, stok=10, stokMinimum=5, aktif=true}"
        ));
    }

}
