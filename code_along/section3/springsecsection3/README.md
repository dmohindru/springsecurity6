## About springsection3 project
### This project demonstrate how to
1. Bootstrap a basic spring boot project with security.
2. Protect application endpoints with custom spring security config (SecurityFilterChain).
3. Allow public access to application endpoints with custom spring security config (SecurityFilterChain).
4. Create in memory users and their retrieval.
5. Create user in external DB and their retrieval.
6. Implement our own UserDetailsService interface for Spring security.


### Important videos in this section
1. Video 24: Understanding User Management interfaces and Classes
2. Video 25: Deep Dive of UserDetails Interface & User class
3. Video 26: Deep Dive of UserDetailsService & UserDetailsManger interfaces
4. Video 27: Deep Dive of UserDetailsManager Implementation classes

### Run MySQL docker image
```shell
# username: root
# password: my-secret-pw
docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -v mysql:/home/dhruv/opt/mysql mysql
```

### Create necessary tables in MySQL DB scripts
```sql
# Create default tables for spring boot out of the box authentication
CREATE TABLE `users`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `enabled` INT NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE `authorities` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `authority` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));

# Insert dummy data into tables
INSERT IGNORE INTO `users` VALUES (NULL, 'happy', '12345', '1');
    
INSERT IGNORE INTO `authorities` VALUES (NULL, 'happy', 'write');

# Create custom tables for spring security
CREATE TABLE `customer` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `email` varchar(45) NOT NULL,
                            `pwd` varchar(200) NOT NULL,
                            `role` varchar(45) NOT NULL,
                            PRIMARY KEY (`id`)
);
# Insert sample data in table
INSERT INTO `customer` (`email`, `pwd`, `role`)
VALUES ('johndoe@example.com', '54321', 'admin');
```