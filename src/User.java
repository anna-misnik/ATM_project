import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {

    private String firstname;
    private String lastname;
    private String uuid; //id code for the user
    private byte pinHash[]; //the MD5 hash (128 bit) of the user`s pin number
    private ArrayList<Account> accounts; //easier insertion; list of accounts for the particular user


    //CONSTRUCTOR
    public User(String firstname, String lastname, String pin, Bank theBank){
        this.firstname=firstname;
        this.lastname=lastname;

        //hashing pin using MD5 algorithm
        //security reasoons
        //trycatch needed as Java does not know the string I am passing (MD5)
        //but it is never gonna be caught  because MD5 is valid alg.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //get the bytes of th epin object and  digest through alg and return another array of bytes
            this.pinHash = md.digest(pin.getBytes());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught noSuch AlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //generate uuid for the account
        this.uuid = theBank.getNewUserUUID();

        //create enpty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastname, firstname, this.uuid);
    } //CONSTRUCTOR ENDS

    public void addAccount(Account onAcct){
        this.accounts.add(onAcct);
    }

    //getter
    public String getUUID(){
        return this.uuid;
    }

    public String getFirstName(){
        return this.firstname;
    }

    //Check whether a given pin matches the true User pin
    public boolean validatePin(String pin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); //hashing algorithm
            return MessageDigest.isEqual(md.digest(pin.getBytes()),
                    this.pinHash); //check if provided pin is equal to the stored pin
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught noSuch AlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    //print summaries for the accounts of this user
    public void printAccountsSummery(){
        System.out.printf("\n\n%s's accounts summary\n", this.firstname);
        for(int i=0; i<this.accounts.size(); i++){
            System.out.printf(" %d) %s\n", i+1,
                    this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    //get the number of accounts of the user
    public int numAccount(){
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int accindex){
        this.accounts.get(accindex).printTransHistory();
    }

    public double getAcctBalance(int account){
        return this.accounts.get(account).getBalance();
    }

    //get the UUID of a particular account
    public String getAcctUUID(int acctIndx) {
        return this.accounts.get(acctIndx).getUUID();
    }

    public void addAccountTransaction(int acctIndx, double amount, String memo){
        this.accounts.get(acctIndx).addTransaction(amount, memo);
    }
}
