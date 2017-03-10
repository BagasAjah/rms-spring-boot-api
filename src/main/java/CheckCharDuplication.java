import java.util.HashSet;
import java.util.Set;

public class CheckCharDuplication {
	
	private static String[] data = new String[]{
		"abcdebf", "bxgblvcfa", "zsaxcnmlb"};

	public static void main(String[] args) {
		char[] data1 = data[0].toCharArray();
		char[] data2 = data[1].toCharArray();
		char[] data3 = data[2].toCharArray();
		Set<String> result1 = new HashSet<String>();
		Set<String> finalResult = new HashSet<String>();
		
		for (int i = 0; i< data1.length; i++) {
			for(int j = 0; j< data2.length; j++) {

				if(data1[i] == data2[j]) {
					result1.add(String.valueOf(data1[i]));
				}
			}
		}
		for (String resStr : result1) {
			for(int j = 0; j< data3.length; j++) {
				if(resStr.equals(String.valueOf(data3[j]))) {
					finalResult.add(resStr);
				}
				
			}
		}
		System.out.println(finalResult);
	}
}

