package cn.com.agree.ab.client.gui.stepProgressBar;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import cn.com.agree.ab.client.gui.stepProgressBar.image.IProgressBarImage;

public class ProgressBarComposite extends Composite{
	
	static Display display = Display.getDefault();
	private static IProgressBarImage progressBarImage=null;
	
	public ProgressBarComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

	public static IProgressBarImage getProgressBarImage() {
		return progressBarImage;
	}

	public static void setProgressBarImage(IProgressBarImage progressBarImage) {
		ProgressBarComposite.progressBarImage = progressBarImage;
	}
	
}
