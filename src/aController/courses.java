package aController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainP.Course;
import mainP.Teacher;
import utilities.sqliteConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// main courses class
public class courses {
    ObservableList<Integer> creditlist= FXCollections.observableArrayList(1,2,3,4);
    @FXML
    private ChoiceBox credit;
    @FXML
    private ChoiceBox teachr;
    @FXML
    private TextField cd_name;
    @FXML
    private TextField  cd_code;
    @FXML
    private ChoiceBox prereq;
    @FXML
    private ChoiceBox semestr;
    @FXML
    private ChoiceBox typee;


    ObservableList<Teacher> teacherlist= FXCollections.observableArrayList();
    ObservableList<String> myteacherlist= FXCollections.observableArrayList();
    ObservableList<String> prereqlist= FXCollections.observableArrayList();
    ObservableList<Integer> semestrlist= FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
    ObservableList<String> typplist= FXCollections.observableArrayList("CORE","ELECTIVE");


    void getTeachers() throws SQLException, IOException {
        try {
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users where type='FACULTY'");
            while (resultSet.next()) {
                teacherlist.add(new Teacher(resultSet.getInt("id"), resultSet.getString("FName"), resultSet.getString("LName"), resultSet.getString("type")));
                myteacherlist.add(resultSet.getString("FName"));
            }
        }
        catch (SQLException e) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Login");
            alert.setHeaderText(null);
            alert.setContentText("Database issue :/");
            alert.showAndWait();
        }
        try {
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from courses");
            while (resultSet.next()) {
           prereqlist.add(resultSet.getString("code"));
            }
            prereqlist.add("None");
        }
        catch (SQLException e) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Login");
            alert.setHeaderText(null);
            alert.setContentText("Database Issue :/ ");
            alert.showAndWait();
        }


    }

    @FXML
    private void initialize() throws IOException, SQLException {
        credit.setValue(1);
        credit.setItems(creditlist);

        teachr.setValue(" ");
        teachr.setItems(myteacherlist);

        prereq.setValue("CS101");
        prereq.setItems(prereqlist);

        semestr.setValue(1);
        semestr.setItems(semestrlist);

        typee.setValue("CORE");
        typee.setItems(typplist);
    getTeachers();
    }

    @FXML
    void cd_addcourse(MouseEvent event) throws SQLException, IOException {
        try {
            // Establish database connection
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();

            // Check if the course with the same name or code already exists
            String query = "SELECT COUNT(*) FROM courses WHERE cName = '" + cd_name.getText() + 
                           "' OR code = '" + cd_code.getText() + "'";
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next(); // Move to the first (and only) row of the result
            int count = resultSet.getInt(1);

            if (count > 0) {
                // If a duplicate exists, show an error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate Course");
                alert.setHeaderText(null);
                alert.setContentText("A course with the same name or code already exists!\n" +
                                     "Please provide unique details.");
                alert.showAndWait();
            } else {
                // If no duplicate exists, proceed with the insertion
                int status = statement.executeUpdate(
                    "INSERT INTO courses (cName, code, cHrs, aTeacher, preReq, type, semester) VALUES (" +
                    "'" + cd_name.getText() + "', " +
                    "'" + cd_code.getText() + "', " +
                    "'" + credit.getValue() + "', " +
                    "'" + teachr.getValue() + "', " +
                    "'" + prereq.getValue() + "', " +
                    "'" + typee.getValue() + "', " +
                    "'" + semestr.getValue() + "')"
                );

                if (status == 1) {
                    // Show success message if insertion is successful
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Course");
                    alert.setHeaderText(null);
                    alert.setContentText("Course '" + cd_name.getText() + "' has been added!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            // Show error message if a database error occurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while adding the course: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void setting(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/setting.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void dash(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/homeAdmin.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mainP/login.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void homei(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/homeView.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }

    @FXML
    void users(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/users.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void dcourse(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/displayCourses.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void duser(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/displayUsers.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
}
