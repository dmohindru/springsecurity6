## About springsection6 project
### This project demonstrate how to
1. Bootstrap a basic spring boot project with security.
2. Protect application endpoints with custom spring security config (SecurityFilterChain) 
with Authentication(AuthN) and Authorization(AuthZ).
3. Allow public access to application endpoints with custom spring security config (SecurityFilterChain).
4. Create user in external DB and their retrieval.
5. Use BCryptPasswordEncoder
6. Implement our own AuthenticationProvider
7. Enable CORs an CSRF configuration.
8. Create and deal with custom authorization.
9. Write your own custom security filters.

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