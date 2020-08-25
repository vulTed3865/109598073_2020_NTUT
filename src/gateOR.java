import java.util.Iterator;
import java.util.ListIterator;

public class gateOR extends Device{
	
	@Override
	public boolean getOutput(){
		boolean result = IPins.get(0).getOutput();
		if(IPins.size() <= 1) {
			return result;
		}
		else {
			for (int index = 1 ; index <IPins.size() ; index++) {
				result = result | IPins.get(index).getOutput();
			}
			return result;
		}
		
//		Iterator X = IPins.listIterator();
//		while(X.hasNext()) {
//			X.next() |
//		}
		
		
		
		
	}
	
}
