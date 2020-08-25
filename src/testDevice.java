

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testDevice {

	@Before
	public void Setup() {
		
	}
	
	@After 
	public void tearDown() {
		
	}
	
	@Test
	public void testDevicePolymorphism() {
		Device device = new IPin();
		assertEquals(IPin.class.getName() , device.getClass().getName());
		
		device = new OPin();
		assertEquals(OPin.class.getName() , device.getClass().getName());
		
		device = new gateAND();
		assertEquals(gateAND.class.getName() , device.getClass().getName());
		
		device = new gateOR();
		assertEquals(gateOR.class.getName() , device.getClass().getName());
		
		device = new gateNOT();
		assertEquals(gateNOT.class.getName() , device.getClass().getName());
	}
	
	
	@Test
	public void testIPinAndOPin() {
		//0=0
		IPin iPin = new IPin();
		iPin.setInput(false);
		
		OPin oPin = new OPin();
		oPin.addInputPin(iPin);
		
		assertEquals(false, oPin.getOutput());
		
		//1=1
		iPin = new IPin();
		iPin.setInput(true);
		
		oPin = new OPin();
		oPin.addInputPin(iPin);
		
		assertEquals(true, oPin.getOutput());
		
	}
	
	@Test
	public void testgateNOT() {
		// 0 -> 1
		IPin ipin = new IPin();		
		gateNOT NOTgate = new gateNOT();
		ipin.setInput(false);
		NOTgate.addInputPin(ipin);
		assertEquals(true,NOTgate.getOutput());
		
		// 1 -> 0
		ipin = new IPin();		
		NOTgate = new gateNOT();
		ipin.setInput(true);
		NOTgate.addInputPin(ipin);
		assertEquals(false,NOTgate.getOutput());
		
	}
	
	@Test
	public void testgateOR() {
		
		// 0  -> 0
		IPin ipin1 = new IPin();
		gateOR ORgate = new gateOR();
		ipin1.setInput(false);
		ORgate.addInputPin(ipin1);
		assertEquals(false,ORgate.getOutput());
		
		//1  -> 1
		ipin1 = new IPin();
		ORgate = new gateOR();
		ipin1.setInput(true);
		ORgate.addInputPin(ipin1);
		assertEquals(true,ORgate.getOutput());
		
		
		// 0 | 0 -> 0
		ipin1 = new IPin();
		ORgate = new gateOR();
		IPin ipin2 = new IPin();
		ipin1.setInput(false);
		ipin2.setInput(false);
		
		ORgate.addInputPin(ipin1);
		ORgate.addInputPin(ipin2);
		assertEquals(false,ORgate.getOutput());
		
		// 1 | 0 -> 1
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ORgate = new gateOR();
		ipin1.setInput(true);
		ipin2.setInput(false);
		
		ORgate.addInputPin(ipin1);
		ORgate.addInputPin(ipin2);
		assertEquals(true,ORgate.getOutput());
		
		// 0 | 1 -> 1
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ORgate = new gateOR();
		ipin1.setInput(false);
		ipin2.setInput(true);
		
		ORgate.addInputPin(ipin1);
		ORgate.addInputPin(ipin2);
		assertEquals(true,ORgate.getOutput());
		
		// 1 | 1 -> 1
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ORgate = new gateOR();
		ipin1.setInput(true);
		ipin2.setInput(true);
		
		ORgate.addInputPin(ipin1);
		ORgate.addInputPin(ipin2);
		assertEquals(true,ORgate.getOutput());
	
		
	}
		
	@Test
	public void testgateAND() {

		// 0  -> 0
		IPin ipin1 = new IPin();
		gateAND ANDgate = new gateAND();
		ipin1.setInput(false);
		ANDgate.addInputPin(ipin1);
		assertEquals(false,ANDgate.getOutput());
		
		//1  -> 1
		ipin1 = new IPin();
		ANDgate = new gateAND();
		ipin1.setInput(true);
		ANDgate.addInputPin(ipin1);
		assertEquals(true,ANDgate.getOutput());
		
		// 0 | 0 -> 0
		ipin1 = new IPin();
		IPin ipin2 = new IPin();
		ANDgate = new gateAND();
		ipin1.setInput(false);
		ipin2.setInput(false);
		
		ANDgate.addInputPin(ipin1);
		ANDgate.addInputPin(ipin2);
		assertEquals(false,ANDgate.getOutput());
		
		// 1 | 0 -> 0
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ANDgate = new gateAND();
		ipin1.setInput(true);
		ipin2.setInput(false);
		
		ANDgate.addInputPin(ipin1);
		ANDgate.addInputPin(ipin2);
		assertEquals(false,ANDgate.getOutput());
		
		// 0 | 1 -> 0
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ANDgate = new gateAND();
		ipin1.setInput(false);
		ipin2.setInput(true);
		
		ANDgate.addInputPin(ipin1);
		ANDgate.addInputPin(ipin2);
		assertEquals(false,ANDgate.getOutput());
		
		// 1 | 1 -> 1
		ipin1 = new IPin();
		ipin2 = new IPin();
		
		ANDgate = new gateAND();
		ipin1.setInput(true);
		ipin2.setInput(true);
		
		ANDgate.addInputPin(ipin1);
		ANDgate.addInputPin(ipin2);
		assertEquals(true,ANDgate.getOutput());
	
		
	}
	
	
	
		
}

