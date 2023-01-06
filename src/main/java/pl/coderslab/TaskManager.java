package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        User user = new User();
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome in UserDao app");
        options();

        String input = scanner.nextLine();
        while (!"6".equals(input)) {
            switch (input) {
                case "1" -> {
                    System.out.println("Please enter a User Name");
                    user.setUserName(scanner.nextLine());
                    System.out.println("Please enter an email");
                    user.setEmail(scanner.nextLine());
                    System.out.println("Please enter a password");
                    user.setPassword(scanner.nextLine());
                    userDao.create(user);
                    System.out.println("User has been added correctly");
                }
                case "2" -> {
                    System.out.println("Please enter an ID");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        user = userDao.read(id);
                        System.out.println("User ID = " + user.getId());
                        System.out.println("UserName = " + user.getUserName());
                        System.out.println("Email = " + user.getEmail());


                    } catch (NumberFormatException e) {
                        System.out.println("Please enter correct ID");
                    }
                }
                case "3" -> {
                    System.out.println("Please enter an ID");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        user = userDao.read(id);
                        userDao.update(user);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter correct ID");
                    }

                }
                case "4" -> {
                    System.out.println("Please enter an ID");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        userDao.delete(id);
                        System.out.println("User has been deleted successfully");

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter correct ID");
                    }

                }
                case "5" -> userDao.findAll();

                default -> {
                    System.out.println("Please give a correct option");
                }

            }
            System.out.println();
            options();
            input = scanner.nextLine();
        }
        System.out.println("App closed. Good Bye!");


    }

    private static void options() {
        System.out.println("Please choose an option");
        System.out.println("1. Create user");
        System.out.println("2. Read user by ID");
        System.out.println("3. Update user");
        System.out.println("4. Delete user by ID");
        System.out.println("5. Find All users");
        System.out.println("6. Exit");

    }
}
