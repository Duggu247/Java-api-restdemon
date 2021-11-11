-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 11, 2021 at 01:40 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `online-food-order`
--
CREATE DATABASE IF NOT EXISTS `online-food-order` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `online-food-order`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(100) NOT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `registered_on` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_name`, `mobile`, `email`, `password`, `registered_on`) VALUES
(1, 'John william', NULL, 'johnwilliam24@gmail.com', 'asdjfklhankmdsfaljhasdf209', '2021-11-03 16:21:28'),
(2, 'Sakthi', '1234567890', 'sakthivelr247@gmail.com', 'IvnXGDN40MKJJSpQl9fUJg==', '2021-11-10 00:00:00'),
(3, 'John', '1234567890', 'John@gmail.com', 'IvnXGDN40MKJJSpQl9fUJg==', '2021-11-10 12:38:27');

-- --------------------------------------------------------

--
-- Table structure for table `bill_items`
--

DROP TABLE IF EXISTS `bill_items`;
CREATE TABLE `bill_items` (
  `item_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `dish_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill_items`
--

INSERT INTO `bill_items` (`item_id`, `bill_id`, `dish_id`, `quantity`, `price`) VALUES
(1, 1, 4, 1, 240),
(2, 2, 1, 1, 100.5),
(3, 2, 2, 2, 100.5),
(4, 3, 1, 1, 155),
(5, 3, 2, 2, 240),
(6, 4, 1, 1, 155),
(7, 4, 2, 2, 240),
(8, 5, 3, 1, 155),
(9, 5, 5, 2, 240);

-- --------------------------------------------------------

--
-- Table structure for table `coupens`
--

DROP TABLE IF EXISTS `coupens`;
CREATE TABLE `coupens` (
  `coupen_id` int(11) NOT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `discount` float NOT NULL DEFAULT 0,
  `discount_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0-percentage, 1-amount',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0-expired, 1-alive',
  `created_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `coupens`
--

INSERT INTO `coupens` (`coupen_id`, `expiry_date`, `discount`, `discount_type`, `status`, `created_on`) VALUES
(1, '2021-11-11 16:35:01', 40, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `customer_mobile` varchar(50) NOT NULL,
  `primary_delivery_address` text NOT NULL,
  `registered_on` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `email`, `password`, `customer_name`, `customer_mobile`, `primary_delivery_address`, `registered_on`) VALUES
(1, 'abc@gmail.com', 'asdkjfhad09eraksdf', 'Shiva', '911234456789', 'No.7,Subam street, Velachery, Chennai-48', '2021-11-03 16:23:21'),
(2, 'def@gmail.com', 'asdkjfhad09eraksdf', 'Komal', '0442349874', 'No.17,chetti street, ramnagar, cbe-526352', '2021-11-03 16:24:46'),
(3, 'John@gmail.com', 'IvnXGDN40MKJJSpQl9fUJg==', 'John', '1234567890', 'no.1, New Colony, Munnar.', '2021-11-10 01:14:29'),
(4, 'testuser@gmail.com', 'IvnXGDN40MKJJSpQl9fUJg==', 'TestUser', '1234567890', 'Chennai', '2021-11-11 04:47:05'),
(5, 'Sugans@gmail.com', 'IvnXGDN40MKJJSpQl9fUJg==', 'Suganya', '1234567890', 'Chennai', '2021-11-11 05:13:00');

-- --------------------------------------------------------

--
-- Table structure for table `customer_bills`
--

DROP TABLE IF EXISTS `customer_bills`;
CREATE TABLE `customer_bills` (
  `bill_id` int(11) NOT NULL,
  `bill_amount` float NOT NULL,
  `actual_amount` float NOT NULL,
  `discount` float NOT NULL DEFAULT 0,
  `payment_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-online,1-cod'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer_bills`
--

INSERT INTO `customer_bills` (`bill_id`, `bill_amount`, `actual_amount`, `discount`, `payment_type`) VALUES
(1, 200, 240, 40, 0),
(2, 200.5, 240.5, 50.5, 0),
(3, 200.5, 240.5, 50.5, 0),
(4, 200.5, 240.5, 50.5, 0),
(5, 200.5, 240.5, 50.5, 0);

-- --------------------------------------------------------

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes` (
  `dish_id` int(11) NOT NULL,
  `dish_name` varchar(150) NOT NULL,
  `food_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-veg,1-nv',
  `category` tinyint(4) DEFAULT NULL COMMENT '0-rice bowl, 1- Gravy,2-Roti,3-soup, 4-dry'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dishes`
--

INSERT INTO `dishes` (`dish_id`, `dish_name`, `food_type`, `category`) VALUES
(1, 'Madras Meals', 0, 0),
(2, 'Covai Meals', 0, 0),
(3, 'Chicken Biriyani', 1, 0),
(4, 'Chicken Biriyani', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `hotel_id` int(11) NOT NULL,
  `hotel_name` varchar(100) NOT NULL,
  `location_id` int(11) NOT NULL,
  `email` varchar(250) NOT NULL,
  `mobile` int(11) NOT NULL,
  `password` varchar(250) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0-close, 1-open',
  `registered_on` datetime NOT NULL DEFAULT current_timestamp(),
  `address` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`hotel_id`, `hotel_name`, `location_id`, `email`, `mobile`, `password`, `status`, `registered_on`, `address`) VALUES
(1, 'Anandas', 1, 'anandas@gmail.com', 44238947, 'asdlkfj34905834alksdjflasdfkjl', 1, '2021-11-03 16:26:58', NULL),
(2, 'Green Palace', 2, 'greenpalace@gmail.com', 0, 'asdlkfj34905834alksdjflasdfkjl', 1, '2021-11-03 16:27:50', NULL),
(3, 'Hotel1', 1, 'hotel1@gmail.com', 1234567890, 'IvnXGDN40MKJJSpQl9fUJg==', 1, '2021-11-10 04:55:01', 'no.1, New Colony, Munnar.'),
(5, 'Hotel2', 2, 'hotel2@gmail.com', 1234567890, 'IvnXGDN40MKJJSpQl9fUJg==', 1, '2021-11-10 04:55:32', 'no.1, New Colony, Munnar.'),
(10, 'Hotel2', 2, 'hotel3@gmail.com', 1234567890, 'IvnXGDN40MKJJSpQl9fUJg==', 1, '2021-11-11 04:48:12', 'Covai'),
(12, 'Hotel2', 3, 'hotel4@gmail.com', 1234567890, 'IvnXGDN40MKJJSpQl9fUJg==', 1, '2021-11-11 04:49:27', 'Covai');

-- --------------------------------------------------------

--
-- Table structure for table `hotel_menu`
--

DROP TABLE IF EXISTS `hotel_menu`;
CREATE TABLE `hotel_menu` (
  `menu_id` int(11) NOT NULL,
  `hotel_id` int(11) NOT NULL,
  `dish_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `price` float NOT NULL,
  `offer_percentage` float NOT NULL,
  `is_available` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0-no,1-yes'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hotel_menu`
--

INSERT INTO `hotel_menu` (`menu_id`, `hotel_id`, `dish_id`, `description`, `price`, `offer_percentage`, `is_available`) VALUES
(1, 1, 1, 'Rice bowl, sambar, rasam, poriyal', 150, 0, 1),
(2, 1, 3, 'Chicken Biriyani with Onion raitha', 250, 0, 1),
(3, 2, 1, 'Rice bowl, sambar, rasam, poriyal, papad', 130, 0, 1),
(4, 2, 3, 'Chicken Biriyani with Onion raitha and Chicken gravy', 270, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `location_id` int(11) NOT NULL,
  `location_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`location_id`, `location_name`) VALUES
(1, 'Chennai'),
(2, 'Coimbatore'),
(5, 'Madurai'),
(9, 'Munnar'),
(6, 'Salem'),
(13, 'Theni'),
(11, 'Thenkasi'),
(8, 'Vellore');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `coupen_id` int(11) DEFAULT NULL,
  `customer_notes` text DEFAULT NULL,
  `ordered_on_at` datetime NOT NULL DEFAULT current_timestamp(),
  `delivery_address` text NOT NULL,
  `contact_no` varchar(50) NOT NULL,
  `hotel_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0-pending,1-delivered,2-cancelled'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `coupen_id`, `customer_notes`, `ordered_on_at`, `delivery_address`, `contact_no`, `hotel_id`, `bill_id`, `status`) VALUES
(1, 1, 1, 'Extra onion raitha', '2021-11-03 16:37:52', 'no.11, good will street, royapetta, chennai-24', '987654232', 1, 1, 0),
(2, 1, 1, 'extra sauce', '2021-11-11 12:28:58', 'no.1, east street, Royapuram, Chennai.', '9876543219', 1, 2, 0),
(3, 2, 1, 'extra sauce', '2021-11-11 01:36:44', 'no.1, east street, Royapuram, Chennai.', '9876543219', 2, 4, 0),
(4, 3, 1, 'extra sauce', '2021-11-11 04:51:38', 'no.1, east street, Royapuram, Chennai.', '9876543219', 2, 5, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `bill_items`
--
ALTER TABLE `bill_items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `coupens`
--
ALTER TABLE `coupens`
  ADD PRIMARY KEY (`coupen_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `customer_bills`
--
ALTER TABLE `customer_bills`
  ADD PRIMARY KEY (`bill_id`);

--
-- Indexes for table `dishes`
--
ALTER TABLE `dishes`
  ADD PRIMARY KEY (`dish_id`);

--
-- Indexes for table `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`hotel_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `hotel_menu`
--
ALTER TABLE `hotel_menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`location_id`),
  ADD UNIQUE KEY `location_name` (`location_name`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `bill_items`
--
ALTER TABLE `bill_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `coupens`
--
ALTER TABLE `coupens`
  MODIFY `coupen_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customer_bills`
--
ALTER TABLE `customer_bills`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `dishes`
--
ALTER TABLE `dishes`
  MODIFY `dish_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
  MODIFY `hotel_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `hotel_menu`
--
ALTER TABLE `hotel_menu`
  MODIFY `menu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
