package cn.com.sky.hyperbola;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import cn.com.sky.hyperbola.model.ContactsEntry;

public class ChatAction  extends Action implements ISelectionListener, IWorkbenchAction {

	private IWorkbenchWindow window;
	private IStructuredSelection selection;
	public static final String ID = "cn.com.sky.hyperbola.chat";
	
	public ChatAction(IWorkbenchWindow window){
		this.window = window;
		setId(ID);
		setText("&Chat");
		setToolTipText("click to chat");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.CHAT));
		window.getSelectionService().addSelectionListener(this);
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if(incoming instanceof IStructuredSelection){
			selection = (IStructuredSelection)incoming;
			setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof ContactsEntry);
		}else{
			setEnabled(false);
		}
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void run() {
		Object item = selection.getFirstElement();
		IWorkbenchPage page = window.getActivePage();
		ChatEditorInput input = new ChatEditorInput(((ContactsEntry)item).getName());
		try {
			page.openEditor(input, ChatEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
	}


}
