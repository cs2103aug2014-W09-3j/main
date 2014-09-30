//import org.eclipse.swt.*;
//import org.eclipse.swt.graphics.*;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//
//public class TaerasGUI {
//	static Image oldImage;
//	public static void main(String [] args) {
//		final Display display = new Display ();
//		final Shell shell = new Shell (display);
//		shell.setBackgroundMode (SWT.INHERIT_DEFAULT);
//		FillLayout layout1 = new FillLayout (SWT.VERTICAL);
//		layout1.marginWidth = layout1.marginHeight = 10;
//		shell.setLayout (layout1);
////		Group group = new Group (shell, SWT.NONE);
//	//	group.setText ("Group ");
//	//	RowLayout layout2 = new RowLayout (SWT.VERTICAL);
//		//layout2.marginWidth = layout2.marginHeight = layout2.spacing = 10;
//		//group.setLayout (layout2);
//		/*for (int i=0; i<8; i++) {
//			Button button = new Button (group, SWT.RADIO);
//			button.setText ("Button " + i);
//		}*/
//		shell.addListener (SWT.Resize, new Listener () {
//			@Override
//			public void handleEvent (Event event) {
//				Rectangle rect = shell.getClientArea ();
//				Image newImage = new Image (display, Math.max (1, rect.width), 1);	
//				GC gc = new GC (newImage);
//				gc.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
//				gc.setBackground (display.getSystemColor (SWT.COLOR_BLUE));
//				gc.fillGradientRectangle (rect.x, rect.y, rect.width, 1, false);
//				gc.dispose ();
//				shell.setBackgroundImage (newImage);
//			//	if (oldImage != null) oldImage.dispose ();
//			//	oldImage = newImage;
//			}
//		});
//		shell.pack ();
//		shell.open ();
//		while (!shell.isDisposed ()) {
//			if (!display.readAndDispatch ()) display.sleep ();
//		}
//		if (oldImage != null) oldImage.dispose ();
//		display.dispose ();
//	}
//}

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class TaerasGUI {
	private static Table table;

public static void main (String [] args) {

	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setSize(630, 342);
	shell.setBackgroundMode (SWT.INHERIT_DEFAULT);
	//shell.setLayout (new RowLayout ());


	shell.addListener (SWT.Resize, new Listener () {
		@Override
		public void handleEvent (Event event) {
			Rectangle rect = shell.getClientArea ();
			Image newImage = new Image (display, Math.max (1, rect.width), 1);	
			GC gc = new GC (newImage);
			Device device = Display.getCurrent ();
			Color red = new Color (device, 244, 219, 226);
			Color darkRed = new Color(device, 192, 131, 181);

			gc.setForeground (red);
			gc.setBackground (darkRed);			
			gc.fillGradientRectangle (rect.x, rect.y, rect.width, 1, false);
			gc.dispose ();
			shell.setBackgroundImage (newImage);
		//	if (oldImage != null) oldImage.dispose ();
		//	oldImage = newImage;
		}
	});
	shell.setLayout(new FormLayout());
	
	DateTime calendar = new DateTime (shell, SWT.CALENDAR);
	FormData fd_calendar = new FormData();
	calendar.setLayoutData(fd_calendar);
	final Text text = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL);
	FormData fd_text = new FormData();
	fd_text.right = new FormAttachment(100, -10);
	fd_text.left = new FormAttachment(0, 5);
	text.setLayoutData(fd_text);

	Image image = null;
	if ((text.getStyle() & SWT.ICON_CANCEL) == 0) {
		image = display.getSystemImage(SWT.ICON_ERROR);
		ToolBar toolBar = new ToolBar (shell, SWT.FLAT);
		ToolItem item = new ToolItem (toolBar, SWT.PUSH);
		item.setImage (image);
//		item.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				text.setText("");
//				System.out.println("Search cancelled");
//			}
//		});
	}
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		fd_text.bottom = new FormAttachment(lblNewLabel, -6);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(100, -104);
		fd_lblNewLabel.left = new FormAttachment(0, 5);
		fd_lblNewLabel.bottom = new FormAttachment(100, -10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Categorized task 1000 to grocerries");
							text.setText("Type your command here");
							
							table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
							fd_calendar.top = new FormAttachment(table, 0, SWT.TOP);
							fd_calendar.right = new FormAttachment(table, -3);
							FormData fd_table = new FormData();
							fd_table.left = new FormAttachment(0, 152);
							fd_table.right = new FormAttachment(100, -12);
							fd_table.bottom = new FormAttachment(text, -13);
							fd_table.top = new FormAttachment(0, 5);
							table.setLayoutData(fd_table);
							table.setHeaderVisible(true);
							table.setLinesVisible(true);
							
							TableColumn tblclmnTaskId = new TableColumn(table, SWT.NONE);
							tblclmnTaskId.setWidth(63);
							tblclmnTaskId.setText("Task ID");
							
							TableColumn tblclmnCategory = new TableColumn(table, SWT.NONE);
							tblclmnCategory.setWidth(69);
							tblclmnCategory.setText("Category");
							
							TableColumn tblclmnDescription = new TableColumn(table, SWT.NONE);
							tblclmnDescription.setWidth(224);
							tblclmnDescription.setText("Description");
							
							TableColumn tblclmnDeadline = new TableColumn(table, SWT.NONE);
							tblclmnDeadline.setWidth(107);
							tblclmnDeadline.setText("Deadline");
//	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	if (image != null) image.dispose(); 
	display.dispose();
}
}


//import org.eclipse.swt.*;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//import org.eclipse.swt.custom.*;
//import org.eclipse.swt.graphics.*;
//
//public class TaerasGUI{
//
//	static String text = "Plans do not materialize out of nowhere, nor are they entirely static. To ensure the planning process is " +
//		"transparent and open to the entire Eclipse community, we (the Eclipse PMC) post plans in an embryonic "+
//		"form and revise them throughout the release cycle. \n"+
//		"The first part of the plan deals with the important matters of release deliverables, release milestones, target "+
//		"operating environments, and release-to-release compatibility. These are all things that need to be clear for "+
//		"any release, even if no features were to change.  \n";
//	static Image oldImage;
//	
//	public static void main(String [] args) {
//		final Display display = new Display();
//		final Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout());
//		final StyledText styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
//		styledText.setText(text);
//		FontData data = display.getSystemFont().getFontData()[0];
//		Font font = new Font(display, data.getName(), 16, SWT.BOLD);
//		styledText.setFont(font);
//		styledText.setForeground(display.getSystemColor (SWT.COLOR_BLUE));
//		styledText.addListener (SWT.Resize, new Listener () {
//			@Override
//			public void handleEvent (Event event) {
//				Rectangle rect = styledText.getClientArea ();
//				Image newImage = new Image (display, 1, Math.max (1, rect.height));
//				GC gc = new GC (newImage);
//				gc.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
//				gc.setBackground (display.getSystemColor (SWT.COLOR_YELLOW));
//				gc.fillGradientRectangle (rect.x, rect.y, 1, rect.height, true);
//				gc.dispose ();
//				styledText.setBackgroundImage (newImage);
//				if (oldImage != null) oldImage.dispose ();
//				oldImage = newImage;
//			}
//		});	
//		shell.setSize(700, 400);
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//		if (oldImage != null) oldImage.dispose ();
//		font.dispose();
//		display.dispose();
//	}
//}
//
//import org.eclipse.swt.*;
//import org.eclipse.swt.graphics.*;
//import org.eclipse.swt.widgets.*;
//
//public class TaerasGUI {
//
//public static void main (String [] args) {
//	Display display = new Display ();
//	Shell shell = new Shell (display);
//	Text text = new Text (shell, SWT.BORDER);
//	Rectangle clientArea = shell.getClientArea ();
//	text.setLocation (clientArea.x, clientArea.y);
//	int columns = 10;
//	GC gc = new GC (text);
//	FontMetrics fm = gc.getFontMetrics ();
//	int width = columns * fm.getAverageCharWidth ();
//	int height = fm.getHeight ();
//	gc.dispose ();
//	text.setSize (text.computeSize (width, height));
//	shell.pack ();
//	shell.open ();
//	while (!shell.isDisposed ()) {
//		if (!display.readAndDispatch ()) display.sleep ();
//	}
//	display.dispose ();
//}
//}

//import org.eclipse.swt.*;
//import org.eclipse.swt.graphics.*;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//
///*
// * Detect when the user scrolls a text control
// * 
// * For a list of all SWT example snippets see
// * http://www.eclipse.org/swt/snippets/
// */
//
//public class TaerasGUI {
//public static void main(String[] args) {
//	Display display = new Display ();
//	Shell shell = new Shell (display);
//	shell.setLayout (new FillLayout ());
//	final Text text = new Text (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//	for (int i=0; i<32; i++) {
//		text.append (i + "-This is a line of text in a widget-" + i + "\n");
//	}
//	text.setSelection (0);
//	Listener listener = new Listener () {
//		int lastIndex = text.getTopIndex ();
//		@Override
//		public void handleEvent (Event e) {
//			int index = text.getTopIndex ();
//			if (index != lastIndex) {
//				lastIndex = index;
//				System.out.println ("Scrolled, topIndex=" + index);
//			}
//		}
//	};
//	/* NOTE: Only detects scrolling by the user */
//	text.addListener (SWT.MouseDown, listener);
//	text.addListener (SWT.MouseMove, listener);
//	text.addListener (SWT.MouseUp, listener);
//	text.addListener (SWT.KeyDown, listener);
//	text.addListener (SWT.KeyUp, listener);
//	text.addListener (SWT.Resize, listener);
//	ScrollBar hBar = text.getHorizontalBar();
//	if (hBar != null) {
//		hBar.addListener (SWT.Selection, listener);
//	}
//	ScrollBar vBar = text.getVerticalBar();
//	if (vBar != null) {
//		vBar.addListener (SWT.Selection, listener);
//	}
//	shell.pack ();
//	Point size = shell.computeSize (SWT.DEFAULT, SWT.DEFAULT);
//	shell.setSize (size. x - 32, size.y / 2);
//	shell.open ();
//	while (!shell.isDisposed ()) {
//		if (!display.readAndDispatch ()) display.sleep ();
//	}
//	display.dispose ();
//}
//}




//content assist
//import org.eclipse.swt.*;
//import org.eclipse.swt.graphics.*;
//import org.eclipse.swt.layout.*;
//import org.eclipse.swt.widgets.*;
//
//public class TaerasGUI {
//
//public static void main(String [] args) {
//	final Display display = new Display();
//	final Shell shell = new Shell(display);
//	shell.setLayout(new GridLayout());
//	final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);
//	text.setLayoutData(new GridData(150, SWT.DEFAULT));
//	shell.pack();
//	shell.open();
//
//	final Shell popupShell = new Shell(display, SWT.ON_TOP);
//	popupShell.setLayout(new FillLayout());
//	final Table table = new Table(popupShell, SWT.SINGLE);
//	for (int i = 0; i < 5; i++) {
//		new TableItem(table, SWT.NONE);
//	}
//
//	text.addListener(SWT.KeyDown, new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			switch (event.keyCode) {
//				case SWT.ARROW_DOWN:
//					int index = (table.getSelectionIndex() + 1) % table.getItemCount();
//					table.setSelection(index);
//					event.doit = false;
//					break;
//				case SWT.ARROW_UP:
//					index = table.getSelectionIndex() - 1;
//					if (index < 0) index = table.getItemCount() - 1;
//					table.setSelection(index);
//					event.doit = false;
//					break;
//				case SWT.CR:
//					if (popupShell.isVisible() && table.getSelectionIndex() != -1) {
//						text.setText(table.getSelection()[0].getText());
//						popupShell.setVisible(false);
//					}
//					break;
//				case SWT.ESC:
//					popupShell.setVisible(false);
//					break;
//			}
//		}
//	});
//	text.addListener(SWT.Modify, new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			String string = text.getText();
//			if (string.length() == 0) {
//				popupShell.setVisible(false);
//			} else {
//				TableItem[] items = table.getItems();
//				for (int i = 0; i < items.length; i++) {
//					items[i].setText(string + '-' + i);
//				}
//				Rectangle textBounds = display.map(shell, null, text.getBounds());
//				popupShell.setBounds(textBounds.x, textBounds.y + textBounds.height, textBounds.width, 150);
//				popupShell.setVisible(true);
//			}
//		}
//	});
//
//	table.addListener(SWT.DefaultSelection, new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			text.setText(table.getSelection()[0].getText());
//			popupShell.setVisible(false);
//		}
//	});
//	table.addListener(SWT.KeyDown, new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			if (event.keyCode == SWT.ESC) {
//				popupShell.setVisible(false);
//			}
//		}
//	});
//
//	Listener focusOutListener = new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			/* async is needed to wait until focus reaches its new Control */
//			display.asyncExec(new Runnable() {
//				@Override
//				public void run() {
//					if (display.isDisposed()) return;
//					Control control = display.getFocusControl();
//					if (control == null || (control != text && control != table)) {
//						popupShell.setVisible(false);
//					}
//				}
//			});
//		}
//	};
//	table.addListener(SWT.FocusOut, focusOutListener);
//	text.addListener(SWT.FocusOut, focusOutListener);
//
//	shell.addListener(SWT.Move, new Listener() {
//		@Override
//		public void handleEvent(Event event) {
//			popupShell.setVisible(false);
//		}
//	});
//
//	while (!shell.isDisposed()) {
//		if (!display.readAndDispatch()) display.sleep();
//	}
//	display.dispose();
//}
//
//}