📚 Online Course Recommendation System

A comprehensive project featuring a modern Web Application, Swing GUI, and Servlet implementation for recommending courses based on user preferences. 

💻 Technology Stack
===================

**Frontend (Web UI)**
- **HTML5 & CSS3**: Modern, responsive layout with glassmorphism, dynamic animations, and premium aesthetics.
- **JavaScript (Vanilla ES6+)**: Interactive forms, API fetching, and dynamic DOM manipulation without external frameworks.

**Backend**
- **Java (JDK 24)**: Core application logic.
- **Custom HTTP Server (`com.sun.net.httpserver`)**: Serves static web assets and provides RESTful JSON API endpoints (`/api/recommend`).
- **Core Java Concepts**: OOPs, Collections, Multithreading (background course loading), Exception Handling.
- **Design Patterns**: DAO (Data Access Object) Pattern.

**Database**
- **H2 Database (In-Memory)**: Fast, embedded relational database for seamless data seeding and testing without external dependencies.
- **JDBC**: Database connectivity using PreparedStatements for secure CRUD operations.

**Legacy / Alternative Interfaces**
- **Java Swing**: Desktop-based Graphical User Interface.
- **Apache Tomcat & Servlets**: Alternative web deployment handling database insertion.

🚀 Features
✅ Web-Based UI (Modern App)

- Modern Glassmorphism UI with smooth animations
- REST API via built-in Java HttpServer
- Dynamic search, filtering, and smart autocomplete
- In-depth curriculums (7-9 topics per course) and Must-Know Prerequisites
- Integrated UI scrollbars for gracefully handling large course data


✅ Object-Oriented Programming

Inheritance (Course → ProgrammingCourse / NonTechnicalCourse)

Polymorphism (Course reference, subclass objects)

Encapsulation (private fields + getters)

Exception Handling (custom exception)

✅ Collections & Generics

ArrayList<Course> used for storing and displaying courses

Generic DAO & Service layers

✅ Multithreading

Background loading using a custom thread: CourseLoaderThread

✅ Database (JDBC + H2 Embedded)

DBConnection.java handles schema creation and extensive data seeding natively

CourseDAO.java performs CRUD operations

Uses PreparedStatement, ResultSet, DAO design pattern

✅ Swing-Based GUI

Add a new course

View all courses

Get recommended courses

Smooth UI interaction

🗂️ Project Structure
Online-Course-Recommendation-System/
│
├── src/
│   ├── model/
│   │   ├── Course.java
│   │   ├── ProgrammingCourse.java
│   │   ├── NonTechnicalCourse.java
│   │
│   ├── service/
│   │   └── CourseService.java
│   │
│   ├── database/
│   │   ├── CourseDAO.java
│   │   └── DBConnection.java
│   │
│   ├── exceptions/
│   │   └── InvalidInputException.java
│   │
│   ├── threads/
│   │   └── CourseLoaderThread.java
│   │
│   ├── ui/
│   │   ├── MainGUI.java
│   │   └── WebServer.java
│
├── web/ (Frontend Assets)
│   ├── index.html
│   ├── styles.css
│   └── app.js
│
├── lib/ (JDBC driver)
│
├── bin/ (compiled classes)
│
└── README.md

🛢️ Database Setup (MySQL)

Create database:

CREATE DATABASE online_courses;


Create table:

CREATE TABLE courses (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    difficulty VARCHAR(50),
    rating DOUBLE,
    duration INT,
    description TEXT
);

⚙️ How to Compile & Run
Compile
javac -d bin -cp "bin;lib/*" src\**\*.java

Run Web Server (Modern UI)
java -cp "bin;lib/*" ui.WebServer
Then open http://localhost:8080 in your browser.

Run Desktop App (Swing UI)
cd bin
java -cp ".;../lib/*" ui.MainGUI


🧵 Multithreading Example

CourseLoaderThread runs during recommendations:

for (int i = 1; i <= 5; i++) {
    System.out.println("Loading recommended courses... " + i);
    Thread.sleep(500);
}

---

## 🌐 Servlet Implementation (Apache Tomcat + JDBC)

### 📌 Objective
To demonstrate Java Servlet technology integrated with **MySQL (JDBC)** by creating and deploying a servlet on **Apache Tomcat** that performs database insertion with proper validation and error handling.

This fulfills:
- **Servlet Implementation – 10 marks**
- **Code Quality & Execution – 5 marks**
- **Innovation / Extra Effort – 2 marks**

---

### 🛠️ Technologies Used
- Java Servlet API
- Apache Tomcat 9.0.113
- MySQL 8.x
- JDBC (PreparedStatement)
- Java (JDK 24)
- HTTP (GET method)
- macOS

---

### 📂 Servlet Project Structure

```
StudentServletApp/
└── WEB-INF/
    ├── web.xml
    └── classes/
        ├── AddServlet.java
        └── AddServlet.class
```

---

### 🧾 Servlet Functionality
The servlet accepts course details via HTTP request parameters, validates inputs, and inserts data into a MySQL database.

**Parameters handled:**
- name
- category
- difficulty
- rating
- duration
- description

---

### 🧾 Servlet Code (AddServlet.java – JDBC Integrated)

```java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddServlet extends HttpServlet {

    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/online_courses?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "";
    private static final String DB_PASS = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String difficulty = request.getParameter("difficulty");
            String ratingStr = request.getParameter("rating");
            String durationStr = request.getParameter("duration");
            String description = request.getParameter("description");

            if (name == null || category == null || difficulty == null ||
                ratingStr == null || durationStr == null ||
                name.isEmpty() || category.isEmpty() || difficulty.isEmpty()) {

                out.println("<h3>Error: All fields are required</h3>");
                return;
            }

            double rating = Double.parseDouble(ratingStr);
            int duration = Integer.parseInt(durationStr);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO courses (name, category, difficulty, rating, duration, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setString(3, difficulty);
            ps.setDouble(4, rating);
            ps.setInt(5, duration);
            ps.setString(6, description);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<h2>Course added successfully</h2>");
            } else {
                out.println("<h3>Failed to add course</h3>");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            out.println("<h3>Database Error:</h3>");
            out.println(e.getMessage());
        }
    }
}
```

---

### 🗂️ Deployment Descriptor (web.xml)

```xml
<web-app>
    <servlet>
        <servlet-name>AddServlet</servlet-name>
        <servlet-class>AddServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>AddServlet</servlet-name>
        <url-pattern>/add</url-pattern>
    </servlet-mapping>
</web-app>
```

---

### ▶️ How to Run the Servlet

1. Start Apache Tomcat:
```bash
cd apache-tomcat-9.0.113/bin
./startup.sh
```

2. Open browser and execute:
```
http://localhost:8080/StudentServletApp/add?name=Java&category=Programming&difficulty=Beginner&rating=4.5&duration=25&description=CoreJava
```

---

### ✅ Output

```
Course added successfully
```

---

### 🧪 Testing & Debugging
- Fixed HTTP 404 via correct servlet mapping
- Resolved MySQL authentication & public key retrieval issues
- Handled NumberFormatException and null parameters
- Verified database insertion via MySQL CLI

---

### ⭐ Innovation / Extra Effort
- Manual servlet compilation using Tomcat libraries
- JDBC integration without frameworks
- Secure PreparedStatement usage
- Separate database user instead of root
- Integrated servlet alongside Swing + JDBC desktop app

---

### 🎯 Marks Mapping

| Criteria | Status |
|--------|--------|
| Servlet Implementation (10) | ✅ Completed |
| Code Quality & Execution (5) | ✅ Successful |
| Innovation / Extra Effort (2) | ✅ Manual Deployment + JDBC |

---

### 🏁 Conclusion
This project demonstrates **desktop-based Java applications (Swing + JDBC)** as well as **web-based Java applications (Servlet + Tomcat + MySQL)**, showcasing strong practical understanding of Java full-stack fundamentals.
