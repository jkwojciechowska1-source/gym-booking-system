package pl.julia.gymapp.service;

import pl.julia.gymapp.model.Booking;
import pl.julia.gymapp.model.Training;
import pl.julia.gymapp.model.User;
import pl.julia.gymapp.repository.BookingRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class BookingService {
    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public String book(User user, Training training) {

        for (Booking b : repository.getAllBookings()) {
            if (b.getUser().getId() == user.getId() && b.getTraining().getId() == training.getId()) {
                return "Already booked!";
            }
        }

        int count = 0;
        for (Booking b : repository.getAllBookings()) {
            if (b.getTraining().getId() == training.getId()) {
                count++;
            }
        }

        if (count >= training.getMaxParticipants()) {
            return "No free spots!";
        }

        repository.addBooking(new Booking(user, training));
        return "Booked successfully!";
    }

    public void showBookings() {
        List<Booking> bookings = repository.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }

        System.out.println("Bookings:");
        for (Booking b : bookings) {
            System.out.println("User: " + b.getUser().getName() + " | Training: " + b.getTraining().getName());
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("bookings.txt")) {

            for (Booking b : repository.getAllBookings()) {
                writer.write(
                        b.getUser().getId() + "," + b.getTraining().getId() + "\n");
            }

            System.out.println("Bookings saved to file!");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile(List<User> users, List<Training> trainings) {
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int userId = Integer.parseInt(parts[0]);
                int trainingId = Integer.parseInt(parts[1]);

                User foundUser = null;
                Training foundTraining = null;

                for (User u : users) {
                    if (u.getId() == userId) {
                        foundUser = u;
                    }
                }

                for (Training t : trainings) {
                    if (t.getId() == trainingId) {
                        foundTraining = t;
                    }
                }

                if (foundUser != null && foundTraining != null) {
                    repository.addBooking(new Booking(foundUser, foundTraining));
                }
            }

            System.out.println("Bookings loaded from file!");

        } catch (IOException e) {
            System.out.println("No file found or error reading.");
        }
    }
}
