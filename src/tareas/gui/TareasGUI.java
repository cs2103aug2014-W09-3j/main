package tareas.gui;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class TareasGUI{
	Display display = new Display();
	Shell shell = new Shell(display);
	private Table table_1;
	
	public void run() {

		shell.setSize(693, 474);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setText("Tareas");

		shell.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rect = shell.getClientArea();
				Image newImage = new Image(display, Math.max(1, rect.width), 1);
				GC gc = new GC(newImage);
				Device device = Display.getCurrent();
				Color red = new Color(device, 244, 219, 226);
				Color darkRed = new Color(device, 192, 131, 181);

				gc.setForeground(red);
				gc.setBackground(darkRed);
				gc.fillGradientRectangle(rect.x, rect.y, rect.width, 1, false);
				gc.dispose();
				shell.setBackgroundImage(newImage);
			}
		});
		shell.setLayout(new FormLayout());
		
		DateTime calendar = new DateTime(shell, SWT.CALENDAR);
		FormData fd_calendar = new FormData();
		fd_calendar.bottom = new FormAttachment(0, 154);
		fd_calendar.top = new FormAttachment(0, 6);
		fd_calendar.right = new FormAttachment(0, 149);
		fd_calendar.left = new FormAttachment(0, 10);

		
		calendar.setLayoutData(fd_calendar);
		
		final Text text = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -10);
		fd_text.left = new FormAttachment(0, 5);
		text.setLayoutData(fd_text);
		
		Image image = null;
		if ((text.getStyle() & SWT.ICON_CANCEL) == 0) {
			image = display.getSystemImage(SWT.ICON_ERROR);
			ToolBar toolBar = new ToolBar(shell, SWT.FLAT);
			ToolItem item = new ToolItem(toolBar, SWT.PUSH);
			item.setImage(image);
		}
		
		final Label lblNewLabel = new Label(shell, SWT.NONE);
		fd_text.bottom = new FormAttachment(lblNewLabel, -6);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(100, -104);
		fd_lblNewLabel.left = new FormAttachment(0, 5);
		fd_lblNewLabel.bottom = new FormAttachment(100, -10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Welcome to Tareas");
		text.setText("Type your command here");
		
		text.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_RETURN) {
					System.out.println(text.getText());
					lblNewLabel.setText(text.getText());
					text.setText(" ");
				}
			}
		});
		
		Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(calendar, 6);
		fd_table.bottom = new FormAttachment(text, -13);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.right = new FormAttachment(100, -10);
		
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Creating column for TaskID.
		TableColumn taskIDClmn = new TableColumn(table, SWT.NULL);
		taskIDClmn.setWidth(63);
		taskIDClmn.setText("Task ID");

		// Creating column for Category.
		TableColumn categoryClmn = new TableColumn(table, SWT.NONE);
		categoryClmn.setWidth(69);
		categoryClmn.setText("Category");

		// Creating column for description.
		TableColumn descriptionClmn = new TableColumn(table, SWT.NONE);
		descriptionClmn.setWidth(224);
		descriptionClmn.setText("Description");

		// Creating column for deadline.
		TableColumn deadlineClmn = new TableColumn(table, SWT.NONE);
		deadlineClmn.setWidth(107);
		deadlineClmn.setText("Deadline");

		TableColumn startTimeClmn = new TableColumn(table, SWT.NONE);
		startTimeClmn.setWidth(107);
		startTimeClmn.setText("Start Time");

		TableColumn endTimeClmn = new TableColumn(table, SWT.NONE);
		endTimeClmn.setWidth(107);
		endTimeClmn.setText("End Time");
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	
	public static void main(String args[]){
		new TareasGUI().run();
	}
}

