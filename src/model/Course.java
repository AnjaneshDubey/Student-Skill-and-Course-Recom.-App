package model;

public class Course {
    private int courseId;
    private String courseName;
    private String category;
    private String difficulty;
    private double rating;
    private int durationHours;
    private String description;
    
    private String freeTool;
    private String premiumTool;
    private String advancedTopics;
    private String prerequisites;

    // ------ Constructor ------
    public Course(int courseId, String courseName, String category, String difficulty,
                  double rating, int durationHours, String description,
                  String freeTool, String premiumTool, String advancedTopics, String prerequisites) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.category = category;
        this.difficulty = difficulty;
        this.rating = rating;
        this.durationHours = durationHours;
        this.description = description;
        this.freeTool = freeTool;
        this.premiumTool = premiumTool;
        this.advancedTopics = advancedTopics;
        this.prerequisites = prerequisites;
    }

    // ------ Constructor (Legacy) ------
    public Course(int courseId, String courseName, String category, String difficulty,
                  double rating, int durationHours, String description) {
        this(courseId, courseName, category, difficulty, rating, durationHours, description, "Visual Studio Code", "IntelliJ IDEA", "Foundational Concepts", "Basic Computer Literacy");
    }

    // ------ Getters ------
    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public double getRating() { return rating; }
    public int getDurationHours() { return durationHours; }
    public String getDescription() { return description; }
    public String getFreeTool() { return freeTool; }
    public String getPremiumTool() { return premiumTool; }
    public String getAdvancedTopics() { return advancedTopics; }
    public String getPrerequisites() { return prerequisites; }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", rating=" + rating +
                ", durationHours=" + durationHours +
                ", description='" + description + '\'' +
                '}';
    }
}
