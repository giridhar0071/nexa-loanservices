
-- =====================
-- Loan Types
-- =====================
INSERT INTO loanschema.loan_types (loan_type_id, type, description, status)
VALUES
  (gen_random_uuid(), 'home', 'Home loan product', 'active'),
  (gen_random_uuid(), 'auto', 'Auto loan product', 'active'),
  (gen_random_uuid(), 'personal', 'Personal loan product', 'active');

-- =====================
-- Users
-- =====================
INSERT INTO loanschema.users (user_id, name, dob, gender, email, phone, annual_salary, credit_score)
VALUES
  (gen_random_uuid(), 'Alice Johnson', '1985-04-12', 'F', 'alice@example.com', '555-111-2222', 75000.00, 780), -- High credit
  (gen_random_uuid(), 'Bob Smith', '1990-06-25', 'M', 'bob@example.com', '555-333-4444', 40000.00, 640), -- Mid credit
  (gen_random_uuid(), 'Charlie Lee', '1995-02-14', 'M', 'charlie@example.com', '555-555-6666', 32000.00, 580), -- Low credit, points tier
  (gen_random_uuid(), 'Diana Prince', '1988-09-09', 'F', 'diana@example.com', '555-777-8888', 29000.00, 520), -- Very low credit, points tier
  (gen_random_uuid(), 'Ethan Hunt', '1975-12-01', 'M', 'ethan@example.com', '555-999-0000', 28000.00, 450); -- Rejected (<500)

-- =====================
-- Loan Points
-- (For simplicity, one set for each loan type: 500–549, 550–589, 590–629)
-- Fee percentages are in decimal (0.08 = 8%, but here schema uses numeric(12,2) so store 0.08)
-- =====================

-- Home loan points
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.08, 500, 549 FROM loanschema.loan_types WHERE type = 'home';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.06, 550, 589 FROM loanschema.loan_types WHERE type = 'home';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.04, 590, 629 FROM loanschema.loan_types WHERE type = 'home';

-- Auto loan points
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 200000.00, 0.08, 500, 549 FROM loanschema.loan_types WHERE type = 'auto';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 200000.00, 0.06, 550, 589 FROM loanschema.loan_types WHERE type = 'auto';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 200000.00, 0.04, 590, 629 FROM loanschema.loan_types WHERE type = 'auto';

-- Personal loan points
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 100000.00, 0.08, 500, 549 FROM loanschema.loan_types WHERE type = 'personal';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 100000.00, 0.06, 550, 589 FROM loanschema.loan_types WHERE type = 'personal';
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 100000.00, 0.04, 590, 629 FROM loanschema.loan_types WHERE type = 'personal';

-- =====================
-- Loan Eligibility Rules
-- (APR for 630+ and link to points for 500–629)
-- =====================

-- Home Loan Rules
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 10.50, 60000, 720, 850 FROM loanschema.loan_types WHERE type = 'home';
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 11.50, 60000, 630, 719 FROM loanschema.loan_types WHERE type = 'home';
-- link 590–629
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 500000.00, NULL, 60000, 590, 629, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 590
WHERE lt.type = 'home';
-- link 550–589
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 500000.00, NULL, 60000, 550, 589, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 550
WHERE lt.type = 'home';
-- link 500–549
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 500000.00, NULL, 60000, 500, 549, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 500
WHERE lt.type = 'home';

-- Auto Loan Rules
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 200000.00, 10.50, 35000, 720, 850 FROM loanschema.loan_types WHERE type = 'auto';
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 200000.00, 11.50, 35000, 630, 719 FROM loanschema.loan_types WHERE type = 'auto';
-- link 590–629
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 200000.00, NULL, 35000, 590, 629, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 590
WHERE lt.type = 'auto';
-- link 550–589
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 200000.00, NULL, 35000, 550, 589, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 550
WHERE lt.type = 'auto';
-- link 500–549
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 200000.00, NULL, 35000, 500, 549, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 500
WHERE lt.type = 'auto';

-- Personal Loan Rules
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 100000.00, 10.50, 30000, 720, 850 FROM loanschema.loan_types WHERE type = 'personal';
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 100000.00, 11.50, 30000, 630, 719 FROM loanschema.loan_types WHERE type = 'personal';
-- link 590–629
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 100000.00, NULL, 30000, 590, 629, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 590
WHERE lt.type = 'personal';
-- link 550–589
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 100000.00, NULL, 30000, 550, 589, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 550
WHERE lt.type = 'personal';
-- link 500–549
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 100000.00, NULL, 30000, 500, 549, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 500
WHERE lt.type = 'personal';

-- Insert a user
INSERT INTO loanschema.users (name, dob, gender, email, phone, annual_salary, credit_score)
VALUES ('Alice Johnson', '1990-04-15', 'Female', 'alice.johnson@example.com', '5551234567', 65000.00, 720);

-- Insert a loan type
INSERT INTO loanschema.loan_types (type, description)
VALUES ('Home Loan', 'Loan for purchasing a house');

-- Insert loan points (for credit score ranges, required by eligibility rules)
INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.08, 500, 549
FROM loanschema.loan_types WHERE type = 'Home Loan';

INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.06, 550, 589
FROM loanschema.loan_types WHERE type = 'Home Loan';

INSERT INTO loanschema.loan_points (loan_type_id, max_loan_amount, percentage_on_loan_approval, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 0.04, 590, 629
FROM loanschema.loan_types WHERE type = 'Home Loan';

-- Insert loan eligibility rules
INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 10.50, 60000.00, 720, 850
FROM loanschema.loan_types WHERE type = 'Home Loan';

INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, apr, min_salary, min_credit_score, max_credit_score)
SELECT loan_type_id, 500000.00, 11.50, 60000.00, 630, 719
FROM loanschema.loan_types WHERE type = 'Home Loan';

INSERT INTO loanschema.loan_eligibility_rules (loan_type_id, max_loan_amount, min_salary, min_credit_score, max_credit_score, loan_point_id)
SELECT lt.loan_type_id, 500000.00, 60000.00, 590, 629, lp.loan_point_id
FROM loanschema.loan_types lt
JOIN loanschema.loan_points lp ON lt.loan_type_id = lp.loan_type_id AND lp.min_credit_score = 590;

-- Insert a loan application for Alice
INSERT INTO loanschema.loan_application (user_id, loan_type_id, loan_eid, requested_amount, submitted_date, status)
SELECT u.user_id, lt.loan_type_id, le.loan_eid, 250000.00, CURRENT_DATE, 'Draft'
FROM loanschema.users u
JOIN loanschema.loan_types lt ON lt.type = 'Home Loan'
JOIN loanschema.loan_eligibility_rules le ON le.loan_type_id = lt.loan_type_id
WHERE u.email = 'alice.johnson@example.com'
LIMIT 1;

-- Insert repayment option (only valid if loan was Approved — faking for testing)
INSERT INTO loanschema.approved_loans_repayment_options (loan_id, months, apr, monthly_payments, total_principal_amount, total_payable_amount)
SELECT la.loan_id, 120, 10.50, 2700.00, 250000.00, 324000.00
FROM loanschema.loan_application la
JOIN loanschema.users u ON la.user_id = u.user_id
WHERE u.email = 'alice.johnson@example.com'
LIMIT 1;
