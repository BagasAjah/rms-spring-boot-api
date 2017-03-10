import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicates {

	public static int[] removeDuplicates(int[] input){
		int index=0;
		Set<Integer> setInput = new HashSet<Integer>();
		for (int i : input) {
			if (setInput.contains(i)) {
				continue;
			}
			setInput.add(i);
		}
		int[] output = new int[setInput.size()];
		for (int num : setInput) {
			output[index] = num;
			index++;
		}
		return output;
	}
	
	public static void main(String a[]){
		int[] input1 = {2,3,6,6,8,9,10,10,10,12,12};
		int[] output = removeDuplicates(input1);
		for(int i:output){
			System.out.print(i+" ");
		}
	}
}

