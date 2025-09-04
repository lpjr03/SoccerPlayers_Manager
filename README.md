# Football Team Backend

This project is a simple **Java application** that manages the insertion and deletion of soccer players in a team. It uses **PostgreSQL** as the database and **pgAdmin** for database management.
In addition, **Java serialization** has been implemented to handle the persistence of player objects.


---

## 📋 Prerequisites

Before running the project, make sure you have the following installed:

- **Java 17**
- **Docker**
- **Maven**
- **VS Code** (or any preferred IDE)

---

## 🚀 How to Run the Project

First of all, **clone this repository**. 
Then:

## 1. Configure Environment Variables

Create a `.env` file in the project root with the following values:

```env
# PostgreSQL
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_database

# pgAdmin
PGADMIN_DEFAULT_EMAIL=your_email
PGADMIN_DEFAULT_PASSWORD=your_password
```

### 2. Start the database and pgAdmin with Docker
Run the following command to start PostgreSQL and pgAdmin:

```bash
docker-compose up -d
```
This will start:

- **PostgreSQL** on port 5432
- **pgAdmin** on port 8888

**You can access pgAdmin at: http://localhost:8888**

### 3. Build the Project with Maven

Run the following command to clean and build your project using Maven:

```bash
mvn clean install
```

### 4. Run the Java Project

Open the project in your IDE (e.g., Visual Studio Code) and run the Main.java file.

---

## 📦 Dependencies

This project uses the following main dependencies:

### Production
- **[Lombok 1.18.32](https://projectlombok.org/)** – reduces boilerplate code (getters, setters, constructors)
- **[PostgreSQL JDBC 42.6.1](https://jdbc.postgresql.org/)** – PostgreSQL database driver
- **[SLF4J Simple 2.0.12](http://www.slf4j.org/)** – simple logging framework
- **[java-dotenv 5.2.2](https://github.com/cdimascio/java-dotenv)** – to load environment variables from `.env` file

### Testing
- **[JUnit 6.0.0-M2](https://junit.org/junit5/)** – testing framework
- **[Mockito Core 5.18.0](https://site.mockito.org/)** – mocking framework for unit tests
- **[Mockito JUnit Jupiter 5.12.0](https://site.mockito.org/)** – integration with JUnit 5

### Build & Quality Plugins (Maven)
- **Maven Jar Plugin 3.3.0** – to package the project into a JAR with a manifest
- **Maven Javadoc Plugin 3.11.2** – to generate documentation
- **Maven Checkstyle Plugin 3.6.0** – code style validation
- **SpotBugs Maven Plugin 4.9.3.2** – static analysis for detecting bugs



