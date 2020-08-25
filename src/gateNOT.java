
public class gateNOT extends Device{
	
	@Override
	public boolean getOutput(){
		return !this.IPins.get(0).getOutput();
	}
}
