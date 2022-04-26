package COMP424;
import java.util.*;

/* DANA, Luna (260857641)

COMP424 - Assignment 1
Question 3)

*/
// This class is to generate all the permutations of n cities
public class Permute {
	
	ArrayList<Double> cost = new ArrayList<Double>();
	ArrayList<ArrayList<City>> permutations = new ArrayList<ArrayList<City>>();
	
	public void addcost(ArrayList<City> cities, int n) {
		Double cost = 0.0;
        for (int i = 0; i < n; i++) {
        	if (i == n-1) {
        		Double temploop = cities.get(i).distanceToCity(cities.get(0));
        		cost = cost + temploop;
        		break;
        	}
        	Double temp = cities.get(i).distanceToCity(cities.get(i+1));
        	cost = cost + temp;
        }
        this.cost.add(cost);
        this.permutations.add(cities);
    }
        	
    // Generating permutation using Heap Algorithm to have the 7! possible
	// city combinations
    public void heapPermutation(ArrayList<City> cities, int size, int n) {
    	
    	if (size == 1) { addcost(cities, n); }
    	
        for (int i = 0; i < size; i++) {
            heapPermutation(cities, size - 1, n);
            if (size % 2 == 1) {
                City temp = cities.get(0);
                City temp2 = cities.get(size-1);
                cities.set(0,temp2);
                cities.set(size-1,temp);
            }
            else {
            	City temp = cities.get(i);
            	City temp2 = cities.get(size-1);
            	cities.set(i,temp2);
                cities.set(size-1,temp);
            }
        }
    }
    
    public double getMin() {
    	double min = this.cost.stream().min(Comparator.naturalOrder()).get();
    	return min;
    }  
    
    public static void main(String args[]){     	
    }
}
 