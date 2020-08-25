
public class gateAND extends Device{
	public boolean getOutput(){
		
	
	boolean result = IPins.get(0).getOutput();
	if(IPins.size() <= 1) {
		return result ;
	}
	else {
		for (int index = 1 ; index <IPins.size() ; index++) {
			result = result & IPins.get(index).getOutput();
		}
		System.out.println("AND result" + result );
		return result;
	}
	}
}
