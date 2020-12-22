package me.henry.lib_base.others.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 输入法工具类
 */
public class InputMethodUtil {

	public static void hideSoftInput(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputManager.isActive())
					inputManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
		}, 200);

	}

	public static void showSoftInput(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(v, 0);
			}
		}, 200);

	}


	public static void hideSoftInput(Activity act) {
		InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(act.getWindow().getDecorView().getWindowToken(),0);


	}

	public static void showSoftInput(Activity act) {
		InputMethodManager inputManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(act.getWindow().getDecorView(), InputMethodManager.RESULT_HIDDEN);

	}

	/**
	 * 强制弹出软键盘
	 * @param txtSearchKey
	 */
	public static void ShowKeyboard(final EditText txtSearchKey) {
		txtSearchKey.postDelayed(()-> {
			InputMethodManager m = (InputMethodManager)
					txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			txtSearchKey.requestFocus();
			m.showSoftInput(txtSearchKey,0);
		}, 150);
	}

	/**
	 * 隐藏键盘
	 * @param txtSearchKey
	 */
	public static void HideKeyboard(final EditText txtSearchKey){
		InputMethodManager m = (InputMethodManager)
				txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
		txtSearchKey.clearFocus();
	}
}
