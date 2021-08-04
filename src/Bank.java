import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users; //list of customers; each element is of type USER
    private  ArrayList<Account> accounts;

    //CONSTRUCTOR
    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }//CONSTRUCTOR ENDS

    //generate new user id (random numbers) and check so it does not already exist in the system
    public String getNewUserUUID(){

        String uuid; //unique user id
        Random rng = new Random();
        int len = 6; //number of characters
        boolean nonUnique; //a flag

        //do something ones and check some conditions and keep
        //doing until condition == false
        do {
            //generate the number
            uuid = "";
            for(int i=0; i<len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString(); //0 inclusive and 10 exclusive; generates number and make to string
            }

            //check unique
            //iterates through all the objects
            nonUnique=false;
            for(User u: this.users){
                if(uuid.compareTo(u.getUUID()) == 0){ //IF == 0 >> strings are equal
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique); //keep generating until nonUnique==false

        return uuid;
    }

    public String getNewAccount(){

        String uuid; //unique user id
        Random rng = new Random();
        int len = 10; //number of characters; more accounts than users
        boolean nonUnique; //a flag

        //do something ones and check some conditions and keep
        //doing until condition == false
        do {
            //generate the number
            uuid = "";
            for(int i=0; i<len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString(); //0 inclusive and 10 exclusive; generates number and make to string
            }

            //check unique
            //iterates through all the objects
            nonUnique=false;
            for(Account account : this.accounts){
                if(uuid.compareTo(account.getUUID()) == 0){ //IF == 0 >> strings are equal
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique); //keep generating until nonUnique==false

        return uuid;
    }


    public void  addAccount(Account onAccount) {
        this.accounts.add(onAccount);
    }

    //create a new user
    public  User addUser(String firstname, String lastname, String pin){

        //create a new user and add it to the list
        User newUser = new User(firstname, lastname,pin, this);
        this.users.add(newUser);

        //create a savings account automatically
        Account newAccount = new Account("Savings", newUser, this);

        //create a new user accounts and add to User and Bank
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);



        return newUser;
    }

    //login method
    //get the User object associated with a particular userID
    //and pin, if they are valid
    public User userLogin(String userID, String pin){

        //search through list of users
        for(User u: this.users){

            //check if userID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }

        }
        //if user is not found or pin is incorrect
        return null;
    }

    //get the name of the bank
    public String getName(){
        return this.name;
    }

}
