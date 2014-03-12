package cr.gov.respiremossalud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cr.gov.respiremossalud.data.UserAdapter;
import cr.gov.respiremossalud.fragments.HelpSmokers;
import cr.gov.respiremossalud.model.User;

public class AddSmokers extends SherlockListActivity implements OnClickListener {
	private ListView listFriends;
	private ArrayList<User> friends = new ArrayList<User>();
	private ArrayList<ParseUser> friendSmokers;
	private ProgressDialog progressDialog;
	private UserAdapter userAdapter;
	private ParseUser currentUser;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_smokers);
//		getSupportActionBar().setTitle(getString(R.string.title_amigos_Buscar));
		listFriends = getListView();
		currentUser = ParseUser.getCurrentUser();
		
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
//			updateViewsWithProfileInfo();
			getFriendSmokersList();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
			
		}
	}
	
	private void startLoginActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getSupportMenuInflater();
//	    inflater.inflate(R.menu.add_smokers, menu);
//	    return true;
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setCustomView(R.layout.buscar_menu);
		actionBar.setDisplayShowCustomEnabled(true);
			
		Button AddSmokers = (Button) findViewById(R.id.add_smokers);
		
		AddSmokers.setOnClickListener(this);
		
		return true;

	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.add_smokers:
			addSmokers();
			break;
		default:
			break;
		}
	}

	private void addSmokers() {
		progressDialog = ProgressDialog.show(this, "", "Agregando tus amigos...", true);

		ParseRelation<ParseObject> relation = currentUser.getRelation("helpingSmokers");
		
		for (User friend : friends) {
			if (friend.selected){
				ParseUser friendSelected = (ParseUser) ParseObject.createWithoutData("_User", friend.getId());
				
				relation.add(friendSelected);
				
				Log.d("RS", friend.getId());
			}
		}
		currentUser.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				progressDialog.dismiss();
				Log.d("RS", "updated currentUser");
				showHelpSmokersActiviy();
			}
		});
//		currentUser.put("helpingSmokers", );
		
	}
	protected void showHelpSmokersActiviy() {
		Intent intent = new Intent(this, HelpSmokers.class);
		startActivity(intent);
	}
	
	private void getFriendSmokersList() {
		progressDialog = ProgressDialog.show(this, "", "Obteniedo tus amigos de Facebook...", true);
		
		Log.d(RespiremosSalud.TAG, "getFriendSmokersList");
		
		Request.newMyFriendsRequest(ParseFacebookUtils.getSession(), new Request.GraphUserListCallback() {
			
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				progressDialog.dismiss();
				progressDialog = ProgressDialog.show(AddSmokers.this, "", "Buscando tus amigos Fumadores...", true);
				
//				Log.d(RespiremosSalud.TAG, response.toString());
				if (users != null) {
				      List<String> friendsListCollection = new ArrayList<String>();
				      for (GraphUser user : users) {
//				    	  	Log.d(RespiremosSalud.TAG, user.getName()+", ");
				    	  	friendsListCollection.add(user.getId());
				      }
				      
				      // Construct a ParseUser query that will find friends whose
				      // facebook IDs are contained in the current user's friend list.
				      ParseQuery<ParseUser> friendQuery = ParseUser.getQuery();
//				      friendQuery.whereExists("facebookId");
				      friendQuery.whereContainedIn("facebookId", friendsListCollection);
				      friendQuery.whereGreaterThanOrEqualTo("tipo", 3);
				      friendQuery.findInBackground(new FindCallback<ParseUser>() {
						
						@Override
						public void done(List<ParseUser> objects, ParseException e) {
							
							progressDialog.dismiss();
							if(e == null){
//								Log.d(RespiremosSalud.TAG, e.toString());
								for (ParseObject user : objects) {
//								Log.d(RespiremosSalud.TAG, user.getObjectId());
//									try {
										User newUser = new User();
//										JSONObject responseObject = user.getJSONObject("profile");
										newUser.setId(user.getObjectId());
										newUser.setNombre(user.getString("nombre"));
										newUser.setFacebookId(user.getString(User.FB_ID));
										newUser.setTipo(user.getInt(User.TYPE));
//										newUser.setSinFumar(user.getDate(User.SIN_FUMAR).toString());
										friends.add(newUser);
//									Log.d(RespiremosSalud.TAG, user.getJSONObject("profile").getString("name")+", ");
										Log.d(RespiremosSalud.TAG, user.getString(User.FB_ID));
//									}
								}
								userAdapter = new UserAdapter(AddSmokers.this, friends, true, false);
								listFriends.setAdapter(userAdapter);
							}else{
//								Log.d(RespiremosSalud.TAG, "e es null");
							}
//							if(objects.isEmpty()){
//								Log.d(RespiremosSalud.TAG, "objects viene vacio");
//							}
						}
					});
				      
				      // findObjects will return a list of ParseUsers that are friends with
				      // the current user
//				      List<ParseObject> friendUsers = friendQuery.find();
				    }
			}
		}).executeAsync();
				
	}
	
	
	
}
