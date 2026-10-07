package ui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Course;
import service.CourseService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class WebServer {
    private static final CourseService service = new CourseService();
    private static final String WEB_DIR = "web";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Static File Handlers
        server.createContext("/", new StaticFileHandler("index.html", "text/html"));
        server.createContext("/styles.css", new StaticFileHandler("styles.css", "text/css"));
        server.createContext("/app.js", new StaticFileHandler("app.js", "application/javascript"));
        
        // JSON API endpoint
        server.createContext("/api/recommend", new ApiRecommendHandler());
        
        server.setExecutor(null);
        server.start();
        System.out.println("Vidyexa Server started on http://localhost:8080/");
    }

    static class StaticFileHandler implements HttpHandler {
        private String fileName;
        private String contentType;

        public StaticFileHandler(String fileName, String contentType) {
            this.fileName = fileName;
            this.contentType = contentType;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File(WEB_DIR + "/" + fileName);
            if (file.exists()) {
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(file.toPath(), os);
                os.close();
            } else {
                String response = "404 (Not Found)";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    static class ApiRecommendHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String category = "";
            String difficulty = "";
            
            if (query != null) {
                String[] params = query.split("&");
                for (String param : params) {
                    String[] pair = param.split("=");
                    if (pair.length == 2) {
                        if (pair[0].equals("category")) category = pair[1].replace("+", " ");
                        if (pair[0].equals("difficulty")) difficulty = pair[1].replace("+", " ");
                    }
                }
            }
            
            ArrayList<Course> recs = service.recommendCourses(category, difficulty);
            
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < recs.size(); i++) {
                Course c = recs.get(i);
                json.append("{")
                    .append("\"id\":").append(c.getCourseId()).append(",")
                    .append("\"name\":\"").append(escapeJson(c.getCourseName())).append("\",")
                    .append("\"category\":\"").append(escapeJson(c.getCategory())).append("\",")
                    .append("\"difficulty\":\"").append(escapeJson(c.getDifficulty())).append("\",")
                    .append("\"rating\":").append(c.getRating()).append(",")
                    .append("\"duration\":").append(c.getDurationHours()).append(",")
                    .append("\"description\":\"").append(escapeJson(c.getDescription())).append("\",")
                    .append("\"freeTool\":\"").append(escapeJson(c.getFreeTool())).append("\",")
                    .append("\"premiumTool\":\"").append(escapeJson(c.getPremiumTool())).append("\",")
                    .append("\"advancedTopics\":\"").append(escapeJson(c.getAdvancedTopics())).append("\"")
                    .append("}");
                if (i < recs.size() - 1) json.append(",");
            }
            json.append("]");

            byte[] response = json.toString().getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
        
        private String escapeJson(String s) {
            if (s == null) return "";
            return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
        }
    }
}
