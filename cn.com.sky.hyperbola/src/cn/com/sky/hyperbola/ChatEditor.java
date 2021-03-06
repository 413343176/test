package cn.com.sky.hyperbola;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ChatEditor extends EditorPart {
	
	public static final String ID = "cn.com.sky.hyperbola.chateditor";
	private Text transcript;
	private Text entry;
	
	public ChatEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		setSite(site);
		setInput(input);
		setPartName(getUser());
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		top.setLayout(layout);
		transcript = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		transcript.setLayoutData(new GridData(GridData.FILL ,GridData.FILL, true, true));
		transcript.setEditable(false);
		transcript.setBackground(transcript.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		transcript.setForeground(transcript.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		entry = new Text(top, SWT.WRAP | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		gridData.heightHint = entry.getLineHeight() * 2;
		entry.setLayoutData(gridData);
		entry.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == SWT.CR){
					sendMessage();
					e.doit = false;
				}
			}
		});
	}

	@Override
	public void setFocus() {
		if(entry != null && !entry.isDisposed()){
			entry.setFocus();
		}
	}
	
	public String getUser(){
		return ((ChatEditorInput)getEditorInput()).getName();
	}
	public String renderMessage(String from, String body){
		if(from == null){
			return body;
		}
		int j  = from.indexOf("@");
		if(j > 0){
			from = from.substring(0, j);
		}
		
		return "<" + from + ">" + body;
	}
	
	private void scrollToEnd(){
		int n = transcript.getCharCount();
		transcript.setSelection(n, n);
		transcript.showSelection();
	}
	
	private void sendMessage(){
		String body = entry.getText();
		if(body.length() == 0){
			return ;
		}
		transcript.append(renderMessage(getUser(), body));
		transcript.append("\n");
		scrollToEnd();
		entry.setText("");
	}
	

}
