# Payment-System

# Axis Task

This project is a Spring Boot application that provides APIs for managing accounts and transactions.

## Technologies Used

- Java 17
- Spring Boot 3
- H2 Database
- Docker

## Running the Project

### To run the project, use the following Docker command:

docker run -p 8888:8888 youssef02222/axis-task:1.0

### Accessing the Database
Access the H2 Database using the H2 Console at http://localhost:8888/h2-console. Use the following JDBC URL: jdbc:h2:mem:testdb  , with username : sa and empty password
### Accessing Swagger UI
Swagger UI is available at http://localhost:8888/swagger-ui/index.html#/.



## Functionality
The project includes two controllers for managing accounts and transactions.

### Accounts Controller
Create Account: Users can create an account with an initial balance of 1000. The account ID is a unique 30-character string.
Update Account: Users can update account details.
Delete Account: Users can delete an account.


### Transactions Controller

Users can perform various transactions:

Balance Inquiry: Check the account balance.
Deposit: Deposit funds into the account.
Withdraw: Withdraw funds from the account.
Reverse Transaction: Users can reverse deposit or withdraw transactions.
Inquire Transaction: Users can inquire about a transaction using the transaction ID provided in the response.
