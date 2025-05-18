import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudentById {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Students";
        String user = "root";
        String password = "Shontu@13";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter student ID to delete: ");
            int studentId = scanner.nextInt();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM students WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student record deleted successfully.");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
                scanner.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources.");
                e.printStackTrace();
            }
        }
    }
}
