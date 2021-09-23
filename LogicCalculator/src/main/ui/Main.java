package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // Exception handling modelled code off sample application JsonSerializationDemo
        try {
            new LogicCalculator();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
