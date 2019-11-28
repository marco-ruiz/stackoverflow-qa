package so.a58684441;
/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marco Ruiz
 */
public class ArrayRearrangement {
	
	public static void main(String[] args) {
//		int[] result = rearrange(1, 2, 3, 4);
		int[] result = rearrange(1, 1, 1, 4);
		System.out.println(Arrays.stream(result).boxed().collect(Collectors.toList()));
	}
	
	private static int[] rearrange(int... numbers) {
		List<List<Integer>> numsByOddity = createOddityLists(numbers);
		padShorterList(numsByOddity);
		return joinLists(numsByOddity).stream().mapToInt(i->i).toArray();
	}

	private static List<List<Integer>> createOddityLists(int... numbers) {
		List<Integer> numsList = Arrays.stream(numbers).boxed().collect(Collectors.toList());
		
		List<List<Integer>> numsByOddity = new ArrayList<List<Integer>>();
		numsByOddity.add(new ArrayList<>()); // List of odd numbers
		numsByOddity.add(new ArrayList<>()); // List of even numbers
		
		numsList.forEach(num -> numsByOddity.get(num % 2).add(num));
		return numsByOddity;
	}

	private static void padShorterList(List<List<Integer>> numsByOddity) {
		int sizeDiff = numsByOddity.get(0).size() - numsByOddity.get(1).size();
		int listIndexToBePadded = sizeDiff < 0 ? 0 : 1;
		List<Integer> padding = Collections.nCopies(Math.abs(sizeDiff), 0);
		numsByOddity.get(listIndexToBePadded).addAll(padding);
	}

	private static List<Integer> joinLists(List<List<Integer>> numsByOddity) {
		List<Integer> resultList = new ArrayList<>(numsByOddity.get(1));
		for (int idx = 0; idx < numsByOddity.get(0).size(); idx++)
			resultList.add(idx * 2, numsByOddity.get(0).get(idx));
		return resultList;
	}
}
