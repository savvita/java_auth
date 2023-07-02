package models;

public class User {
    private final int id;
    private final String userName;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
