package cn.com.sky.hyperbola;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
//Õ∏ ”Õº
public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
//		layout.addView(ContactsView.ID, layout.LEFT, 1.0f, layout.getEditorArea());
		layout.addStandaloneView(ContactsView.ID, false, layout.LEFT, 0.5f, layout.getEditorArea());
	}
}
