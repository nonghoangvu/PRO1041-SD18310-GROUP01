/*
User: sa
Pasword: 123
server: localhost
Port: 1433
*/
CREATE DATABASE THTrueMilk
GO
USE THTrueMilk
GO
CREATE TABLE [Milk]
(
    [id]              BIGINT PRIMARY KEY,
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
    [status] NVARCHAR(10)
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
    [id]                  INT PRIMARY KEY,
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
    [id]               INT PRIMARY KEY,
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
    [id]                     INT PRIMARY KEY,
    [customer_id]            INT,
    [payment_menthod]        NVARCHAR(50),
    [payment_status]         NVARCHAR(50),
    [coupon_id]              INT,
    [tax]                    MONEY,
    [total_amount_after_tax] MONEY,
    [notes]                  NVARCHAR(MAX),
    [created_at]             DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [BillDetails]
(
    [id]           INT PRIMARY KEY,
    [bill_id]      INT,
    [milk_id]      INT,
    [service_id]   INT,
    [quantity]     INT,
    [price]        MONEY,
    [total_amount] MONEY,
    [staff_id]     INT,
    [sale_bill_id] INT
)
GO
CREATE TABLE [HistoryBill]
(
    [id]              INT PRIMARY KEY,
    [bill_id]         INT,
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
CREATE TABLE [DeliveryNote]
(
    [id]           INT PRIMARY KEY,
    [creationdate] DATETIME DEFAULT (GETDATE()),
    [customer_id]  INT,
    [bill_id]      INT,
    [note]         NVARCHAR(MAX)
)
GO
CREATE TABLE [DeliveryNoteDetail]
(
    [id]                INT PRIMARY KEY,
    [delivery_note_id]  INT,
    [status_id]         INT,
    [product_id]        BIGINT,
    [staff_id]          INT,
    [Quantity]          INT,
    [transport_unit_id] INT
)
GO
CREATE TABLE [Status]
(
    [id]         INT PRIMARY KEY,
    [statusname] NVARCHAR(50)
)
GO
CREATE TABLE [Transport]
(
    [id]                    INT PRIMARY KEY,
    [deliverynotedetail_id] INT,
    [deliverydate]          DATETIME DEFAULT (GETDATE()),
    [estimatedtime]         DATETIME,
    [transportunit_id]      INT
)
GO
CREATE TABLE [TransportUnit]
(
    [id]                  INT PRIMARY KEY,
    [transport_unit_name] NVARCHAR(255),
    [address]             NVARCHAR(255),
    [phone]               NVARCHAR(15)
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
ALTER TABLE [BillDetails]
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
ALTER TABLE [BillDetails]
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
    ADD FOREIGN KEY ([customer_id]) REFERENCES [customer] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([delivery_note_id]) REFERENCES [DeliveryNote] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([status_id]) REFERENCES [Status] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([product_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([staff_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([transport_unit_id]) REFERENCES [TransportUnit] ([id])
GO
ALTER TABLE [DeliveryNoteDetail]
    ADD FOREIGN KEY ([transport_unit_id]) REFERENCES [TransportUnit] ([id])
GO
INSERT INTO [Users] ([username], [password], [role])
VALUES ('Admin', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'Admin'),
       ('NongHoangVu04', '$2a$10$ALO4bzEz7frQ0XHXyU3a/ehNCLg1MC2ROOWQNuoRs7tpNpwsYvVEO', 'Admin'),
       ('Employee001', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
       ('convitcute', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User');
GO
INSERT INTO Salary (salary_type, salary_mount, status) VALUES ('A', 131112, 'Active'), ('B', 131112, 'Active')
INSERT INTO [Flavor] ([taste], [user_id])
VALUES (N'Ngọt', 1),
       ('Chocolate', 2),
       ('Vanilla', 1);
INSERT INTO [Unit] ([measurement_unit], [user_id])
VALUES (N'Lít', 1),
       ('Milliliter', 2),
       ('Gam', 1);
GO
INSERT INTO [PackagingSpecification] ([packaging_type], [user_id])
VALUES ('Chai', 2),
       (N'Hộp', 2),
       ('Lon', 2);
GO
INSERT INTO [Milk] ([id], [product_name], [img], [price], [amount], [production_date], [expiration_date], [provider])
VALUES
    (98765432, N'Sữa Tươi 1L', 'sua_tuoi_1l.jpg', 25000, 100, '2023-01-01', '2023-02-01', 'TH True Milk'),
    (87654321, N'Sữa Chocolate 500ml', 'sua_chocolate_500ml.jpg', 30000, 80, '2023-02-01', '2023-03-01', 'TH True Milk'),
    (76543210, N'Sữa Dâu 1L', 'sua_dau_1l.jpg', 28000, 90, '2023-03-01', '2023-04-01', 'TH True Milk'),
    (6543210, N'Sữa UHT 1L', 'sua_uht_1l.jpg', 22000, 120, '2023-04-01', '2023-05-01', 'TH True Milk'),
    (54321098, N'Yogurt 200g', 'yogurt_200g.jpg', 15000, 150, '2023-05-01', '2023-06-01', 'TH True Milk'),
    (43210987, N'Sữa Vanilla 500ml', 'sua_vanilla_500ml.jpg', 28000, 100, '2023-06-01', '2023-07-01', 'TH True Milk'),
    (32109876, N'Sữa Low-Fat 1L', 'sua_low_fat_1l.jpg', 23000, 110, '2023-07-01', '2023-08-01', 'TH True Milk'),
    (21098765, N'Sữa Dừa 500ml', 'sua_dua_500ml.jpg', 32000, 70, '2023-08-01', '2023-09-01', 'TH True Milk'),
    (10987654, N'Sữa Bổ Sung Canxi 1L', 'sua_bo_sung_canxi_1l.jpg', 26000, 130, '2023-09-01', '2023-10-01', 'TH True Milk'),
    (9876543, N'Sữa Cà Phê 500ml', 'sua_ca_phe_500ml.jpg', 35000, 60, '2023-10-01', '2023-11-01', 'TH True Milk'),
    (87654320, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml.jpg', 29000, 95, '2023-11-01', '2023-12-01', 'TH True Milk'),
    (76543219, N'Sữa Mocha 1L', 'sua_mocha_1l.jpg', 33000, 105, '2023-12-01', '2024-01-01', 'TH True Milk'),
    (65432108, N'Sữa Hạt Lựu 500ml', 'sua_hat_luu_500ml.jpg', 30000, 85, '2024-01-01', '2024-02-01', 'TH True Milk'),
    (54321097, N'Sữa Đậu Nành 1L', 'sua_dau_nanh_1l.jpg', 27000, 115, '2024-02-01', '2024-03-01', 'TH True Milk'),
    (43210986, N'Sữa Hạt Nho 500ml', 'sua_hat_nho_500ml.jpg', 31000, 75, '2024-03-01', '2024-04-01', 'TH True Milk'),
    (32109875, N'Sữa Protein Whey 1L', 'sua_protein_whey_1l.jpg', 34000, 110, '2024-04-01', '2024-05-01', 'TH True Milk'),
    (21098764, N'Sữa Yogurt Vani 500ml', 'sua_yogurt_vani_500ml.jpg', 28000, 100, '2024-05-01', '2024-06-01', 'TH True Milk'),
    (10987653, N'Sữa Hạt Mè 1L', 'sua_hat_me_1l.jpg', 32000, 120, '2024-06-01', '2024-07-01', 'TH True Milk'),
    (98765442, N'Sữa Hạt Dưa Hấu 500ml', 'sua_hat_dua_hau_500ml.jpg', 29000, 95, '2024-07-01', '2024-08-01', 'TH True Milk'),
    (87654331, N'Sữa Cacao 1L', 'sua_cacao_1l.jpg', 33000, 105, '2024-08-01', '2024-09-01', 'TH True Milk'),
    (76543220, N'Sữa Hạt Hạnh Nhân 500ml', 'sua_hat_hanh_nhan_500ml.jpg', 30000, 85, '2024-09-01', '2024-10-01', 'TH True Milk'),
    (65432109, N'Sữa Yogurt Dâu 200g', 'sua_yogurt_dau_200g.jpg', 32000, 75, '2024-10-01', '2024-11-01', 'TH True Milk'),
    (43210988, N'Sữa Dâu Tây 500ml', 'sua_dau_tay_500ml.jpg', 28000, 100, '2024-11-01', '2024-12-01', 'TH True Milk'),
    (32109877, N'Sữa Hạt Việt Quất 1L', 'sua_hat_viet_quat_1l.jpg', 29000, 95, '2024-12-01', '2025-01-01', 'TH True Milk'),
    (21098766, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml_2.jpg', 33000, 105, '2025-01-01', '2025-02-01', 'TH True Milk'),
    (10987655, N'Sữa Mocha 1L', 'sua_mocha_1l_2.jpg', 30000, 85, '2025-02-01', '2025-03-01', 'TH True Milk'),
    (98765434, N'Sữa Hạt Lựu 500ml', 'sua_hat_luu_500ml_2.jpg', 32000, 110, '2025-03-01', '2025-04-01', 'TH True Milk'),
    (87654323, N'Sữa Đậu Nành 1L', 'sua_dau_nanh_1l_2.jpg', 29000, 95, '2025-04-01', '2025-05-01', 'TH True Milk'),
    (76543212, N'Sữa Hạt Nho 500ml', 'sua_hat_nho_500ml_2.jpg', 27000, 115, '2025-05-01', '2025-06-01', 'TH True Milk'),
    (52525366, N'Sữa Hạt Việt Quất 500ml', 'sua_hat_viet_quat_500ml.jpg', 31000, 80, '2025-06-01', '2025-07-01', 'TH True Milk');

INSERT INTO [ProductInfo] ([milk_id], [flavor_id], [brand], [volume], [unit_id], [origin], [composition],
                           [packaging_id], [product_description], [user_id])
VALUES
    (98765432, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa tươi', 1, N'Mô tả sữa tươi', 2),
    (87654321, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate', 2, N'Mô tả sữa chocolate', 1),
    (76543210, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dâu', 1, N'Mô tả sữa dâu', 2),
    (6543210, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa UHT', 1, N'Mô tả sữa UHT', 1),
    (54321098, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt', 3, N'Mô tả yogurt', 2),
    (43210987, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa vanilla', 2, N'Mô tả sữa vanilla', 1),
    (32109876, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa low-fat', 1, N'Mô tả sữa low-fat', 2),
    (21098765, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa', 2, N'Mô tả sữa dừa', 1),
    (10987654, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa bổ sung canxi', 1, N'Mô tả sữa bổ sung canxi', 2),
    (9876543, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa cà phê', 2, N'Mô tả sữa cà phê', 1),
    (87654320, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 2, N'Mô tả sữa hạt mâm xôi', 1),
    (76543219, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mocha', 1, N'Mô tả sữa mocha', 2),
    (65432108, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt lựu', 2, N'Mô tả sữa hạt lựu', 1),
    (54321097, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đậu nành', 1, N'Mô tả sữa đậu nành', 2),
    (43210986, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nho', 2, N'Mô tả sữa hạt nho', 1),
    (32109875, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa protein whey', 1, N'Mô tả sữa protein whey', 2),
    (21098764, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần yogurt vani', 2, N'Mô tả yogurt vani', 1),
    (10987653, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mè', 1, N'Mô tả sữa hạt mè', 2),
    (98765442, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dưa hấu', 2, N'Mô tả sữa hạt dưa hấu', 1),
    (87654331, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa cacao', 1, N'Mô tả sữa cacao', 2),
    (76543220, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt hạnh nhân', 2, N'Mô tả sữa hạt hạnh nhân', 1),
    (65432109, 3, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt dâu', 3, N'Mô tả yogurt dâu', 2),
    (43210988, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dâu tây', 2, N'Mô tả sữa dâu tây', 1),
    (32109877, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt việt quất', 1, N'Mô tả sữa hạt việt quất', 2),
    (21098766, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 2, N'Mô tả sữa hạt mâm xôi', 1),
    (10987655, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mocha', 1, N'Mô tả sữa mocha', 2),
    (98765434, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt lựu', 2, N'Mô tả sữa hạt lựu', 1),
    (87654323, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đậu nành', 1, N'Mô tả sữa đậu nành', 2),
    (76543212, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nho', 2, N'Mô tả sữa hạt nho', 1),
    (52525366, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt việt quất', 2, N'Mô tả sữa hạt việt quất', 1);

INSERT INTO SaleMilk (id, sale_event, percent_decrease, start_day, end_day, milk_id, staff_id) VALUES (1, 'SALE 28-11-2023', 30, GETDATE(), '2023-12-01', 98765432, 1)
INSERT INTO SaleMilk (id, sale_event, percent_decrease, start_day, end_day, milk_id, staff_id) VALUES (2, 'SALE 28-11-2023', 70, GETDATE(), '2023-12-01', 87654321, 1)

UPDATE SaleMilk SET end_day = '2023-12-01' WHERE id = 1
UPDATE SaleMilk SET percent_decrease = 10 WHERE id = 2

SELECT * FROM SaleMilk
SELECT * FROM Users
SELECT * FROM MILK INNER JOIN PRODUCTINFO ON MILK.ID = PRODUCTINFO.MILK_ID