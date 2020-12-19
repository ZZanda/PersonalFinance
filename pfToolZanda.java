package pftool;

import java.sql.*;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class pfToolMain {
    public static void main(String[] args) throws Exception {
        String connectionString = "jdbc:mysql://localhost/pftool?user=test&password=test";
        Connection conn = DriverManager.getConnection(connectionString);

        Statement statement = conn.createStatement();
        ResultSet resultSet;

        try (Scanner scanner = new Scanner(System.in)) {
            //Select action
            System.out.print("Select action (1 - Add new expense, 2 - Delete expense, 3 - Get report): ");
            int actionID = scanner.nextInt();
            if (actionID < 1 || actionID > 3) {
                System.out.println("Invalid input!");
                return;

            } else if (actionID == 1) {
                //Enter expense year
                System.out.print("Enter year (year should be current or previous year: ");
                int newExpenseYear = scanner.nextInt();
                if (newExpenseYear < Calendar.getInstance().get(Calendar.YEAR) - 1 || newExpenseYear > Calendar.getInstance().get(Calendar.YEAR)) {
                    System.out.println("Invalid input!");
                    return;

                } else {
                    //If year is valid, enter expense month
                    System.out.print("Enter month (1-12): ");
                    int newExpenseMonth = scanner.nextInt();
                    if (newExpenseMonth < 1 || newExpenseMonth > 12) {
                        System.out.println("Invalid input!");
                        return;

                    } else {
                        //If month is valid, select category
                        System.out.print("Select from fallowing categories: ");

                        //Query categories from the DB
                        resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                                " FROM categories" +
                                " ORDER BY ID ASC");

                        //Get categories and store existing IDs
                        int[] categoryIds = new int[20];
                        int index = 0;
                        while (resultSet.next()) {
                            int id = resultSet.getInt("ID");
                            String categoryName = resultSet.getString("CategoryName");
                            categoryIds[index] = id;
                            index++;
                            System.out.print(id + " - " + categoryName + " ");
                        }
                        System.out.println();
                        System.out.print("Enter category ID: ");
                        int categoryIdEntered = scanner.nextInt();

                        //Get entered category name
                        for (int i = 0; i < categoryIds.length; i++) {
                            if (categoryIds[i] == categoryIdEntered) {
                                PreparedStatement getCategoryName = conn.prepareStatement("SELECT CategoryName FROM Categories WHERE ID = ?");
                                getCategoryName.setString(1, Integer.toString(categoryIdEntered));
                                ResultSet catName = getCategoryName.executeQuery();
                                String selectedCategoryName = "";
                                while (catName.next()) {
                                    selectedCategoryName = catName.getString("CategoryName");
                                }

                                //If category is valid, select subcategory
                                System.out.print("Select from fallowing subcategories: ");

                                PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
                                getSubcategories.setString(1, Integer.toString(categoryIdEntered));
                                ResultSet subcategories = getSubcategories.executeQuery();

                                //Get subcategories and store existing IDs
                                int[] subCategoryIds = new int[100];
                                int index1 = 0;
                                while (subcategories.next()) {
                                    int subId = subcategories.getInt("ID");
                                    String subCategoryName = subcategories.getString("SubCategoryName");
                                    subCategoryIds[index1] = subId;
                                    index1++;
                                    System.out.print(subId + " - " + subCategoryName + " ");
                                }
                                System.out.println();
                                System.out.print("Enter Subcategory ID: ");
                                int subCategoryIdEntered = scanner.nextInt();

                                //Get entered subcategory name
                                for (int j = 0; j < subCategoryIds.length; j++) {
                                    if (subCategoryIds[j] == subCategoryIdEntered) {
                                        PreparedStatement getSubCategoryName = conn.prepareStatement("SELECT SubCategoryName FROM SubCategories WHERE ID = ?");
                                        getSubCategoryName.setString(1, Integer.toString(subCategoryIdEntered));
                                        ResultSet subCatName = getSubCategoryName.executeQuery();
                                        String selectedSubCategoryName = "";
                                        while (subCatName.next()) {
                                            selectedSubCategoryName = subCatName.getString("SubCategoryName");
                                        }

                                        //If subcategory is valid, enter amount spent
                                        System.out.print("Enter amount spent: ");
                                        double amountEntered = scanner.nextDouble();

                                        if (amountEntered < 0) {
                                            System.out.println("Amount should be greater than 0!");
                                            return;
                                        } else {
                                            //If amount entered is valid, ask user for approval to save data
                                            System.out.println("To approve input (year: " + newExpenseYear + ", month: " + newExpenseMonth + ", category: " + selectedCategoryName + ", subcategory: " + selectedSubCategoryName + ", amount: " + amountEntered + ") enter approve!");
                                            System.out.print("Enter 'approve': ");
                                            String userApproval = scanner.next();

                                            //If user approves, save the data entered
                                            if (userApproval.equalsIgnoreCase("approve")) {
                                                PreparedStatement insertExpense = conn.prepareStatement(
                                                        "insert into Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) values (?, ?, ?, ?, ?)");

                                                insertExpense.setString(1, Integer.toString(newExpenseYear));
                                                insertExpense.setString(2, Integer.toString(newExpenseMonth));
                                                insertExpense.setString(3, Integer.toString(categoryIdEntered));
                                                insertExpense.setString(4, Integer.toString(subCategoryIdEntered));
                                                insertExpense.setString(5, Double.toString(amountEntered));

                                                System.out.println(insertExpense.executeUpdate() + " expense row successfully inserted!");

                                                return;

                                            } else {
                                                //Cancel, if user doesn't approve save action
                                                System.out.println("Input is not saved.");
                                                return;
                                            }
                                        }
                                        //Validation message for non-existing subcategory ID
                                    } else if (j == subCategoryIds.length - 1) {
                                        System.out.println("Subcategory with ID " + subCategoryIdEntered + " does not exist");
                                        return;
                                    }
                                }
                                //Validation message for non-existing category ID
                            } else if (i == categoryIds.length - 1) {
                                System.out.println("Category with ID " + categoryIdEntered + " does not exist");
                                return;
                            }
                        }
                    }
                }
            } else if (actionID == 2) {
                //TODO add deletion of expense

            } else {
                //TODO add reports

            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
        }
    }
}