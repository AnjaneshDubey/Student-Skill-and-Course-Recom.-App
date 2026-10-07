package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS courses (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "category VARCHAR(100), " +
                    "difficulty VARCHAR(50), " +
                    "rating DOUBLE, " +
                    "duration INT, " +
                    "description TEXT, " +
                    "free_tool VARCHAR(255), " +
                    "premium_tool VARCHAR(255), " +
                    "advanced_topics TEXT)";
            stmt.execute(sql);
            
            // Seed extended data with tools and advanced topics
            stmt.execute("INSERT INTO courses (name, category, difficulty, rating, duration, description, free_tool, premium_tool, advanced_topics) VALUES " +
                    // Frontend
                    "('HTML & CSS Basics', 'Frontend Development', 'Beginner', 4.7, 20, 'Learn the building blocks of the web.', 'Visual Studio Code', 'WebStorm', 'Semantic HTML|Flexbox & Grid Layouts|Responsive Media Queries')," +
                    "('JavaScript Essentials', 'Frontend Development', 'Beginner', 4.8, 35, 'Master vanilla JS.', 'Visual Studio Code', 'WebStorm', 'DOM Manipulation|ES6+ Features|Asynchronous Fetch API')," +
                    "('React.js Front to Back', 'Frontend Development', 'Intermediate', 4.9, 45, 'Build modern UI.', 'Visual Studio Code', 'WebStorm', 'React Hooks (useState, useEffect)|Context API|React Router DOM')," +
                    "('Advanced Frontend Architecture', 'Frontend Development', 'Advanced', 4.9, 45, 'Scale your apps.', 'Visual Studio Code', 'WebStorm', 'Redux State Management|Micro-frontends|Webpack & Vite Config')," +
                    
                    // Backend
                    "('Node.js & Express Basics', 'Backend Development', 'Beginner', 4.6, 25, 'Intro to server-side JS.', 'Visual Studio Code', 'WebStorm', 'HTTP & Routing|Callbacks & Event Loop|Express Middleware')," +
                    "('RESTful API Design', 'Backend Development', 'Intermediate', 4.8, 40, 'Design robust APIs.', 'Postman', 'Insomnia Premium', 'JWT Authentication|CRUD Operations|MongoDB Integration')," +
                    "('Microservices with Spring Boot', 'Backend Development', 'Advanced', 4.9, 55, 'Enterprise backend systems.', 'Eclipse', 'IntelliJ IDEA Ultimate', 'Spring Cloud Gateway|Apache Kafka Messaging|Distributed Tracing')," +
                    
                    // Cyber
                    "('Intro to Cybersecurity', 'Cybersecurity', 'Beginner', 4.8, 25, 'Security fundamentals.', 'Wireshark', 'Burp Suite Pro', 'Threat Modeling|Symmetric Encryption|Password Hashing')," +
                    "('Ethical Hacking & Pen Testing', 'Cybersecurity', 'Intermediate', 4.9, 50, 'Find vulnerabilities.', 'Kali Linux', 'Nessus Professional', 'Network Scanning (Nmap)|Web App Exploitation|Metasploit Framework')," +
                    
                    // AI
                    "('Artificial Intelligence Fundamentals', 'Artificial Intelligence', 'Beginner', 4.7, 30, 'Intro to AI.', 'Jupyter Notebook', 'Google Colab Pro', 'Search Algorithms (A*)|Propositional Logic|Intelligent Agents')," +
                    "('Advanced AI Architectures', 'Artificial Intelligence', 'Advanced', 4.9, 60, 'Cutting-edge AI.', 'Jupyter Notebook', 'Google Colab Pro', 'Transformer Models|Large Language Models (LLMs)|Generative Adversarial Networks')," +

                    // ML
                    "('Machine Learning A-Z', 'Machine Learning', 'Beginner', 4.8, 40, 'ML basics.', 'Google Colab', 'AWS SageMaker', 'Supervised Learning|Linear Regression|Scikit-Learn Basics')," +
                    "('Deep Learning with PyTorch', 'Machine Learning', 'Intermediate', 4.9, 55, 'Neural Networks.', 'Jupyter Notebook', 'Google Colab Pro', 'Convolutional Neural Networks (CNN)|Tensors & Gradients|Model Training Loops')," +

                    // Cloud
                    "('AWS Cloud Practitioner', 'Cloud Computing', 'Beginner', 4.8, 30, 'Intro to cloud.', 'AWS Free Tier', 'A Cloud Guru', 'EC2 Instances|S3 Storage|IAM Roles')," +
                    "('Docker & Kubernetes', 'Cloud Computing', 'Intermediate', 4.9, 50, 'Containerization.', 'Docker Desktop', 'Kubernetes Lens Pro', 'Dockerfiles & Images|Kubernetes Pods|Service Load Balancing')," +
                    
                    // Data Science & Analytics (Fully Expanded)
                    "('Data Science Foundations', 'Data Science', 'Beginner', 4.8, 30, 'Intro to Python and Stats.', 'Jupyter Notebook', 'DataSpell', 'Python Basics & Variables|Descriptive Statistics|Probability Distributions')," +
                    "('Data Wrangling & Analysis', 'Data Science', 'Intermediate', 4.7, 40, 'Clean and analyze data.', 'Google Colab', 'AWS SageMaker', 'Pandas DataFrames|NumPy Arrays|Data Cleaning Techniques')," +
                    "('Advanced Data Science & EDA', 'Data Science', 'Advanced', 4.9, 55, 'Discover deep insights.', 'Kaggle Kernels', 'DataSpell', 'Exploratory Data Analysis (EDA)|Feature Engineering|Advanced Scikit-Learn')," +
                    "('Data Analytics with Tableau', 'Data Analytics', 'Beginner', 4.7, 35, 'Visualize data perfectly.', 'Tableau Public', 'Tableau Desktop', 'Data Connections|Calculated Fields|Interactive Dashboards')," +
                    "('Advanced Business Analytics', 'Data Analytics', 'Advanced', 4.8, 45, 'Drive business logic.', 'PowerBI Desktop', 'PowerBI Pro', 'DAX Expressions|Data Modeling|KPI Tracking')," +

                    // UI/UX
                    "('UI/UX Design Principles', 'UI/UX Design', 'Beginner', 4.6, 25, 'Fundamentals of design.', 'Penpot', 'Figma', 'Color Theory|Typography Hierarchy|Layout Grids')," +
                    "('Figma for UI Designers', 'UI/UX Design', 'Intermediate', 4.8, 35, 'Master prototyping.', 'Figma (Free)', 'Figma Professional', 'Wireframes|Auto-layout|Interactive Prototypes')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
