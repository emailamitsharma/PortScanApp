-- DO NOT DELETE THESE FILES. THIS IS NOT FOR H2, ONLY MEANT FOR MYSQL.

create database portScanDB;

use  portScanDB;

drop table if exists scan_details;

CREATE TABLE scan_details (
     scan_id INT NOT NULL AUTO_INCREMENT,
     identifier VARCHAR(255) NOT NULL,
     scan_time DATETIME NOT NULL,
     is_scan_completed CHAR(1),
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     created_by VARCHAR(255) DEFAULT 'SYSTEM',
     updated_date TIMESTAMP ,
     updated_by VARCHAR(255),
     PRIMARY KEY (scan_id)
) AUTO_INCREMENT=100;



drop table if exists port_details;

CREATE TABLE port_details (
     id INT NOT NULL AUTO_INCREMENT,
     port_number INT NOT NULL,
     state CHAR(8) NOT NULL,
     scan_id INT NOT NULL,
     service VARCHAR(255),
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     created_by VARCHAR(255) DEFAULT 'SYSTEM',
     updated_date TIMESTAMP,
     updated_by VARCHAR(255),
     PRIMARY KEY (id),
     CONSTRAINT fk_scanId FOREIGN KEY (scan_id) REFERENCES scan_details (scan_id) ON DELETE CASCADE ON UPDATE CASCADE
) AUTO_INCREMENT=100;


-- INDEXES ONLY BELOW QUERIES NEED TO BE FIRED AS SPRING JPA WILL TAKRE CARE OF DDL BY OWN ON BOOTSTART

-- As per current need.
CREATE  INDEX idx_port_detail_scan_id ON port_details (scan_id);
CREATE  INDEX idx_scan_detail_identifier ON scan_details (identifier);