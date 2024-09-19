
# 🎉 Keep in Touch - Event & Contact Management Application 🎉
![start](https://i.ibb.co/kmYs1p2/start.png)

Keep in Touch is a **comprehensive web application** that empowers users to create and manage their **events** and **personal contacts** with ease. Designed to help users **organize their appointments**, Keep in Touch includes a **built-in calendar** for better planning and goal achievement.

Whether you need to **schedule events** or **track important contacts**, Keep in Touch provides the tools to make it happen seamlessly.

[Открой PDF файл](Elmaz_Dzhelianchyk_KEEP_IN_TOUCH.pdf)


---

## ✨ Features at a Glance ✨

- 🗓 **Event Management**: Create, edit, delete, and view events. Never miss an appointment again!
- 📇 **Contact Management**: Add, update, delete, and search contacts. Keep your network organized.
- 🔒 **Secure Authentication**: All passwords are securely encrypted with **BCrypt**.
- 👥 **Admin Dashboard**: Administrators can manage users, monitor activity, and moderate content.
- 🔗 **REST API**: Easily integrate the system with other applications or frontend frameworks.
- 📆 **Calendar Integration**: Manage your schedule with a user-friendly calendar view.

> With **Spring Boot**, **Thymeleaf**, and **MySQL** under the hood, Keep in Touch guarantees fast and secure performance, while **Java 21** ensures modern compatibility.

---

## 🛠 Technologies Utilized

This project leverages a powerful stack to ensure reliability and maintainability:

### Backend Technologies:
- **Java 21**: Leverages the latest features for maximum performance.
- **Spring Boot**: The backbone of our application, providing fast setup and easy configuration.
    - **Spring Boot Starter Data JPA**: Simplifies database interactions.
    - **Spring Boot Starter Thymeleaf**: Enables dynamic, server-side HTML generation.
    - **Spring Boot Starter Security**: Ensures secure authentication and authorization.
    - **Spring Boot Starter Validation**: Manages user input validation.
    - **Spring Boot DevTools**: Supports faster development cycles with hot reloads.

### Database:
- **MySQL 8.0.37**: A stable and reliable RDBMS to handle contact and event data.
- **Hibernate**: Simplifies database interaction with its powerful ORM features.

### Frontend Technologies:
- **Thymeleaf**: Provides dynamic page rendering for a smooth user experience.
- **Bootstrap**: Ensures responsiveness and mobile-first design.
- **JavaScript & jQuery**: Adds interactivity and dynamic functionality to the app.

---

## 🎯 Core Features

### 1. Event Management 🗓️
Easily manage all your events and appointments:
- **Create**: Add events with details like title, date, and description.
- **Edit**: Modify existing events when plans change.
- **Delete**: Remove canceled events.
- **View**: See a comprehensive list of all events in a calendar or list format.

<!-- ![Event Management Screenshot](#) -->

### 2. Contact Management 📇
Keep your contacts organized:
- **Add Contact**: Store contact information like name, email, and phone number.
- **Edit Contact**: Make updates to contact details.
- **Delete Contact**: Remove outdated or irrelevant contacts.
- **Search Contact**: Easily find specific contacts by name or other details.

<!-- ![Contact Management Screenshot](#) -->

### 3. Admin Dashboard 🛡️
The admin panel provides extra control over the platform:
- **Manage Users**: View, edit, or delete user accounts.
- **Monitor Events and Contacts**: Ensure proper usage of the platform.
- **Moderation**: Review and moderate user-generated content.

<!-- ![Admin Dashboard Screenshot](#) -->

### 4. Secure Authentication & Authorization 🔐
Your data’s safety is our top priority:
- **BCrypt Encryption**: Passwords and security question answers are encrypted using industry-standard algorithms.
- **User Roles**: Granular control over user permissions (e.g., Admin, User).

<!-- ![Login/Registration Screenshot](#) -->

---

## 📈 Architecture & Design

Keep in Touch follows a **layered architecture** that promotes clear separation of concerns:

### Layered Design:
1. **Presentation Layer** (Views)
    - Responsible for rendering **Thymeleaf** templates and displaying dynamic content to the user.

2. **Controller Layer** (Endpoints)
    - Handles incoming HTTP requests and routes them to appropriate services.

3. **Service Layer** (Business Logic)
    - Processes the business rules and communicates with the DAO layer.

4. **DAO Layer** (Database Access)
    - Manages the database interactions using **JPA** and **Hibernate** for smooth data persistence.

5. **Security Layer** (Authentication & Authorization)
    - Ensures that sensitive data is securely encrypted, with proper role-based access control.

---

## 🚀 Getting Started

### 1. Prerequisites
To run **Keep in Touch**, ensure you have the following installed:
- **Java 21**
- **MySQL 8.0+**
- **Maven 3.6+**

### 2. Clone the Repository
- git clone https://github.com/9elmaz9/keep_in_touch
- cd keep-in-touch

---
### 3. Database Setup 🛠️ (External Properties)

To set up your MySQL database for **Keep in Touch** using external properties, follow these steps:

1. **Create a new MySQL database**:
   - Open your MySQL terminal and run the following command to create the database:
     ```sql
     CREATE DATABASE keep_in_touch;
     ```

2. **Create an external properties file**:
   - Store your database configuration in an **external `application.properties`** or `application.yml` file. This file should be located outside the application JAR for easier maintenance.

3. **Configure the external properties**:
   - Add the following properties to your external `application.properties` file:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/keep_in_touch
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

   - Ensure the `url`, `username`, and `password` fields are updated with your MySQL credentials.

4. **Point to the external configuration**:
   - When starting your Spring Boot application, specify the location of your external `application.properties` file:
     ```bash
     java -jar your-app.jar --spring.config.location=file:/path/to/your/application.properties
     ```

   - Replace `/path/to/your/application.properties` with the actual path to your external configuration file.

5. **Run database migrations** (if applicable):
   - If you're using database migrations (e.g., Flyway or Liquibase), ensure that your external configuration file includes migration settings, or they will run automatically when you start the application.

6. **Verify the connection**:
   - Start your MySQL server and run the application with the external configuration file. Verify that the tables are generated and that the connection is successful.

By using an external `application.properties` file, you can easily manage your database configurations without modifying the application code itself.

---

### 4. Build and Run 🚀

Now that the database is configured, you're ready to build and run **Keep in Touch**. Follow these steps:

1. **Build the application** using Maven:
   - Run the following command to compile the project and package it into a runnable JAR file:
     ```bash
     mvn clean install
     ```

2. **Run the application**:
   - Use the following command to start the application with your external properties file:
     ```bash
     java -jar target/your-app.jar --spring.config.location=file:/path/to/your/application.properties
     ```

   - Ensure the path to the `application.properties` file is correct.

3. **Access the application**:
   - Once the application is running, open your browser and go to:
     ```
     http://localhost:8080
     ```

   - This will take you to the home page of **Keep in Touch**.

---

### 5. Running Tests 🧪

To ensure everything is working as expected, you can run the pre-built tests included in the project:

1. **Run Unit Tests**:
   - Use the following command to execute all unit tests:
     ```bash
     mvn test
     ```

2. **Verify Test Results**:
   - Ensure that all tests pass successfully. This will confirm that the core features (contacts, events, authentication) work correctly.

---

### 6. API Documentation 📜

**Keep in Touch** includes a REST API for developers to integrate with other systems or for frontend communication.

1. **API Endpoints**:
   - Explore the available endpoints for managing users, contacts, and events.

---

<!-- ### 7. Screenshots 🎨 

Add relevant screenshots to give users an idea of how the application looks. Recommended screenshots:

 - **Login Page** ![Login Page Screenshot](#)
- **User Dashboard** ![User Dashboard Screenshot](#)
- **Contact Management** ![Contact Management Screenshot](#)
- **Event Calendar** ![Event Calendar Screenshot](#)
- **Admin Dashboard** ![Admin Dashboard Screenshot](#)

---   -->

### 8. License 📄

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

### 9. Final Notes 📢

**Keep in Touch** is a powerful event and contact management system designed for personal and professional use. With a secure authentication system, admin functionalities, and a REST API, it offers flexibility and security for managing personal information

Stay tuned for more updates!


### 10. DEMO 📢

<!-- ![Login/Registration Screenshot](#) -->
###  Admin 
![start](https://i.ibb.co/kmYs1p2/start.png)
![admin](https://i.ibb.co/HPWnbwd/singin.png)
![admin1](https://i.ibb.co/Jt33zYW/ADMINNNNN.png)
![admin2](https://i.ibb.co/GF77hbk/admin.png)
![admin3](https://i.ibb.co/3WzCrtx/admin-Dashboard.png)
![admin4](https://i.ibb.co/bgppRvz/deleteus-Admin.png)


###  User & Event 
![user](https://i.ibb.co/NNhgTvc/useeeeeeeeeer.png)
![user6](https://i.ibb.co/5Bb66Rh/userProf.png)

![user2](https://i.ibb.co/PmQc8Kp/1.png)

![user3](https://i.ibb.co/jvLQHRz/2.png)
![user5](https://i.ibb.co/RDjHyXK/user3.png)

![user4](https://i.ibb.co/qnhzJdT/3.png)


![user5](https://i.ibb.co/4fCL6kC/user4.png)


![user7](https://i.ibb.co/9N64XTT/usec-Conntact-Form.png)

![user8](https://i.ibb.co/34X0rLn/update-Contact.png)

![uset8](https://i.ibb.co/ccgWTsB/usershowcontact-POISK.png)
![uset8](https://i.ibb.co/2YWWCNW/contact.png)

![uset8](https://i.ibb.co/1MKHnng/elmaz3.png)

![uset8](https://i.ibb.co/pnvMjrq/elmaz4.png)

![uset8](https://i.ibb.co/996cZRh/ELMAZ.png)
![uset8](https://i.ibb.co/x2R7sPh/ELMAZ2.png)

![uset8](https://i.ibb.co/QHKdqbN/change-PAsswoed.png)
![uset8](https://i.ibb.co/v3stpDV/succsesfully.png)





###  Register & Password
![uset84](https://i.ibb.co/NNhgTvc/useeeeeeeeeer.png)
![uset82](https://i.ibb.co/rxcXVMC/register-succes.png)
![uset81](https://i.ibb.co/McHj0mH/AA.png)

![uset83](https://i.ibb.co/MBtSXkL/PHOEBE1.png)
![uset89](https://i.ibb.co/F8FWJwt/Phoebe.png)



![uset887](https://i.ibb.co/QHKdqbN/change-PAsswoed.png)
![uset86](https://i.ibb.co/v304zHX/PH.png)


![uset888](https://i.ibb.co/cLwKyQZ/LOGGGGOUT.png)
![uset883](https://i.ibb.co/NTC4cGN/forgot-password1.png)
![uset833](https://i.ibb.co/d56j404/answer.png)

![uset853](https://i.ibb.co/NCCzMFW/reset.png)

![uset8](https://i.ibb.co/zsFfs53/succsed1.png)

![uset8](https://i.ibb.co/6mNqH20/2.png)

![uset8](https://i.ibb.co/tqVnSLk/3.png)











