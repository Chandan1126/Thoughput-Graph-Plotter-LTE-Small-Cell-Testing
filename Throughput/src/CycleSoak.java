import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class CycleSoak {
	static ArrayList<String> cy_pid=new ArrayList<String>();
	static ArrayList[] cycleData=new ArrayList[2];
	static ArrayList<Float> p_1=new ArrayList<Float>();
	static ArrayList<Float> p_2=new ArrayList<Float>();
	static ArrayList<Float> avg_1=new ArrayList<Float>();
	static ArrayList<Float> avg_2=new ArrayList<Float>();
	static String pid_1,pid_2;
	static float interval;
	ArrayList[] getSoak(String file_name,int interval) throws IOException{
		avg_1.clear();
		avg_2.clear();
		p_2.clear();
		p_1.clear();
		cy_pid.clear();
		this.interval=interval;
		//String strfile="/home/chandan/Desktop/Data Sample/4UEs TCP 24 hrs/19hrs/topbh.txt";
		String strfile=file_name;
		 String path="CPU-core.txt";
		 File logFile = new File(path);
		File file=new File(strfile);
		  FileInputStream fis = null;
		  try{
			 
			  fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				for(String line =br.readLine();line!=null;line=br.readLine() ){
					if(line.contains("cyclesoak")){
						if(line.contains("R"))
						//System.out.println(line);
						 cy_pid.add(line);
						}
					}
			
		  br.close();} 
		  
		   finally {
				try {
					if (fis != null)
						fis.close();
					   
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		  String[] k=null;
		pid_1=cy_pid.get(0).split("root")[0].replace(" ", "");
		String temp="";
		for(int i=1;i<cy_pid.size();i++){
			temp=cy_pid.get(i).split("root")[0].replace(" ", "");
			if(!temp.equalsIgnoreCase(pid_1)){
				pid_2=cy_pid.get(i).split("root")[0].replace(" ", "");
				break;
			}
			else
				continue;
		}
	//	System.out.println("pid_1="+pid_1+" pid_2="+pid_2);
		 BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
		 writer.write("Core-1:"+pid_1);
		 writer.newLine();
		 writer.write("Core-2:"+pid_2);
		 writer.newLine();
		 writer.close();
		for(String p:cy_pid){
			if(p.contains(pid_1)){
				String[] J=p.split("R");
				p_1.add(Float.valueOf(J[1].substring(2,5).replace(" ", "")));	
			}
			else if(p.contains(pid_2)){
				String[] J=p.split("R");
				p_2.add(Float.valueOf(J[1].substring(2,5).replace(" ", "")));
				
			    }
			}
		float core_1=0,core_2=0;
	     float count=0;
	     for(int i=0;i<p_1.size();i++){
	    	// System.out.println(p_1.get(i));
	    	 core_1+=(100-(p_1.get(i)));
	    	 core_2+=(100-(p_2.get(i)));
	    	 count++;
	    	 if(count==interval){
	    		 avg_1.add(core_1/count);
	    		 avg_2.add(core_2/count);
	    		 core_1=0;core_2=0;count=0;
	    		 }
	     }
	     avg_1.add(core_1/count);
		 avg_2.add(core_2/count);
		 cycleData[0]=avg_1;
         cycleData[1]=avg_2;	
         return cycleData;
	}

}
