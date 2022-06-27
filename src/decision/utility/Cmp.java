package decision.utility;

public abstract class Cmp<T> {
	
	public abstract boolean preceq(T o1, T o2);
	
	public boolean prec(T o1, T o2) {
		return this.preceq(o1, o2) && !this.preceq(o2, o1);
	}
	
	public boolean succeq(T o1, T o2) {
		return this.preceq(o2, o1);
	}
	
	public boolean succ(T o1, T o2) {
		return this.preceq(o2, o1) && !this.preceq(o1, o2);
	}
	
	public boolean sim(T o1, T o2) {
		return this.preceq(o1, o2) && this.succeq(o1, o2);
	}
	
	public boolean incomp(T o1, T o2) {
		return !this.preceq(o1, o2) && !this.preceq(o2, o1);
	}
	
	

}
