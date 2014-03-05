package cr.gov.respiremossalud;

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cr.gov.respiremossalud.model.User;

public class CuandoActivity extends SherlockActivity implements OnClickListener {

	private DatePicker datePicker;
	private TimePicker timePicker;
	private Button nextButton;
	private ParseUser currentUser;
	private int userTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cuando);
//		getSupportActionBar().setTitle(getString(R.string.title_cuando_dejaste));
		currentUser = ParseUser.getCurrentUser();
		userTipo = currentUser.getInt(User.TYPE);
		
		datePicker = (DatePicker) findViewById(R.id.datePicker);
//		timePicker = (TimePicker) findViewById(R.id.timePicker);
		nextButton = (Button) findViewById(R.id.nextButton);
		
		nextButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.nextButton:
			saveAndNext();
			break;

		default:
			break;
		}
	}

	private void saveAndNext() {
		int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);

	    Date date =  calendar.getTime();
	    
		currentUser.put("sinFumar", date);
		currentUser.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				showNextActivity();
			}
		});
	}
	
	protected void showNextActivity() {
		
			showHomeActiviy();

}
	private void showHomeActiviy() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setCustomView(R.layout.cuando_menu);
		actionBar.setDisplayShowCustomEnabled(true);
				
		return true;

	}
	
}
