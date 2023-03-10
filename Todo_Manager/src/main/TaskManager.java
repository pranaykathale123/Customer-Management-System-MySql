package main;

import java.util.List;
import java.util.Scanner;

import DBConnection.DbConnection;
import dao.TaskDao;
import dao.UserDao;
import model.Task;
import model.User;

public class TaskManager {
    private static Scanner scanner = new Scanner(System.in);
    static TaskDao taskDao = new TaskDao();
    static UserDao userDao = new UserDao();
    static User currentUser = new User();

    public static void main(String[] args) throws Exception {
    	DbConnection.getConnection();
        showLoginMenu();
    }

    private static void showLoginMenu() throws Exception {
        int choice = -1;
        while (choice != 0) {
            System.out.println("1. Login");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter your email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String password = scanner.nextLine();
                    boolean flag =false;
                    userDao = new UserDao();
                    flag = userDao.login(email,password);
                    if (flag) {
                    	currentUser.setEmail(email);
                        showMainMenu();
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static void showMainMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("1. Add task");
            System.out.println("2. Update task");
            System.out.println("3. Delete task");
            System.out.println("4. Search task");
            System.out.println("5. View all tasks");
            System.out.println("6. View completed tasks");
            System.out.println("7. View incomplete tasks");
            System.out.println("0. Logout");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    updateTask();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    searchTask();
                    break;
                case 5:
                    viewAllTasks();
                    break;
                case 6:
                    viewCompletedTasks();
                    break;
                case 7:
                    viewIncompleteTasks();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static void addTask() {
    	//System.out.println(currentUser.getEmail());
    	System.out.println("Enter the task Id");
    	int taskId = scanner.nextInt();
    	scanner.nextLine(); 
        System.out.println("Enter task title:");
        String title = scanner.nextLine();
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
        System.out.println("Enter task Status");
        boolean status = scanner.nextBoolean();
        
        Task task = new Task(taskId,title, description, currentUser.getEmail(),status);
        System.out.println(task.getAssignedTo());
        System.out.println(task.getTaskId());
        System.out.println(task.getTaskText());
        System.out.println(task.getTaskTitle());
        try {
			taskDao.addTask(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Task added successfully!");
    }

    private static void updateTask() {
        System.out.println("Enter task ID:");
        int taskId = scanner.nextInt();
        scanner.nextLine();
        Task task=null;
		try {
			task = taskDao.getTask(taskId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        if (task != null && task.getAssignedTo().equals(currentUser.getEmail())) {
            System.out.println("Enter new task title:");
            String title = scanner.nextLine();
            System.out.println("Enter new task description:");
            String description = scanner.nextLine();

            task.setTaskTitle(title);
            task.setTaskText(description);
            try {
				taskDao.updateTask(task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Invalid task ID or you are not authorized to update this task!");
        }
    }

    private static void deleteTask() {
        System.out.println("Enter task ID:");
        int taskId = scanner.nextInt();
        scanner.nextLine();
        Task task=null;
		try {
			task = taskDao.getTask(taskId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(task.getTaskId()!=0) {
        	if (task.getAssignedTo().equals(currentUser.getEmail())) {
            	try {
					taskDao.deleteTask(taskId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	System.out.println("Task deleted successfully!");
            }
        }
         else {
        	System.out.println("Invalid task ID or you are not authorized to delete this task!");
        }
    }
    private static void searchTask() {
        System.out.println("Enter search keyword:");
        String keyword = scanner.nextLine();
        System.out.println("Search results:");
        try {
			List<Task> result = taskDao.searchTasks(keyword,currentUser.getEmail());
			for(Task task:result) {
				System.out.println(task);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void viewAllTasks() {
        System.out.println("All tasks:");
        try {
			taskDao.getAllTasks().forEach(System.out::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void viewCompletedTasks() {
        System.out.println("Completed tasks:");
        taskDao.getTasksByStatus(true).forEach(System.out::println);
    }

    private static void viewIncompleteTasks() {
        System.out.println("Incomplete tasks:");
        taskDao.getIncompleteTasksByStatus(false).forEach(System.out::println);
    }
    
}


