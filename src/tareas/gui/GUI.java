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

public class GUI{
	Display display = new Display();
	Shell shell = new Shell(display);
	private Table table_1;
	
	public GUI() {

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
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	
	public static void main(String args[]){
		new GUI();
	}
}
