/*****************************************************
 LAGERSYSTEM SQL
 *****************************************************/
 
/*
 Contains the customers company name
 */
CREATE TABLE IF NOT EXISTS companies (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(1000) NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	is_active boolean NOT NULL default 0,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
 The users table
 */
CREATE TABLE IF NOT EXISTS users (
	id int(11) NOT NULL AUTO_INCREMENT,
	username varchar(300) NOT NULL, 
	password varchar(64) NOT NULL,
	company_id int(11) NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
 Defines each users role
 */
CREATE TABLE IF NOT EXISTS roles (
	user_id int(11) NOT NULL,
	role varchar(100) NOT NULL,
	PRIMARY KEY (user_id, role), 
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
 Contains all the different storages fx 'Storage 1' and 'Booze Storage'
 */
CREATE TABLE IF NOT EXISTS storages (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL, 
	company_id int(11) NOT NULL,
	is_open boolean NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
 Inventory is fx Mokai, Tuborg etc.
 */
CREATE TABLE IF NOT EXISTS inventory (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	units int(11) DEFAULT 0,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	storage_id int(11) NOT NULL,
	price double NOT NULL DEFAULT 0.0,
	PRIMARY KEY (id),
	FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
 Stations to move inventory to fx. Bar 1, Bar 2 etc.  
 */
CREATE TABLE IF NOT EXISTS stations (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	company_id int(11) NOT NULL,
	importance varchar(100) NOT NULL,
	PRIMARY KEY (id), 
	FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS inventory_log(
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	units int(11) NOT NULL,
	price double NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	storage_id int(11) NOT NULL,
	station_id int(11) NOT NULL,
	performed_action varchar(500) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE,
	-- FOREIGN KEY (station_id) REFERENCES stations(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS storage_log(
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	storage_id int(11) NOT NULL,
	performed_action varchar(500) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS archive_log(
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	storage_id int(11) NOT NULL,
	opened_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	closed_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,	
	PRIMARY KEY (id),
	FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS inventory_snapshot(
	id int(11) NOT NULL AUTO_INCREMENT,
	archive_log_id int(11) NOT NULL,
	name varchar(500) NOT NULL,
	inventory_id int(11) NOT NULL,
	units_at_open int(11) NOT NULL,
	units_at_close int(11) NOT NULL DEFAULT 0,
	PRIMARY KEY (id),
	-- FOREIGN KEY (inventory_id) REFERENCES inventory(id),
	FOREIGN KEY (archive_log_id) REFERENCES archive_log(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/**
 * INSERT ADDITIONAL FIELDS
 */
ALTER TABLE inventory ADD sales_price double NOT NULL DEFAULT 0.0 AFTER price;
ALTER TABLE inventory_log ADD sales_price double NOT NULL DEFAULT 0.0 AFTER price;
ALTER TABLE inventory_log ADD inventory_id int(11) NOT NULL AFTER id;
ALTER TABLE inventory_snapshot ADD price double NOT NULL DEFAULT 0.0;
ALTER TABLE inventory_snapshot ADD sales_price double NOT NULL DEFAULT 0.0;

/**
 * UPDATE UNITS FROM INT TO DOUBLE
 */
ALTER TABLE inventory MODIFY COLUMN units DOUBLE NOT NULL DEFAULT 0.0;
ALTER TABLE inventory_log MODIFY COLUMN units DOUBLE NOT NULL DEFAULT 0.0;
ALTER TABLE inventory_snapshot MODIFY COLUMN units_at_close DOUBLE NOT NULL DEFAULT 0.0;
ALTER TABLE inventory_snapshot MODIFY COLUMN units_at_open DOUBLE NOT NULL DEFAULT 0.0;

CREATE TABLE IF NOT EXISTS categories(
	id int(11) NOT NULL AUTO_INCREMENT,
	company_id int(11) NOT NULL,
	category varchar(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS inventory_categories(
	inventory_id int(11) NOT NULL,
	category_id int(11) NOT NULL,
	PRIMARY KEY (inventory_id),
	FOREIGN KEY (inventory_id) REFERENCES inventory(id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS temporary_units(
	inventory_id int(11) NOT NULL,
	temp_units DOUBLE NOT NULL,
	storage_id int(11) NOT NULL,
	PRIMARY KEY (inventory_id),
	FOREIGN KEY (inventory_id) REFERENCES inventory(id) ON DELETE CASCADE,
	FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/**
 * Tvivlsom foregin key som ikke fungerer efter planen. 
 */
ALTER TABLE inventory_snapshot DROP FOREIGN KEY inventory_snapshot_ibfk_1;



/*****************************************************
 INSERT DUMMY DATA HERE
 *****************************************************/
INSERT INTO companies (name) VALUES ("Allo");
INSERT INTO users (username, password, company_id) VALUES ("admin", "3c11440050cbedc97d35541159636783b81482d81880a4ef22cc7e6c460bdcb8", 1);
INSERT INTO roles (user_id, role) VALUES (LAST_INSERT_ID(), "admin");
INSERT INTO roles (user_id, role) VALUES (LAST_INSERT_ID(), "manager");
INSERT INTO roles (user_id, role) VALUES (LAST_INSERT_ID(), "user");
