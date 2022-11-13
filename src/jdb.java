import java.sql.*;

public class jdb {
    public static void main(String arg[]) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/empdata",
                    "root", "pradeep jw909");

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "select * from empinfo");
            int id, rating;
            String name, mobile, email;
            float salary;
            String data[][] = new String[100][100];
            int c = 0;
            int row = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                mobile = resultSet.getString("mobile").trim();
                email = resultSet.getString("email").trim();
                salary = resultSet.getFloat("salary");
                rating = resultSet.getInt("rating");

                data[c][0] = Integer.toString(id);
                data[c][1] = name;
                data[c][2] = mobile;
                data[c][3] = email;
                data[c][4] = Float.toString(salary);
                data[c][5] = Integer.toString(rating);
                c++;

            }
            for (int i = 0; i < c; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(data[i][j] + " ");
                }
                System.out.println("");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}