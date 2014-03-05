package cr.gov.respiremossalud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cr.gov.respiremossalud.model.Locationh;

public class DenunciaActivity extends SherlockFragmentActivity implements OnMarkerClickListener {

	private SupportMapFragment map;
	private ArrayList<Locationh> locales;
	private HashMap<String, ParseObject> places_marker_map = new HashMap<String, ParseObject>();
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denuncia);
		
		map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
		
		LatLng latlng = new LatLng(9.6843424,-84.2706638);
		map.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 9));
		map.getMap().setMyLocationEnabled(true);
		
		getLocales();
		
	}

	private void getLocales() {
		 progressDialog = ProgressDialog.show(this, "", "Obteniendo areas de salud...", true);

//		ParseGeoPoint currentLocation = new ParseGeoPoint(currentLatLng.latitude, currentLatLng.longitude);
//		locales = new ArrayList<Locationh>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Locationh.CLASS);
		query.whereExists("geo");
//		query.whereNear(Parqueo.GEO, currentLocation);
//		query.setLimit(10);
//		query.orderByAscending("nombre");
		query.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> list, ParseException e) {
				progressDialog.dismiss();
				if (e == null) {
		    			
		    			drawMarkers(list);
		    			
//		            for(ParseObject pParqueo: pParqueos){
//		            		Parqueo parqueo = new Parqueo(pParqueo);
//		            		parqueos.add(parqueo);
//		            }
//		            dbOperations.insertParqueos(parqueos);
//		            drawData();
		            
		            
		        } else {
		            Log.e("Parse", "Error: " + e.getMessage());
		        }
		    }
			
		});
		
	}

	protected void drawMarkers(List<ParseObject> list) {
			
			GoogleMap theMap = map.getMap();
			
			theMap.setOnMarkerClickListener(this);
			
			if (theMap!= null){
				for (ParseObject area : list) {
					ParseGeoPoint geo = area.getParseGeoPoint("geo");
					LatLng latlng = new LatLng(geo.getLatitude(), geo.getLongitude());
					Marker marker = theMap.addMarker(new MarkerOptions()
						.position(latlng)
						.title(area.getString("nombre"))
						.snippet(area.getString("telefono1"))
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
					
					
					places_marker_map.put(marker.getId(), area);
				}
			}

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setCustomView(R.layout.denuncia_menu);
		actionBar.setDisplayShowCustomEnabled(true);
				
		return true;

	}
	
}
