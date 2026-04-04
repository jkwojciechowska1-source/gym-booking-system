package pl.julia.gymapp;

import java.util.List;
import java.util.ArrayList;

import pl.julia.gymapp.model.User;
import pl.julia.gymapp.model.Training;
import pl.julia.gymapp.repository.BookingRepository;
import pl.julia.gymapp.service.BookingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BookingRepository repo = new BookingRepository();
        BookingService service = new BookingService(repo);

        Scanner scanner = new Scanner(System.in);

        List<Training> trainings = new ArrayList<>();

        trainings.add(new Training(1, "Siłowy", 5));
        trainings.add(new Training(2, "Cardio", 10));
        trainings.add(new Training(3, "Fitness", 15));

        List<User> users = new ArrayList<>();

        users.add(new User(1, "Julia"));
        users.add(new User(2, "Anna"));

        service.loadFromFile(users, trainings);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("\n1. Book training");
            System.out.println("2. Show bookings");
            System.out.println("3. Add user");
            System.out.println("4. Add training");
            System.out.println("5. Save to file");
            System.out.println("0. Exit");

            int choice;

            while (true) {
                System.out.print("Select option: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number.");
                }
            }

            if (choice == 1) {
                System.out.println("Choose user:");

                for (User u : users) {
                    System.out.println(u.getId() + ". " + u.getName());
                }

                int userChoice = Integer.parseInt(scanner.nextLine());
                User selectedUser = null;

                for (User u : users) {
                    if (u.getId() == userChoice) {
                        selectedUser = u;
                    }
                }

                if (selectedUser == null) {
                    System.out.println("Invalid user!");
                    continue;
                }

                System.out.println("Choose training:");

                for (Training t : trainings) {
                    System.out.println(t.getId() + ". " + t.getName());
                }

                int trainingChoice = Integer.parseInt(scanner.nextLine());
                Training selectedTraining = null;

                for (Training t : trainings) {
                    if (t.getId() == trainingChoice) {
                        selectedTraining = t;
                    }
                }

                if (selectedTraining == null) {
                    System.out.println("Invalid training!");
                    continue;
                }

                System.out.println(service.book(selectedUser, selectedTraining));

            } else if (choice == 2) {
                service.showBookings();

            } else if (choice == 3) {
                System.out.print("Enter user name: ");
                String name = scanner.nextLine();
                int newId = users.size() + 1;
                users.add(new User(newId, name));
                System.out.println("User added!");

            } else if (choice == 4) {
                System.out.print("Enter training name: ");
                String name = scanner.nextLine();
                System.out.print("Enter max participants: ");
                int max = Integer.parseInt(scanner.nextLine());
                int newId = trainings.size() + 1;
                trainings.add(new Training(newId, name, max));
                System.out.println("Training added!");

            } else if (choice == 5) {
                service.saveToFile();

            } else if (choice == 0) {
                break;

            } else {
                System.out.println("Invalid option! Try again.");
            }
        }
    }
}