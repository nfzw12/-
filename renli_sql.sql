CREATE DATABASE IF NOT EXISTS employees_test;
USE employees_test;

CREATE TABLE IF NOT EXISTS staff (
    id INT PRIMARY KEY,                -- 员工编号
    name VARCHAR(255) NOT NULL,         -- 员工姓名
    sex VARCHAR(255) NOT NULL,          -- 员工性别
    age VARCHAR(255) NOT NULL,          -- 员工年龄
    department VARCHAR(255) NOT NULL,   -- 员工所在部门
    position VARCHAR(255) NOT NULL ,     -- 员工岗位
		price VARCHAR(255)            -- 员工薪资，字符型，最大255个字符
);

INSERT INTO staff (id, name, sex, age, department, position,price) 
VALUES 
(1, '张三', '男', '30', '技术部', '开发工程师', '8000'),
(2, '李四', '女', '28', '人事部', '人事专员', '9500'),
(3, '王五', '男', '35', '市场部', '市场经理', '12000'),
(4, '赵六', '女', '40', '财务部', '财务主管', '7500'),
(5, '孙七', '男', '25', '技术部', '前端开发', '10500'),
(6, '周八', '女', '32', '销售部', '销售主管', '13000'),
(7, '吴九', '男', '27', '技术部', '后端开发', '9000'),
(8, '郑十', '女', '29', '技术部', '产品经理', '11000'),
(9, '钱十一', '男', '45', '行政部', '行政经理', '10000'),
(10, '孙十二', '女', '38', '市场部', '市场专员', '9500');

-- 部门信息表
CREATE TABLE IF NOT EXISTS department (
    id INT PRIMARY KEY,                -- 部门编号，设置为主键
    name VARCHAR(255) NOT NULL,         -- 部门名称，不能为空
    amount VARCHAR(255) NOT NULL,       -- 部门人数，不能为空
    director_name VARCHAR(255)                   -- 部门负责人的员工编号
);

INSERT INTO department (id, name, amount, director_name) VALUES
(1, '技术部', '4', '张三'),  -- 张三是技术部负责人
(2, '人事部', '1', '李四'),  -- 李四是人事部负责人
(3, '市场部', '2', '王五'),  -- 王五是市场部负责人
(4, '财务部', '1', '赵六'),  -- 赵六是财务部负责人
(5, '销售部', '1', '周八'),  -- 周八是销售部负责人
(6, '行政部', '1', '钱十一');  -- 钱十一是行政部负责人

DELIMITER $$

CREATE TRIGGER update_department_count_after_insert
AFTER INSERT ON staff
FOR EACH ROW
BEGIN
    UPDATE department
    SET amount = CAST(amount AS UNSIGNED) + 1
    WHERE name = NEW.department;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER update_department_count_after_delete
AFTER DELETE ON staff
FOR EACH ROW
BEGIN
    UPDATE department
    SET amount = CAST(amount AS UNSIGNED) - 1
    WHERE name = OLD.department;
END $$

DELIMITER ;

-- 招聘信息表
CREATE TABLE IF NOT EXISTS employment (
    id INT PRIMARY KEY,          -- 应聘者编号，整数类型，主键
    name VARCHAR(255),           -- 应聘者姓名，字符型，最大255个字符
    sex VARCHAR(255),            -- 应聘人性别，字符型，最大255个字符
    age VARCHAR(255),            -- 应聘人年龄，字符型，最大255个字符
    position VARCHAR(255)        -- 应聘岗位，字符型，最大255个字符
);

INSERT INTO employment (id, name, sex, age, position) VALUES
(1, '张三', '男', '28', '软件工程师'),
(2, '李四', '女', '25', '产品经理'),
(3, '王五', '男', '30', '市场专员'),
(4, '赵六', '女', '22', '人力资源'),
(5, '孙七', '男', '35', '销售经理'),
(6, '周八', '男', '40', '财务主管'),
(7, '吴九', '女', '29', 'UI设计师'),
(8, '郑十', '男', '32', '技术支持'),
(9, '冯十一', '女', '26', '数据分析师'),
(10, '陈十二', '男', '31', '前端开发');

-- 考勤记录表
CREATE TABLE attendance (
    record_id INT PRIMARY KEY AUTO_INCREMENT,   -- 考勤记录ID（自增）
    employee_id INT,                            -- 员工ID（外键，关联员工表）
    clock_in_time DATETIME,                     -- 上班打卡时间
    clock_out_time DATETIME,                    -- 下班打卡时间
    attendance_date DATE,                       -- 考勤日期
    FOREIGN KEY (employee_id) REFERENCES staff(id) ON DELETE CASCADE -- 关联员工表
);

INSERT INTO attendance (employee_id, clock_in_time, clock_out_time, attendance_date)
VALUES
    (1, '2024-11-06 08:30:00', '2024-11-06 17:30:00', '2024-11-06'),  -- 员工1，2024年11月6日
    (2, '2024-11-06 08:45:00', '2024-11-06 17:15:00', '2024-11-06'),  -- 员工2，2024年11月6日
    (3, '2024-11-06 08:30:00', '2024-11-06 17:30:00', '2024-11-06'),  -- 员工3，2024年11月6日
    (4, '2024-11-06 08:50:00', '2024-11-06 17:00:00', '2024-11-06'),  -- 员工4，2024年11月6日
    (5, '2024-11-07 08:30:00', '2024-11-07 17:30:00', '2024-11-07'),  -- 员工5，2024年11月7日
    (2, '2024-11-07 09:00:00', '2024-11-07 18:00:00', '2024-11-07'),  -- 员工2，2024年11月7日
    (6, '2024-11-07 08:30:00', '2024-11-07 17:30:00', '2024-11-07'),  -- 员工6，2024年11月7日
    (7, '2024-11-08 08:30:00', '2024-11-08 17:30:00', '2024-11-08'),  -- 员工7，2024年11月8日
    (8, '2024-11-08 09:00:00', '2024-11-08 18:00:00', '2024-11-08'),  -- 员工8，2024年11月8日
    (9, '2024-11-08 08:45:00', '2024-11-08 17:15:00', '2024-11-08');  -- 员工9，2024年11月8日




DELIMITER $$

DROP PROCEDURE IF EXISTS clock_in $$	-- 打卡的存储过程,调用call clock_in(1)

CREATE PROCEDURE clock_in(
    IN p_employee_id INT
)
BEGIN
    DECLARE v_current_time DATETIME;
    DECLARE v_current_date DATE;

    -- 获取当前时间和日期
    SET v_current_time = NOW();
    SET v_current_date = CURDATE();

    -- 插入打卡记录
    INSERT INTO attendance (employee_id, clock_in_time, attendance_date)
    VALUES (p_employee_id, v_current_time, v_current_date);
    
    -- 输出成功信息
    SELECT '打卡成功' AS message;
END $$

DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS clock_out $$	   -- 签退的存储过程，如果不在之前打卡的那一天或者没找到打卡记录就会失败，调用clock_out(1)

CREATE PROCEDURE clock_out(IN p_employee_id INT)  
BEGIN
    DECLARE v_today DATE;
    DECLARE v_existing_record INT;
    
    -- 获取当前日期
    SET v_today = CURDATE();

    -- 检查今天是否已经有打卡记录
    SELECT COUNT(*) INTO v_existing_record
    FROM attendance
    WHERE employee_id = p_employee_id
      AND attendance_date = v_today
      AND clock_out_time IS NULL; -- 确保未签退

    -- 如果记录存在且未签退
    IF v_existing_record > 0 THEN
        -- 更新考勤记录的签退时间
        UPDATE attendance
        SET clock_out_time = NOW()
        WHERE employee_id = p_employee_id
          AND attendance_date = v_today
          AND clock_out_time IS NULL; -- 确保只更新未签退的记录

        SELECT '签退成功' AS message;
    ELSE
        -- 如果没有考勤记录或者已经签退
        SELECT '失败：没有找到今天的打卡记录或已签退' AS message;
    END IF;
END $$

DELIMITER ;

-- 管理员登录表
CREATE TABLE IF NOT EXISTS Admin (
    id INT PRIMARY KEY,               -- 管理员编号
    password VARCHAR(255) NOT NULL     -- 管理员密码
);

INSERT INTO Admin (id, password) 
VALUES (123456, '123456');

-- 员工登录表

CREATE TABLE IF NOT EXISTS employee_login (
    id INT PRIMARY KEY,               -- 员工编号
    password VARCHAR(255) DEFAULT '666',  -- 员工密码，默认值为 666
    FOREIGN KEY (id) REFERENCES staff(id) ON DELETE CASCADE -- 外键约束，引用 staff 表的 id
);

INSERT INTO employee_login (id)
VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);

-- 触发器，如果插入新员工，自动生成新的默认登录密码
DELIMITER $$

CREATE TRIGGER IF NOT EXISTS after_insert_staff
AFTER INSERT ON staff
FOR EACH ROW
BEGIN
    INSERT INTO employee_login (id, password)
    VALUES (NEW.id, '666');
END $$

DELIMITER ;
