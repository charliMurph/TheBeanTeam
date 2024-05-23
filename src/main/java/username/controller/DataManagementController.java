package username.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;
import java.util.List;

public class DataManagementController implements IControllerPaths {

    public VBox AddApps;
    @FXML
    private ToggleSwitch FBSwitch;
    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;


    public DataManagementController() {
        userDAO = new UserDAO();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
        this.id = user.getId();
    }

    public void init() {
        List<String> apps = userDAO.getListOfApps(id);

        for (String app : apps){
            System.out.println("creating app : " + app);
            HBox hbox = createAppHBox(app);
            AddApps.getChildren().add(hbox);
        }
    }
    private HBox createAppHBox(String app) {
        HBox hBox = new HBox();
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPrefHeight(55.0);
        hBox.setStyle("-fx-border-color: DFC6FF; -fx-border-radius: 25;");

        Text appName = new Text(app);
        appName.setFont(new Font(17.0));
        HBox.setMargin(appName, new Insets(0, 0, 0, 15.0));
        hBox.getChildren().add(appName);

        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.setPrefHeight(18.0);
        toggleSwitch.setPrefWidth(45.0);
        HBox.setMargin(toggleSwitch, new Insets(0, 10, 0, 0));
        hBox.getChildren().add(toggleSwitch);

//        initializeToggleSwitch(toggleSwitch, app.name);

        return hBox;
    }

    @FXML
    private void initializeToggleSwitch(ToggleSwitch toggleSwitch, String appName) {
        int isActive = userDAO.getIsActiveStatus(id, appName);
        if (isActive == -1) {
            toggleSwitch.setVisible(false);
            HBox parentBox = (HBox) toggleSwitch.getParent();
            Label addNewLabel = new Label("Add New");
            addNewLabel.setTextFill(Color.web("#af71ff"));
            addNewLabel.setUnderline(true);
            addNewLabel.setTranslateX(-15);
            addNewLabel.setOnMouseClicked(event -> handleAddNew(appName));
            parentBox.getChildren().add(addNewLabel);
        } else {
            toggleSwitch.setSelected(isActive == 1);
        }
        toggleSwitch.setUserData(appName);
    }

    private void handleAddNew(String appName) {
        System.out.println("Adding new application: " + appName);
        // Implementation for adding the new application to the database
    }

    public void onSwitch() {
        int currentStatus = userDAO.getIsActiveStatus(id, "Facebook");
        if (currentStatus == -1) {
            System.err.println("Error fetching current status");
            return;
        }

        int newStatus = (currentStatus == 1) ? 0 : 1;
        userDAO.setApplicationActiveStatus(id, "Facebook", newStatus);
        FBSwitch.setSelected(newStatus == 1);
        System.out.println("Switch toggled to " + (newStatus == 1 ? "on" : "off"));
    }

    public User getUser() {
        return user;
    }

    @Override
    public void Home(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }

    @Override
    public void DataMan(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Profile-view.fxml", "Profile");
    }

    @Override
    public void Analytics(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Analytics-view.fxml", "Analytics");
    }

    @Override
    public void Settings(MouseEvent event) {
        Navigate.goTo("/username/Settings-view.fxml", event);
    }

    @Override
    public void Notifications(MouseEvent event) {
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }

    @Override
    public void Resources(MouseEvent event) {
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void Goals() {
        userDAO.close();
        Navigate.caseGoto(null, user, primaryStage, "/username/Goals-view.fxml", "Goals");
    }
}
