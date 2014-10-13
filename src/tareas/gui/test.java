package tareas.gui;

/**
 * Created by Her Lung on 10/10/2014.
 */
public class test {

    public void executeCommand(String input) {
        SampleController ui = SampleController.getInstance();
        ui.changeDisplayMessage(input);
    }

}
