package de.dhbw.contents;
import java.text.DecimalFormat;
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
import de.dhbw.database.Coordinates;
import de.dhbw.database.DataBaseHandler;

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
			//Diagramm erstellen
			plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);
			
			//XY Koordinatenachsen formatieren
			formatXYBorderDisplay(plot);
			
			// X Werte (Anzahl Seehoehenmessungen) konfiguriren 
			configXSeries(plot);
			
			//Y Werte (Seehoehe) konfigurieren
			configYSeries(plot);

	        //Legende entfernen
			removeLegend(plot);
	        
	        // Werte fuer X und Y Achsen festlegen
	        XYSeries altitudeSeries = defineXYValues();
	        
	        // Diagramm mit Daten befuellen
	        plot.addSeries(altitudeSeries, formatDiagramm());

	        return view;
	    }
	    
	    //liefert Seehoehendaten als Liste zur Anzeige als Y Werte im Diagramm
	    public Number[] getAltitudeSeries(DataBaseHandler db){
	    	List <Coordinates> coordinates = db.getAllCoordinatePairs();
	    	Number [] altitudes = new Number[coordinates.size()];
	    	for (int i = 0; i<(coordinates.size()-1); i++){
	    		double d = coordinates.get(i).get_altitude();
	    		altitudes[i] = Math.round(d);
	    	}
	    	return altitudes;
	    }
	    
	    //XY Koordinatenachsen formatieren
	    public void formatXYBorderDisplay(XYPlot plot){
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
	    }
	    
	    
	    // X Werte (Anzahl Seehoehenmessungen) konfiguriren 
	    public void configXSeries(XYPlot plot){
	        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, getAltitudeSeries(db).length);     
	        plot.setDomainValueFormat(new DecimalFormat("0"));
	        plot.setDomainStepValue(1);
	    }
	    
	    //Y Werte (Seehoehe) konfigurieren
	    public void configYSeries(XYPlot plot){
	        plot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
	        plot.setRangeStepValue(10);
	        plot.setRangeValueFormat(new DecimalFormat("0"));
	    }
	    
	    //Legende entfernen
	    public void removeLegend(XYPlot plot){
	    	plot.getLayoutManager().remove(plot.getDomainLabelWidget());
	        plot.getLayoutManager().remove(plot.getRangeLabelWidget());
	        plot.getLayoutManager().remove(plot.getTitleWidget());	
	    }
	    
	    // Werte fuer X und Y Achsen festlegen
		public XYSeries defineXYValues() {
			XYSeries altitudeSeries = new SimpleXYSeries(
					Arrays.asList(getAltitudeSeries(db)),
					SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Hoehenmeter"); // Seehohe als Titel
			return altitudeSeries;
		}
		
		//Diagrammlinien und -füllfarben festelgen
		public LineAndPointFormatter formatDiagramm(){
			LineAndPointFormatter series1Format = new LineAndPointFormatter(
		               Color.rgb(0, 200, 0),                   // Lienienfarbe
		               Color.rgb(0, 100, 0),                   // Punktfarbe
		               Color.GREEN, null);                      // Füllungsfarbe
	
	        // Fuellungsfarbe leicht transparent 
	        Paint lineFill = new Paint();
	        lineFill.setAlpha(200);
	        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));
	
	        series1Format.setFillPaint(lineFill);
	        return series1Format;
		}
	    
}