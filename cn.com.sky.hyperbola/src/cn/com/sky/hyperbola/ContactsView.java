package cn.com.sky.hyperbola;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import cn.com.sky.hyperbola.model.Contact;
import cn.com.sky.hyperbola.model.ContactsEntry;
import cn.com.sky.hyperbola.model.ContactsGroup;
import cn.com.sky.hyperbola.model.IContactsListener;
import cn.com.sky.hyperbola.model.Session;
//йсм╪
public class ContactsView extends ViewPart {
	public static final String ID = "cn.com.sky.hyperbola.contacts";
	private IAdapterFactory adapterFactory = new AdapterFactory();
	private Session session;
	private TreeViewer treeViewer;
	public ContactsView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		initializeSession();
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Contact.class);
		getSite().setSelectionProvider(treeViewer);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(session.getRoot());
		session.getRoot().addContactsListener(new IContactsListener() {
			@Override
			public void contactsChanged(ContactsGroup contacts, ContactsEntry entry) {
				treeViewer.refresh();
			}
		});
		PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getTree(), "cn.com.sky.hyperbola.ContactsView");
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
	private void initializeSession(){
		session = new Session();
		ContactsGroup root = session.getRoot();
		ContactsGroup friendsGroup = new ContactsGroup(root, "Friends");
		root.addEntry(friendsGroup);
		friendsGroup.addEntry(new ContactsEntry(friendsGroup, "aa", "AA", "localhost"));
		friendsGroup.addEntry(new ContactsEntry(friendsGroup, "bb", "BB", "localhost"));
		friendsGroup.addEntry(new ContactsEntry(friendsGroup, "cc", "CC", "localhost"));
		ContactsGroup otherGroup = new ContactsGroup(root, "Other");
		root.addEntry(otherGroup);
		otherGroup.addEntry(new ContactsEntry(friendsGroup, "zz", "ZZ", "localhost"));
	}
	
	@Override
	public void dispose() {
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		super.dispose();
	}

}
