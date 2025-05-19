import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        String user = "root";
        String password = "Shontu@13";

        Connection connection = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            String sql1 = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
            ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, 10);
            ps1.setString(2, "Alice");
            ps1.setInt(3, 20);
            ps1.executeUpdate();
            String sql2 = "INSERT INTO courses (student_id, course_name) VALUES (?, ?)";
            ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, 10);  // Foreign key referencing students.id
            ps2.setString(2, "Mathematics");
            ps2.executeUpdate();
            connection.commit();
            System.out.println("Transaction committed successfully.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occurred, rolling back transaction.");
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Failed to rollback transaction.");
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
