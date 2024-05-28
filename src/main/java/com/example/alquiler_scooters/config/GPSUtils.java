package com.example.alquiler_scooters.config;

import java.util.Random;

public class GPSUtils {

    private static final double LATITUDE_RANGE = 0.001;  // Aproximadamente 100 metros
    private static final double LONGITUDE_RANGE = 0.001;  // Aproximadamente 100 metros
    private static final Random RANDOM = new Random();

    public static String updateLocation(String currentLocation) {
        String[] parts = currentLocation.split(", ");
        double currentLatitude = Double.parseDouble(parts[0]);
        double currentLongitude = Double.parseDouble(parts[1]);

        double newLatitude = currentLatitude + (RANDOM.nextDouble() - 0.5) * LATITUDE_RANGE;
        double newLongitude = currentLongitude + (RANDOM.nextDouble() - 0.5) * LONGITUDE_RANGE;

        return newLatitude + ", " + newLongitude;
    }
}