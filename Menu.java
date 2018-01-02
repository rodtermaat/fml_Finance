
/**
 * This is the main driver of the application.  
 * It is the UI and eventually will be converted to 
 * a screen and for Android - which started this little adventure.  
 * See the read me for more details
 * 
 * @author (rod termaat)
 * @version (version 0.0 of fml finance)
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Menu
{
    // instance variables - replace the example below with your own
    Scanner keyboard = new Scanner(System.in);
    boolean exit;   
                    //keeps the program running
    
    int tranID = 0; // created a unique id for each transaction.  I know theres a better way,
                    // but this is what I know now
    boolean zBalance;
                    // tracks if you have initalized the the ledger balance.  
                    // and stops you from doing it more than once. Again better way?
    
    ArrayList<Transaction> rows = new ArrayList<>();
                    // create an ArrayList of the Transaction object and creates
                    // the ledger/checkbook of the application
    Transaction row;
                    // not sure what this actually does, but is needed based
                    // on similar sample code I have studied
                    
    Balance balance = new Balance();
                    // created a new Balance object called balance
                    // and takes care of tracking the various deposits and
                    // expense of each transaction
    //Balance balance;
                    // It does not appear that this is needed here.
                    // so why is it needed above in Transaction?
                    
    
    // Just the main method that executes when the program starts                
    public static void Main(String[] args){
        Menu menu = new Menu();
        menu.runMenu();
    }

    // drives the program until exit is true which is set in the printMenu method
    public void runMenu(){
        printHeader();
        while(!exit){
            printMenu();
            int choice = getInput();
            performAction(choice);
        }
    }
    
    private void printHeader(){
        System.out.println("---------------------------------");
        System.out.println("|    Welcome to fml Finance.    |");
        System.out.println("|          f ACILITATING        |");
        System.out.println("|          m INIMALIST          |");
        System.out.println("|          l IVING              |");
        System.out.println("---------------------------------");
    }
    
    // Prints out the menu constantly. Almost too much, but hey its a console app
    private void printMenu(){
        System.out.println("");
        System.out.println("Please make a selection");
        System.out.println("1) make deposit");
        System.out.println("2) enter expense");
        System.out.println("3) list Transactions");
        System.out.println("4) account setup");
        System.out.println("0) exit");
    }
    
    // Reads the input of the user from the printMenu above
    // makes sure their choice is valid
    private int getInput(){
        int choice = -1;
        do {
            System.out.println("");
            System.out.print("Enter a selection...");
        try {
            choice = Integer.parseInt(keyboard.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("Error(1): Only numbers allowed");
        }
        if(choice < 0 || choice > 4){
            System.out.println("Error(2): 1 - 4 only dumbass");
        }
       } while(choice < 0 || choice > 4);
        return choice;
    
    }
    
    // Attempt to simplify the call and response nature of the console app
    private String askQuestion(String question){
        String response = "";
        Scanner input = new Scanner(System.in);
        System.out.print(question);
        response = input.nextLine();
        return response;
    }
    
    // Calls the various methods in response to the users choice
    private void performAction(int choice){
        switch(choice){
            case 0:
                //System.out.println("");
                System.out.println("Thanks for playing. Now go save some $");
                System.exit(0);
                break;
            case 1:
                addDeposit();
                break;
            case 2:
                addExpense();
                break;
            case 3:
                listTransactions();
                break;
            case 4:
                initialBalance();
                break;
             default: 
                System.out.println("Error(3) unknown error");
            }
        }
    
    // Calls the setBalance meethod in the Balance class to initialize the ledger
    // with an amount the user enters.  Basically your initial deposit into the account
    private void initialBalance(){
        //this process does not stop the user from invoking it after doing some
        //deposits or withdraws.  Need to correct this so they cannot do it later?
        if(!zBalance){
        int iBalance = Integer.parseInt(askQuestion("Enter and initial balance (0 is aok)..."));
        balance.setBalance(iBalance);
        System.out.println("Account initialize with $" + balance.getBalance());
        zBalance = true;
        }
        // prints this if they try to do set up twice.
        else {
            System.out.println("Account already seeded, nice try sucker");
        }
    }   
    
    // Calls the deposit method of the Balance class
    // It makes sure they enter a number and its accounts 
    // for the positive and negative entries.
    // This method method and the withdraw method track the balance
    // separately than the transaction.  Something to be considersed
    // later.  Should it all be done together?  I think so, but not really
    // sure the best way to implement.
    // Needs to better deal with integer and such, but good for now
    private void addDeposit(){
        int d_amount = 0;
        try
        { d_amount = Integer.parseInt(askQuestion("Enter deposit amount..."));
          if (d_amount > 0){
          addTransaction(d_amount);
          String display = (balance.deposit(d_amount));
          System.out.println (display);
        }
          else {
          addTransaction(-1 * d_amount);
          String display = balance.deposit(-1 * d_amount);
          System.out.println (display);
         } 
        }
        catch(NumberFormatException e)
        {
            System.out.println("By their very nature Deposits are not 0 - dumbass!");
        }
    }
    
    // Same as deposit, but for Expense
    private void addExpense(){
        int e_amount = 0;
        try
        { e_amount = Integer.parseInt(askQuestion("Enter expense amount..."));
          if (e_amount < 0){
          addTransaction(e_amount);
          String display = balance.withdraw(e_amount);
          System.out.println (display);}
          else {
          addTransaction(-1 * e_amount);
          String display = balance.withdraw(-1 * e_amount);
          System.out.println (display);
        }
         }
        catch(NumberFormatException e)
        {   System.out.println("By their very nature Expenses are not 0 - dumbass!");
        }
    
    }
    
    // Does the work of adding the transaction row to the ArrayList
    // via the Transaction oject
    private void addTransaction(int t_amount){
        String category = askQuestion("Enter category... ");
        String name = askQuestion("Enter description... ");
        //int amount = Integer.parseInt(askQuestion("Enter amount like 1234... "));
        String date = askQuestion("Enter date like 01/01/18... ");
      
        tranID++;
        row = new Transaction(tranID, date, category, name, t_amount);
        rows.add(row);
        }
    
    // List out the rows of the Array list to the console
    // There is some silly tab (\t) stuff there to try and
    // control the look of the text on the console
    private void listTransactions(){
        System.out.println("----------------------");
        System.out.println("Your balance is...$" + balance.getBalance());
        System.out.println("----------------------------------");
        System.out.println("Index\t Date\t\t Category\tName\t\tAmount");
        System.out.println("---------------------------------------------------------------");
        String num;
        
        for (Transaction printRow : rows)
        {
            String t_cat;
            String t_name;
            if(printRow.getCategory().length() < 7){
               t_cat = "\t\t";}
               else{ t_cat = "\t";}
            if(printRow.getName().length() < 8) {
                t_name = "\t\t";}
                else{ t_name = "\t";}
                
            //System.out.println(printRow.getName().length());
            System.out.println(
                    printRow.getID() + "\t" + 
                    printRow.getDate() + "\t " +
                    //printRow.getCategory() + "\t\t" +
                    printRow.getCategory() + t_cat +
                    //printRow.getName() + "\t\t" +
                    printRow.getName() + t_name +
                    printRow.getAmount());

        }
    }
    
}