package cn.com.agree.ab.client.gui.stepProgressBar.image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import cn.com.agree.ab.client.gui.stepProgressBar.ProgressBarManage;


/**
 * 生成方块图片
 * @author auror
 *
 */
public class SquareBarImage implements IProgressBarImage{
	private int stepSum=0;
	private List<Image> normalImageList=new ArrayList<Image>();
	private List<Image> grayImageList=new ArrayList<Image>();
	private Image backgroundImage;
	private URL normalImageUrl = getClass().getResource("/image/"  + "squareNormal.jpg");
	private URL grayImageUrl = getClass().getResource("/image/"  + "squareGray.jpg");
	private Image normalImage = ImageDescriptor.createFromURL(normalImageUrl).createImage();
	private Image grayImage=ImageDescriptor.createFromURL(grayImageUrl).createImage();
	private List<String> stepName=null;
	
	private Font font;
	Color color = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);//字体颜色
	private Point fontPoint;

	//图片尺寸
	Rectangle rect=null;
	
	public SquareBarImage(Image backgroundImage){

		init(backgroundImage);
		createImageList();
		drawToCanvas();
	}
	
	private void init(Image backgroundImage){
		rect=normalImage.getBounds();
		stepSum=ProgressBarManage.getStepNum();
		stepName=ProgressBarManage.getStepNames();
		this.backgroundImage=backgroundImage;
		font = new Font( Display.getDefault(),"粗体", rect.height/3, SWT.BOLD);
		fontPoint=new Point(20,rect.height/3);
	}
	
	private void createImageList(){
		for(int i=1;i<=stepSum;i++){
			//创建正常颜色的图片
			Image normalImageTemp=new Image(null,normalImage.getImageData().scaledTo(rect.width, rect.height));			
			GC gc=new GC(normalImageTemp);
			gc.setFont(font);
			gc.setForeground(color);
			gc.drawText(""+i,fontPoint.x, fontPoint.y, true);
			if(stepName!=null&&stepName.get(i-1)!=null){
				gc.drawText(stepName.get(i-1),rect.width/3, fontPoint.y, true);
			}
			normalImageList.add(normalImageTemp);
			gc.dispose();
			//创建灰色图片
			Image grayImageTemp=new Image(null,grayImage.getImageData().scaledTo(rect.width, rect.height));
			GC gcGray=new GC(grayImageTemp);
			gcGray.setFont(font);
			gcGray.setForeground(color);
			gcGray.drawText(""+i,fontPoint.x, fontPoint.y, true);
			if(stepName!=null&&stepName.get(i-1)!=null){
				gcGray.drawText(stepName.get(i-1),rect.width/3, fontPoint.y, true);
			}
			grayImageList.add(grayImageTemp);
			gc.dispose();
			
		}
	}
	
	private void drawToCanvas(){
		if(stepSum<1){
			return;
		}
		GC backGc=new GC(backgroundImage);
		for(int i=0;i<stepSum;i++){
			if(i==0){
				backGc.drawImage(normalImageList.get(i), (i)*rect.width, 0);
			}else{
				backGc.drawImage(grayImageList.get(i), (i)*rect.width, 0);
			}
		}
		backGc.setClipping(0, 0, stepSum*rect.width, rect.height);
		backGc.dispose();
	}

	@Override
	public String nextStep(int nowStep) {
		if((nowStep+1)>stepSum){
			return "已经是最后一步。";
		}else{
			GC gc=new GC(backgroundImage);
			gc.drawImage(normalImageList.get(nowStep), nowStep*rect.width, 0);
			gc.dispose();
			return null;
		}
		
	}

	@Override
	public String backStep(int nowStep) {
		if((nowStep-1)<1){
			return "已经是初始步骤。";
		}else{
			GC gc=new GC(backgroundImage);
			gc.drawImage(grayImageList.get(nowStep-1), (nowStep-1)*rect.width, 0);
			gc.dispose();
			return null;
		}		
	}

	@Override
	public void setStep(int stepNum) {
		int nowStep=ProgressBarManage.getNowStep();
		GC gc=new GC(backgroundImage);
		if(nowStep==stepNum){
			//什么也不做
		}else if(stepNum>stepSum||stepNum<1){
			//设置的步骤超出范围，什么也不做
		}else if(nowStep<stepNum){
			for(int i=nowStep;i<=stepNum;i++){
				gc.drawImage(normalImageList.get(i-1), (i-1)*rect.width, 0);
			}
		}else if(nowStep>stepNum){
			for(int i=stepNum;i<nowStep;i++){
				gc.drawImage(grayImageList.get(i), i*rect.width, 0);
			}
		}
		gc.dispose();
	}
}
