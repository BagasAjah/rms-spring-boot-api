
public class ReverseInteger {

	public static void main(String[] args) {
		System.out.println(reverse(321));
		System.out.println(reverse(123456789));
		System.out.println(reverse(-98765));
	}

	private static int reverse(int x) {
		String regex = "\\d+";
		StringBuilder numOnly = new StringBuilder();
		StringBuilder notNum = new StringBuilder();
		String revStr = String.valueOf(x);
		int strLengt = revStr.length();
		for (int i=0; i<strLengt; i++) {
			if(String.valueOf(revStr.charAt(i)).matches(regex)) {
				numOnly.append(String.valueOf(revStr.charAt(i)));
			} else {
				notNum.append(String.valueOf(revStr.charAt(i)));
			}
		}
		
		String result = new StringBuilder(numOnly).reverse().toString();
	    return Integer.parseInt(notNum + result);
	}

}
