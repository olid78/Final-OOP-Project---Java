package entities;

import java.io.Serializable;
import java.util.UUID;

public class Category implements Serializable {
    private String id;
    private String name;

    public Category(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}