
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class LogicSimulator {
	private Vector<Device> circuits;
	private Vector<Device> iPins;
	private Vector<Device> oPins;
	private Vector<String> OutputCandidate;
	
	public boolean load(String Filepath) {
		File file = new File(Filepath);
		Vector<String> list = new Vector<String>();
		clearAll();
		
		// load and read file data
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = "";

			while ((lineTxt = bufferedReader.readLine()) != null) {
				list.add(lineTxt); // String to Integer
			}
			bufferedReader.close();
			read.close();
			createIPins(Integer.parseInt(list.get(0)));				//		read the first line  >>get input pins number
			createOutputCandidates(Integer.parseInt(list.get(1)));	//		read the second line >>get input pins number
	
	
					
		} catch (Exception e) {
			//System.out.println(e);
			
			return false;
		}

		setGatetoCircuits(list);
		setEachGateInputs(list);
		
		createOPins(getOutputCandidateSize());
		setOPins(OutputCandidate); 
		return true;
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
			
		

		String resultgraph = 
				"Simulation Result:\n" + 
				"i i i | o\n" + 
				"1 2 3 | 1\n" + 
				"------+--\n" + result;
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
		return Table;
	}
	
	public int getInputPinsSize() {
		return iPins.size();
	}
	public int getOutputPinsSize() {
		return oPins.size();
	}
	public int getCircuitsSize() {
		return circuits.size();
	}

	private int getOutputCandidateSize() {
		return OutputCandidate.size();
	}
	
	private void clearAll() {
		circuits = new Vector<>();
		iPins = new Vector<>();
		oPins = new Vector<>();
		OutputCandidate = new Vector<>();
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
	private void setEachGateInputs(Vector<String> GateData) {
		//parse the file data and define the pin
		for (int i = 2; i < GateData.size(); i++) {
				
			String[] words = GateData.get(i).split(" ");
				
			for (int j = 0; j < words.length; j++) {
				if (words[j].equals("0")) {
					continue;
				}

						
				if (words[j].indexOf('.') == -1) {
					if (words[j].indexOf('-') != -1) { 
						//negative part
						//ex: -1 ->ipin num 1 =>index 0 of ipins 
						int fromIpinNum = (int) Integer.parseInt(words[j]);
						int IpinIndex = Math.abs(fromIpinNum) -1;
						int selectGateIndex = i-2;
						circuits.get(selectGateIndex).addInputPin(iPins.get(IpinIndex));
					}
				} 
				else {
					//float part 
					//ex: 2.1 => 2 means gate 2  => index in the circuits is 2-1 = 1  
					float fromGateNumber = (float) Float.parseFloat(words[j]);
					int fromGNint = (int)fromGateNumber-1;
					int selectGateIndex = i-2;
					
					circuits.get(selectGateIndex).addInputPin(circuits.get(fromGNint) ); //add other gate as input
					String gateToBeRemoved = (int)fromGateNumber + "";
					koGateFromOutputCandidate(gateToBeRemoved);

				}
			}
				
		}
		
	}

	private void setGatetoCircuits(Vector<String> GateData) {
		// parse the gatetype and generate the gate in circuit
		for (int i = 2; i < GateData.size(); i++) {
			
			String[] words = GateData.get(i).split(" ");
			switch (words[0]) {
				case "1": //AND gate
				circuits.add(new gateAND());
					break;
					
				case "2": //OR gate
				circuits.add(new gateOR());
					break;
				
				case "3": //NOT¡@gate
				circuits.add(new gateNOT());
					break;
			}
		}
	}
	private void createOutputCandidates(int gates) {
		for (int i = 0; i < gates; i++) {
			String Outputlist = "" + (i+1) ;
			OutputCandidate.add(Outputlist);
//			System.out.println("show OC :" + OutputCandidate.get(i));
		}

	}
	private void koGateFromOutputCandidate(String gateToBeRemoved) {
		if(OutputCandidate.contains(gateToBeRemoved))
			OutputCandidate.remove(gateToBeRemoved);
	}
	
}
