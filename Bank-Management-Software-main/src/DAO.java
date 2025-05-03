import java.sql.*;
import java.util.*;

public class DAO {
    String url = "jdbc:mysql://localhost:3306/BankDB";
    String un = "root";
    String p = "10010@Uday";
    Connection con;

    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, un, p);
            if (con == null) {
                System.out.println("Connection established.");
            }
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public void addUserCred(String name, String pass) throws Exception {
        String q = "insert into username_pass values(?,?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, name);
        ps.setString(2, pass);
        ps.executeUpdate();
    }
    public boolean checkInUsage(String name, boolean c) throws Exception {
        String q = "SELECT 1 FROM username_pass WHERE user_name = ?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (c) System.out.println("User name already exist.\nPlease select a new one.");
            return true;
        }
        return false;
    }
    public boolean loginCheckUser(String name, String pass) throws Exception {
        if (!checkInUsage(name,false)) {
            System.out.println("Wrong Username");
            return false;
        }
        String q = "SELECT password FROM username_pass WHERE user_name = ?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getString(1).equals(pass)) return true;
        System.out.println("Wrong Password");
        return false;
    }
    public boolean accExist(String accNo) throws Exception {
        String q = "SELECT 1 FROM acc_username WHERE Account_No = ?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, accNo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public void createAccount(String username, String type, double balance) throws Exception {
        String q2 = "select count(*) from acc_username where user_name = ?";
        PreparedStatement p2 = con.prepareStatement(q2);
        p2.setString(1, username);
        ResultSet rs = p2.executeQuery();
        rs.next();
        String AccNo = username + "@" + String.valueOf((rs.getInt(1)+1));
        String q1 = "insert into acc_username values (?,?)";
        PreparedStatement p1 = con.prepareStatement(q1);
        p1.setString(1, AccNo);
        p1.setString(2, username);
        p1.executeUpdate();
        System.out.println("\nYour account number is: "+AccNo);
        String q3 = "insert into accDet values(?,?,?)";
        PreparedStatement p3 = con.prepareStatement(q3);
        p3.setString(1, AccNo);
        p3.setString(2, type);
        p3.setDouble(3, balance);
        p3.executeUpdate();
    }
    public List getAccounts(String user) throws Exception {
        String q = "SELECT u.Account_No, a.Account_Type FROM acc_username u JOIN accDet a on u.Account_No = a.Account_No WHERE user_name = ?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        List<List<String>> ls = new ArrayList<>();
        while (rs.next()) {
            ls.add(new ArrayList<>());
            ls.get(ls.size()-1).add(rs.getString(1));
            ls.get(ls.size()-1).add(rs.getString(2));
        }
        return ls;
    }
    public void delAcc(String accNo) throws Exception {
        String q2 = "Delete FROM acc_username WHERE Account_No = ?";
        String q1 = "Delete FROM accDet WHERE Account_No = ?";
        PreparedStatement p1 = con.prepareStatement(q1);
        PreparedStatement p2 = con.prepareStatement(q2);
        p1.setString(1, accNo);
        p2.setString(1, accNo);
        p1.executeUpdate();
        p2.executeUpdate();
    }
    public List<String> checkBal(String accNo) throws Exception {
        String q = "SELECT Account_Type, Account_Balance FROM accDet WHERE Account_No = ?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, accNo);
        ResultSet rs = ps.executeQuery();
        rs.next();
        List<String> ls = new ArrayList<>();
        ls.add(rs.getString(1));
        ls.add(String.valueOf(rs.getDouble(2)));
        return ls;
    }
    public int updateBalance(String accNo, double amt) throws Exception {
        String q = "update accDet set Account_Balance = ? where Account_No = ?";
        PreparedStatement p = con.prepareStatement(q);
        p.setDouble(1, amt);
        p.setString(2, accNo);
        return p.executeUpdate();
    }
    public void transfer(double fBal, double tBal, String fAccNo, String tAccNo) throws Exception {
        String q = "update accDet set Account_Balance = ? where Account_No = ?";
        con.setAutoCommit(false);
        try {
            PreparedStatement p = con.prepareStatement(q);
            p.setDouble(1, fBal);
            p.setString(2, fAccNo);
            p.addBatch();
            p.setDouble(1, tBal);
            p.setString(2, tAccNo);
            p.addBatch();
            p.executeBatch();
            con.commit(); 
        } catch (Exception e) {
            con.rollback();
            System.out.println("Error!!!");
            System.exit(0);
        } finally {
            con.setAutoCommit(true);
        }
    }
    public void changePass(String username, String password) throws Exception {
        String q = "update username_pass set password = ? where user_name = ?";
        PreparedStatement p = con.prepareStatement(q);
        p.setString(1, password);
        p.setString(2, username);
        p.executeUpdate();
    }
}