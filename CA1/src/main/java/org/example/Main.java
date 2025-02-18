package org.example;

public class Main {
    public static void main(String[] args) {

        Hotel hotel = new Hotel();
        hotel.initFromJson("CA1/data.json");
        hotel.logState("CA1/state.json");
    }
}