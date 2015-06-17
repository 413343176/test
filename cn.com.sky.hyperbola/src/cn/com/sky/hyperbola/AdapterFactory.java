package cn.com.sky.hyperbola;

import java.net.URL;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import cn.com.sky.hyperbola.model.Contact;
import cn.com.sky.hyperbola.model.ContactsEntry;
import cn.com.sky.hyperbola.model.ContactsGroup;
import cn.com.sky.hyperbola.model.Presence;

public class AdapterFactory implements IAdapterFactory {

	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((ContactsGroup) o).getParent();
		}

		@Override
		public Object[] getChildren(Object o) {
			return ((ContactsGroup) o).getEntries();
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.GROUPS);
		}

		@Override
		public String getLabel(Object o) {
			ContactsGroup group = ((ContactsGroup) o);
			int available = 0;
			Contact[] entries = group.getEntries();
			for (Contact entry : entries) {
				if (entry instanceof ContactsEntry
						&& ((ContactsEntry) entry).getPresence() != Presence.INVISIBLE) {
					available++;
				}
			}
			return group.getName() + "(" + available + "/" + entries.length
					+ ")";
		}
	};

	private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((ContactsEntry) o).getParent();
		}

		@Override
		public Object[] getChildren(Object o) {
			return new Object[0];
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			Presence presence = ((ContactsEntry) object).getPresence();
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, persenceToKey(presence));

		}

		@Override
		public String getLabel(Object o) {
			ContactsEntry entry = (ContactsEntry) o;
			return entry.getName() + "-" + entry.getServer();
		}
	};

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof ContactsEntry) {
			return entryAdapter;
		}
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof ContactsGroup) {
			return groupAdapter;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		// TODO Auto-generated method stub
		return new Class[] { IWorkbenchAdapter.class };
	}

	public String persenceToKey(Presence presence) {
		if (presence == Presence.AWAY) {
			return IImageKeys.AWAY;
		} else if (presence == Presence.DO_NOT_DISTURB) {
			return IImageKeys.DO_NOT_DISTURB;
		} else if (presence == Presence.INVISIBLE) {
			return IImageKeys.OFFLINE;
		} else if (presence == Presence.ONLINE) {
			return IImageKeys.ONLINE;
		}
		return null;
	}

}
