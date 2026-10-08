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
                    "advanced_topics TEXT, " +
                    "prerequisites VARCHAR(255))";
            stmt.execute(sql);
            
            // Seed extended data with tools, advanced topics, and prerequisites
            stmt.execute("INSERT INTO courses (name, category, difficulty, rating, duration, description, free_tool, premium_tool, advanced_topics, prerequisites) VALUES " +
                    // Frontend
                    "('HTML & CSS Basics', 'Frontend Development', 'Beginner', 4.7, 20, 'Learn the building blocks of the web.', 'Visual Studio Code', 'WebStorm', 'Semantic HTML|Flexbox & Grid Layouts|Responsive Media Queries|CSS Variables|Web Accessibility (a11y)|Browser DevTools|Basic DOM Trees|HTML5 Forms', 'Basic Computer Literacy|Web Navigation')," +
                    "('JavaScript Essentials', 'Frontend Development', 'Beginner', 4.8, 35, 'Master vanilla JS.', 'Visual Studio Code', 'WebStorm', 'DOM Manipulation|ES6+ Features|Asynchronous Fetch API|Promises & Async/Await|Closures & Scope|Event Bubbling|Local Storage|Basic RegEx', 'HTML & CSS Basics|Basic Logic')," +
                    "('React.js Front to Back', 'Frontend Development', 'Intermediate', 4.9, 45, 'Build modern UI.', 'Visual Studio Code', 'WebStorm', 'React Hooks (useState, useEffect)|Context API|React Router DOM|Component Lifecycle|State Management|Prop Drilling|Custom Hooks|React Suspense', 'JavaScript Essentials|HTML/CSS|ES6+ syntax')," +
                    "('Advanced Frontend Architecture', 'Frontend Development', 'Advanced', 4.9, 45, 'Scale your apps.', 'Visual Studio Code', 'WebStorm', 'Redux State Management|Micro-frontends|Webpack & Vite Config|Server-Side Rendering (SSR)|Next.js Integration|WebSockets|Performance Optimization|GraphQL Integration', 'React.js Basics|Advanced JS|HTTP Protocols')," +
                    
                    // Backend
                    "('Node.js & Express Basics', 'Backend Development', 'Beginner', 4.6, 25, 'Intro to server-side JS.', 'Visual Studio Code', 'WebStorm', 'HTTP & Routing|Callbacks & Event Loop|Express Middleware|File System API|RESTful Principles|NPM & Package Management|Environment Variables|Basic Security', 'JavaScript Basics|Basic Command Line')," +
                    "('RESTful API Design', 'Backend Development', 'Intermediate', 4.8, 40, 'Design robust APIs.', 'Postman', 'Insomnia Premium', 'JWT Authentication|CRUD Operations|MongoDB Integration|Mongoose Models|Rate Limiting|Data Validation (Joi)|Error Handling Middlewares|API Documentation', 'Node.js Basics|Express Framework|JSON Basics')," +
                    "('Microservices with Spring Boot', 'Backend Development', 'Advanced', 4.9, 55, 'Enterprise backend systems.', 'Eclipse', 'IntelliJ IDEA Ultimate', 'Spring Cloud Gateway|Apache Kafka Messaging|Distributed Tracing|Eureka Service Discovery|Circuit Breaker (Resilience4j)|Dockerizing Spring Boot|Centralized Configuration|OAuth2 Security', 'Core Java|Spring Boot Basics|SQL Databases')," +
                    
                    // Cyber
                    "('Intro to Cybersecurity', 'Cybersecurity', 'Beginner', 4.8, 25, 'Security fundamentals.', 'Wireshark', 'Burp Suite Pro', 'Threat Modeling|Symmetric Encryption|Password Hashing|Phishing Awareness|OSINT Basics|Basic Network Topologies|Malware Types|Social Engineering', 'Basic Computer Literacy|Basic Networking Concepts')," +
                    "('Ethical Hacking & Pen Testing', 'Cybersecurity', 'Intermediate', 4.9, 50, 'Find vulnerabilities.', 'Kali Linux', 'Nessus Professional', 'Network Scanning (Nmap)|Web App Exploitation|Metasploit Framework|SQL Injection|Cross-Site Scripting (XSS)|Privilege Escalation|Buffer Overflows|Wireshark Packet Analysis', 'Intro to Cybersecurity|Linux Command Line|Networking Protocols')," +
                    
                    // AI
                    "('Artificial Intelligence Fundamentals', 'Artificial Intelligence', 'Beginner', 4.7, 30, 'Intro to AI.', 'Jupyter Notebook', 'Google Colab Pro', 'Search Algorithms (A*)|Propositional Logic|Intelligent Agents|Heuristics|State Space Search|Game Trees (Minimax)|Knowledge Representation|Expert Systems', 'Basic Math|Basic Python|Logic Fundamentals')," +
                    "('Advanced AI Architectures', 'Artificial Intelligence', 'Advanced', 4.9, 60, 'Cutting-edge AI.', 'Jupyter Notebook', 'Google Colab Pro', 'Transformer Models|Large Language Models (LLMs)|Generative Adversarial Networks (GANs)|Reinforcement Learning|Autoencoders|Self-Attention Mechanisms|Transfer Learning|Model Deployment', 'Python Mastery|Calculus/Linear Algebra|Deep Learning Basics')," +

                    // ML
                    "('Machine Learning A-Z', 'Machine Learning', 'Beginner', 4.8, 40, 'ML basics.', 'Google Colab', 'AWS SageMaker', 'Supervised Learning|Linear Regression|Scikit-Learn Basics|Decision Trees|K-Nearest Neighbors (KNN)|Model Evaluation|Cross-Validation|Basic Data Preprocessing', 'Core Math/Stats|Basic Pandas/NumPy|Python Basics')," +
                    "('Deep Learning with PyTorch', 'Machine Learning', 'Intermediate', 4.9, 55, 'Neural Networks.', 'Jupyter Notebook', 'Google Colab Pro', 'Convolutional Neural Networks (CNN)|Tensors & Gradients|Model Training Loops|Recurrent Neural Networks (RNN)|LSTMs|Backpropagation|PyTorch Basics|Optimization Algorithms', 'Machine Learning Basics|Advanced Calculus|Python')," +

                    // Cloud
                    "('AWS Cloud Practitioner', 'Cloud Computing', 'Beginner', 4.8, 30, 'Intro to cloud.', 'AWS Free Tier', 'A Cloud Guru', 'EC2 Instances|S3 Storage|IAM Roles|VPC Networking & Subnets|RDS Database Setup|CloudWatch Monitoring|AWS Lambda|Route 53|Security Groups', 'Basic Networking|Web Protocols|Command Line Basics')," +
                    "('Docker & Kubernetes', 'Cloud Computing', 'Intermediate', 4.9, 50, 'Containerization.', 'Docker Desktop', 'Kubernetes Lens Pro', 'Dockerfiles & Images|Kubernetes Pods|Service Load Balancing|Docker Compose|Volumes & Persistent Data|K8s Deployments|Ingress Controllers|Helm Charts', 'Cloud Fundamentals|Linux OS Basics|YAML/JSON')," +
                    
                    // Data Science & Analytics (Fully Expanded)
                    "('Data Science Foundations', 'Data Science', 'Beginner', 4.8, 30, 'Intro to Python and Stats.', 'Jupyter Notebook', 'DataSpell', 'Python Basics & Variables|Descriptive Statistics|Probability Distributions|Jupyter Environments|Data Types|Basic Plotting (Matplotlib)|Hypothesis Testing|Inferential Stats', 'Basic Math|Basic Computer Literacy')," +
                    "('Data Wrangling & Analysis', 'Data Science', 'Intermediate', 4.7, 40, 'Clean and analyze data.', 'Google Colab', 'AWS SageMaker', 'Pandas DataFrames|NumPy Arrays|Data Cleaning Techniques|Handling Missing Data|Data Merging & Joining|Time Series Basics|Seaborn Visualization|Data Transformation', 'Data Science Foundations|Python Basics|Basic Stats')," +
                    "('Advanced Data Science & EDA', 'Data Science', 'Advanced', 4.9, 55, 'Discover deep insights.', 'Kaggle Kernels', 'DataSpell', 'Exploratory Data Analysis (EDA)|Feature Engineering|Advanced Scikit-Learn|Dimensionality Reduction (PCA)|Ensemble Methods|Hyperparameter Tuning|Pipeline Construction|Model Interpretability', 'Data Wrangling|Machine Learning Basics|Advanced Python')," +
                    "('Data Analytics with Tableau', 'Data Analytics', 'Beginner', 4.7, 35, 'Visualize data perfectly.', 'Tableau Public', 'Tableau Desktop', 'Data Connections|Calculated Fields|Interactive Dashboards|Tableau Interface|Data Blending|Basic Chart Types|Filtering & Sorting|Storytelling with Data', 'Basic Excel/Spreadsheets|Basic Data Concepts')," +
                    "('Advanced Business Analytics', 'Data Analytics', 'Advanced', 4.8, 45, 'Drive business logic.', 'PowerBI Desktop', 'PowerBI Pro', 'DAX Expressions|Data Modeling|KPI Tracking|PowerQuery Transformations|Advanced Visualizations|Row-Level Security|Scheduled Refreshes|Integrating R/Python Scripts', 'Data Analytics Basics|SQL Queries|Data Warehousing Concepts')," +

                    // UI/UX
                    "('UI/UX Design Principles', 'UI/UX Design', 'Beginner', 4.6, 25, 'Fundamentals of design.', 'Penpot', 'Figma', 'Color Theory|Typography Hierarchy|Layout Grids|User Personas|Wireframing Basics|Accessibility Guidelines|Visual Balance|UI Components', 'Basic Design Concepts|Creativity')," +
                    "('Figma for UI Designers', 'UI/UX Design', 'Intermediate', 4.8, 35, 'Master prototyping.', 'Figma (Free)', 'Figma Professional', 'Wireframes|Auto-layout|Interactive Prototypes|Design Systems|Component Variants|Micro-interactions|Figma Plugins|Developer Handoff', 'UI/UX Principles|Design Tool Familiarity')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
