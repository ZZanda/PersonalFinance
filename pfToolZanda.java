package pftool_package;

import java.sql.*;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class pftool_main {
    public static void main(String[] args) throws Exception {
        String connectionString = "jdbc:mysql://localhost/pftool?user=java&password=Testing12";
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

                //Add new expense
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


                        for (int i = 0; i < categoryIds.length; i++) {
                            if (categoryIds[i] == categoryIdEntered) {
                                //Get entered category name
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

                                                //Select total expenses per subcategory
                                                PreparedStatement getSubcategoryTotal = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses WHERE Year = ? AND Month = ? AND SubCategoryID = ?");
                                                getSubcategoryTotal.setString(1, Integer.toString(newExpenseYear));
                                                getSubcategoryTotal.setString(2, Integer.toString(newExpenseMonth));
                                                getSubcategoryTotal.setString(3, Integer.toString(subCategoryIdEntered));
                                                ResultSet subCatTotal = getSubcategoryTotal.executeQuery();
                                                double subcategoryTotalPerMonth = 0;
                                                while (subCatTotal.next()) {
                                                    subcategoryTotalPerMonth = subCatTotal.getDouble("SUM(AmountSpent)");
                                                }

                                                //Select total expenses per category
                                                PreparedStatement getCategoryTotal = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses WHERE Year = ? AND Month = ? AND CategoryID = ?");
                                                getCategoryTotal.setString(1, Integer.toString(newExpenseYear));
                                                getCategoryTotal.setString(2, Integer.toString(newExpenseMonth));
                                                getCategoryTotal.setString(3, Integer.toString(categoryIdEntered));
                                                ResultSet catTotal = getCategoryTotal.executeQuery();
                                                double categoryTotalPerMonth = 0;
                                                while (catTotal.next()) {
                                                    categoryTotalPerMonth = catTotal.getDouble("SUM(AmountSpent)");
                                                }

                                                //Select category budget
                                                PreparedStatement getCategoryBudget = conn.prepareStatement("SELECT `Limit` FROM Budget WHERE CategoryID = ?");
                                                getCategoryBudget.setString(1, Integer.toString(categoryIdEntered));
                                                ResultSet categoryBudget = getCategoryBudget.executeQuery();
                                                double selectedCategoryBudget = 0;
                                                while (categoryBudget.next()) {
                                                    selectedCategoryBudget = categoryBudget.getDouble("Limit");
                                                }

                                                System.out.println("Total expenses per subcategory " + selectedSubCategoryName + " in period " + newExpenseYear + "/" + newExpenseMonth + " is " + subcategoryTotalPerMonth);
                                                System.out.println("Total expenses per category " + selectedCategoryName + " in period " + newExpenseYear + "/" + newExpenseMonth + " is " + categoryTotalPerMonth + " (" + Math.round(categoryTotalPerMonth / selectedCategoryBudget * 100) + "% from category budget spent)");

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
                //Delete existing expense
            } else if (actionID == 2) {
                //Enter expense year
                System.out.print("Enter year: ");
                int newExpenseYear = scanner.nextInt();

                //Enter expense month
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


                    for (int i = 0; i < categoryIds.length; i++) {
                        if (categoryIds[i] == categoryIdEntered) {
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
                                    //If subcategory is valid, select expense to delete
                                    System.out.println("Select from following expenses: ");

                                    PreparedStatement getExpenseList = conn.prepareStatement("SELECT Expenses.ID AS expense_id, Expenses.`Year`, Expenses.`Month`, Expenses.AmountSpent, Subcategories.SubCategoryName from Expenses" +
                                            " JOIN Subcategories ON Subcategories.ID = Expenses.SubCategoryID" +
                                            " where Expenses.`Year` = ?" +
                                            " and Expenses.`Month` = ?" +
                                            " and Expenses.SubCategoryID = ?");

                                    getExpenseList.setString(1, Integer.toString(newExpenseYear));
                                    getExpenseList.setString(2, Integer.toString(newExpenseMonth));
                                    getExpenseList.setString(3, Integer.toString(subCategoryIdEntered));
                                    ResultSet expenseList = getExpenseList.executeQuery();

                                    System.out.printf("%11s  %-6s   %-6s   %-30s   %-20s%n", "Expense ID", "Year", "Month", "Subcategory Name", "Amount");

                                    String subcategoryNameSelected = "";
                                    while (expenseList.next()) {
                                        int expenseID = expenseList.getInt("expense_id");
                                        String expenseYear = expenseList.getString("Year");
                                        String expenseMonth = expenseList.getString("Month");
                                        String subcategoryName = expenseList.getString("SubCategoryName");
                                        String expenseAmount = expenseList.getString("AmountSpent");
                                        subcategoryNameSelected = subcategoryName;

                                        System.out.printf("%11s  %-6s   %-6s   %-30s   %-20s%n", expenseID, expenseYear, expenseMonth, subcategoryName, expenseAmount);
                                    }
                                    System.out.print("Enter Expense ID for expense You want to delete: ");
                                    int expenseIdToDelete = scanner.nextInt();

                                    PreparedStatement getExpenseInfo = conn.prepareStatement("SELECT Expenses.ID AS expense_id, Expenses.`Year`, Expenses.`Month`, Expenses.AmountSpent, Subcategories.SubCategoryName from Expenses" +
                                            " JOIN Subcategories ON Subcategories.ID = Expenses.SubCategoryID" +
                                            " WHERE Expenses.ID = ?");
                                    getExpenseInfo.setString(1, Integer.toString(expenseIdToDelete));
                                    ResultSet expenseInfoToDelete = getExpenseInfo.executeQuery();

                                    int expenseToDeleteYear = 0;
                                    int expenseToDeleteMonth = 0;
                                    String expenseToDeleteSubcategory = "";
                                    double expenseToDeleteAmount = 0;

                                    while (expenseInfoToDelete.next()) {
                                        expenseToDeleteYear = expenseInfoToDelete.getInt("Year");
                                        expenseToDeleteMonth = expenseInfoToDelete.getInt("Month");
                                        expenseToDeleteSubcategory = expenseInfoToDelete.getString("SubCategoryName");
                                        expenseToDeleteAmount = expenseInfoToDelete.getDouble("AmountSpent");
                                    }


                                    System.out.println("To approve deletion of expense (year: " + expenseToDeleteYear + ", month: " + expenseToDeleteMonth + ", subcategory: " + expenseToDeleteSubcategory + ", amount: " + expenseToDeleteAmount + ") enter approve!");
                                    System.out.print("Enter 'approve': ");
                                    String userApproval = scanner.next();

                                    //If user approves, save the data entered
                                    if (userApproval.equalsIgnoreCase("approve")) {
                                        PreparedStatement deleteExpense = conn.prepareStatement(
                                                "DELETE FROM Expenses where ID = ?");

                                        deleteExpense.setString(1, Integer.toString(expenseIdToDelete));

                                        System.out.println(deleteExpense.executeUpdate() + " expense row successfully deleted!");
                                        return;

                                    } else {
                                        //Cancel, if user doesn't approve save action
                                        System.out.println("Selected expense is not deleted.");
                                        return;
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
            } else {
                //TODO add reports

            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
        }
    }
}