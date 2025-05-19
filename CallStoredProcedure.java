import java.sql.*;
import java.util.Scanner;

public class CallStoredProcedure {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        String user = "root";
        String password = "Shontu@13";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID to search: ");
        int studentId = scanner.nextInt();

        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String sql = "{CALL getStudentById(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, studentId);
            resultSet = callableStatement.executeQuery();
            boolean found = false;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
                found = true;
            }
            if (!found) {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (Exception e) {
            System.out.println("Error calling stored procedure.");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
