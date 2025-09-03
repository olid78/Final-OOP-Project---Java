package storage;

import java.io.*;

public class DataStorage {
    private static final String FILE_NAME = "finance_data.ser";

    public static void save(Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}