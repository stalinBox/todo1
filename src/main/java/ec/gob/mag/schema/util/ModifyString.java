package ec.gob.mag.schema.util;

public class ModifyString {
	public static String cleanBlanks(String str) {
		str = str.replaceAll(" +", " ");
		str = str.trim();
		return str;
	}
}
