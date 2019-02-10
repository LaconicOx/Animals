package custom_utilities;

import java.util.HashSet;

public class Test {

	public static void main(String[] args) {
		HashSet<Integer> ts = new HashSet<>();
		ts.add(1);
		ts.add(2);
		ts.add(3);
		
		HashSet<Dum> td = new HashSet<>();
		td.add(new Dum(1));
		td.add(new Dum(2));
		td.add(new Dum(3));
		
		FiniteHeapSort<Dum> dumSort = new FiniteHeapSort<>(td);
		
		FiniteHeapSort<Integer> sort = new FiniteHeapSort<>(ts);
		
		Object[] to = sort.toArray();
		/*
		StringBuilder sb = new StringBuilder();
		while(!sort.isEmpty()) {
			sb.append(sort.removeMin() + ", ");
		}
		System.out.println(sb.toString());
		*/
		
		for(Object ob : to ) {
			System.out.println(ob.toString());
		}
	}

}
