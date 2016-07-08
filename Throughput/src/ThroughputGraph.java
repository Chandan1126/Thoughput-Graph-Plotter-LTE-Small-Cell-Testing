
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
public class ThroughputGraph extends ApplicationFrame {
    static  int l2_count;
	static String l2_path;
    static String format;

	public ThroughputGraph(final String title,String l2_path,int l2_count,String format) throws IOException {
       super(title);
       this.format=format;
        this.l2_path=l2_path;
        this.l2_count=l2_count;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        setLocationByPlatform(true);
        pack();
        //RefineryUtilities.centerFrameOnScreen(this);
       setVisible(true);


    }
    
    private XYDataset createDataset() throws IOException {
    	ArrayList[] thr =new ManipulateFiles().getThr(l2_path,l2_count);
    	ArrayList<Float> dl=thr[0];
        ArrayList<Float> ul=thr[1];
        if(format.equalsIgnoreCase("Hour")){
        	l2_count=l2_count/360;
        }
        else  if(format.equalsIgnoreCase("Min")){
        	l2_count=l2_count/6;
        }
        else  if(format.equalsIgnoreCase("Sec")){
        	l2_count=l2_count;
        }
         XYSeries series1 = new XYSeries("Downlink");
         XYSeries series2 = new XYSeries("Uplink");
         XYSeries series3 = new XYSeries("Total");
        for(int i=0;i<dl.size();i++){
        series1.add((i+1)*l2_count, dl.get(i));
        series2.add((i+1)*l2_count, ul.get(i));
        series3.add((i+1)*l2_count,dl.get(i)+ul.get(i));
        } 
         XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
                
        return dataset;
        
    }
    
   
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "DL-UL Throughput",      // chart title
            "Time("+format+")",                      // x axis label
            "Throughput(Mbps)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

    //    final StandardLegend legend = (StandardLegend) chart.getLegend();
    //  legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
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

