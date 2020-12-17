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
