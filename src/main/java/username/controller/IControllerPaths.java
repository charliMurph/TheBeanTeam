package username.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public interface IControllerPaths {
    @FXML
    public void Profile(MouseEvent event);
    @FXML
    public void Analytics(MouseEvent event);
    @FXML
    public void Settings(MouseEvent event);
    @FXML
    public void Notifications(MouseEvent event);
    @FXML
    public void DataMan(MouseEvent event);

    @FXML
    public void Resources(MouseEvent event);
    @FXML
    public void Home(MouseEvent event);
    @FXML
    public void UserGoals(MouseEvent event);

    public void initialize();

}

