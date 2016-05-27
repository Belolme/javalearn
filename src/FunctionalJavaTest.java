import static fj.data.List.list;

import fj.F;
import fj.Show;
import fj.data.Array;
import fj.data.List;
import fj.function.Characters;
import fj.function.Integers;

import static fj.Show.intShow;
import static fj.Show.listShow;

public class FunctionalJavaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final List<Integer> a = List.list(1, 2, 3).map(i -> i + 42);
		listShow(intShow).println(a); // [43,44,45]

		// combined into a single line
		listShow(intShow).println(list(1, 2, 3).map(i -> i + 42)); // [43,44,45]
		
		final Array<String> stringArr = Array.array("Hello", "There", "wet", "DAY");
		System.out.println(stringArr.exists(s -> List.fromString(s).forall(Characters.isLowerCase)));
		Show.arrayShow(Show.stringShow).println(stringArr);
		
		final Array<Integer> intArr = Array.array(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42);
		final Array<Integer> intArr2 = intArr.filter(Integers.even);
		Show.arrayShow(Show.intShow).println(intArr2);
		F<Integer, F<Integer, Integer>> add2 = i -> (j->i+j);
		System.out.println(intArr.foldLeft(add2,	0));
		final Array<Integer> intArr3 = intArr.bind(i -> Array.array(i,8));
		Show.arrayShow(Show.intShow).println(intArr3);
	}

}
