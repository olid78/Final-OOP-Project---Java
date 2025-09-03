package entities;

import java.io.Serializable;
import java.util.UUID;

public class Expense implements Serializable {
    private String id;
    private User user;
    private Category category;
    private double amount;
    private String description;

    public Expense(User user, Category category, double amount, String description) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Category getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public void setUser(User user) { this.user = user; }
    public void setCategory(Category category) { this.category = category; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
}