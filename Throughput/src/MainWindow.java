import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
public class MainWindow  implements ActionListener{
	static int Graph_no=0;
	static int Excel_no=0;
	static JFileChooser chooser = new JFileChooser();
	static JFrame main_window=new JFrame("Throughput Graph Plot");
	static int flag=0;
	int l2_timer=0;
	int cycle_timer=0;
	static File path;
	JTextField cycle_path=new JTextField("Path of Cyclsoak");
	JTextField interval_cyclesoak=new JTextField("10");
	JTextField interval_l2console=new JTextField("10");
	JTextField l2console_path=new JTextField("Path of L2 Console");
	JTextField output_path=new JTextField("Path of Output File");
	JButton close=new JButton("Close");
	private ThroughputGraph throughputGraph;
	private CpuUtilizationGraph cpuUtilizationGraph;
	String[] format_list={"Select Time Format","Hour","Min","Sec"};
	JComboBox interval_format_cycle = new JComboBox(format_list);
	JComboBox interval_format_l2 = new JComboBox(format_list);
	JProgressBar pbar;
	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;
	/**********cunstructor************/
	public MainWindow(){
	main_window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	//main_window.setSize(450, 250);
	main_window.setLocationByPlatform(true);
	main_window.setPreferredSize(new Dimension(600, 250));
	main_window.setResizable(false);
	main_window.pack();
	cycle_path.enable(false);
	JButton Browse_cyclesoak=new JButton("Browse cyclsoak");
	Browse_cyclesoak.addActionListener(this);
	JButton Browse_l2console=new JButton("Browse L2console");
	Browse_l2console.addActionListener(this);
	JButton Browse_output=new JButton("Save File at");
	Browse_output.addActionListener(this);
	JButton Generate_excel=new JButton("Generate Excel");
	JButton graph=new JButton("Plot Graph");
	graph.addActionListener(this);
	interval_format_cycle.setSelectedIndex(0);
	interval_format_cycle.addActionListener(this);
	interval_format_l2.setSelectedIndex(0);
	interval_format_l2.addActionListener(this);
	Generate_excel.addActionListener(this);
	close.addActionListener(this);
	JLabel FCL=new JLabel("Browse cyclesock File ",SwingConstants.CENTER);
	JLabel IL=new JLabel("Interval Size For Cyclesoak",SwingConstants.RIGHT);
	JLabel L2L=new JLabel("Browse L2 Console File",SwingConstants.CENTER);
	JLabel ILC=new JLabel("Interval Size For L2Console",SwingConstants.CENTER);
	JLabel OL=new JLabel("Path For Output File",SwingConstants.CENTER);
	JLabel CC=new JLabel("Developed By : chandan6.kumar@aricent.com",SwingConstants.CENTER);
	l2console_path.enable(false);
	output_path.enable(false);
	JPanel JP1=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP2=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP3=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP4=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP5=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP6=new JPanel(new GridLayout(1,0,2,1));
	JPanel JP7=new JPanel(new GridLayout(1,0,2,1));
	JP1.add(FCL);JP1.add(cycle_path);JP1.add(Browse_cyclesoak);
	JP2.add(IL);JP2.add(interval_cyclesoak);JP2.add(interval_format_cycle);
	JP3.add(L2L);JP3.add(l2console_path);JP3.add(Browse_l2console);
	JP4.add(ILC);JP4.add(interval_l2console);JP4.add(interval_format_l2);
	JP5.add(OL);JP5.add(output_path);JP5.add(Browse_output);
	JP6.add(Generate_excel);JP6.add(graph);JP6.add(close);
	JP7.add(CC);
	pbar = new JProgressBar();
    pbar.setMinimum(MY_MINIMUM);
    pbar.setMaximum(MY_MAXIMUM);
	main_window.setLayout(new FlowLayout());
	main_window.add(JP1);main_window.add(JP2);main_window.add(JP3);main_window.add(JP4);
	main_window.add(JP5);main_window.add(JP6);main_window.add(JP7);
	main_window.setLocationRelativeTo(null);
	main_window.setVisible(true);
	
	}
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		String action=e.getActionCommand();
		if(e.getSource()==interval_format_cycle){
			String format=(String)interval_format_cycle.getSelectedItem();
			if(format.equalsIgnoreCase("Hour")){
				float temp=Float.valueOf(interval_cyclesoak.getText())*3600;
				cycle_timer=(int)temp/3;
				System.out.println(cycle_timer);
			}
			else if(format.equalsIgnoreCase("Min")){
				float temp=Float.valueOf(interval_cyclesoak.getText())*60;
				cycle_timer=(int)temp/3;
				System.out.println(cycle_timer);
			}
			else if(format.equalsIgnoreCase("Sec")){
				float temp=Float.valueOf(interval_cyclesoak.getText());
				cycle_timer=(int)temp/3;
				System.out.println(cycle_timer);
			}
		}
		if(e.getSource()==interval_format_l2){
			String format=(String)interval_format_l2.getSelectedItem();
			if(format.equalsIgnoreCase("Hour")){
				float temp=Float.valueOf(interval_l2console.getText())*3600;
				l2_timer=(int)temp/10;
				System.out.println(l2_timer);
			}
			else if(format.equalsIgnoreCase("Min")){
				float temp=Float.valueOf(interval_l2console.getText())*60;
				l2_timer=(int)temp/10;
				System.out.println(l2_timer);
			}
			else if(format.equalsIgnoreCase("Sec")){
				float temp=Float.valueOf(interval_l2console.getText());
				l2_timer=(int)temp/10;
				System.out.println(l2_timer);
			}
		}
		
		if (action.equals("Browse cyclsoak")){
			chooser.setSelectedFile(path);
		    //chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select Cyclesoak File");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
            path=chooser.getSelectedFile();
		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		   cycle_path.setText(chooser.getSelectedFile().getAbsolutePath());
		        
		}
		
	}
		else if (action.equals("Browse L2console")){
		//	JFileChooser chooser = new JFileChooser();
			
				chooser.setSelectedFile(path);
			
		    //chooser.setCurrentDirectory(new java.io.File("."));
			
		    chooser.setDialogTitle("Select L2 Console File");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		   l2console_path.setText(chooser.getSelectedFile().getAbsolutePath());
		   //System.out.println(l2console_path.getText());     
		}
		}
		else if (action.equals("Save File at")){
			chooser.setSelectedFile(path);
		   // chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select A directory to save Excel File");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    output_path.setText(chooser.getSelectedFile().getAbsolutePath());
		    //System.out.println(output_path.getText());
			
		        
		}
		
	}
		else if (action.equals("Close")){
     main_window.dispose();
	}
		else if (action.equals("Plot Graph")){
			Graph_no++;
			String l2=l2console_path.getText();
			String cycle=cycle_path.getText();
			String cycle_interval=interval_cyclesoak.getText();
			String l2_interval=interval_l2console.getText();
			String Title_cycle="CPU Utilization-"+String.valueOf(Graph_no);
			String Title_l2="Throughput-"+String.valueOf(Graph_no);
			String format_l2=(String)interval_format_l2.getSelectedItem();
			String format_c=(String)interval_format_cycle.getSelectedItem();
			String format=(String)interval_format_cycle.getSelectedItem();
			String format_1=(String)interval_format_l2.getSelectedItem();
			 if (cycle_path.getText().equalsIgnoreCase("Path of Cyclsoak"))
				JOptionPane.showMessageDialog(main_window, "Please Select cyclesoak file!!");
			 else if(l2console_path.getText().equalsIgnoreCase("Path of L2 Console"))
				JOptionPane.showMessageDialog(main_window, "Please Select L2 Console file!!");
			else if (output_path.getText().equalsIgnoreCase("Path of Output File"))
				JOptionPane.showMessageDialog(main_window, "Please provide directory for Excel file !!");
			else if(interval_cyclesoak.getText().equalsIgnoreCase("0"))
				JOptionPane.showMessageDialog(main_window, "Interval should be greater than 0 !!");
			else if(interval_l2console.getText().equalsIgnoreCase("0"))
				JOptionPane.showMessageDialog(main_window, "Interval should be greater than 0 !!");
			else if(interval_cyclesoak.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(main_window, "Please Enter Interval Size  !!");
			else if(interval_l2console.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(main_window, "Please Enter Interval Size !!");
			else if(format.equalsIgnoreCase("Select Time Format"))
				JOptionPane.showMessageDialog(main_window, "Please Choose a time format !!");
			else if(format_1.equalsIgnoreCase("Select Time Format"))
				JOptionPane.showMessageDialog(main_window, "Please Choose a time format !!");
			else{
			
				try {
					cpuUtilizationGraph = new CpuUtilizationGraph("CPU Utilization",cycle,cycle_timer,format_c);
					throughputGraph = new ThroughputGraph("THroughput",l2,l2_timer,format_l2);
					
					// new CpuUtilizationGraph(Title_cycle,cycle, Integer.parseInt(cycle_interval));
					 //new ThroughputGraph(Title_l2,l2,Integer.parseInt(l2_interval));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			}
		else if (action.equals("Generate Excel")){
			Excel_no++;
			String format=(String)interval_format_cycle.getSelectedItem();
			String format_1=(String)interval_format_l2.getSelectedItem();
			 if (cycle_path.getText().equalsIgnoreCase("Path of Cyclsoak"))
				JOptionPane.showMessageDialog(main_window, "Please Select cyclesoak file!!");
			 else if(l2console_path.getText().equalsIgnoreCase("Path of L2 Console"))
				JOptionPane.showMessageDialog(main_window, "Please Select L2 Console file!!");
			else if (output_path.getText().equalsIgnoreCase("Path of Output File"))
				JOptionPane.showMessageDialog(main_window, "Please provide directory for Excel file !!");
			else if(interval_cyclesoak.getText().equalsIgnoreCase("0"))
				JOptionPane.showMessageDialog(main_window, "Interval should be greater than 0 !!");
			else if(interval_l2console.getText().equalsIgnoreCase("0"))
				JOptionPane.showMessageDialog(main_window, "Interval should be greater than 0 !!");
			else if(interval_cyclesoak.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(main_window, "Please Enter Interval Size  !!");
			else if(interval_l2console.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(main_window, "Please Enter Interval Size !!");
			else if(format.equalsIgnoreCase("Select Time Format"))
				JOptionPane.showMessageDialog(main_window, "Please Choose a time format !!");
			else if(format_1.equalsIgnoreCase("Select Time Format"))
				JOptionPane.showMessageDialog(main_window, "Please Choose a time format !!");
			else{
				String format_l2=(String)interval_format_l2.getSelectedItem();
				String format_c=(String)interval_format_cycle.getSelectedItem();
		String l2=l2console_path.getText();
		String cycle=cycle_path.getText();
		//String cycle_interval=interval_cyclesoak.getText();
		//String l2_interval=interval_l2console.getText();
		String out_path=output_path.getText();
		String l2_interval=String.valueOf(l2_timer);
		String cycle_interval=String.valueOf(cycle_timer);
		try {
			flag=new ExcelWriter().generateExcel(l2, l2_interval,cycle, cycle_interval,out_path,Excel_no,format_l2,format_c);
			if (flag==1){
			JOptionPane.showMessageDialog(main_window, "Excel written successfully..");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} }
	}	
		
	}
	public static void main(String[] args){
		new MainWindow();
		
	}
}
