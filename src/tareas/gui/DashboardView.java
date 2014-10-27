package tareas.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Her Lung on 27/10/2014.
 */
public class DashboardView {

    public DashboardView() {}

    public void showDashboard() {
        FlowPane root = new FlowPane();
        root.setId("dashboard");
        root.getStylesheets().add("tareas/gui/css/dashboard.css");

        FlowPane overall = getOverallComponent(380, 280);
        FlowPane statistics = getStatisticsComponent(380, 280);

        FlowPane barGraphArea = new FlowPane();

        root.getChildren().addAll(overall, statistics);

        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private FlowPane getOverallComponent(int width, int height) {
        FlowPane overall = new FlowPane();
        overall.setId("overall");
        overall.setPrefSize(width, height);

        overall.getChildren().addAll(
                labelGenerator("Completed Tasks", "headings", width),
                labelGenerator("9", "numbers", width),
                labelGenerator("Overdue Tasks", "headings", width),
                labelGenerator("21", "numbers", width)
        );

        return overall;
    }

    private FlowPane getStatisticsComponent(int width, int height) {
        FlowPane statistics = new FlowPane();
        statistics.setId("statistics");
        statistics.setPrefSize(width, height);

        statistics.getChildren().addAll(
                labelGenerator("Uncompleted Tasks", "headings", width),
                labelGenerator("34", "numbers", width),
                labelGenerator("Deadline Tasks", "mini-headings", 190),
                labelGenerator("Timed Tasks", "mini-headings", 190),
                labelGenerator("12", "mini-numbers", 190),
                labelGenerator("8", "mini-numbers", 190),
                labelGenerator("Floating Tasks", "mini-headings", 190),
                labelGenerator("Important Tasks", "mini-headings", 190),
                labelGenerator("2", "mini-numbers", 190),
                labelGenerator("5", "mini-numbers", 190)
        );

        return statistics;
    }

    private Label labelGenerator(String value, String id, int width) {
        Label label = new Label(value);
        label.setId(id);
        label.setPrefWidth(width);
        return label;
    }

    private Label labelGenerator(String value, String id, int width, int height) {
        Label label = new Label(value);
        label.setId(id);
        label.setPrefWidth(width);
        return label;
    }
}
