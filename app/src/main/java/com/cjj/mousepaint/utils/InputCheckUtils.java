package com.cjj.mousepaint.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *create by cjj
 *
 */
public class InputCheckUtils {


	public static boolean isEmpty(String input)
	{
		if(input==null || input.length()<=0)
			return true;
		else
			return false;
	}


	public static boolean checkInputIsPhoneNumber(String input)
	{
		Pattern pattern = Pattern.compile(PatternUtils.CHECK_IS_PHONE);
		Matcher matcher = pattern.matcher(input);

		if(matcher.matches())
			return true;
		else
			return false;
	}


	public static boolean checkInputIsConFormNickName(String input)
	{
		Pattern pattern = Pattern.compile(PatternUtils.INPUT_ABC_OR_NUMBER_OR_HANZI);
		Matcher matcher = pattern.matcher(input);

		if(matcher.matches())
			return true;
		else
			return false;
	}


	public static boolean compareIsEqual(String input1,String input2)
	{
		if(input1==null || input2 == null)
		{
			return false;
		}
		else
		{
			if(input1.equals(input2))
			{
				return true;
			}
		}

		return false;
	}


	public static boolean checkInputRangeIsConform(String input,int minRange,int maxRange)
	{
		if(input == null)
		{
			return false;
		}

		int length = input.length();

		if(length>=minRange && length <= maxRange)
		{
			return true;
		}

		return false;
	}


	public static boolean checkInputRangeIsConform(String input,int range)
	{
	     if(input == null)
	     {
	    	 return false;
	     }

	     if(input.length() == range)
	     {
	    	 return true;
	     }

	     return false;
	}


	public static boolean checkPhoneIsChinaMobile(String input)
	{
		if(input == null)
		{
			return false;
		}
		
		if(input.matches(PatternUtils.CHECK_PHONE_IS_CHINA_MOBILE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
	public static boolean isValidEmail(String mail) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return mail.matches(regex);
	}

}
