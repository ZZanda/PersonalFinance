package mypackage;
import java.sql.*;
import java.util.*;

public class PersonalFinanceReports{

	public static void main(String[] args) throws Exception {
		String connectionString = "jdbc:mysql://localhost/pftool?user=newuser&password=password";
		Connection conn = DriverManager.getConnection(connectionString);

		Statement statement = conn.createStatement();
		ResultSet resultSet;


		try (Scanner scanner = new Scanner(System.in)) {

			//Get reports
			System.out.print("Choose a report (1 - Detailed list of expenses; 2 - Summary per category; 3 - Summary per subcategory; 4 - Summary per period): ");
			int choice = scanner.nextInt();

			switch (choice) {
			//Report - Detailed list of expenses
			case 1:

				//Enter expense year
				System.out.print("Enter year (0 for all): ");
				int y1 = scanner.nextInt();
				if (y1 < 0) {
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
				System.out.print("Enter category ID (0 for all): ");
				int c1 = scanner.nextInt();

				for (int i = 0; i < categoryIds.length; i++) {
					if (categoryIds[i] == c1) {

						//If category is valid, select subcategory
						System.out.print("Select from following subcategories: ");

						PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
						getSubcategories.setString(1, Integer.toString(c1));
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
						System.out.print("Enter Subcategory ID (0 for all): ");
						int sb1 = scanner.nextInt();

						for (int j = 0; j < subCategoryIds.length; j++) {
							if (subCategoryIds[j] == sb1) {

								// Detailed report generation
								resultSet = statement.executeQuery("SELECT EXPENSES.ID, Year, Month, EXPENSES.CategoryID, EXPENSES.SubCategoryID, AmountSpent, CATEGORIES.CategoryName, SUBCATEGORIES.SubCategoryName" +
										" FROM expenses"+
										" JOIN categories on categories.ID = expenses.CategoryID"+ 
										" JOIN subcategories on subcategories.ID = expenses.CategoryID"+ 
										(c1==0&&sb1==0&&y1==0&&m1==0?"":(" WHERE "))+
										(c1==0?"":(" EXPENSES.CategoryID = '" + c1 + "'"))+
										(c1==0||sb1==0?"":(" and"))+
										(sb1==0?"":(" EXPENSES.SubCategoryID = '" + sb1 + "'"))+
										(sb1==0||y1==0?"":(" and"))+
										(y1==0?"":(" Year='" + y1 + "'"))+
										(y1==0||m1==0?"":(" and"))+
										(m1==0?"":(" Month='" + m1 + "'"))+ 
										" ORDER BY ID"); 

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

								return;

								//Validation message for non-existing subcategory ID
							} else if (j == subCategoryIds.length - 1) {
								System.out.println("Subcategory with ID " + sb1 + " does not exist");
								return;
							}
						}
						//Validation message for non-existing category ID
					} else if (i == categoryIds.length - 1) {
						System.out.println("Category with ID " + c1 + " does not exist");
						return;
					}
				}
				break;			

				//Report - Summary per category
			case 2:
				//Enter expense year
				System.out.print("Enter year (year should be current or previous year): ");
				int y2 = scanner.nextInt();
				if (y2 < Calendar.getInstance().get(Calendar.YEAR) - 1 || y2 > Calendar.getInstance().get(Calendar.YEAR)) {
					System.out.println("Invalid input!");
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

				//Get categories and store existing IDs
				int[] categoryIds1 = new int[20];
				int index1 = 0;
				while (resultSet.next()) {
					int id = resultSet.getInt("ID");
					String categoryName = resultSet.getString("CategoryName");
					categoryIds1[index1] = id;
					index1++;
					System.out.print(id + " - " + categoryName + " ");
				}
				System.out.println();
				System.out.print("Enter category ID: ");
				int c2 = scanner.nextInt();

				for (int i = 0; i < categoryIds1.length; i++) {
					if (categoryIds1[i] == c2) {

						//Get entered category name
						PreparedStatement getCategoryName = conn.prepareStatement("SELECT CategoryName FROM Categories WHERE ID = ?");
						getCategoryName.setString(1, Integer.toString(c2));
						ResultSet catName = getCategoryName.executeQuery();
						String selectedCategoryName = "";
						while (catName.next()) {
							selectedCategoryName = catName.getString("CategoryName");
						}

						//Select total expenses per category
						PreparedStatement getCategoryTotal = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses WHERE Year = ? AND Month = ? AND CategoryID = ?");
						getCategoryTotal.setString(1, Integer.toString(y2));
						getCategoryTotal.setString(2, Integer.toString(m2));
						getCategoryTotal.setString(3, Integer.toString(c2));
						ResultSet catTotal = getCategoryTotal.executeQuery();
						double categoryTotalPerMonth = 0;
						while (catTotal.next()) {
							categoryTotalPerMonth = catTotal.getDouble("SUM(AmountSpent)");
						}

						//Select category budget
						PreparedStatement getCategoryBudget = conn.prepareStatement("SELECT `Limit` FROM Budget WHERE CategoryID = ?");
						getCategoryBudget.setString(1, Integer.toString(c2));
						ResultSet categoryBudget = getCategoryBudget.executeQuery();
						double selectedCategoryBudget = 0;
						while (categoryBudget.next()) {
							selectedCategoryBudget = categoryBudget.getDouble("Limit");
						}

						//Summary report per selected category
						double percentspent = Math.round(categoryTotalPerMonth / selectedCategoryBudget * 100);

						System.out.printf("%-6s   %-6s   %-12s   %-18s   %-15s   %-15s   %-20s%n", "Year", "Month", "CategoryID", "Category Name", "AmountSpent", "Category budget", "% from budget spent");
						System.out.printf("%-6s   %-6s   %-12s   %-18s   %-15s   %-15s   %-20s%n", y2, m2, c2, selectedCategoryName, categoryTotalPerMonth, selectedCategoryBudget, percentspent);	

						return;

						//Validation message for non-existing category ID
					} else if (i == categoryIds1.length - 1) {
						System.out.println("Category with ID " + c2 + " does not exist");
						return;
					}
				}

				break;

				//Report - Summary per subcategory
			case 3:
				//Enter expense year
				System.out.print("Enter year (year should be current or previous year): ");
				int y3 = scanner.nextInt();
				if (y3 < Calendar.getInstance().get(Calendar.YEAR) - 1 || y3 > Calendar.getInstance().get(Calendar.YEAR)) {
					System.out.println("Invalid input!");
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

				//Get categories and store existing IDs
				int[] categoryIds2 = new int[20];
				int index2 = 0;
				while (resultSet.next()) {
					int id = resultSet.getInt("ID");
					String categoryName = resultSet.getString("CategoryName");
					categoryIds2[index2] = id;
					index2++;
					System.out.print(id + " - " + categoryName + " ");
				}
				System.out.println();
				System.out.print("Enter category ID: ");
				int c3 = scanner.nextInt();

				for (int i = 0; i < categoryIds2.length; i++) {
					if (categoryIds2[i] == c3) {
						//Get entered category name
						PreparedStatement getCategoryName = conn.prepareStatement("SELECT CategoryName FROM Categories WHERE ID = ?");
						getCategoryName.setString(1, Integer.toString(c3));
						ResultSet catName = getCategoryName.executeQuery();
						String selectedCategoryName = "";
						while (catName.next()) {
							selectedCategoryName = catName.getString("CategoryName");
						}

						//If category is valid, select subcategory
						System.out.print("Select from following subcategories: ");

						PreparedStatement getSubcategories = conn.prepareStatement("SELECT ID, SubCategoryName FROM SubCategories WHERE CategoryID = ? ORDER BY ID");
						getSubcategories.setString(1, Integer.toString(c3));
						ResultSet subcategories = getSubcategories.executeQuery();

						//Get subcategories and store existing IDs
						int[] subCategoryIds = new int[100];
						int index3 = 0;
						while (subcategories.next()) {
							int subId = subcategories.getInt("ID");
							String subCategoryName = subcategories.getString("SubCategoryName");
							subCategoryIds[index3] = subId;
							index3++;
							System.out.print(subId + " - " + subCategoryName + " ");
						}
						System.out.println();
						System.out.print("Enter Subcategory ID: ");
						int sb1 = scanner.nextInt();

						//Get entered subcategory name
						for (int j = 0; j < subCategoryIds.length; j++) {
							if (subCategoryIds[j] == sb1) {
								PreparedStatement getSubCategoryName = conn.prepareStatement("SELECT SubCategoryName FROM SubCategories WHERE ID = ?");
								getSubCategoryName.setString(1, Integer.toString(sb1));
								ResultSet subCatName = getSubCategoryName.executeQuery();
								String selectedSubCategoryName = "";
								while (subCatName.next()) {
									selectedSubCategoryName = subCatName.getString("SubCategoryName");
								}

								//Select total expenses per subcategory
								PreparedStatement getSubcategoryTotal = conn.prepareStatement("SELECT SUM(AmountSpent) FROM Expenses WHERE Year = ? AND Month = ? AND SubCategoryID = ?");
								getSubcategoryTotal.setString(1, Integer.toString(y3));
								getSubcategoryTotal.setString(2, Integer.toString(m3));
								getSubcategoryTotal.setString(3, Integer.toString(sb1));
								ResultSet subCatTotal = getSubcategoryTotal.executeQuery();
								double subcategoryTotalPerMonth = 0;
								while (subCatTotal.next()) {
									subcategoryTotalPerMonth = subCatTotal.getDouble("SUM(AmountSpent)");
								}

								//Summary report per subcategory
								System.out.printf("%-6s   %-6s   %-10s   %-12s   %-15s   %-21s   %-15s%n", "Year", "Month", "Category ID", "Category Name", "Subcategory ID", "Subategory Name", "AmountSpent");
								System.out.printf("%-6s   %-6s   %-10s   %-12s   %-15s   %-21s   %-15s%n", y3, m3, c3, selectedCategoryName, sb1, selectedSubCategoryName, subcategoryTotalPerMonth);	
								return;

								//Validation message for non-existing subcategory ID
							} else if (j == subCategoryIds.length - 1) {
								System.out.println("Subcategory with ID " + sb1 + " does not exist");
								return;
							}
						}
						//Validation message for non-existing category ID
					} else if (i == categoryIds2.length - 1) {
						System.out.println("Category with ID " + c3 + " does not exist");
						return;
					}
				}
				break;	

				//Report - Summary per period
			case 4:
				//Enter expense year
				System.out.print("Enter year (year should be current or previous year): ");
				int y4 = scanner.nextInt();
				if (y4 < Calendar.getInstance().get(Calendar.YEAR) - 1 || y4 > Calendar.getInstance().get(Calendar.YEAR)) {
					System.out.println("Invalid input!");
					return;
				} 

				//If year is valid, enter expense month
				System.out.print("Enter month (1-12): ");
				int m4 = scanner.nextInt();
				if (m4 < 1 || m4 > 12) {
					System.out.println("Invalid input!");
					return;
				}

				resultSet = statement.executeQuery("SELECT categories.ID, categories.CategoryName, SUM(expenses.AmountSpent), MIN(budget.limit) from Categories"+
						" JOIN expenses on expenses.categoryID = categories.ID"+ 
						" JOIN budget on budget.categoryID = categories.ID"+ 
						" WHERE expenses.`year`='" + y4 + "' and expenses.`month`='" + m4 + "'" + 
						" GROUP BY categories.ID"); 

				System.out.printf("%-6s   %-6s   %-12s   %-20s   %-12s   %-18s   %-21s%n", "Year", "Month", "Category ID", "Category name", "Amount Spent", "Category Budget", "% from budget spent");

				while (resultSet.next()) {

					int CatID = resultSet.getInt("categories.ID");
					String CategoryName = resultSet.getString("Categories.CategoryName");
					double  AmountSpent = resultSet.getDouble("SUM(expenses.AmountSpent)");
					double  Budget = resultSet.getDouble("MIN(budget.limit)");

					System.out.printf("%-6s   %-6s   %-12s   %-20s   %-12s   %-18s   %-21s%n", y4, m4, CatID, CategoryName, AmountSpent, Budget, Math.round(AmountSpent / Budget * 100));
				}

				resultSet = statement.executeQuery("SELECT SUM(`limit`) from Budget");
				double  Budget2=0;
				while (resultSet.next()) {
					Budget2 = resultSet.getDouble("SUM(`limit`)");
				}

				resultSet = statement.executeQuery("SELECT SUM(expenses.AmountSpent) from Expenses"+
						" WHERE expenses.`year`='" + y4 + "' and expenses.`month`='" + m4 + "'" + 
						" GROUP BY expenses.`month`"); 

				System.out.println();
				System.out.printf("%-6s   %-6s   %-12s   %-18s   %-21s%n", "Year", "Month", "Total Amount Spent", "Total Budget", "% from budget spent");

				while (resultSet.next()) {
					double  AmountSpent2 = resultSet.getDouble("SUM(expenses.AmountSpent)");

					System.out.printf("%-6s   %-6s   %-18s   %-18s   %-21s%n", y4, m4, AmountSpent2, Budget2, Math.round(AmountSpent2 / Budget2 * 100));
				}

				break;
			default:
				System.out.println("Invalid input!");
				break;

			}
		}
	}
}