package sobar.app.service.algorithm;

import java.util.List;

import sobar.app.service.model.Business;

public class BinarySearch {

	public static int search(List<Business> sortedList, long key, int low, int high) {
		int middle = (low + high) / 2;

		if (high < low) {
			return -1;
		}

		if (key == sortedList.get(middle).getId()) {
			return middle;
		} else if (key < sortedList.get(middle).getId()) {
			return search(sortedList, key, low, middle - 1);
		} else {
			return search(sortedList, key, middle + 1, high);
		}
	}

}
