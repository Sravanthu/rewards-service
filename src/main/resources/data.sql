-- Insert customers
INSERT INTO customers (id, name, email) VALUES (1, 'Ram Krishna', 'ram.krishna@example.com');
INSERT INTO customers (id, name, email) VALUES (2, 'Santhosh Ravi', 'santhosh.ravi@example.com');
INSERT INTO customers (id, name, email) VALUES (3, 'Jahnavi Kapoor', 'jahnavi.kapoor@example.com');

-- Insert transactions for Ram Krishna (ID: 1)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 120.50, DATE_SUB(CURRENT_DATE(), INTERVAL 5 DAY), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 75.00, DATE_SUB(CURRENT_DATE(), INTERVAL 15 DAY), 'Purchase at Grocery Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 45.25, DATE_SUB(CURRENT_DATE(), INTERVAL 25 DAY), 'Purchase at Gas Station');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 200.00, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 5 DAY), 'Purchase at Department Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 65.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 15 DAY), 'Purchase at Restaurant');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 110.50, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 2 MONTH), INTERVAL 5 DAY), 'Purchase at Hardware Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 30.25, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 2 MONTH), INTERVAL 15 DAY), 'Purchase at Pharmacy');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 150.00, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 3 MONTH), INTERVAL 5 DAY), 'Purchase at Clothing Store');

-- Insert transactions for Santhosh Ravi (ID: 2)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 95.50, DATE_SUB(CURRENT_DATE(), INTERVAL 3 DAY), 'Purchase at Bookstore');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 55.25, DATE_SUB(CURRENT_DATE(), INTERVAL 13 DAY), 'Purchase at Cafe');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 210.75, DATE_SUB(CURRENT_DATE(), INTERVAL 23 DAY), 'Purchase at Furniture Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 80.00, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 3 DAY), 'Purchase at Grocery Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 120.25, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 13 DAY), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 45.50, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 2 MONTH), INTERVAL 3 DAY), 'Purchase at Gas Station');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 175.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 2 MONTH), INTERVAL 13 DAY), 'Purchase at Department Store');

-- Insert transactions for Jahnavi Kapoor (ID: 3)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 60.25, DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY), 'Purchase at Hardware Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 135.50, DATE_SUB(CURRENT_DATE(), INTERVAL 17 DAY), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 25.75, DATE_SUB(CURRENT_DATE(), INTERVAL 27 DAY), 'Purchase at Convenience Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 90.00, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 7 DAY), 'Purchase at Sporting Goods Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 150.25, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), INTERVAL 17 DAY), 'Purchase at Furniture Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 70.50, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 2 MONTH), INTERVAL 7 DAY), 'Purchase at Restaurant');
