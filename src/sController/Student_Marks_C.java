package sController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Student_Marks_C implements Initializable{


    private Student_marks_M m ;

    @FXML
    private ComboBox<String> combo;
    @FXML
    private TableView <Student_marks_GetSet> table;
    @FXML
    private TableColumn<Student_marks_GetSet, String> No;
    @FXML
    private TableColumn <Student_marks_GetSet, String> obtn_col;
    @FXML
    private TableColumn <Student_marks_GetSet, SimpleStringProperty> total_col;
    @FXML
    private Button Quizes;
    @FXML
    private Label info_table;
    @FXML
    private Label course_name;
    private String no;
    private String course;
    public ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        m = new Student_marks_M();

        fun_combox();
        combo.setItems(list);

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
        Parent root = FXMLLoader.load(getClass().getResource("/sView/homeview.fxml"));

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
    void dash(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/homeStudent.fxml"));

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
    void transcript(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/transcript.fxml"));

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
    void attendance(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sView/Attendance_atd.fxml"));

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));

    }
    ///
    public void setlabel()
    {
        course_name.setText(combo.getValue());
    }
    @FXML
    public void fun_combox()
    {
        ResultSet rs = m.registeredCourses();
        try {
            while(rs.next())
            {
                list.add(rs.getString(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void for_table_quizes()
    {
        course = combo.getValue();
        info_table.setText("Quiz");
        ResultSet rs = m.calculate_nos(course, "Quiz_Marks");
        ResultSet rs1 = m.calculate_obtn_marks(course, "Quiz_Marks");
        ResultSet rs2 = m.calculate_total_marks(course, "Quiz_Marks");
        ObservableList<Student_marks_GetSet> data = FXCollections.observableArrayList();
        try{
//        	System.out.println("Course.............."+course);
//        	System.out.println("Marks in quiz............" +rs1);
//        	System.out.println("Marks in quiz............" +rs2);

            while(rs.next() && rs1.next() && rs2.next())
            {
                data.add(new Student_marks_GetSet(rs.getString(1), rs1.getString(1), rs2.getString(1)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        No.setCellValueFactory(new PropertyValueFactory<>("no"));
        obtn_col.setCellValueFactory(new PropertyValueFactory<>("obtn"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        // step: 4
        try{
            //System.out.println("Trying to send data to table");
            table.setItems(data);
        }
        catch(Exception e)
        {
            //System.out.println("Something went wrong.");
            e.printStackTrace();
        }

        table.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() >= 1){
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    Student_marks_GetSet selectedPerson = table.getSelectionModel().getSelectedItem();
                    no = selectedPerson.getNo();
                    System.out.println("No ............... " + no);

                    ResultSet r1 = m.working_Min_Quiz(course, no);
                    ResultSet r2 = m.working_Max_Quiz(course, no);
                    ResultSet r3 = m.workingAvgQuiz(course, no);
                    ResultSet r4 = m.workingStdQuiz(course, no);

                    try{
                        while(r1.next() && r2.next() && r3.next() && r4.next())
                        {
                            JOptionPane.showMessageDialog(null, "MIN Marks: " + r1.getString(1) + "\n MAX marks: " + r2.getString(1)
                                    + "\n Average Marks: " + r3.getString(1) + "\n Standard deviation: " + r4.getString(1));
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    // end here
                }
            }
        });


    }

    @FXML
    public void for_table_assignments()
    {
        course = combo.getValue();
        info_table.setText("Assignments");
        ResultSet rs = m.calculate_nos(course, "Assignments_Marks");
        ResultSet rs1 = m.calculate_obtn_marks(course, "Assignments_Marks");
        ResultSet rs2 = m.calculate_total_marks(course, "Assignments_Marks");
        ObservableList<Student_marks_GetSet> data = FXCollections.observableArrayList();
        try{
            while(rs.next() && rs1.next() && rs2.next())
            {
                data.add(new Student_marks_GetSet(rs.getString(1), rs1.getString(1), rs2.getString(1)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        No.setCellValueFactory(new PropertyValueFactory<>("no"));
        obtn_col.setCellValueFactory(new PropertyValueFactory<>("obtn"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        // step: 4
        try{
            //System.out.println("Trying to send data to table");
            table.setItems(data);
        }
        catch(Exception e)
        {
            //System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        table.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() >= 1){
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    Student_marks_GetSet selectedPerson = table.getSelectionModel().getSelectedItem();
                    no = selectedPerson.getNo();

                    ResultSet r1 = m.workingMinAss(course, no);
                    ResultSet r2 = m.workingMaxAss(course, no);
                    ResultSet r3 = m.workingAvgAss(course, no);
                    ResultSet r4 = m.workingStdAss(course, no);

                    try{
                        while(r1.next() && r2.next() && r3.next() && r4.next())
                        {
                            JOptionPane.showMessageDialog(null, "MIN Marks: " + r1.getString(1) + "\n MAX marks: " + r2.getString(1)
                                    + "\n Average Marks: " + r3.getString(1) + "\n Standard deviation: " + r4.getString(1));
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    // end here
                }
            }
        });
    }

    @FXML
    public void for_table_sessional()
    {
        course = combo.getValue();
        info_table.setText("Sessional");
        ResultSet rs = m.calculate_nos(course, "sessional");
        ResultSet rs1 = m.calculate_obtn_marks(course, "sessional");
        ResultSet rs2 = m.calculate_total_marks(course, "sessional");
        ObservableList<Student_marks_GetSet> data = FXCollections.observableArrayList();
        try{
            while(rs.next() && rs1.next() && rs2.next())
            {
                data.add(new Student_marks_GetSet(rs.getString(1), rs1.getString(1), rs2.getString(1)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        No.setCellValueFactory(new PropertyValueFactory<>("no"));
        obtn_col.setCellValueFactory(new PropertyValueFactory<>("obtn"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        // step: 4
        try{
            //System.out.println("Trying to send data to table");
            table.setItems(data);
        }
        catch(Exception e)
        {
            //System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        table.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() >= 1){
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    Student_marks_GetSet selectedPerson = table.getSelectionModel().getSelectedItem();
                    no = selectedPerson.getNo();

                    ResultSet r1 = m.workingMinSessional(course , no);
                    ResultSet r2 = m.workingMaxSessional(course, no);
                    ResultSet r3 = m.workingAvgSessional(course, no);
                    ResultSet r4 = m.workingStdSessional(course, no);

                    try{
                        while(r1.next() && r2.next() && r3.next() && r4.next())
                        {
                            JOptionPane.showMessageDialog(null, "MIN Marks: " + r1.getString(1) + "\n MAX marks: " + r2.getString(1)
                                    + "\n Average Marks: " + r3.getString(1) + "\n Standard deviation: " + r4.getString(1));
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    // end here
                }
            }
        });


    }

    @FXML
    public void for_table_final()
    {
        course = combo.getValue();
        info_table.setText("Final");
        ResultSet rs = m.calculate_nos(course, "final");
        ResultSet rs1 = m.calculate_obtn_marks(course, "final");
        ResultSet rs2 = m.calculate_total_marks(course, "final");
        ObservableList<Student_marks_GetSet> data = FXCollections.observableArrayList();
        try{
            while(rs.next() && rs1.next() && rs2.next())
            {
                data.add(new Student_marks_GetSet(rs.getString(1), rs1.getString(1), rs2.getString(1)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        No.setCellValueFactory(new PropertyValueFactory<>("no"));
        obtn_col.setCellValueFactory(new PropertyValueFactory<>("obtn"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        // step: 4
        try{
            System.out.println("Trying to send data to table");
            table.setItems(data);
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        table.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() >= 1){
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    Student_marks_GetSet selectedPerson = table.getSelectionModel().getSelectedItem();
                    no = selectedPerson.getNo();

                    ResultSet r1 = m.workingMinFinal(course, no);
                    ResultSet r2 = m.workingMaxFinal(course, no);
                    ResultSet r3 = m.workingAvgFinal(course, no);
                    ResultSet r4 = m.workingStdFinal(course, no);

                    try{
                        while(r1.next() && r2.next() && r3.next() && r4.next())
                        {
                            JOptionPane.showMessageDialog(null, "MIN Marks: " + r1.getString(1) + "\n MAX marks: " + r2.getString(1)
                                    + "\n Average Marks: " + r3.getString(1) + "\n Standard deviation: " + r4.getString(1));
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    // end here
                }
            }
        });
    }

    @FXML
    public void for_table_project()
    {
        course = combo.getValue();
        info_table.setText("Project");
        ResultSet rs = m.calculate_nos(course, "project");
        ResultSet rs1 = m.calculate_obtn_marks(course, "project");
        ResultSet rs2 = m.calculate_total_marks(course, "project");
        ObservableList<Student_marks_GetSet> data = FXCollections.observableArrayList();
        try{
            while(rs.next() && rs1.next() && rs2.next())
            {
                data.add(new Student_marks_GetSet(rs.getString(1), rs1.getString(1), rs2.getString(1)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        No.setCellValueFactory(new PropertyValueFactory<>("no"));
        obtn_col.setCellValueFactory(new PropertyValueFactory<>("obtn"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        // step: 4
        try{
            System.out.println("Trying to send data to table");
            table.setItems(data);
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
        table.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() >= 1){
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    Student_marks_GetSet selectedPerson = table.getSelectionModel().getSelectedItem();
                    no = selectedPerson.getNo();

                    ResultSet r1 = m.workingMinProjects(course, no);
                    ResultSet r2 = m.workingMaxProjects(course, no);
                    ResultSet r3 = m.workingAvgProjects(course, no);
                    ResultSet r4 = m.workingStdProjects(course, no);

                    try{
                        while(r1.next() && r2.next() && r3.next() && r4.next())
                        {

                            JOptionPane.showMessageDialog(null, "MIN Marks: " + r1.getString(1) + "\n MAX marks: " + r2.getString(1)
                                    + "\n Average Marks: " + r3.getString(1) + "\n Standard deviation: " + r4.getString(1));
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    // end here
                }
            }
        });
    }





    // for extra
    public void for_Grand_total()
    {
        info_table.setText("Grand Total");
        course = combo.getValue();
        ResultSet rs1 = m.Q_OB_marks(course);
        ResultSet rs2 = m.Q_Total_Marks(course);
        ResultSet rs3 = m.A_OB_marks(course);
        ResultSet rs4 = m.A_Total_Marks(course);
        ResultSet rs5 = m.S_OB_marks(course);
        ResultSet rs6 = m.S_Total_Marks(course);
        ResultSet rs7 = m.F_OB_Marks(course);
        ResultSet rs8 = m.F_Total_Marks(course);
        ResultSet rs9 = m.P_OB_Marks(course);
        ResultSet rs10 = m.P_Total_Marks(course);
        
        String r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
        Double v1, v2, v3, v4, v5, obtn_marks, v6, v7, v8, v9,v10, total_marks;

        try {
            List<ResultSet> resultSets = Arrays.asList(rs1, rs3, rs5, rs7, rs9, rs2, rs4, rs6, rs8, rs10);

            boolean allHaveNext;
            do {
                allHaveNext = true;

                // Check if all ResultSets have a next row
                for (ResultSet rs : resultSets) {
                    try {
                        if (!rs.next()) {
                            allHaveNext = false;
                            break;
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error accessing ResultSet: " + e.getMessage());
                        return; // Exit if there's an error
                    }
                }

                if (allHaveNext) {
                    try {
                        // Collect values dynamically
                        double[] obtainedMarks = new double[5];
                        double[] totalMarks = new double[5];

                        for (int i = 0; i < 5; i++) {
                            String obtainedMarkStr = resultSets.get(i).getString(1);
                            String totalMarkStr = resultSets.get(i + 5).getString(1);

                            // Validate and parse values
                            obtainedMarks[i] = (obtainedMarkStr != null && !obtainedMarkStr.isEmpty())
                                    ? Double.parseDouble(obtainedMarkStr)
                                    : 0.0;

                            totalMarks[i] = (totalMarkStr != null && !totalMarkStr.isEmpty())
                                    ? Double.parseDouble(totalMarkStr)
                                    : 0.0;
                        }

                        // Sum up values
                        double obtn_marks1 = Arrays.stream(obtainedMarks).sum();
                        double total_marks1 = Arrays.stream(totalMarks).sum();

                        // Display results
                        System.out.println("Obtained Marks: " + obtn_marks1);
                        System.out.println("Total Marks: " + total_marks1);

                        JOptionPane.showMessageDialog(null, "Here are the Grand Total Marks\n\n\n "
                                + "Obtained Marks: " + obtn_marks1 + "\nTotal Marks: " + total_marks1);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid numeric format in database: " + e.getMessage());
                    }
                }

            } while (allHaveNext);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error accessing database: " + e.getMessage());
        }

    }

}


