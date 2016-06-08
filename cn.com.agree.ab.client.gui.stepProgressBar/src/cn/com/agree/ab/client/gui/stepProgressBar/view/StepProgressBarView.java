package cn.com.agree.ab.client.gui.stepProgressBar.view;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cn.com.agree.ab.client.gui.controller.IController;
import cn.com.agree.ab.client.gui.stepProgressBar.ProgressBarManage;
import cn.com.agree.ab.client.gui.view.AbstractView;
import cn.com.agree.ab.client.gui.view.ArgsInfo;

public class StepProgressBarView extends AbstractView {
	ProgressBarManage progressBarManage;

	public StepProgressBarView(IController controller, Composite parent, int style) {
		progressBarManage = new ProgressBarManage(controller, parent, style);

	}

	@Override
	public Composite getContentPane() {
		return null;
	}

	@Override
	public Control getControl() {
		return ProgressBarManage.getProgressBarComposite();
	}

	@Override
	public void refresh(String key) {

	}

	public void init(@ArgsInfo(name = "stepSum", caption = "总步骤数") int stepSum,
			@ArgsInfo(name = "type", caption = "图标形状：1、长方形,2、圆形") int type,
			@ArgsInfo(name = "stepName", caption = "步骤名称") List<String> stepName) {
		progressBarManage.init(stepSum, type,stepName);
	}

	@SuppressWarnings("static-access")
	public void nextStep() {
		progressBarManage.getProgressBarImage().nextStep(
				progressBarManage.getNowStep());
		progressBarManage.setNowStep(progressBarManage.getNowStep() + 1);
		progressBarManage.refreshCanvas();
	}

	@SuppressWarnings("static-access")
	public void backStep() {
		progressBarManage.getProgressBarImage().backStep(
				progressBarManage.getNowStep());
		progressBarManage.setNowStep(progressBarManage.getNowStep() - 1);
		progressBarManage.refreshCanvas();
	}

	@SuppressWarnings("static-access")
	public void setStep(
			@ArgsInfo(name = "stepNum", caption = "要设置的位置") int stepNum) {
		progressBarManage.getProgressBarImage().setStep(stepNum);
		progressBarManage.setNowStep(stepNum);
		progressBarManage.refreshCanvas();
	}
}
