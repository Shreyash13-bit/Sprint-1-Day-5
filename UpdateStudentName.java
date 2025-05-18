import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStudentName {
    public static void main(String[] args) {
        // JDBC connection info
        String url = "jdbc:mysql://localhost:3306/Students";
        String user = "root";
        String password = "Shontu@13";
        int studentId = 2;
        String newName = "Robert";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE students SET name = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, studentId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student name updated successfully.");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}
