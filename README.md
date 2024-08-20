
# Order Monitoring System

**Description:**  Developed a web application using microservice architecture to manage orders and warehouse, and provide notifications and statistics.

## Functionality
**UserService**: 
   - User creation
   - User deletions
   - User update
   - User view by id
   - View all users
     
**OrderService**:
   - Creating an order
   - Order deletion
   - Updating order status
     
**InventoryManagementService**:
   - Adding a product
   - Deleting an item
   - Product update
   - Stock replenishment
   - View all items
   - View product by id
   - 
**NotificationService**:
   - Creating a notification
   - Deletion of notice
   - View all notifications by user id
   - View notification by user id
   - View all notifications
     
**StatisticsService**
   - Creating statistics
   - Deleting daily statistics
   - View all statistics
   - View statistics for a specific day

## Architecture

The project is implemented based on a microservice architecture using the following components:

- **EurekaServer**: To discover microservices and simplify the management of component interactions.
- **ApiGateway**: To route requests and provide secure and optimized access to microservices.
- **UserService**: For user management.
- **OrderService**: For order management
- **InventoryManagementService**: For warehouse management.
- **NotificationService**: To receive order status notifications.
- **StatisticService**: For doing business and understanding general statistics.

## Rules of Use

- Email and phone number must be unique.
- The user id we specify in the order must exist.
- Product id we specify in the order must exist.

## Technologies

The following technologies and libraries are used in the project:

- **Java**.
- **Spring Framework**:
  - Spring Boot
  - Spring Data JPA
  - Spring Cloud
  - Spring Web
- **PostgreSQL** is a relational database.
- **Kafka** - message broker for asynchronous communication between microservices.
- **Maven** - a tool for dependency management and project building.
- **Passay** - a library for password security.
- **libphonenumber** - library for processing and validating phone numbers.
- **Commons-Validator** - library for data validation.
- **Design Patterns** - design patterns for creating clean and maintainable code.
- **S.O.L.I.D.** - Principles for architecture flexibility and scalability.

## Achievements
- Microservice architecture: Implemented distributed microservice architecture providing  high scalability and fault tolerance.
- Performance Optimization: Optimized database operations using Spring Data JPA and PostgreSQL, resulting in a 40% performance improvement.
- EurekaServer Integration: Integrate EurekaServer to simplify communication between microservices.
- Efficient routing: Implemented ApiGateway for secure and optimized query routing.
- Improved user interaction: NotificationService and StatisticService are developed to improve user interaction and track key metrics.
- Data Validation and Security: Used Passay, libphonenumber and Commons-Validator libraries to provide validation and security of user data.

### Installation and Startup

- JDK (Java Development Kit) installed.
- Maven installed.
- Installed PostgreSQL.
- Kafka installed.
