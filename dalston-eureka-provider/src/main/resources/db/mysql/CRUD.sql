USE  cloud;

SELECT * FROM department d;

SELECT * FROM department d ORDER BY dept_no LIMIT 3;

SELECT * FROM department d WHERE d.dept_no > (SELECT dept_no FROM department ORDER BY dept_no LIMIT 2, 1) ORDER BY dept_no LIMIT 3;

SELECT COUNT(dept_no) FROM department;

-- 执行存储过程，批量插入数据
CALL PatchInsert();

SELECT * FROM employee;

-- 使用索引 1.5
SELECT * FROM employee emp WHERE emp.emp_name = 'Grace7699';

-- 使用索引 0.004
SELECT * FROM employee emp WHERE emp.emp_id = '7849';

