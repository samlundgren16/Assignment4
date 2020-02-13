package cs363;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.Reader;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.Scanner;

/**
 * Two Methods: Main and DialogForLogin
 * @author Sam Lundgren
 *Main uses a chain of else if's to determine what the user is asking the program to do, checks what user input is and 
 *runs the task required of input
 */
public class hw4_1 {
    
    /**
     * dialogForLogin Allows communication with the menu that appears
     * @return theAns
     */
    public static String[] dialogForLogin() {
    	//setting variables
        String theAns[] = new String[2];
        JPanel thePan = new JPanel(new GridBagLayout());
        GridBagConstraints gridConstraint = new GridBagConstraints();

        gridConstraint.fill = GridBagConstraints.HORIZONTAL;

        //username label
        JLabel theUserName = new JLabel("Username: ");
        gridConstraint.gridx = 0;
        gridConstraint.gridy = 0;
        gridConstraint.gridwidth = 1;
        thePan.add(theUserName, gridConstraint);

        //username text field
        JTextField theUsernameTF = new JTextField(20);
        gridConstraint.gridx = 1;
        gridConstraint.gridy = 0;
        gridConstraint.gridwidth = 2;
        thePan.add(theUsernameTF, gridConstraint);

        //password label
        JLabel thePassword = new JLabel("Password: ");
        gridConstraint.gridx = 0;
        gridConstraint.gridy = 1;
        gridConstraint.gridwidth = 1;
        thePan.add(thePassword, gridConstraint);

        //password text field
        JPasswordField thePasswordPF = new JPasswordField(20);
        gridConstraint.gridx = 1;
        gridConstraint.gridy = 1;
        gridConstraint.gridwidth = 2;
        thePan.add(thePasswordPF, gridConstraint);
        thePan.setBorder(new LineBorder(Color.GRAY));

        //options to click on
        String[] theOptions = new String[] { "OK", "Cancel" };
        int otherOption = JOptionPane.showOptionDialog(null, thePan, "Login", JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, theOptions, theOptions[0]);
        if (otherOption == 0) // pressing OK button
        {
            theAns[0] = theUsernameTF.getText();
            theAns[1] = new String(thePasswordPF.getPassword());
        }
        //else if(otherOption == 1)//pressing cancel
        //{
        	
        //}
        return theAns;
    }
    
    //Examples taken from TestJDBC from Tavanapong
    public static void main(String[] args) {
    	//address and username/password initializing
        String dbServer = "jdbc:mysql://localhost:3306/graph?useSSL=false";
        String userName = "";
        String password = "";

        //calling dialogForLogin and setting username and password
        String theAns[] = dialogForLogin();
        userName = theAns[0];
        password = theAns[1];
        //start setting up connection and statement variables
        Connection connectionTest;
        Statement state_ment;

        //creating a scanner named after yours truly
        Scanner samScanner = null;
        //try and catch
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionTest = DriverManager.getConnection(dbServer, userName, password);
            state_ment = connectionTest.createStatement();
            String theQuerySQL = "";

            String theOption = "";
            String doAsInstructed = "Enter a: Insert a new graph node." + "\n" + "Enter b: Insert a new graph edge." + "\n"
                    + "Enter c: Delete a graph node." + "\n" + "Enter d: List all reachable nodes from a given node."
                    + "\n" + "Enter e: Quit Program";

            //for inserting a new graph node
            while (true) {
                theOption = JOptionPane.showInputDialog(doAsInstructed);
                if (theOption.equals("a")) {
                    samScanner = new Scanner(System.in);

                    System.out.print("Enter node name: ");
                    String nodeEntered = samScanner.nextLine();
                    System.out.print("Enter node importance (integer): ");
                    int nodeImportance = samScanner.nextInt();
                    String sqlStatement = "insert into nodes " + " (nodename, importance)" + " values (?, ?) ";

                    PreparedStatement state_ment_M = connectionTest.prepareStatement(sqlStatement);

                    // set param values
                    state_ment_M.setString(1, nodeEntered);
                    state_ment_M.setInt(2, nodeImportance);
                    // Execute SQL Query
                    try {
                        state_ment_M.executeUpdate();
                        System.out.println("Completed this insert.");

                    } catch (MySQLIntegrityConstraintViolationException ec) {
                        System.out.print("Sorry, Duplicate entry, cannot insert node");
                    }
                } else if (theOption.equalsIgnoreCase("b")) {
                	//entering a new graph edge
                    samScanner = new Scanner(System.in);

                    System.out.print("Please Enter the start node name: ");
                    String enteredStartNode = samScanner.nextLine();
                    System.out.print("Please Enter the end node name: ");
                    String enteredEndNode = samScanner.nextLine();
                    System.out.print("Please Enter the edge cost as an integer: ");
                    int enteredCost = samScanner.nextInt();

                    String sqlStatement = "select nodename from nodes";
                    PreparedStatement state_ment_M = connectionTest.prepareStatement(sqlStatement);

                    ResultSet result_set = state_ment_M.executeQuery(sqlStatement);

                    boolean checkStart = false;
                    boolean checkEnd = false;

                    while (result_set.next()) {
                        String theNode = result_set.getString("nodename");
                        if (enteredStartNode.equals(theNode)) {
                            checkStart = true;

                        }
                        if (enteredEndNode.equals(theNode)) {
                            checkEnd = true;
                        }
                    }

                    if (!checkStart) {
                        System.out.print("Please Enter the start node importance (integer): ");
                        int enteredNodeImportance = samScanner.nextInt();
                        String sqlStatementInsert = "insert into nodes " + " (nodename, importance)" + " values (?, ?) ";
                        PreparedStatement start_statement = connectionTest.prepareStatement(sqlStatementInsert);
                        start_statement.setString(1, enteredStartNode);
                        start_statement.setInt(2, enteredNodeImportance);
                        start_statement.executeUpdate();
                        System.out.println("The Start node is inserted with importance.");
                    }
                    if (!checkEnd) {
                        System.out.print("Please Enter end node importance (integer): ");
                        int enteredNodeImportance = samScanner.nextInt();
                        String sqlStatementInsertEnd = "insert into nodes " + " (nodename, importance)" + " values (?, ?) ";
                        PreparedStatement end_statement = connectionTest.prepareStatement(sqlStatementInsertEnd);
                        end_statement.setString(1, enteredEndNode);
                        end_statement.setInt(2, enteredNodeImportance);
                        end_statement.executeUpdate();
                        System.out.println("The End node is inserted with importance.");
                    }
                    String sqlStatementInsertEdge = "insert into edges " + " (startnode, endnode, cost)" + " values (?, ?, ?) ";
                    PreparedStatement state_ment_edge = connectionTest.prepareStatement(sqlStatementInsertEdge);

                    // set param values
                    state_ment_edge.setString(1, enteredStartNode);
                    state_ment_edge.setString(2, enteredEndNode);
                    state_ment_edge.setInt(3, enteredCost);
                    try {
                        state_ment_edge.executeUpdate();
                        System.out.println("The Insert of edge is complete.");

                    } catch (MySQLIntegrityConstraintViolationException ec) {
                        System.out.print("Sorry, there is a Duplicate edge, error\n");
                    }
                } else if (theOption.equals("c")) {
                	//for entering a node to delete
                    samScanner = new Scanner(System.in);
                    System.out.print("Please Enter the delete node name: ");
                    String deleteNodeEntered = samScanner.nextLine();

                    String sqlStatementDelete = "delete from edges where startnode = '" + deleteNodeEntered + "' or endnode = '"
                            + deleteNodeEntered + "'";
                    PreparedStatement deleteThis = connectionTest.prepareStatement(sqlStatementDelete);

                    deleteThis.executeUpdate();
                    String sqlDeleteStatement = "delete from nodes where nodename = '" + deleteNodeEntered + "'";
                    PreparedStatement delete_node = connectionTest.prepareStatement(sqlDeleteStatement);

                    delete_node.executeUpdate();
                    System.out.print("This Operation is  finished\n");
                } else if (theOption.equals("d")) {
                	//for seeing a list of all reachable nodes from a given node
                    samScanner = new Scanner(System.in);
                    System.out.print("Please Enter the node name to find all reachable nodes: ");
                    String nodeEnteredCheck = samScanner.nextLine();

                    try {
                        connectionTest = DriverManager.getConnection(dbServer, userName, password);
                        CallableStatement statementCall = connectionTest.prepareCall("{call reachable(?)}");
                        statementCall.setString(1, nodeEnteredCheck);

                        ResultSet result_set;
                        ResultSetMetaData result_set_meta_data;
                        //stmt = connectionTest.createStatement();
                        result_set = statementCall.executeQuery();
                        result_set_meta_data = result_set.getMetaData();
                        String showThe = "";
                        while (result_set.next()) {
                            for (int i = 0; i < result_set_meta_data.getColumnCount(); i++) {
                                showThe += result_set.getString(i + 1) + ", ";
                            }
                            showThe += "\n";
                        }

                        System.out.println(showThe);
                        
                        statementCall.close();
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    }

                } else {
                    System.out.print("GOOD BYE!, Exited The Program");
                    break;
                }
            }

            state_ment.close();
            connectionTest.close();
        } catch (Exception ec) {
            System.out.println("This Program terminated due to errors");
            ec.printStackTrace(); // for debugging
        }
        //Cannot figure out how to not get an error for the cancel button press to show up
    }

}
