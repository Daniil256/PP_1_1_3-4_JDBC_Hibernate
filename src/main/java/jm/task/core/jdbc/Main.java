package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Daniil", "Kryuchkov", (byte) 25);
        service.saveUser("Kristina", "Kryuchkovф", (byte) 28);
        service.saveUser("Pavel", "Vasilev", (byte) 45);
        service.saveUser("Alexnder", "Zagatskiy", (byte) 29);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
