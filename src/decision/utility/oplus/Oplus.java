package decision.utility.oplus;

/**
 * Abstract addition
 * @author Pierre Pomeret-Coquot
 *
 * @param <U> Type for values to add
 */
public abstract class Oplus<U> {
	
	/**
	 * Oplus (+) operator
	 * @param o1 First component 
	 * @param o2 Second component
	 * @return o1 (+) o2
	 */
	public abstract U oplus(U o1, U o2);
	
	/**
	 * Neutral element of oplus (+)
	 * @return The neutral element for oplus (+)
	 */
	public abstract U zero();

}
