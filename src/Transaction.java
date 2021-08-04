import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp; //date
    private String memo; //??
    private Account inAccount; //the account where the transaction was performed

    //CUNSTRACTOR 1
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = " ";
    } //CUNSTR 1 ENDS

    //CUNSTRACTOR 2
    public Transaction(double amount, String memo, Account inAccount){

        //call the two=arg constructor first
        this(amount, inAccount);

        this.memo=memo;
    }//CUNSTR 2 ENDS

    //get the amount of the transaction 
    public double getAmount(){
        return  this.amount;
    }

    //get a string summarizing the transaction
    public String getSummaryLine(){
        if(this.amount >= 0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        } else
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
                    this.amount, this.memo);
    }


}
