package username.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import username.model.Navigate;

public interface IControllerPaths {
    public void Profile();

    public void Analytics(MouseEvent event);
    public void Settings(MouseEvent event);

    public void Notifications(MouseEvent event);

    public void UserPref(MouseEvent event);
    public void Resources(MouseEvent event);

    public void Goals(MouseEvent event);

    public void Home(MouseEvent event);

}

