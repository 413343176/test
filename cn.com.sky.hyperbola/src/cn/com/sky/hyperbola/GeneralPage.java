package cn.com.sky.hyperbola;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class GeneralPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	private BooleanFieldEditor bfe;
	public GeneralPage() {
		// TODO Auto-generated constructor stub
		setPreferenceStore(new ScopedPreferenceStore(new ConfigurationScope(), Application.PLUGIN_ID));
	}

	public GeneralPage(int style) {
		super(style);
		// TODO Auto-generated constructor stub
	}

	public GeneralPage(String title, int style) {
		super(title, style);
		// TODO Auto-generated constructor stub
	}

	public GeneralPage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	@Override
	protected void createFieldEditors() {
		bfe = new BooleanFieldEditor("autoLogin", "ÊÇ·ñ×Ô¶¯µÇÂ¼", getFieldEditorParent());
		addField(bfe);
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean performOk() {
		return super.performOk();
	}

}
