package de.dhbw.contents;
import de.dhbw.database.*;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidplot.Plot;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import de.dhbw.container.R;

public class EvaluationDiagrammFragment extends SherlockFragment{
		private Context mContext;
		private XYPlot plot;
		private DataBaseHandler db;
	
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }
	 
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	 
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.evaluation_diagramm_fragment, container, false);
			mContext = getActivity();
			db = new DataBaseHandler(mContext);
	        
	        Number[] days =   { 1  , 2   , 3   , 4   , 5   , 6   , 7 };
	        Number[] values = { 380, 143, 196, 320, 361, 321, 321 };
	        
	        // initialize our XYPlot reference:
	        plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);
	        
	        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);
	        plot.setPlotMargins(0, 0, 0, 0);
	        plot.setPlotPadding(0, 0, 0, 0);
	        plot.setGridPadding(0, 10, 5, 0);

	        plot.setBackgroundColor(Color.WHITE);

	        plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
	        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);

	        plot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
	        plot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);

	        plot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.BLACK);
	        plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
	        plot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);

	        // Domain
	        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, values.length);     
	        plot.setDomainValueFormat(new DecimalFormat("0"));
	        plot.setDomainStepValue(1);

	        //Range
	        plot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
	        plot.setRangeStepValue(10);
	        //mySimpleXYPlot.setRangeStep(XYStepMode.SUBDIVIDE, values.length);
	        plot.setRangeValueFormat(new DecimalFormat("0"));

	        //Remove legend
	        plot.getLayoutManager().remove(plot.getDomainLabelWidget());
	        plot.getLayoutManager().remove(plot.getRangeLabelWidget());
	        plot.getLayoutManager().remove(plot.getTitleWidget());
	        
	        
	        // Turn the above arrays into XYSeries':
	        XYSeries series1 = new SimpleXYSeries(
	        		Arrays.asList(values),          
	        		SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, 
	                "Hoehenmeter");                             // Set the display title of the series

	        // Create a formatter to use for drawing a series using LineAndPointRenderer:
	        LineAndPointFormatter series1Format = new LineAndPointFormatter(
	                Color.rgb(0, 200, 0),                   // line color
	                Color.rgb(0, 100, 0),                   // point color
	                Color.GREEN, null);                      // fill color 

	        // setup our line fill paint to be a slightly transparent gradient:
	        Paint lineFill = new Paint();
	        lineFill.setAlpha(200);
	        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));

	        series1Format.setFillPaint(lineFill);

	        // add a new series' to the xyplot:
	        plot.addSeries(series1, series1Format);

	        return view;
	    }
	    
//	    public Number[] getAltitudeSeries(DataBaseHandler db){
//	    	List <Coordinates> coordinates = db.getAllCoordinatePairs();
//	    	Number [] altitudes = {};
//	    	for (int i = 0; i<coordinates.size(); i++){
//	    		double d = coordinates.get(i).get_altitude();
//	    		altitudes[i] = Math.round(d);
//	    	}
//	    	return altitudes;
//	    }
	    
	    
}