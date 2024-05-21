package username.controller;

import javafx.scene.input.MouseEvent;
import username.model.Navigate;

public class AnalyticsController {

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
    public void DataMan(MouseEvent event){
        Navigate.goTo("/username/Dmanagement-view.fxml", event);
    }

    public void Resources(MouseEvent event){
        Navigate.goTo("/username/Resource-view.fxml", event);
    }
    public void Goals(MouseEvent event){
        Navigate.goTo("/username/Home-view.fxml", event);
    }

    public void Home(MouseEvent event){
        Navigate.goTo("/username/Home-view.fxml", event);
    }

}