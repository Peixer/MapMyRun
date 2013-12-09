package de.dhbw.tracking;

import java.util.ArrayList;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	//Liste der Elemente die über die Karte gelegt werden
	private ArrayList <OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	 
	 public MyItemizedOverlay(Drawable pDefaultMarker,
	   ResourceProxy pResourceProxy) {
	  super(pDefaultMarker, pResourceProxy);
	 }
	 //Fuegt Elemente der (overlayItemList-)Liste zurucek
	 public void addItem(GeoPoint p, String title, String snippet){
	  OverlayItem newItem = new OverlayItem(title, snippet, p);
	  overlayItemList.add(newItem);
	  populate();
	 }
	 
	 @Override
	 public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
	  return false;
	 }
	 
	 //Erstellt neues Element das über die Karte gelegt wird
	 @Override
	 protected OverlayItem createItem(int arg0) {
	  return overlayItemList.get(arg0);
	 }
	 
	 //Gibt Anzahl der Elemente in der (overlayItemList-)Liste zuruck
	 @Override
	 public int size() {
	  return overlayItemList.size();
	 }
}
