package cn.com.sky.hyperbola;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import cn.com.sky.hyperbola.model.ContactsEntry;
import cn.com.sky.hyperbola.model.ContactsGroup;


public class AddContactsEntryAction extends Action implements
		ISelectionListener, IWorkbenchAction {

	public static final String ID = "cn.com.sky.hyperbola.addContact";
	public final IWorkbenchWindow window;
	private IStructuredSelection selection; 
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if(incoming instanceof IStructuredSelection){
			selection = (IStructuredSelection)incoming;
			setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof ContactsGroup);
		}else{
			setEnabled(false);
		}
	}
	AddContactsEntryAction(IWorkbenchWindow window){
		this.window = window;
		setId(ID);
		setText("&Add contact");
		setActionDefinitionId(ID);
		setToolTipText("选中一个组");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ADD_CONTACT));
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}
	@Override
	public void run() {
		AddContactDialog dialog = new AddContactDialog(window.getShell());
		int code = dialog.open();
		if(code == Window.OK){
			Object item = selection.getFirstElement();
			ContactsGroup group = (ContactsGroup)item;
			ContactsEntry entry = new ContactsEntry(group, dialog.getUserId(), dialog.getNickname(), dialog.getServer());
			group.addEntry(entry);
		}
	}

}
