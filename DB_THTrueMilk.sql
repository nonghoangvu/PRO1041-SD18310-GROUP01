﻿/*
User: sa
Pasword: 123
server: localhost
Port: 1433
*/
--USE master
GO
--DROP DATABASE THTrueMilk
GO

CREATE DATABASE THTrueMilk
GO
USE THTrueMilk
GO
CREATE TABLE [Milk]
(
    [id]              BIGINT IDENTITY (1,1) PRIMARY KEY,
    [barcode]         BIGINT,
    [product_name]    NVARCHAR(255),
    [img]             NVARCHAR(100),
    [price]           INT,
    [amount]          INT,
    [production_date] DATETIME,
    [expiration_date] DATETIME,
    [provider]        NVARCHAR(100),
    [isDelete]        BIT DEFAULT 0
)
GO
CREATE TABLE [Flavor]
(
    [id]        INT IDENTITY (1,1) PRIMARY KEY,
    [taste]     NVARCHAR(50),
    [create_at] DATETIME DEFAULT (GETDATE()),
    [user_id]   INT
)
GO
CREATE TABLE [Unit]
(
    [id]               INT IDENTITY (1,1) PRIMARY KEY,
    [measurement_unit] NCHAR(10),
    [create_at]        DATETIME DEFAULT (GETDATE()),
    [user_id]          INT
)
GO
CREATE TABLE [PackagingSpecification]
(
    [id]             INT IDENTITY (1,1) PRIMARY KEY,
    [packaging_type] NVARCHAR(100),
    [create_at]      DATETIME DEFAULT (GETDATE()),
    [user_id]        INT
)
CREATE TABLE [ProductInfo]
(
    [id]                  INT IDENTITY (1,1) PRIMARY KEY,
    [milk_id]             BIGINT,
    [flavor_id]           INT,-- New
    [brand]               NVARCHAR(100) DEFAULT 'TH True Milk',
    [volume]              FLOAT,
    [unit_id]             INT,
    [origin]              NVARCHAR(50),
    [composition]         NVARCHAR(MAX),
    [packaging_id]        INT,
    [product_description] NVARCHAR(MAX),
    [create_at]           DATETIME      DEFAULT GETDATE(),
    [user_id]             INT
)
CREATE TABLE [HistoryProduct]
(
    [id]          INT IDENTITY (1,1) PRIMARY KEY,
    [description] NVARCHAR(MAX),
    [datetime]    DATETIME DEFAULT GETDATE(),
    [username]    NVARCHAR(50),
    [change_type] NVARCHAR(50)
)
GO
CREATE TABLE [Users]
(
    [id]       INT PRIMARY KEY IDENTITY (1, 1),
    [username] NVARCHAR(50),
    [password] NVARCHAR(MAX),
    [role]     NVARCHAR(255)
)
GO
CREATE TABLE [UserDetails]
(
    [id]           INT PRIMARY KEY IDENTITY (1, 1),
    [user_id]      INT,
    [salary_id]    INT,
    [fullname]     NVARCHAR(100),
    [gender]       NVARCHAR(10),
    [tel]          NVARCHAR(12),
    [email]        NVARCHAR(100),
    [photo]        NVARCHAR(200),
    [address]      NVARCHAR(200),
    [birthdate]    DATE,
    [citizen_id]   NVARCHAR(20),
    [job_position] NVARCHAR(50),
    [note]         NVARCHAR(200),
    [status]       NVARCHAR(50),
    [created_at]   DATE DEFAULT GETDATE()
)
GO
CREATE TABLE [Salary]
(
    [id]           INT PRIMARY KEY IDENTITY (1, 1),
    [salary_type]  NVARCHAR(100),
    [salary_mount] FLOAT,
    [status]       NVARCHAR(10)
)
GO
CREATE TABLE [WorkShift]
(
    [id]         INT PRIMARY KEY IDENTITY (1, 1),
    [shift_name] NVARCHAR(50),
    [start_time] TIME,
    [end_time]   TIME
)
GO

CREATE TABLE [WorkSchedule]
(
    [id]        INT PRIMARY KEY IDENTITY (1, 1),
    [work_date] DATE,
    [shift_id]  INT,
    [user_id]   INT,
    [note]      NVARCHAR(255)
)
GO
CREATE TABLE [Customer]
(
    [id]               INT PRIMARY KEY,
    [fullname]         NVARCHAR(50),
    [phone]            NVARCHAR(20),
    [email]            NVARCHAR(50) UNIQUE,
    [birth_year]       INT,
    [address]          NVARCHAR(50),
    [customer_type_id] INT,
    [note]             NVARCHAR(255),
    [created_at]       DATETIME DEFAULT (GETDATE())
)
GO
CREATE TABLE [CustomerType]
(
    [id]       INT PRIMARY KEY,
    [customer] NVARCHAR(50)
)
GO
CREATE TABLE [Coupons]
(
    [id]                    INT PRIMARY KEY,
    [name]                  NVARCHAR(50),
    [describe]              NVARCHAR(200),
    [discount_value]        INT,
    [quantity]              INT,
    [total_price_after_tax] MONEY,
    [start_day]             DATE,
    [end_day]               DATE,
    [maximum_usage_time]    INT,
    [customer_id]           INT
)
GO
CREATE TABLE [DetailCoupon]
(
    [id]             INT,
    [coupon_id]      INT,
    [percentage_off] INT,
    [total_price]    MONEY,
    [staff_id]       INT,
    [state]          NVARCHAR(30),
    [end_day]        DATE
)
GO
CREATE TABLE [Orders]
(
    [customer_id]   INT,
    [milk_id]       INT,
    [coupon_id]     INT,
    [quantity]      INT,
    [order_date]    DATE,
    [delivery_date] DATE,
    [total_price]   MONEY
)
GO
CREATE TABLE [DiscountUsed]
(
    [id]          INT PRIMARY KEY,
    [customer_id] INT,
    [coupon_id]   INT,
    [order_id]    INT,
    [usage_date]  DATE
)
GO
CREATE TABLE [SaleBill]
(
    [id]                  INT PRIMARY KEY IDENTITY(1,1),
    [sale_event]          NVARCHAR(50),
    [discount_conditions] NVARCHAR(50),
    [percent_decrease]    INT,
    [start_day]           DATE,
    [end_day]             DATE,
    [staff_id]            INT,
    [created_at]          DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [SaleMilk]
(
    [id]               INT PRIMARY KEY IDENTITY(1,1),
    [sale_event]       NVARCHAR(50),
    [percent_decrease] INT,
    [start_day]        DATE,
    [end_day]          DATE,
    [milk_id]          BIGINT,
    [staff_id]         INT,
    [created_at]       DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [Bill]
(
    [id]                     BIGINT PRIMARY KEY,
    [customer_id]            INT,
    [payment_menthod]        NVARCHAR(50),
    [payment_status]         NVARCHAR(50),
    [coupon_id]              INT,
    [tax]                    MONEY,
    [total_amount_after_tax] MONEY,
    [notes]                  NVARCHAR(MAX),
    [created_at]             DATETIME DEFAULT GETDATE(), 
	[staff_id]               INT,
    [sale_bill_id]           INT,
	[shopping_method]        NVARCHAR(250)              
)
GO
CREATE TABLE [BillDetails]
(
    [id]           INT PRIMARY KEY IDENTITY(1,1),
    [bill_id]      BIGINT,
    [milk_id]      INT,
    [service_id]   INT,
    [quantity]     INT,
    [price]        MONEY,
)
GO
CREATE TABLE [HistoryBill]
(
    [id]              INT PRIMARY KEY,
    [bill_id]         BIGINT,
    [milk_id]         BIGINT,
    [customer_id]     INT,
    [staff_id]        INT,
    [invoice_date]    DATETIME,
    [sale_bill_id]    INT,
    [total_amount]    MONEY,
    [payment_status]  NVARCHAR(50),
    [payment_date]    DATETIME,
    [payment_menthod] NVARCHAR(50),
    [service_id]      INT
)
GO
CREATE TABLE [Status]
(
    [id]         INT PRIMARY KEY identity (1,1),
    [statusname] NVARCHAR(50)
);
GO
CREATE TABLE [TransportUnit]
(
    [id]                  INT PRIMARY KEY identity (1,1),
    [transport_unit_name] NVARCHAR(255),
    [address]             NVARCHAR(255),
    [phone]               NVARCHAR(15)
)
GO
CREATE TABLE [DeliveryNote]
(
    [id]                INT PRIMARY KEY identity (1,1),
    [creationdate]      DATETIME DEFAULT (GETDATE()),
    [customer_name]     NVARCHAR(50),
    [address]           NVARCHAR(50),
    [bill_id]           BIGINT,
    [waybill_number]    NVARCHAR(50),
    [transport_unit_id] INT,
    [note]              NVARCHAR(MAX),
    [shipping_cost]     DECIMAL(10, 2),
    [status_id]         INT,
    [sdt]               nvarchar(50),
    [estimatedtime]     DATETIME,
    [milk_name]         NVARCHAR(250),
    [quantity]          INT,
    [total_amount]      DECIMAL(10, 2),
)
GO
ALTER TABLE [ProductInfo]
    ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [ProductInfo]
    ADD FOREIGN KEY ([flavor_id]) REFERENCES [Flavor] ([id])
GO
ALTER TABLE [ProductInfo]
    ADD FOREIGN KEY ([unit_id]) REFERENCES [Unit] ([id])
GO
ALTER TABLE [ProductInfo]
    ADD FOREIGN KEY ([packaging_id]) REFERENCES [PackagingSpecification] ([id])
GO
ALTER TABLE [ProductInfo]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [Flavor]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [Unit]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [PackagingSpecification]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [UserDetails]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [UserDetails]
    ADD FOREIGN KEY ([salary_id]) REFERENCES [Salary] ([id])
GO
ALTER TABLE [WorkSchedule]
    ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [WorkSchedule]
    ADD FOREIGN KEY ([shift_id]) REFERENCES [WorkShift] ([id])
GO
ALTER TABLE [Customer]
    ADD FOREIGN KEY ([customer_type_id]) REFERENCES [CustomerType] ([id])
GO
ALTER TABLE [DiscountUsed]
    ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [Orders]
    ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [DetailCoupon]
    ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [Bill]
    ADD FOREIGN KEY ([customer_id]) REFERENCES [Customer] ([id])
GO
ALTER TABLE [Bill]
    ADD FOREIGN KEY ([sale_bill_id]) REFERENCES [SaleBill] ([id])
GO
ALTER TABLE [BillDetails]
    ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [HistoryBill]
    ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [SaleMilk]
    ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [SaleMilk]
    ADD FOREIGN KEY ([staff_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [Bill]
    ADD FOREIGN KEY ([staff_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [HistoryBill]
    ADD FOREIGN KEY ([staff_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [HistoryBill]
    ADD FOREIGN KEY ([customer_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [HistoryBill]
    ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [DeliveryNote]
    ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [DeliveryNote]
    ADD FOREIGN KEY ([transport_unit_id]) REFERENCES [TransportUnit] ([id])
GO
ALTER TABLE [DeliveryNote]
    ADD FOREIGN KEY ([status_id]) REFERENCES [Status] ([id])
GO
INSERT INTO [Users] ([username], [password], [role])
VALUES ('Admin', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'Admin'),
       ('vunhph33506', '$2a$10$ALO4bzEz7frQ0XHXyU3a/ehNCLg1MC2ROOWQNuoRs7tpNpwsYvVEO', 'User'),
       ('phongvvutuan', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
       ('nguyentranthanhdat', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
	   ('hoangquocbinh0411', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User');
GO

INSERT INTO Salary (salary_type, salary_mount, [status])
VALUES ('Monthly salary', 8000000, 'Active'),
       ('Hourly wage', 21000, 'Active')
go

INSERT INTO [UserDetails] ([user_id], [salary_id], [fullname], [gender], [tel], [email], [photo], [address], [birthdate], [citizen_id], [job_position], [note], [status])
VALUES
(1, 1, N'Admin', N'Male', N'1234567890', N'admin@admin.com', N'hen.png', N'Internet', '1990-01-01', N'123456789', N'Manager', N'This is a note for John Doe', N'Active'),
(2, 2, N'Nong Hoang Vu', N'Male', N'0345904585', N'vunhph33506@fpt.edu.vn', N'boy.png', N'My Dinh 2 - Ha Noi - VietNam', '2004-12-01', N'987654321', N'IT', N'This is a note for Nong Hoang Vu', N'Active'),
(3, 2, N'Vũ Tuấn Phong', N'Male', N' 0345129882', N'phongvvutuan@gmail.com', N'photo3.jpg', N'Null', '2004-08-17', N'555111222', N'Analyst', N'This is a note for Bob Johnson', N'Active'),
(4, 2, N'Nguyen Tran Thanh Dat', N'Female', N'0366097403 ', N' nguyentranthanhdat0000@gmail.com', N'photo4.jpg', N'Hoang Van Thu NamDinh', '2004-08-04', N'777888999', N'Developer', N'This is a note for Alice Brown', N'Active'),
(5, 1, N'Hoang Quoc Binh', N'Male', N'0937818716', N'hoangquocbinh0411@gmail.com', N'photo5.jpg', N'654 Birch St, State', '2003-11-04', N'999000111', N'Sales', N'This is a note for Michael White', N'Active');
GO

INSERT INTO [Flavor] ([taste], [user_id])
VALUES  (N'Matcha', 1),
        (N'Chanh Xanh', 2),
        (N'Dâu Rừng', 1),
        (N'Hạt Nho Đen', 2),
        (N'Hạt Mâm Xôi Hữu Cơ', 1),
        (N'Kem Dừa', 2),
        (N'Cà Phê Sữa', 1),
        (N'Hạt Dưa Lưới', 2),
        (N'Cacao Hữu Cơ', 1),
        (N'Hạt Mâm Xôi Lựu', 2);
INSERT INTO [Unit] ([measurement_unit], [user_id])
VALUES (N'Chai', 1),
       (N'Lọ', 2),
       (N'Hộp', 2),
       (N'Gói', 1),
       (N'Đơn', 2),
       (N'Quả', 1),
       (N'Túi', 2),
       (N'Ống', 1),
       (N'Lon', 2),
       ('Kg', 1);
GO
INSERT INTO [PackagingSpecification] ([packaging_type], [user_id])
VALUES (N'Túi', 2),
       (N'Hộp Cao Cấp', 1),
       (N'Chai Thủy Tinh', 2),
       (N'Gói Nhỏ', 1),
       (N'Chai Nhựa', 2),
       (N'Lon Đen', 1),
       (N'Hộp Xanh', 2),
       (N'Túi Hút Chân Không', 1),
       (N'Hộp Nhỏ', 2),
       (N'Chai Đen', 1);
GO
INSERT INTO [Milk] ([barcode], [product_name], [img], [price], [amount], [production_date], [expiration_date], [provider])
VALUES
    (98765432, N'Sữa Tươi Ngọt 1L', '1.jpg', 25000, 100, '2023-01-01', '2023-02-01', 'TH True Milk'),
    (87654321, N'Sữa Chocolate 500ml', 'chocolate.jpg', 30000, 80, '2023-02-01', '2023-03-01', 'TH True Milk'),
    (76543210, N'Sữa Dâu Ngọt 1L', 'Dau.jpg', 28000, 90, '2023-03-01', '2023-04-01', 'TH True Milk'),
    (6543210, N'Sữa UHT Ngọt 1L', '4.jpg', 22000, 120, '2023-04-01', '2023-05-01', 'TH True Milk'),
    (54321098, N'Yogurt Ngọt 200g', 'ocCho.jpg', 15000, 150, '2023-05-01', '2023-06-01', 'TH True Milk'),
    (43210987, N'Sữa Vanilla 500ml', 'chocolateThung.jpg', 28000, 100, '2023-06-01', '2023-07-01', 'TH True Milk'),
    (32109876, N'Sữa Low-Fat Ngọt 1L', '4.jpg', 23000, 110, '2023-07-01', '2023-08-01', 'TH True Milk'),
    (21098765, N'Sữa Dừa 500ml', 'ImageNull.png', 32000, 70, '2023-08-01', '2023-09-01', 'TH True Milk'),
    (10987654, N'Sữa Bổ Sung Canxi Ngọt 1L', '1.jpg', 26000, 130, '2023-09-01', '2023-10-01', 'TH True Milk'),
    (9876543, N'Sữa Cà Phê 500ml', 'chocolateThung.jpg', 35000, 60, '2023-10-01', '2023-11-01', 'TH True Milk'),
    (87654320, N'Sữa Hạt Mâm Xôi 500ml', 'viOcCho.jpg', 29000, 95, '2023-11-01', '2023-12-01', 'TH True Milk'),
    (76543219, N'Sữa Mocha 1L', 'viDauNho.jpg', 33000, 105, '2023-12-01', '2024-01-01', 'TH True Milk'),
    (65432108, N'Sữa Hạt Lựu 500ml', 'chocolateVi.jpg', 30000, 85, '2024-01-01', '2024-02-01', 'TH True Milk'),
    (54321097, N'Sữa Đậu Nành Ngọt 1L', 'chocolate.jpg', 27000, 115, '2024-02-01', '2024-03-01', 'TH True Milk'),
    (43210986, N'Sữa Hạt Nho 500ml', 'hopCam.png', 31000, 75, '2024-03-01', '2024-04-01', 'TH True Milk'),
    (32109875, N'Sữa Protein Whey Ngọt 1L', '3.jpg', 34000, 110, '2024-04-01', '2024-05-01', 'TH True Milk'),
    (21098764, N'Sữa Yogurt Vani 500ml', 'Dau.jpg', 28000, 100, '2024-05-01', '2024-06-01', 'TH True Milk'),
    (10987653, N'Sữa Hạt Mè Ngọt 1L', 'ocCho.jpg', 32000, 120, '2024-06-01', '2024-07-01', 'TH True Milk'),
    (98765442, N'Sữa Hạt Dưa Hấu 500ml', 'viDauNho.jpg', 29000, 95, '2024-07-01', '2024-08-01', 'TH True Milk'),
    (87654331, N'Sữa Cacao 1L', 'viCam.png', 33000, 105, '2024-08-01', '2024-09-01', 'TH True Milk'),
    (98765431, N'Sữa Hạt Dưa Lưới 500ml', 'chocolateThung.jpg', 28000, 95, '2024-09-01', '2024-10-01', 'TH True Milk'),
    (87654330, N'Sữa Cacao Hữu Cơ 1L', 'chocolateVi.jpg', 34000, 100, '2024-10-01', '2024-11-01', 'TH True Milk'),
    (76543229, N'Sữa Hạt Mâm Xôi Lựu 500ml', 'viOcCho.jpg', 31000, 85, '2024-11-01', '2024-12-01', 'TH True Milk'),
    (65432118, N'Sữa Matcha 1L', 'chocolateVi.jpg', 33000, 110, '2024-12-01', '2025-01-01', 'TH True Milk'),
    (54321017, N'Sữa Chanh Xanh 500ml', 'hopCam.png', 29000, 90, '2025-01-01', '2025-02-01', 'TH True Milk'),
    (43210976, N'Sữa Dâu Rừng 1L', 'viDauChai.png', 32000, 120, '2025-02-01', '2025-03-01', 'TH True Milk'),
    (32109865, N'Sữa Hạt Nho Đen 500ml', 'thungOcCho.jpg', 35000, 80, '2025-03-01', '2025-04-01', 'TH True Milk'),
    (21098754, N'Sữa Hạt Mâm Xôi Hữu Cơ 1L', 'sua_hat_mam_xoi_huu_co_1l.jpg', 33000, 105, '2025-04-01', '2025-05-01', 'TH True Milk'),
    (10987643, N'Sữa Kem Dừa 500ml', 'viDauNho.jpg', 30000, 100, '2025-05-01', '2025-06-01', 'TH True Milk'),
    (98765440, N'Sữa Cà Phê Sữa 1L', 'chocolate.jpg', 36000, 95, '2025-06-01', '2025-07-01', 'TH True Milk');
INSERT INTO [ProductInfo] (
    [milk_id], [flavor_id], [brand], [volume], [unit_id], [origin], [composition],
    [packaging_id], [product_description], [user_id]
)
VALUES
    (1, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa tươi', 1, N'Mô tả sản phẩm: Sữa Tươi Ngọt 1L', 2),
    (2, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate', 2, N'Mô tả sản phẩm: Sữa Chocolate 500ml', 1),
    (3, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dâu', 1, N'Mô tả sản phẩm: Sữa Dâu Ngọt 1L', 2),
    (4, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa UHT', 1, N'Mô tả sản phẩm: Sữa UHT Ngọt 1L', 1),
    (5, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt', 3, N'Mô tả sản phẩm: Yogurt 200g', 2),
    (6, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa vanilla', 2, N'Mô tả sản phẩm: Sữa Vanilla 500ml', 1),
    (7, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa low-fat', 1, N'Mô tả sản phẩm: Sữa Low-Fat Ngọt 1L', 2),
    (8, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa', 2, N'Mô tả sản phẩm: Sữa Dừa 500ml', 1),
    (9, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa bổ sung canxi', 1, N'Mô tả sản phẩm: Sữa Bổ Sung Canxi Ngọt 1L', 2),
    (10, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa cà phê', 2, N'Mô tả sản phẩm: Sữa Cà Phê 500ml', 1),
    (11, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 2, N'Mô tả sản phẩm: Sữa Hạt Mâm Xôi 500ml', 2),
    (12, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mocha', 1, N'Mô tả sản phẩm: Sữa Mocha 1L', 2),
    (13, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt lựu', 2, N'Mô tả sản phẩm: Sữa Hạt Lựu 500ml', 1),
    (14, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đậu nành', 1, N'Mô tả sản phẩm: Sữa Đậu Nành Ngọt 1L', 2),
    (15, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nho', 2, N'Mô tả sản phẩm: Sữa Hạt Nho 500ml', 1),
    (16, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa protein whey', 1, N'Mô tả sản phẩm: Sữa Protein Whey Ngọt 1L', 2),
    (17, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần yogurt vani', 2, N'Mô tả sản phẩm: Yogurt Vani 500ml', 1),
    (18, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mè', 1, N'Mô tả sản phẩm: Sữa Hạt Mè Ngọt 1L', 2),
    (19, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dưa hấu', 2, N'Mô tả sản phẩm: Sữa Hạt Dưa Hấu 500ml', 1),
    (20, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa cacao', 1, N'Mô tả sản phẩm: Sữa Cacao 1L', 2),
    (21, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dưa lưới', 1, N'Mô tả sản phẩm: Sữa Hạt Dưa Lưới 500ml', 2),
    (22, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa cacao hữu cơ', 2, N'Mô tả sản phẩm: Sữa Cacao Hữu Cơ 1L', 1),
    (23, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi lựu', 2, N'Mô tả sản phẩm: Sữa Hạt Mâm Xôi Lựu 500ml', 1),
    (24, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa matcha', 1, N'Mô tả sản phẩm: Sữa Matcha 1L', 2),
    (25, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần sữa chanh xanh', 3, N'Mô tả sản phẩm: Sữa Chanh Xanh 500ml', 1),
    (26, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dâu rừng', 1, N'Mô tả sản phẩm: Sữa Dâu Rừng 1L', 2),
    (27, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nho đen', 2, N'Mô tả sản phẩm: Sữa Hạt Nho Đen 500ml', 1),
    (28, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi hữu cơ', 1,
     N'Mô tả sản phẩm: Sữa Hạt Mâm Xôi Hữu Cơ 1L', 2),
    (29, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa kem dừa', 2, N'Mô tả sản phẩm: Sữa Kem Dừa 500ml', 1),
    (30, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa cà phê sữa', 1, N'Mô tả sản phẩm: Sữa Cà Phê Sữa 1L', 2);

INSERT INTO SaleMilk ( sale_event, percent_decrease, start_day, end_day, milk_id, staff_id)
VALUES ('SALE 28-11-2023', 30, GETDATE(), '2023-12-01', 1, 1)
INSERT INTO SaleMilk ( sale_event, percent_decrease, start_day, end_day, milk_id, staff_id)
VALUES ('SALE 28-11-2023', 70, GETDATE(), '2023-12-01', 2, 1)
--Bug--
INSERT INTO [CustomerType] ([id], [customer])
VALUES (1, N'Regular');
INSERT INTO [CustomerType] ([id], [customer])
VALUES (2, N'VIP');
GO
-- Add data to Customer table
INSERT INTO [Customer] ([id], [fullname], [phone], [email], [birth_year], [address], [customer_type_id], [note])
VALUES (1, N'John Doe', N'555-555-5555', N'john.doe@example.com', 1980, N'123 Elm St, City', 1, N'Regular customer');
INSERT INTO [Customer] ([id], [fullname], [phone], [email], [birth_year], [address], [customer_type_id], [note])
VALUES (2, N'Jane Smith', N'777-777-7777', N'jane.smith@example.com', 1975, N'456 Oak St, Town', 2, N'VIP customer');
GO
INSERT INTO [Bill] ([id], [customer_id], [payment_menthod], [payment_status], [coupon_id], [tax],
                    [total_amount_after_tax], [notes], [created_at], [shopping_method])
VALUES (1, 1, N'Credit Card', N'Paid', 1, 0.05, 105.00, N'Note 1', '2023-11-25 13:34:00', N'directly'),
       (2, 2, N'PayPal', N'Pending', 2, 0.10, 110.00, N'Note 2', '2023-11-25 13:34:00', N'directly'),
       (3, 1, N'Cash', N'Unpaid', 3, 0.15, 115.00, N'Note 3', '2023-11-25 13:34:00', N'directly'),
       (4, 2, N'Credit Card', N'Paid', 4, 0.20, 120.00, N'Note 4', '2023-11-25 13:34:00', N'directly'),
       (5, 2, N'PayPal', N'Pending', 5, 0.25, 125.00, N'Note 5', '2023-11-25 13:34:00', N'delivery'),
       (6, 1, N'Cash', N'Unpaid', 1, 0.30, 130.00, N'Note 6', '2023-11-25 13:34:00', N'delivery'),
       (7, 2, N'Credit Card', N'Paid', 2, 0.35, 135.00, N'Note 7', '2023-11-25 13:34:00', N'delivery'),
       (8, 2, N'PayPal', N'Pending', 3, 0.40, 140.00, N'Note 8', '2023-11-25 13:34:00', N'delivery'),
       (9, 1, N'Cash', N'Unpaid', 4, 0.45, 145.00, N'Note 9', '2023-11-25 13:34:00', N'delivery'),
       (10, 2, N'Credit Card', N'Paid', 5, 0.50, 150.00, N'Note 10', '2023-11-25 13:34:00', N'delivery');

INSERT INTO [Status] ([statusname])
VALUES (N'Đang chuẩn bị');
INSERT INTO [Status] ([statusname])
VALUES (N'Bàn giao cho bên vận chuyển');
INSERT INTO [Status] ([statusname])
VALUES (N'Đang giao hàng');
INSERT INTO [Status] ([statusname])
VALUES (N'Huỷ giao hàng');
INSERT INTO [Status] ([statusname])
VALUES (N'Giao thành công');
GO
-- Add data to TransportUnit table
INSERT INTO [TransportUnit] ([transport_unit_name], [address], [phone])
VALUES (N'Express Delivery', N'123 Main Street, City A', N'123-456-7890');
INSERT INTO [TransportUnit] ([transport_unit_name], [address], [phone])
VALUES (N'Speedy Logistics', N'456 Elm Street, City B', N'987-654-3210');
INSERT INTO [TransportUnit] ([transport_unit_name], [address], [phone])
VALUES (N'Rapid Shippers', N'789 Oak Street, City C', N'789-123-4567');
INSERT INTO [TransportUnit] ([transport_unit_name], [address], [phone])
VALUES (N'Swift Couriers', N'101 Pine Street, City D', N'654-321-9870');
INSERT INTO [TransportUnit] ([transport_unit_name], [address], [phone])
VALUES (N'Quick Dispatch', N'202 Maple Street, City E', N'321-987-6540');
GO
-- Add data to DeliveryNoteDetail table

INSERT INTO [DeliveryNote] ([bill_id], [waybill_number], [transport_unit_id], [note], [shipping_cost], [status_id],
                            [estimatedtime], [milk_name], [quantity], [customer_name], [address], [sdt], [total_amount])
VALUES (1, N'ABC123', 1, N'Giao hàng đến địa chỉ A', 100.00, 1, '2023-11-30 12:00:00', 1, 10, N'Nguyễn Văn A',
        N'Địa chỉ A', N'0123456789', 500.00),
       (2, N'XYZ456', 2, N'Giao hàng đến địa chỉ B', 200.00, 2, '2023-12-01 14:00:00', 2, 20, N'Nguyễn Văn B',
        N'Địa chỉ B', N'0123456789', 1000.00),
       (3, N'PQR789', 3, N'Giao hàng đến địa chỉ C', 300.00, 3, '2023-12-02 16:00:00', 3, 30, N'Nguyễn Văn C',
        N'Địa chỉ C', N'0123456789', 1500.00),
       (4, N'LMN012', 4, N'Giao hàng đến địa chỉ D', 400.00, 4, '2023-12-03 18:00:00', 4, 40, N'Nguyễn Văn D',
        N'Địa chỉ D', N'0123456789', 2000.00),
       (5, N'JKL345', 1, N'Giao hàng đến địa chỉ E', 500.00, 5, '2023-12-04 20:00:00', 5, 50, N'Nguyễn Văn E',
        N'Địa chỉ E', N'0123456789', 2500.00),
       (6, N'ABC123', 2, N'Giao hàng đến địa chỉ F', 100.00, 1, '2023-11-30 12:00:00', 6, 60, N'Nguyễn Văn F',
        N'Địa chỉ F', N'0123456789', 500.00),
       (7, N'XYZ456', 3, N'Giao hàng đến địa chỉ G', 200.00, 2, '2023-12-01 14:00:00', 7, 70, N'Nguyễn Văn G',
        N'Địa chỉ G', N'0123456789', 1000.00),
       (8, N'PQR789', 4, N'Giao hàng đến địa chỉ H', 300.00, 3, '2023-12-02 16:00:00', 8, 80, N'Nguyễn Văn H',
        N'Địa chỉ H', N'0123456789', 1500.00),
       (9, N'LMN012', 5, N'Giao hàng đến địa chỉ I', 400.00, 4, '2023-12-03 18:00:00', 9, 90, N'Nguyễn Văn I',
        N'Địa chỉ I', N'0123456789', 2000.00),
       (10, N'JKL345', 1, N'Giao hàng đến địa chỉ J', 500.00, 1, '2023-12-04 20:00:00', 10, 100, N'Nguyễn Văn J',
        N'Địa chỉ J', N'0123456789', 2500.00);

/*
UPDATE SaleMilk SET end_day = '2023-12-01' WHERE id = 1
UPDATE SaleMilk SET percent_decrease = 10 WHERE id = 2

SELECT * FROM SaleMilk
SELECT * FROM Users
SELECT * FROM MILK INNER JOIN PRODUCTINFO ON MILK.ID = PRODUCTINFO.MILK_ID
*/
--Select * from Users
Select * from UserDetails inner join Users on UserDetails.user_id  = Users.id

update UserDetails set fullname = 'Nong Hoang Vu' where user_id = 2