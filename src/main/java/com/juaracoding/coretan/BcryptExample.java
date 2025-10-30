package com.juaracoding.coretan;


import org.mindrot.jbcrypt.BCrypt;

public class BcryptExample {
    public static void main(String[] args) {
        String originalPassword = "MySecurePassword@2025";

        // --- Proses Hashing (Saat Pengguna Mendaftar) ---
        // Parameter kedua adalah "work factor" atau "log rounds".
        // Nilai antara 10-12 adalah standar yang baik.
        int workFactor = 12;
        String hashedPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(workFactor));

        System.out.println("Original Password : " + originalPassword);
        System.out.print("Generated Salt and Hashed Password :");
        System.out.println(hashedPassword);

        // Struktur hash Bcrypt: $2a$[cost]$[22-char salt][31-char hash]
        // Contoh: $2a$12$E4/g..s29.gFB.e5tBDM..9wG.Y.U4.jP/F3dCRiy3g2k2.DbVz/i

        System.out.println("\n--- Proses Verifikasi (Saat Pengguna Login) ---");

        // Pengguna memasukkan password saat login
        String candidatePassword1 = "MySecurePassword@2025";
        String candidatePassword2 = "WrongPassword";

        // BCrypt.checkpw akan mengambil salt dari `hashedPassword` secara otomatis,
        // lalu menghash `candidatePassword` dengan salt tersebut dan membandingkannya.
        boolean isMatch1 = BCrypt.checkpw(candidatePassword1, hashedPassword);
        boolean isMatch2 = BCrypt.checkpw(candidatePassword2, hashedPassword);

        System.out.println("Verification for '" + candidatePassword1 + "': " + isMatch1); // true
        System.out.println("Verification for '" + candidatePassword2 + "': " + isMatch2); // false

        //$2a$12$wIA5b9bCAjyaVtkYKwpwJeIO0Oavy/iGra0LBxu9O8BO2SjDIimLq
        //$2a$12$5Hw9fcFsb8B.YJdX4ahJMO91eXi/J8AD6zIDfhajDByVg7K3z68wO
    }

}
