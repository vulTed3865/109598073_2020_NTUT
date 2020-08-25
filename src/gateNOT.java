
public class gateNOT extends Device{
	
	@Override
	public boolean getOutput(){
		
		System.out.println("not result: " +this.IPins.get(0).getClass().getName());
//		return true;
		return !this.IPins.get(0).getOutput();
	}
}
