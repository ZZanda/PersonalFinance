package pftool_package;

import java.sql.*;


public class queries {

    //Method to get Category name
    public static String getCategoryName(Connection conn, int categoryIdEntered) throws Exception {
        PreparedStatement getCategoryNameStm = conn.prepareStatement("SELECT CategoryName FROM Categories WHERE ID = ?");
        getCategoryNameStm.setInt(1, categoryIdEntered);
        ResultSet catName = getCategoryNameStm.executeQuery();
        String selectedCategoryName = "";

        while (catName.next()) {
            selectedCategoryName = catName.getString("CategoryName");
        }

        return selectedCategoryName;
    }

    //Method to get subcategory name
    public static String getSubCategoryName(Connection conn, int subCategoryIdEntered) throws Exception {
        PreparedStatement getSubCategoryNameStm = conn.prepareStatement("SELECT SubCategoryName FROM SubCategories WHERE ID = ?");
        getSubCategoryNameStm.setInt(1, subCategoryIdEntered);
        ResultSet subCatName = getSubCategoryNameStm.executeQuery();

        String selectedSubCategoryName = "";
        while (subCatName.next()) {
            selectedSubCategoryName = subCatName.getString("SubCategoryName");
        }
        return selectedSubCategoryName;
    }

    //Method to get subcategory total expenses per month
    public static double getSubCategoryTotal (Connection conn, int year, int month, int subCategoryId) throws Exception {
        PreparedStatement getSubcategoryTotalStm = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses" +
                " WHERE Year = ?" +
                " AND Month = ?" +
                " AND SubCategoryID = ?");
        getSubcategoryTotalStm.setInt(1, year);
        getSubcategoryTotalStm.setInt(2, month);
        getSubcategoryTotalStm.setInt(3, subCategoryId);
        ResultSet subCatTotal = getSubcategoryTotalStm.executeQuery();

        double subcategoryTotalPerMonth = 0;
        while (subCatTotal.next()) {
            subcategoryTotalPerMonth = subCatTotal.getDouble("SUM(AmountSpent)");
        }
        return subcategoryTotalPerMonth;
    }

    //Method to get category total expenses per month
    public static double getCategoryTotal (Connection conn, int year, int month, int categoryId) throws Exception {
        PreparedStatement getCategoryTotalStm = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses" +
                " WHERE Year = ?" +
                " AND Month = ?" +
                " AND CategoryID = ?");
        getCategoryTotalStm.setInt(1, year);
        getCategoryTotalStm.setInt(2, month);
        getCategoryTotalStm.setInt(3, categoryId);
        ResultSet catTotal = getCategoryTotalStm.executeQuery();

        double categoryTotalPerMonth = 0;
        while (catTotal.next()) {
            categoryTotalPerMonth = catTotal.getDouble("SUM(AmountSpent)");
        }
        return categoryTotalPerMonth;
    }

    //Method to get category budget
    public static double getCategoryBudget (Connection conn, int categoryId) throws Exception {
        PreparedStatement getCategoryBudgetStm = conn.prepareStatement("SELECT `Limit` FROM Budget" +
                " WHERE CategoryID = ?");
        getCategoryBudgetStm.setInt(1, categoryId);
        ResultSet categoryBudget = getCategoryBudgetStm.executeQuery();

        double selectedCategoryBudget = 0;
        while (categoryBudget.next()) {
            selectedCategoryBudget = categoryBudget.getDouble("Limit");
        }
        return selectedCategoryBudget;
    }

    //Method to get min year from Expenses
    public static int getMinExpenseYear (Statement statement) throws Exception {
        ResultSet getMinYear = statement.executeQuery("SELECT MIN(`Year`) FROM Expenses");
        int minYear = 0;
        while (getMinYear.next()) {
            minYear = getMinYear.getInt("MIN(`Year`)");
        }
        return minYear;
    }

    public static int getMaxExpenseYear (Statement statement) throws Exception {
        ResultSet getMaxYear = statement.executeQuery("SELECT MAX(`Year`) FROM Expenses");
        int maxYear = 0;
        while (getMaxYear.next()) {
            maxYear = getMaxYear.getInt("MAX(`Year`)");
        }
        return maxYear;
    }
    }