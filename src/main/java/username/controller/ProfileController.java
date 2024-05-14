//package username.controller;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.input.MouseEvent;
//import username.model.Navigate;
//import username.model.User;
//
//public class ProfileController {
//
//    @FXML
//    private Label usernameLabel;
//
//    @FXML
//    private Label emailLabel;
//
//    @FXML
//    private Label firstNameLabel;
//
//    @FXML
//    private Label lastNameLabel;
//
//    @FXML
//    private Label ageLabel;
//
//
//    // Method to initialize the profile with user data
//    public void initializeProfile(User user) {
//        // Populate the UI elements with user data
//        usernameLabel.setText(user.getUsername());
//        emailLabel.setText(user.getEmail());
//        firstNameLabel.setText(user.getFirstName());
//        lastNameLabel.setText(user.getLastName());
//        ageLabel.setText(String.valueOf(user.getAge()));
//    }
//
//    public void Profile(MouseEvent event){
//        Navigate.goTo("/username/Profile-view.fxml", event);
//    }
//    public void Analytics(MouseEvent event){
//        Navigate.goTo("/username/Analytics-view.fxml", event);
//    }
//    public void Settings(MouseEvent event){
//        Navigate.goTo("/username/Settings-view.fxml", event);
//    }
//    public void Notifications(MouseEvent event){
//        Navigate.goTo("/username/Notifications-view.fxml", event);
//    }
//    public void DataMan(MouseEvent event){
//        Navigate.goTo("/username/Dmanagement-view.fxml", event);
//    }
//
//    public void Resources(MouseEvent event){
//        Navigate.goTo("/username/Resource-view.fxml", event);
//    }
//    public void Goals(MouseEvent event){
//        Navigate.goTo("/username/Home-view.fxml", event);
//    }
//
//    public void Home(MouseEvent event){
//        Navigate.goTo("/username/Home-view.fxml", event);
//    }
//
//}