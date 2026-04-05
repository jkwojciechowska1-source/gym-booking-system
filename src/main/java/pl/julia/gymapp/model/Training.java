package pl.julia.gymapp.model;

public class Training {
    private int id;
    private String name;
    private int maxParticipants;

    public Training(int id, String name, int maxParticipants) {
        this.id = id;
        this.name = name;
        this.maxParticipants = maxParticipants;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMaxParticipants() { return maxParticipants; }
}