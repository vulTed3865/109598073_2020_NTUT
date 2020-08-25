import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class LogicSimulator {
	private Vector<Device> circuits;
	private Vector<Device> iPins;
	private Vector<Device> oPins;
	private Vector<String> OutputCandidate;
	
	public LogicSimulator() {
		circuits = new Vector<>();
		iPins = new Vector<>();
		oPins = new Vector<>();
		OutputCandidate = new Vector<>();
	}

//	public boolean load(String Filepath) {
//		File file = new File(Filepath);
////    	ArrayList<Integer> list = new ArrayList<Integer>();
//		try {
//			if (file.isFile() && file.exists()) {
//				return true;
//
//			} else {
//				System.out.println("load file fail");
//				return false;
//			}
//
//		} catch (Exception e) {
//			System.out.println(e);
//			return false;
//		}

//		  InputStreamReader read = new InputStreamReader(new FileInputStream(file));
//      BufferedReader bufferedReader = new BufferedReader(read);
//      String lineTxt = "";
//
//      while ((lineTxt = bufferedReader.readLine()) != null)
//      {
//          list.add(lineTxt);
//      }
//      bufferedReader.close();
//      read.close();
		// https://www.cnblogs.com/0201zcr/p/5009975.html

//	}

	public boolean load(String Filepath) {
		File file = new File(Filepath);
		ArrayList<String> list = new ArrayList<String>();
		String[] words = null;
		int linecount = 0;
		int CountGate = 0;
		// load and read file data
		try {
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";

				while ((lineTxt = bufferedReader.readLine()) != null) {
					list.add(lineTxt); // String to Integer
					linecount++;
				}
				bufferedReader.close();
				read.close();
			} else {
				System.out.println("load file fail");
				return false;
			}

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

//		read the first line >>get input pins number
		if (Integer.parseInt(list.get(0)) > 0) {
			for (int i = 0; i < Integer.parseInt(list.get(0)); i++) {
				IPin iPin = new IPin();
				iPins.add(iPin);
			}
		}
		
		if (Integer.parseInt(list.get(1)) > 0) {
			for (int i = 0; i < Integer.parseInt(list.get(1)); i++) {
				String Outputlist = "" + (i+1) ;
				OutputCandidate.add(Outputlist);
			}
		}
		
		System.out.println("show OC :" + OutputCandidate.get(0));
		System.out.println("show OC :" + OutputCandidate.get(1));
		System.out.println("show OC :" + OutputCandidate.get(2));

		// parse the gatetype and generate the gate in circuit
		for (int i = 2; i < linecount; i++) {
			
			words = list.get(i).split(" ");
			
			for (int j = 0; j < words.length; j++) {
				
				Device device = new Device();
				if (j == 0) {
					
					switch (words[0]) {
						case "1": //AND gate
						device = new gateAND();
						circuits.add(device);
						CountGate ++;
							break;
							
						case "2": //OR gate
						device = new gateOR();
						circuits.add(device);
						CountGate ++;
							break;
						
						case "3": //NOT¡@gate
						device = new gateNOT();
						circuits.add(device);
						CountGate ++;
							break;
					}

				} 
				
			}
			
		}
//		System.out.println("circuit size():" + circuits.size() + "CountGate:" + CountGate);
		
		//parse the file data and define the pin
		for (int i = 2; i < linecount; i++) {
			
			words = list.get(i).split(" ");
			
			for (int j = 1; j < words.length; j++) {
			
					System.out.println(words[j]);

					if (words[j].equals("0")) {
						System.out.println("get 0 go nextline");
						continue;
					}

					// parse the digit type
					// negative / float /
					
					if (words[j].indexOf('.') == -1) {
						if (words[j].indexOf('-') != -1) { 
							//negative
 							System.out.println("get negative : " + words[j] + " j:" + j);
 							System.out.println("circuit size():" + circuits.size() + "CountGate:" + CountGate);
 							int fromipinnum = (int) Integer.parseInt(words[j]);
 							System.out.println("fromipinnum:" + Math.abs(fromipinnum));
 							int Ipinindex = Math.abs(fromipinnum) -1;
 							circuits.get(j-1).addInputPin(iPins.get(Ipinindex));
 							
						}
					} 
					else {
						System.out.println("get float : " + words[j]);
						float fromgatenumber = (float) Float.parseFloat(words[j]);
						int fromGN = (int)fromgatenumber-1;
						circuits.get(j-1).addInputPin(circuits.get(fromGN)); //add other gate as input
						
						String gateberemove = (int)fromgatenumber + "";
						
						boolean found = OutputCandidate.contains(gateberemove);
						
						System.out.println("OutputCandidatesize : " + OutputCandidate.size() );
						System.out.println("found : " + found + " gateberemove : " +gateberemove );
						if(found)
							OutputCandidate.remove(gateberemove);
						
						
						for(int k = 0 ; k < OutputCandidate.size();k++) {
							System.out.println("");
							System.out.println("the OC " + k + " ");
							System.out.println(OutputCandidate.get(k));
							System.out.println("");
						}
						
						System.out.println("");
						
					}
			

			}
			
		}
		int outputgate = Integer.parseInt(OutputCandidate.get(0));
		System.out.println("outputgate : " + OutputCandidate.get(0));
		oPins.add(new OPin());
		oPins.get(0).addInputPin(circuits.get(outputgate-1));
//		 
		return true;
	}

	public String getSimulationResult(Vector<Boolean> inputValues) {
		String result = "";

		for (int i = 0; i < inputValues.size(); i++) {
			iPins.get(i).setInput(inputValues.get(i));
		}

		// generate "0 1 1 "
		for (int i = 0; i < inputValues.size(); i++) {
			result += inputValues.get(i);
			result += " ";
		}
		
		
		
		result = result + "|" + oPins.get(0).getOutput() + "\n";
//		System.out.println("result :"+ result);
		String resultgraph = "Simulation Result:\n" + "i i i | o\n" + "1 2 3 | 1\n" + "------+--\n" + result;

		return result;
	}

	public String getTruthTable() {
		String Table = "";

		return Table;
	}
}
