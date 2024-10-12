package crud.javafxcrud.Controllers;
import Main.Main;
import crud.javafxcrud.DAO.UserDAO;
import crud.javafxcrud.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class WelcomeController {

    @FXML
    private TextField regUsernameField, regNameField,loginUsernameField;
    @FXML
    private PasswordField regPassField,loginPassField;

    Stage stage = new Stage();
    private void openNewStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/crud/javafxcrud/userInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 380);
        UserController userController = fxmlLoader.getController();
        userController.setWelcomeController(this);
        stage.setTitle("Üdvözöllek!");
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage() throws IOException {
        stage.close();
    }

    public void registerUser(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.addUser(new User(regUsernameField.getText(), regPassField.getText(), regNameField.getText()));
            System.out.println("Felhasználó sikeresen regisztrálva.");
            messageBox("", "Sikeres regisztráció!");
        } catch (SQLException e) {
            System.err.println("Hiba a regisztráció során: " + e.getMessage());
            messageBox("Hiba", "Hiba történt a regisztráláskor: " + e.getMessage());
        }
    }

    private void messageBox(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void loginUser(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        try {
            boolean isLoginSuccessful = userDAO.loginUser(loginUsernameField.getText(), loginPassField.getText());

            if (isLoginSuccessful) {
                System.out.println("Felhasználó sikeresen bejelentkezve.");
                messageBox("", "Sikeres bejelentkezés!");
                openNewStage();
            } else {
                System.out.println("Hibás felhasználónév vagy jelszó.");
                messageBox("Hiba", "Hibás felhasználónév vagy jelszó.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a bejelentkezés során: " + e.getMessage());
            messageBox("Hiba", "Hiba történt a bejelentkezéskor: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

