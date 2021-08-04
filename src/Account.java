import java.util.ArrayList;

public class Account {

    private  String name;
    private String uuid;
    private User holder; //the owner
    private ArrayList<Transaction> transactions; //list of transactions (history)

    //CUNSTRACTOR
    public Account(String name, User holder, Bank theBank){
        //setters
        this.name=name;
        this.holder=holder;

        this.uuid = theBank.getNewAccount();

        this.transactions = new ArrayList<Transaction>();

    }//CUNSTRACTOR ENDS

    //getter
    public  String getUUID(){
        return this.uuid;
    }

    //get the summary line for each account
    public String getSummaryLine(){

        //get the balance
        double balance = this.getBalance();

        //format the summary line, depending on the whether the balance is negative
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
        else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance(){
        double balance = 0;
        for(Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    //print the transaction history
    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int j = this.transactions.size()-1; j>= 0; j--){
            System.out.printf(this.transactions.get(j).getSummaryLine());
        }
        System.out.println();
    }

    //add transaction to the account history
    public void addTransaction(double amount, String memo){
        //create new transaction object and add it to the list
        Transaction newTrans = new Transaction(amount,memo,this);
        this.transactions.add(newTrans);
    }
}
