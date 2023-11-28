/*
User: sa
Pasword: 123
server: localhost
Port: 1433
*/
/*CREATE DATABASE THTrueMilk
GO
USE THTrueMilk
GO
CREATE TABLE [Milk] (
  [id] BIGINT PRIMARY KEY,
  [product_name] NVARCHAR(255),
  [img] NVARCHAR(100),
  [price] INT,
  [amount] INT,
  [production_date] DATETIME,
  [expiration_date] DATETIME,
  [provider] NVARCHAR(100),
  [isDelete] BIT DEFAULT 0
)
GO
CREATE TABLE [Flavor] (
  [id] INT IDENTITY(1,1) PRIMARY KEY,
  [taste] NVARCHAR(50),
  [create_at] DATETIME DEFAULT (GETDATE()),
  [user_id] INT
)
GO
CREATE TABLE [Unit] (
  [id] INT IDENTITY(1,1) PRIMARY KEY,
  [measurement_unit] NCHAR(10),
  [create_at] DATETIME DEFAULT (GETDATE()),
  [user_id] INT
)
GO
CREATE TABLE [PackagingSpecification] (
  [id] INT IDENTITY(1,1) PRIMARY KEY,
  [packaging_type] NVARCHAR(100),
  [create_at] DATETIME DEFAULT (GETDATE()),
  [user_id] INT
)
CREATE TABLE [ProductInfo] (
  [id] INT IDENTITY(1,1) PRIMARY KEY,
  [milk_id] BIGINT,
  [flavor_id] INT,-- New
  [brand] NVARCHAR(100) DEFAULT 'TH True Milk',
  [volume] FLOAT,
  [unit_id] INT,
  [origin] NVARCHAR(50),
  [composition] NVARCHAR(MAX),
  [packaging_id] INT,
  [product_description] NVARCHAR(MAX),
  [create_at] DATETIME DEFAULT GETDATE(),
  [user_id] INT
)
CREATE TABLE [HistoryProduct] (
  [id] INT IDENTITY(1,1) PRIMARY KEY,
  [description] NVARCHAR(MAX),
  [datetime] DATETIME DEFAULT GETDATE(),
  [username] NVARCHAR(50),
  [change_type] NVARCHAR(50)
)
GO
CREATE TABLE [Users] (
  [id] INT PRIMARY KEY IDENTITY(1, 1),
  [username] NVARCHAR(50),
  [password] NVARCHAR(MAX),
  [role] NVARCHAR(255)
)
GO
CREATE TABLE [UserDetails] (
  [id] INT PRIMARY KEY IDENTITY(1, 1),
  [user_id] INT,
  [salary_id] INT,
  [fullname] NVARCHAR(100),
  [gender] NVARCHAR(10),
  [tel] NVARCHAR(12),
  [email] NVARCHAR(100),
  [photo] NVARCHAR(200),
  [address] NVARCHAR(200),
  [birthdate] DATE,
  [citizen_id] NVARCHAR(20),
  [job_position] NVARCHAR(50),
  [note] NVARCHAR(200),
  [status] NVARCHAR(50),
  [created_at] DATE DEFAULT GETDATE()
)
GO
CREATE TABLE [Salary] (
  [id] INT PRIMARY KEY IDENTITY(1, 1),
  [salary_type] NVARCHAR(100),
  [salary_mount] FLOAT
)
GO

CREATE TABLE [WorkShift] (
  [id] INT PRIMARY KEY IDENTITY(1, 1),
  [shift_name] NVARCHAR(50),
  [start_time] TIME,
  [end_time] TIME
)
GO

CREATE TABLE [WorkSchedule] (
  [id] INT PRIMARY KEY IDENTITY(1, 1),
  [work_date] DATE,
  [shift_id] INT,
  [user_id] INT,
  [note] NVARCHAR(255)
)
GO
CREATE TABLE [Customer] (
  [id] INT PRIMARY KEY,
  [fullname] NVARCHAR(50),
  [phone] NVARCHAR(20),
  [email] NVARCHAR(50) UNIQUE,
  [birth_year] INT,
  [address] NVARCHAR(50),
  [customer_type_id] INT,
  [note] NVARCHAR(255),
  [created_at] DATETIME DEFAULT (GETDATE())
)
GO
CREATE TABLE [CustomerType] (
  [id] INT PRIMARY KEY,
  [customer] NVARCHAR(50)
)
GO
CREATE TABLE [Coupons] (
  [id] INT PRIMARY KEY,
  [name] NVARCHAR(50),
  [describe] NVARCHAR(200),
  [discount_value] INT,
  [quantity] INT,
  [total_price_after_tax] MONEY,
  [start_day] DATETIME,
  [end_day] DATETIME,
  [maximum_usage_time] INT,
  [customer_id] INT
)
GO
CREATE TABLE [DetailCoupon] (
  [id] INT,
  [coupon_id] INT,
  [percentage_off] INT,
  [total_price] MONEY,
  [staff_id] INT,
  [state] NVARCHAR(30)
)
GO
CREATE TABLE [Orders] (
  [customer_id] INT,
  [milk_id] INT,
  [coupon_id] INT,
  [quantity] INT,
  [order_date] DATETIME,
  [delivery_date] DATETIME,
  [total_price] MONEY
)
GO
CREATE TABLE [DiscountUsed] (
  [id] INT PRIMARY KEY,
  [customer_id] INT,
  [coupon_id] INT,
  [order_id] INT,
  [usage_date] DATETIME
)
GO
CREATE TABLE [SaleBill] (
  [id] INT PRIMARY KEY,
  [sale_event] NVARCHAR(50),
  [discount_conditions] NVARCHAR(50),
  [percent_decrease] INT,
  [start_day] DATE,
  [end_day] DATE,
  [staff_id] INT,
  [created_at] DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [SaleMilk] (
  [id] INT PRIMARY KEY,
  [sale_event] NVARCHAR(50),
  [percent_decrease] INT,
  [start_day] DATE,
  [end_day] DATE,
  [milk_id] BIGINT,
  [staff_id] INT,
  [created_at] DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [Bill] (
  [id] INT PRIMARY KEY,
  [customer_id] INT,
  [payment_menthod] NVARCHAR(50),
  [payment_status] NVARCHAR(50),
  [coupon_id] INT,
  [tax] MONEY,
  [total_amount_after_tax] MONEY,
  [notes] NVARCHAR(MAX),
  [created_at] DATETIME DEFAULT GETDATE()
)
GO
CREATE TABLE [BillDetails] (
  [id] INT PRIMARY KEY,
  [bill_id] INT,
  [milk_id] INT,
  [service_id] INT,
  [quantity] INT,
  [price] MONEY,
  [total_amount] MONEY,
  [staff_id] INT,
  [sale_bill_id] INT
)
GO
CREATE TABLE [HistoryBill] (
  [id] INT PRIMARY KEY,
  [bill_id] INT,
  [milk_id] BIGINT,
  [customer_id] INT,
  [staff_id] INT,
  [invoice_date] DATETIME,
  [sale_bill_id] INT,
  [total_amount] MONEY,
  [payment_status] NVARCHAR(50),
  [payment_date] DATETIME,
  [payment_menthod] NVARCHAR(50),
  [service_id] INT
)
GO
CREATE TABLE [DeliveryNote] (
  [id] INT PRIMARY KEY,
  [creationdate] DATETIME DEFAULT (GETDATE()),
  [customer_id] INT,
  [bill_id] INT,
  [note] NVARCHAR(MAX)
)
GO
CREATE TABLE [DeliveryNoteDetail] (
  [id] INT PRIMARY KEY,
  [delivery_note_id] INT,
  [status_id] INT,
  [product_id] BIGINT,
  [staff_id] INT,
  [Quantity] INT,
  [transport_unit_id] INT
)
GO
CREATE TABLE [Status] (
  [id] INT PRIMARY KEY,
  [statusname] NVARCHAR(50)
)
GO
CREATE TABLE [Transport] (
  [id] INT PRIMARY KEY,
  [deliverynotedetail_id] INT,
  [deliverydate] DATETIME DEFAULT (GETDATE()),
  [estimatedtime] DATETIME,
  [transportunit_id] INT
)
GO
CREATE TABLE [TransportUnit] (
  [id] INT PRIMARY KEY,
  [transport_unit_name] NVARCHAR(255),
  [address] NVARCHAR(255),
  [phone] NVARCHAR(15)
)
GO
ALTER TABLE [ProductInfo] ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [ProductInfo] ADD FOREIGN KEY ([flavor_id]) REFERENCES [Flavor] ([id])
GO
ALTER TABLE [ProductInfo] ADD FOREIGN KEY ([unit_id]) REFERENCES [Unit] ([id])
GO
ALTER TABLE [ProductInfo] ADD FOREIGN KEY ([packaging_id]) REFERENCES [PackagingSpecification] ([id])
GO
ALTER TABLE [ProductInfo] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [Flavor] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [Unit] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [PackagingSpecification] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [UserDetails] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [UserDetails] ADD FOREIGN KEY ([salary_id]) REFERENCES [Salary] ([id])
GO
ALTER TABLE [WorkSchedule] ADD FOREIGN KEY ([user_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [WorkSchedule] ADD FOREIGN KEY ([shift_id]) REFERENCES [WorkShift] ([id])
GO
ALTER TABLE [Customer] ADD FOREIGN KEY ([customer_type_id]) REFERENCES [CustomerType] ([id])
GO
ALTER TABLE [DiscountUsed] ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [Orders] ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [DetailCoupon] ADD FOREIGN KEY ([coupon_id]) REFERENCES [Coupons] ([id])
GO
ALTER TABLE [Bill] ADD FOREIGN KEY ([customer_id]) REFERENCES [Customer] ([id])
GO
ALTER TABLE [BillDetails] ADD FOREIGN KEY ([sale_bill_id]) REFERENCES [SaleBill] ([id])
GO
ALTER TABLE [BillDetails] ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [HistoryBill] ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [SaleMilk] ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [SaleMilk] ADD FOREIGN KEY ([staff_id]) REFERENCES [UserDetails] ([id])
GO
ALTER TABLE [BillDetails] ADD FOREIGN KEY ([staff_id]) REFERENCES [UserDetails] ([id])
GO
ALTER TABLE [HistoryBill] ADD FOREIGN KEY ([staff_id]) REFERENCES [UserDetails] ([id])
GO
ALTER TABLE [HistoryBill] ADD FOREIGN KEY ([customer_id]) REFERENCES [UserDetails] ([id])
GO
ALTER TABLE [HistoryBill] ADD FOREIGN KEY ([milk_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [DeliveryNote] ADD FOREIGN KEY ([bill_id]) REFERENCES [Bill] ([id])
GO
ALTER TABLE [DeliveryNote] ADD FOREIGN KEY ([customer_id]) REFERENCES [customer] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([delivery_note_id]) REFERENCES [DeliveryNote] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([status_id]) REFERENCES [Status] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([product_id]) REFERENCES [Milk] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([staff_id]) REFERENCES [Users] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([transport_unit_id]) REFERENCES [TransportUnit] ([id])
GO
ALTER TABLE [DeliveryNoteDetail] ADD FOREIGN KEY ([transport_unit_id]) REFERENCES [TransportUnit] ([id])
GO
INSERT INTO [Milk] ([id],[product_name], [img], [price], [amount], [production_date], [expiration_date], [provider])
VALUES
(1,N'Sữa Tươi 1L', 'sua_tuoi_1l.jpg', 25000, 100, '2023-01-01', '2023-02-01', 'TH True Milk'),
(2,N'Sữa Chocolate 500ml', 'sua_chocolate_500ml.jpg', 30000, 80, '2023-02-01', '2023-03-01', 'TH True Milk'),
(3,N'Sữa Dâu 1L', 'sua_dau_1l.jpg', 28000, 90, '2023-03-01', '2023-04-01', 'TH True Milk'),
(4,N'Sữa UHT 1L', 'sua_uht_1l.jpg', 22000, 120, '2023-04-01', '2023-05-01', 'TH True Milk'),
(5,N'Yogurt 200g', 'yogurt_200g.jpg', 15000, 150, '2023-05-01', '2023-06-01', 'TH True Milk'),
(6,N'Sữa Vanilla 500ml', 'sua_vanilla_500ml.jpg', 28000, 100, '2023-06-01', '2023-07-01', 'TH True Milk'),
(7, N'Sữa Low-Fat 1L', 'sua_low_fat_1l.jpg', 23000, 110, '2023-07-01', '2023-08-01', 'TH True Milk'),
(8,N'Sữa Dừa 500ml', 'sua_dua_500ml.jpg', 32000, 70, '2023-08-01', '2023-09-01', 'TH True Milk'),
(9, N'Sữa Bổ Sung Canxi 1L', 'sua_bo_sung_canxi_1l.jpg', 26000, 130, '2023-09-01', '2023-10-01', 'TH True Milk'),
(10,N'Sữa Cà Phê 500ml', 'sua_ca_phe_500ml.jpg', 35000, 60, '2023-10-01', '2023-11-01', 'TH True Milk'),
(11,N'Sữa Hữu Cơ 1L', 'sua_huu_co_1l.jpg', 30000, 95, '2023-11-01', '2023-12-01', 'TH True Milk'),
(12, N'Sữa Hạt Almond 500ml', 'sua_hat_almond_500ml.jpg', 38000, 50, '2023-12-01', '2024-01-01', 'TH True Milk'),
(13,N'Sữa Protein 1L', 'sua_protein_1l.jpg', 27000, 125, '2024-01-01', '2024-02-01', 'TH True Milk'),
(14, N'Yogurt Việt Quất 200g', 'yogurt_viet_quat_200g.jpg', 18000, 170, '2024-02-01', '2024-03-01', 'TH True Milk'),
(15, N'Sữa Hạt Dẻ Cười 500ml', 'sua_hat_de_cuoi_500ml.jpg', 32000, 80, '2024-03-01', '2024-04-01', 'TH True Milk'),
(16, N'Sữa Tươi Nguyên Chất 1L', 'sua_tuoi_nguyen_chat_1l.jpg', 24000, 110, '2024-04-01', '2024-05-01', 'TH True Milk'),
(17, N'Sữa Hạt Nho 1L', 'sua_hat_nho_1l.jpg', 28000, 90, '2025-01-01', '2025-02-01', 'TH True Milk'),
(18, N'Sữa Đào 500ml', 'sua_dao_500ml.jpg', 30000, 90, '2024-05-01', '2024-06-01', 'TH True Milk'),
(19, N'Sữa Lactose-Free 1L', 'sua_lactose_free_1l.jpg', 28000, 105, '2024-06-01', '2024-07-01', 'TH True Milk'),
(20,N'Sữa Caramel 500ml', 'sua_caramel_500ml.jpg', 35000, 75, '2024-07-01', '2024-08-01', 'TH True Milk'),
(21,N'Sữa Đậu Nành 1L', 'sua_dau_nanh_1l.jpg', 26000, 120, '2024-08-01', '2024-09-01', 'TH True Milk'),
(22, N'Sữa Gạo 1L', 'sua_gao_1l.jpg', 20000, 150, '2024-09-01', '2024-10-01', 'TH True Milk'),
(23, N'Sữa Matcha 500ml', 'sua_matcha_500ml.jpg', 38000, 60, '2024-10-01', '2024-11-01', 'TH True Milk'),
(24, N'Sữa Hạt Dưa Hấu 1L', 'sua_hat_dua_hau_1l.jpg', 26000, 120, '2024-11-01', '2024-12-01', 'TH True Milk'),
(25, N'Sữa Cacao 500ml', 'sua_cacao_500ml.jpg', 32000, 70, '2024-12-01', '2025-01-01', 'TH True Milk'),
(26, N'Sữa Hạt Nho 1L', 'sua_hat_nho_1l.jpg', 28000, 90, '2025-01-01', '2025-02-01', 'TH True Milk'),
(27, N'Yogurt Dâu 200g', 'yogurt_dau_200g.jpg', 18000, 170, '2025-02-01', '2025-03-01', 'TH True Milk'),
(28, N'Sữa Dâu Tây 500ml', 'sua_dau_tay_500ml.jpg', 30000, 100, '2025-03-01', '2025-04-01', 'TH True Milk'),
(29, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 25000, 110, '2025-04-01', '2025-05-01', 'TH True Milk'),
(30, N'Sữa Mocha 500ml', 'sua_mocha_500ml.jpg', 35000, 80, '2025-05-01', '2025-06-01', 'TH True Milk'),
(31, N'Sữa Hạt Việt Quất 1L', 'sua_hat_viet_quat_1l.jpg', 30000, 95, '2025-06-01', '2025-07-01', 'TH True Milk'),
(32, N'Sữa Hạt Hạnh Nhân 500ml', 'sua_hat_hanh_nhan_500ml.jpg', 38000, 50, '2025-07-01', '2025-08-01', 'TH True Milk'),
(33, N'Sữa Protein Whey 1L', 'sua_protein_whey_1l.jpg', 27000, 125, '2025-08-01', '2025-09-01', 'TH True Milk'),
(34, N'Yogurt Vani 200g', 'yogurt_vani_200g.jpg', 15000, 150, '2025-09-01', '2025-10-01', 'TH True Milk'),
(35, N'Sữa Hạt Mè 500ml', 'sua_hat_me_500ml.jpg', 32000, 90, '2025-10-01', '2025-11-01', 'TH True Milk'),
(36, N'Sữa Hạt Dẻ Cười 1L', 'sua_hat_de_cuoi_1l.jpg', 24000, 100, '2025-11-01', '2025-12-01', 'TH True Milk'),
(37, N'Sữa Mỡ Cá Hồi 500ml', 'sua_mo_ca_hoi_500ml.jpg', 30000, 120, '2025-12-01', '2026-01-01', 'TH True Milk'),
(38, N'Sữa Hạt Điều 1L', 'sua_hat_dieu_1l.jpg', 26000, 130, '2026-01-01', '2026-02-01', 'TH True Milk'),
(39, N'Sữa Táo 500ml', 'sua_tao_500ml.jpg', 35000, 70, '2026-02-01', '2026-03-01', 'TH True Milk'),
(40, N'Sữa Hạt Lựu 1L', 'sua_hat_luu_1l.jpg', 28000, 110, '2026-03-01', '2026-04-01', 'TH True Milk'),
(41, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 22000, 130, '2026-04-01', '2026-05-01', 'TH True Milk'),
(42, N'Sữa Trà Xanh 500ml', 'sua_tra_xanh_500ml.jpg', 35000, 80, '2026-05-01', '2026-06-01', 'TH True Milk'),
(43, N'Sữa Hạt Đào 1L', 'sua_hat_dao_1l.jpg', 28000, 100, '2026-06-01', '2026-07-01', 'TH True Milk'),
(44, N'Sữa Dừa Mát Lạnh 500ml', 'sua_dua_mat_lanh_500ml.jpg', 32000, 70, '2026-07-01', '2026-08-01', 'TH True Milk'),
(45, N'Yogurt Mâm Xôi 200g', 'yogurt_mam_xoi_200g.jpg', 18000, 170, '2026-08-01', '2026-09-01', 'TH True Milk'),
(46, N'Sữa Vani 500ml', 'sua_vani_500ml.jpg', 30000, 90, '2026-09-01', '2026-10-01', 'TH True Milk'),
(47, N'Sữa Matcha Hạt Đậu 1L', 'sua_matcha_hat_dau_1l.jpg', 26000, 110, '2026-10-01', '2026-11-01', 'TH True Milk'),
(48, N'Sữa Cappuccino 500ml', 'sua_cappuccino_500ml.jpg', 35000, 80, '2026-11-01', '2026-12-01', 'TH True Milk'),
(49, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 30000, 95, '2026-12-01', '2027-01-01', 'TH True Milk'),
(50, N'Sữa Mocha Dẻo 500ml', 'sua_mocha_deo_500ml.jpg', 38000, 50, '2027-01-01', '2027-02-01', 'TH True Milk'),
(51, N'Sữa Protein Gainer 1L', 'sua_protein_gainer_1l.jpg', 27000, 125, '2027-02-01', '2027-03-01', 'TH True Milk'),
(52, N'Yogurt Dưa Lưới 200g', 'yogurt_dua_luoi_200g.jpg', 15000, 150, '2027-03-01', '2027-04-01', 'TH True Milk'),
(53, N'Sữa Hạt Mè Đen 500ml', 'sua_hat_me_den_500ml.jpg', 32000, 90, '2027-04-01', '2027-05-01', 'TH True Milk'),
(54, N'Sữa Hạt Lựu Hồng 1L', 'sua_hat_luu_hong_1l.jpg', 24000, 100, '2027-05-01', '2027-06-01', 'TH True Milk'),
(55, N'Sữa Chocolate Nấm Linh Chi 500ml', 'sua_chocolate_nam_linh_chi_500ml.jpg', 30000, 120, '2027-06-01', '2027-07-01', 'TH True Milk'),
(56, N'Sữa Hạt Nho Đỏ 1L', 'sua_hat_nho_do_1l.jpg', 26000, 130, '2027-07-01', '2027-08-01', 'TH True Milk'),
(57, N'Sữa Dừa Đen 500ml', 'sua_dua_den_500ml.jpg', 35000, 70, '2027-08-01', '2027-09-01', 'TH True Milk'),
(58, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 28000, 110, '2027-09-01', '2027-10-01', 'TH True Milk'),
(59, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 35000, 75, '2027-10-01', '2027-11-01', 'TH True Milk'),
(60, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 26000, 120, '2027-11-01', '2027-12-01', 'TH True Milk'),
(61, N'Sữa Phô Mai 500ml', 'sua_pho_mai_500ml.jpg', 32000, 80, '2028-01-01', '2028-02-01', 'TH True Milk'),
(62, N'Sữa Gạo Lứt Hạt Sen 1L', 'sua_gao_lut_hat_sen_1l.jpg', 26000, 110, '2028-02-01', '2028-03-01', 'TH True Milk'),
(63, N'Sữa Mận 500ml', 'sua_man_500ml.jpg', 35000, 70, '2028-03-01', '2028-04-01', 'TH True Milk'),
(64, N'Sữa Dẻo Hạt Dưa 1L', 'sua_deo_hat_dua_1l.jpg', 28000, 100, '2028-04-01', '2028-05-01', 'TH True Milk'),
(65, N'Yogurt Vị Nho 200g', 'yogurt_vi_nho_200g.jpg', 18000, 170, '2028-05-01', '2028-06-01', 'TH True Milk'),
(66, N'Sữa Cacao 500ml', 'sua_cacao_500ml.jpg', 30000, 90, '2028-06-01', '2028-07-01', 'TH True Milk'),
(67, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 26000, 120, '2028-07-01', '2028-08-01', 'TH True Milk'),
(68, N'Sữa Hương Dừa 500ml', 'sua_huong_dua_500ml.jpg', 35000, 80, '2028-08-01', '2028-09-01', 'TH True Milk'),
(69, N'Sữa Gạo Lứt Hạt Sen 1L', 'sua_gao_lut_hat_sen_1l.jpg', 30000, 95, '2028-09-01', '2028-10-01', 'TH True Milk'),
(70, N'Sữa Vị Bạc Hà 500ml', 'sua_vi_bac_ha_500ml.jpg', 35000, 60, '2028-10-01', '2028-11-01', 'TH True Milk'),
(71, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 30000, 95, '2028-11-01', '2028-12-01', 'TH True Milk'),
(72, N'Yogurt Chanh Xanh 200g', 'yogurt_chanh_xanh_200g.jpg', 20000, 150, '2028-12-01', '2029-01-01', 'TH True Milk'),
(73, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 32000, 90, '2029-01-01', '2029-02-01', 'TH True Milk'),
(74, N'Sữa Hạt Dưa Lưới 1L', 'sua_hat_dua_luoi_1l.jpg', 24000, 100, '2029-02-01', '2029-03-01', 'TH True Milk'),
(75, N'Sữa Gạo Lứt Hạt Nha Đam 500ml', 'sua_gao_lut_hat_nha_dam_500ml.jpg', 30000, 120, '2029-03-01', '2029-04-01', 'TH True Milk'),
(76, N'Sữa Nha Đam Hạt Dưa Lưới 1L', 'sua_nha_dam_hat_dua_luoi_1l.jpg', 26000, 130, '2029-04-01', '2029-05-01', 'TH True Milk'),
(77, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 35000, 70, '2029-05-01', '2029-06-01', 'TH True Milk'),
(78, N'Sữa Hạt Cà Phê Sữa 1L', 'sua_hat_ca_phe_sua_1l.jpg', 28000, 110, '2029-06-01', '2029-07-01', 'TH True Milk'),
(79, N'Sữa Chocolate Hạt Nho Đỏ 500ml', 'sua_chocolate_hat_nho_do_500ml.jpg', 35000, 75, '2029-07-01', '2029-08-01', 'TH True Milk'),
(80, N'Sữa Mâm Xôi 1L', 'sua_mam_xoi_1l.jpg', 26000, 120, '2029-08-01', '2029-09-01', 'TH True Milk'),
(81, N'Sữa Hạt Dưa Lưới 500ml', 'sua_hat_dua_luoi_500ml.jpg', 32000, 80, '2029-09-01', '2029-10-01', 'TH True Milk'),
(82, N'Sữa Phô Mai 1L', 'sua_pho_mai_1l.jpg', 26000, 110, '2029-10-01', '2029-11-01', 'TH True Milk'),
(83, N'Sữa Chocolate Hạt Mâm Xôi 500ml', 'sua_chocolate_hat_mam_xoi_500ml.jpg', 30000, 130, '2029-11-01', '2029-12-01', 'TH True Milk'),
(84, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 28000, 140, '2029-12-01', '2030-01-01', 'TH True Milk'),
(85, N'Sữa Dừa Đen 500ml', 'sua_dua_den_500ml.jpg', 35000, 75, '2030-01-01', '2030-02-01', 'TH True Milk'),
(86, N'Sữa Hạt Cà Phê Sữa 1L', 'sua_hat_ca_phe_sua_1l.jpg', 26000, 110, '2030-02-01', '2030-03-01', 'TH True Milk'),
(87, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml.jpg', 32000, 80, '2030-03-01', '2030-04-01', 'TH True Milk'),
(88, N'Sữa Đào Mật Ong 1L', 'sua_dao_mat_ong_1l.jpg', 26000, 110, '2030-04-01', '2030-05-01', 'TH True Milk'),
(89, N'Sữa Mocha Dẻo 500ml', 'sua_mocha_deo_500ml.jpg', 30000, 90, '2030-05-01', '2030-06-01', 'TH True Milk'),
(90, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2030-06-01', '2030-07-01', 'TH True Milk'),
(91, N'Sữa Gạo Lứt 500ml', 'sua_gao_lut_500ml.jpg', 32000, 80, '2030-07-01', '2030-08-01', 'TH True Milk'),
(92, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 26000, 110, '2030-08-01', '2030-09-01', 'TH True Milk'),
(93, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 32000, 80, '2030-09-01', '2030-10-01', 'TH True Milk'),
(94, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2030-10-01', '2030-11-01', 'TH True Milk'),
(95, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 32000, 80, '2030-11-01', '2030-12-01', 'TH True Milk'),
(96, N'Sữa Chocolate Hạt Nha Đam 1L', 'sua_chocolate_hat_nha_dam_1l.jpg', 26000, 110, '2030-12-01', '2031-01-01', 'TH True Milk'),
(97, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 32000, 80, '2031-01-01', '2031-02-01', 'TH True Milk'),
(98, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 26000, 120, '2031-02-01', '2031-03-01', 'TH True Milk'),
(99, N'Sữa Dừa Mát Lạnh 500ml', 'sua_dua_mat_lanh_500ml.jpg', 32000, 80, '2031-03-01', '2031-04-01', 'TH True Milk'),
(100, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 26000, 110, '2031-04-01', '2031-05-01', 'TH True Milk'),
(101, N'Sữa Hạt Cà Phê Sữa 1L', 'sua_hat_ca_phe_sua_1l.jpg', 26000, 110, '2030-02-01', '2030-03-01', 'TH True Milk'),
(102, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml.jpg', 32000, 80, '2030-03-01', '2030-04-01', 'TH True Milk'),
(103, N'Sữa Đào Mật Ong 1L', 'sua_dao_mat_ong_1l.jpg', 26000, 110, '2030-04-01', '2030-05-01', 'TH True Milk'),
(104, N'Sữa Mocha Dẻo 500ml', 'sua_mocha_deo_500ml.jpg', 30000, 90, '2030-05-01', '2030-06-01', 'TH True Milk'),
(105, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2030-06-01', '2030-07-01', 'TH True Milk'),
(106, N'Sữa Gạo Lứt 500ml', 'sua_gao_lut_500ml.jpg', 32000, 80, '2030-07-01', '2030-08-01', 'TH True Milk'),
(107, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 26000, 110, '2030-08-01', '2030-09-01', 'TH True Milk'),
(108, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 32000, 80, '2030-09-01', '2030-10-01', 'TH True Milk'),
(109, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2030-10-01', '2030-11-01', 'TH True Milk'),
(110, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 32000, 80, '2030-11-01', '2030-12-01', 'TH True Milk'),
(111, N'Sữa Chocolate Hạt Nha Đam 1L', 'sua_chocolate_hat_nha_dam_1l.jpg', 26000, 110, '2030-12-01', '2031-01-01', 'TH True Milk'),
(112, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 32000, 80, '2031-01-01', '2031-02-01', 'TH True Milk'),
(113, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 26000, 120, '2031-02-01', '2031-03-01', 'TH True Milk'),
(114, N'Sữa Dừa Mát Lạnh 500ml', 'sua_dua_mat_lanh_500ml.jpg', 32000, 80, '2031-03-01', '2031-04-01', 'TH True Milk'),
(115, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 26000, 110, '2031-04-01', '2031-05-01', 'TH True Milk'),
(116, N'Sữa Hạt Cà Phê Sữa 1L', 'sua_hat_ca_phe_sua_1l.jpg', 26000, 110, '2031-05-01', '2031-06-01', 'TH True Milk'),
(117, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml.jpg', 32000, 80, '2031-06-01', '2031-07-01', 'TH True Milk'),
(118, N'Sữa Đào Mật Ong 1L', 'sua_dao_mat_ong_1l.jpg', 26000, 110, '2031-07-01', '2031-08-01', 'TH True Milk'),
(119, N'Sữa Mocha Dẻo 500ml', 'sua_mocha_deo_500ml.jpg', 30000, 90, '2031-08-01', '2031-09-01', 'TH True Milk'),
(120, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2031-09-01', '2031-10-01', 'TH True Milk'),
(121, N'Sữa Gạo Lứt 500ml', 'sua_gao_lut_500ml.jpg', 32000, 80, '2031-10-01', '2031-11-01', 'TH True Milk'),
(122, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 26000, 110, '2031-11-01', '2031-12-01', 'TH True Milk'),
(123, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 32000, 80, '2031-12-01', '2032-01-01', 'TH True Milk'),
(124, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2032-01-01', '2032-02-01', 'TH True Milk'),
(125, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 32000, 80, '2032-02-01', '2032-03-01', 'TH True Milk'),
(126, N'Sữa Chocolate Hạt Nha Đam 1L', 'sua_chocolate_hat_nha_dam_1l.jpg', 26000, 110, '2032-03-01', '2032-04-01', 'TH True Milk'),
(127, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 32000, 80, '2032-04-01', '2032-05-01', 'TH True Milk'),
(128, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 26000, 120, '2032-05-01', '2032-06-01', 'TH True Milk'),
(129, N'Sữa Dừa Mát Lạnh 500ml', 'sua_dua_mat_lanh_500ml.jpg', 32000, 80, '2032-06-01', '2032-07-01', 'TH True Milk'),
(130, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 26000, 110, '2032-07-01', '2032-08-01', 'TH True Milk'),
(131, N'Sữa Hạt Cà Phê Sữa 1L', 'sua_hat_ca_phe_sua_1l.jpg', 26000, 110, '2032-08-01', '2032-09-01', 'TH True Milk'),
(132, N'Sữa Hạt Mâm Xôi 500ml', 'sua_hat_mam_xoi_500ml.jpg', 32000, 80, '2032-09-01', '2032-10-01', 'TH True Milk'),
(133, N'Sữa Đào Mật Ong 1L', 'sua_dao_mat_ong_1l.jpg', 26000, 110, '2032-10-01', '2032-11-01', 'TH True Milk'),
(134, N'Sữa Mocha Dẻo 500ml', 'sua_mocha_deo_500ml.jpg', 30000, 90, '2032-11-01', '2032-12-01', 'TH True Milk'),
(135, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2032-12-01', '2033-01-01', 'TH True Milk'),
(136, N'Sữa Gạo Lứt 500ml', 'sua_gao_lut_500ml.jpg', 32000, 80, '2033-01-01', '2033-02-01', 'TH True Milk'),
(137, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 26000, 110, '2033-02-01', '2033-03-01', 'TH True Milk'),
(138, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 32000, 80, '2033-03-01', '2033-04-01', 'TH True Milk'),
(139, N'Sữa Hạt Mâm Xôi 1L', 'sua_hat_mam_xoi_1l.jpg', 35000, 60, '2033-04-01', '2033-05-01', 'TH True Milk'),
(140, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 32000, 80, '2033-05-01', '2033-06-01', 'TH True Milk'),
(141, N'Sữa Chocolate Hạt Nha Đam 1L', 'sua_chocolate_hat_nha_dam_1l.jpg', 26000, 110, '2033-06-01', '2033-07-01', 'TH True Milk'),
(142, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 32000, 80, '2033-07-01', '2033-08-01', 'TH True Milk'),
(143, N'Sữa Gạo Lứt 1L', 'sua_gao_lut_1l.jpg', 26000, 120, '2033-08-01', '2033-09-01', 'TH True Milk'),
(144, N'Sữa Dừa Mát Lạnh 1L', 'sua_dua_mat_lanh_1l.jpg', 28000, 100, '2032-01-01', '2032-02-01', 'TH True Milk'),
(145, N'Sữa Hạt Điều 500ml', 'sua_hat_dieu_500ml.jpg', 34000, 75, '2032-02-01', '2032-03-01', 'TH True Milk'),
(146, N'Sữa Chocolate Hạt Nha Đam 1L', 'sua_chocolate_hat_nha_dam_1l.jpg', 30000, 90, '2032-03-01', '2032-04-01', 'TH True Milk'),
(147, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 32000, 80, '2032-04-01', '2032-05-01', 'TH True Milk'),
(148, N'Sữa Mâm Xôi 1L', 'sua_mam_xoi_1l.jpg', 28000, 100, '2032-05-01', '2032-06-01', 'TH True Milk'),
(149, N'Sữa Hạt Điều Mật Ong 500ml', 'sua_hat_dieu_mat_ong_500ml.jpg', 34000, 75, '2032-06-01', '2032-07-01', 'TH True Milk'),
(150, N'Sữa Hạt Điều 1L', 'sua_hat_dieu_1l.jpg', 32000, 80, '2032-07-01', '2032-08-01', 'TH True Milk'),
(151, N'Sữa Dừa Mát Lạnh 500ml', 'sua_dua_mat_lanh_500ml.jpg', 30000, 90, '2032-08-01', '2032-09-01', 'TH True Milk'),
(152, N'Sữa Mocha Dẻo 1L', 'sua_mocha_deo_1l.jpg', 34000, 75, '2032-09-01', '2032-10-01', 'TH True Milk'),
(153, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2032-10-01', '2032-11-01', 'TH True Milk'),
(154, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2032-11-01', '2032-12-01', 'TH True Milk'),
(155, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2032-12-01', '2033-01-01', 'TH True Milk'),
(156, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2033-01-01', '2033-02-01', 'TH True Milk'),
(157, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2033-02-01', '2033-03-01', 'TH True Milk'),
(158, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2033-03-01', '2033-04-01', 'TH True Milk'),
(159, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2033-04-01', '2033-05-01', 'TH True Milk'),
(160, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2033-05-01', '2033-06-01', 'TH True Milk'),
(161, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2033-06-01', '2033-07-01', 'TH True Milk'),
(162, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2033-07-01', '2033-08-01', 'TH True Milk'),
(163, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2033-08-01', '2033-09-01', 'TH True Milk'),
(164, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2033-09-01', '2033-10-01', 'TH True Milk'),
(165, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2033-10-01', '2033-11-01', 'TH True Milk'),
(166, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2033-11-01', '2033-12-01', 'TH True Milk'),
(167, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2033-12-01', '2034-01-01', 'TH True Milk'),
(168, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2034-01-01', '2034-02-01', 'TH True Milk'),
(169, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2034-02-01', '2034-03-01', 'TH True Milk'),
(170, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2034-03-01', '2034-04-01', 'TH True Milk'),
(171, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2034-04-01', '2034-05-01', 'TH True Milk'),
(172, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2034-05-01', '2034-06-01', 'TH True Milk'),
(173, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2034-06-01', '2034-07-01', 'TH True Milk'),
(174, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2034-07-01', '2034-08-01', 'TH True Milk'),
(175, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2034-08-01', '2034-09-01', 'TH True Milk'),
(176, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2034-09-01', '2034-10-01', 'TH True Milk'),
(177, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2034-10-01', '2034-11-01', 'TH True Milk'),
(178, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2034-11-01', '2034-12-01', 'TH True Milk'),
(179, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2034-12-01', '2035-01-01', 'TH True Milk'),
(180, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2035-01-01', '2035-02-01', 'TH True Milk'),
(181, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2035-02-01', '2035-03-01', 'TH True Milk'),
(182, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2035-03-01', '2035-04-01', 'TH True Milk'),
(183, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2035-04-01', '2035-05-01', 'TH True Milk'),
(184, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2035-05-01', '2035-06-01', 'TH True Milk'),
(185, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2035-06-01', '2035-07-01', 'TH True Milk'),
(186, N'Sữa Hạt Điều Mật Ong 1L', 'sua_hat_dieu_mat_ong_1l.jpg', 32000, 80, '2035-07-01', '2035-08-01', 'TH True Milk'),
(187, N'Sữa Hạt Cà Phê Sữa 500ml', 'sua_hat_ca_phe_sua_500ml.jpg', 30000, 90, '2035-08-01', '2035-09-01', 'TH True Milk'),
(188, N'Sữa Hạt Nha Đam 1L', 'sua_hat_nha_dam_1l.jpg', 34000, 75, '2035-09-01', '2035-10-01', 'TH True Milk'),
(189, N'Sữa Hạt Mâm Xôi Mật Ong 500ml', 'sua_hat_mam_xoi_mat_ong_500ml.jpg', 32000, 80, '2035-10-01', '2035-11-01', 'TH True Milk'),
(190, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 30000, 90, '2035-11-01', '2035-12-01', 'TH True Milk'),
(191, N'Sữa Hạt Nha Đam 500ml', 'sua_hat_nha_dam_500ml.jpg', 34000, 75, '2035-12-01', '2036-01-01', 'TH True Milk'),
(192, N'Sữa Dừa Hạt Dưa Lưới 1L', 'sua_dua_hat_dua_luoi_1l.jpg', 28000, 105, '2032-01-01', '2032-02-01', 'TH True Milk'),
(193, N'Sữa Gạo Lứt Hạt Điều 500ml', 'sua_gao_lut_hat_dieu_500ml.jpg', 35000, 75, '2032-02-01', '2032-03-01', 'TH True Milk'),
(194, N'Sữa Hạt Cà Phê Đậm 1L', 'sua_hat_ca_phe_dam_1l.jpg', 26000, 120, '2032-03-01', '2032-04-01', 'TH True Milk'),
(195, N'Sữa Gạo Lứt Hạt Nha Đam 500ml', 'sua_gao_lut_hat_nha_dam_500ml.jpg', 28000, 100, '2032-04-01', '2032-05-01', 'TH True Milk'),
(196, N'Sữa Hạt Điều 1L', 'sua_hat_dieu_1l.jpg', 30000, 95, '2032-05-01', '2032-06-01', 'TH True Milk'),
(197, N'Sữa Chocolate Hạt Nha Đam 500ml', 'sua_chocolate_hat_nha_dam_500ml.jpg', 32000, 80, '2032-06-01', '2032-07-01', 'TH True Milk'),
(198, N'Sữa Hạt Cà Phê Sữa Đậm 1L', 'sua_hat_ca_phe_sua_dam_1l.jpg', 28000, 110, '2032-07-01', '2032-08-01', 'TH True Milk'),
(199, N'Sữa Gạo Lứt Hạt Điều Mật Ong 500ml', 'sua_gao_lut_hat_dieu_mat_ong_500ml.jpg', 33000, 70, '2032-08-01', '2032-09-01', 'TH True Milk'),
(200, N'Sữa Hạt Điều 1L', 'sua_hat_dieu_1l.jpg', 30000, 95, '2032-09-01', '2032-10-01', 'TH True Milk');
GO
INSERT INTO [Users] ([username], [password], [role]) VALUES 
('Admin', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'Admin'),
('NongHoangVu04', '$2a$10$ALO4bzEz7frQ0XHXyU3a/ehNCLg1MC2ROOWQNuoRs7tpNpwsYvVEO', 'Admin'),
('Employee001', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User'),
('convitcute', '$2a$10$roI7ElW8vMZ/aa/ndvev5ekg.szrhPLnsihszv5fyi1moKL5DNrN2', 'User');
GO
INSERT INTO [Flavor] ([taste], [user_id])
VALUES
(N'Ngọt', 1),
('Chocolate', 2),
('Vanilla', 1);
INSERT INTO [Unit] ([measurement_unit], [user_id])
VALUES
(N'Lít', 1),
('Milliliter', 2),
('Gam', 1);
GO
INSERT INTO [PackagingSpecification] ([packaging_type], [user_id])
VALUES
('Chai', 2),
(N'Hộp', 2),
('Lon', 2);
GO
INSERT INTO [ProductInfo] ([milk_id], [flavor_id], [brand], [volume], [unit_id], [origin], [composition], [packaging_id], [product_description], [user_id])
VALUES
(1, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa tươi', 1, N'Mô tả sữa tươi', 2),
(2, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate', 2, N'Mô tả sữa chocolate', 1),
(3, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dâu', 1, N'Mô tả sữa dâu', 2),
(4, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa UHT', 1, N'Mô tả sữa UHT', 1),
(5, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt', 3, N'Mô tả yogurt', 2),
(6, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa vanilla', 2, N'Mô tả sữa vanilla', 1),
(7, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa low-fat', 1, N'Mô tả sữa low-fat', 2),
(8, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa', 2, N'Mô tả sữa dừa', 1),
(9, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa bổ sung canxi', 1, N'Mô tả sữa bổ sung canxi', 2),
(10, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa cà phê', 2, N'Mô tả sữa cà phê', 1),
(11, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hữu cơ', 1, N'Mô tả sữa hữu cơ', 2),
(12, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt almond', 2, N'Mô tả sữa hạt almond', 1),
(13, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa protein', 1, N'Mô tả sữa protein', 2),
(14, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt việt quất', 3, N'Mô tả yogurt việt quất', 1),
(15, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dẻ cười', 2, N'Mô tả sữa hạt dẻ cười', 2),
(16, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa tươi nguyên chất', 1, N'Mô tả sữa tươi nguyên chất', 1),
(17, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đậu nành', 1, N'Mô tả sữa đậu nành', 2),
(21, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa đào', 2, N'Mô tả sữa đào', 2),
(18, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa lactose-free', 1, N'Mô tả sữa lactose-free', 1),
(19, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa caramel', 2, N'Mô tả sữa caramel', 2),
(20, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đậu nành', 1, N'Mô tả sữa đậu nành', 1),
(22, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo', 1, N'Mô tả sữa gạo', 1),
(23, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa matcha', 2, N'Mô tả sữa matcha', 2),
(24, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt dưa hấu', 1, N'Mô tả sữa hạt dưa hấu', 1),
(25, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa cacao', 1, N'Mô tả sữa cacao', 2),
(26, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nho', 2, N'Mô tả sữa hạt nho', 1),
(27, 3, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt dâu', 3, N'Mô tả yogurt dâu', 2),
(28, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dâu tây', 2, N'Mô tả sữa dâu tây', 1),
(29, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(30, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha', 2, N'Mô tả sữa mocha', 1),
(31, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt việt quất', 1, N'Mô tả sữa hạt việt quất', 2),
(32, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt hạnh nhân', 2, N'Mô tả sữa hạt hạnh nhân', 1),
(33, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa protein whey', 1, N'Mô tả sữa protein whey', 2),
(34, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt vani', 3, N'Mô tả yogurt vani', 1),
(35, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mè', 2, N'Mô tả sữa hạt mè', 2),
(36, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt dẻ cười', 1, N'Mô tả sữa hạt dẻ cười', 1),
(37, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mỡ cá hồi', 2, N'Mô tả sữa mỡ cá hồi', 2),
(38, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt điều', 1, N'Mô tả sữa hạt điều', 1),
(39, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa táo', 2, N'Mô tả sữa táo', 2),
(40, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt lựu', 1, N'Mô tả sữa hạt lựu', 1),
(41, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt', 1, N'Mô tả sữa gạo lứt', 2),
(42, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa trà xanh', 2, N'Mô tả sữa trà xanh', 1),
(43, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt đào', 1, N'Mô tả sữa hạt đào', 2),
(44, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dừa mát lạnh', 1, N'Mô tả sữa dừa mát lạnh', 1),
(45, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt mâm xôi', 3, N'Mô tả yogurt mâm xôi', 2),
(46, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa vani', 2, N'Mô tả sữa vani', 1),
(47, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa matcha hạt đậu', 1, N'Mô tả sữa matcha hạt đậu', 2),
(48, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa cappuccino', 2, N'Mô tả sữa cappuccino', 1),
(49, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(50, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha dẻo', 2, N'Mô tả sữa mocha dẻo', 1),
(51, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa protein gainer', 1, N'Mô tả sữa protein gainer', 2),
(52, 3, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt dưa lưới', 3, N'Mô tả yogurt dưa lưới', 1),
(53, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mè đen', 2, N'Mô tả sữa hạt mè đen', 2),
(54, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt lựu hồng', 1, N'Mô tả sữa hạt lựu hồng', 1),
(55, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate nấm linh chi', 2, N'Mô tả sữa chocolate nấm linh chi', 2),
(56, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt nho đỏ', 1, N'Mô tả sữa hạt nho đỏ', 1),
(57, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa đen', 2, N'Mô tả sữa dừa đen', 2),
(58, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt điều mật ong', 1, N'Mô tả sữa hạt điều mật ong', 1),
(59, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 2),
(60, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt', 1, N'Mô tả sữa gạo lứt', 1),
(61, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa phô mai', 2, N'Mô tả sữa phô mai', 2),
(62, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt hạt sen', 1, N'Mô tả sữa gạo lứt hạt sen', 1),
(63, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mận', 2, N'Mô tả sữa mận', 2),
(64, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa dẻo hạt dưa', 1, N'Mô tả sữa dẻo hạt dưa', 1),
(65, 2, 'TH True Milk', 0.2, 3, N'Việt Nam', N'Thành phần yogurt vị nho', 3, N'Mô tả yogurt vị nho', 2),
(66, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa cacao', 2, N'Mô tả sữa cacao', 1),
(67, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt nha đam', 1, N'Mô tả sữa hạt nha đam', 2),
(68, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hương dừa', 2, N'Mô tả sữa hương dừa', 1),
(69, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt hạt sen', 1, N'Mô tả sữa gạo lứt hạt sen', 2),
(70, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa vị bạc hà', 2, N'Mô tả sữa vị bạc hà', 1),
(71, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt nha đam', 1, N'Mô tả sữa hạt nha đam', 2),
(72, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần yogurt chanh xanh', 2, N'Mô tả yogurt chanh xanh', 2),
(73, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt điều', 1, N'Mô tả sữa hạt điều', 1),
(74, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dưa lưới', 2, N'Mô tả sữa hạt dưa lưới', 2),
(75, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa gạo lứt hạt nha đam', 2, N'Mô tả sữa gạo lứt hạt nha đam', 1),
(76, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa nha đam hạt dưa lưới', 1, N'Mô tả sữa nha đam hạt dưa lưới', 2),
(77, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(78, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 1, N'Mô tả sữa hạt cà phê sữa', 2),
(79, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate hạt nho đỏ', 2, N'Mô tả sữa chocolate hạt nho đỏ', 2),
(80, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mâm xôi', 1, N'Mô tả sữa mâm xôi', 1),
(81, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt dưa lưới', 2, N'Mô tả sữa hạt dưa lưới', 1),
(82, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa phô mai', 1, N'Mô tả sữa phô mai', 2),
(83, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa chocolate hạt mâm xôi', 2, N'Mô tả sữa chocolate hạt mâm xôi', 2),
(84, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt điều mật ong', 1, N'Mô tả sữa hạt điều mật ong', 1),
(85, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha dẻo', 2, N'Mô tả sữa mocha dẻo', 2),
(86, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 1),
(87, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa đào mật ong', 2, N'Mô tả sữa đào mật ong', 2),
(88, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mocha dẻo', 1, N'Mô tả sữa mocha dẻo', 1),
(89, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 2, N'Mô tả sữa hạt mâm xôi', 2),
(90, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đào mật ong', 1, N'Mô tả sữa đào mật ong', 1),
(91, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha dẻo', 2, N'Mô tả sữa mocha dẻo', 2),
(92, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 1),
(93, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 2),
(94, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 1),
(95, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 2),
(96, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 1),
(97, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 2),
(98, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt', 1, N'Mô tả sữa gạo lứt', 1),
(99, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa mát lạnh', 2, N'Mô tả sữa dừa mát lạnh', 2),
(100, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt nha đam', 1, N'Mô tả sữa hạt nha đam', 1),
(101, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 2),
(102, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 1),
(103, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa đào mật ong', 2, N'Mô tả sữa đào mật ong', 2),
(104, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa mocha dẻo', 1, N'Mô tả sữa mocha dẻo', 1),
(105, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 2, N'Mô tả sữa hạt mâm xôi', 2),
(106, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đào mật ong', 1, N'Mô tả sữa đào mật ong', 1),
(107, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha dẻo', 2, N'Mô tả sữa mocha dẻo', 2),
(108, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 1),
(109, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 2),
(110, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 1),
(111, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(112, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(113, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(114, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa gạo lứt', 1, N'Mô tả sữa gạo lứt', 2),
(115, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa dừa mát lạnh', 2, N'Mô tả sữa dừa mát lạnh', 1),
(116, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt nha đam', 1, N'Mô tả sữa hạt nha đam', 2),
(117, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(118, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(119, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(120, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(121, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(122, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(123, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(124, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa đào mật ong', 1, N'Mô tả sữa đào mật ong', 2),
(125, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa mocha dẻo', 2, N'Mô tả sữa mocha dẻo', 1),
(126, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(127, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(128, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(129, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(130, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(131, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(132, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(133, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(134, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(135, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(136, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(137, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(138, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(139, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(140, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(141, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(142, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(143, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(144, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(145, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(146, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(147, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(148, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(149, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(150, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(151, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(152, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(153, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(154, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(155, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(156, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(157, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(158, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(159, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(160, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(161, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(162, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(163, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(164, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(165, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(166, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(167, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(168, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(169, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(170, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(171, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(172, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(173, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(174, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(175, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(176, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(177, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(178, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(179, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(180, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(181, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(182, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(183, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(184, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(185, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(186, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(187, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(188, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(189, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(190, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(191, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(192, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(193, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(194, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2),
(195, 2, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt điều', 2, N'Mô tả sữa hạt điều', 1),
(196, 3, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa chocolate hạt nha đam', 1, N'Mô tả sữa chocolate hạt nha đam', 2),
(197, 1, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt cà phê sữa', 2, N'Mô tả sữa hạt cà phê sữa', 1),
(198, 2, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt cà phê đậm', 1, N'Mô tả sữa hạt cà phê đậm', 2),
(199, 3, 'TH True Milk', 0.5, 2, N'Việt Nam', N'Thành phần sữa hạt nha đam', 2, N'Mô tả sữa hạt nha đam', 1),
(200, 1, 'TH True Milk', 1.0, 1, N'Việt Nam', N'Thành phần sữa hạt mâm xôi', 1, N'Mô tả sữa hạt mâm xôi', 2);


SELECT * FROM MILK INNER JOIN PRODUCTINFO ON MILK.ID = PRODUCTINFO.MILK_ID
*/
SELECT * FROM Salary
INSERT INTO Salary (salary_type, salary_mount, status) VALUES ('B', 121414, 'Active')
SELECT *FROM UserDetails
ALTER TABLE Salary
add [status] nvarchar(10)
GO
UPDATE UserDetails SET status = 'Active' where id = 1 or id = 2
UPDATE Salary
SET [status] = N'Active'
WHERE id = 1 or id = 2
