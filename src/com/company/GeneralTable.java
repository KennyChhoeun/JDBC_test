package com.company;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;
/* Java program that will present contents of any
 * table call with one parameter - table name
 * Author: Kenny Chhoeun
 * CS 4350: Database System
 */
public class GeneralTable {

    public static void main(String[] args) {
	    if (args.length < 1) {
	        System.out.println("table name data not provided");
	        return;
        }
	    String tableName = args[0];
	    String ps = "8991tnim";
	    System.out.println("Showing Table: " + tableName);
	    try{
	        //load mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Trying connection with mySQL Server");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lab4","root",ps);
            Scanner in = new Scanner(System.in);
            System.out.println("1. Display Trip Schedule");
            System.out.println("2. Delete a trip offering");
            System.out.println("3. Add trip offering");
            System.out.println("4. Change driver for a trip");
            System.out.println("5. Change the bus for a trip offering");
            System.out.println("6. Display trip stop information");
            System.out.println("7. Display weekly schedule based of given driver and date");
            System.out.println("8. Add a driver");
            System.out.println("9. Add a bus");
            System.out.println("10. Delete a bus");
            System.out.println("11. Insert data of trip specified by key");
            System.out.println("\n\nPICK AN OPTION");

            short choice = in.nextShort();
            Statement stmt = conn.createStatement();
            switch (choice) {
                case 1:
                    displaySchedule(stmt);
                    break;
                case 2:
                    break;
            }
        } catch(Exception e) {
	        e.printStackTrace();
        }
    }

    public static void displaySchedule(Statement stmt) throws SQLException{
        Scanner in = new Scanner(System.in);
        System.out.print("StartLocationName: ");
        String startLocation = in.nextLine().trim();
        System.out.print("DestinationName: ");
        String destinationName = in.nextLine().trim();
        System.out.println("Date: ");
        String date = in.nextLine().trim();
        //get table data

        try {
            ResultSet rs = stmt.executeQuery("SELECT t.ScheduledStartTime, t.ScheduledArrivalTime, t.DriverName, t.BusID " +
                                                "FROM TripOffering t, Trip tp " +
                                                "WHERE tp.StartLocationName = '" + startLocation + "' AND " +
                                                "tp.DestinationName = '" + destinationName + "' AND " +
                                                "t.Date = '" + date + "' AND " +
                                                "t.TripNumber = tp.TripNumber " +
                                                "ORDER BY ScheduledStartTime");

            //print column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            for(int i = 1; i <= colCount; i++)
            {
                System.out.print(rsmd.getColumnName(i) + "\t\t\t\t");
            }
            System.out.println();
            //print out the data
            while(rs.next()){
                for(int i = 1; i<=colCount; i++)
                {
                    System.out.print(rs.getString(i)+"\t\t\t\t\t\t\t");
                }
                System.out.println();
            }
            rs.close();
            System.out.println("----------------------------------------------------------------------------------------");
        } catch (SQLException e){
            System.out.println("No schedule from " + startLocation + " to " + destinationName + " on " + date);
        }
    }
}