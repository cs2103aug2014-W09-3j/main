package tareas.gui;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 * Created by Her Lung on 28/10/2014.
 */

public class GUIAnimation {
    //@author A0065490A
    public static FadeTransition addFadeOutAnimation(Scene scene) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), scene.getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.play();
        return ft;
    }

    //@author A0065490A
    public static FadeTransition addFadeInAnimation(Scene scene) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), scene.getRoot());
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
        return ft;
    }
}
