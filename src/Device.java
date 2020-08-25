import java.util.Vector;



public class Device {
	protected Vector<Device> IPins;
	public Device()
    {
		IPins = new Vector<>();
    }
	public void addInputPin(Device Inputpin) {
//		System.out.println(" addInputPin  : " + Inputpin);
		this.IPins.add(Inputpin);
	}
	public void setInput(boolean Inputvalue)  {
		throw new RuntimeException("setInput method is not for device");
	}
	public boolean getOutput(){
		throw new RuntimeException("getOutput method is not for device");
		
	}
}
