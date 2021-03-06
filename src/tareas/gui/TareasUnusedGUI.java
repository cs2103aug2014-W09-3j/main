//@author A0112151A -- unused
//Reason: our group decided to change the GUI library from SWT to Javafx. This
//GUI is written in SWT. The following code was not future debugged and refactored
//as the change was very soon after the code was written.

//
//package tareas.gui;
//
//import java.awt.BorderLayout;
//import java.util.ArrayList;
//
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//
//import tareas.controller.*;
//import tareas.common.*;
//import tareas.parser.TareasCommand;
//
//import org.eclipse.jface.window.ApplicationWindow;
//import org.eclipse.jface.dialogs.*;
//import org.eclipse.swt.*;
//import org.eclipse.swt.events.*;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Device;
//import org.eclipse.swt.graphics.GC;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//
//public class TareasUnusedGUI implements Runnable {
//    TareasController controller = new TareasController();
//    Display display = new Display();
//    Shell shell = new Shell(display);
//    private Table table_1;
//    private Table table;
//    private Text text;
//    private Label lblNewLabel;
//    private int counter = 0 ;
//    private TableItem item;
//    private ArrayList<String> string;
//
//
//    public Display getDisplay(){
//        return display;
//    }
//
//    public void run() {
//        string = new ArrayList<String>();
//        shell.setSize(693, 474);
//        shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
//        shell.setText("Tareas");
//
//        shell.addListener(SWT.Resize, new Listener() {
//            @Override
//            public void handleEvent(Event event) {
//                Rectangle rect = shell.getClientArea();
//                Image newImage = new Image(display, Math.max(1, rect.width), 1);
//                GC gc = new GC(newImage);
//                Device device = Display.getCurrent();
//                Color red = new Color(device, 244, 219, 226);
//                Color darkRed = new Color(device, 192, 131, 181);
//
//                gc.setForeground(red);
//                gc.setBackground(darkRed);
//                gc.fillGradientRectangle(rect.x, rect.y, rect.width, 1, false);
//                gc.dispose();
//                shell.setBackgroundImage(newImage);
//            }
//        });
//        shell.setLayout(new FormLayout());
//
//        DateTime calendar = new DateTime(shell, SWT.CALENDAR);
//        FormData fd_calendar = new FormData();
//        fd_calendar.bottom = new FormAttachment(0, 154);
//        fd_calendar.top = new FormAttachment(0, 6);
//        fd_calendar.right = new FormAttachment(0, 149);
//        fd_calendar.left = new FormAttachment(0, 10);
//
//
//        calendar.setLayoutData(fd_calendar);
//
//        text = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL);
//        FormData fd_text = new FormData();
//        fd_text.right = new FormAttachment(100, -10);
//        fd_text.left = new FormAttachment(0, 5);
//        text.setLayoutData(fd_text);
//
//        Image image = null;
//        if ((text.getStyle() & SWT.ICON_CANCEL) == 0) {
//            image = display.getSystemImage(SWT.ICON_ERROR);
//            ToolBar toolBar = new ToolBar(shell, SWT.FLAT);
//            ToolItem item = new ToolItem(toolBar, SWT.PUSH);
//            item.setImage(image);
//        }
//
//        lblNewLabel = new Label(shell, SWT.NONE);
//        fd_text.bottom = new FormAttachment(lblNewLabel, -6);
//        FormData fd_lblNewLabel = new FormData();
//        fd_lblNewLabel.right = new FormAttachment(100, -104);
//        fd_lblNewLabel.left = new FormAttachment(0, 5);
//        fd_lblNewLabel.bottom = new FormAttachment(100, -10);
//        lblNewLabel.setLayoutData(fd_lblNewLabel);
//        lblNewLabel.setText("Welcome to Tareas");
//        text.setText("Type your command here");
//
//        text.addListener(SWT.Traverse, new Listener() {
//            @Override
//            public void handleEvent(Event event) {
//                if (event.detail == SWT.TRAVERSE_RETURN) {
//                    System.out.println(text.getText());
//                    if(text.getText().equals("add"))
//
//                        lblNewLabel.setText(text.getText());
//                    text.setText(" ");
//                }
//            }
//        });
//        JTable table2 = new JTable(3, 3);
//
//        Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
//                { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
//        Object columnNames[] = { "Column One", "Column Two", "Column Three" };
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JTable table3 = new JTable(rowData, columnNames);
//
//        JScrollPane scrollPane = new JScrollPane(table3);
//        frame.add(scrollPane, BorderLayout.CENTER);
//        frame.setSize(300, 150);
//        frame.setVisible(true);
//
//        table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
//        FormData fd_table = new FormData();
//        fd_table.top = new FormAttachment(calendar, 6);
//        fd_table.bottom = new FormAttachment(text, -13);
//        fd_table.left = new FormAttachment(0, 10);
//        fd_table.right = new FormAttachment(100, -10);
//
//        table.setLayoutData(fd_table);
//        table.setHeaderVisible(true);
//        table.setLinesVisible(true);
//
//        // Creating column for TaskID.
//        TableColumn taskIDClmn = new TableColumn(table, SWT.NULL);
//        taskIDClmn.setWidth(63);
//        taskIDClmn.setText("Task ID");
//
//        // Creating column for Category.
//        TableColumn categoryClmn = new TableColumn(table, SWT.NONE);
//        categoryClmn.setWidth(69);
//        categoryClmn.setText("Category");
//
//        // Creating column for description.
//        TableColumn descriptionClmn = new TableColumn(table, SWT.NONE);
//        descriptionClmn.setWidth(224);
//        descriptionClmn.setText("Description");
//
//        // Creating column for deadline.
//        TableColumn deadlineClmn = new TableColumn(table, SWT.NONE);
//        deadlineClmn.setWidth(107);
//        deadlineClmn.setText("Deadline");
//
//        TableColumn startTimeClmn = new TableColumn(table, SWT.NONE);
//        startTimeClmn.setWidth(107);
//        startTimeClmn.setText("Start Time");
//
//        TableColumn endTimeClmn = new TableColumn(table, SWT.NONE);
//        endTimeClmn.setWidth(107);
//        endTimeClmn.setText("End Time");
//
//        table_1 = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
//        FormData fd_table_1 = new FormData();
//        fd_table_1.bottom = new FormAttachment(calendar, 0, SWT.BOTTOM);
//        fd_table_1.right = new FormAttachment(100, -10);
//        fd_table_1.left = new FormAttachment(calendar, 6);
//        fd_table_1.top = new FormAttachment(0, 5);
//        table_1.setLayoutData(fd_table_1);
//        table_1.setHeaderVisible(true);
//        table_1.setLinesVisible(true);
//
//        TableColumn tblclmnTaskId = new TableColumn(table_1, SWT.NONE);
//        tblclmnTaskId.setWidth(100);
//        tblclmnTaskId.setText("Task ID");
//
//        TableColumn tblclmnDescription = new TableColumn(table_1, SWT.NONE);
//        tblclmnDescription.setWidth(100);
//        tblclmnDescription.setText("Description");
//
//        text.addListener(SWT.Traverse, new Listener() {
//            @Override
//            public void handleEvent(Event event) {
//                if (event.detail == SWT.TRAVERSE_RETURN) {
//                    String line = text.getText();
//                    TareasCommand command = controller.executeCommand(line);
//                    process(command);
//                    //counter++;
//                    System.out.println(line);
//                    if(line.equals("add")){
//                        TableItem item = new TableItem(table, SWT.NULL);
//                        //	item.setText(0, "Item 10");
//                        //	add();
//
//                    }
//
//                    System.out.println("added");
//                    lblNewLabel.setText(text.getText());
//                    text.setText("");
//                }
//            }
//        });
//
//        //	TableItem item = new TableItem(table, SWT.NULL);
//        for (int loopIndex = 0; loopIndex < 4; loopIndex++) {
//            TableItem item = new TableItem(table, SWT.NULL);
//            // item.setText("Item " + loopIndex);
//            item.setText(0, "Item " + loopIndex);
//            item.setText(1, "Yes");
//            item.setText(2, "No");
//            item.setText(3, "A table item");
//            item.setText(4, "trying");
//            item.setText(5, "ok");
//        }
//        TableItem item = new TableItem(table, SWT.NULL);
//        item.setText(0, "Item 4");
//
//
//        shell.open();
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch())
//                display.sleep();
//        }
//        //display.dispose();
//    }
//
//    public void add(Task newTask){
//        item.setText(newTask.getTaskID(), newTask.getTaskID());
//
//    }
//
//    public void process(TareasCommand command){
//        switch(command.getType()){
//            case ADD_COMMAND:
//                String taskDescription = command.getPrimaryArgument();
//                TableItem test = new TableItem(table, SWT.NULL);
//
//                test.setText(0,  "Task " + counter++);
//                test.setText(1, taskDescription);
//
//                String[] result = test.getText().split("\\s");
//
//                String word = result[0] + " " + result[1];
//                System.out.println(word);
//                string.add(word);
//
//                String feedbackAdd = "Task \""  +  taskDescription + "\" added";
//                lblNewLabel.setText(feedbackAdd);
//
//                break;
//
//            case EDIT_COMMAND:
//                int taskEdit = Integer.parseInt(command.getPrimaryArgument());
//                String taskDescriptionEdit = command.getArgument("des");
//                TableItem editing = new TableItem(table, SWT.NULL);
//                boolean exists = false;
//                String taskName = "Task " + taskEdit;
//                int index = 0;
//
//                for(int i = 0; i < string.size(); i++){
//                    System.out.println(string);
//                    if(string.get(i).equals(taskName)){
//                        System.out.println("looping");
//                        exists = true;
//                        index = i;
//                        string.remove(i);
//                    }
//
//                }
//                if(exists){
//                    System.out.println("yes");
//                    table.remove(index);
//                    editing.setText(0, "Task " + taskEdit);
//                    editing.setText(1, taskDescriptionEdit);
//                    lblNewLabel.setText("Task found");
//                    string.add("Task " + taskEdit);
//                }
//                else
//                    lblNewLabel.setText("Task not found");
//
//                break;
//
//            case DELETE_COMMAND:
//                int taskDelete = Integer.parseInt(command.getPrimaryArgument());
//                boolean isDeleted = false;
//                String taskNameDelete = "Task " + taskDelete;
//
//                for(int i = 0; i < string.size(); i++){
//                    System.out.println(string);
//                    if(string.get(i).equals(taskNameDelete)){
//                        System.out.println("looping");
//                        isDeleted = true;
//                        table.remove(i);
//                        string.remove(i);
//                    }
//
//                }
//
//                if(isDeleted){
//                    System.out.println("ok");
//                    lblNewLabel.setText("Task deleted");
//                }
//                else
//                    lblNewLabel.setText("Task not found");
//                break;
//
//            case SEARCH_COMMAND:
//                int taskSearchId = Integer.parseInt(command.getPrimaryArgument());
//                boolean found = false;
//                String taskNameSearch = "Task " + taskSearchId;
//
//                for(int i = 0; i < string.size(); i++){
//                    System.out.println(string);
//                    if(string.get(i).equals(taskNameSearch)){
//                        System.out.println("looping");
//                        found = true;
//                    }
//
//                }
//
//                if(found){
//                    System.out.println("ok");
//                    lblNewLabel.setText("Task found");
//                }
//                else
//                    lblNewLabel.setText("Task not found");
//
//                break;
//
//            case DONE_COMMAND:
//                int taskDone = Integer.parseInt(command.getPrimaryArgument());
//                String taskNameDone = "Task " + taskDone;
//                boolean isDone = false;
//                for(int i = 0; i < string.size(); i++){
//                    System.out.println(string);
//                    if(string.get(i).equals(taskNameDone)){
//                        System.out.println("loopingdone");
//                        isDone = true;
//                        table.remove(i);
//                        //		counter--;
//                        string.remove(i);
//                    }
//                }
//
//                if(isDone)
//                    lblNewLabel.setText("Task " + taskDone+ " done");
//                else
//                    lblNewLabel.setText("Task " + taskDone+ " not found");
//                break;
//            default:
//                lblNewLabel.setText("Unrecognized command");
//                break;
//
//        }
//
//    }
//
//
//    public static void main(String args[]){
//        TareasGUI trying = new TareasGUI();
//        trying.run();
//
//    }
//}

