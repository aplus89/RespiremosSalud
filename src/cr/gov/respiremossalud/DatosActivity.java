package cr.gov.respiremossalud;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cr.gov.respiremossalud.model.User;

public class DatosActivity extends SherlockActivity implements OnClickListener {

	private EditText numeroCigarros;
	private EditText cigarrosEnCaja;
	private EditText precioCaja;
	private ParseUser currentUser;
	private Button nextButton;
	private int userTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datos);
//		getSupportActionBar().setTitle(getString(R.string.title_introduce_datos));
		currentUser = ParseUser.getCurrentUser();
		userTipo = currentUser.getInt(User.TYPE);
		
		numeroCigarros = (EditText) findViewById(R.id.numeroCigarros);
		cigarrosEnCaja = (EditText) findViewById(R.id.cigarrosEnCaja);
		precioCaja = (EditText) findViewById(R.id.precioCaja);
		nextButton = (Button) findViewById(R.id.nextButton);
		
		nextButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.nextButton:
			saveAndNextDatos();
			break;

		default:
			break;
		}
	}

	private void saveAndNextDatos() {
		int numCigarros = 0;
		int cigarrosCaj = 0;
		double precioCaj = 0.0;
		try {
			numCigarros = Integer.parseInt(numeroCigarros.getText().toString());
			cigarrosCaj = Integer.parseInt(cigarrosEnCaja.getText().toString());
			precioCaj = Double.parseDouble(precioCaja.getText().toString());
		} catch (Exception e) {	}
		
		currentUser.put("numCigarros", numCigarros);
		currentUser.put("cigarrosCaj", cigarrosCaj);
		currentUser.put("precioCaj", precioCaj);
		
		if(userTipo == 3){
//			Time now = new Time();
//			now.setToNow();
			currentUser.put("sinFumar", new Date());
		}
		
		currentUser.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				showNextActivity();
			}
		});
		
//		numCigarros = numeroCigarros.getText().toString(); 
		
	}
	
protected void showNextActivity() {
			
			switch (userTipo) {
			case User.TYPE_NO:
				showHomeActiviy();
				break;
			case User.TYPE_EX:
				showCuandoActivity();
				break;
			case User.TYPE_LEAVING:
				showCuandoActivity();
				break;
			case User.TYPE_YES:
				showHomeActiviy();
				break;
			default:
				break;
			}
	}

private void showHomeActiviy() {
	Intent intent = new Intent(this, HomeActivity.class);
	startActivity(intent);		
}
private void showCuandoActivity() {
	Intent intent = new Intent(this, CuandoActivity.class);
	startActivity(intent);
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {

	ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setDisplayShowHomeEnabled(false);
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setCustomView(R.layout.datos_menu);
	actionBar.setDisplayShowCustomEnabled(true);
			
	return true;

}

}
