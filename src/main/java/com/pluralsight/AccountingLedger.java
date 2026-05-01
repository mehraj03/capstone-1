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
        System.out.println(" _____            _   _       _          ____              _    ");
        System.out.println("|  __ \\          | | | |     | |        |  _ \\            | |   ");
        System.out.println("| |  | | __ _  __| | | | ___ | | _____  | |_) | __ _ _ __ | | __");
        System.out.println("| |  | |/ _` |/ _` | | |/ _ \\| |/ / _ \\ |  _ < / _` | '_ \\| |/ /");
        System.out.println("| |__| | (_| | (_| | | | (_) |   <  __/ | |_) | (_| | | | |   < ");
        System.out.println("|_____/ \\__,_|\\__,_| |_|\\___/|_|\\_\\___| |____/ \\__,_|_| |_|_|\\_\\");
        System.out.println();
        System.out.println("    \"Where every transaction comes with a smile (and a groan)\"");
        System.out.println();
        loadTransactions();
        homeScreen();
    }

    // load transactions from csv
    public static void loadTransactions() {
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            bufferedReader.readLine();

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
            System.err.println("couldn't find the file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file.");
        }
    }

    // save transactions to csv
    public static void saveTransaction(Transaction t) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("\n" + t.displayTransaction());
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving transaction");
        }
    }

    //home screen
    public static void homeScreen() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println(" _    _  ____  __  __ ______ ");
            System.out.println("| |  | |/ __ \\|  \\/  |  ____|");
            System.out.println("| |__| | |  | | \\  / | |__   ");
            System.out.println("|  __  | |  | | |\\/| |  __|  ");
            System.out.println("| |  | | |__| | |  | | |____ ");
            System.out.println("|_|  |_|\\____/|_|  |_|______|");
            System.out.println();
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("D")) {
                addDeposit();
            } else if (choice.equals("P")) {
                makePayment();
            } else if (choice.equals("L")) {
                ledgerScreen();
            } else if (choice.equals("X")) {
                running = false;
                System.out.println("Goodbye! Why did the banker quit? He lost interest!");
            } else {
                System.out.println("Invalid option. That's NOT funny. Please try again.. ");
            }

        }

    }

    // add deposit
    public static void addDeposit() {
        System.out.println();
        System.out.println(" _____  ______ _____   ____   _____ _____ _______ ");
        System.out.println("|  __ \\|  ____|  __ \\ / __ \\ / ____|_   _|__   __|");
        System.out.println("| |  | | |__  | |__) | |  | | (___   | |    | |   ");
        System.out.println("| |  | |  __| |  ___/| |  | |\\___ \\  | |    | |   ");
        System.out.println("| |__| | |____| |    | |__| |____) |_| |_   | |   ");
        System.out.println("|_____/|______|_|     \\____/|_____/|_____|  |_|   ");
        System.out.println("(Money in is the BEST kind of money)");
        System.out.println();
        System.out.print("Description: ");
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
        System.out.println("Deposit added! Why don't scientists trust atoms? Because they make up everything!");
    }

    // make payment
    public static void makePayment() {
        System.out.println();
        System.out.println(" _____    _ __     ____  __ ______ _   _ _______ ");
        System.out.println("|  __ \\  / \\\\ \\   / /  \\/  |  ____| \\ | |__   __|");
        System.out.println("| |__) |/ _ \\\\ \\_/ /| \\  / | |__  |  \\| |  | |   ");
        System.out.println("|  ___// ___ \\\\   / | |\\/| |  __| | . ` |  | |   ");
        System.out.println("| |   /_/   \\_\\| |  | |  | | |____| |\\  |  | |   ");
        System.out.println("|_|            |_|  |_|  |_|______|_| \\_|  |_|   ");
        System.out.println("(Saying goodbye to your money is hard, I know)");
        System.out.println();

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
        System.out.println("Payment recorded! I told my wallet a joke. It didn't laugh — it just got lighter.");
    }

    // ledger screen
    public static void ledgerScreen() {
        boolean onScreen = true;
        while (onScreen) {
            System.out.println();
            System.out.println(" _      ______ _____   _____ ______ _____  ");
            System.out.println("| |    |  ____|  __ \\ / ____|  ____|  __ \\ ");
            System.out.println("| |    | |__  | |  | | |  __| |__  | |__) |");
            System.out.println("| |    |  __| | |  | | | |_ |  __| |  _  / ");
            System.out.println("| |____| |____| |__| | |__| | |____| | \\ \\ ");
            System.out.println("|______|______|_____/ \\_____|______|_|  \\_\\");
            System.out.println();
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toUpperCase();

            if (choice.equals("A")) {
                displayAll();
            } else if (choice.equals("D")) {
                displayDeposits();
            } else if (choice.equals("P")) {
                displayPayments();
            } else if (choice.equals("R")) {
                reportScreen();
            } else if (choice.equals("H")) {
                onScreen = false;
            } else {
                System.out.println("Invalid option. Please try again");
            }
        }
    }

    // display all
    public static void displayAll() {
        System.out.println("\n===== ALL TRANSACTIONS ====");
        System.out.println("(Behold — every penny you've ever moved!)");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i).displayTransaction());
        }
    }

    // display depoist only
    public static void displayDeposits() {
        System.out.println("\n===== DEPOSITS =====");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                System.out.println(t.displayTransaction());
            }
        }
    }

    // display payments only
    public static void displayPayments() {
        System.out.println("\n===== PAYMENTS =====");
        System.out.println("(The painful stuff!)");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                System.out.println(t.displayTransaction());
            }

        }
    }

    // report screen
    public static void reportScreen() {
        boolean onScreen = true;
        while (onScreen) {
            System.out.println();
            System.out.println(" _____  ______ _____   ____  _____ _______ _____ ");
            System.out.println("|  __ \\|  ____|  __ \\ / __ \\|  __ \\__   __/ ____|");
            System.out.println("| |__) | |__  | |__) | |  | | |__) | | | | (___  ");
            System.out.println("|  _  /|  __| |  ___/| |  | |  _  /  | |  \\___ \\ ");
            System.out.println("| | \\ \\| |____| |    | |__| | | \\ \\  | |  ____) |");
            System.out.println("|_|  \\_\\______|_|     \\____/|_|  \\_\\ |_| |_____/ ");
            System.out.println("Numbers don't lie. Unfortunately.");
            System.out.println();
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                monthToDate();
            } else if (choice.equals("2")) {
                previousMonth();
            } else if (choice.equals("3")) {
                yearToDate();
            } else if (choice.equals("4")) {
                previousYear();
            } else if (choice.equals("5")) {
                searchByVendor();
            } else if (choice.equals("0")) {
                onScreen = false;
            } else {
                System.out.println("Invalid option. Please try again");
            }

        }
    }

    // month to date
    public static void monthToDate() {
        System.out.println("\n===== MONTH TO DATE ======");
        System.out.println("(How's the month treating you?)");
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        for (int i = transactions.size() -1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            LocalDate d = LocalDate.parse(t.getDate());
            if (d.getYear() == currentYear && d.getMonthValue() == currentMonth) {
                System.out.println(t.displayTransaction());
            }

        }

    }
    // previous month
    public static void previousMonth() {
        System.out.println("\n===== PREVIOUS MONTH =====");
        System.out.println("(Back to the future... I mean past)");
        LocalDate today = LocalDate.now();
        int prevMonth = today.getMonthValue() -1;
        int year = today.getYear();
        if (prevMonth == 0) {
            prevMonth = 12;
            year = year - 1;
        }

        for (int i =  transactions.size() -1 ; i >=0; i--) {
            Transaction t = transactions.get(i);
            LocalDate d = LocalDate.parse(t.getDate());
            if (d.getYear() == year && d.getMonthValue() == prevMonth) {
                System.out.println(t.displayTransaction());
            }

        }
    }
    // year to date
    public static void yearToDate() {
        System.out.println("\n===== YEAR TO DATE =====");
        System.out.println("(Time flies when you're spending money!)");
        int currentYear = LocalDate.now().getYear();

        for (int i = transactions.size() -1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            LocalDate d = LocalDate.parse(t.getDate());
            if (d.getYear() == currentYear) {
                System.out.println(t.displayTransaction());
            }
        }
    }
    //PREVIOUS YEAR
    public static void previousYear() {
        System.out.println("\n===== PREVIOUS YEAR=====");
        System.out.println("(Who's been taking your money?)");
        int prevYear = LocalDate.now().getYear() - 1;

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            LocalDate d = LocalDate.parse(t.getDate());
            if (d.getYear() == prevYear) {
                System.out.println(t.displayTransaction());
            }
        }
    }
    // search by vendor
    public static void searchByVendor() {
        System.out.print("Enter vendor name: ");
        String vendor = scanner.nextLine();

        System.out.println("\n===== TRANSACTION FOR " + vendor + " =====");
        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t.displayTransaction());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transaction found for vendor: " + vendor);
        }
    }


}









