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
	


	public boolean load(String Filepath) {
		File file = new File(Filepath);
		Vector<String> list = new Vector<String>();
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
				createIPins(Integer.parseInt(list.get(0)));				//		read the first line  >>get input pins number
				createOutputCandidates(Integer.parseInt(list.get(1)));	//		read the second line >>get input pins number
			}	
					
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		
		// parse the gatetype and generate the gate in circuit
		for (int i = 2; i < linecount; i++) {
			
			words = list.get(i).split(" ");
			switch (words[0]) {
				case "1": //AND gate
				circuits.add(new gateAND());
				CountGate ++;
					break;
					
				case "2": //OR gate
				circuits.add(new gateOR());
				CountGate ++;
					break;
				
				case "3": //NOT¡@gate
				circuits.add(new gateNOT());
				CountGate ++;
					break;
			}
		}

		//parse the file data and define the pin
		for (int i = 2; i < linecount; i++) {
			
			words = list.get(i).split(" ");
			
			for (int j = 0; j < words.length; j++) {
			
//					System.out.println(words[j]);

					if (words[j].equals("0")) {
						System.out.println("get 0 go nextline");
						System.out.println("");
						continue;
					}

					
					if (words[j].indexOf('.') == -1) {
						if (words[j].indexOf('-') != -1) { 
							//negative
							//ex: -1 ->ipin num 1 =>index 0 of ipins 
							System.out.println("get nega : " + words[j]);
							int fromipinnum = (int) Integer.parseInt(words[j]);
 							int Ipinindex = Math.abs(fromipinnum) -1;
 							int selectgateindex = i-2;
 							circuits.get(selectgateindex).addInputPin(iPins.get(Ipinindex));
 							System.out.println("selectgateindex: "+ selectgateindex +" Ipinindex: "+ Ipinindex );
 							//circuits 1 gateand  -> 3 gatenot  -> 2 gate or 
						}
					} 
					else {
						System.out.println("get float : " + words[j]);
						//ex: 2.1 => 2 means gate 2  => index in the circuits is 2-1 = 1  
						float fromgatenumber = (float) Float.parseFloat(words[j]);
						int fromGN = (int)fromgatenumber-1;
						int selectgateindex = i-2;
						
						circuits.get(selectgateindex).addInputPin(circuits.get(fromGN) ); //add other gate as input
						System.out.println("selectgateindex: "+selectgateindex+" fromGN: "+ fromGN );
						
						
						String gateberemove = (int)fromgatenumber + "";
						boolean found = OutputCandidate.contains(gateberemove);
						
						System.out.println("OutputCandidatesize : " + OutputCandidate.size() );
						System.out.println("found : " + found + " gateberemove : " +gateberemove );
						if(found)
							OutputCandidate.remove(gateberemove);
						
						
//						for(int k = 0 ; k < OutputCandidate.size();k++) {
//							System.out.println("");
//							System.out.println("the OC " + k + " ");
//							System.out.println(OutputCandidate.get(k));
//							System.out.println("");
//						}
						
//						System.out.println("");
						
					}
			}
			
		}
		
		int outputgate = Integer.parseInt(OutputCandidate.get(0));
		int OutputCandidatenumber = OutputCandidate.size();
		
		createOPins(OutputCandidatenumber);
		setOPins(OutputCandidate);
		
		
//		OPin output  = new OPin();
//		oPins.add(output);
//		oPins.get(0).addInputPin(circuits.get(outputgate-1));
//		System.out.println("circuits n : " + circuits.get(outputgate-1));
//		System.out.println("oPins 0 : " + oPins.get(0));
		
//		 
		return true;
	}
	
	private void createIPins(int ipins) {
		for (int i = 0; i < ipins; i++) {
			IPin iPin = new IPin();
			iPins.add(iPin);
		}
	}
	private void createOPins(int opins) {
		for (int i = 0; i < opins; i++) {
			OPin oPin = new OPin();
			oPins.add(oPin);
		}
	}
	private void setOPins(Vector<String> OutputCandidate) {
		
		for (int i = 0; i < OutputCandidate.size(); i++) {
			int outputgate = Integer.parseInt(OutputCandidate.get(i));
			oPins.get(i).addInputPin(circuits.get(outputgate-1));
		}
		
		
		
	}
	private void createOutputCandidates(int gates) {
		for (int i = 0; i < gates; i++) {
			String Outputlist = "" + (i+1) ;
			OutputCandidate.add(Outputlist);
			System.out.println("show OC :" + OutputCandidate.get(i));
		}

	}
	public String getSimulationResult(Vector<Boolean> inputValues) {
		String result = "";

		for (int i = 0; i < inputValues.size(); i++) {
			iPins.get(i).setInput(inputValues.get(i));
		}

		// generate "0 1 1 "
		for (int i = 0; i < inputValues.size(); i++) {
			
			if(inputValues.get(i).equals(false)) {
				result += 0;
				
			}else {
				result += 1;
			}
			result += " ";
		}
		if(oPins.get(0).getOutput()) {
			result = result + "|" + " " + 1  + "\n";
		}
		else {
			result = result + "|" + " " + 0  + "\n";
		}
			
		
		
//		System.out.println("result :"+ result);
		String resultgraph = 
				"Simulation Result:\n" + 
				"i i i | o\n" + 
				"1 2 3 | 1\n" + 
				"------+--\n" + result;
		System.out.println(resultgraph);
		return resultgraph;
	}

	public String getTruthTable() {
		String Table = "";
		//head
		//line1
		Table += "Truth table:\n" ;
		for (int i = 0; i < iPins.size(); i++) {
			Table += "i " ;
		}
		Table += "|" ;
		for (int i = 0; i < oPins.size(); i++) {
			Table += " " ;
			Table += "o" ;
		}
		Table += "\n" ;
		
		//line2
		for (int i=0; i<iPins.size(); i++)
        {
			Table += (i + 1) + " " ;
        }
		Table += "|" ;

		System.out.println(oPins.size());
        for (int i=0; i<oPins.size(); i++)
        {
        	Table += " ";
        	Table +=i+1 ;
        }
        Table +="\n";
		
		//line3
        for (int i=0; i<iPins.size(); i++)
        {
			Table += "--" ;
        }
        Table +="+";
        for (int i=0; i<oPins.size(); i++)
        {
        	Table +="--";
        }
        Table +="\n";
        
		//body
        
        
        int rows = (int) Math.pow(2,iPins.size());
		
        for (int i=0; i<rows; i++) {
        	int currentipin = 0;
            for (int j=iPins.size()-1; j>=0; j--) {
            	int truthvalue = i/(int) Math.pow(2, j)%2;

            	//set input value to iPins.ipin
    			if(truthvalue == 1 )
    				iPins.get(currentipin).setInput(true);
    			else if (truthvalue == 0 )
    				iPins.get(currentipin).setInput(false);
    			Table += truthvalue + " ";
    			System.out.println("currentipin : "+currentipin);
    			currentipin++;
    			
            }
            Table +="|";
            
            //insert output value
            
            for (int j=0; j<oPins.size(); j++)
            {
            	Table += " " ;
            	if(oPins.get(j).getOutput()) {
                  	Table +=  1  ;
          		}
          		else {
          			Table +=  0  ;
          		}
    				
            }
            Table +="\n";

        }
        System.out.println(Table);
		    
		
//		assertEquals("Truth table:\n" +
//                "i i i | o\n" +
//                "1 2 3 | 1\n" +
//                "------+--\n" +
//                "0 0 0 | 0\n" +
//                "0 0 1 | 0\n" +
//                "0 1 0 | 0\n" +
//                "0 1 1 | 0\n" +
//                "1 0 0 | 1\n" +
//                "1 0 1 | 1\n" +
//                "1 1 0 | 0\n" +
//                "1 1 1 | 0\n", logicSimulator.getTruthTable());
		
		
		return Table;
	}
}
