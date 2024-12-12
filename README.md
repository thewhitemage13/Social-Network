# Social Network

## Description
Developed a social network using a microservice architecture that provides flexibility, scalability, and high system performance. The project includes multiple microservices, each responsible for a specific functionality of the platform, ensuring reliable and efficient interaction between components.

## Functionality

### UserService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/UserService/src/main/java/org/thewhitemage13/service/UserService.java)
- User creation
- User deletions
- User update
- User view by ID
- View all users
- Open a user to view full information about the user

### SubscriptionService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/SubscriptionService/src/main/java/org/thewhitemage13/service/SubscriptionService.java)
- Subscribe to other users
- Unsubscribe
- View all user subscriptions
- View all of a user's subscribers

### PostService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/PostService/src/main/java/org/thewhitemage13/service/PostService.java)
- Post creation
- Editing a post
- Deleting a post
- Opening a post to view full details
- Opening all user posts by user ID

### MediaService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/MediaService/src/main/java/org/thewhitemage13/service/MediaService.java)
- Upload a media file
- Deleting media files
- Get information about media

### LikeService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/LikeService/src/main/java/org/thewhitemage13/service/LikeService.java)
- Put likes on publications
- Put likes on comments
- Delete likes
- View all likes on a post
- View all likes on a comment

### CommentService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/CommentService/src/main/java/org/thewhitemage13/service/CommentService.java)
- Create comments
- Delete comment
- Update comment
- Get all comments on a post

### NotificationService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/blob/main/NotificationService/src/main/java/org/thewhitemage13/service/NotificationService.java)
- Create notifications
- Update notifications
- Get notification by user ID
- Get all notifications by user ID

### StatisticService
[Source Code](https://github.com/thewhitemage13/SocialNetwork/tree/main/StatisticService/src/main/java/org/thewhitemage13/service)
- Creating statistics
- Updating statistics
- View statistics by date
- View all statistics

## Architecture

The project is implemented based on a microservice architecture using the following components:

- **EurekaServer**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/EurekaServer) - To discover microservices and simplify the management of component interactions.
- **ApiGateway**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/ApiGateWay) - To route requests and provide secure and optimized access to microservices.
- **UserService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/UserService) - For user management.
- **SubscriptionService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/SubscriptionService) - To manage subscriptions.
- **PostService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/PostService) - To manage posts.
- **MediaService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/MediaService) - To manage media files.
- **LikeService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/LikeService) - To manage likes.
- **CommentService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/CommentService) - To manage comments.
- **NotificationService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/NotificationService) - To manage notifications.
- **StatisticService**: [Repository](https://github.com/thewhitemage13/SocialNetwork/tree/main/StatisticService) - Statistical Management.

## Rules of Use

- Email and phone number must be unique.
- The user ID specified in any request must exist.
- The product ID specified in any request must exist.

## Technologies

- **Java**
- **Spring Framework**:
  - Spring Boot
  - Spring Data JPA
  - Spring Cloud
  - Spring Web
  - Spring AOP
- **Amazon S3**: Cloud storage for media files
- **PostgreSQL**: Relational database
- **Kafka**: Message broker for microservice communication
- **Redis**: Cache for improved performance
- **Maven**: Dependency management and project build tool
- **Passay**: Library for password security
- **libphonenumber**: Library for phone number validation
- **Commons-Validator**: Library for data validation
- **Design Patterns**: Used for clean and maintainable code
- **S.O.L.I.D.**: Principles for architecture flexibility and scalability

## Achievements

- **Microservice Architecture**: Designed and implemented a flexible, scalable microservice architecture.
- **Amazon S3 Integration**: Implemented secure and efficient media storage.
- **Data Validation**: Utilized Passay, libphonenumber, and Commons-Validator for secure, accurate user data.
- **Kafka Communication**: Ensured reliable and high-performance communication between microservices using Kafka and Spring Cloud.
- **Design Principles**: Applied S.O.L.I.D. principles and design patterns for maintainable and extensible code.
- **Caching**: Improved performance with Redis.
- **Logging**: Implemented logging using Spring AOP.
- **Documentation**: Documented code using Swagger and Javadoc.

## Installation and Startup

1. Install **JDK** (Java Development Kit).
2. Install **Maven**.
3. Install **PostgreSQL**.
4. Install **Kafka**.
5. Create an **Amazon S3** account and configure access.
6. Clone the repository and follow the individual microservice setup guides provided in their respective folders.
