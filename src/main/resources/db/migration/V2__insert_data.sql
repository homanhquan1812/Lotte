INSERT INTO users (username, password, role) VALUES
('admin',   '$2a$12$nhWk7fK8ToEnHXKa4/iQp.q7FoSN9fVjEn5sIs1w.P9Pk2viva3bu', 'ADMIN'),  -- password: admin123
('staff1',  '$2a$12$hrNeBKZL/kxJk163QbHYmeZzKie190GsJPHAzfYeappm5eNzbl1yC', 'STAFF'),  -- password: staff123
('staff2',  '$2a$12$QFdK2E9Pk30dxWox6GyiUuPJO/7nA4GgaA709u.Qej1lIxHFxFA76', 'STAFF');  -- password: staff123

INSERT INTO documents (code, title, description, category, status, created_by, file_name) VALUES
('DOC-001', 'Annual Report 2025',        'Company annual financial report',        'Finance',   'APPROVED',  1, 'annual_report_2025.pdf'),
('DOC-002', 'Employee Handbook',          'Onboarding policy document',             'HR',        'APPROVED',  1, 'employee_handbook.pdf'),
('DOC-003', 'Q1 Budget Draft',            'Draft budget for Q1 planning',           'Finance',   'DRAFT',     2, NULL),
('DOC-004', 'Vendor Contract - Acme Co',  'Service agreement with Acme Co',         'Legal',     'SUBMITTED', 2, 'acme_contract.pdf'),
('DOC-005', 'Marketing Campaign Brief',   'Q2 campaign strategy brief',             'Marketing', 'DRAFT',     3, NULL),
('DOC-006', 'Leave Request Policy',       'Updated staff leave request procedure',  'HR',        'REJECTED',  3, NULL),
('DOC-007', 'IT Security Guideline',      'Internal security compliance guideline', 'IT',        'APPROVED',  1, 'it_security_guideline.pdf'),
('DOC-008', 'Vendor Contract - Beta Ltd', 'Service agreement with Beta Ltd',        'Legal',     'SUBMITTED', 2, 'beta_contract.pdf'),
('DOC-009', 'Q1 Sales Summary',           'Regional sales performance summary',     'Finance',   'APPROVED',  3, 'q1_sales_summary.pdf'),
('DOC-010', 'Office Relocation Notice',   'Draft notice for office relocation',     'HR',        'DRAFT',     1, NULL);