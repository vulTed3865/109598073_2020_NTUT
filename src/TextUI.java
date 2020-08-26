import java.util.Scanner;
import java.util.Vector;

public class TextUI {
	private boolean isEventend = false;
	private boolean isLoad = false;
	private LogicSimulator ls = new LogicSimulator();
	public void displayMenu() {
		System.out.print(
				"1. Load logic circuit file\n" +
				"2. Simulation\n" +
				"3. Display truth table\n" + 
				"4. Exit\n");
		
	}
	public void processCommand() {
		Scanner scanner = new Scanner(System.in);
		
		//command1 2 3 
		System.out.print("Command:");
		
		String inputCommand = scanner.nextLine();
		switch(inputCommand) {
		
			case "1" :
				
				System.out.print("Please key in a file path:");
				if(ls.load(scanner.nextLine()))
				{	
					isLoad = true;
					System.out.println("Circuit: "+ 
							ls.getInputPinsSize() + " input pins, "+  
							ls.getOutputPinsSize()+ " output pins and "+
							ls.getCircuitsSize()  + " gates \r\n"  );
				}
				else {
					System.out.println("File not found or file format error!! ");
					
				}
					
				
				break;
			case "2" :
				if(!isLoad) {
					System.out.println("Please load an lcf file, before using this operation.");
				}
				else {
					   
					   Vector<Boolean> inputValues = new Vector<>();
					   for (int i = 0; i < ls.getInputPinsSize(); i++) {
						   System.out.printf("Please key in the value of input pin %d:",i+1);
						   String inputvalue = scanner.nextLine();
						   if(inputvalue.equals("0") ||inputvalue.equals("1") )
						   {
							   inputValues.add(!inputvalue.equals("0"));
						   }
						   else {
							   //reinput
							   i--;
							   System.out.println("The value of input pin must be 0/1");
						   }
							   
					   }
					   System.out.println(ls.getSimulationResult(inputValues));
				}
				
				break;
			case "3" :
				if(!isLoad) {
					System.out.println("Please load an lcf file, before using this operation.");
				}
				else {
					System.out.println(ls.getTruthTable());
					
				}
				break;
			case "4" :
				System.out.println("Goodbye, thanks for using LS");
				ChangeisEventend();
				System.exit(0);
				break;
			default:
				System.out.println("Invaild Command !!");
				break;
		}
				
		
	}
	public void execute() {
		
		while(isEventend == false) {
			System.out.println("");
			displayMenu();
			processCommand();
		}
		
		
	}
	private boolean ChangeisEventend() {
		isEventend = !isEventend;
		return isEventend;
	}
}
