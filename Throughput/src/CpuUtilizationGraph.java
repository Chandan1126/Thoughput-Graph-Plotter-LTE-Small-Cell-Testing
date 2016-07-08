
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.Spacer;
public class CpuUtilizationGraph extends ApplicationFrame {
   static String cycle_path;
	static int count;
    static String format;
    public CpuUtilizationGraph(String title, String cycle, int parseInt,String format) throws IOException {
    	 super(title);
    	 this.format=format;
         this.cycle_path=cycle;
         this.count=parseInt;
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          final XYDataset dataset = createDataset();
          final JFreeChart chart = createChart(dataset);
          final ChartPanel chartPanel = new ChartPanel(chart);
          chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
          setContentPane(chartPanel);
          pack();
          setLocationByPlatform(true);
         // RefineryUtilities.centerFrameOnScreen(this);
         // RefineryUtilities.
         setVisible(true);

	}

	private XYDataset createDataset() throws IOException {
    
		ArrayList[] cycle =new CycleSoak().getSoak(cycle_path,count);
    	ArrayList<Float> c0=cycle[0];
        ArrayList<Float> c1=cycle[1];
        if(format.equalsIgnoreCase("Hour")){
        	count=count/1200;
        }
        else if(format.equalsIgnoreCase("Min")){
        	count=count/20;
        }
        else if(format.equalsIgnoreCase("Sec")){
        	count=count;
        }
        final XYSeries series1 = new XYSeries("Core-0");
        final XYSeries series2 = new XYSeries("Core-1");
        final XYSeries series3 = new XYSeries("Total");
        for(int i=0;i<c0.size();i++){
        series1.add((i+1)*count, c0.get(i));
        series2.add((i+1)*count, c1.get(i));
        series3.add((i+1)*count,(c0.get(i)+c1.get(i))/2);
        } 
          
     //   final XYSeries series2 = new XYSeries("Uplink");
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
                
        return dataset;
        
    }
    
   
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "CPU Utilization",      // chart title
            "Time("+format+")",                      // x axis label
            "Utilization(%)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            true                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

    //    final StandardLegend legend = (StandardLegend) chart.getLegend();
    //  legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
         XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, true);
        plot.setRenderer(renderer);
        

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

}

