import controller.DiaryController;
import data.repositories.CustomerException;
import dtos.LogInRequest;
import dtos.RegisterUserRequest;

import java.util.Scanner;

import static java.lang.System.*;

public class DiaryMain {
    private static DiaryController diaryController = new DiaryController();
    private static String currentUser;
    public static void main(String[] args) throws CustomerException {
        displayMainMenu();
        }

        private static void displayMainMenu() throws CustomerException {
            String menu = """
                Hi! Welcome.
                What do you want to do?
                1. Create Account
                2. Log in
                3. Exit
                
                Enter:\s""";

            char userInput = input(menu).charAt(0);
            switch (userInput){
                case '1' -> registerAccount();
                case '2' -> logIn();
                case '3' -> exitApplication();
                default -> displayMainMenu();
            }
        }

        private static void exitApplication() {
            print("Thank you for using this application");
            exit(99);
        }



        private static void logIn() throws CustomerException {
            String username = input("Enter username: ");
            String password = input("Enter password: ");
            LogInRequest logInRequest = new LogInRequest();
            logInRequest.setPassword(password);
            logInRequest.setUsername(username);
            String login = diaryController.unlock(logInRequest);
            if (login.equals("Incorrect Password") || login.equals("Diary Not Found")) {
                print(login);
                logIn();
            }else {
                currentUser = username;
                print(login);
                displayDiaryMenu();
            }
        }

        private static void displayDiaryMenu() throws CustomerException {
            String menu = """
                Diary Menu:
                1. Create Entry
                2. Find Entry
                3. Update Entry
                4. Log out
                
                Enter:\s""";

            char userInput = input(menu).charAt(0);
            switch (userInput){
                case '1' -> createEntry();
                case '2' -> findEntry();
                case '3' -> logOut();
                default -> displayDiaryMenu();
            }
        }

        private static void logOut() throws CustomerException {
            String locked = diaryController.lock(currentUser);
            print(locked);
            displayMainMenu();
        }

        private static void findEntry() {
            String title = input("Enter title: ");
            String foundEntry = diaryController.findEntry(currentUser, title);
            print(foundEntry);
        }

        private static void createEntry() {
            String title = input("Enter title: ");
            String body = input("Enter body: ");
            String entryCreated = diaryController.createEntry(currentUser, title, body);
            print(entryCreated);
        }

        private static void registerAccount() throws CustomerException {
            String username = input("Enter username: ");
            String password = input("Enter password: ");
            RegisterUserRequest registerUserRequest = new RegisterUserRequest();
            registerUserRequest.setUsername(username);
            registerUserRequest.setPassword(password);
            String registered = diaryController.register(registerUserRequest);
            print(registered);
            displayMainMenu();
        }

        private static String input(String prompt) {
            out.print(prompt);
            Scanner keyboardInput = new Scanner(in);
            return keyboardInput.nextLine();
        }

        private static void print(String message) {
            out.println(message);
        }
}
