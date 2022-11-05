package cep;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static java.lang.Thread.sleep;

/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a button.
 *
 */
public class TempGraph extends ApplicationFrame {

    /** The time series data. */
    private static TimeSeries series;

    /** The most recent value added. */
    private static double lastValue = 100.0;
private static JButton button;
    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    public TempGraph(final String title) {

        super(title);
        this.series = new TimeSeries("Temperature", Millisecond.class);
        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        final JFreeChart chart = createChart(dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);
       // button = new JButton("Add New Data Item");
       // button.setActionCommand("ADD_DATA");
        //button.addActionListener(this);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
       // content.add(button, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

    }

    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "", 
            "Time", 
            "Temperature",
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 100.0); 
        return result;
    }
    
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Handles a click on the button by adding new (random) data.
     *
     * @param e  the action event.
     */
    public static void func(int x, double y) {
        //System.out.println("JOJO");
            final double factor = 0.90 + 0.2 * Math.random();
          //  lastValue = lastValue * factor;
            lastValue = y;
           // final Millisecond now = new Millisecond();
           // System.out.println("Now = " + now.toString());
           Millisecond temp = new Millisecond();
            series.add(temp, lastValue);
  //      series.mark(temp);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void start() throws InterruptedException {

        final TempGraph demo = new TempGraph("");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
       /* while(true) {
            func(0,0.0);

            sleep(100);
        }*/

    }

}