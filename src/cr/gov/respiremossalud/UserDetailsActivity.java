package cr.gov.respiremossalud;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.facebook.FacebookRequestError;
import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class UserDetailsActivity extends SherlockActivity {

	private ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	// private TextView userLocationView;
	// private TextView userGenderView;
	// private TextView userDateOfBirthView;
	// private TextView userRelationshipView;
	private Button logoutButton;
	private TextView puntosText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.userdetails);
//		getSupportActionBar().setTitle(getString(R.string.title_perfil));

		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
		userNameView = (TextView) findViewById(R.id.userName);
		puntosText = (TextView) findViewById(R.id.profilePuntos);
		// userLocationView = (TextView) findViewById(R.id.userLocation);
		// userGenderView = (TextView) findViewById(R.id.userGender);
		// userDateOfBirthView = (TextView) findViewById(R.id.userDateOfBirth);
		// userRelationshipView = (TextView)
		// findViewById(R.id.userRelationship);

		logoutButton = (Button) findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogoutButtonClicked();
			}
		});

		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			// showSendMessagesActiviy();
			// createUserProfile();
			// showUserActivity();
			updateViewsWithProfileInfo();
			// showHelpSmokersActiviy();
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
	}

	private void updateViewsWithProfileInfo() {
		// getFriendsList();
		ParseUser currentUser = ParseUser.getCurrentUser();
		String facebookId = currentUser.getString("facebookId");
		userProfilePictureView.setProfileId(facebookId);
		userNameView.setText(currentUser.getString("nombre"));
		puntosText.setText(currentUser.getInt("points") + " Puntos");

	}

	private void onLogoutButtonClicked() {
		// Log the user out
		ParseFacebookUtils.getSession().closeAndClearTokenInformation();
		ParseUser.logOut();
		// ParseFacebookUtils.
		// Go to the login view
		startLoginActivity();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setCustomView(R.layout.perfil_menu);
		actionBar.setDisplayShowCustomEnabled(true);
				
		return true;

	}

	private void startLoginActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
