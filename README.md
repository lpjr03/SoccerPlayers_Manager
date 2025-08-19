# Football Team Backend

This project is a simple **Java backend (NOT SpringBoot)** that manages the insertion and deletion of soccer players in a team. It uses **PostgreSQL** as the database and **pgAdmin** for database management.
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

