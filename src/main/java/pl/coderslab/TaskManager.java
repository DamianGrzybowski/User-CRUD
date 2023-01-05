package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.SQLException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        User user = new User();


//        System.out.println("Tworzenie nowego użytkownika");
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Podaj imię");
//        user.setUserName(scanner.nextLine());
//        System.out.println("Podaj email");
//        user.setEmail(scanner.nextLine());
//        System.out.println("Podaj hasło");
//        user.setPassword(scanner.nextLine());
        System.out.println("Podaj id");
        UserDao userDao = new UserDao();
        int id = scanner.nextInt();
        if (userDao.read(id) != null) {
            user = userDao.read(id);
            System.out.println(user.getId());
            System.out.println(user.getUserName());
            System.out.println(user.getEmail());
        } else {
            System.out.println("Nie ma użytkownika o id: " + id);
        }


    }
}
