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
import username.model.DatabaseConnection;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataManagementController implements IControllerPaths {

    public VBox AddApps;

    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;
    private AppUsageController appUsageController;


    public DataManagementController() {
        userDAO = new UserDAO();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
        this.id = user.getId();
        this.appUsageController = new AppUsageController(user);
    }
    @Override
    public void initialize(){
        return;
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
        hBox.setSpacing(50);
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setPrefHeight(55.0);
        hBox.setStyle("-fx-border-color: DFC6FF; -fx-border-radius: 25;");
        Text appName = new Text(app);
        appName.setFont(new Font(17.0));
        HBox.setMargin(appName, new Insets(0, 0, 10, 15.0));
        hBox.getChildren().add(appName);

        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.setPrefHeight(18.0);
        toggleSwitch.setPrefWidth(45.0);
        HBox.setMargin(toggleSwitch, new Insets(0, 10, 0, 0));

        hBox.getChildren().add(toggleSwitch);
        toggleSwitch.setOnMouseClicked(event -> handleSwitch(app));
        initializeToggleSwitch(toggleSwitch, app);
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

    public void handleSwitch(String appName) {
        int currentStatus = userDAO.getIsActiveStatus(id, appName);
        if (currentStatus == -1) {
            System.err.println("Error fetching current status");
            return;
        }
        int newStatus = (currentStatus == 1) ? 0 : 1;
        userDAO.setApplicationActiveStatus(id, appName, newStatus);
        System.out.println("Switch toggled to " + (newStatus == 1 ? "on" : "off"));
    }

    public User getUser() {
        return user;
    }

    @Override
    public void Home(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }

    @Override
    public void DataMan(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        Navigate.caseGoto(event, user,  primaryStage,"/username/Profile-view.fxml", "Profile");
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

//    public void handleLogout() {
//        int userId = user.getId();
//        logLogoutTime(userId);
//        userDAO.close();
//        Navigate.goTo("/username/Login-view.fxml", new MouseEvent());
//    }

    private void logLogoutTime(int userId) {
        long usageTime = calculateUsageTime(userId); // Calculate the usage time in seconds
        String appName = "Computer"; // or getCurrentAppName();
        String query = "UPDATE appData SET stop_time = CURRENT_TIMESTAMP, hours = hours + ? WHERE authenticationId = ? AND applicationName = ? AND stop_time IS NULL";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, usageTime / 3600); // Convert seconds to hours
            statement.setInt(2, userId);
            statement.setString(3, appName);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private long calculateUsageTime(int userId) {
        // Retrieve the latest login time
        String query = "SELECT MAX(id) AS id FROM appData WHERE authenticationId = ? AND applicationName = ?";
        String appName = "Computer"; // or getCurrentAppName();
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, appName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String selectTimeQuery = "SELECT strftime('%s', 'now') - strftime('%s', start_time) AS usage_time FROM appData WHERE id = ?";
                try (PreparedStatement timeStatement = connection.prepareStatement(selectTimeQuery)) {
                    timeStatement.setInt(1, id);
                    ResultSet timeResultSet = timeStatement.executeQuery();
                    if (timeResultSet.next()) {
                        return timeResultSet.getLong("usage_time");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}