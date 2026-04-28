package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    static String fileName = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        loadTransactions();
        homeScreen();
    }
    // load transtions from csv
    public static void loadTransactions() {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split("\\|");

                String date = tokens[0];
                String time = tokens[1];
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                Transaction t = new Transaction(date, time, description, vendor, amount);
                transactions.add(t);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("couldnt find the file: " + fileName);
        } catch (IOException e) {
            System.err.println("error reading file.");
        }
    }
    // save transtions to csv
    public static void saveTransaction(Transaction t) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(t.toCsvLine() + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving transaction");
        }
    }
    //home screen
    public static void homeScreen() {
         boolean running = true;
         while (running) {
             System.out.println("\n===== ACCOUNTING LEDGER =====");
             System.out.println("D) Add Deposit");
             System.out.println("P) Make Payment (Debit)");
             System.out.println("L) Ledger");
             System.out.println("x) Exist");
             System.out.println("Coose an option: ");

             String choice = scanner.nextLine().toUpperCase();

             if (choice.equals("D")) {
                 addDeposit();
             } else if (choice.equals("P")) {
                 makePayment();
             } else if (choice.equals("L")) {
                 ledgerScreen();
             } else if (choice.equals("x")) {
                 running = false;
                 System.out.println("Goodbye!");
             } else {
                 System.out.println("Invalid option. Pleae try again. ");
             }

         }

    }
    // add deposit //
    public static void addDeposit(){
        System.out.println("\n==== ADD DEPOSIT====");
        System.out.print("Description");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = LocalDate.now().format(dateFmt);
        String time = LocalTime.now().format(timeFmt);

        Transaction t = new Transaction(date, time, description, vendor, amount);
        transactions.add(t);
        saveTransaction(t);
        System.out.print("deposit added!");
    }
    // make payment
    public static void makePayment() {
        System.out.println("\n==== MAKE PAYMENT ====");

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Amount:  ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (amount > 0) {
            amount = amount * -1;
        }
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = LocalDate.now().format(dateFmt);
        String time = LocalTime.now().format(timeFmt);

        Transaction t = new Transaction(date, time, description, vendor, amount);
        transactions.add(t);
        saveTransaction(t);
        System.out.println("Payment recorded! ");
    }




}




