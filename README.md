# 💰 DAD JOKES BANK

## Description
*"Where every transaction comes with a smile (and a groan)"*

This is a simple CLI app for tracking financial transactions.
It can record deposits and payments.
You can view your full ledger or filter by deposits or payments only.
It also runs pre-built reports like Month to Date or Search by Vendor.
Every transaction is saved to a CSV file.
Your data sticks around between runs.

╔══════════════════════════════════╗
║                                  ║
║   💰   DAD JOKES BANK   💰       ║
║                                  ║
║   Track every dollar, in & out   ║
║                                  ║
╚══════════════════════════════════╝



## The Two Classes

### 📄 Transaction.java

The data class — represents one financial transaction.

- **5 private fields:** date, time, description, vendor, amount
- **Constructor** that takes all 5 values
- **5 getters** (no setters — transactions are immutable historical records)
- **`displayTransaction()`** — formats the transaction using `String.format()` for clean column alignment

### 📄 AccountingLedger.java

The main program — handles menus, file I/O, and reports.

- Loads transactions from CSV into an `ArrayList<Transaction>` on startup
- 3 menu screens: **Home**, **Ledger**, and **Reports**
- Saves new transactions to CSV using `FileWriter` in append mode
- Filters transactions by date, type (deposit/payment), or vendor
- Uses `LocalDate.now()` and `LocalTime.now()` to auto-stamp new transactions
---

## Menu Walkthrough

### 🏠 Home Screen
Main menu with 4 options: Add Deposit, Make Payment, Ledger, Exit. Loops until the user types X.
```java
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
```
### 💵 Add Deposit
Prompts for description, vendor, and amount. Date/time are auto-stamped. Saves to CSV.
```java
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
```
### 💳 Make Payment
Same as deposit, but auto-converts positive amounts to negative so users don't have to remember the sign.
```java 
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

```
### 📒 Ledger Screen
Sub-menu to view All, Deposits only, Payments only, or jump to Reports. Loops backwards through the ArrayList so newest transactions show first.
```java 
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
```
### 📊 Reports Screen
```java
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
```
Sub-menu with 5 reports: Month to Date, Previous Month, Year to Date, Previous Year, and Search by Vendor.
---
## Code I'm Most Proud of

## 💎 Code I'm Most Proud Of
The **reverse iteration trick** I use to show newest transactions first.
```java
for (int i = transactions.size() -1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            LocalDate d = LocalDate.parse(t.getDate());
            if (d.getYear() == currentYear && d.getMonthValue() == currentMonth) {
                System.out.println(t.displayTransaction());
            }

        }
```
The requirement said newest entries had to display first. My first thought was sorting,
but I realized — since I always add new transactions to the end of the ArrayList,
I can just loop backwards. No sorting needed.

This same backwards loop is used in 9 different methods — every display and report method.
 One simple pattern that solves the problem cleanly.
 Taught me that sometimes the simplest solution beats the fancy one.


I struggled a lot with **typos in variable names** at first.
Java doesn't care that they're "basically the same word."
Three slightly different names = three different variables to the compiler.
I learned to use IntelliJ's `Shift + F6` rename shortcut to fix this.

The other big one was **new transactions saving on the same line** in my CSV file.
The previous line didn't end with a newline character.
So new data got smushed onto the end of the old data.
Took me 20 minutes of staring at the file to figure it out.

There was also a moment where I had **6 compile errors at once**.
I didn't know where to start.
I learned to read error messages **top down**.
Fix the first one, recompile, see what's left.
That single trick saved me hours.

## Next Time...

I need to **think through the project before I start coding**.
I jumped right in without sketching out the menu structure.
I ended up refactoring a bunch of stuff because the screens didn't connect right.

I also need to **test more often instead of in big chunks**.
I wrote like 100 lines of code and tried to run everything at once.
Of course there were a dozen issues.
Next time I'll test each method as I write it.

And finally — **proofread before I commit**.
They're small things but they make the project look unprofessional.
