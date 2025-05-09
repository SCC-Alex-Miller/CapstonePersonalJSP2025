-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 05, 2025 at 08:43 PM
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
  `cardImage` varchar(256) DEFAULT NULL,
  `cardAnswer` varchar(256) NOT NULL,
  `fkPackID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`cardID`, `cardQuestion`, `cardImage`, `cardAnswer`, `fkPackID`) VALUES
(4, 'Who was the first president of the United States?', NULL, 'George Washington', 7),
(7, 'What cities did the U.S. drop atomic bombs on in WW2?', NULL, 'Hiroshima and Nagasaki', 7),
(8, 'Columbus sailed the ocean blue...', NULL, 'in 1492!', 7),
(44, 'Who was the first president of the United States?', NULL, 'George Washington', 16),
(45, 'What cities did the U.S. drop atomic bombs on in WW2?', NULL, 'Hiroshima and Nagasaki', 16),
(46, 'Columbus sailed the ocean blue...', NULL, 'in 1492!', 16);

-- --------------------------------------------------------

--
-- Table structure for table `pack`
--

CREATE TABLE `pack` (
  `packID` int(11) NOT NULL,
  `packName` varchar(256) NOT NULL,
  `fkPackCategoryID` int(11) NOT NULL,
  `isPublic` tinyint(1) NOT NULL DEFAULT 0,
  `packHighScore` int(11) NOT NULL,
  `packHighScoreTime` varchar(256) NOT NULL,
  `createdDate` date NOT NULL,
  `fkUserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pack`
--

INSERT INTO `pack` (`packID`, `packName`, `fkPackCategoryID`, `isPublic`, `packHighScore`, `packHighScoreTime`, `createdDate`, `fkUserID`) VALUES
(7, 'History Quiz', 8, 1, 0, '00:00:00', '2025-04-19', 2),
(8, 'History Test', 8, 0, 0, '00:00:00', '2025-04-20', 2),
(16, 'History Pop Quiz', 8, 1, 0, '00:00:00', '2025-04-19', 3);

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

--
-- Dumping data for table `packcategory`
--

INSERT INTO `packcategory` (`packCategoryID`, `packCategoryName`, `packCategoryCreatedDate`, `fkUserID`) VALUES
(8, 'History', '2025-04-01', 2),
(10, 'Math', '2025-04-20', 2),
(24, 'History', '2025-04-01', 3);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportID` int(11) NOT NULL,
  `reportType` varchar(256) NOT NULL,
  `reportActive` tinyint(1) NOT NULL,
  `reportUserNotes` varchar(256) NOT NULL,
  `reportAdminNotes` varchar(256) DEFAULT NULL,
  `reportCreatedByID` int(11) NOT NULL,
  `reportedUserID` int(11) NOT NULL,
  `reportedPackID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`reportID`, `reportType`, `reportActive`, `reportUserNotes`, `reportAdminNotes`, `reportCreatedByID`, `reportedUserID`, `reportedPackID`) VALUES
(1, 'Misinformation', 1, 'Incorrect Info', NULL, 3, 2, 7),
(2, 'Violent', 1, 'Atomic bombs should not be discussed.', NULL, 3, 2, 7),
(3, 'Spam', 1, 'Stole my pack.', NULL, 2, 3, 16);

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
(2, 'lhsamiller', 'lhsamiller@gmail.com', 'aca483d8c5229c79628a679a31ab5f28$4096$fda1707f582f0cfb6ad436ad03de6a027eebb9b20ed5248aa8bb0715a19f11ca', 1, 0, 1, '', '2025-04-01 00:00:00'),
(3, 'Testy', 'testy.mctesterson@test.com', '6c74391e0c681f1a72b73c86e4165269$4096$91c76ac84a9c2ec1fb3532c63ecdcfdbcfecbbf081494c246f758e78eb3915f7', 0, 0, 1, '', '2025-04-21 00:00:00');

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
  ADD KEY `report_fk_user_reportCreatedByID` (`reportCreatedByID`),
  ADD KEY `report_fk_pack_reportedPackID` (`reportedPackID`);

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
  MODIFY `cardID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `pack`
--
ALTER TABLE `pack`
  MODIFY `packID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `packcategory`
--
ALTER TABLE `packcategory`
  MODIFY `packCategoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
  ADD CONSTRAINT `report_fk_pack_reportedPackID` FOREIGN KEY (`reportedPackID`) REFERENCES `pack` (`packID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `report_fk_user_reportCreatedByID` FOREIGN KEY (`reportCreatedByID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `report_fk_user_reportedUserID` FOREIGN KEY (`reportedUserID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
