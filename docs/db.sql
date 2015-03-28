/*****************************************************
 LAGERSYSTEM SQL
 *****************************************************/
 
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
 Contains the customers company name
 */
CREATE TABLE IF NOT EXISTS companies (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(1000) NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
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
	updated_at timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
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
	updated_at timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
	storage_id int(11) NOT NULL,
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
	PRIMARY KEY (id), 
	FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS log(
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(500) NOT NULL,
	units int(11) DEFAULT 0,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	storage_id int(11) NOT NULL,
	station_id int(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (storage_id) REFERENCES storages(id),
	FOREIGN KEY (station_id) REFERENCES stations(id)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*****************************************************
 INSERT DUMMY DATA HERE
 *****************************************************/
INSERT INTO companies (name) VALUES ("LagerStyring A/S");
INSERT INTO users (username, password, company_id) VALUES ("admin", "3c11440050cbedc97d35541159636783b81482d81880a4ef22cc7e6c460bdcb8", 1);