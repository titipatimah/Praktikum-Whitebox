package com.praktikum.whitebox.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class KategoriTest {

    @Test
    @DisplayName("Constructor tanpa argumen menghasilkan objek default")
    void testConstructorNoArgs() {
        Kategori kategori = new Kategori();
        assertNull(kategori.getKode());
        assertNull(kategori.getNama());
        assertNull(kategori.getDeskripsi());
        assertFalse(kategori.isAktif());
    }
    @Test
    @DisplayName("Constructor dengan argumen mengisi field dengan benar")
    void testConstructorWithArgs() {
        Kategori kategori = new Kategori("KAT001", "Elektronik", "Kategori barang elektronik");
        assertEquals("KAT001", kategori.getKode());
        assertEquals("Elektronik", kategori.getNama());
        assertEquals("Kategori barang elektronik", kategori.getDeskripsi());
        assertTrue(kategori.isAktif());
    }
    @Test
    @DisplayName("Setter dan Getter berfungsi dengan benar")
    void testSetterGetter() {
        Kategori kategori = new Kategori();
        kategori.setKode("KAT002");
        kategori.setNama("Furniture");
        kategori.setDeskripsi("Kategori perabot rumah");
        kategori.setAktif(true);

        assertEquals("KAT002", kategori.getKode());
        assertEquals("Furniture", kategori.getNama());
        assertEquals("Kategori perabot rumah", kategori.getDeskripsi());
        assertTrue(kategori.isAktif());
    }
    @Test
    @DisplayName("Equals - objek sama dianggap equal")
    void testEqualsSameValues() {
        Kategori k1 = new Kategori("KAT003", "Pakaian", "Kategori baju");
        Kategori k2 = new Kategori("KAT003", "Pakaian", "Kategori baju");
        assertEquals(k1, k2);
        assertEquals(k1.hashCode(), k2.hashCode());
    }
    @Test
    @DisplayName("Equals - objek berbeda dianggap tidak equal")
    void testEqualsDifferentValues() {
        Kategori k1 = new Kategori("KAT004", "Elektronik", "Barang elektronik");
        Kategori k2 = new Kategori("KAT005", "Furniture", "Barang rumah");
        assertNotEquals(k1, k2);
        assertNotEquals(k1.hashCode(), k2.hashCode());
    }
    @Test
    @DisplayName("Equals - same reference returns true")
    void testEqualsSameReference() {
        Kategori k = new Kategori("KAT006", "Mainan", "Kategori mainan");
        assertTrue(k.equals(k)); // memicu if (this == o)
    }
    @Test
    @DisplayName("Equals - null returns false")
    void testEqualsNull() {
        Kategori k = new Kategori("KAT007", "Buku", "Kategori buku");
        assertFalse(k.equals(null)); // memicu if (o == null)
    }

    @Test
    @DisplayName("Equals - different class returns false")
    void testEqualsDifferentClass() {
        Kategori k = new Kategori("KAT008", "Olahraga", "Kategori olahraga");
        Object other = new Object();
        assertFalse(k.equals(other)); // memicu getClass() != o.getClass()
    }

    @Test
    @DisplayName("toString menampilkan semua field")
    void testToString() {
        Kategori kategori = new Kategori("KAT009", "Aksesoris", "Kategori aksesoris");
        String str = kategori.toString();

        assertTrue(str.contains("KAT009"));
        assertTrue(str.contains("Aksesoris"));
        assertTrue(str.contains("Kategori aksesoris"));
        assertTrue(str.contains("true"));
    }
}
