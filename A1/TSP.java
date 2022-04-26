package COMP424;
import java.util.*;

/* DANA, Luna (260857641)

COMP424 - Assignment 1
Question 3)

*/

public class TSP {	
	// Method to generate n random cities
	public ArrayList<City> GenerateNcities(int n){
		ArrayList<City> cities = new ArrayList<City>() ;
		for (int i = 0; i<n ; i++) {
			City temp = new City();
			cities.add(temp);
		}
		return cities;
	}
	
	// Method to solve one TSP problem with brute force using the permutation class
	public static double TSP_BruteForce(ArrayList<City> cities, Permute obj){
	    double minimal_cost = obj.getMin();
	    return minimal_cost; 
	}
	
	// Same as brute force but selects a random permutation instead of the best tour
	public static ArrayList<City> TSP_BruteForce_RandomTour(ArrayList<City> cities, Permute obj){
	    int j = (int) (Math.random() * obj.permutations.size()-1);
	    ArrayList<City> random_permut_chosen = obj.permutations.get(j);
	    return random_permut_chosen;
	}
	
	// Method to swap the cities when doing the 2-opt local search
	public static ArrayList<City> SwapCities(ArrayList<City> cities, int i, int j){
	    ArrayList<City> newTour = new ArrayList<City>();
	    for (int o = 0; o<i; o++) {
	    	newTour.add(cities.get(o)); }
	    for (int o = j; o>=i; o--) {
	    	newTour.add(cities.get(o)); }
	    for (int o = j+1; o<cities.size(); o++) {
	    	newTour.add(cities.get(o)); }
	    return newTour;
	}
	
	// 2-opt local search for Hill Climbing implementation
	public static double TSP_HillClimbing(ArrayList<City> cities) {
		double best_cost = GetCostOfATour(cities);
		ArrayList<City> existing_route = cities;
        Boolean terminate = true;
        
		while(terminate) {
			terminate = false;
			for(int i = 0; i<existing_route.size()-1; i++) {
				for (int j = i+1; j<existing_route.size(); j++) {
					ArrayList<City> new_route = SwapCities(existing_route, i, j);
					Double new_cost  = GetCostOfATour(new_route);
					if(new_cost < best_cost) {
						existing_route = new_route;
						best_cost = new_cost;
						terminate = true;
						break;
					}
		         }
				if(terminate==true) {
					break;
				}
		    }
	    }
		return best_cost;
}
	
	
	// Method which returns the cost of a tour
	public static double GetCostOfATour(ArrayList<City> cities) {
		Double cost = 0.0;
        for (int i = 0; i < cities.size(); i++) {
        	if (i == cities.size()-1) {
        		Double temploop = cities.get(i).distanceToCity(cities.get(0));
            	cost = cost + temploop;
        		break;
        	}
        	Double temp = cities.get(i).distanceToCity(cities.get(i+1));
        	cost = cost + temp;
        }
        return cost;
	}

	// Method which returns the standard deviation of an ArrayList of costs
	public static double getSd(ArrayList<Double> allcosts) {
        double mean = allcosts.stream().mapToDouble(val -> val).average().orElse(0.0);
        double temp = 0;
        for (int i = 0; i < allcosts.size(); i++) {
            double val = allcosts.get(i);
            double squrDiffToMean = Math.pow(val - mean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = (double) temp / (double) (allcosts.size());
        return Math.sqrt(meanOfDiffs);
    }
	
	// Method which returns the maximum of an ArrayList of costs
	public static double getMax(ArrayList<Double> allcosts) {
    	double max = allcosts.stream().max(Comparator.naturalOrder()).get();
    	return max; }  
	
	// Method which returns the minimum of an ArrayList of costs
	public static double getMin(ArrayList<Double> allcosts) {
    	double min = allcosts.stream().min(Comparator.naturalOrder()).get();
    	return min; 
    }
	
	// Method which returns the mean of an ArrayList of costs
	public static double getMean(ArrayList<Double> allcosts) {
    	double mean = allcosts.stream().mapToDouble(val -> val).average().orElse(0.0);
    	return mean; 
    }
	
	public static void Runner(int num_of_cities) {
		 TSP QA = new TSP() ;
	        ArrayList<Double> BruteForceCosts = new ArrayList<Double>();
	        ArrayList<Double> BruteForce_randomtourCosts = new ArrayList<Double>();
	        ArrayList<Double> HillCliming_randomtourCosts = new ArrayList<Double>();

	        int Optimal_Solutions_b = 0;
	        int Optimal_Solutions_c = 0;

	        // Question 3 (a) and (b)
	        for (int i = 0; i<100 ; i++) {
	        	ArrayList<City> cities = QA.GenerateNcities(num_of_cities);
	        	Permute obj = new Permute();
	     	    obj.heapPermutation(cities, cities.size(), cities.size());
	            ArrayList<City> tempTour = TSP_BruteForce_RandomTour(cities,obj);
	           
	            double temp_brute = TSP_BruteForce(cities,obj);
	            double temp_random = GetCostOfATour(tempTour);
	            double temp_hill_C = TSP_HillClimbing(tempTour);

	            BruteForceCosts.add(temp_brute);
	            BruteForce_randomtourCosts.add(temp_random);
	            HillCliming_randomtourCosts.add(temp_hill_C);
	        	
	        	if (temp_brute == temp_random) {
	            	Optimal_Solutions_b++;
	            }
	        	if (temp_brute == temp_hill_C) {
	            	Optimal_Solutions_c++;
	            }
			}
	        
	        System.out.println("Question 3 (a) :");
	        System.out.println("Min : " + getMin(BruteForceCosts));
	        System.out.println("Max : " + getMax(BruteForceCosts));
	        System.out.println("Mean : " + getMean(BruteForceCosts));
	        System.out.println("SD : " + getSd(BruteForceCosts));
	        
	        System.out.println("-------------------------------");
	        
	        System.out.println("Question 3 (b) :");
	        System.out.println("Min : " + getMin(BruteForce_randomtourCosts));
	        System.out.println("Max : " + getMax(BruteForce_randomtourCosts));
	        System.out.println("Mean : " + getMean(BruteForce_randomtourCosts));
	        System.out.println("SD : " + getSd(BruteForce_randomtourCosts));
	        System.out.println("Optimal solutions : " + Optimal_Solutions_b);
	        
	        System.out.println("-------------------------------");

	        System.out.println("Question 3 (c) :");
	        System.out.println("Min : " + getMin(HillCliming_randomtourCosts));
	        System.out.println("Max : " + getMax(HillCliming_randomtourCosts));
	        System.out.println("Mean : " + getMean(HillCliming_randomtourCosts));
	        System.out.println("SD : " + getSd(HillCliming_randomtourCosts));   
	        System.out.println("Optimal solutions : " + Optimal_Solutions_c);
	        System.out.println("-------------------------------");

    }
	
	public static void Runner100() {
		 TSP QA = new TSP() ;
	        ArrayList<Double> BruteForce_randomtourCosts = new ArrayList<Double>();
	        ArrayList<Double> HillCliming_randomtourCosts = new ArrayList<Double>();

	        // Question 3 (a) and (b)
	        for (int i = 0; i<100 ; i++) {
	        	ArrayList<City> cities = QA.GenerateNcities(100);
	            ArrayList<City> tempTour = cities;
	           
	            double temp_random = GetCostOfATour(tempTour);
	            double temp_hill_C = TSP_HillClimbing(tempTour);

	            BruteForce_randomtourCosts.add(temp_random);
	            HillCliming_randomtourCosts.add(temp_hill_C);
	        	
			}
	        
	        System.out.println("Question 3 (b) with 100 cities:");
	        System.out.println("Min : " + getMin(BruteForce_randomtourCosts));
	        System.out.println("Max : " + getMax(BruteForce_randomtourCosts));
	        System.out.println("Mean : " + getMean(BruteForce_randomtourCosts));
	        System.out.println("SD : " + getSd(BruteForce_randomtourCosts));
	        
	        System.out.println("-------------------------------");

	        System.out.println("Question 3 (c) (b) with 100 cities:");
	        System.out.println("Min : " + getMin(HillCliming_randomtourCosts));
	        System.out.println("Max : " + getMax(HillCliming_randomtourCosts));
	        System.out.println("Mean : " + getMean(HillCliming_randomtourCosts));
	        System.out.println("SD : " + getSd(HillCliming_randomtourCosts));   
	        
	        System.out.println("-------------------------------");

	}
	
	
	
	public static void main(String[] args) {
		Runner(7);
		Runner100();


	} 
	
	
}
