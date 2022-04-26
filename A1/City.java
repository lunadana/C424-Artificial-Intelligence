package COMP424;

/* DANA, Luna (260857641)

COMP424 - Assignment 1
Question 3)

*/// Source: https://www.baeldung.com/java-simulated-annealing-for-traveling-salesman
public class City {

	public double x;
	public double y;
	
	// initialize the two attributes of a city which are their position in the 2d plane
	public City() {
	    this.x = Math.random() ;
	    this.y = Math.random() ;
	}

	// Distance from this.city to another city
	public double distanceToCity(City city) {
	    double x = Math.abs(this.x - city.x);
	    double y = Math.abs(this.y - city.y);
	    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
}
