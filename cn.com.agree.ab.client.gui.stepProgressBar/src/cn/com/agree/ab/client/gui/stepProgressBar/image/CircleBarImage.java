package cn.com.agree.ab.client.gui.stepProgressBar.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import cn.com.agree.ab.client.gui.stepProgressBar.ProgressBarManage;

public class CircleBarImage implements IProgressBarImage {
	private int stepSum = 0;
	private Map<String, Image> normalImageMap = new HashMap<String, Image>();
	private Map<String, Image> grayImageMap = new HashMap<String, Image>();
	private Image backgroundImage;
	private Display display = Display.getDefault();
	private int ovalDiameter = 40;
	private static String normalStrip = "normalStrip";
	private static String grayStrip = "grayStrip";
	private int stripHigh = ovalDiameter / 10;
	private int stripWide = ovalDiameter * 4;
	private List<String> stepName = null;

	private Font font;
	private Point fontSize;
	Color color = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);// 字体颜色

	public CircleBarImage(Image backgroundImage) {

		init(backgroundImage);
		createImageMap();
		drawToCanvas();
	}

	private void init(Image backgroundImage) {
		stepSum = ProgressBarManage.getStepNum();
		stepName = ProgressBarManage.getStepNames();
		this.backgroundImage = backgroundImage;
		font = new Font(Display.getDefault(), "粗体", ovalDiameter / 3, SWT.BOLD);
	}

	private void createImageMap() {
		for (int i = 1; i <= stepSum; i++) {

			Image modelImage = new Image(display, 500, 200);
			GC gc = new GC(modelImage);
			gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
			gc.fillOval(0, 0, ovalDiameter, ovalDiameter);
			gc.setFont(font);
			gc.setForeground(color);
			fontSize = gc.textExtent("" + i);
			gc.drawText("" + i, (ovalDiameter - fontSize.x) / 2,
					(ovalDiameter - fontSize.y) / 2, true);
			gc.setClipping(0, 0, ovalDiameter, ovalDiameter + fontSize.y + 3);
			normalImageMap.put("" + i, modelImage);
			gc.dispose();

			Image imageGray = new Image(Display.getDefault(), 500, 200);
			GC gcGray = new GC(imageGray);
			gcGray.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			gcGray.fillOval(0, 0, ovalDiameter, ovalDiameter);
			gcGray.setFont(font);
			gcGray.setForeground(color);
			gcGray.drawText("" + i, (ovalDiameter - fontSize.x) / 2,
					(ovalDiameter - fontSize.y) / 2, true);
			gcGray.setClipping(0, 0, ovalDiameter, ovalDiameter + fontSize.y
					+ 3);
			grayImageMap.put("" + i, imageGray);
			gc.dispose();

		}

		Image stripNormal = new Image(Display.getDefault(), 500, 200);
		GC gcStrip = new GC(stripNormal);
		gcStrip.setAdvanced(true);
		gcStrip.setAntialias(SWT.ON);
		gcStrip.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gcStrip.fillRectangle(0, 0, stripWide, stripHigh);
		gcStrip.setFont(font);
		gcStrip.setForeground(color);
		gcStrip.setClipping(0, 0, stripWide, stripHigh);
		normalImageMap.put(normalStrip, stripNormal);
		gcStrip.dispose();

		Image stripGray = new Image(Display.getDefault(), 500, 200);
		GC gcStripGray = new GC(stripGray);
		gcStripGray.setAdvanced(true);
		gcStripGray.setAntialias(SWT.ON);
		gcStripGray.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		gcStripGray.fillRectangle(0, 0, stripWide, stripHigh);
		gcStripGray.setFont(font);
		gcStripGray.setForeground(color);
		gcStripGray.setClipping(0, 0, stripWide, stripHigh);
		grayImageMap.put(grayStrip, stripGray);
		gcStripGray.dispose();
	}

	private void drawToCanvas() {
		if (stepSum < 1) {
			return;
		}
		GC backGc = new GC(backgroundImage);
		for (int i = 1; i <= stepSum; i++) {
			Point ovalPoint = conutOvalPoint(i);
			Point stripPoint = countStripPoint(i);
			if (i == 1) {
				backGc.drawImage(normalImageMap.get("" + i), ovalPoint.x,
						ovalPoint.y);
			} else {
				backGc.drawImage(grayImageMap.get(grayStrip), stripPoint.x,
						stripPoint.y);
				backGc.drawImage(grayImageMap.get("" + i), ovalPoint.x,
						ovalPoint.y);
			}
		}
		drawStepName(backGc);
		backGc.setClipping(0, 0, countStripPoint(stepSum + 1).x + ovalDiameter
				/ 4, ovalDiameter+fontSize.y+3);
		backGc.dispose();
	}

	private void drawStepToCanvas(int step) {
		if (step < 1) {
			return;
		}
		GC backGc = new GC(backgroundImage);
		for (int i = 1; i <= stepSum; i++) {
			Point ovalPoint = conutOvalPoint(i);
			Point stripPoint = countStripPoint(i);
			if (i == 1) {
				backGc.drawImage(normalImageMap.get("" + i), ovalPoint.x,
						ovalPoint.y);
			} else {
				if (i <= step) {
					backGc.drawImage(normalImageMap.get(normalStrip),
							stripPoint.x, stripPoint.y);
					backGc.drawImage(normalImageMap.get("" + i), ovalPoint.x,
							ovalPoint.y);
				} else {
					backGc.drawImage(grayImageMap.get(grayStrip), stripPoint.x,
							stripPoint.y);
					backGc.drawImage(grayImageMap.get("" + i), ovalPoint.x,
							ovalPoint.y);
				}
			}
		}
		drawStepName(backGc);
		backGc.setClipping(0, 0, countStripPoint(stepSum + 1).x + ovalDiameter
				/ 4, ovalDiameter+fontSize.y+3);
//		backGc.setClipping(0, 0, countStripPoint(stepSum + 1).x + ovalDiameter
//				/ 4, ovalDiameter);
		backGc.dispose();
	}

	@Override
	public String nextStep(int nowStep) {
		if (nowStep == stepSum) {
			return "已经是最后的步骤。";
		} else {
			drawStepToCanvas(nowStep + 1);
		}
		return null;
	}

	@Override
	public String backStep(int nowStep) {
		if ((nowStep - 1) < 1) {
			return "已经是初始步骤。";
		} else {
			drawStepToCanvas(nowStep - 1);
		}
		return null;
	}

	@Override
	public void setStep(int stepNum) {
		if (stepNum < 1) {
			// 初始步骤，什么也不做
		} else {
			drawStepToCanvas(stepNum);
		}
	}

	/**
	 * 计算第n步时长条图片的位置
	 * 
	 * @param step
	 * @return
	 */
	private Point countStripPoint(int step) {
		Point point = null;
		if (step < 2) {
			return null;
		} else if (step == 2) {
			point = new Point(ovalDiameter, (ovalDiameter - stripHigh) / 2);// ovalDiameter*3
																			// /4
		} else {
			point = new Point((step - 1) * ovalDiameter + (step - 2)
					* stripWide - (step - 2) * ovalDiameter / 2,
					(ovalDiameter - stripHigh) / 2);
		}
		return point;
	}

	/**
	 * 计算第n步圆形图片的位置
	 * 
	 * @param step
	 * @return
	 */
	private Point conutOvalPoint(int step) {
		Point point = null;
		if (step < 1) {
			return null;
		} else {
			point = new Point((step - 1) * (ovalDiameter + stripWide)
					- (step - 1) * ovalDiameter / 2, 0);
		}
		return point;
	}

	private void drawStepName(GC gc){
		if (stepSum < 1) {
			return;
		}
		for (int i = 1; i <= stepSum; i++) {
			Point stepNamePoint = conutOvalPoint(i);
			if (stepName != null && stepName.get(i - 1) != null) {
				gc.drawText(stepName.get(i - 1), stepNamePoint.x, ovalDiameter + 3, true);
			}
		}
	}
}
