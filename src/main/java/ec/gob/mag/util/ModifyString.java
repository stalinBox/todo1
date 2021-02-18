package ec.gob.mag.util;

public class ModifyString {
	public static String cleanBlanks(String str) {
		str= str.replaceAll(" +", " ");
		str= str.trim();
		return str;
	}
}
