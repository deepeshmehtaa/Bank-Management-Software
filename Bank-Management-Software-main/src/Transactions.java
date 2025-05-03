import java.util.*;

public class Transactions {
    Scanner sc = new Scanner(System.in);
    DAO dao = new DAO();

    public void credit(double amt, String accNo) throws Exception {
        double bal = Double.parseDouble(dao.checkBal(accNo).get(1));
        int c = dao.updateBalance(accNo, bal+amt);
        if (c==0) {
            System.out.println("Error!!!");
            System.exit(0);
        }
        System.out.println("$" + amt + " Amount Credited");
        System.out.println("Current Balance: " + (bal+amt));
    }
    public void debit(String username, double amt, String accNo) throws Exception {
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
        double bal = Double.parseDouble(dao.checkBal(accNo).get(1));
        int c = dao.updateBalance(accNo, bal-amt);
        if (c==0) {
            System.out.println("Error!!!");
            System.exit(0);
        }
        System.out.println("$" + amt + " Amount Debited");
        System.out.println("Current Balance: " + (bal-amt));
    }
    public void transfer(String username, double amt, String fAccNo, String tAccNo) throws Exception {
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
        if (!dao.accExist(fAccNo)) System.out.println("Sender's Account Number is wrong!!!");
        else if (!dao.accExist(tAccNo)) System.out.println("Receiver's Account Number is wrong!!!");
        else {
            double fbal = Double.parseDouble(dao.checkBal(fAccNo).get(1));
            double tbal = Double.parseDouble(dao.checkBal(tAccNo).get(1));
            dao.transfer(fbal-amt, tbal+amt, fAccNo, tAccNo);
            System.out.println("Transaction successful");
            System.out.println("Current Balance: " + (fbal-amt));
        }
    }
}
