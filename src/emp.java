import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class dbc {
    String data[][] = new String[100][100];
    int c = 0;

    dbc() {
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
            String name, mobile, email, role;
            float salary;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name").trim();
                role = resultSet.getString("role");
                mobile = resultSet.getString("mobile").trim();
                email = resultSet.getString("email").trim();
                salary = resultSet.getFloat("salary");
                rating = resultSet.getInt("rating");

                data[c][0] = Integer.toString(id);
                data[c][1] = name;
                data[c][2] = role;
                data[c][3] = mobile;
                data[c][4] = email;
                data[c][5] = Float.toString(salary);
                data[c][6] = Integer.toString(rating);
                c++;

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    dbc(String[] a) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/empdata",
                    "root", "pradeep jw909");

            Statement statement;
            statement = connection.createStatement();

            statement.executeUpdate("Insert into empinfo values " + "( " +
                    a[0] + "," + " ' " + a[1] + " ' " + "," +
                    " ' " + a[2] + " ' " + "," + " ' " + a[3] + " ' " + ","
                    + " ' " + a[4] + " ' " + "," + " ' " + a[5] + " ' " + "," + a[6] + " );");

            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }

    }

    dbc(int a) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/empdata",
                    "root", "pradeep jw909");
            Statement statement;
            statement = connection.createStatement();
            PreparedStatement preparedStmt = connection.prepareStatement("delete from empinfo where id = " + a + " ;");
            preparedStmt.execute();
            statement.close();
            connection.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    String[][] getdata() {
        return data;
    }

}

class jtab {
    jtab(String[][] obj) {
        JFrame frm = new JFrame();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        frm.setSize(600, 500);
        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("data", new emp(obj));
        jtp.addTab("Add", new insert());
        jtp.addTab("delete", new delete());
        frm.add(jtp);
    }
}

class delete extends JPanel {
    delete() {
        JPanel jpl = new JPanel();
        JLabel jbl = new JLabel();
        JTextField jfl = new JTextField();
        JButton jbtn = new JButton("Submit");
        jbl.setText("Enter key to delete corresponding data entry : ");
        jbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String st = jfl.getText();
                if (st != "") {
                    new dbc(Integer.parseInt(st));
                } else {
                    System.out.println("sadfasdf");
                    ;
                }

            }
        });
        jpl.setLayout(new GridLayout(3, 1));
        jpl.add(jbl);
        jpl.add(jfl);
        jpl.add(jbtn);
        add(jpl);
    }
}

class insert extends JPanel {
    insert() {
        JPanel jpl = new JPanel();
        jpl.setLayout(new GridLayout(10, 2));
        JLabel idLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel roleLabel = new JLabel();
        JLabel mobileLabel = new JLabel();
        JLabel emailLabel = new JLabel();
        JLabel salaryLabel = new JLabel();
        JLabel ratingLabel = new JLabel();

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField role = new JTextField();
        JTextField mobile = new JTextField();
        JTextField email = new JTextField();
        JTextField salary = new JTextField();
        JTextField rating = new JTextField();

        JButton btn = new JButton("Save");

        idLabel.setText("ID : ");
        nameLabel.setText("Name : ");
        roleLabel.setText("Role : ");
        mobileLabel.setText("Mobile : ");
        emailLabel.setText("Email : ");
        salaryLabel.setText("Salary : ");
        ratingLabel.setText("Rating : ");

        jpl.add(idLabel);
        jpl.add(id);
        jpl.add(nameLabel);
        jpl.add(name);
        jpl.add(roleLabel);
        jpl.add(role);
        jpl.add(mobileLabel);
        jpl.add(mobile);
        jpl.add(emailLabel);
        jpl.add(email);
        jpl.add(salaryLabel);
        jpl.add(salary);
        jpl.add(ratingLabel);
        jpl.add(rating);
        jpl.add(btn);
        add(jpl);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String data[] = new String[7];
                data[0] = id.getText();
                data[1] = name.getText();
                data[2] = role.getText();
                data[3] = mobile.getText();
                data[4] = rating.getText();
                data[5] = email.getText();
                data[6] = salary.getText();
                new dbc(data);
                dbc sql = new dbc();
                new emp(sql.getdata());
            }
        });
    }
}

public class emp extends JPanel {

    emp(String[][] obj) {

        System.out.println("sadf");
        JPanel jpl = new JPanel();
        String headings[] = { "Id", "Name", "Role", "Mobile", "email", "Salary", "Rating" };
        JTable tbl = new JTable(obj, headings);
        JScrollPane jsp = new JScrollPane(tbl);
        JButton btn = new JButton("Refresh");

        add(jsp);
        add(jpl);
    }

    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                dbc sqldata = new dbc();
                String obj[][] = sqldata.getdata();
                new jtab(obj);
            }
        });
    }
}
