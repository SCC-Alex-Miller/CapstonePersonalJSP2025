-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 01, 2025 at 09:47 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `flashcard`
--
CREATE DATABASE IF NOT EXISTS `flashcard` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `flashcard`;

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `cardID` int(11) NOT NULL,
  `cardQuestion` varchar(256) NOT NULL,
  `cardImage` varchar(256) NOT NULL,
  `cardAnswer` varchar(256) NOT NULL,
  `fkPackID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pack`
--

CREATE TABLE `pack` (
  `packID` int(11) NOT NULL,
  `packName` varchar(256) NOT NULL,
  `fkPackCategoryID` int(11) NOT NULL,
  `packHighScore` int(11) NOT NULL,
  `packHighScoreTime` varchar(256) NOT NULL,
  `createdDate` date NOT NULL,
  `fkUserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `packcategory`
--

CREATE TABLE `packcategory` (
  `packCategoryID` int(11) NOT NULL,
  `packCategoryName` varchar(256) NOT NULL,
  `packCategoryCreatedDate` date NOT NULL,
  `fkUserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportID` int(11) NOT NULL,
  `reportType` varchar(256) NOT NULL,
  `reportActive` tinyint(1) NOT NULL,
  `reportUserNotes` varchar(256) NOT NULL,
  `reportAdminNotes` varchar(256) NOT NULL,
  `reportCreatedByID` int(11) NOT NULL,
  `reportedUserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `username` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `reportStrikes` int(11) NOT NULL,
  `activeStatus` tinyint(1) NOT NULL,
  `adminMessage` varchar(256) DEFAULT NULL,
  `createdDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `username`, `email`, `password`, `isAdmin`, `reportStrikes`, `activeStatus`, `adminMessage`, `createdDate`) VALUES
(1, 'lhsamiller', 'lhsamiller@gmail.com', '11b51d3dfca351b2c722f92a502c3b74$4096$312e2c631fa216506f71cacec8113404b7ace9d26df37a4a9acf2084ab530c06', 0, 0, 1, '', '2025-03-24 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`cardID`),
  ADD KEY `card_fk_pack` (`fkPackID`);

--
-- Indexes for table `pack`
--
ALTER TABLE `pack`
  ADD PRIMARY KEY (`packID`),
  ADD KEY `pack_fk_user` (`fkUserID`),
  ADD KEY `pack_fk_packCategory` (`fkPackCategoryID`);

--
-- Indexes for table `packcategory`
--
ALTER TABLE `packcategory`
  ADD PRIMARY KEY (`packCategoryID`),
  ADD KEY `packCategory_fk_user` (`fkUserID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportID`),
  ADD KEY `report_fk_user_reportedUserID` (`reportedUserID`),
  ADD KEY `report_fk_user_reportCreatedByID` (`reportCreatedByID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `card`
--
ALTER TABLE `card`
  MODIFY `cardID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pack`
--
ALTER TABLE `pack`
  MODIFY `packID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `packcategory`
--
ALTER TABLE `packcategory`
  MODIFY `packCategoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `card`
--
ALTER TABLE `card`
  ADD CONSTRAINT `card_fk_pack` FOREIGN KEY (`fkPackID`) REFERENCES `pack` (`packID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pack`
--
ALTER TABLE `pack`
  ADD CONSTRAINT `pack_fk_packCategory` FOREIGN KEY (`fkPackCategoryID`) REFERENCES `packcategory` (`packCategoryID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pack_fk_user` FOREIGN KEY (`fkUserID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `packcategory`
--
ALTER TABLE `packcategory`
  ADD CONSTRAINT `packCategory_fk_user` FOREIGN KEY (`fkUserID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_fk_user_reportCreatedByID` FOREIGN KEY (`reportCreatedByID`) REFERENCES `user` (`userID`),
  ADD CONSTRAINT `report_fk_user_reportedUserID` FOREIGN KEY (`reportedUserID`) REFERENCES `user` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
