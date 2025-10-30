SET IDENTITY_INSERT [projectz].[MstKategori] ON
;

INSERT INTO [projectz].[MstKategori] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'1', NULL, NULL, N'Fashion')
;

INSERT INTO [projectz].[MstKategori] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'2', NULL, NULL, N'Elektronik')
;

INSERT INTO [projectz].[MstKategori] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'3', NULL, NULL, N'Home Tools')
;

INSERT INTO [projectz].[MstKategori] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'4', NULL, NULL, N'Transport')
;

SET IDENTITY_INSERT [projectz].[MstKategori] OFF
;

    SET IDENTITY_INSERT [projectz].[MstSupplier] ON
;

INSERT INTO [projectz].[MstSupplier] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'1', NULL, NULL, N'PT FASHION SHOW')
;

INSERT INTO [projectz].[MstSupplier] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'2', NULL, NULL, N'PT GARUDA AIRLINES')
;

INSERT INTO [projectz].[MstSupplier] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'3', NULL, NULL, N'PT MAJU MUNDUR')
;

INSERT INTO [projectz].[MstSupplier] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'4', NULL, NULL, N'PT BAYAR SEKALI')
;

SET IDENTITY_INSERT [projectz].[MstSupplier] OFF
;

SET IDENTITY_INSERT [projectz].[MstProduk] ON
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'1', N'1', NULL, NULL, N'NIKE')
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'2', N'1', NULL, NULL, N'ARDILES')
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'3', N'2', NULL, NULL, N'TV SONY')
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'4', N'2', NULL, NULL, N'RADIO')
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'5', N'4', NULL, NULL, N'SEPEDA LISTRIK')
;

INSERT INTO [projectz].[MstProduk] ([CreatedBy], [CreatedDate], [ID], [IDKategoriProduk], [ModifiedBy], [ModifiedDate], [Nama]) VALUES (N'1', N'2025-10-28 20:54:52.000000', N'6', N'3', NULL, NULL, N'PISAU DAPUR')
;

SET IDENTITY_INSERT [projectz].[MstProduk] OFF
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'1', N'1')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'1', N'3')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'2', N'1')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'2', N'3')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'2', N'4')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'3', N'2')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'3', N'3')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'3', N'4')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'4', N'2')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'4', N'3')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'4', N'4')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'5', N'1')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'5', N'2')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'5', N'3')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'5', N'4')
;

INSERT INTO [projectz].[ProdukSupplier] ([IDProduk], [IDSupplier]) VALUES (N'6', N'1')
;