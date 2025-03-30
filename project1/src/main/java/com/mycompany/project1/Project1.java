package com.mycompany.project1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Project1 {
    static class Clist {
        private List<Task> tasks = new ArrayList<>();
        private List<Task> archivedTasks = new ArrayList<>(); // Store archived tasks

        // Add a new task with priority, category, due date, notes, and tags
        public void addTask(String description, String priority, String category, String dueDate, String notes, List<String> tags) {
            tasks.add(new Task(description, priority, category, dueDate, notes, tags));
            System.out.println("Task added successfully.");
        }

        // Delete a task by its number
        public boolean deleteTask(int taskNumber) {
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number. Please try again.");
                return false;
            }
            Task removedTask = tasks.remove(taskNumber - 1); // Convert to zero-based index
            System.out.println("Task '" + removedTask.getdesc() + "' deleted successfully.");
            return true;
        }

        // Archive a completed task
        public void archiveTask(int taskNumber) {
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number. Please try again.");
                return;
            }
            Task task = tasks.get(taskNumber - 1);
            if (task.isdone()) {
                archivedTasks.add(task);
                tasks.remove(taskNumber - 1);
                System.out.println("Task '" + task.getdesc() + "' archived successfully.");
            } else {
                System.out.println("Task '" + task.getdesc() + "' is not completed and cannot be archived.");
            }
        }

        // Rearrange tasks
        public void rearrange(int m, int n) {
            m = m - 1; // Convert to zero-based index
            n = n - 1; // Convert to zero-based index

            // Check if positions are valid
            if (m < 0 || m >= tasks.size() || n < 0 || n >= tasks.size()) {
                System.out.println("Invalid Position, Please try again.");
                return;
            }

            // Swap the Task objects at positions m and n
            Task temp = tasks.get(m);
            tasks.set(m, tasks.get(n));
            tasks.set(n, temp);

            System.out.println("Tasks rearranged successfully.");
            showTasks();
        }

        // Mark a task as done
        public void marktask(int ntask) {
            if (ntask < 1 || ntask > tasks.size()) {
                System.out.println("Invalid task number. Please try again.");
                return;
            }
            Task task = tasks.get(ntask - 1);
            task.checkmark();
            System.out.println("Task has been marked: " + task.getdesc());
        }

        // Edit a task
        public void editTask(int taskNumber, String newDescription, String newPriority, String newCategory, String newDueDate, String newNotes, List<String> newTags) {
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number. Please try again.");
                return;
            }
            Task task = tasks.get(taskNumber - 1);
            task.setdesc(newDescription);
            task.setPriority(newPriority);
            task.setCategory(newCategory);
            task.setDueDate(newDueDate);
            task.setNotes(newNotes);
            task.setTags(newTags);
            System.out.println("Task updated successfully.");
        }

        // Search tasks by keyword
        public void searchTasks(String keyword) {
            System.out.println("Search results:");
            for (Task task : tasks) {
                if (task.getdesc().contains(keyword)) {
                    System.out.println(task.Mark());
                }
            }
        }

        // Show all tasks
        public void showTasks() {
            System.out.println("\nTasks List:");
            if (tasks.isEmpty()) {
                System.out.println("No tasks added yet.");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ") " + tasks.get(i).Mark());
                }
            }
        }

        // Show archived tasks
        public void showArchivedTasks() {
            System.out.println("\nArchived Tasks List:");
            if (archivedTasks.isEmpty()) {
                System.out.println("No tasks archived yet.");
            } else {
                for (int i = 0; i < archivedTasks.size(); i++) {
                    System.out.println((i + 1) + ") " + archivedTasks.get(i).Mark());
                }
            }
        }

        // Save tasks to a file
        public void saveTasks(String filename) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(tasks);
                oos.writeObject(archivedTasks);
                System.out.println("Tasks saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving tasks: " + e.getMessage());
            }
        }

        // Load tasks from a file
        public void loadTasks(String filename) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                tasks = (List<Task>) ois.readObject();
                archivedTasks = (List<Task>) ois.readObject();
                System.out.println("Tasks loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
            }
        }

        // Sort tasks by priority
        public void sortTasksByPriority() {
            tasks.sort((t1, t2) -> t1.getPriority().compareToIgnoreCase(t2.getPriority()));
            System.out.println("Tasks sorted by priority.");
            showTasks();
        }

        // Sort tasks by due date
        public void sortTasksByDueDate() {
            tasks.sort((t1, t2) -> t1.getDueDate().compareToIgnoreCase(t2.getDueDate()));
            System.out.println("Tasks sorted by due date.");
            showTasks();
        }

        // Filter tasks by category
        public void filterTasksByCategory(String category) {
            System.out.println("Filtered tasks by category '" + category + "':");
            for (Task task : tasks) {
                if (task.getCategory().equalsIgnoreCase(category)) {
                    System.out.println(task.Mark());
                }
            }
        }

        // Filter tasks by priority
        public void filterTasksByPriority(String priority) {
            System.out.println("Filtered tasks by priority '" + priority + "':");
            for (Task task : tasks) {
                if (task.getPriority().equalsIgnoreCase(priority)) {
                    System.out.println(task.Mark());
                }
            }
        }

        // Show task statistics
        public void showTaskStatistics() {
            int completed = 0;
            int pending = 0;
            for (Task task : tasks) {
                if (task.isdone()) {
                    completed++;
                } else {
                    pending++;
                }
            }
            System.out.println("Task Statistics:");
            System.out.println("Completed tasks: " + completed);
            System.out.println("Pending tasks: " + pending);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Clist Task = new Clist();

        // Load tasks from file (if any)
        Task.loadTasks("tasks.dat");

        while (true) {
            System.out.println("\n1) Add new task\n2) Show all tasks\n3) Delete a task\n4) Rearrange the list\n5) Mark a task as done\n6) Edit a task\n7) Search tasks\n8) Archive a task\n9) Show archived tasks\n10) Save tasks\n11) Sort tasks by priority\n12) Sort tasks by due date\n13) Filter tasks by category\n14) Filter tasks by priority\n15) Show task statistics\n16) Exit");
            System.out.print("Enter your choice: ");
            int choice = -1;

            // Loop until a valid integer is entered
            while (true) {
                try {
                    choice = input.nextInt();
                    input.nextLine(); // Clear the buffer
                    break; // Exit the loop if input is valid
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 16.");
                    input.nextLine(); // Clear the invalid input from the buffer
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Please enter the task: ");
                    String taskDescription = input.nextLine();
                    System.out.print("Enter task priority (High, Medium, Low): ");
                    String priority = input.nextLine();
                    System.out.print("Enter task category (Work, Personal, Shopping, etc.): ");
                    String category = input.nextLine();
                    System.out.print("Enter due date (e.g., '2 days' or '5 hours', or leave blank if none): ");
                    String dueDate = input.nextLine();
                    System.out.print("Enter notes (optional): ");
                    String notes = input.nextLine();
                    System.out.print("Enter tags (comma-separated, optional): ");
                    String tagsInput = input.nextLine();
                    List<String> tags = new ArrayList<>();
                    if (!tagsInput.isEmpty()) {
                        for (String tag : tagsInput.split(",")) {
                            tags.add(tag.trim());
                        }
                    }

                    Task.addTask(taskDescription, priority, category, dueDate, notes, tags);
                    Task.showTasks();
                    break;

                case 2:
                    Task.showTasks();
                    break;

                case 3:
                    System.out.println("Enter the task number to delete:");
                    int taskToDelete = input.nextInt();
                    Task.deleteTask(taskToDelete);
                    Task.showTasks();
                    break;

                case 4:
                    System.out.println("Choose the positions of the two tasks you want to swap:");
                    int m = input.nextInt();
                    int n = input.nextInt();
                    Task.rearrange(m, n);
                    break;

                case 5:
                    System.out.println("Enter the task number to mark as done:");
                    int taskNumber = input.nextInt();
                    Task.marktask(taskNumber);
                    Task.showTasks();
                    break;

                case 6:
                    System.out.println("Enter the task number to edit:");
                    int taskToEdit = input.nextInt();
                    input.nextLine(); // Clear the buffer
                    System.out.println("Enter the new task description:");
                    String newDescription = input.nextLine();
                    System.out.println("Enter the new task priority (High, Medium, Low): ");
                    String newPriority = input.nextLine();
                    System.out.println("Enter the new task category (Work, Personal, Shopping, etc.): ");
                    String newCategory = input.nextLine();
                    System.out.println("Enter the new due date (e.g., '2 days' or '5 hours', or leave blank if none): ");
                    String newDueDate = input.nextLine();
                    System.out.println("Enter the new notes (optional): ");
                    String newNotes = input.nextLine();
                    System.out.println("Enter the new tags (comma-separated, optional): ");
                    String newTagsInput = input.nextLine();
                    List<String> newTags = new ArrayList<>();
                    if (!newTagsInput.isEmpty()) {
                        for (String tag : newTagsInput.split(",")) {
                            newTags.add(tag.trim());
                        }
                    }
                    Task.editTask(taskToEdit, newDescription, newPriority, newCategory, newDueDate, newNotes, newTags);
                    Task.showTasks();
                    break;

                case 7:
                    System.out.println("Enter a keyword to search for tasks:");
                    String keyword = input.nextLine();
                    Task.searchTasks(keyword);
                    break;

                case 8:
                    System.out.println("Enter the task number to archive:");
                    int taskToArchive = input.nextInt();
                    Task.archiveTask(taskToArchive);
                    Task.showTasks();
                    break;

                case 9:
                    Task.showArchivedTasks();
                    break;

                case 10:
                    Task.saveTasks("tasks.dat");
                    break;

                case 11:
                    Task.sortTasksByPriority();
                    break;

                case 12:
                    Task.sortTasksByDueDate();
                    break;

                case 13:
                    System.out.println("Enter the category to filter by:");
                    String filterCategory = input.nextLine();
                    Task.filterTasksByCategory(filterCategory);
                    break;

                case 14:
                    System.out.println("Enter the priority to filter by:");
                    String filterPriority = input.nextLine();
                    Task.filterTasksByPriority(filterPriority);
                    break;

                case 15:
                    Task.showTaskStatistics();
                    break;

                case 16:
                    System.out.println("Exiting...");
                    Task.saveTasks("tasks.dat"); // Save tasks before exiting
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 16.");
            }
        }
    }
}