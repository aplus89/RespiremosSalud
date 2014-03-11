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

public class MainActivity extends SherlockActivity implements OnClickListener {

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
		setContentView(R.layout.main);
		
		getSupportActionBar().hide();
		
		buttonNo = (Button) findViewById(R.id.buttonNo);
		buttonEx = (Button) findViewById(R.id.buttonEx);
		buttonSi = (Button) findViewById(R.id.buttonFumador);
		buttonDej = (Button) findViewById(R.id.buttonDej);
		
		buttonNo.setOnClickListener(this);
		buttonEx.setOnClickListener(this);
		buttonSi.setOnClickListener(this);
		buttonDej.setOnClickListener(this);
		
		// loginButton.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// onLoginButtonClicked();
		// }
		// });
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			// showSendMessagesActiviy();
			// createUserProfile();
//			showUserActivity();
//			showDenunciaActivity();
//			showHelpSmokersActiviy();
//			showReciveActiviy();
//			showInfoFragment();
//			showCuandoActivity();
//			showDatosActivity();
//			showHomeActiviy();
			showNextActivity(false);
//			showBenefitsActivity();
//			 showHelpSmokersActiviy();
		}
	}
	
	private void showReciveActiviy() {
		Intent intent = new Intent(this, ReciveMessagesActivity.class);
		startActivity(intent);		
	}
	private void showHomeActiviy() {
		Intent intent = new Intent(this, RedisenoActivity.class);
		startActivity(intent);
	}

	private void showCuandoActivity() {
		Intent intent = new Intent(this, CuandoActivity.class);
		startActivity(intent);
	}
	private void showDatosActivity() {
		Intent intent = new Intent(this, DatosActivity.class);
		startActivity(intent);
	}
	private void showUserActivity() {
		Intent intent = new Intent(this, UserDetailsActivity.class);
		startActivity(intent);
	}
	private void showInfoFragment() {
		Intent intent = new Intent(this, InfoFragment.class);
		startActivity(intent);
	}
	private void showBenefitsActivity() {
		Intent intent = new Intent(this, BenefitsActivity.class);
		startActivity(intent);
	}
	private void showDenunciaActivity() {
		Intent intent = new Intent(this, DenunciaActivity.class);
		startActivity(intent);
	}

	private void onLoginButtonClicked() {
		 progressDialog = ProgressDialog.show(this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
				"user_relationships", "user_birthday", "user_location",
				Permissions.Friends.ABOUT_ME);
		Log.d(RespiremosSalud.TAG, "Logging in...");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				Log.d(RespiremosSalud.TAG, "Logging done...");
				// MainActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d(RespiremosSalud.TAG,
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d(RespiremosSalud.TAG,
							"User signed up and logged in through Facebook!");
					createUserProfile();
				} else {
					Log.d(RespiremosSalud.TAG,
							"User logged in through Facebook!");
					createUserProfile();
				}
			}
		});
	}

	protected void createUserProfile() {

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity

			// Fetch Facebook user info if the session is active
			Session session = ParseFacebookUtils.getSession();
			if (session != null && session.isOpened()) {
				makeMeRequest();
			}
			// Intent intent = new Intent(this, UserDetailsActivity.class);
			// startActivity(intent);
			// showUserDetailsActivity();
		}

	}

	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							// Save the user profile info in a user property
							ParseUser currentUser = ParseUser.getCurrentUser();

							// Create a JSON object to hold the profile info
							JSONObject userProfile = new JSONObject();
							try {
								// Populate the JSON object
								userProfile.put("facebookId", user.getId());
								currentUser.put("nombre", user.getName());
								if (user.getLocation() != null) {
									if (user.getLocation().getProperty("name") != null) {
										userProfile.put("location",
												(String) user.getLocation()
														.getProperty("name"));
									}
								}
								if (user.getProperty("gender") != null) {
									userProfile.put("gender",
											(String) user.getProperty("gender"));
								}
								if (user.getBirthday() != null) {
									userProfile.put("birthday",
											user.getBirthday());
								}
								if (user.getProperty("relationship_status") != null) {
									userProfile
											.put("relationship_status",
													(String) user
															.getProperty("relationship_status"));
								}

								currentUser.put("profile", userProfile);
								currentUser.put("facebookId", user.getId());
								currentUser.put(User.TYPE, userTipo);
								currentUser
										.saveInBackground(new SaveCallback() {
											@Override
											public void done(ParseException e) {
												// MainActivity.this.progressDialog.dismiss();
											}
										});

								// Show the user info
								// updateViewsWithProfileInfo();
								showNextActivity(true);
							} catch (JSONException e) {
								Log.d(RespiremosSalud.TAG,
										"Error parsing returned user data.");
							}

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(RespiremosSalud.TAG,
										"The facebook session was invalidated.");
								onLogoutButtonClicked(false);
							} else {
								Log.d(RespiremosSalud.TAG, "Some other error: "
										+ response.getError().getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}

	protected void showNextActivity(boolean firstRun) {
		
		if(firstRun){
			
			switch (userTipo) {
			case User.TYPE_NO:
				showHomeActiviy();
				break;
			case User.TYPE_EX:
				showDatosActivity();
				break;
			case User.TYPE_LEAVING:
				showDatosActivity();
				break;
			case User.TYPE_YES:
				showDatosActivity();
				break;
			default:
				break;
			}
			
		}else{
			showHomeActiviy();
		}
	}

	private void onLogoutButtonClicked(boolean closeAndClear) {
		// Log the user out
		if (closeAndClear) {
			ParseFacebookUtils.getSession().closeAndClearTokenInformation();
		}
		ParseUser.logOut();
		// ParseFacebookUtils.
		// Go to the login view
		// startLoginActivity();
	}

	protected void showSendMessagesActiviy() {
		Intent intent = new Intent(this, SendMessagesActivity.class);
		startActivity(intent);
	}

	protected void showHelpSmokersActiviy() {
		Intent intent = new Intent(this, HelpSmokers.class);
		startActivity(intent);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonNo:
			userTipo = User.TYPE_NO;
			onLoginButtonClicked();
			break;
		case R.id.buttonDej:
			userTipo = User.TYPE_LEAVING;
			onLoginButtonClicked();
			break;
		case R.id.buttonFumador:
			userTipo = User.TYPE_YES;
			onLoginButtonClicked();
			break;
		case R.id.buttonEx:
			userTipo = User.TYPE_EX;
			onLoginButtonClicked();
			break;

		default:
			break;
		}
	}

}
