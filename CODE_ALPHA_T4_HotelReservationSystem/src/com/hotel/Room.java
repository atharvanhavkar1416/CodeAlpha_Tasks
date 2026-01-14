package com.hotel;

public class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - $" + price;
    }
}