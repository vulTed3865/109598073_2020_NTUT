
public class IPin extends Device{
	
	private boolean Inputvalue;
	
//	@Override
//	public void addInputPin(Device Inputpin)   {
//		throw new RuntimeException("addInputPin method is not for IPin!!!!");
//	}
	
	@Override
	public boolean getOutput(){
		return Inputvalue;
	}
	
	@Override
	public void setInput(boolean Inputvalue) {
		this.Inputvalue = Inputvalue;
	}
	

}
