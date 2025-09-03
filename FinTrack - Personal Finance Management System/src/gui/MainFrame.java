package gui;

import entities.*;
import manager.ExpenseManager;
import storage.DataStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private ExpenseManager manager;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        // Load existing data
        Object data = DataStorage.load();
        if (data instanceof ExpenseManager) {
            manager = (ExpenseManager) data;
        } else {
            manager = new ExpenseManager();
        }

        setTitle("Personal Finance & Expense Tracker");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Buttons
        JPanel buttonPanel = new JPanel();
        JButton addUserBtn = new JButton("Add User");
        JButton addCategoryBtn = new JButton("Add Category");
        JButton addExpenseBtn = new JButton("Add Expense");
        JButton updateExpenseBtn = new JButton("Update Expense");
        JButton deleteExpenseBtn = new JButton("Delete Expense");

        buttonPanel.add(addUserBtn);
        buttonPanel.add(addCategoryBtn);
        buttonPanel.add(addExpenseBtn);
        buttonPanel.add(updateExpenseBtn);
        buttonPanel.add(deleteExpenseBtn);
        add(buttonPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"User", "Category", "Amount", "Description"}, 0);
        expenseTable = new JTable(tableModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        refreshTable();

        // Add User
        addUserBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter User Name:");
            if (name != null && !name.trim().isEmpty()) {
                manager.addUser(new User(name));
                saveData();
            }
        });

        // Add Category
        addCategoryBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter Category Name:");
            if (name != null && !name.trim().isEmpty()) {
                manager.addCategory(new Category(name));
                saveData();
            }
        });

        // Add Expense
        addExpenseBtn.addActionListener(e -> {
            if (manager.getUsers().isEmpty() || manager.getCategories().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add at least one User and one Category first!");
                return;
            }
            User user = (User) JOptionPane.showInputDialog(this, "Select User:", "User",
                    JOptionPane.QUESTION_MESSAGE, null,
                    manager.getUsers().toArray(), null);

            Category category = (Category) JOptionPane.showInputDialog(this, "Select Category:", "Category",
                    JOptionPane.QUESTION_MESSAGE, null,
                    manager.getCategories().toArray(), null);

            String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:");
            String desc = JOptionPane.showInputDialog(this, "Enter Description:");
            if (amountStr != null && desc != null) {
                try {
                    double amt = Double.parseDouble(amountStr);
                    manager.addExpense(new Expense(user, category, amt, desc));
                    saveData();
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount!");
                }
            }
        });

        // Update Expense
        updateExpenseBtn.addActionListener(e -> {
            int row = expenseTable.getSelectedRow();
            if (row >= 0) {
                Expense exp = manager.getExpenses().get(row);

                User user = (User) JOptionPane.showInputDialog(this, "Select User:", "User",
                        JOptionPane.QUESTION_MESSAGE, null,
                        manager.getUsers().toArray(), exp.getUser());

                Category category = (Category) JOptionPane.showInputDialog(this, "Select Category:", "Category",
                        JOptionPane.QUESTION_MESSAGE, null,
                        manager.getCategories().toArray(), exp.getCategory());

                String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:", exp.getAmount());
                String desc = JOptionPane.showInputDialog(this, "Enter Description:", exp.getDescription());

                if (amountStr != null && desc != null) {
                    try {
                        double amt = Double.parseDouble(amountStr);
                        exp.setUser(user);
                        exp.setCategory(category);
                        exp.setAmount(amt);
                        exp.setDescription(desc);
                        saveData();
                        refreshTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid amount!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select an expense to update!");
            }
        });

        // Delete Expense
        deleteExpenseBtn.addActionListener(e -> {
            int row = expenseTable.getSelectedRow();
            if (row >= 0) {
                Expense exp = manager.getExpenses().get(row);
                manager.deleteExpense(exp);
                saveData();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select an expense to delete!");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Expense exp : manager.getExpenses()) {
            tableModel.addRow(new Object[]{
                    exp.getUser().getName(),
                    exp.getCategory().getName(),
                    exp.getAmount(),
                    exp.getDescription()
            });
        }
    }

    private void saveData() {
        DataStorage.save(manager);
    }
}