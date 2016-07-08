import java.io.File;
import javax.swing.JFileChooser;
public class FileChooser {
	 File choose_file() {
		 File dir=null;
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Select File");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    //  System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
	     //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
	    	 dir=chooser.getSelectedFile();
	        
	    } else {
	      System.out.println("No Selection ");
	    }
     return dir;
	 }
}
