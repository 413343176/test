package cn.com.agree.ab.client.gui.stepProgressBar;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import cn.com.agree.ab.client.gui.controller.IController;
import cn.com.agree.ab.client.gui.stepProgressBar.image.CircleBarImage;
import cn.com.agree.ab.client.gui.stepProgressBar.image.IProgressBarImage;
import cn.com.agree.ab.client.gui.stepProgressBar.image.SquareBarImage;

public class ProgressBarManage {
	
	private static ProgressBarComposite progressBarComposite=null;
	private static ProgressBarCanvas canvas=null;
	private IProgressBarImage progressBarImage=null;
	private static int nowStep=1;
	private static int stepSum=0;
	private static List<String> stepNames=null;
	
	public ProgressBarManage(IController controller, Composite parent, int style){
		progressBarComposite=new ProgressBarComposite(parent, style);
		progressBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));
		
		canvas = new ProgressBarCanvas(progressBarComposite, SWT.FULL_SELECTION);//SWT.BORDER
	}
	
	public void init(int stepSum,int type,List<String> stepName){
		nowStep=1;
		stepNames=stepName;
		setStepNum(stepSum);
		switch(type){
		case 1:progressBarImage=new SquareBarImage(canvas.getBackgroundImage());break;
		case 2:progressBarImage=new CircleBarImage(canvas.getBackgroundImage());break;
		}
		canvas.refresh();
	}

	public static ProgressBarComposite getProgressBarComposite() {
		return progressBarComposite;
	}

	public IProgressBarImage getProgressBarImage() {
		return progressBarImage;
	}

	public static int getNowStep() {
		return nowStep;
	}

	public static void setNowStep(int step) {
		if(step<1||step>stepSum){
			return;
		}else{
			nowStep = step;
		}
	}
	
	public void refreshCanvas(){
		canvas.refresh();
	}

	public static int getStepNum() {
		return stepSum;
	}

	public static void setStepNum(int stepNum) {
		ProgressBarManage.stepSum = stepNum;
	}

	public static List<String> getStepNames() {
		return stepNames;
	}
}
