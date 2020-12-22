package me.henry.lib_base.others.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;


/**
 * 资源工具类
 * @author chenf
 * @since 1.0
 */
public class ResourceUtil {
	
	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}


	public static float getDimension(Context context, int id) {
		return context.getResources().getDimension(id);
	}

	public static int getColor(Context context, int id) {
		return context.getResources().getColor(id);
	}


	
	public static Drawable getDrawable(Context context, int id) {
		return context.getResources().getDrawable(id);
	}
	
	public static ColorStateList getColorStateList(Context context, int id) {
		return context.getResources().getColorStateList(id);
	}

	public static Drawable getDrawable(Context context, String resName) {
		return getDrawable(context, getResourcesIdByName(context, "drawable", resName));
	}
	
	public static int getDrawableId(Context context, String resName) {
		return getResourcesIdByName(context, "drawable", resName);
	}

	public static int getRawId(Context context, String resName) {
		return getResourcesIdByName(context, "raw", resName);
	}

	/**
	 * 读取铃音列表时
	 */
	public static int getRawIdForRingtone(Context context, String resName) {
		if("system".equals(resName) || "secret_message".equals(resName)) return -1;
		return getResourcesIdByName(context, "raw", resName);
	}
	
	private static int getResourcesIdByName(Context context, String resourceType, String resourcesName) {
		Resources resources = context.getResources();
		int id = resources.getIdentifier(resourcesName, resourceType, context.getPackageName());
		if(id == 0) {

		}
		return id;
	}

	public static String[] getStringArray(Context context, int arrayId) {
		return context.getResources().getStringArray(arrayId);
	}

	public static int[] getIntArray(Context context, int arrayId) {
		return context.getResources().getIntArray(arrayId);
	}
	
}
