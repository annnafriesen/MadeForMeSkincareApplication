package ui;

import model.TheOrdinaryProducts;

import java.io.FileNotFoundException;

//Runs MadeForMe SkinCare application.
public class Main {
    public static void main(String[] args) {
        try {
            new MadeForMeSkinCareApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
