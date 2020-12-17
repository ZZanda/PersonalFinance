DROP DATABASE IF EXISTS pftool;
CREATE DATABASE pftool;
USE pftool;

CREATE TABLE Categories (
  ID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CategoryName VARCHAR(45) NOT NULL);

INSERT INTO Categories (ID, CategoryName) VALUES (1, 'Shopping');
INSERT INTO Categories (ID, CategoryName) VALUES (2, 'Health');
INSERT INTO Categories (ID, CategoryName) VALUES (3, 'Food');
INSERT INTO Categories (ID, CategoryName) VALUES (4, 'Home');
INSERT INTO Categories (ID, CategoryName) VALUES (5, 'Transportation');
INSERT INTO Categories (ID, CategoryName) VALUES (6, 'Leisure');
INSERT INTO Categories (ID, CategoryName) VALUES (7, 'Education');
INSERT INTO Categories (ID, CategoryName) VALUES (8, 'Travelling');
INSERT INTO Categories (ID, CategoryName) VALUES (9, 'Insurance');
INSERT INTO Categories (ID, CategoryName) VALUES (10, 'Investments');

CREATE TABLE IF NOT EXISTS SubCategories (
  ID INT UNSIGNED NOT NULL PRIMARY KEY,
  SubCategoryName VARCHAR(45) NOT NULL,
  CategoryID INT UNSIGNED NOT NULL,

CONSTRAINT fk_Categories
    FOREIGN KEY (CategoryID) REFERENCES Categories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );

INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (1, 'Clothing and footwear', 1);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (2, 'Electronics', 1);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (3, 'Services', 1);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (4, 'Beauty products', 1);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (5, 'Other shopping', 1);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (6, 'Pharmacy', 2);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (7, 'Medical expenses', 2);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (8, 'Other health', 2);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (9, 'Groceries', 3);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (10, 'Restaurants', 3);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (11, 'Other food', 3);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (12, 'Utilities', 4);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (13, 'Rent', 4);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (14, 'Home supplies', 4);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (15, 'Home credit payments', 4);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (16, 'Other home', 4);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (17, 'Car related expenses', 5);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (18, 'Leasing', 5);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (19, 'Public transport', 5);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (20, 'Other transportation', 5);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (21, 'Sports', 6);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (22, 'Culture events and concerts', 6);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (23, 'Bars, parties', 6);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (24, 'Other leisure', 6);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (25, 'Education fees', 7);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (26, 'Study credit payments', 7);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (27, 'Other education', 7);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (28, 'Hotels', 8);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (29, 'Flights', 8);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (30, 'Restaurants abroad', 8);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (31, 'Other travelling', 8);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (32, 'Home', 9);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (33, 'Car', 9);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (34, 'Health', 9);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (35, 'Other insurance', 9);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (36, 'Pension investments', 10);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (37, 'Savings', 10);
INSERT INTO SubCategories (ID, SubCategoryName, CategoryID) VALUES (38, 'Other investments', 10);


CREATE TABLE IF NOT EXISTS Expenses (
  `Year` INT UNSIGNED NOT NULL,
  `Month` INT UNSIGNED NOT NULL,
  SubCategoryID INT UNSIGNED NOT NULL,
  AmountSpent DECIMAL(8,2) NOT NULL);
  
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (`Year`, `Month`, SubCategoryID, AmountSpent);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 1, 50);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 2, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 3, 45);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 4, 23);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 5, 15);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 6, 10);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 7, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 8, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 9, 140);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 10, 123);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 11, 34);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 12, 90);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 13, 200);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 14, 35);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 15, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 16, 13);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 17, 55);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 18, 150);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 19, 15);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 20, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 21, 30);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 22, 15);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 23, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 24, 16);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 25, 50);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 26, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 27, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 28, 150);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 29, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 30, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 31, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 32, 20);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 33, 20);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 34, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 35, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 36, 30);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 37, 20);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 10, 38, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 1, 80);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 2, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 3, 30);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 4, 15);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 5, 12);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 6, 10);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 7, 25);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 8, 4);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 9, 170);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 10, 60);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 11, 25);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 12, 85);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 13, 200);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 14, 26);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 15, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 16, 25);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 17, 45);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 18, 150);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 19, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 20, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 21, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 22, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 23, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 24, 12);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 25, 50);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 26, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 27, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 28, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 29, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 30, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 31, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 32, 20);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 33, 20);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 34, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 35, 0);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 36, 30);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 37, 15);
INSERT INTO Expenses (`Year`, `Month`, SubCategoryID, AmountSpent) VALUES (2020, 11, 38, 0);
