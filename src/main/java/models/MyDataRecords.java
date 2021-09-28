package models;

public class MyDataRecords {
    public record UserData(int id, String email, String firstName, String LastName, String avatar) {
    }

    public record ResourceData(int id, String name, int year, String color, String pantoneValue) {
    }
}
