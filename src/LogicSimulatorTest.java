import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class LogicSimulatorTest {
	
	String file1Path;
	String file2Path;

	@Before
	public void setUp()
	{
	    file1Path = "src/File1.lcf";
	    file2Path = "src/File2.lcf";
	}
	
	@Test
	public void testLoad() {
		LogicSimulator logicSimulator = new LogicSimulator();
		assertEquals(true, logicSimulator.load(file1Path));
	}
	
	@Test
	public void testGetSimulationResult()
	{
	    LogicSimulator logicSimulator = new LogicSimulator();
	    logicSimulator.load(file1Path);
	    
	    //Set input 
	    Vector<Boolean> inputValues = new Vector<>();
	    inputValues.add(false);
	    inputValues.add(true);
	    inputValues.add(true);
	    
	    assertEquals("Simulation Result:\n" +
	                 "i i i | o\n" +
	                 "1 2 3 | 1\n" +
	                 "------+--\n" +
	                 "0 1 1 | 0\n", logicSimulator.getSimulationResult(inputValues));
	}

}
