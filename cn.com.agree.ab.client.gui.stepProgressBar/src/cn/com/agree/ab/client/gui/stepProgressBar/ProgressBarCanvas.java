package cn.com.agree.ab.client.gui.stepProgressBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ProgressBarCanvas{
	
	private Image backgroundImage=new Image(Display.getDefault(), 2000, 500);//背景图片，用于承载其他图片
	private static Canvas canvas;

	public ProgressBarCanvas(Composite parent, int style) {
		canvas = new Canvas(parent, SWT.FULL_SELECTION);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(backgroundImage, 0, 0);
			}
		});
		canvas.setData(backgroundImage);
		canvas.redraw();
	}
	
	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public static Canvas getCanvas() {
		return canvas;
	}
	
	public void refresh(){
		canvas.redraw();
	}
}
