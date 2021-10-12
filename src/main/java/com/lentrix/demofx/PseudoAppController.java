package com.lentrix.demofx;

import com.lentrix.usermanager.ActiveUser;
import com.lentrix.usermanager.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class PseudoAppController implements Initializable {
    @FXML
    private Button createButton, editButton, profileButton, otherProfileButton, deleteButton;

    private User user;

    private Alert a = new Alert(null);

    public void onCreateUser() {
        checkAccess("create-user");
    }

    public void onEditUser() {
        checkAccess("update-user");
    }

    public void onViewProfile() {
        checkAccess("view-own-profile");
    }

    public void onViewOtherProfile() {
        checkAccess("view-other-profile");
    }

    public void onDeleteUser() {
        checkAccess("delete-user");
    }

    private void checkAccess(String permissionString) {
        if(user.can(permissionString)) {
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setTitle("Allowed!");
            a.setContentText(user.getFullName()+" is allowed to " + permissionString);
        }else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Restricted!");
            a.setContentText(user.getFullName() + " is not allowed to " + permissionString);
        }
        a.show();
    }

    public void onLogout() {
        ActiveUser.setUser(null);
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = ActiveUser.getUser();
    }
}
