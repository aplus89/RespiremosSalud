package cr.gov.respiremossalud;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.parse.ParseUser;

import cr.gov.respiremossalud.fragments.HelpSmokers;
import cr.gov.respiremossalud.fragments.ReciveMessagesActivity;
import cr.gov.respiremossalud.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends SherlockFragmentActivity implements OnClickListener, OnChronometerTickListener{
	
	private ParseUser currentUser;
	private int userTipo;
	private LinearLayout beneficiosSection;
	private LinearLayout perfilSection;
	private LinearLayout ayudandoSection;
	private LinearLayout ayudadoSection;
	private LinearLayout denunciaSection;
	private LinearLayout leySection;
	private TextView tiempoText;
	private TextView puntosText;
	private TextView ayudandoText;
	private TextView ayudandomeText;
	private Date sinFumar;
	private Chronometer cronometro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home);
		getSupportActionBar().hide();
		
		beneficiosSection  = (LinearLayout) findViewById(R.id.beneficios);
		perfilSection      = (LinearLayout) findViewById(R.id.perfil);
		ayudandoSection    = (LinearLayout) findViewById(R.id.ayudando);
		ayudadoSection     = (LinearLayout) findViewById(R.id.ayudado);
		denunciaSection    = (LinearLayout) findViewById(R.id.denuncia);
		leySection         = (LinearLayout) findViewById(R.id.ley);
		
		tiempoText = (TextView) findViewById(R.id.tiempo_text);
		puntosText = (TextView) findViewById(R.id.puntos_text);
		ayudandoText = (TextView) findViewById(R.id.ayudando_text);
		ayudandomeText = (TextView) findViewById(R.id.ayudado_text);
		
		cronometro = (Chronometer) findViewById(R.id.cronometro);
		cronometro.setOnChronometerTickListener(this);

		beneficiosSection.setOnClickListener(this);
		perfilSection.setOnClickListener(this);    
		ayudandoSection.setOnClickListener(this);  
		ayudadoSection.setOnClickListener(this);   
		denunciaSection.setOnClickListener(this);  
		leySection.setOnClickListener(this);       
		
		currentUser = ParseUser.getCurrentUser();
		userTipo = currentUser.getInt(User.TYPE);

		if(currentUser!= null){
			getHomeInfo();
		}else {
			startLoginActivity();
		}
		
	}

	private void getHomeInfo() {
		getUserPoints();
		getHelpingFriends();
		Log.d("RS", userTipo+"");
		if(userTipo == User.TYPE_NO){
			beneficiosSection.setVisibility(View.GONE);
		}else{
			getUserTime();
		}
		if(userTipo == User.TYPE_EX && userTipo == User.TYPE_NO){
			ayudadoSection.setVisibility(View.GONE);
		}else{
			getHelpMeFriends();
		}
	}
	
	private void getUserTime() {
if (currentUser.get(User.SIN_FUMAR) != null) {
			
			sinFumar = currentUser.getDate(User.SIN_FUMAR);
			Log.d(RespiremosSalud.TAG+" sinFumar: ", sinFumar.toString());
			
			long lastSuccess = sinFumar.getTime(); //Some Date object
			long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
			cronometro.setBase(lastSuccess - elapsedRealtimeOffset);
			cronometro.start();
}
		
	}

	private void getUserPoints(){
		puntosText.setText(currentUser.getInt("points")+"");
	}
	
	private void getHelpingFriends(){
		
	}
	
	private void getHelpMeFriends(){
		
	}

	private void startLoginActivity() {
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.beneficios:
			showBenefitsActivity();
			break;
		case R.id.perfil:
			showUserActivity();
			break;
		case R.id.ayudando:
			showHelpSmokersActiviy();
			break;
		case R.id.ayudado:
			showReciveActiviy();
			break;
		case R.id.denuncia:
			showDenunciaActivity();
			break;
		case R.id.ley:
			showLey();
			break;
		default:
			break;
		}
		
	}
	
	private void showLey() {
		
	}

	private void showUserActivity() {
		Intent intent = new Intent(this, UserDetailsActivity.class);
		startActivity(intent);
	}
	private void showDenunciaActivity() {
		Intent intent = new Intent(this, DenunciaActivity.class);
		startActivity(intent);
	}
	protected void showHelpSmokersActiviy() {
		Intent intent = new Intent(this, HelpSmokers.class);
		startActivity(intent);
	}
	private void showReciveActiviy() {
		Intent intent = new Intent(this, ReciveMessagesActivity.class);
		startActivity(intent);		
	}
	private void showBenefitsActivity() {
		Intent intent = new Intent(this, BenefitsActivity.class);
		startActivity(intent);
	}

	@Override
	public void onChronometerTick(Chronometer crono) {
		Date past=new Date(crono.getBase());
		Date today=new Date(SystemClock.elapsedRealtime());
		
		int days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
		int hours = Hours.hoursBetween(new DateTime(past), new DateTime(today)).getHours();
		int minutes = Minutes.minutesBetween(new DateTime(past), new DateTime(today)).getMinutes();
		
		int hoursOutDays = hours - (days*24);
		int minutesOutHours = minutes - (hours*60);
		String tiempoFormateado = days+"d "+hoursOutDays+"hr "+minutesOutHours+"min";
		tiempoText.setText(tiempoFormateado);
		
		Log.d(RespiremosSalud.TAG+"contando dias: ", tiempoFormateado);
		
	}
}
