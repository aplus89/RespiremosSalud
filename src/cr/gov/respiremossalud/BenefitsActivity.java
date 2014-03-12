package cr.gov.respiremossalud;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseUser;

import cr.gov.respiremossalud.fragments.EFragment;
import cr.gov.respiremossalud.model.User;

public class BenefitsActivity extends EFragment implements OnChronometerTickListener {

	private Chronometer cronometro;
	private ParseUser currentUser;
	private Date sinFumar;

	
	private void setProgress20min(int minutes) {
		int total = 20;
		int percent = (minutes*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje20min.setText(percent+"%");
		this.progress20min.setProgress(percent);
	}

	private void setProgress8hr(int hours) {
		int total = 8;
		int percent = (hours*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje8hr.setText(percent+"%");
		this.progress8hr.setProgress(percent);
	}

	private void setProgress48hr(int hours) {
		int total = 48;
		int percent = (hours*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje48hr.setText(percent+"%");
		this.progress48hr.setProgress(percent);
	}

	private void setProgress72hr(int hours) {
		int total = 72;
		int percent = (hours*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje72hr.setText(percent+"%");
		this.progress72hr.setProgress(percent);
	}

	private void setProgress6mes(int dias) {
		int total = 6*30;
		int percent = (dias*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje6mes.setText(percent+"%");
		this.progress6mes.setProgress(percent);
	}

	private void setProgress1yr(int dias) {
		int total = 365;
		int percent = (dias*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje1yr.setText(percent+"%");
		this.progress1yr.setProgress(percent);
	}

	private void setProgress4yr(int dias) {
		int total = 365*4;
		int percent = (dias*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje4yr.setText(percent+"%");
		this.progress4yr.setProgress(percent);
	}

	private void setProgress10yr(int dias) {
		int total = 365*10;
		int percent = (dias*100)/total;
		if (percent>=100) {
			percent = 100;
		}
		porcentaje10yr.setText(percent+"%");
		this.progress10yr.setProgress(percent);
	}

	private ProgressBar progress20min;
	private ProgressBar progress8hr;
	private ProgressBar progress48hr;
	private ProgressBar progress72hr;
	private ProgressBar progress6mes;
	private ProgressBar progress1yr;
	private ProgressBar progress4yr;
	private ProgressBar progress10yr;
	private TextView porcentaje20min;
	private TextView porcentaje8hr;
	private TextView porcentaje48hr;
	private TextView porcentaje72hr;
	private TextView porcentaje6mes;
	private TextView porcentaje1yr;
	private TextView porcentaje4yr;
	private TextView porcentaje10yr;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.benefits, null);
//		getSupportActionBar().setTitle(getString(R.string.title_beneficios));
		currentUser = ParseUser.getCurrentUser();
		cronometro = (Chronometer) view.findViewById(R.id.cronometro);
		
		cronometro.setOnChronometerTickListener(this);
		
		progress20min = (ProgressBar) view.findViewById(R.id.progress20min);
		progress8hr = (ProgressBar) view.findViewById(R.id.progress8hr);
		progress48hr = (ProgressBar) view.findViewById(R.id.progress48hr);
		progress72hr = (ProgressBar) view.findViewById(R.id.progress72hr);
		progress6mes = (ProgressBar) view.findViewById(R.id.progress6mes);
		progress1yr = (ProgressBar) view.findViewById(R.id.progress1yr);
		progress4yr = (ProgressBar) view.findViewById(R.id.progress4yr);
		progress10yr = (ProgressBar) view.findViewById(R.id.progress10yr);
		
		porcentaje20min = (TextView) view.findViewById(R.id.porcentaje20min);
		porcentaje8hr = (TextView) view.findViewById(R.id.porcentaje8hr);
		porcentaje48hr = (TextView) view.findViewById(R.id.porcentaje48hr);
		porcentaje72hr = (TextView) view.findViewById(R.id.porcentaje72hr);
		porcentaje6mes = (TextView) view.findViewById(R.id.porcentaje6mes);
		porcentaje1yr = (TextView) view.findViewById(R.id.porcentaje1yr);
		porcentaje4yr = (TextView) view.findViewById(R.id.porcentaje4yr);
		porcentaje10yr = (TextView) view.findViewById(R.id.porcentaje10yr);
		
//		R.id.te
		

		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
//			updateViewsWithProfileInfo();
			showBenefits();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
			
		}
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		currentUser = ParseUser.getCurrentUser();
//		ParseUser currentUser = ParseUser.getCurrentUser();
//		if (currentUser != null) {
//			// Check if the user is currently logged
//			// and show any cached content
//			updateViewsWithProfileInfo();
//		} else {
//			// If the user is not logged in, go to the
//			// activity showing the login view.
//			startLoginActivity();
//		}
	}


	private void showBenefits() {
		
		
		if (currentUser.get(User.SIN_FUMAR) != null) {
			
			sinFumar = currentUser.getDate(User.SIN_FUMAR);
			Log.d(RespiremosSalud.TAG+" sinFumar: ", sinFumar.toString());
			
			long lastSuccess = sinFumar.getTime(); //Some Date object
			long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
			cronometro.setBase(lastSuccess - elapsedRealtimeOffset);
			cronometro.start();
			
			
//			JSONObject userProfile = currentUser.getJSONObject("profile");
//			try {
//				if (userProfile.getString("facebookId") != null) {
//					String facebookId = userProfile.get("facebookId")
//							.toString();
//					userProfilePictureView.setProfileId(facebookId);
//				} else {
//					// Show the default, blank user profile picture
//					userProfilePictureView.setProfileId(null);
//				}
//				if (userProfile.getString("name") != null) {
//					userNameView.setText(userProfile.getString("name"));
//				} else {
//					userNameView.setText("");
//				}
//				if (userProfile.getString("location") != null) {
//					userLocationView.setText(userProfile.getString("location"));
//				} else {
//					userLocationView.setText("");
//				}
//				if (userProfile.getString("gender") != null) {
//					userGenderView.setText(userProfile.getString("gender"));
//				} else {
//					userGenderView.setText("");
//				}
//				if (userProfile.getString("birthday") != null) {
//					userDateOfBirthView.setText(userProfile
//							.getString("birthday"));
//				} else {
//					userDateOfBirthView.setText("");
//				}
//				if (userProfile.getString("relationship_status") != null) {
//					userRelationshipView.setText(userProfile
//							.getString("relationship_status"));
//				} else {
//					userRelationshipView.setText("");
//				}
//			} catch (JSONException e) {
//				Log.d(RespiremosSalud.TAG,
//						"Error parsing saved user data.");
//			}

		}
	}

	private void startLoginActivity() {
		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onChronometerTick(Chronometer crono) {
		
//		long myElapsedMillis = SystemClock.elapsedRealtime() - crono.getBase();
//		long seconds = myElapsedMillis/1000;
//		double pricePerSecond = price / 3600;
//		double charge = seconds * pricePerSecond;
//		DecimalFormat df = new DecimalFormat("#.##");
//		CharSequence text = df.format(charge);
////		CharSequence text = "segundos: " + seconds + ", ¢/s:" +pricePerSecond+", cargo:" +charge;
//		txtCharge.setText(cSymbol+text);
		
		Date past=new Date(crono.getBase());
		Date today=new Date(SystemClock.elapsedRealtime());
		
		int days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
		int hours = Hours.hoursBetween(new DateTime(past), new DateTime(today)).getHours();
		int minutes = Minutes.minutesBetween(new DateTime(past), new DateTime(today)).getMinutes();
		
		int hoursOutDays = hours - (days*24);
		int minutesOutHours = minutes - (hours*60);
		
		setProgress20min(minutes);
		setProgress8hr(hours);
		setProgress48hr(hours);
		setProgress72hr(hours);
		setProgress6mes(days);
		setProgress1yr(days);
		setProgress4yr(days);
		setProgress10yr(days);
		
		Log.d(RespiremosSalud.TAG+"contando dias: ", days+"d "+hoursOutDays+"hr "+minutesOutHours+"min");
		
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		ActionBar actionBar = getSupportActionBar();
//	    actionBar.setDisplayShowTitleEnabled(false);
//	    actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setCustomView(R.layout.beneficios_menu);
//		actionBar.setDisplayShowCustomEnabled(true);
//		return true;
//	}

	
}
