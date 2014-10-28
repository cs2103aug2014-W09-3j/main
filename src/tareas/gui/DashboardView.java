package tareas.gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tareas.controller.TareasController;

import java.util.Calendar;
import java.util.Stack;

/**
 * Created by Her Lung on 27/10/2014.
 */
public class DashboardView {
    Timeline timeline;
    Stack<Integer> values = new Stack<>();

    public DashboardView() {
        TareasController tareasController = new TareasController();
        Stack<Integer> tempStack = tareasController.getInitialiseValues();
        while(!tempStack.empty()) {
            values.push(tempStack.pop());
        }
    }

    public void showDashboard() {
        FlowPane root = new FlowPane();
        root.setId("dashboard");
        root.getStylesheets().add("tareas/gui/css/dashboard.css");

        Label title = labelGenerator("Tareas Dashboard", "title", 780, 20);
        FlowPane overall = getOverallComponent(390, 150);
        FlowPane statistics = getStatisticsComponent(390, 150);

        FlowPane barGraphArea = new FlowPane();
        barGraphArea.getChildren().add(getBarChart());

        Label message = labelGenerator("Press any key to continue...",
                "dashboard-message", 780, 20);

        root.getChildren().addAll(title, overall, statistics, barGraphArea, message);

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                closeDashboard(scene);
            }
        });
        scene.setOnMouseClicked(t -> {
            closeDashboard(scene);
        });

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Dashboard");
        stage.setScene(scene);

        stage.setAlwaysOnTop(true);
        stage.requestFocus();

        stage.show();
    }

    private void closeDashboard(Scene scene) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), scene.getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                Stage innerStage = (Stage) scene.getWindow();
                innerStage.close();
            }
        });
    }

    private FlowPane getOverallComponent(int width, int height) {
        FlowPane overall = new FlowPane();
        overall.setId("overall");
        overall.setPrefSize(width, height);

        overall.getChildren().addAll(
                labelGenerator("Completed Tasks", "headings", width),
                labelGenerator(values.pop().toString(), "numbers", width),
                labelGenerator("Overdue Tasks", "headings", width),
                labelGenerator(values.pop().toString(), "numbers", width)
        );

        return overall;
    }

    private BarChart<Number, String> getBarChart() {
        NumberAxis xAxis = new NumberAxis();
        Axis yAxis = new CategoryAxis();
        BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);

        xAxis.setLabel("Number of tasks");
        yAxis.setLabel("Category");
        barChart.setPrefSize(780, 360);
        barChart.setBarGap(0);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(0, "Today"));
        series2.getData().add(new XYChart.Data(0, "Today"));
        series1.getData().add(new XYChart.Data(0, "Tomorrow"));
        series2.getData().add(new XYChart.Data(0, "Tomorrow"));
        series1.getData().add(new XYChart.Data(0, day(2)));
        series2.getData().add(new XYChart.Data(0, day(2)));
        series1.getData().add(new XYChart.Data(0, day(3)));
        series2.getData().add(new XYChart.Data(0, day(3)));
        series1.getData().add(new XYChart.Data(0, day(4)));
        series2.getData().add(new XYChart.Data(0, day(4)));
        series1.getData().add(new XYChart.Data(0, day(5)));
        series2.getData().add(new XYChart.Data(0, day(5)));
        series1.getData().add(new XYChart.Data(0, day(6)));
        series2.getData().add(new XYChart.Data(0, day(6)));

        barChart.setLegendVisible(false);
        barChart.getData().addAll(series1, series2);

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        for (XYChart.Series<Number, String> series : barChart.getData()) {
                            for (XYChart.Data<Number, String> data : series.getData()) {
                                data.setXValue(values.pop());
                            }
                        }
                    }
                }));
        tl.setCycleCount(1);
        tl.play();

        return barChart;
    }

    private FlowPane getStatisticsComponent(int width, int height) {
        FlowPane statistics = new FlowPane();
        statistics.setId("statistics");
        statistics.setPrefSize(width, height);

        statistics.getChildren().addAll(
                labelGenerator("Uncompleted Tasks", "headings", width),
                labelGenerator("Deadline Tasks", "mini-headings", 190),
                labelGenerator("Timed Tasks", "mini-headings", 190),
                labelGenerator(values.pop().toString(), "mini-numbers", 190),
                labelGenerator(values.pop().toString(), "mini-numbers", 190),
                labelGenerator("Floating Tasks", "mini-headings", 190),
                labelGenerator("Important Tasks", "mini-headings", 190),
                labelGenerator(values.pop().toString(), "mini-numbers", 190),
                labelGenerator(values.pop().toString(), "mini-numbers", 190)
        );

        return statistics;
    }

    private String day(int daysFromNow) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, daysFromNow);
        int day = calender.get(Calendar.DAY_OF_WEEK);
        switch(day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Error";
        }
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

    /*private void animateNumbers(Label timerLabel) {
        String destValue = timerLabel.getText();
        Integer counter = 0;
        Integer timeSeconds = Integer.parseInt(destValue);
        timerLabel.setText("0");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1),
                new EventHandler() {
                    // KeyFrame event handler
                    public void handle(ActionEvent event) {
                        counter++;
                        // update timerLabel
                        timerLabel.setText(
                                counter.toString());
                        if (counter >= timeSeconds) {
                            timeline.stop();
                        }
                    }
                }));
        timeline.playFromStart();
    }*/

}
