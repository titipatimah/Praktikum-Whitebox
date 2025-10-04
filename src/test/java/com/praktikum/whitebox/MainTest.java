package com.praktikum.whitebox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {

    @Test
    void testMainOutput() {
        // Tangkap output dari console
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            // Jalankan method main()
            Main.main(new String[]{});

            // Ambil hasil output
            String output = outputStream.toString();

            // Verifikasi isi output
            assertTrue(output.contains("Hello and welcome!"), "Output tidak berisi teks pembuka.");
            assertTrue(output.contains("i = 1"), "Output tidak berisi i = 1.");
            assertTrue(output.contains("i = 5"), "Output tidak berisi i = 5.");
        } finally {
            // Kembalikan System.out
            System.setOut(originalOut);
        }
    }
}
