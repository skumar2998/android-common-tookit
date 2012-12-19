package com.lurencun.android.view;

import android.app.Activity;
import android.os.Handler;

import com.lurencun.android.common.Params;
import com.lurencun.android.system.ActivityUtil;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-12-19
 * @desc   : TODO
 */
public abstract class DelaySwitchActivity extends Activity {

	private final Runnable switchCallback;
	private final Handler switchHandler;
	private int splashDelay = 3 * 1000;
	private Class<? extends Activity> nextActivity;
	private Params params;
	
	{
		switchCallback = new Runnable(){
			@Override
			public void run() {
				switchToNextView();
			}
		};
		switchHandler = new Handler();
		switchHandler.postDelayed(switchCallback, splashDelay);
	}
	
	protected void setSplashDelay(int switchHandler){
		splashDelay = switchHandler;
	}
	
	protected void setNextActivity(Class<? extends Activity> target){
		nextActivity = target;
	}
	
	protected void setParams(Params params){
		this.params = params;
	}
	
	protected void cancelSwitchAction(){
		switchHandler.removeCallbacks(switchCallback);
	}
	
	protected void switchToNextView(){
		ActivityUtil.switchTo(this, nextActivity, params);
		cancelSwitchAction();
	}
}
