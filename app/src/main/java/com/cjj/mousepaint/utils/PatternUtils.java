package com.cjj.mousepaint.utils;

public class PatternUtils {
	
	public static final String CHECK_IS_PHONE = "^(13|15|18)\\d{9}$";
	public static final String CHECK_PHONE_IS_CHINA_MOBILE = "^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8])[0-9]{8}$";
	public static final String CHECK_PASSWORD_ABC_OR_123 = "^[a-zA-Z]{0,1}+[a-zA-Z0-9]{5,13}$";
	public static final String INPUT_ABC_OR_NUMBER_OR_HANZI = "^[\u4E00-\u9FA5A-Za-z]+[\u4E00-\u9FA5A-Za-z0-9_$]+$";
}
