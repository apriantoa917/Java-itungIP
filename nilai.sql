-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 25, 2019 at 07:15 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nilai`
--

-- --------------------------------------------------------

--
-- Table structure for table `tabel_ip`
--

CREATE TABLE `tabel_ip` (
  `semester` int(1) NOT NULL,
  `total_sks` int(11) NOT NULL,
  `total_bobot` double NOT NULL,
  `total_nilai_akhir` double NOT NULL,
  `ips` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tabel_ip`
--

INSERT INTO `tabel_ip` (`semester`, `total_sks`, `total_bobot`, `total_nilai_akhir`, `ips`) VALUES
(1, 5, 7, 17.5, 3.5),
(2, 1, 4, 4, 4),
(3, 0, 0, 0, 0),
(4, 0, 0, 0, 0),
(5, 0, 0, 0, 0),
(6, 0, 0, 0, 0),
(7, 0, 0, 0, 0),
(8, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tabel_ipk`
--

CREATE TABLE `tabel_ipk` (
  `id` int(11) NOT NULL,
  `ipk` double NOT NULL,
  `cumlaude` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tabel_ipk`
--

INSERT INTO `tabel_ipk` (`id`, `ipk`, `cumlaude`) VALUES
(1, 3.75, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tabel_nilai`
--

CREATE TABLE `tabel_nilai` (
  `semester` int(1) NOT NULL,
  `status_praktikum` char(1) NOT NULL DEFAULT '0',
  `mata_kuliah` varchar(50) NOT NULL,
  `sks` int(1) NOT NULL,
  `uts` double NOT NULL,
  `puts` int(11) NOT NULL,
  `kuts` double NOT NULL,
  `uas` double NOT NULL,
  `puas` int(11) NOT NULL,
  `kuas` double NOT NULL,
  `tugas` double NOT NULL,
  `ptugas` int(11) NOT NULL,
  `ktugas` double NOT NULL,
  `modul` double NOT NULL,
  `pmodul` int(11) NOT NULL,
  `kmodul` double NOT NULL,
  `kumulasi` double NOT NULL,
  `huruf` varchar(2) NOT NULL,
  `bobot` double NOT NULL,
  `nilai_akhir` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tabel_nilai`
--

INSERT INTO `tabel_nilai` (`semester`, `status_praktikum`, `mata_kuliah`, `sks`, `uts`, `puts`, `kuts`, `uas`, `puas`, `kuas`, `tugas`, `ptugas`, `ktugas`, `modul`, `pmodul`, `kmodul`, `kumulasi`, `huruf`, `bobot`, `nilai_akhir`) VALUES
(1, '0', 'data 1', 3, 75, 30, 22.5, 95, 30, 28.5, 65, 40, 26, 0, 0, 0, 77, 'B+', 3.5, 10.5),
(1, '0', 'data 2', 2, 100, 30, 30, 65, 30, 19.5, 73, 40, 29.2, 0, 0, 0, 78.7, 'B+', 3.5, 7),
(2, '1', 'data 3', 1, 96, 30, 28.8, 75, 30, 22.5, 85, 20, 17, 100, 20, 20, 88.3, 'A', 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `temporary`
--

CREATE TABLE `temporary` (
  `id` int(11) NOT NULL DEFAULT '1',
  `ts` varchar(100) NOT NULL,
  `ti` int(11) NOT NULL,
  `td` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temporary`
--

INSERT INTO `temporary` (`id`, `ts`, `ti`, `td`) VALUES
(1, '', 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tabel_ip`
--
ALTER TABLE `tabel_ip`
  ADD PRIMARY KEY (`semester`);

--
-- Indexes for table `tabel_nilai`
--
ALTER TABLE `tabel_nilai`
  ADD PRIMARY KEY (`mata_kuliah`),
  ADD KEY `semester` (`semester`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tabel_nilai`
--
ALTER TABLE `tabel_nilai`
  ADD CONSTRAINT `tabel_nilai_ibfk_1` FOREIGN KEY (`semester`) REFERENCES `tabel_ip` (`semester`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
