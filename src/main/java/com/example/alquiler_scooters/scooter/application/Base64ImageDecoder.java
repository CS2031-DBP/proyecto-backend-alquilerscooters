package com.example.alquiler_scooters.scooter.application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64ImageDecoder {

    public static void decodeAndSaveImage(String base64Image, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            fos.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String codigoimagen = "iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAACCElEQVR4Xu2aPbLCMAyE9YaCMkfwUTgaHC1HyRFSpmCenrRyQPzMGxrkZreJsT7T7MheB0Q/11WeZ/4R4SzCWYSzCGcRziKcRTirw6uEms38qMhpmVR/rTb3wplwPXzEY27r0WC9WNkHcp4bCoSHwFer30pywhOwmeurCI+De2mWaYsB4fGwbgdMqDEiaDTCw2A8vHT4tVW6iM9guYvwCNj9MrV1P3f2QS8Qrofv2gTdJD2qPYhw1tfhcBB+HYLBFqcXC9JbnyFcDKvfZbwUsdn6S91B9Fff/QhXw/ALDjrslxpENVuu/WwiXA4r0rK4g/YhBhbe4soJKwmXw8gAVmqxyqy8OYiZEOFaGBuaGyf7pQbGufx7nvY6wiVwzHhsjiZaJrzGtBl9SQWEa+CH3hHPz7Hp2Sp4+uAg4Rq4t5WHgfBrwSfsdbGccD3sfkE4gESaxk1zljcOEq6DJYR4EANvq+m9g4QrYHSTxkkkp6j6YHUHCQ+DL9JQsjDQHXTFTwCEy+Gb7n75rzDOrOi4s08RLoXj7aWdO7DSSks3bnZPo+MIV8Nuk2WA1q8wNoi3lygRHgTvfvXSEgktdL/vEB4Eg2mK63/Eg7CS8FDY83P6597rXke4BsbD4Ehop/0A8pMIqwjXwxLq8J6fTX7TfN7rCJfAn4lwFuEswlmEswhnEc4inPVF+A+S3BAjI2I4NwAAAABJRU5ErkJggg==";
        String base64Image = codigoimagen;
        String filePath = "QRCode.png";
        decodeAndSaveImage(base64Image, filePath);
        System.out.println("Image saved successfully.");
    }
}
