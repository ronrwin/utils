package com.audionote.util;

/**
 * 
 * @description 判断手机号码
 * @author Win.FR
 */
public class MobileFormat
{
	/**
	 * 中国移动拥有号码段为:139,138,137,136,135,134,159,158,157(3G),151,150,188(3G),187(3G
	 * );13个号段 中国联通拥有号码段为:130,131,132,156(3G),186(3G),185(3G);6个号段
	 * 中国电信拥有号码段为:133,153,189(3G),180(3G);4个号码段
	 */
	private static String regMobileStr = "^1(([3][456789])|([5][01789])|([8][78]))[0-9]{8}$";
	private static String regMobile3GStr = "^((157)|(18[78]))[0-9]{8}$";
	private static String regUnicomStr = "^1(([3][012])|([5][6])|([8][56]))[0-9]{8}$";
	private static String regUnicom3GStr = "^((156)|(18[56]))[0-9]{8}$";
	private static String regTelecomStr = "^1(([3][3])|([5][3])|([8][09]))[0-9]{8}$";
	private static String regTelocom3GStr = "^(18[09])[0-9]{8}$";
	private static String regPhoneString = "^(?:13\\d|15\\d)\\d{5}(\\d{3}|\\*{3})$";

	public String mobile = "";
	public int facilitatorType = 0;
	public boolean isLawful = false;
	public boolean is3G = false;

	public MobileFormat(String mobile)
	{
		this.setMobile(mobile);
	}

	public void setMobile(String mobile)
	{
		if (mobile == null)
		{
			return;
		}
		
		/** 第一步判断中国移动 */
		if (mobile.matches(MobileFormat.regMobileStr))
		{
			this.mobile = mobile;
			facilitatorType = 0;
			isLawful = true;
			if (mobile.matches(MobileFormat.regMobile3GStr))
			{
				is3G = true;
			}
		}
		/** 第二步判断中国联通 */
		else if (mobile.matches(MobileFormat.regUnicomStr))
		{
			this.mobile = mobile;
			facilitatorType = 1;
			isLawful = true;
			if (mobile.matches(MobileFormat.regUnicom3GStr))
			{
				is3G = true;
			}
		}
		/** 第三步判断中国电脑 */
		else if (mobile.matches(MobileFormat.regTelecomStr))
		{
			this.mobile = mobile;
			facilitatorType = 2;
			isLawful = true;
			if (mobile.matches(MobileFormat.regTelocom3GStr))
			{
				is3G = true;
			}
		}
		
		/** 第四步判断座机 */
		if (mobile.matches(MobileFormat.regPhoneString))
		{
			this.mobile = mobile;
			facilitatorType = 0;
			isLawful = true;
			if (mobile.matches(MobileFormat.regMobile3GStr))
			{
				is3G = true;
			}
		}
	}
}
