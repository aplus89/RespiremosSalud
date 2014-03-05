package cr.gov.respiremossalud;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.SaveCallback;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import cr.gov.respiremossalud.fragments.InfoFragment;
import cr.gov.respiremossalud.model.User;

public class RedisenoActivity extends SherlockActivity {

	private Button loginButton;
	private Dialog progressDialog;
	private Button buttonNo;
	private Button buttonEx;
	private Button buttonSi;
	private Button buttonDej;
	private int userTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rediseno);
		
		getSupportActionBar().hide();
		
		
		
//		PushService.setDefaultPushCallback(this, RedisenoActivity.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();
//		
//		ParseAnalytics.trackAppOpened(getIntent());
//		
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		
	}
	

}
