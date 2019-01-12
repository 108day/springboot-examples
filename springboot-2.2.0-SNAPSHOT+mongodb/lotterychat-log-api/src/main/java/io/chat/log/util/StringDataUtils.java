package io.chat.log.util;

/**
* @Description: TODO(字符串处理工具)
* @author sheji zhaosheji.kevin@gmail.com
* @date 2019年1月3日
 */
public final class StringDataUtils {

	/**
	 * @Description: TODO(首字母小写)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月3日
	 */
	public static String firstWordLower(String str) {
		if(str==null) {
			throw new IllegalArgumentException("The parameter str must not be null !");
		}
		if("".equals(str.trim())) {
			throw new IllegalArgumentException("The parameter str must not be \"\" !");
		}
		if(str.length()==1) {
			return str.toLowerCase();
		}
		String firstWord = str.substring(0, 1).toLowerCase();
		String otherWord = str.substring(1, str.length());
		return firstWord+otherWord;
	}
	
	
}
