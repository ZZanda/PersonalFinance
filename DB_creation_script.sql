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
  SubCategoryID INT UNSIGNED NOT NULL,
  AmountSpent DECIMAL(8,2) NOT NULL,
  
  CONSTRAINT fk_SubCategories
    FOREIGN KEY (SubCategoryID) REFERENCES SubCategories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );
  
  
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

CREATE TABLE IF NOT EXISTS Budget (
  ID INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  CategoryID INT UNSIGNED NOT NULL UNIQUE,
  `Limit` DECIMAL(8,2) NOT NULL,
  
  CONSTRAINT fk_CategoriesBudget
    FOREIGN KEY (CategoryID) REFERENCES Categories (ID)
    ON DELETE RESTRICT ON UPDATE CASCADE
    );
  
INSERT INTO Budget (CategoryID, `Limit`) VALUES (1, 340);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (2, 280);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (3, 210);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (4, 50);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (5, 120);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (6, 30);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (7, 50);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (8, 50);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (9, 40);
INSERT INTO Budget (CategoryID, `Limit`) VALUES (10, 30);

