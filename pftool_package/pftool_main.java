package pftool_package;

import java.sql.*;
import java.util.*;

public class pftool_main {

    public static void main(String[] args) throws Exception {
        String connectionString = "jdbc:mysql://localhost/pftool?user=test&password=test";
        Connection conn = DriverManager.getConnection(connectionString);

        Statement statement = conn.createStatement();
        ResultSet resultSet;


        try (Scanner scanner = new Scanner(System.in)) {
            //Select action
            System.out.print("Select action (1 - Add new expense; 2 - Delete expense; 3 - Get report; 4 - Edit budget): ");
            int actionID = scanner.nextInt();

            if (actionID < 1 || actionID > 4) {
                System.out.println("Invalid input!");
                return;
            }
            //Action 1 - add new expense
            if (actionID == 1) {
                //Enter expense year
                System.out.print("Enter year (year should be current or previous year): ");
                int newExpenseYear = scanner.nextInt();
                if (newExpenseYear < Calendar.getInstance().get(Calendar.YEAR) - 1 || newExpenseYear > Calendar.getInstance().get(Calendar.YEAR)) {
                    System.out.println("Invalid input!");
                    return;
                }
                //If year is valid, enter expense month
                System.out.print("Enter month (1-12): ");
                int newExpenseMonth = scanner.nextInt();
                if (newExpenseMonth < 1 || newExpenseMonth > 12) {
                    System.out.println("Invalid input!");
                    return;
                }
                //If month is valid, select category
                System.out.print("Select from following categories: ");

                //Query categories from the DB
                resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                        " FROM categories" +
                        " ORDER BY ID ASC");

                //Get categories and store existing IDs for further validation
                ArrayList<Integer> categoryIdList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String categoryName = resultSet.getString("CategoryName");
                    categoryIdList.add(id);
                    System.out.print(id + " - " + categoryName + " ");
                }
                //Enter category ID
                System.out.println();
                System.out.print("Enter category ID: ");
                int categoryIdEntered = scanner.nextInt();

                //Validate does entered ID exist in Categories list
                if (!categoryIdList.contains(categoryIdEntered)) {
                    System.out.println("Category with ID " + categoryIdEntered + " does not exist");
                    return;
                }
                //Get entered category name
                String selectedCategoryName = queries.getCategoryName(conn, categoryIdEntered);

                //Select subcategory
                System.out.print("Select from following subcategories: ");

                PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
                getSubcategories.setInt(1, categoryIdEntered);
                ResultSet subcategories = getSubcategories.executeQuery();

                //Get subcategories and store existing IDs for further validation
                ArrayList<Integer> subCategoryIdList = new ArrayList<>();
                while (subcategories.next()) {
                    int subId = subcategories.getInt("ID");
                    String subCategoryName = subcategories.getString("SubCategoryName");
                    subCategoryIdList.add(subId);
                    System.out.print(subId + " - " + subCategoryName + " ");
                }
                System.out.println();
                System.out.print("Enter Subcategory ID: ");
                int subCategoryIdEntered = scanner.nextInt();

                //Validate does entered ID exist in Subcategories list
                if (!subCategoryIdList.contains(subCategoryIdEntered)) {
                    System.out.println("Subcategory with ID " + subCategoryIdEntered + " does not exist under the selected category");
                    return;
                }
                //Get entered subcategory name
                String selectedSubCategoryName = queries.getSubCategoryName(conn, subCategoryIdEntered);

                //Enter amount spent
                System.out.print("Enter amount spent: ");
                double amountEntered = scanner.nextDouble();

                if (amountEntered < 0) {
                    System.out.println("Amount should be greater or equal to 0!");
                    return;
                }
                //If amount entered is valid, ask user for approval to save data
                System.out.println("To approve input (year: " + newExpenseYear + ", month: " + newExpenseMonth + ", category: " + selectedCategoryName + ", subcategory: " + selectedSubCategoryName + ", amount: " + amountEntered + ") enter approve!");
                System.out.print("Enter 'approve': ");
                String userApproval = scanner.next();

                //Cancel, if user doesn't approve save action
                if (!userApproval.equalsIgnoreCase("approve")) {
                    System.out.println("Input is not saved.");
                    return;
                }
                //If user approves, save the data entered
                PreparedStatement insertExpense = conn.prepareStatement(
                        "insert into Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) values (?, ?, ?, ?, ?)");

                insertExpense.setInt(1, newExpenseYear);
                insertExpense.setInt(2, newExpenseMonth);
                insertExpense.setInt(3, categoryIdEntered);
                insertExpense.setInt(4, subCategoryIdEntered);
                insertExpense.setDouble(5, amountEntered);

                System.out.println(insertExpense.executeUpdate() + " expense row successfully inserted!");

                //Get total expenses per subcategory
                double subcategoryTotalPerMonth = queries.getSubCategoryTotal(conn, newExpenseYear, newExpenseMonth, subCategoryIdEntered);

                //Select total expenses per category
                double categoryTotalPerMonth = queries.getCategoryTotal(conn, newExpenseYear, newExpenseMonth, categoryIdEntered);

                //Get category budget
                double selectedCategoryBudget = queries.getCategoryBudget(conn, categoryIdEntered);

                //Print short summary per subcategory and category
                System.out.println("Total expenses per subcategory " + selectedSubCategoryName + " in period " + newExpenseYear + "/" + newExpenseMonth + " is " + subcategoryTotalPerMonth);
                System.out.println("Total expenses per category " + selectedCategoryName + " in period " + newExpenseYear + "/" + newExpenseMonth + " is " + categoryTotalPerMonth + " (" + Math.round(categoryTotalPerMonth / selectedCategoryBudget * 100) + "% from category budget spent)");


                //Action 2- delete expense from DB
            } else if (actionID == 2) {
                //Enter expense year
                System.out.print("Enter year: ");
                int newExpenseYear = scanner.nextInt();

                if (newExpenseYear < queries.getMinExpenseYear(statement) || newExpenseYear > queries.getMaxExpenseYear(statement)) {
                    System.out.println("Expenses in the selected year don't exist!");
                    return;
                }
                //Enter expense month
                System.out.print("Enter month (1-12): ");
                int newExpenseMonth = scanner.nextInt();
                if (newExpenseMonth < 1 || newExpenseMonth > 12) {
                    System.out.println("Invalid input!");
                    return;
                }
                //If month is valid, select category
                System.out.print("Select from following categories: ");

                //Query categories from the DB
                resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                        " FROM categories" +
                        " ORDER BY ID ASC");

                //Get categories and store existing IDs
                ArrayList<Integer> categoryIdList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String categoryName = resultSet.getString("CategoryName");
                    categoryIdList.add(id);
                    System.out.print(id + " - " + categoryName + " ");
                }
                System.out.println();
                System.out.print("Enter category ID: ");
                int categoryIdEntered = scanner.nextInt();

                //Validate does entered ID exist in Categories list
                if (!categoryIdList.contains(categoryIdEntered)) {
                    System.out.println("Category with ID " + categoryIdEntered + " does not exist");
                    return;
                }
                //Select subcategory
                System.out.print("Select from following subcategories: ");

                PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
                getSubcategories.setInt(1, categoryIdEntered);
                ResultSet subcategories = getSubcategories.executeQuery();

                //Get subcategories and store existing IDs for further validation
                ArrayList<Integer> subCategoryIdList = new ArrayList<>();
                while (subcategories.next()) {
                    int subId = subcategories.getInt("ID");
                    String subCategoryName = subcategories.getString("SubCategoryName");
                    subCategoryIdList.add(subId);
                    System.out.print(subId + " - " + subCategoryName + " ");
                }
                System.out.println();
                System.out.print("Enter Subcategory ID: ");
                int subCategoryIdEntered = scanner.nextInt();

                //Validate does entered ID exist in Subcategories list
                if (!subCategoryIdList.contains(subCategoryIdEntered)) {
                    System.out.println("Subcategory with ID " + subCategoryIdEntered + " does not exist under the selected category");
                    return;
                }
                //If subcategory is valid, select expense to delete
                PreparedStatement getExpenseList = conn.prepareStatement("SELECT Expenses.ID AS expense_id, Expenses.`Year`, Expenses.`Month`, Expenses.AmountSpent, Subcategories.SubCategoryName from Expenses" +
                        " JOIN Subcategories ON Subcategories.ID = Expenses.SubCategoryID" +
                        " where Expenses.`Year` = ?" +
                        " and Expenses.`Month` = ?" +
                        " and Expenses.SubCategoryID = ?");

                getExpenseList.setInt(1, newExpenseYear);
                getExpenseList.setInt(2, newExpenseMonth);
                getExpenseList.setInt(3, subCategoryIdEntered);
                ResultSet expenseList = getExpenseList.executeQuery();

                if (!expenseList.isBeforeFirst()) {
                    System.out.println("No expenses found per selected subcategory in the selected period!");
                    return;
                }
                System.out.println("Select from following expenses: ");
                System.out.printf("%11s  %-6s   %-6s   %-30s   %-20s%n", "Expense ID", "Year", "Month", "Subcategory Name", "Amount");

                //Print list of expenses and store expense IDs for further validation
                String subcategoryNameSelected;
                ArrayList<Integer> expenseIdList = new ArrayList<>();
                while (expenseList.next()) {
                    int expenseID = expenseList.getInt("expense_id");
                    String expenseYear = expenseList.getString("Year");
                    String expenseMonth = expenseList.getString("Month");
                    String subcategoryName = expenseList.getString("SubCategoryName");
                    String expenseAmount = expenseList.getString("AmountSpent");
                    subcategoryNameSelected = subcategoryName;
                    expenseIdList.add(expenseID);

                    System.out.printf("%11s  %-6s   %-6s   %-30s   %-20s%n", expenseID, expenseYear, expenseMonth, subcategoryNameSelected, expenseAmount);
                }
                System.out.print("Enter Expense ID for expense You want to delete: ");
                int expenseIdToDelete = scanner.nextInt();

                //Validation for ID entered
                if (!expenseIdList.contains(expenseIdToDelete)) {
                    System.out.println("ID is not valid! Please select ID from the list above.");
                    return;
                }

                PreparedStatement getExpenseInfo = conn.prepareStatement("SELECT Expenses.ID AS expense_id, Expenses.`Year`, Expenses.`Month`, Expenses.AmountSpent, Subcategories.SubCategoryName from Expenses" +
                        " JOIN Subcategories ON Subcategories.ID = Expenses.SubCategoryID" +
                        " WHERE Expenses.ID = ?");
                getExpenseInfo.setInt(1, expenseIdToDelete);
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

                //Ask user to approve deletion
                System.out.println("To approve deletion of expense (year: " + expenseToDeleteYear + ", month: " + expenseToDeleteMonth + ", subcategory: " + expenseToDeleteSubcategory + ", amount: " + expenseToDeleteAmount + ") enter 'approve'!");
                System.out.print("Enter 'approve': ");
                String userApproval = scanner.next();

                if (!userApproval.equalsIgnoreCase("approve")) {
                    System.out.println("Selected expense is not deleted.");
                    return;
                }
                //If user approves, save the data entered
                PreparedStatement deleteExpense = conn.prepareStatement(
                        "DELETE FROM Expenses where ID = ?");

                deleteExpense.setInt(1, expenseIdToDelete);

                System.out.println(deleteExpense.executeUpdate() + " expense row successfully deleted!");

            } else if (actionID == 3) {
                //Get reports
                System.out.print("Choose a report (1 - Detailed list of expenses; 2 - Summary per category; 3 - Summary per subcategory; 4 - Summary per period): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    //Report - Detailed list of expenses
                    case 1:

                        //Enter expense year
                        System.out.print("Enter year (0 for all): ");
                        int y1 = scanner.nextInt();
                        if (y1 < 0 || y1 > queries.getMaxExpenseYear(statement)) {
                            System.out.println("Invalid input!");
                            return;
                        }
                        //If year is valid, enter expense month
                        System.out.print("Enter month (1-12; 0 for all): ");
                        int m1 = scanner.nextInt();
                        if (m1 < 0 || m1 > 12) {
                            System.out.println("Invalid input!");
                            return;
                        }
                        //If month is valid, select category
                        System.out.print("Select from following categories: ");

                        //Query categories from the DB
                        resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                                " FROM categories" +
                                " ORDER BY ID ASC");

                        //Get categories and store existing IDs for further validation
                        ArrayList<Integer> categoryIdList = new ArrayList<>();
                        categoryIdList.add(0);
                        while (resultSet.next()) {
                            int id = resultSet.getInt("ID");
                            String categoryName = resultSet.getString("CategoryName");
                            categoryIdList.add(id);
                            System.out.print(id + " - " + categoryName + " ");
                        }
                        System.out.println();
                        System.out.print("Enter category ID (0 for all): ");
                        int c1 = scanner.nextInt();

                        //Validate does entered ID exist in Categories list
                        if (!categoryIdList.contains(c1)) {
                            System.out.println("Category with ID " + c1 + " does not exist");
                            return;
                        }
                        //If category is valid, select subcategory
                        System.out.print("Select from following subcategories: ");

                        PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
                        getSubcategories.setInt(1, c1);
                        ResultSet subcategories = getSubcategories.executeQuery();

                        //Get subcategories and store existing IDs for further validation
                        ArrayList<Integer> subCategoryIdList = new ArrayList<>();
                        subCategoryIdList.add(0);
                        while (subcategories.next()) {
                            int subId = subcategories.getInt("ID");
                            String subCategoryName = subcategories.getString("SubCategoryName");
                            subCategoryIdList.add(subId);
                            System.out.print(subId + " - " + subCategoryName + " ");
                        }
                        System.out.println();
                        System.out.print("Enter Subcategory ID (0 for all): ");
                        int sb1 = scanner.nextInt();

                        //Validate does entered ID exist in Subcategories list
                        if (!subCategoryIdList.contains(sb1)) {
                            System.out.println("Subcategory with ID " + sb1 + " does not exist under the selected category");
                            return;
                        }
                        // Detailed report generation
                        resultSet = statement.executeQuery("SELECT EXPENSES.ID, Year, Month, EXPENSES.CategoryID, EXPENSES.SubCategoryID, AmountSpent, CATEGORIES.CategoryName, SUBCATEGORIES.SubCategoryName" +
                                " FROM expenses" +
                                " JOIN categories on categories.ID = expenses.CategoryID" +
                                " JOIN subcategories on subcategories.ID = expenses.CategoryID" +
                                (y1 == 0 && m1 == 0 && c1 == 0 && sb1 == 0 ? "" : (" WHERE ")) +
                                (y1 == 0 ? "" : (" Year='" + y1 + "'")) +
                                (y1 != 0 && (m1 != 0 || c1 != 0 || sb1 != 0) ? " and" : ("")) +
                                (m1 == 0 ? "" : (" Month='" + m1 + "'")) +
                                (m1 != 0 && (c1 != 0 || sb1 != 0) ? " and" : ("")) +
                                (c1 == 0 ? "" : (" EXPENSES.CategoryID = '" + c1 + "'")) +
                                (c1 != 0 && sb1 != 0 ? " and" : ("")) +
                                (sb1 == 0 ? "" : (" EXPENSES.SubCategoryID = '" + sb1 + "'")) +
                                " ORDER BY ID");

                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("No expenses found by selected criteria!");
                            return;
                        }
                        System.out.printf("%6s  %-6s   %-6s   %-12s   %-15s   %-12s   %-21s   %-21s%n", "ID", "Year", "Month", "CategoryID", "Category name", "SubCategoryID", "Subcategory name", "AmountSpent");

                        while (resultSet.next()) {
                            int id = resultSet.getInt("EXPENSES.ID");
                            String Year = resultSet.getString("Year");
                            String Month = resultSet.getString("Month");
                            String CategoryID = resultSet.getString("EXPENSES.CategoryID");
                            String SubCategoryID = resultSet.getString("EXPENSES.SubCategoryID");
                            Double AmountSpent = resultSet.getDouble("AmountSpent");
                            String CategoryName = resultSet.getString("CATEGORIES.CategoryName");
                            String SubCategoryName = resultSet.getString("SUBCATEGORIES.SubCategoryName");

                            System.out.printf("%6s  %-6s   %-6s   %-12s   %-15s   %-12s   %-21s   %-21s%n", id, Year, Month, CategoryID, CategoryName, SubCategoryID, SubCategoryName, AmountSpent);
                        }
                        break;

                    //Report - Summary per category
                    case 2:
                        //Enter expense year
                        System.out.print("Enter year: ");
                        int y2 = scanner.nextInt();

                        //Validate year
                        if (y2 < queries.getMinExpenseYear(statement) || y2 > queries.getMaxExpenseYear(statement)) {
                            System.out.println("Expenses in the selected year don't exist!");
                            return;
                        }
                        //If year is valid, enter expense month
                        System.out.print("Enter month (1-12): ");
                        int m2 = scanner.nextInt();
                        if (m2 < 1 || m2 > 12) {
                            System.out.println("Invalid input!");
                            return;
                        }
                        //If month is valid, select category
                        System.out.print("Select from following categories: ");

                        //Query categories from the DB
                        resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                                " FROM categories" +
                                " ORDER BY ID ASC");

                        //Get categories and store existing IDs for further validation
                        ArrayList<Integer> categoryIdList2 = new ArrayList<>();
                        while (resultSet.next()) {
                            int id = resultSet.getInt("ID");
                            String categoryName = resultSet.getString("CategoryName");
                            categoryIdList2.add(id);
                            System.out.print(id + " - " + categoryName + " ");
                        }
                        System.out.println();
                        System.out.print("Enter category ID: ");
                        int c2 = scanner.nextInt();

                        //Validate does entered ID exist in Categories list
                        if (!categoryIdList2.contains(c2)) {
                            System.out.println("Category with ID " + c2 + " does not exist");
                            return;
                        }
                        //Get entered category name
                        String selectedCategoryName = queries.getCategoryName(conn, c2);

                        //Select total expenses per category
                        double categoryTotalPerMonth = queries.getCategoryTotal(conn, y2, m2, c2);

                        //Select category budget
                        double selectedCategoryBudget = queries.getCategoryBudget(conn, c2);

                        //Summary report per selected category
                        double percentSpent = Math.round(categoryTotalPerMonth / selectedCategoryBudget * 100);

                        System.out.printf("%-6s   %-6s   %-12s   %-18s   %-15s   %-15s   %-20s%n", "Year", "Month", "CategoryID", "Category Name", "AmountSpent", "Category budget", "% from budget spent");
                        System.out.printf("%-6s   %-6s   %-12s   %-18s   %-15s   %-15s   %-20s%n", y2, m2, c2, selectedCategoryName, categoryTotalPerMonth, selectedCategoryBudget, percentSpent);

                        break;

                    //Report - Summary per subcategory
                    case 3:
                        //Enter expense year
                        System.out.print("Enter year: ");
                        int y3 = scanner.nextInt();

                        //Validate year
                        if (y3 < queries.getMinExpenseYear(statement) || y3 > queries.getMaxExpenseYear(statement)) {
                            System.out.println("Expenses in the selected year don't exist!");
                            return;
                        }
                        //If year is valid, enter expense month
                        System.out.print("Enter month (1-12): ");
                        int m3 = scanner.nextInt();
                        if (m3 < 1 || m3 > 12) {
                            System.out.println("Invalid input!");
                            return;
                        }
                        //If month is valid, select category
                        System.out.print("Select from following categories: ");

                        //Query categories from the DB
                        resultSet = statement.executeQuery("SELECT ID, CategoryName" +
                                " FROM categories" +
                                " ORDER BY ID ASC");

                        //Get categories and store existing IDs for further validation
                        ArrayList<Integer> categoryIdList3 = new ArrayList<>();
                        while (resultSet.next()) {
                            int id = resultSet.getInt("ID");
                            String categoryName = resultSet.getString("CategoryName");
                            categoryIdList3.add(id);
                            System.out.print(id + " - " + categoryName + " ");
                        }
                        System.out.println();
                        System.out.print("Enter category ID: ");
                        int c3 = scanner.nextInt();

                        //Validate does entered ID exist in Categories list
                        if (!categoryIdList3.contains(c3)) {
                            System.out.println("Category with ID " + c3 + " does not exist");
                            return;
                        }
                        //Get entered category name
                        String selectedCategoryName3 = queries.getCategoryName(conn, c3);

                        //If category is valid, select subcategory
                        System.out.print("Select from following subcategories: ");

                        PreparedStatement getSubcategories3 = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
                        getSubcategories3.setInt(1, c3);
                        ResultSet subcategories3 = getSubcategories3.executeQuery();

                        //Get subcategories and store existing IDs for further validation
                        ArrayList<Integer> subCategoryIdList3 = new ArrayList<>();
                        while (subcategories3.next()) {
                            int subId = subcategories3.getInt("ID");
                            String subCategoryName = subcategories3.getString("SubCategoryName");
                            subCategoryIdList3.add(subId);
                            System.out.print(subId + " - " + subCategoryName + " ");
                        }

                        System.out.println();
                        System.out.print("Enter Subcategory ID: ");
                        int sb3 = scanner.nextInt();

                        //Validate does entered ID exist in Subcategories list
                        if (!subCategoryIdList3.contains(sb3)) {
                            System.out.println("Subcategory with ID " + sb3 + " does not exist under the selected category");
                            return;
                        }
                        //Get entered subcategory name
                        String selectedSubCategoryName = queries.getSubCategoryName(conn, sb3);

                        //Select total expenses per subcategory
                        double subcategoryTotalPerMonth = queries.getSubCategoryTotal(conn, y3, m3, sb3);

                        //Summary report per subcategory
                        System.out.printf("%-6s   %-6s   %-10s   %-12s   %-15s   %-21s   %-15s%n", "Year", "Month", "Category ID", "Category Name", "Subcategory ID", "Subcategory Name", "AmountSpent");
                        System.out.printf("%-6s   %-6s   %-10s   %-12s   %-15s   %-21s   %-15s%n", y3, m3, c3, selectedCategoryName3, sb3, selectedSubCategoryName, subcategoryTotalPerMonth);

                        break;

                    //Report - Summary per period
                    case 4:
                        //Enter expense year
                        System.out.print("Enter year: ");
                        int y4 = scanner.nextInt();

                        //Validate year
                        if (y4 < queries.getMinExpenseYear(statement) || y4 > queries.getMaxExpenseYear(statement)) {
                            System.out.println("Expenses in the selected year don't exist!");
                            return;
                        }
                        //If year is valid, enter expense month
                        System.out.print("Enter month (1-12): ");
                        int m4 = scanner.nextInt();
                        if (m4 < 1 || m4 > 12) {
                            System.out.println("Invalid input!");
                            return;
                        }
                        resultSet = statement.executeQuery("SELECT categories.ID, categories.CategoryName, SUM(expenses.AmountSpent), MIN(budget.limit) from Categories" +
                                " JOIN expenses on expenses.categoryID = categories.ID" +
                                " JOIN budget on budget.categoryID = categories.ID" +
                                " WHERE expenses.`year`='" + y4 + "' and expenses.`month`='" + m4 + "'" +
                                " GROUP BY categories.ID");

                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("No expenses found by selected criteria!");
                            return;
                        }

                        System.out.printf("%-6s   %-6s   %-12s   %-20s   %-12s   %-18s   %-21s%n", "Year", "Month", "Category ID", "Category name", "Amount Spent", "Category Budget", "% from budget spent");

                        while (resultSet.next()) {

                            int CatID = resultSet.getInt("categories.ID");
                            String CategoryName = resultSet.getString("Categories.CategoryName");
                            double AmountSpent = resultSet.getDouble("SUM(expenses.AmountSpent)");
                            double Budget = resultSet.getDouble("MIN(budget.limit)");

                            System.out.printf("%-6s   %-6s   %-12s   %-20s   %-12s   %-18s   %-21s%n", y4, m4, CatID, CategoryName, AmountSpent, Budget, Math.round(AmountSpent / Budget * 100));
                        }

                        resultSet = statement.executeQuery("SELECT SUM(`limit`) from Budget");
                        double Budget2 = 0;
                        while (resultSet.next()) {
                            Budget2 = resultSet.getDouble("SUM(`limit`)");
                        }

                        resultSet = statement.executeQuery("SELECT SUM(expenses.AmountSpent) from Expenses" +
                                " WHERE expenses.`year`='" + y4 + "' and expenses.`month`='" + m4 + "'" +
                                " GROUP BY expenses.`month`");

                        System.out.println();
                        System.out.printf("%-6s   %-6s   %-12s   %-18s   %-21s%n", "Year", "Month", "Total Amount Spent", "Total Budget", "% from budget spent");

                        while (resultSet.next()) {
                            double AmountSpent2 = resultSet.getDouble("SUM(expenses.AmountSpent)");

                            System.out.printf("%-6s   %-6s   %-18s   %-18s   %-21s%n", y4, m4, AmountSpent2, Budget2, Math.round(AmountSpent2 / Budget2 * 100));
                        }

                        break;

                    default:
                        System.out.println("Invalid input!");
                        break;
                }
            } else {

                System.out.println("Current budget limit per categories:");
                System.out.println();
                ResultSet getBudget = statement.executeQuery("SELECT Budget.Limit, Categories.CategoryName from Budget" +
                        " JOIN categories ON Categories.ID = Budget.CategoryID" +
                        " order by Categories.CategoryName");

                System.out.printf("%-20s  %-20s%n", "Category", "Monthly Limit");

                while (getBudget.next()) {
                    double limitPerCategory = getBudget.getDouble("Budget.Limit");
                    String categoryName = getBudget.getString("Categories.CategoryName");

                    System.out.printf("%-20s  %-20s%n", categoryName, limitPerCategory);
                }
                System.out.println();
                System.out.println("Enter category name You want to edit: ");
                String categoryToEdit = scanner.next();

                //Get category ID by name
                PreparedStatement getCategoryID = conn.prepareStatement("SELECT ID FROM Categories" +
                        " WHERE CategoryName = ?");
                getCategoryID.setString(1, categoryToEdit);
                ResultSet categoryIdResults = getCategoryID.executeQuery();

                if (!categoryIdResults.next()) {
                    System.out.println("Category not found!");
                    return;
                }
                int categoryIdToEdit = categoryIdResults.getInt("ID");

                System.out.println("Enter the new limit: ");
                double newLimit = scanner.nextDouble();

                if (newLimit < 0) {
                    System.out.println("The new limit should be greater than 0!");
                    return;
                }

                System.out.println("To approve the new limit " + newLimit + " for category " + categoryToEdit + " enter 'approve'");
                String userApproval = scanner.next();

                //If user approves, save the data entered
                if (!userApproval.equalsIgnoreCase("approve")) {
                    System.out.println("Budget changes are not saved!");
                    return;
                }
                PreparedStatement updateBudget = conn.prepareStatement(
                        "UPDATE BUDGET" +
                                " set `Limit` = ?" +
                                " WHERE CategoryID = ?");

                updateBudget.setDouble(1, newLimit);
                updateBudget.setInt(2, categoryIdToEdit);

                System.out.println(updateBudget.executeUpdate() + " budget row successfully updated!");
                System.out.println();

                System.out.println("Updated budget limit per categories:");
                System.out.println();
                ResultSet getUpdatedBudget = statement.executeQuery("SELECT Budget.Limit, Categories.CategoryName from Budget" +
                        " JOIN categories ON Categories.ID = Budget.CategoryID" +
                        " order by Categories.CategoryName");

                System.out.printf("%-20s  %-20s%n", "Category", "Monthly Limit");

                while (getUpdatedBudget.next()) {
                    double updatedLimitPerCategory = getUpdatedBudget.getDouble("Budget.Limit");
                    String updatedCategoryName = getUpdatedBudget.getString("Categories.CategoryName");

                    System.out.printf("%-20s  %-20s%n", updatedCategoryName, updatedLimitPerCategory);
                }
            }
        } catch (
                InputMismatchException e) {
            System.out.println("Invalid input!");
        }
    }
}