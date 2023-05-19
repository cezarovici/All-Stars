package PaooGame.DataBase;

import java.io.*;
import java.sql.*;

public class DataBaseManager {

    private Connection connection;

    public DataBaseManager(String databasePath) {
        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTable(String tableName, String columnsDefinition) {
        try (Statement statement = connection.createStatement()) {
            // Execute the query to create the table
            String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnsDefinition + ")";
            statement.execute(query);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveData(String tableName, String columnName, Object data) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)")) {

            oos.writeObject(data);
            byte[] dataBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);

            statement.setBinaryStream(1, bais, dataBytes.length);
            statement.executeUpdate();
            System.out.println("Data saved successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Object loadData(String tableName, String columnName, int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT " + columnName + " FROM " + tableName + " WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    InputStream is = resultSet.getBinaryStream(columnName);
                    ObjectInputStream ois = new ObjectInputStream(is);
                    Object data = ois.readObject();
                    ois.close();
                    return data;
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
