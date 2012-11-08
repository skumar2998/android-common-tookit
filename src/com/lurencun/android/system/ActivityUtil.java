package com.lurencun.android.system;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * <ul>
 * <li><b>name : </b>		ActivityUtil		</li>
 * <li><b>description :</b>	TODO				</li>
 * <li><b>author : </b>		桥下一粒砂			</li>
 * <li><b>e-mail : </b>		chenyoca@gmail.com	</li>
 * <li><b>weibo : </b>		@桥下一粒砂			</li>
 * <li><b>date : </b>		2012-9-21 下午3:01:08		</li>
 * </ul>
 */
public final class ActivityUtil {

	/**
	 * </br><b>description :</b>设置Activity全屏显示。
	 * @param activity 			Activity引用
	 * @param isFull 			true为全屏，false为非全屏
	 */
	public static void toggleFullScreen(Activity activity,boolean isFull){
		hideTitleBar(activity);
		Window window = activity.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		if (isFull) {
			params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			window.setAttributes(params);
			window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			window.setAttributes(params);
			window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
	/**
	 * 设置为全屏
	 * @param activity
	 * @param isFull
	 */
	public static void setFullScreen(Activity activity){
		toggleFullScreen(activity,true);
	}
	
	/**
	 * 获取系统状态栏高度
	 * @param activity
	 * @return
	 *
	 */
	public static int getStatusBarHeight(Activity activity){
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
		    int dpHeight = Integer.parseInt(field.get(obj).toString());
		    int pxHeight = activity.getResources().getDimensionPixelSize(dpHeight);
		    return pxHeight;
		} catch (Exception e1) {
		    e1.printStackTrace();
		    return 0;
		} 
		
	}
	
	/**
	 * 隐藏Activity的系统默认标题栏
	 * @param activity
	 */
	public static void hideTitleBar(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * 强制设置Actiity的显示方向为垂直方向。
	 * @param activity
	 */
	public static void setScreenVertical(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	/**
	 * 强制设置Actiity的显示方向为横向。
	 * @param activity
	 */
	public static void setScreenHorizontal(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	/**
	 * 隐藏软件输入法
	 * @param activity
	 */
	public static void hideSoftInput(Activity activity){
	    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	/**
	 * 使UI适配输入法
	 * @param activity
	 */
	public static void adjustSoftInput(Activity activity) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	
	/**
	 * 跳转到某个Activity
	 * @param activity
	 * @param targetActivity
	 */
	public static void switchTo(Activity activity,Class<? extends Activity> targetActivity){
		switchTo(activity, new Intent(activity,targetActivity));
	}
	
	/**
	 * 根据给定的Intent进行Activity跳转
	 * @param activity
	 * @param intent
	 */
	public static void switchTo(Activity activity,Intent intent){
		activity.startActivity(intent);
		activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	/**
	 * 带参数进行Activity跳转
	 * @param activity
	 * @param targetActivity
	 * @param params
	 */
	public static void switchTo(Activity activity,Class<? extends Activity> targetActivity,
	        HashMap<String,Object> params){
			Intent intent = new Intent(activity,targetActivity);
			if( null != params ){
				for(Map.Entry<String, Object> entry : params.entrySet()){
					IntentUtil.setValueToIntent(intent, entry.getKey(), entry.getValue());
				}
			}
			switchTo(activity, intent);
	}
	
	public interface MessageFilter{
		String filter(String msg);
	}
	public static MessageFilter msgFilter;
	
	/**
	 * 显示Toast消息，并保证运行在UI线程中
	 * @param activity
	 * @param message
	 */
	public static void show(final Activity activity,final String message){
		final String msg = msgFilter != null ? msgFilter.filter(message) : message;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * 长时间显示Toast消息，并保证运行在UI线程中
	 * @param activity
	 * @param message
	 */
	public static void showL(final Activity activity,final String message){
		final String msg = msgFilter != null ? msgFilter.filter(message) : message;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	/**
	 * 显示Toast消息
	 * @param activity
	 * @param message
	 */
	public static void show(Activity activity,int msgResID){
		Toast.makeText(activity, msgResID, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	 * @param context
	 * @param dpValue
	 * @return
	 */
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
}
