package com.company;
import java.sql.*;
import java.util.Scanner;
/* Java program that will present contents of any table call with one parameter
 * table name
 */
public class GeneralTable {
    public static void main(String[] args) {
	    if (args.length < 1) {
	        System.out.println("table name data not provided");
	        return;
        }
	    String tableName = args[0];
	    System.out.println("Showing Table: " + tableName);
	    try{
	        //load mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Trying connection with mySQL Server");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lab4","root","8991tnim");
            Scanner in = new Scanner(System.in);
            System.out.print("1. Add to table\n2.Edit table\n3.");
            short choice = in.nextShort();
            switch (choice) {
                case 1:
                    break;


            }

            //result set
            Statement stmt = conn.createStatement();
            System.out.print("Select * From " + tableName + "\n");
            String varSql = "Select * From " + tableName;

            String dropTbl = "Drop table " + tableName;
            ResultSet result = stmt.executeQuery(varSql);
            ResultSetMetaData rsMeta = result.getMetaData();

            //display column names as string
            String colNames = "";
            int colCount = rsMeta.getColumnCount();
            for(int col = 1; col <= colCount; col++){
                colNames = colNames + rsMeta.getColumnName(col) + " ";
            }
            System.out.println(colNames);

            //display column values
            while(result.next()){
                for(int col = 1; col<= colCount; col++){
                    System.out.print(result.getString(col) + " ");
                }
                System.out.println();
            }

            //clean up
            result.close();
            stmt.close();
            conn.close();

        } catch(Exception e) {
	        e.printStackTrace();
        }
    }
}