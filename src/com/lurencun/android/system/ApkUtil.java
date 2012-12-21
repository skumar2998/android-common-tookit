package com.lurencun.android.system;

import java.io.File;
import java.io.IOException;

import com.lurencun.android.resource.FileUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-8-11
 * @desc   : APK相关功能帮助器类
 */
public class ApkUtil {

	/**
	 * 判断APK包是否已经安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean exist(Context context, String packageName) {
		if (null == packageName || "".equals(packageName)) {
			throw new IllegalArgumentException("Package name cannot be null or empty !");
		}
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
			return null != info;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 安装指定APK文件
	 * @param activity
	 * @param apkFile
	 */
	public static void install(Activity activity, File apkFile) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}
	
	/**
	 * 启动一个指定包名的应用
	 * @param activity
	 * @param packageName
	 *
	 */
	public static void launch(Activity activity,String packageName){
		Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
		if( null != intent ){
			activity.startActivity(intent);
		}
	}
	
	public static void cleanAppCache(Activity activity) throws IOException{
		String path = activity.getDir(".", Context.MODE_PRIVATE).getAbsolutePath();
		String dir = path.substring(0, path.lastIndexOf("/") + 1);
		FileUtil.deleteDirectory(dir);
	}
}
