import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class ManipulateFiles {
	static ArrayList[] Throughput=new ArrayList[2]; 
	  ArrayList<Float> array_dl=new ArrayList<Float>();
	 ArrayList<Float> array_ul=new ArrayList<Float>();
  ArrayList[] getThr(String file_name,int interval) throws IOException{
	  array_dl.clear();
	  array_ul.clear();
	  String strfile=file_name;
	  File file=new File(strfile);
	  FileInputStream fis = null;
	  float dl_sum=0,ul_sum=0;int countd=0,countu=0;
	  try{
		  
		  fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			for(String line =br.readLine();line!=null;line=br.readLine() ){
				if(line.contains("UL_TP[")){
					countu++;
					String[] cols= line.split("UL_TP");
					String[] actual_cols= cols[1].split("]");
					ul_sum+=Float.parseFloat(actual_cols[0].replace("[",""));
					//System.out.println(actual_cols[0].replace("[", ""));
					if(countu==interval){
						array_ul.add(ul_sum/countu);
						countu=0;
						ul_sum=0;
					}
					}
				else if(line.contains("DL_TP[")){
					countd++;
					String[] cols= line.split("DL_TP");
					String[] actual_cols= cols[1].split("]");
					dl_sum+=Float.parseFloat(actual_cols[0].replace("[",""));
					//System.out.println(actual_cols[0].replace("[", ""));
					if(countd==interval){
						array_dl.add(dl_sum/countd);
						countd=0;
						dl_sum=0;
					}
					}
			}
			array_dl.add(dl_sum/countd);
			array_ul.add(ul_sum/countu);
			Throughput[0]=array_dl;
			Throughput[1]=array_ul;
			br.close();
			
	  } finally {
			try {
				if (fis != null)
					fis.close();
				   
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	  
	  return Throughput;
  }
}
