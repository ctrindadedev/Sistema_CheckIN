> WORK IN PROGRESS

# Check-in and Event Management System

A complete Full-Stack system for event management, user registrations, and real-time attendance control (check-in). 

Developed with a focus on security, solid architectural practices, apply design patterns (Like Singleton) , and automated testing.

## 💻 Tech Stack

<div style="display: flex; flex-wrap: wrap; gap: 15px; margin-top: 10px; margin-bottom: 20px;">
  <img align="center" alt="React" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/react/react-original.svg"/>
  <img align="center" alt="TypeScript" height="50" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/typescript/typescript-plain.svg">
  <img align="center" alt="Vite" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/vite/vite-original.svg"/>
  
  <img align="center" alt="Java" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg"/>
  <img align="center" alt="Spring" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg"/>
  <img align="center" alt="PostgreSQL" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-original.svg"/>
  
  <img align="center" alt="JUnit" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/junit/junit-original.svg" />
  <img align="center" alt="Swagger" height="50" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/swagger/swagger-original.svg" />
</div>

## 📖 About the Repository

This repository contains the source code for both the Front-end and Back-end of the application. The system was designed to solve the problem of event access control, allowing Organizers to create events with a limited capacity, and Participants to register and check in on the day of the event.


## Key Features

### 🔐 Security & Authentication
- **JWT (JSON Web Token) Athentication:** Secure login with hashed passwords using BCrypt.
- **Role-Based Access Control:** Differentiated permissions between `ORGANIZADOR` (Organizer - can manage events) and `PARTICIPANTE` (Participant).

### 📅 Event Management
- Complete event CRUD.
- Control and validation of available spots limit.

### 📝 Registrations & Check-in
- Automatic user registration linked to the event.
- Business rule validation (prevents double registration, prevents registration if no spots are available).
- Check-in system to confirm participant attendance at the event.

### 🧪 Code Quality & Testing
- **Unit Tests:** Coverage of the Services layer using *JUnit 5* and *Mockito*.
- **Integration Tests:** Validation of the HTTP request flow and business rules using *MockMvc*.

## TO-DO
- [ ] QR Code generation and reading for instant check-in.
- [ ] Automated email dispatch (registration confirmation and reminders).
- [ ] User Interface (UI/UX) refinement on the Front-end.

---

## 🛠️ How to run the project locally

### Prerequisites
- Java 17+
- Node.js & npm (or yarn)
- PostgreSQL (running locally or via Docker)

### Back-end Setup
1. Navigate to the `back-end` folder.
2. Configure your database credentials in the `src/main/resources/application.properties` file.
3. Run the project in your IDE or via terminal:

   ```bash
   ./mvnw spring-boot:run
   ```

4. The API will be up and running. Swagger UI can be accessed on the default port 8080//swagger-ui.html.


### Front-end Setup

1. Navigate to the `front-end` folder.
2. Install deps

   ```bash
   npm i
   ```

3. Start the development server

   ```bash
   npm run dev
   ```
