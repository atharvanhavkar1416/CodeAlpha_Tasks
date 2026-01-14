package com.hotel;

import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class HotelService {

    // 1. SEARCH ROOMS
    public void displayAvailableRooms() {
        // Oracle uses 1 for True, 0 for False
        String query = "SELECT * FROM rooms WHERE is_available = 1";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Available Rooms ---");
            while (rs.next()) {
                System.out.println("Room: " + rs.getInt("room_number") +
                        " | Type: " + rs.getString("category") +
                        " | Price: $" + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. BOOK ROOM
    public void bookRoom(String customerName, int roomNumber) {
        String checkQuery = "SELECT is_available, price FROM rooms WHERE room_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            
            checkStmt.setInt(1, roomNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int availableStatus = rs.getInt("is_available");
                double price = rs.getDouble("price");

                if (availableStatus == 1) { // 1 means Available
                    System.out.println("Room Found! Price is $" + price);
                    System.out.print("Confirm Booking? (yes/no): ");
                    Scanner sc = new Scanner(System.in);
                    
                    if(!sc.next().equalsIgnoreCase("yes")) {
                        System.out.println("Booking Cancelled.");
                        return;
                    }

                    // Update Room Status to 0 (Booked)
                    String updateRoom = "UPDATE rooms SET is_available = 0 WHERE room_number = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateRoom);
                    updateStmt.setInt(1, roomNumber);
                    updateStmt.executeUpdate();

                    // Save Booking to Database
                    String insertBooking = "INSERT INTO bookings (customer_name, room_number) VALUES (?, ?)";
                    PreparedStatement bookStmt = conn.prepareStatement(insertBooking);
                    bookStmt.setString(1, customerName);
                    bookStmt.setInt(2, roomNumber);
                    bookStmt.executeUpdate();

                    System.out.println("SUCCESS! Room " + roomNumber + " booked for " + customerName);
                    
                    // Generate Text File Receipt
                    generateReceipt(customerName, roomNumber, price);

                } else {
                    System.out.println("Sorry, Room " + roomNumber + " is already booked.");
                }
            } else {
                System.out.println("Room number does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. CANCEL BOOKING
    public void cancelBooking(int roomNumber) {
        String deleteBooking = "DELETE FROM bookings WHERE room_number = ?";
        String updateRoom = "UPDATE rooms SET is_available = 1 WHERE room_number = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start Transaction

            try (PreparedStatement delStmt = conn.prepareStatement(deleteBooking);
                 PreparedStatement upStmt = conn.prepareStatement(updateRoom)) {

                delStmt.setInt(1, roomNumber);
                int rows = delStmt.executeUpdate();

                if (rows > 0) {
                    upStmt.setInt(1, roomNumber);
                    upStmt.executeUpdate();
                    conn.commit(); // Commit Changes
                    System.out.println("Booking cancelled. Room " + roomNumber + " is now free.");
                } else {
                    System.out.println("No booking found for Room " + roomNumber);
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback if error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. GENERATE FILE RECEIPT
    public void generateReceipt(String customerName, int roomNumber, double price) {
        try {
            FileWriter writer = new FileWriter("Receipt_" + roomNumber + ".txt");
            writer.write("--- HOTEL RESERVATION RECEIPT ---\n");
            writer.write("Customer Name: " + customerName + "\n");
            writer.write("Room Number:   " + roomNumber + "\n");
            writer.write("Amount Paid:   $" + price + "\n");
            writer.write("Status:        CONFIRMED\n");
            writer.write("--------------------------------\n");
            writer.write("Thank you for booking with us!");
            writer.close();
            System.out.println("Receipt saved as 'Receipt_" + roomNumber + ".txt'");
        } catch (IOException e) {
            System.out.println("Error generating receipt.");
            e.printStackTrace();
        }
    }
}