package sController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainP.Course;
import mainP.Student;
import utilities.sqliteConnection;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;
import javafx.application.Platform;

public class feeDetails implements Initializable {

    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course,Integer> idColumn;
    @FXML
    private TableColumn<Course,String> CNameColumn;
    @FXML
    private TableColumn<Course,Integer> CHrsColumn;
    @FXML
    private Label totalfees;

    public Integer totalcredit;
    public Integer feetotal;
    public Integer percredit;


    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mainP/login.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    @FXML
    void dash(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/homeStudent.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    
    @FXML
    void transcript(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/transcript.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    
    @FXML
    void attendance(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/Attendance_atd.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
   
    void courseReg(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/courseRegistration.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    
    @FXML
    void st_marks(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/Student_Marks_C.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    @FXML
    void studyPlan(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/semester1.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    @FXML
    void feeDe(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/feeDetails.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    @FXML
    void homei(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/homeview.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Course,Integer>("id"));
        CNameColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("cName"));
        CHrsColumn.setCellValueFactory(new PropertyValueFactory<Course,Integer>("cHrs"));

        tableView.setItems(getCourses());
        try {
            getTotal();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //JOptionPane.showMessageDialog(null,feetotal);
        String ab= Integer.toString(feetotal);
        String ac=" Rs: "+ab;
        totalfees.setText(ac);
       // showTotalFees(feetotal);
        

    }

    public ObservableList<Course> getCourses() {
        totalcredit = 0; // Reset total credit hours
        ObservableList<Course> Course = FXCollections.observableArrayList();

        try {
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();

            // Fetch registered courses for the student
            ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM registration " +
                "INNER JOIN courses ON courses.id = registration.cID " +
                "WHERE sID = " + Student.id + ";"
            );

            while (resultSet.next()) {
                // Add course to the list
                Course.add(new Course(
                    resultSet.getInt("id"),
                    resultSet.getString("cName"),
                    resultSet.getString("code"),
                    resultSet.getInt("cHrs"),
                    resultSet.getString("aTeacher"),
                    resultSet.getString("preReq"),
                    resultSet.getString("type"),
                    resultSet.getInt("semester")
                ));

                // Sum up credit hours
                totalcredit += resultSet.getInt("cHrs");
            }

        } catch (SQLException e) {
            System.err.println("Cannot Connect to Database: " + e.getMessage());
        }

        return Course;
    }

    public void getTotal() throws SQLException {
        try {
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();

            // Fetch the latest 'amount' from the fee table
            ResultSet resultSet = statement.executeQuery("SELECT amount AS latestAmount\r\n"
            		+ "FROM fee\r\n"
            		+ "ORDER BY id DESC\r\n"
            		+ "LIMIT 1;\r\n"
            		+ ";");
            if (resultSet.next()) {
                percredit = resultSet.getInt("latestAmount"); // Get the latest fee per credit hour
            } else {
                percredit = 0; // Default to 0 if no fee records exist
            }

            // Calculate the total fee based on total credit hours
            feetotal = totalcredit * percredit;

        } catch (SQLException e) {
            System.err.println("Error calculating total fee: " + e.getMessage());
        }
    }

}
