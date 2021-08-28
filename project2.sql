-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 28, 2021 at 06:40 AM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project2`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `akun`
-- (See below for the actual view)
--
CREATE TABLE `akun` (
`ID` int(10)
,`PASSWORD` varchar(50)
,`Username` varchar(25)
,`Nama` varchar(50)
,`jenisKelamin` varchar(10)
,`TempatLahir` varchar(50)
,`TanggalLahir` date
,`Alamat` text
);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(5) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `noperasi` varchar(50) NOT NULL,
  `terminal` varchar(100) NOT NULL,
  `kota` varchar(50) NOT NULL,
  `fasilitas` varchar(50) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `tarif` int(100) NOT NULL,
  `tanggal` date DEFAULT NULL,
  `nmPesan` varchar(50) DEFAULT NULL,
  `total` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `nama`, `brand`, `noperasi`, `terminal`, `kota`, `fasilitas`, `jumlah`, `tarif`, `tanggal`, `nmPesan`, `total`) VALUES
(1, 'Siang', 'Pahala', 'P03', 'Pasar Senin', 'Jakarta', 'Bisnis1', 2, 75000, '2021-08-24', 'Aldi', 150000),
(2, 'Siang', 'Pahala', 'P03', 'Pasar Senin', 'Jakarta', 'Bisnis1', 2, 75000, '2021-08-11', 'susan', 150000),
(3, 'Malam', 'Sumber Alam', 'SA01', 'Cicahem', 'Bandung', 'Ekonomi', 3, 125000, '2021-08-19', 'Aldi', 375000),
(4, 'Malam', 'Maju Prima', 'MJ01', 'Gelora Surabaya', 'Surabaya', 'Eksekutif', 5, 150000, '2021-08-31', 'Ade', 750000),
(5, 'Malam', 'Sumber Alam', 'SA01', 'Cicahem', 'Bandung', 'Ekonomi', 3, 125000, '2021-08-10', 'Ardi', 375000),
(6, 'Pagi', 'Pahala', 'Bisnis', 'Cicahem', 'Jakarta', 'Bisnis', 4, 60000, '2021-09-08', 'Ari', 240000),
(7, 'Siang', 'Bejeu', 'BJ01', 'Cicahem', 'Bandung', 'Bisnis', 3, 110000, '2021-09-08', 'Ali', 330000),
(8, 'Malam', 'Maju Prima', 'Eksekutif', 'Cicahem', 'Jakarta', 'Bisnis', 7, 150000, '2021-09-08', 'Udin', 1050000);

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `id` int(11) NOT NULL,
  `brand` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`id`, `brand`) VALUES
(2, 'Pahala'),
(3, 'Sumber Alam'),
(4, 'Maju Prima'),
(5, 'Bejeu'),
(6, 'Doa Ibu'),
(7, 'Damri'),
(8, 'Sumber Rejeki'),
(9, 'Arimbi'),
(11, 'Sumber Alam'),
(12, 'Sahabat'),
(13, 'Tunggal Jaya');

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id` int(11) NOT NULL,
  `kBrand` varchar(50) NOT NULL,
  `kelas` varchar(50) NOT NULL,
  `fasilitas` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id`, `kBrand`, `kelas`, `fasilitas`) VALUES
(1, 'Bejeu', 'Bisnis', 'AC, Toilet, Snack'),
(2, 'Sumber Alam', 'Ekonomi', 'AC	'),
(3, 'Maju Prima', 'Eksekutif', 'AC, Toilet, Snack, Wifi'),
(4, 'Pahala', 'Ekonomi', 'AC, Toilet'),
(5, 'Pahala', 'Bisnis', 'AC, Toilet, Snack'),
(8, 'Damri', 'Bisnis', 'AC, Toilet, Snack'),
(10, 'Arimbi', 'Eksekutif', 'AC, Snack, Cathering 2x, Wifi, Toilet'),
(11, 'Maju Prima', 'Bisnis', 'AC, Wifi, Snack');

-- --------------------------------------------------------

--
-- Table structure for table `kendaraan`
--

CREATE TABLE `kendaraan` (
  `id` int(5) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `noperasi` varchar(50) NOT NULL,
  `terminal` varchar(100) NOT NULL,
  `kota` varchar(50) NOT NULL,
  `fasilitas` varchar(50) NOT NULL,
  `status` varchar(25) NOT NULL,
  `tarif` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kendaraan`
--

INSERT INTO `kendaraan` (`id`, `nama`, `brand`, `noperasi`, `terminal`, `kota`, `fasilitas`, `status`, `tarif`) VALUES
(1, 'Siang', 'Bejeu', 'Bisnis', 'Bandung', 'Cicahem', 'BJ01', 'Kerusakan Ringan', 110000),
(2, 'Pagi', 'Pahala', 'Bisnis', 'Jakarta', 'Kebun Jeruk', 'P02', 'Ready', 60000),
(4, 'Malam', 'Sumber Alam', 'Ekonomi', 'Bandung', 'Cicahem', 'SA01', 'Ready', 125000),
(5, 'Malam', 'Maju Prima', 'Eksekutif', 'Bandung', 'Cicahem', 'MP01', 'Ready', 105000),
(6, 'Malam', 'Maju Prima', 'Eksekutif', 'Surabaya', 'Gelora Surabaya', 'MJ01', 'Reasy', 150000),
(8, 'Pariwisata', 'Pahala', 'Ekonomi', 'Jakarta', 'Pasar Senin', 'PP01', 'Ready', 100000),
(9, 'Siang', 'Pahala', 'Ekonomi', 'Jakarta', 'Pasar Senin', 'PA03', 'Ready', 175000);

-- --------------------------------------------------------

--
-- Table structure for table `kota`
--

CREATE TABLE `kota` (
  `id` int(3) NOT NULL,
  `kota` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kota`
--

INSERT INTO `kota` (`id`, `kota`) VALUES
(1, 'Jakarta'),
(2, 'Bandung'),
(4, 'Semarang'),
(5, 'Surabaya'),
(6, 'Banten'),
(7, 'Lampung'),
(8, 'Medan'),
(9, 'Padang'),
(10, 'Riau'),
(12, 'Jambi'),
(13, 'Palembang'),
(14, 'Depok'),
(15, 'Magelang');

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `id` int(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jk` varchar(10) NOT NULL,
  `tglLahir` date DEFAULT NULL,
  `alamat` text,
  `tempatLahir` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id`, `nama`, `jk`, `tglLahir`, `alamat`, `tempatLahir`) VALUES
(1, 'Super Admin', 'Perempuan', '2021-08-09', 'Super Admin', 'admin'),
(2, 'Susanti', 'Laki-Laki', '2021-08-06', 'Sukamasju', 'Jakarta'),
(3, 'Super Admin', 'Laki-Laki', '2021-08-03', 'Kencana', 'BDG'),
(4, 'Mahmud', 'Laki-Laki', '2021-08-01', 'Pasir Maju', 'Bengkulu'),
(5, 'Sumarudin', 'Perempuan', '1999-10-18', 'jl. kencana', 'Sukamakmur'),
(6, 'Marni', 'Perempuan', '1990-03-04', 'Samrinda', 'Binjai'),
(7, 'Muhammad', 'Perempuan', '2006-08-22', 'Ps.Kemis', 'Tangerang'),
(8, 'aldi', 'Laki-Laki', '2006-08-22', 'Ps.Kemis', 'Tangerang'),
(9, 'Muhammad Aldi Ardiansyah', 'Laki-Laki', '1999-11-14', 'Ps. Kemid Tangerang', 'Tangerang'),
(10, 'Muhammad Aldi', 'Laki-Laki', '1995-09-03', 'Pasar Baru', 'Jakarta'),
(11, 'Nunu', 'Perempuan', '2002-11-18', 'Jl. Sukse Jakarta Timur', 'Bandung'),
(12, 'Nunu', 'Perempuan', '2002-11-18', 'Jl. Sukse Jakarta Timur', 'Bandung');

-- --------------------------------------------------------

--
-- Table structure for table `terminal`
--

CREATE TABLE `terminal` (
  `id` int(3) NOT NULL,
  `terminal` varchar(50) NOT NULL,
  `kota` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `terminal`
--

INSERT INTO `terminal` (`id`, `terminal`, `kota`) VALUES
(3, 'Cicahem', 'Bandung'),
(4, 'Kebun Jeruk', 'Jakarta'),
(5, 'Pasar Senin', 'Jakarta'),
(6, 'Sultan Hasanudin', 'Banten'),
(7, 'Gelora Surabaya', 'Surabaya'),
(8, 'Rajabasa', 'Lampung');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) NOT NULL,
  `user` varchar(25) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `user`, `pass`) VALUES
(1, 'admin', 'admin'),
(2, 'susan', 'susan'),
(4, 'mahmud', 'mahmud'),
(5, 'sumsum', 'sumsum'),
(6, 'marni', 'marni'),
(9, 'aldi', 'aldi'),
(10, 'maldi', 'maldi'),
(11, 'nun', '12345');

-- --------------------------------------------------------

--
-- Structure for view `akun`
--
DROP TABLE IF EXISTS `akun`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `akun`  AS  select `user`.`id` AS `ID`,`user`.`pass` AS `PASSWORD`,`user`.`user` AS `Username`,`pengguna`.`nama` AS `Nama`,`pengguna`.`jk` AS `jenisKelamin`,`pengguna`.`tempatLahir` AS `TempatLahir`,`pengguna`.`tglLahir` AS `TanggalLahir`,`pengguna`.`alamat` AS `Alamat` from (`user` join `pengguna` on((`user`.`id` = `pengguna`.`id`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kota`
--
ALTER TABLE `kota`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `terminal`
--
ALTER TABLE `terminal`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `brand`
--
ALTER TABLE `brand`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `kendaraan`
--
ALTER TABLE `kendaraan`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `kota`
--
ALTER TABLE `kota`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `terminal`
--
ALTER TABLE `terminal`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
