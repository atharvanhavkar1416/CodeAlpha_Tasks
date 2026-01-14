package com.hotel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelService service = new HotelService();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Hotel Reservation System (Oracle + File I/O)");

        while (true) {
            System.out.println("\n1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            // Simple validation to prevent crashing if user types text
            if(sc.hasNextInt()) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        service.displayAvailableRooms();
                        break;
                    case 2:
                        System.out.print("Enter Your Name: ");
                        String name = sc.next();
                        System.out.print("Enter Room Number to Book: ");
                        int roomNum = sc.nextInt();
                        service.bookRoom(name, roomNum);
                        break;
                    case 3:
                        System.out.print("Enter Room Number to Cancel: ");
                        int cancelRoom = sc.nextInt();
                        service.cancelBooking(cancelRoom);
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Option");
                }
            } else {
                System.out.println("Please enter a valid number.");
                sc.next(); // Clear invalid input
            }
        }
    }
}