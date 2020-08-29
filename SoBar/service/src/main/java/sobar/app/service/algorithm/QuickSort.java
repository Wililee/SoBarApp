package sobar.app.service.algorithm;

import java.util.Collections;
import java.util.List;

import sobar.app.service.model.Business;

public class QuickSort {

	public static List<Business> sortByID(List<Business> list, int begin, int end) {
		List<Business> sorted = list;
		
		if (begin < end) {
	        int partitionIndex = partitionByID(list, begin, end);
	 
	        sortByID(list, begin, partitionIndex-1);
	        sortByID(list, partitionIndex+1, end);
	    }
		
		return sorted;
	}
	
	public static List<Business> sortByStars(List<Business> list, int begin, int end) {
		List<Business> sorted = list;
		
		if (begin < end) {
	        int partitionIndex = partitionByStars(list, begin, end);
	 
	        sortByStars(list, begin, partitionIndex-1);
	        sortByStars(list, partitionIndex+1, end);
	    }
		
		return sorted;
	}
	
	private static int partitionByID(List<Business> list, int begin, int end) {
	    long pivot = list.get(end).getId();
	    int i = (begin-1);
	 
	    for (int j = begin; j < end; j++) {
	        if (list.get(j).getId() <= pivot) {
	            i++;
	            
	            Collections.swap(list, i, j);
	        }
	    }
	 
	    Collections.swap(list, i+1, end);
	 
	    return i+1;
	}
	
	private static int partitionByStars(List<Business> list, int begin, int end) {
	    double pivot = list.get(end).getStars();
	    int i = (begin-1);
	 
	    for (int j = begin; j < end; j++) {
	        if (list.get(j).getStars() <= pivot) {
	            i++;
	            
	            Collections.swap(list, i, j);
	        }
	    }
	 
	    Collections.swap(list, i+1, end);
	 
	    return i+1;
	}
}
