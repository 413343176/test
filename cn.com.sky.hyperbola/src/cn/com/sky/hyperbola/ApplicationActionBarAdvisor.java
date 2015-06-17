package cn.com.sky.hyperbola;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
//快速视图
import org.eclipse.ui.internal.preferences.PreferencesAdapter;
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction helpAction;
    private AddContactsEntryAction addContact;
    private ChatAction chatAction;
    private IWorkbenchAction preAction;

    protected void makeActions(IWorkbenchWindow window) {
    	exitAction = ActionFactory.QUIT.create(window);
    	register(exitAction);
    	aboutAction = ActionFactory.ABOUT.create(window);
    	register(aboutAction);
    	helpAction = ActionFactory.HELP_CONTENTS.create(window);
    	register(helpAction);
    	addContact = new AddContactsEntryAction(window);
    	register(addContact);
    	chatAction = new ChatAction(window);
    	register(chatAction);
    	preAction = ActionFactory.PREFERENCES.create(window);
    }
    //菜单栏
    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager hyperbolaMenu = new MenuManager("&Hyperbola","hyperbola");
    	hyperbolaMenu.add(exitAction);
    	hyperbolaMenu.add(addContact);
    	hyperbolaMenu.add(chatAction);
    	hyperbolaMenu.add(preAction);
    	MenuManager aboutMenu = new MenuManager("&About","about");
    	aboutMenu.add(aboutAction);
    	menuBar.add(hyperbolaMenu);
    	menuBar.add(aboutMenu);
    	hyperbolaMenu.add(new GroupMarker("other-action"));
    	hyperbolaMenu.appendToGroup("other-action", aboutAction);
    	MenuManager helpMenu = new MenuManager("&Help","help");
    	helpMenu.add(helpAction);
    	hyperbolaMenu.add(helpMenu);
    	menuBar.add(helpMenu);
    }
    //工具栏
    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {
    	IToolBarManager toolBar = new ToolBarManager(coolBar.getStyle());
    	coolBar.add(toolBar);
    	toolBar.add(addContact);
    	toolBar.add(chatAction);
//    	toolBar.add(new Separator());
//    	toolBar.add(aboutAction);
    }
    //状态栏
   @Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		// TODO Auto-generated method stub
		super.fillStatusLine(statusLine);
	}
   public void fillTrayMenu(IMenuManager trayItem){
	   trayItem.add(aboutAction);
	   trayItem.add(exitAction);
   }
   
    
    
}
