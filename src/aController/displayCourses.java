package aController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mainP.Course;
import mainP.Teacher;
import utilities.sqliteConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
// diplay all courses
public class displayCourses implements Initializable{

    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course,Integer> idColumn;
    @FXML
    private TableColumn<Course,String> CNameColumn;
    @FXML
    private TableColumn<Course,String> CCodeColumn;
    @FXML
    private TableColumn<Course,Integer> CHrsColumn;
    @FXML
    private TableColumn<Course,String> TAssignedColumn;
    @FXML
    private TableColumn<Course,String> PreReqColumn;
    @FXML
    private TableColumn<Course,String> TypeColumn;
    @FXML
    private TableColumn<Course,Integer> SemesterColumn;
    ObservableList<String> myteacherlist= FXCollections.observableArrayList();
    ObservableList<String> prereqlist= FXCollections.observableArrayList();
    ObservableList<Teacher> teacherlist= FXCollections.observableArrayList();

    
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
    public void loadData() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Course,Integer>("id"));
        CNameColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("cName"));
        CCodeColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("CCode"));
        TAssignedColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("ATeacher"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("type"));
        PreReqColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("PreReq"));
        CHrsColumn.setCellValueFactory(new PropertyValueFactory<Course,Integer>("cHrs"));
        SemesterColumn.setCellValueFactory(new PropertyValueFactory<Course,Integer>("semester"));

         tableView.setItems(getCourses());
         
    }

    public ObservableList<Course> getCourses(){
        ObservableList<Course> Course= FXCollections.observableArrayList();
        try {
            Connection connection = sqliteConnection.dbConnector();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from courses");
            while (resultSet.next()) {
                Course.add(new Course(resultSet.getInt("id"), resultSet.getString("cName"), resultSet.getString("code"), resultSet.getInt("cHrs"), resultSet.getString("aTeacher"), resultSet.getString("preReq"), resultSet.getString("type"), resultSet.getInt("semester")));
            }


        } catch (SQLException e) {
            System.err.println("Cannot Connect to Database");
        }



        return Course;
    }

    @FXML
    public void rmData() throws SQLException {
Course selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);


        Connection connection= sqliteConnection.dbConnector();
        Statement statement = connection.createStatement();

        int status = statement.executeUpdate("DELETE FROM courses WHERE id= '"+selectedItem.id+"'");


        if (status==1) {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Remove Course");
            alert.setHeaderText(null);
            alert.setContentText("Course "+selectedItem.CName+" have been removed Successfuly!");
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
    void duser(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/displayUsers.fxml"));
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
    void Courses(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/courses.fxml"));
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
    void users(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aView/users.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    
    @FXML
    public void updateData() throws SQLException, IOException {
    	getTeachers();
        Course selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to update.");
            alert.showAndWait();
            return;
        }

        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Update Course");

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField cNameField = new TextField(selectedItem.getCName());
        TextField cCodeField = new TextField(selectedItem.getCCode());
        TextField aTeacherField = new TextField(selectedItem.getATeacher());
        TextField typeField = new TextField(selectedItem.getType());
        TextField preReqField = new TextField(selectedItem.getPreReq());
        TextField cHrsField = new TextField(String.valueOf(selectedItem.getCHrs()));
        TextField semesterField = new TextField(String.valueOf(selectedItem.getSemester()));

        grid.add(new Label("Course Name:"), 0, 0);
        grid.add(cNameField, 1, 0);
        grid.add(new Label("Course Code:"), 0, 1);
        grid.add(cCodeField, 1, 1);
        grid.add(new Label("Assigned Teacher:"), 0, 2);
        grid.add(aTeacherField, 1, 2);
        grid.add(new Label("Type:"), 0, 3);
        grid.add(typeField, 1, 3);
        grid.add(new Label("Pre-requisites:"), 0, 4);
        grid.add(preReqField, 1, 4);
        grid.add(new Label("Credit Hours:"), 0, 5);
        grid.add(cHrsField, 1, 5);
        grid.add(new Label("Semester:"), 0, 6);
        grid.add(semesterField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Course(
                    selectedItem.getId(),
                    cNameField.getText(),
                    cCodeField.getText(),
                    Integer.parseInt(cHrsField.getText()),
                    aTeacherField.getText(),
                    preReqField.getText(),
                    typeField.getText(),
                    Integer.parseInt(semesterField.getText())
                );
            }
            return null;
        });

        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(updatedCourse -> {
            try {
                // Validate Inputs
                String updatedTeacher = updatedCourse.getATeacher();
                String updatedPreReq = updatedCourse.getPreReq();
                String updatedType = updatedCourse.getType();
                int updatedSemester = updatedCourse.getSemester();

                // Validation 1: Check if teacher exists in the teacher list
                if (!myteacherlist.contains(updatedTeacher)) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Teacher", "The teacher '" + updatedTeacher + "' does not exist in the teacher list.");
                    return;
                }

                // Validation 2: Check if pre-requisite exists in the pre-requisite list
                if (!prereqlist.contains(updatedPreReq)) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Pre-Requisite", "The pre-requisite '" + updatedPreReq + "' does not exist in the pre-requisite list.");
                    return;
                }

                // Validation 3: Check if type is "Core" or "Elective"
                if (!updatedType.equalsIgnoreCase("Core") && !updatedType.equalsIgnoreCase("Elective")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Type", "The type must be either 'Core' or 'Elective'.");
                    return;
                }

                // Validation 4: Check if semester is between 1 and 8
                if (updatedSemester < 1 || updatedSemester > 8) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Semester", "The semester must be between 1 and 8.");
                    return;
                }

                // Validation 5: Check for duplicate course name or code
                Connection connection = sqliteConnection.dbConnector();
                Statement statement = connection.createStatement();

                String validationQuery = String.format(
                    "SELECT COUNT(*) FROM courses WHERE (cName = '%s' OR code = '%s') AND id != %d",
                    updatedCourse.getCName(), updatedCourse.getCCode(), updatedCourse.getId()
                );

                ResultSet resultSet = statement.executeQuery(validationQuery);
                resultSet.next();
                int duplicateCount = resultSet.getInt(1);

                if (duplicateCount > 0) {
                    showAlert(Alert.AlertType.ERROR, "Duplicate Course", "A course with the same name or code already exists. Update aborted.");
                    return;
                }

                // Proceed with update if all validations pass
                String updateQuery = String.format(
                    "UPDATE courses SET cName='%s', code='%s', cHrs=%d, aTeacher='%s', preReq='%s', type='%s', semester=%d WHERE id=%d",
                    updatedCourse.getCName(),
                    updatedCourse.getCCode(),
                    updatedCourse.getCHrs(),
                    updatedCourse.getATeacher(),
                    updatedCourse.getPreReq(),
                    updatedCourse.getType(),
                    updatedCourse.getSemester(),
                    updatedCourse.getId()
                );

                int status = statement.executeUpdate(updateQuery);

                if (status == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Course updated successfully!");
                    loadData(); // Refresh table data
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating course. Please try again.\n" + e.getMessage());
            }
        });
    }

    // Utility function to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        
    }
}
