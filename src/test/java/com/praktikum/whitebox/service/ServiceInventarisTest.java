package com.praktikum.whitebox.service;

import com.praktikum.whitebox.model.Produk;
import com.praktikum.whitebox.repository.RepositoryProduk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Service Inventaris dengan Mocking")
public class ServiceInventarisTest {

    @Mock
    private RepositoryProduk mockRepositoryProduk;

    private ServiceInventaris serviceInventaris;
    private Produk produkTest;

    @BeforeEach
    void setUp() {
        serviceInventaris = new ServiceInventaris(mockRepositoryProduk);
        produkTest = new Produk("PROD001", "Laptop Gaming", "Elektronik",
                15000000, 10, 5);
    }

    // ============================
    // Tambah Produk
    // ============================
    @Test
    @DisplayName("Tambah produk berhasil - semua kondisi valid")
    void testTambahProdukBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        when(mockRepositoryProduk.simpan(produkTest)).thenReturn(true);

        boolean hasil = serviceInventaris.tambahProduk(produkTest);

        assertTrue(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk).simpan(produkTest);
    }

    @Test
    @DisplayName("Tambah produk gagal - produk null")
    void testTambahProdukNull() {
        boolean hasil = serviceInventaris.tambahProduk(null);
        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).simpan(any());
    }

    @Test
    @DisplayName("Tambah produk gagal - sudah ada")
    void testTambahProdukSudahAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

        boolean hasil = serviceInventaris.tambahProduk(produkTest);

        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk, never()).simpan(any());
    }

    // ============================
    // Hapus Produk
    // ============================
    @Test
    @DisplayName("Hapus produk gagal - kode invalid")
    void testHapusProdukKodeInvalid() {
        boolean hasil = serviceInventaris.hapusProduk("$$$");
        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).cariByKode(anyString());
    }
    @Test
    @DisplayName("Hapus produk gagal - produk tidak ditemukan")
    void testHapusProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD404")).thenReturn(Optional.empty());
        boolean hasil = serviceInventaris.hapusProduk("PROD404");
        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD404");
        verify(mockRepositoryProduk, never()).hapus(anyString());
    }
    @Test
    @DisplayName("Hapus produk gagal - stok masih ada")
    void testHapusProdukStokMasihAda() {
        Produk p = new Produk("PROD005", "Keyboard", "Elektronik", 250000, 3, 1);
        when(mockRepositoryProduk.cariByKode("PROD005")).thenReturn(Optional.of(p));
        boolean hasil = serviceInventaris.hapusProduk("PROD005");
        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD005");
        verify(mockRepositoryProduk, never()).hapus(anyString());
    }
    @Test
    @DisplayName("Hapus produk berhasil - stok 0")
    void testHapusProdukStokKosong() {
        Produk p = new Produk("PROD008", "Mouse", "Elektronik", 100000, 0, 1);
        when(mockRepositoryProduk.cariByKode("PROD008")).thenReturn(Optional.of(p));
        when(mockRepositoryProduk.hapus("PROD008")).thenReturn(true);
        boolean hasil = serviceInventaris.hapusProduk("PROD008");
        assertTrue(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD008");
        verify(mockRepositoryProduk).hapus("PROD008");
    }

    // ============================
    // Update Stok
    // ============================
    @Test
    @DisplayName("Update stok gagal - kode invalid")
    void testUpdateStokKodeInvalid() {
        boolean hasil = serviceInventaris.updateStok("$$$", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Update stok gagal - stok negatif")
    void testUpdateStokStokNegatif() {
        boolean hasil = serviceInventaris.updateStok("PROD001", -5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Update stok gagal - produk tidak ditemukan")
    void testUpdateStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD404")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.updateStok("PROD404", 5);

        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD404");
        verify(mockRepositoryProduk, never()).updateStok(anyString(), anyInt());
    }

    @Test
    @DisplayName("Update stok berhasil - repository sukses")
    void testUpdateStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 20)).thenReturn(true);

        boolean hasil = serviceInventaris.updateStok("PROD001", 20);

        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 20);
    }

    @Test
    @DisplayName("Update stok gagal - repository return false")
    void testUpdateStokRepositoryGagal() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 30)).thenReturn(false);

        boolean hasil = serviceInventaris.updateStok("PROD001", 30);

        assertFalse(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 30);
    }

    // ============================
    // Keluar Stok
    // ============================
    @Test
    @DisplayName("Keluar stok gagal - kode invalid")
    void testKeluarStokKodeInvalid() {
        boolean hasil = serviceInventaris.keluarStok("$$$", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - jumlah <= 0")
    void testKeluarStokJumlahTidakValid() {
        boolean hasil = serviceInventaris.keluarStok("PROD001", 0);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - produk tidak ditemukan")
    void testKeluarStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD404")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.keluarStok("PROD404", 5);

        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD404");
    }

    @Test
    @DisplayName("Keluar stok gagal - produk tidak aktif")
    void testKeluarStokProdukTidakAktif() {
        produkTest.setAktif(false);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

        boolean hasil = serviceInventaris.keluarStok("PROD001", 2);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - stok tidak mencukupi")
    void testKeluarStokStokTidakCukup() {
        produkTest.setStok(3);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

        boolean hasil = serviceInventaris.keluarStok("PROD001", 10);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok berhasil - stok mencukupi")
    void testKeluarStokBerhasil() {
        produkTest.setStok(10);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 5)).thenReturn(true);

        boolean hasil = serviceInventaris.keluarStok("PROD001", 5);

        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 5);
    }

    // ============================
    // Masuk Stok
    // ============================
    @Test
    @DisplayName("Masuk stok gagal - kode invalid")
    void testMasukStokKodeInvalid() {
        boolean hasil = serviceInventaris.masukStok("$$$", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok gagal - jumlah <= 0")
    void testMasukStokJumlahTidakValid() {
        boolean hasil = serviceInventaris.masukStok("PROD001", 0);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok gagal - produk tidak ditemukan")
    void testMasukStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD404")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.masukStok("PROD404", 5);

        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD404");
    }

    @Test
    @DisplayName("Masuk stok gagal - produk tidak aktif")
    void testMasukStokProdukTidakAktif() {
        produkTest.setAktif(false);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

        boolean hasil = serviceInventaris.masukStok("PROD001", 5);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok berhasil - stok bertambah")
    void testMasukStokBerhasil() {
        produkTest.setStok(10);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 15)).thenReturn(true);

        boolean hasil = serviceInventaris.masukStok("PROD001", 5);

        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 15);
    }

    // ============================
    // Cari Produk
    // ============================
    @Test
    @DisplayName("Cari produk by kode - null")
    void testCariProdukByKodeNull() {
        Optional<Produk> hasil = serviceInventaris.cariProdukByKode(null);
        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Cari produk by kode - kosong")
    void testCariProdukByKodeKosong() {
        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("   ");
        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Cari produk by kode - invalid format")
    void testCariProdukByKodeInvalidFormat() {
        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("$$$");
        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Cari produk by kode - valid ditemukan")
    void testCariProdukByKodeValid() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("PROD001");

        assertTrue(hasil.isPresent());
        verify(mockRepositoryProduk).cariByKode("PROD001");
    }

    @Test
    @DisplayName("Cari produk by nama - hasil ditemukan")
    void testCariProdukByNamaDitemukan() {
        List<Produk> produkList = Arrays.asList(
                new Produk("PROD001", "Laptop Gaming", "Elektronik", 15000000, 10, 5),
                new Produk("PROD002", "Laptop Ultrabook", "Elektronik", 12000000, 8, 3)
        );
        when(mockRepositoryProduk.cariByNama("Laptop")).thenReturn(produkList);

        List<Produk> hasil = serviceInventaris.cariProdukByNama("Laptop");

        assertEquals(2, hasil.size());
        verify(mockRepositoryProduk).cariByNama("Laptop");
    }

    @Test
    @DisplayName("Cari produk by kategori - hasil ditemukan")
    void testCariProdukByKategoriDitemukan() {
        List<Produk> produkList = Arrays.asList(
                new Produk("PROD010", "Meja Belajar", "Furniture", 500000, 5, 2),
                new Produk("PROD011", "Kursi Kantor", "Furniture", 750000, 3, 1)
        );
        when(mockRepositoryProduk.cariByKategori("Furniture")).thenReturn(produkList);

        List<Produk> hasil = serviceInventaris.cariProdukByKategori("Furniture");

        assertEquals(2, hasil.size());
        verify(mockRepositoryProduk).cariByKategori("Furniture");
    }

    // ============================
    // Hitung Total Nilai Inventaris
    // ============================
    @Test
    @DisplayName("Hitung total nilai inventaris")
    void testHitungTotalNilaiInventaris() {
        Produk p1 = new Produk("PROD001", "Laptop", "Elektronik", 10000000, 2, 1);
        Produk p2 = new Produk("PROD002", "Mouse", "Elektronik", 500000, 5, 2);
        Produk nonAktif = new Produk("PROD003", "Keyboard", "Elektronik", 300000, 3, 1);
        nonAktif.setAktif(false);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(p1, p2, nonAktif));

        double hasil = serviceInventaris.hitungTotalNilaiInventaris();

        double expected = (10000000 * 2) + (500000 * 5);
        assertEquals(expected, hasil, 0.001);
    }
    @Test
    @DisplayName("Get produk stok menipis - hasil ditemukan")
    void testGetProdukStokMenipisDitemukan() {
        Produk p1 = new Produk("PROD010", "Flashdisk", "Elektronik", 50000, 2, 5);
        List<Produk> menipis = Collections.singletonList(p1);
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(menipis);
        List<Produk> hasil = serviceInventaris.getProdukStokMenipis();
        assertEquals(1, hasil.size());
        assertEquals("PROD010", hasil.get(0).getKode());
        verify(mockRepositoryProduk).cariProdukStokMenipis();
    }
    @Test
    @DisplayName("Get produk stok menipis - hasil kosong")
    void testGetProdukStokMenipisKosong() {
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(Collections.emptyList());
        List<Produk> hasil = serviceInventaris.getProdukStokMenipis();
        assertTrue(hasil.isEmpty());
        verify(mockRepositoryProduk).cariProdukStokMenipis();
    }
    @Test
    @DisplayName("Get produk stok habis - hasil ditemukan")
    void testGetProdukStokHabisDitemukan() {
        Produk p1 = new Produk("PROD020", "Printer", "Elektronik", 2000000, 0, 2);
        List<Produk> habis = Collections.singletonList(p1);
        when(mockRepositoryProduk.cariProdukStokHabis()).thenReturn(habis);
        List<Produk> hasil = serviceInventaris.getProdukStokHabis();
        assertEquals(1, hasil.size());
        assertEquals("PROD020", hasil.get(0).getKode());
        verify(mockRepositoryProduk).cariProdukStokHabis();
    }
    @Test
    @DisplayName("Get produk stok habis - hasil kosong")
    void testGetProdukStokHabisKosong() {
        when(mockRepositoryProduk.cariProdukStokHabis()).thenReturn(Collections.emptyList());
        List<Produk> hasil = serviceInventaris.getProdukStokHabis();
        assertTrue(hasil.isEmpty());
        verify(mockRepositoryProduk).cariProdukStokHabis();
    }
    @Test
    @DisplayName("Hitung total stok - hanya produk aktif dihitung")
    void testHitungTotalStokProdukAktif() {
        Produk produkAktif1 = new Produk("PROD001", "Laptop", "Elektronik", 10000000, 5, 1);
        Produk produkAktif2 = new Produk("PROD002", "Mouse", "Elektronik", 500000, 3, 1);
        Produk produkNonAktif = new Produk("PROD003", "Keyboard", "Elektronik", 300000, 10, 1);
        produkNonAktif.setAktif(false);
        List<Produk> semuaProduk = Arrays.asList(produkAktif1, produkAktif2, produkNonAktif);
        when(mockRepositoryProduk.cariSemua()).thenReturn(semuaProduk);
        int totalStok = serviceInventaris.hitungTotalStok();
        assertEquals(8, totalStok); // 5 + 3 = 8 (produk nonaktif di-skip)
        verify(mockRepositoryProduk).cariSemua();
    }
    @Test
    @DisplayName("Hitung total stok - semua produk nonaktif")
    void testHitungTotalStokSemuaNonAktif() {
        Produk p1 = new Produk("PROD010", "Flashdisk", "Elektronik", 50000, 5, 2);
        p1.setAktif(false);
        Produk p2 = new Produk("PROD011", "Harddisk", "Elektronik", 750000, 7, 2);
        p2.setAktif(false);
        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(p1, p2));
        int totalStok = serviceInventaris.hitungTotalStok();
        assertEquals(0, totalStok); // semua nonaktif tidak dihitung
        verify(mockRepositoryProduk).cariSemua();
    }
    @Test
    @DisplayName("Hitung total stok - list produk kosong")
    void testHitungTotalStokKosong() {
        when(mockRepositoryProduk.cariSemua()).thenReturn(Collections.emptyList());
        int totalStok = serviceInventaris.hitungTotalStok();
        assertEquals(0, totalStok); // tidak ada produk
        verify(mockRepositoryProduk).cariSemua();
    }

}
