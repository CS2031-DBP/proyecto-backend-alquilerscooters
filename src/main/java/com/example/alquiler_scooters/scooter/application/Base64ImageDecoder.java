package com.example.alquiler_scooters.scooter.application;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Base64;

public class Base64ImageDecoder {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/alquilerscooters";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false); // Desactivar el auto-commit

            Statement stmt = conn.createStatement();
            String sql = "SELECT id, qr_code_image FROM scooters";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id");
                int oid = rs.getInt("qr_code_image");
                byte[] qrCodeImage = readImageFromOID(conn, oid);
                if (qrCodeImage != null) {
                    String base64Image = Base64.getEncoder().encodeToString(qrCodeImage);
                    String filePath = "target/qr_codes/QRCode_" + id + ".png";
                    decodeAndSaveImage(base64Image, filePath);
                }
            }

            rs.close();
            stmt.close();
            conn.commit(); // Hacer commit manualmente
            conn.setAutoCommit(true); // Volver a activar el auto-commit
            conn.close();

            System.out.println("All QR codes have been saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readImageFromOID(Connection conn, int oid) {
        byte[] imageBytes = null;
        try {
            LargeObjectManager lobj = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            LargeObject obj = lobj.open(oid, LargeObjectManager.READ);
            imageBytes = obj.read((int) obj.size());
            obj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    public static void decodeAndSaveImage(String base64Image, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            fos.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
