package crud.javafxcrud.Controllers;

import crud.javafxcrud.DAO.UserDAO;
import crud.javafxcrud.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {

    @FXML
    private TextField idField, updateUsernameField,updatePassField,updateNameField;
    @FXML
    private TableView dataTable;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> userNameCol;
    @FXML
    private TableColumn<User, String> passCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML

    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    WelcomeController wc;

    public void setWelcomeController(WelcomeController welcomeController) {
        wc = welcomeController;
    }
    public void logoutUser(ActionEvent actionEvent) throws IOException {
        wc.closeStage();
    }

    private void refreshUsersTable(){
        UserDAO userDAO = new UserDAO();
        try {
            ArrayList<User> users = (ArrayList<User>) userDAO.findAllUsers();

            dataTable.getItems().clear();
            dataTable.getItems().addAll(users);

            System.out.println("Felhasználók kilistázva!");
        } catch (SQLException e) {
            System.err.println("Hiba a felhasználók megjelenítésében: " + e.getMessage());
        }
    }
    public void listUsers(ActionEvent actionEvent) {
        refreshUsersTable();
    }

    public void deleteUser(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        try {
            int userId = Integer.parseInt(idField.getText());

            boolean isDeleted = userDAO.deleteUser(userId);
            if (isDeleted) {
                System.out.println("Felhasználó sikeresen törölve.");
                refreshUsersTable();
            } else {
                System.out.println("Ilyen felhasználó nem létezik.");
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Hiba a felhasználó törlése során: " + e.getMessage());
        }
    }

    public void updateUser(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        try {
            int userId = Integer.parseInt(idField.getText());
            String updatedUsername = updateUsernameField.getText();
            String updatedPassword = updatePassField.getText();
            String updatedName = updateNameField.getText();
            User updatedUser = new User(updatedUsername, updatedPassword, updatedName);
            updatedUser.setId(userId);
            boolean isUpdated = userDAO.updateUser(updatedUser);
            if (isUpdated) {
                System.out.println("Felhasználó sikeresen módosítva.");
                refreshUsersTable();
            } else {
                System.out.println("Felhasználó nem található.");
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Hiba a módosítás során: " + e.getMessage());
        }
    }
}
