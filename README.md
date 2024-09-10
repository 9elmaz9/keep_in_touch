#### Keep in Touch" App Project - Concept Version
![Dino](https://gifdb.com/images/high/working-emoji-typing-gf7f51a0cl5ynyx9.webp)



## Why Choose "Keep in Touch"?
The app offers several benefits that make managing contacts easier and more efficient:

#### Intuitive Interface: User-friendly interface for easy contact and event management.

#### Centralized Contact Management: Manage all your personal and professional contacts in one place.

#### Integrated Reminders: Never miss an appointment with built-in reminders.

#### Security: Strong security with password encryption to protect your data.

#### Multi-Device Support: Synchronization across devices for access anytime, anywhere.

#### Search and Filter Options: Quickly find contacts by name, company, or role.

#### Flexible and Scalable: Suitable for individual users and small to medium-sized businesses.

## Conclusion
"Keep in Touch" combines ease of use, powerful functionality, and strong security, making it the ideal app for effective event and contact management.







<!--
# "keep_in_touch" applicatie project

## сonceptversie


## Waarom zouden mensen de "Keep in Touch" app moeten kiezen?

### Belangrijkste voordelen en unieke kenmerken van de "Keep in Touch" app:

#### Gecentraliseerd contactbeheer
De app stelt gebruikers in staat om al hun contacten, zowel persoonlijk als professioneel, op één centrale plaats te beheren. Dit maakt het organiseren van contacten handiger en efficiënter.

#### Intuïtieve interface
"Keep in Touch" biedt een eenvoudige en intuïtieve interface waarmee gebruikers gemakkelijk contacten kunnen toevoegen, bewerken en verwijderen, evenals evenementen en herinneringen beheren.

#### Integratie van evenementen en herinneringen
Ingebouwde functies voor evenementen- en herinneringenbeheer zorgen ervoor dat gebruikers geen belangrijke afspraken en evenementen met hun contacten vergeten. Dit is vooral nuttig voor professionals die op de hoogte willen blijven van hun zakelijke bijeenkomsten.

#### Veiligheid en privacy
De app biedt een hoog niveau van gegevensbeveiliging voor gebruikers, inclusief wachtwoordversleuteling en andere maatregelen ter bescherming van vertrouwelijke informatie. Gebruikers kunnen erop vertrouwen dat hun gegevens veilig zijn.

#### Ondersteuning voor meerdere apparaten
"Keep in Touch" ondersteunt gegevenssynchronisatie tussen verschillende apparaten, waardoor gebruikers toegang hebben tot hun contacten en evenementen vanaf elk apparaat, altijd en overal.

#### Zoek- en filterfunctionaliteit
De app biedt krachtige zoek- en filterhulpmiddelen, waarmee snel de benodigde contacten kunnen worden gevonden op basis van naam, bedrijf, functie en andere criteria.

#### Flexibiliteit en schaalbaarheid
"Keep in Touch" is geschikt voor zowel individueel gebruik als voor kleine en middelgrote bedrijven, met flexibele instellingen en schaalmogelijkheden afhankelijk van de behoeften van de gebruikers.

#### Gebruiksgemak
Gebruikers kunnen eenvoudig hun contacten importeren en exporteren, de app integreren met andere diensten en deze zonder extra moeite in hun dagelijks leven gebruiken.

### Conclusie
De "Keep in Touch" app biedt gebruikers een unieke combinatie van gemak, functionaliteit en veiligheid. Dankzij de krachtige hulpmiddelen voor contact- en evenementenbeheer, gebruiksvriendelijkheid en betrouwbare gegevensbescherming, is "Keep in Touch" de ideale keuze voor iedereen die effectief zijn contacten wil beheren en op de hoogte wil blijven van alle belangrijke gebeurtenissen.
 -->



10,09


# 🎉 Keep in Touch - Event & Contact Management System 🎉

Keep in Touch is a **comprehensive web application** that empowers users to create and manage their **events** and **personal contacts** with ease. Designed to help users **organize their appointments**, Keep in Touch includes a **built-in calendar** for better planning and goal achievement.

Whether you need to **schedule events** or **track important contacts**, Keep in Touch provides the tools to make it happen seamlessly.

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

![Event Management Screenshot](#)

### 2. Contact Management 📇
Keep your contacts organized:
- **Add Contact**: Store contact information like name, email, and phone number.
- **Edit Contact**: Make updates to contact details.
- **Delete Contact**: Remove outdated or irrelevant contacts.
- **Search Contact**: Easily find specific contacts by name or other details.

![Contact Management Screenshot](#)

### 3. Admin Dashboard 🛡️
The admin panel provides extra control over the platform:
- **Manage Users**: View, edit, or delete user accounts.
- **Monitor Events and Contacts**: Ensure proper usage of the platform.
- **Moderation**: Review and moderate user-generated content.

![Admin Dashboard Screenshot](#)

### 4. Secure Authentication & Authorization 🔐
Your data’s safety is our top priority:
- **BCrypt Encryption**: Passwords and security question answers are encrypted using industry-standard algorithms.
- **User Roles**: Granular control over user permissions (e.g., Admin, User).

![Login/Registration Screenshot](#)

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

### 7. Screenshots 🎨

Add relevant screenshots to give users an idea of how the application looks. Recommended screenshots:

- **Login Page** ![Login Page Screenshot](#)
- **User Dashboard** ![User Dashboard Screenshot](#)
- **Contact Management** ![Contact Management Screenshot](#)
- **Event Calendar** ![Event Calendar Screenshot](#)
- **Admin Dashboard** ![Admin Dashboard Screenshot](#)

---

### 8. License 📄

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

### 9. Final Notes 📢

**Keep in Touch** is a powerful event and contact management system designed for personal and professional use. With a secure authentication system, admin functionalities, and a REST API, it offers flexibility and security for managing personal information.

In future updates, expect features like:
- **Email Notifications**: Get reminders for your upcoming events.
- **Custom Event Categories**: Organize your events into customizable categories.
- **Integration with Google Calendar**: Sync your events with Google Calendar for seamless organization.

Stay tuned for more updates!
