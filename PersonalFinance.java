package mypackage;
import java.sql.*;
import java.util.*;

public class PersonalFinance {

	public static void main(String[] args) throws Exception {
		String connectionString = "jdbc:mysql://localhost/pftool?user=newuser&password=password";
		Connection conn = DriverManager.getConnection(connectionString);

		Statement statement = conn.createStatement();
		ResultSet resultSet;


		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Choose a report (1 => Detailed list of expenses, 2 => Summary per category, 3 => Summary per subcategory, 4 => Summary per period): ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Choose a category (1 => Shopping, 2 => Health, 3 => Food, 4 => Home, 5 => Transportation, 6 => Leisure, 7 => Education, 8 => Travelling, 9 => Insurance, 10 => Investments): ");
				int c1 = scanner.nextInt();

				if (c1<0||c1>10) {
					System.out.println("Incorrect input");
					return;
				}

				switch (c1) {
				case 1:
					System.out.print("Choose a subcategory (1 => Clothing and footwear, 2 => Electronics, 3 => Services, 4 => Beauty products, 5 => Other shopping): ");
					break;
				case 2:
					System.out.print("Choose a subcategory (6 => Pharmacy, 7 => Medical expenses, 8 => Other health): ");
					break;
				case 3:
					System.out.print("Choose a subcategory (9 => Groceries, 10 => Restaurants, 11 => Other food): ");
					break;
				case 4:
					System.out.print("Choose a subcategory (12 => Utilities, 13 => Rent, 14 => Home supplies, 15 => Home credit payments, 16 => Other home): ");
					break;
				case 5:
					System.out.print("Choose a subcategory (17 => Car related expenses, 18 => Leasing, 19 => Public transport, 20 => Other transportation): ");
					break;
				case 6:
					System.out.print("Choose a subcategory (21 => Sports, 22 => Culture events and concerts, 23 => Bars, parties, 24 => Other leisure): ");
					break;
				case 7:
					System.out.print("Choose a subcategory (25 => Education fees, 26 => Study credit payments, 27 => Other education): ");
					break;
				case 8:
					System.out.print("Choose a subcategory (28 => Hotels, 29 => Flights, 30 => Restaurants abroad, 31 => Other travelling): ");
					break;
				case 9:
					System.out.print("Choose a subcategory (32 => Home, 33 => Car, 34 => Health, 35 => Other insurance): ");
					break;
				case 10:
					System.out.print("Choose a subcategory (36 => Pension investments, 37 => Savings, 38 => Other investments): ");
					break;
				default:
					System.out.println("Invalid input!");
					break;
				}
				int sb1 = scanner.nextInt();

				System.out.print("Choose a year: ");
				int y1 = scanner.nextInt();
				if (y1<2000||y1>2100) {
					System.out.println("Incorrect input");
					return;
				}
				System.out.print("Choose a month (1-12): ");
				int m1 = scanner.nextInt();
				if (m1<0||m1>12) {
					System.out.println("Incorrect input");
					return;
				}


				resultSet = statement.executeQuery("SELECT ID, Year, Month, CategoryID, SubCategoryID, AmountSpent" +
						" FROM expenses" +
						" WHERE CategoryID = '" + c1 + "' and SubCategoryID = '" + sb1 + "' and Year='" + y1 + "' and Month='" + m1 + "'" + 
						" ORDER BY ID"); 

				System.out.printf("%6s  %-6s   %-6s   %-12s   %-12s   %-21s%n", "ID", "Year", "Month", "CategoryID", "SubCategoryID", "AmountSpent");

				while (resultSet.next()) {
					int id = resultSet.getInt("ID");
					String Year = resultSet.getString("Year");
					String Month = resultSet.getString("Month");
					String CategoryID = resultSet.getString("CategoryID");
					String SubCategoryID = resultSet.getString("SubCategoryID");
					String AmountSpent = resultSet.getString("AmountSpent");

					System.out.printf("%6s  %-6s   %-6s   %-12s   %-12s   %-21s%n", id, Year, Month, CategoryID, SubCategoryID, AmountSpent);	
				}
				break;

			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				System.out.println("Invalid input!");
				break;
			}
		}
	}
}
