-- Insert customers
INSERT INTO customers (id, name, email) VALUES (1, 'Ram Krishna', 'ram.krishna@example.com');
INSERT INTO customers (id, name, email) VALUES (2, 'Santhosh Ravi', 'santhosh.ravi@example.com');
INSERT INTO customers (id, name, email) VALUES (3, 'Jahnavi Kapoor', 'jahnavi.kapoor@example.com');

-- Insert transactions for Ram Krishna (ID: 1)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 120.50, DATEADD('DAY', -5, CURRENT_DATE()), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 75.00, DATEADD('DAY', -15, CURRENT_DATE()), 'Purchase at Grocery Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 45.25, DATEADD('DAY', -25, CURRENT_DATE()), 'Purchase at Gas Station');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 200.00, DATEADD('DAY', 5, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Department Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 65.75, DATEADD('DAY', 15, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Restaurant');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 110.50, DATEADD('DAY', 5, DATEADD('MONTH', -2, CURRENT_DATE())), 'Purchase at Hardware Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 30.25, DATEADD('DAY', 15, DATEADD('MONTH', -2, CURRENT_DATE())), 'Purchase at Pharmacy');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (1, 150.00, DATEADD('DAY', 5, DATEADD('MONTH', -3, CURRENT_DATE())), 'Purchase at Clothing Store');

-- Insert transactions for Santhosh Ravi (ID: 2)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 95.50, DATEADD('DAY', -3, CURRENT_DATE()), 'Purchase at Bookstore');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 55.25, DATEADD('DAY', -13, CURRENT_DATE()), 'Purchase at Cafe');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 210.75, DATEADD('DAY', -23, CURRENT_DATE()), 'Purchase at Furniture Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 80.00, DATEADD('DAY', 3, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Grocery Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 120.25, DATEADD('DAY', 13, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 45.50, DATEADD('DAY', 3, DATEADD('MONTH', -2, CURRENT_DATE())), 'Purchase at Gas Station');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (2, 175.75, DATEADD('DAY', 13, DATEADD('MONTH', -2, CURRENT_DATE())), 'Purchase at Department Store');

-- Insert transactions for Jahnavi Kapoor (ID: 3)
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 60.25, DATEADD('DAY', -7, CURRENT_DATE()), 'Purchase at Hardware Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 135.50, DATEADD('DAY', -17, CURRENT_DATE()), 'Purchase at Electronics Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 25.75, DATEADD('DAY', -27, CURRENT_DATE()), 'Purchase at Convenience Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 90.00, DATEADD('DAY', 7, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Sporting Goods Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 150.25, DATEADD('DAY', 17, DATEADD('MONTH', -1, CURRENT_DATE())), 'Purchase at Furniture Store');
INSERT INTO transactions (customer_id, amount, date, description) VALUES (3, 70.50, DATEADD('DAY', 7, DATEADD('MONTH', -2, CURRENT_DATE())), 'Purchase at Restaurant');
