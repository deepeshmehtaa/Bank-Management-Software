Bank Management System (Java + JDBC + MySQL + JFrame)
A simple console + GUI-based mini banking system built using Java, JDBC, MySQL, and JFrame.

Project Summary:

1) Allows user registration, login, and full account management.
2) Users can create, delete, check balance, and transfer funds between accounts.
3) Transactions include: credit, debit, transfer (with password confirmation).
4) DAO class handles all JDBC calls (insert, update, check), ensuring smooth database communication.
5) JFrame is used to make the system interactive with GUI components like forms, dialogs, and menus.

DB Setup (MySQL):

create database BankDB; 
use BankDB;
create table username_pass(User_name varchar(20) primary key, password varchar(10));
create table accDet(Account_No varchar(20) primary key, Account_Type varchar(10), Account_Balance double);
create table acc_username(Account_No varchar(20) primary key, User_name varchar(20));

Tools Used:

1) Java (Core + Swing)
2) JDBC (MySQL connector)
3) MySQL (Local DB)
4) JFrame (for user-friendly interface)