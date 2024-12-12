# Order Monitoring System 🛒

## Overview
Order Monitoring System is a robust web application built using microservice architecture to efficiently manage orders, warehouses, notifications, and statistics. The system is highly scalable and fault-tolerant, providing businesses with a reliable tool for monitoring and optimizing operations.

## Features
### **UserService**
- [User Creation](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)
- [User Deletion](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)
- [User Update](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)
- [View User by ID](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)
- [View All Users](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)

### **OrderService**
- [Create Order](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/OrderService/src/main/java/org/thewhitemage13/service/OrderService.java)
- [Delete Order](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/OrderService/src/main/java/org/thewhitemage13/service/OrderService.java)
- [Update Order Status](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/OrderService/src/main/java/org/thewhitemage13/service/OrderService.java)

### **InventoryManagementService**
- [Add Product](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)
- [Delete Item](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)
- [Update Product](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)
- [Stock Replenishment](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)
- [View All Items](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)
- [View Product by ID](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/InventoryManagementService/src/main/java/org/thewhitemage13/service/ProductService.java)

### **NotificationService**
- [Create Notification](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/NotificationService/src/main/java/org/thewhitemage13/service/NotificationService.java)
- [Delete Notification](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/NotificationService/src/main/java/org/thewhitemage13/service/NotificationService.java)
- [View All Notifications](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/NotificationService/src/main/java/org/thewhitemage13/service/NotificationService.java)
- [View Notifications by User ID](https://github.com/thewhitemage13/OrderMonitoringSystem/blob/main/NotificationService/src/main/java/org/thewhitemage13/service/NotificationService.java)

### **StatisticsService**
- [Create Statistics](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/StatisticsService/src/main/java/org/thewhitemage13/service)
- [Delete Daily Statistics](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/StatisticsService/src/main/java/org/thewhitemage13/service)
- [View All Statistics](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/StatisticsService/src/main/java/org/thewhitemage13/service)
- [View Statistics for a Specific Day](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/StatisticsService/src/main/java/org/thewhitemage13/service)

## Architecture 🏗️
The project employs a distributed microservice architecture to ensure scalability and fault tolerance. Key components include:
- **[EurekaServer](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/EurekaServer):** Simplifies microservice discovery and interaction.
- **[ApiGateway](https://github.com/thewhitemage13/OrderMonitoringSystem/tree/main/ApiGateWay):** Routes requests efficiently and provides secure access to services.
- **UserService:** Manages user-related operations.
- **OrderService:** Handles order-related functionalities.
- **InventoryManagementService:** Manages warehouse operations.
- **NotificationService:** Sends order status notifications.
- **StatisticsService:** Generates and manages business statistics.

## Rules and Constraints ⚠️
- Email and phone number must be unique.
- The user ID specified in an order must exist.
- Product ID specified in an order must exist.

## Technologies 🛠️
The system leverages the following tools and frameworks:
- **Java**: Core language.
- **Spring Framework**: Includes Spring Boot, Spring Data JPA, Spring Cloud, Spring Web, and Spring AOP.
- **PostgreSQL**: Relational database.
- **Kafka**: Asynchronous communication between microservices.
- **Redis**: In-memory caching for enhanced performance.
- **Maven**: Dependency management and build automation.
- **Passay**: Ensures password security.
- **libphonenumber**: Validates phone numbers.
- **Commons-Validator**: Data validation library.
- **Design Patterns**: Promotes clean and maintainable code.
- **S.O.L.I.D.**: Encourages flexible and extensible architecture.

## Achievements 🏆
- **Microservice Architecture**: Distributed system ensuring scalability and fault tolerance.
- **Performance Optimization**: Enhanced database operations leading to a 40% performance boost.
- **Advanced Framework Integration**: Used EurekaServer for simplified microservice interactions.
- **Secure and Efficient Routing**: Leveraged ApiGateway and Kafka for high performance.
- **User-Centric Design**: Developed NotificationService and StatisticsService to enhance user interaction.
- **Data Validation and Security**: Utilized libraries like Passay and libphonenumber to validate user data.

## Installation and Startup 🚀

1. **Prerequisites:**
   - Install [JDK](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Install [Maven](https://maven.apache.org/download.cgi).
   - Install [PostgreSQL](https://www.postgresql.org/download/).
   - Install [Kafka](https://kafka.apache.org/downloads).

2. **Clone the Repository:**
   ```bash
   git clone https://github.com/thewhitemage13/OrderMonitoringSystem.git
   cd OrderMonitoringSystem
   ```

3. **Set Up Environment:**
   - Configure database connections in the `application.properties` files.

4. **Build and Run:**
   - Build the project:
     ```bash
     mvn clean install
     ```
   - Start services using Docker:
     ```bash
     docker-compose up
     ```

5. **API Documentation:**
   - User Service: `http://localhost:8089/swagger-ui.html`
   - Statistic Service: `http://localhost:8088/swagger-ui.html`
   - Order Service: `http://localhost:8087/swagger-ui.html`
   - Notification Service: `http://localhost:8086/swagger-ui.html`
   - Inventory Service: `http://localhost:8085/swagger-ui.html`

## Future Improvements 🔮
- Add a graphical user interface (GUI) for better usability.
- Implement advanced analytics and reporting features.
- Enhance security with multi-factor authentication.

---
Made with ❤️ by [Your Name](https://github.com/yourusername).
