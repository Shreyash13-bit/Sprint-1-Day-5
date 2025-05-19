import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveStudentsSafe {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        String user = "root";
        String password = "Shontu@13";

        String query = "SELECT * FROM students";
        try (
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred.");
            System.out.println("Error Details:");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }
}
