package cn.com.sky.hyperbola;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ChatEditorInput implements IEditorInput {
	
	private String participant;
	
	ChatEditorInput(String participant){
		super();
		Assert.isNotNull(participant);
		this.participant = participant;
	}
	
	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return participant;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return participant;
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean equals(Object obj){
		if(super.equals(obj)){
			return true;
		}
		if(!(obj instanceof ChatEditorInput)){
			return false;
		}
		return participant.equals(((ChatEditorInput)obj).participant);
	}
	@Override
	public int hashCode() {
		return participant.hashCode();
	}
}
