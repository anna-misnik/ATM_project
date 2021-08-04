import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        //init scanner
        Scanner scanner = new Scanner(System.in);

        //init Bank
        Bank theBank = new Bank("Bank of Minions");

        // add a user, with savingsaccount
        User user1 = theBank.addUser("John", "Bush", "1234");

        //add a checking account for user
        Account account1 = new Account("Checking", user1, theBank);
        theBank.addAccount(account1);
        user1.addAccount(account1);

        User currentUser;
        while (true) {

            //stay in the login prompt until successful login
            currentUser = ATM.mainMenuPrompt(theBank, scanner);

            //stay in main menu until user quits
            ATM.printUserMenu(currentUser, scanner);
        }

    }
public static User mainMenuPrompt(Bank theBank,Scanner scanner){
        //inits
    String userID;
    String pin;
    User authUser;

    //prompt the user for user ID/pin combo until a correct one is reached
    do {
        System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
        System.out.print("Enter user ID: ");
        userID = scanner.nextLine();
        System.out.print("Enter pin: ");
        pin = scanner.nextLine();

        //try to get the user object corresponding to the ID & pin combi
        authUser = theBank.userLogin(userID,pin);
        if(authUser== null){
            System.out.println("Incorrect user ID/pin combination. " +
                    "Please try again.");
        }
    }while (authUser == null); //continue looping until successful login

    return authUser;
    }

    public  static void printUserMenu(User authUser, Scanner scanner){

        //print a summery of the user's accounts
        authUser.printAccountsSummery();

        // init
        int choice;

        //user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
                    authUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = scanner.nextInt();

            //handling cases only with the numbers, not letters
            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5.");
            }
        }while(choice < 1 || choice > 5);

        //process the choice
        switch (choice){

            case 1:
                ATM.showTransHistory(authUser, scanner);
                break;
            case 2:
                ATM.withdrawlFunds(authUser, scanner);
                break;
            case 3:
                ATM.depositFunds(authUser, scanner);
                break;
            case 4:
                ATM.transferFunds(authUser,scanner);
                break;
            case 5:
                //gobble up rest of previous input
                scanner.nextLine();
                break;
        }

        //redisplay the enu unless user wants to quit
        //recursive call
        if (choice != 5){
            ATM.printUserMenu(authUser, scanner);
        }
    }
    public static void showTransHistory(User theUser, Scanner scanner){

        int account;
        //get the transaction choise
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ", theUser.numAccount());
            account=scanner.nextInt()-1; //for clients: start with 1, for developers: start with 0 so index-1
            if (account < 0 || account >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(account < 0 || account >= theUser.numAccount()); //loop until the account index is valid

        //print the transaction history
        theUser.printAccountTransactionHistory(account);
    }

    public static void transferFunds(User authUser,Scanner scanner){

        //inits
        int fromAcc;
        int toAcc;
        double amount;
        double acctBalance;

        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", authUser.numAccount());
            fromAcc = scanner.nextInt()-1;
            if (fromAcc < 0 || fromAcc >= authUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAcc < 0 || fromAcc >= authUser.numAccount());
        acctBalance = authUser.getAcctBalance(fromAcc);


        //get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", authUser.numAccount());
            toAcc = scanner.nextInt()-1;
            if (toAcc < 0 || toAcc >= authUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAcc < 0 || toAcc >= authUser.numAccount());

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBalance);
            amount = scanner.nextDouble();
            if(amount <= 0 || amount > acctBalance){
                System.out.println("Invalid amount. Try again.");
            }
        }while(amount <= 0 || amount > acctBalance);

        //finally, do the transfer
        authUser.addAccountTransaction(fromAcc, -1*amount, String.format(
                "Transfer to account %s", authUser.getAcctUUID(toAcc)));

        authUser.addAccountTransaction(toAcc, amount, String.format(
                "Transfer from account %s", authUser.getAcctUUID(fromAcc)));
    }

    public static void withdrawlFunds(User authUser,Scanner scanner){

        //inits
        int fromAcc;
        double amount;
        double acctBalance;
        String memo;

        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", authUser.numAccount());
            fromAcc = scanner.nextInt()-1;
            if (fromAcc < 0 || fromAcc >= authUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(fromAcc < 0 || fromAcc >= authUser.numAccount());
        acctBalance = authUser.getAcctBalance(fromAcc);

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBalance);
            amount = scanner.nextDouble();
            if(amount <= 0 || amount > acctBalance){
                System.out.println("Invalid amount. Try again.");
            }
        }while(amount <= 0 || amount > acctBalance);

        //gobble up rest of previous input
        scanner.nextLine();

        //get a memo
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        //do the withdraw
        authUser.addAccountTransaction(fromAcc,-1*amount, memo);


    }

    public static void depositFunds(User authUser, Scanner scanner){


        //inits
        int toAcc;
        double amount;
        double acctBalance;
        String memo;

        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", authUser.numAccount());
            toAcc = scanner.nextInt()-1;
            if (toAcc < 0 || toAcc >= authUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(toAcc < 0 || toAcc >= authUser.numAccount());
        acctBalance = authUser.getAcctBalance(toAcc);

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBalance);
            amount = scanner.nextDouble();
            if(amount < 0){
                System.out.println("Invalid amount. Try again.");
            }
        }while(amount < 0);



        //gobble up rest of previous input
        scanner.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();

        //do the withdraw
        authUser.addAccountTransaction(toAcc,amount, memo);



    }
}
