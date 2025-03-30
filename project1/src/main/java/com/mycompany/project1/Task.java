package com.mycompany.project1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L; // Fixed serialVersionUID

    private String description;
    private boolean done;
    private String priority; // High, Medium, Low
    private String category; // Work, Personal, Shopping, etc.
    private String dueDate; // Due date in days or hours (e.g., "2 days", "5 hours")
    private String notes; // Additional notes for the task
    private List<String> tags; // Tags for better organization

    public Task(String description, String priority, String category, String dueDate, String notes, List<String> tags) {
        this.description = description;
        this.done = false;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.notes = notes;
        this.tags = tags;
    }

    // Getters and setters
    public String getdesc() { 
        return description; 
    }
    public void setdesc(String description) {
        this.description = description;
    }
    public boolean isdone() {
        return done; 
    }
    public void checkmark() {
        this.done = true; 
    }
    public String getPriority() { 
        return priority; 
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getCategory() {
        return category; 
    }
    public void setCategory(String category) {
        this.category = category; 
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) { 
        this.dueDate = dueDate; 
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Mark method to display task status
    public String Mark() {
        String status = done ? "[✓] " : "[ ] "; // Fixed status display
        String dueDateStr = dueDate != null && !dueDate.isEmpty() ? " (Due: " + dueDate + ")" : "";
        String notesStr = notes != null && !notes.isEmpty() ? " [Notes: " + notes + "]" : "";
        String tagsStr = tags != null && !tags.isEmpty() ? " [Tags: " + String.join(", ", tags) + "]" : "";
        return status + description + " [Priority: " + priority + ", Category: " + category + "]" + dueDateStr + notesStr + tagsStr;
    }
}