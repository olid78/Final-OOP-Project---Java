package manager;

import entities.*;
import java.io.Serializable;
import java.util.*;

public class ExpenseManager implements Serializable {
    private List<User> users = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public List<User> getUsers() { return users; }
    public List<Category> getCategories() { return categories; }
    public List<Expense> getExpenses() { return expenses; }

    public void addUser(User u) { users.add(u); }
    public void addCategory(Category c) { categories.add(c); }
    public void addExpense(Expense e) { expenses.add(e); }

    public void deleteUser(User u) { users.remove(u); }
    public void deleteCategory(Category c) { categories.remove(c); }
    public void deleteExpense(Expense e) { expenses.remove(e); }
}