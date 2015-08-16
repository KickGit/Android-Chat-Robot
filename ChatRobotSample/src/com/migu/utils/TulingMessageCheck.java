package com.migu.utils;

import android.util.Log;

import com.migu.main.MainApplication;
import com.migu.main.R;


public class TulingMessageCheck {

	public static int Check(String code) {
		// TODO Auto-generated method stub
		Log.d(ConstantPools.TAG_STRING, "response Code: "+code);
		int responseValue = Integer.valueOf(code);
		if (responseValue == ConstantPools.TULING_TEXTTYPE) {
			
		}else if (responseValue == ConstantPools.TULING_URLTYPE) {
		
		}else if (responseValue == ConstantPools.TULING_NEWSTYPE) {
			
		}else if (responseValue == ConstantPools.TULING_TRAINSTYPE) {
			
		}else if (responseValue == ConstantPools.TULING_PLAINTYPE) {
			
		}else if (responseValue == ConstantPools.TULING_VIDEO_BOOK_TYPE) {
			
		}
//		if (responseValue == 40007) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40007));
//		}else if (responseValue == 40006) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40006));
//		}else if (responseValue == 40005) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40005));
//		}else if (responseValue == 40004) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40004));
//		}else if (responseValue == 40003) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40003));
//		}else if (responseValue == 40002) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40002));
//		}else if (responseValue == 40001) {
//			ToastUtil_two.showShortToast(MainApplication.getmContext().getResources().getString(R.string.tuling40001));
//		}else if (responseValue == 100000) {
//			return 100;
//		}
		return responseValue;
	}



}
