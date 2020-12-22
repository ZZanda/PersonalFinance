DROP DATABASE IF EXISTS pftool;
CREATE DATABASE pftool;
USE pftool;

CREATE TABLE Categories (
  ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CategoryName VARCHAR(45) NOT NULL);

INSERT INTO Categories (CategoryName) VALUES ('Shopping');
INSERT INTO Categories (CategoryName) VALUES ('Health');
INSERT INTO Categories (CategoryName) VALUES ('Food');
INSERT INTO Categories (CategoryName) VALUES ('Home');
INSERT INTO Categories (CategoryName) VALUES ('Transportation');
INSERT INTO Categories (CategoryName) VALUES ('Leisure');
INSERT INTO Categories (CategoryName) VALUES ('Education');
INSERT INTO Categories (CategoryName) VALUES ('Travelling');
INSERT INTO Categories (CategoryName) VALUES ('Insurance');
INSERT INTO Categories (CategoryName) VALUES ('Investments');

CREATE TABLE IF NOT EXISTS SubCategories (
  ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  SubCategoryName VARCHAR(45) NOT NULL,
  CategoryID INT UNSIGNED NOT NULL,

CONSTRAINT fk_Categories
    FOREIGN KEY (CategoryID) REFERENCES Categories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );

INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Clothing and footwear', 1);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Electronics', 1);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Services', 1);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Beauty products', 1);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other shopping', 1);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Pharmacy', 2);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Medical expenses', 2);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other health', 2);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Groceries', 3);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Restaurants', 3);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other food', 3);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Utilities', 4);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Rent', 4);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Home supplies', 4);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Home credit payments', 4);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other home', 4);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Car related expenses', 5);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Leasing', 5);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Public transport', 5);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other transportation', 5);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Sports', 6);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Culture events and concerts', 6);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Bars, parties', 6);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other leisure', 6);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Education fees', 7);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Study credit payments', 7);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other education', 7);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Hotels', 8);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Flights', 8);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Restaurants abroad', 8);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other travelling', 8);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Home', 9);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Car', 9);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Health', 9);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other insurance', 9);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Pension investments', 10);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Savings', 10);
INSERT INTO SubCategories (SubCategoryName, CategoryID) VALUES ('Other investments', 10);


CREATE TABLE IF NOT EXISTS Expenses (
  ID INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `Year` INT UNSIGNED NOT NULL,
  `Month` INT UNSIGNED NOT NULL,
  CategoryID INT UNSIGNED NOT NULL,
  SubCategoryID INT UNSIGNED NOT NULL,
  AmountSpent DECIMAL(8,2) NOT NULL,
  
  CONSTRAINT fk_ExpensesCategories
    FOREIGN KEY (CategoryID) REFERENCES Categories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  
  CONSTRAINT fk_SubCategories
    FOREIGN KEY (SubCategoryID) REFERENCES SubCategories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );
  
  
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 1, 50);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 2, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 3, 45);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 4, 23);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 5, 15);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 2, 6, 10);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 2, 7, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 2, 8, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 3, 9, 140);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 3, 10, 123);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 3, 11, 34);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 12, 90);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 13, 200);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 14, 35);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 15, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 16, 13);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 5, 17, 55);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 5, 18, 150);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 5, 19, 15);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 5, 20, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 6, 21, 30);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 6, 22, 15);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 6, 23, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 6, 24, 16);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 7, 25, 50);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 7, 26, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 7, 27, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 8, 28, 150);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 8, 29, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 8, 30, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 8, 31, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 9, 32, 20);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 9, 33, 20);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 9, 34, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 9, 35, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 10, 36, 30);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 10, 37, 20);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 10, 10, 38, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 1, 80);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 2, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 3, 30);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 4, 15);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 5, 12);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 2, 6, 10);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 2, 7, 25);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 2, 8, 4);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 3, 9, 170);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 3, 10, 60);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 3, 11, 25);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 12, 85);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 13, 200);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 14, 26);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 15, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 16, 25);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 5, 17, 45);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 5, 18, 150);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 5, 19, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 5, 20, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 6, 21, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 6, 22, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 6, 23, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 6, 24, 12);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 7, 25, 50);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 7, 26, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 7, 27, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 8, 28, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 8, 29, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 8, 30, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 8, 31, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 9, 32, 20);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 9, 33, 20);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 9, 34, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 9, 35, 0);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 10, 36, 30);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 10, 37, 15);
INSERT INTO Expenses (`Year`, `Month`, CategoryID, SubCategoryID, AmountSpent) VALUES (2020, 11, 10, 38, 0);


CREATE TABLE IF NOT EXISTS Budget (
  ID INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  CategoryID INT UNSIGNED NOT NULL UNIQUE,
  `Limit` DECIMAL(8,2) NOT NULL,
  
  CONSTRAINT fk_CategoriesBudget
    FOREIGN KEY (CategoryID) REFERENCES Categories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );
  
INSERT INTO Budget (CategoryID, `Limit`) VALUES (1, 120);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (2, 30);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (3, 280);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (4, 340);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (5, 210);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (6, 30);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (7, 50);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (8, 50);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (9, 40);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (10, 50);