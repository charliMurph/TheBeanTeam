package username.controller;

import javafx.scene.input.MouseEvent;
import username.model.Navigate;

public class NotificationsController {

    public void Profile(MouseEvent event){
        Navigate.goTo("/username/Profile-view.fxml", event);
    }
    public void Analytics(MouseEvent event){
        Navigate.goTo("/username/Analytics-view.fxml", event);
    }
    public void Settings(MouseEvent event){
        Navigate.goTo("/username/Settings-view.fxml", event);
    }
    public void Notifications(MouseEvent event){
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }
    public void Resources(MouseEvent event){
        Navigate.goTo("/username/Resource-view.fxml", event);
    }
    public void Goals(MouseEvent event){
        Navigate.goTo("/username/Home-view.fxml", event);
    }
    public void AppUsageStart(MouseEvent event) {
        Navigate.goTo("/username/AppUsageStart-view.fxml", event );
    }
    public void Home(MouseEvent event){
        Navigate.goTo("/username/Home-view.fxml", event);
    }

}