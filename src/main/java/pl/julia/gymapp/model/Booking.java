package pl.julia.gymapp.model;

public class Booking {
    private User user;
    private Training training;

    public Booking(User user, Training training) {
        this.user = user;
        this.training = training;
    }

    public User getUser() { return user; }
    public Training getTraining() { return training; }
}
