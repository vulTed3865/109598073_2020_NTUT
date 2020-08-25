import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";

				while ((lineTxt = bufferedReader.readLine()) != null) {
					list.add(Integer.parseInt(lineTxt)); // String to Integer
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
		//read the first line >>get input pins number
		if (list.get(0) > 0) {
			for (int i = 0; i < list.get(0); i++) {
				IPin iPin = new IPin();
				iPins.add(iPin);
			}
		}
		
		
		if(list.get(1)> 0) {
			
		}
		
		return false;
	}

	public String getSimulationResult(Vector<Boolean> inputValues) {
		String result = "";

		for (int i = 0; i < inputValues.size(); i++) {
			iPins.get(i).setInput(inputValues.get(i));
		}

		return result;
	}

	public String getTruthTable() {
		String Table = "";

		return Table;
	}
}
