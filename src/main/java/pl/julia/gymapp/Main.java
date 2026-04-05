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

        trainings.add(new Training(1, "Strength Training", 5));
        trainings.add(new Training(2, "Full Body Workout", 10));
        trainings.add(new Training(3, "Fitness Class", 15));

        List<User> users = new ArrayList<>();

        users.add(new User(1, "Julia"));
        users.add(new User(2, "Anna"));

        service.loadFromFile(users, trainings);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Book training");
            System.out.println("2. Show bookings");
            System.out.println("3. Add user");
            System.out.println("4. Add training");
            System.out.println("5. Save to file");
            System.out.println("0. Exit\n");

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

                User selectedUser = chooseUser(users, scanner);
                if (selectedUser == null) continue;

                Training selectedTraining = chooseTraining(trainings, scanner);
                if (selectedTraining == null) continue;

                System.out.println(service.book(selectedUser, selectedTraining));

            } else if (choice == 2) {
                service.showBookings();

            } else if (choice == 3) {

                String name;

                while (true) {
                    System.out.print("Enter user name: ");
                    name = scanner.nextLine();

                    if (name.matches("[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]+")) {
                        break;
                    } else {
                        System.out.println("Invalid name! Use only letters.");
                    }
                }

                int newId = users.size() + 1;
                users.add(new User(newId, name));
                System.out.println("User added!");

            } else if (choice == 4) {

            String name;

            while (true) {
                System.out.print("Enter training name: ");
                name = scanner.nextLine();

                if (name.matches("[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]+")) {
                    break;
                } else {
                    System.out.println("Invalid name! Use only letters.");
                }
            }

                int max;

                while (true) {
                    System.out.print("Enter max participants: ");
                    try {
                        max = Integer.parseInt(scanner.nextLine());

                        if (max > 0) {
                            break;
                        } else {
                            System.out.println("Number must be greater than 0.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Enter a number.");
                    }
                }

            } else if (choice == 5) {
                service.saveToFile();

            } else if (choice == 0) {
                break;

            } else {
                System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static User chooseUser(List<User> users, Scanner scanner) {
        System.out.println("Choose user: ");

        for (User u : users) {
            System.out.println(u.getId() + ". " + u.getName());
        }

        int userChoice;

        while (true) {
            try {
                userChoice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        for (User u : users) {
            if (u.getId() == userChoice) {
                return u;
            }
        }

        System.out.println("Invalid user!");
        return null;
    }

    private static Training chooseTraining(List<Training> trainings, Scanner scanner) {
        System.out.println("Choose training:");

        for (Training t : trainings) {
            System.out.println(t.getId() + ". " + t.getName());
        }

        int trainingChoice;

        while (true) {
            try {
                trainingChoice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        for (Training t : trainings) {
            if (t.getId() == trainingChoice) {
                return t;
            }
        }

        System.out.println("Invalid training!");
        return null;
    }
}