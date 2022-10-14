DROP TABLE IF EXISTS `ledger`;
DROP TABLE IF EXISTS `transaction`;

CREATE TABLE `ledger` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `transaction` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ledger_id` int unsigned NOT NULL,
  `transaction_datetime` datetime NOT NULL,
  `is_credit` tinyint unsigned NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `balance` decimal(10,2) unsigned NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
