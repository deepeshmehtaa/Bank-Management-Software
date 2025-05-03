import java.util.*;

public class Account {
    Scanner sc = new Scanner(System.in);
    DAO dao = new DAO();
    public void menu(String username) throws Exception {
        while (true) {
            System.out.println("\nSelect from the Menu: ");
            System.out.println("1) Create new Account");
            System.out.println("2) Check if account exists with the Account No.");
            System.out.println("3) Get list of all the accounts");
            System.out.println("4) Delete Account");
            System.out.println("5) Check Account Balance");
            System.out.println("6) Make transaction");
            System.out.println("7) Change Password");
            System.out.println("8) Log Out\n");
            
            int out = sc.nextInt();
            sc.nextLine();
            System.out.println();
            switch (out) {
                case 1:
                    createAccount(username);
                    break;
                case 2:
                    accExist();
                    break;
                case 3:
                    accList(username);
                    break;
                case 4:
                    delAcc(username);
                    break;
                case 5:
                    checkBal();
                    break;
                case 6:
                    makeT(username);
                    break;
                case 7:
                    changePass(username);
                    break;
                case 8:
                    User_LogIn.LogIn();
                    break;
                default:
                    System.out.println("Enter valid number");
                    break;
            }
            System.out.println("\nIf you want to QUIT press 'Y', if not then anything else");
            String c = sc.nextLine();
            if (c.toUpperCase().equals("Y")) {
                System.out.println("\nThank You for using.");
                System.exit(0);
            }
        }
    }
    public void createAccount(String user_name) throws Exception {
        System.out.println("Enter type of account");
        String type = sc.nextLine();
        System.out.println("Enter initial balance of the account");
        double bal = sc.nextDouble();
        dao.createAccount(user_name,type,bal);
    }
    public void accExist() throws Exception {
        System.out.println("Enter account number to be checked");
        String accNo = sc.nextLine();
        System.out.println();
        if (dao.accExist(accNo)) System.out.println("Account No: " + accNo + " Exists");
        else System.out.println("Account No: " + accNo + " Doesn't exists");
    }
    public void accList(String user_name) throws Exception{
        List<List<Integer>> ls = dao.getAccounts(user_name);
        System.out.println("Account Numbers associated with the user name: " + user_name + " are:\n");
        for (List x: ls) {
            System.out.println("Account Number: " + x.get(0) + "\tAccount Type: " + x.get(1));
        }
    }
    public void delAcc(String username) throws Exception {
        System.out.println("Enter the account number to be deleted: ");
        String accNo = sc.nextLine();
        System.out.println("Enter the password to proceed.");
        int n = 0;
        while (true) {
            String pass = sc.nextLine();
            if (n==3) {
                System.out.println("\nContinous wrong passwords!!!");
                System.out.println("Log in again");
                n = Integer.MIN_VALUE;
                break;
            }
            if (!dao.loginCheckUser(username, pass)) {
                n++;
            }
            else break;
        }
        if (n==Integer.MIN_VALUE) User_LogIn.LogIn();
        System.out.println("\nAre you sure you want to delete it? (Press 'Y')");
        String c = sc.nextLine();
        if (!c.toUpperCase().equals("Y")) return;
        dao.delAcc(accNo);
        System.out.println("Account deleted");
    }
    public void checkBal() throws Exception {
        System.out.println("Enter the account number: ");
        String accNo = sc.nextLine();
        List<String> ls = dao.checkBal(accNo);
        System.out.println("\nAccount type: " + ls.get(0));
        System.out.println("Account balance: " + ls.get(1));
    }
    public void changePass(String username) throws Exception {
        boolean c = false;
        String pass1;
        String pass2;
        do {    
            if (c) {
                System.out.println("Passwords doesn't match.");
            }
            System.out.println("\nEnter a password:");
            pass1 = sc.nextLine();
            System.out.println("Enter again");
            pass2 = sc.nextLine();
            c = true;
        }
        while (!pass1.equals(pass2));
        dao.changePass(username, pass2);
        System.out.println("\nNow please log in");
        User_LogIn.LogIn();
    }
    public void makeT(String username) throws Exception {
        boolean ch = true;
        while (ch) {
            System.out.println("\nSelect one of the following transactions:");
            System.out.println("1) Credit into Account");
            System.out.println("2) Debit from Account");
            System.out.println("3) Transfer to another Account");
            System.out.println("4) Back\n");
            int c = sc.nextInt();
            sc.nextLine();
            System.out.println();

            Transactions t = new Transactions();
            switch (c) {
                case 1:
                    System.out.println("Enter the Account Number: ");
                    String accNo = sc.nextLine();
                    System.out.println("Enter the amount to be credited: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    t.credit(amt, accNo);
                    break;
                case 2:
                    System.out.println("Enter the Account Number: ");
                    String acNo = sc.nextLine();
                    System.out.println("Enter the amount to be debited: ");
                    double am = sc.nextDouble();
                    sc.nextLine();
                    t.debit(username, am, acNo);
                    break;
                case 3:
                    System.out.println("Enter the Sender's Account Number: ");
                    String fAccNo = sc.nextLine();
                    System.out.println("Enter the Receiver's Account Number: ");
                    String tAccNo = sc.nextLine();
                    System.out.println("Enter the amount to be credited: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    t.transfer(username, amount, fAccNo, tAccNo);
                    break;
                case 4:
                    menu(username);
                    break;
                default:
                    System.out.println("Enter Correct Input!!!\n");
                    ch = false;
                    break;
            }
            ch = !ch;
        }
    }
}
