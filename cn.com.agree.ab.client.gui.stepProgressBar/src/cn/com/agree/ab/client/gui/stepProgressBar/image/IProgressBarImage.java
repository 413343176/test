package cn.com.agree.ab.client.gui.stepProgressBar.image;

public interface IProgressBarImage {
	/**
	 * 上一步
	 */
	public String nextStep(int nowStep);
	/**
	 * 下一步
	 */
	public String backStep(int nowStep);
	/**
	 * 设置第几步
	 */
	public void setStep(int stepNum);
}
