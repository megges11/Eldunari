package eldunari.general.classes;
import java.util.ArrayList;

public class EldunariList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 2596385052059636419L;	
	
	public EldunariList(){
	}

	public T firstOrDefault(){
		if(this.size()!= 0){
			return this.get(0);
		}
		return null;
	}
	
	public T lastOrDefault(){
		if(this.size()!= 0){
			return this.get(this.size()-1);
		}
		return null;
	}	
}
