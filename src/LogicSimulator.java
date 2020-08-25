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

	public LogicSimulator() {
		circuits = new Vector<>();
		iPins = new Vector<>();
		oPins = new Vector<>();
	}

	public boolean load(String Filepath) {
		File file = new File(Filepath);
//    	ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			if (file.isFile() && file.exists()) {
				return true;

			} else {
				System.out.println("load file fail");
				return false;
			}

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

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

	}

	public boolean parseLoadData(String Filepath) {
		File file = new File(Filepath);
		ArrayList<String> list = new ArrayList<String>();
		String[] words = null;
		int linecount = 0;
		int CountGateNOT = 0;
		int CountGateOR = 0;
		int CountGateAND = 0;

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
		
		for (int i = 2; i < linecount; i++) {
			
			words = list.get(i).split(" ");
			Device device = new Device();
			for (int j = 0; j < words.length; j++) {
				// parse the gatetype
				if (j == 0) {
					
					switch (words[0]) {
					case "1":
					device = new gateAND();
						CountGateAND++;
						break;
					case "2":
					device = new gateAND();
						CountGateOR++;
						break;
					case "3":
					device = new gateNOT();
						CountGateNOT++;
						break;
					}

				} 
				else {
					System.out.println(words[j]);
					if (words[j].equals("0")) {
						System.out.println("get 0 gonextline");
						continue;
					}

					// parse the digit type
					// negative / float /
					if (words[j].indexOf('.') == -1) {
						if (words[j].indexOf('-') != -1) { 
							//negative
 							System.out.println("get negative : " + words[j]);
 							device.addinputpin(iPins.get(Math.abs(Integer.parseInt(words[j]))));
 							
 							
 							
						}
					} else {
						System.out.println("get float : " + words[j]);
					}
				}

			}

		}
		System.out.println(" linecount:" + linecount);

//		
//		//read the first line >>get input pins number
//		if (Integer.parseInt(list.get(0) )> 0) {
//			for (int i = 0; i < Integer.parseInt(list.get(0); i++) {
//				IPin iPin = new IPin();
//				iPins.add(iPin);
//			}
//		}
//
//		
//		
//		
//		int gatecount = Integer.parseInt(list.get(1) ); //gate number
//
//		
//		//for
//		
//			for (int i = 0;  i < list.get(2).length() ; i ++) {
//				Scanner sc= new Scanner(list.get(2));  //System.in is a standard input stream  
//				String str= sc.next();   //reads string before the space  
//				if(i==0) {
//					switch(str){
//						case "1":
//							System.out.println("get gateAND");
//							break;
//						case "2":
//							System.out.println("get gateOR");
//							break;
//						case "3":
//							System.out.println("get gateNOT");
//							break;
//					}
//					
//				}
//				
//				if(str.equals(" "));
//					continue;
//				if(str.equals("0"));
//					break;
//				
//				
//			}
//	
//		
//		
//		oPins.addInputPin(circuit.get(gatecount).);

		return false;
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

//		result +="|" + oPins.getOutput() + "\n";

		String resultgraph = "Simulation Result:\n" + "i i i | o\n" + "1 2 3 | 1\n" + "------+--\n" + result;

		return result;
	}

	public String getTruthTable() {
		String Table = "";

		return Table;
	}
}
