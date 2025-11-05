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

SET IDENTITY_INSERT [projectz].[MstGroupMenu] ON
;
INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:45:57.000000', N'1', NULL, NULL, N'User Management', N'Untuk User Management')
;

INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', NULL, NULL, N'Artikel', N'Untuk Artikel aneh aneh')
;
INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', NULL, NULL, N'Buat Delete', N'Buat Delete Doank Sih ....')
;
INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'4', NULL, NULL, N'Purchase', N'Buat Purchase')
;

SET IDENTITY_INSERT [projectz].[MstGroupMenu] OFF
;


SET IDENTITY_INSERT [projectz].[MstMenu] ON
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'1', N'1', NULL, NULL, N'Group-Menu', N'/group-menu', N'Untuk Menu Konfigurasi Group Menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', N'1', NULL, NULL, N'Menu', N'/menu', N'Untuk Menu Konfigurasi Menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', N'1', NULL, NULL, N'Akses', N'/akses', N'Untuk Menu Konfigurasi Akses')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'4', N'1', NULL, NULL, N'User', N'/user', N'Untuk Menu Konfigurasi User')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'5', N'2', NULL, NULL, N'Artikel-1', N'/artikel-1', N'Untuk Konfigurasi Artikel 1')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'6', N'2', NULL, NULL, N'Artikel-2', N'/artikel-2', N'Untuk Konfigurasi Artikel 2')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'7', N'2', NULL, NULL, N'Artikel-3', N'/artikel-3', N'Untuk Konfigurasi Artikel 3')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'8', N'3', NULL, NULL, N'Kategori', N'/kategoriproduk', N'Untuk Kategori Produk')
    

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'9', N'3', NULL, NULL, N'Supplier', N'/supplier', N'Untuk Supplier')
    

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'10', N'3', NULL, NULL, N'Produk', N'/produk', N'Untuk Produk')
    

SET IDENTITY_INSERT [projectz].[MstMenu] OFF
;

SET IDENTITY_INSERT [projectz].[MstAkses] ON
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'1', NULL, NULL, N'Admin', N'Untuk Administrator atau Super User')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'2', NULL, NULL, N'Member', N'Untuk Member Setelah Registrasi')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-05-21 20:46:18.000000', N'3', NULL, NULL, N'Member Test', N'Untuk Member Setelah Registrasi Testing aja')
;

SET IDENTITY_INSERT [projectz].[MstAkses] OFF
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'1')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'2')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'3')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'4')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'7')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'8')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'9')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'10')
;

SET IDENTITY_INSERT [projectz].[MstUser] ON
;

INSERT INTO [projectz].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [ID], [IDAkses], [ModifiedBy], [ModifiedDate], [Username], [NoHp], [OTP], [Password], [TokenEstafet], [NamaLengkap], [Email], [LinkImage], [PathImage], [Alamat]) VALUES (N'1', N'1995-12-12', N'1', N'2025-05-21 21:03:38.464776', N'1', N'1', N'1', N'2025-05-21 21:03:46.874860', N'paul.123', N'081213141321', N'888890', N'$2a$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEbrN6.', NULL, N'Paul Malau', N'poll.chihuy@gmail.com', NULL, NULL, N'Bor Bor Bor Bor Bor ')
;

INSERT INTO [projectz].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [ID], [IDAkses], [ModifiedBy], [ModifiedDate], [Username], [NoHp], [OTP], [Password], [TokenEstafet], [NamaLengkap], [Email], [LinkImage], [PathImage], [Alamat]) VALUES (N'1', N'2001-02-28', N'1', N'2025-05-21 21:03:38.464776', N'2', N'2', N'1', N'2025-05-21 21:03:46.874860', N'kurtcobaan.123', N'08121114132', N'$13$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEN666', N'$13$11$WGf7TWx9qZ74TGXdolGFROkuZJqrtil/8ryVy5cLXkfHfZvEbrN6.', NULL, N'Kurt Cobaan', N'kurt.cobaan@gmail.com', NULL, NULL, N'Jakarta Jakarta Jakarta Jakarta Jakarta Jakarta')
;

SET IDENTITY_INSERT [projectz].[MstUser] OFF
;