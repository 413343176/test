package cn.com.sky.hyperbola;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
//工作台窗口
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	private Image statusImage;
	private Image trayItemImage;
	private TrayItem trayItem;
	private ApplicationActionBarAdvisor actionBarAdvisor ;
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    	actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
        return actionBarAdvisor;
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(250, 350));
        configurer.setShowMenuBar(true);
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
//        configurer.setTitle("Hello Hyperbola");
    }
    @Override
    public void postWindowOpen() {
    	initStatusLine();
    	IWorkbenchWindow window = getWindowConfigurer().getWindow();
    	initTrayItem(window);
    	if(trayItem != null){
    		hookPopMenu(window);
    		hookMinimize(window);
    	}
    }
    private void initStatusLine(){
    	statusImage = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ONLINE).createImage();
    	IStatusLineManager statusLineManeger = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
    	statusLineManeger.setMessage(statusImage, "online");
    }
    
    private TrayItem initTrayItem(IWorkbenchWindow window){
    	final Tray tray = window.getShell().getDisplay().getSystemTray();
    	trayItem = new TrayItem(tray, SWT.NONE);
    	trayItemImage = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ONLINE).createImage();
    	trayItem.setImage(trayItemImage);
    	trayItem.setToolTipText("Hyperbola");
    	return trayItem;
    }
    
    private void hookPopMenu(final IWorkbenchWindow window){
    	trayItem.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				MenuManager trayMenu = new MenuManager();
				Menu menu = trayMenu.createContextMenu(window.getShell());
				actionBarAdvisor.fillTrayMenu(trayMenu);
				menu.setVisible(true);
			}
		});
    }
    
    private void hookMinimize(final IWorkbenchWindow window){
    	window.getShell().addShellListener(new ShellAdapter() {
    		@Override
    		public void shellIconified(ShellEvent e) {
    			window.getShell().setVisible(false);
    		};
		});
    	trayItem.addListener(SWT.DefaultSelection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Shell shell = window.getShell();
				if(!shell.isVisible()){
					shell.setVisible(true);
					shell.setMinimized(false);
				}
			}
		});
    }
    
    
    @Override
    public void dispose() {
    	super.dispose();
    	if(statusImage != null){
    		statusImage.dispose();
    	}
    	if(trayItemImage != null){
    		trayItemImage.dispose();
    	}
    	if(trayItem != null){
    		trayItem.dispose();
    	}
    }
}
