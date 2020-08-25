
public class IPin extends Device{
	
	private boolean Inputvalue;
	
	
	@Override
	public boolean getOutput(){
		return Inputvalue;
	}
	
	@Override
	public void setInput(boolean Inputvalue) {
		this.Inputvalue = Inputvalue;
	}
	

}
