import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import java.util.ArrayList;
public class ExcelWriter {
	
	int generateExcel(String l2_path,String l2_interval,String cycle_path,String cycle_interval,String output_path,int filecount,String format_l2,String format_c) throws FileNotFoundException, IOException {
		int l2_count=Integer.parseInt(l2_interval);
		int cycle_count=Integer.parseInt(cycle_interval);
		String f_l2=format_l2;String f_c=format_c;
		int l2_d=0,cycle_d=0;
		ArrayList[] thr =new ManipulateFiles().getThr(l2_path,l2_count);
		ArrayList[] cycle =new CycleSoak().getSoak(cycle_path,cycle_count);
		HSSFWorkbook workbook = new HSSFWorkbook();
		int flag=0;
		if(f_l2.equalsIgnoreCase("Hour")){
			l2_d=l2_count/360;
		}
		else if(f_l2.equalsIgnoreCase("Min")){
			l2_d=l2_count/6;
		}
		else if(f_l2.equalsIgnoreCase("Sec")){
			l2_d=l2_count*10;
		}
		if (f_c.equalsIgnoreCase("Hour")){
			cycle_d=cycle_count/1200;
		}
		else if (f_c.equalsIgnoreCase("Min")){
			cycle_d=cycle_count/20;
		}
		else if (f_c.equalsIgnoreCase("Sec")){
			cycle_d=cycle_count*3;
		}
		
		HSSFSheet worksheet1 = workbook.createSheet("DL-UL Throughput");
		HSSFSheet worksheet2 = workbook.createSheet("CPU Utilization");
		HSSFRow row = worksheet1.createRow(0);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		//cellStyle.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell((short) 0);
        ArrayList<Float> dl=thr[0];
        ArrayList<Float> ul=thr[1];
        ArrayList<Float> c0=cycle[0];
        ArrayList<Float> c1=cycle[1];
        row = worksheet1.createRow(0);
        cell = row.createCell((short)0);
        cell.setCellValue("Time("+format_l2+")");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)1);
        cell.setCellValue("Downlink");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)2);
        cell.setCellValue("Uplink");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)3);
        cell.setCellValue("Total");
        cell.setCellStyle(cellStyle);
        int rownum = 1;
        for(int i=0;i<dl.size();i++){
         row = worksheet1.createRow(rownum);
         cell = row.createCell((short)0);
         cell.setCellStyle(cellStyle);
         cell.setCellValue((float)rownum*l2_d);
         cell = row.createCell((short)1);
         cell.setCellStyle(cellStyle);
         cell.setCellValue(dl.get(i));
         cell = row.createCell((short)2);
         cell.setCellStyle(cellStyle);
         cell.setCellValue(ul.get(i));  
         cell = row.createCell((short)3);
         cell.setCellStyle(cellStyle);
         cell.setCellValue(ul.get(i)+dl.get(i));
         rownum++;
        }
      ////////////////////Cyclesoak//////////////
        row = worksheet2.createRow(0);
        cell = row.createCell((short)0);
        cell.setCellValue("Time("+format_c+")");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)1);
        cell.setCellValue("Core-0");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)2);
        cell.setCellValue("Core-1");
        cell.setCellStyle(cellStyle);
        cell = row.createCell((short)3);
        cell.setCellValue("Total");
        cell.setCellStyle(cellStyle);
         rownum = 1;
        for(int i=0;i<c0.size();i++){
            row = worksheet2.createRow(rownum);
            cell = row.createCell((short)0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((float)rownum*cycle_d);
            cell = row.createCell((short)1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(c0.get(i));
            cell = row.createCell((short)2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(c1.get(i));  
            cell = row.createCell((short)3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((c1.get(i)+c0.get(i))/2); 
            rownum++;
           }
        try {
        	String file_o=output_path + "/chandan-"+filecount+".xls";
            FileOutputStream out =
                    new FileOutputStream(new File(file_o));
            workbook.write(out);
            out.close();
            //System.out.println("Excel written successfully..");
             flag=1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return flag;	
}

}