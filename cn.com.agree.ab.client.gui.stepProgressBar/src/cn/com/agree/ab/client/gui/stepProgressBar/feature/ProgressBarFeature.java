package cn.com.agree.ab.client.gui.stepProgressBar.feature;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cn.com.agree.ab.client.gui.controller.IController;
import cn.com.agree.ab.client.gui.feature.AbstractFeature;
import cn.com.agree.ab.client.gui.stepProgressBar.view.StepProgressBarView;
import cn.com.agree.ab.client.gui.view.IView;

public class ProgressBarFeature extends AbstractFeature{

	@Override
	public IView createView(IController controller, Composite parentContentPane) {
		return new StepProgressBarView(controller, parentContentPane, SWT.NONE);
	}

}
