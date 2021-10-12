package com.lentrix.demofx;

import com.lentrix.usermanager.User;
import com.lentrix.usermanager.ActiveUser;
import com.lentrix.usermanager.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.synedra.validatorfx.Validator;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    @FXML
    TextField userNameField;

    @FXML
    PasswordField passwordField;

    private Validator validator = new Validator();

    private Connection conn;

    public void onLogin() {
        validator.validate();
        Alert a = new Alert(Alert.AlertType.ERROR);

        if(validator.containsErrors()){
            a.setTitle("Error");
            a.setContentText("You have an error in your entry.");
            a.show();
            return;
        }

        try {
            User user = UserDAO.login(userNameField.getText(), passwordField.getText(), DB.getConnection());
            ActiveUser.setUser(user);

            Parent root = FXMLLoader.load(getClass().getResource("pseudo-app.fxml"));
            userNameField.getScene().getWindow().hide();

            Stage stage = new Stage(StageStyle.DECORATED);

            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(true);
            stage.setScene(new Scene(root, 300,400));

            stage.show();
        }catch(Exception ex) {
            ex.printStackTrace();
            a.setTitle("Error");
            a.setContentText(ex.getMessage());
            a.show();
        }

    }

    public void onClose() {
        System.exit(0);
    }

    public void initialize() {
        validator.createCheck()
                .dependsOn("username", userNameField.textProperty())
                .withMethod(c -> {
                    String username = c.get("username");
                    if(username.isEmpty()) {
                        c.error("The user name cannot be empty.");
                    }
                })
                .decorates(userNameField);

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    if(password.isEmpty()) {
                        c.error("Password cannot be blank.");
                    }
                })
                .decorates(passwordField);
        try {
            this.conn = DB.getConnection();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
