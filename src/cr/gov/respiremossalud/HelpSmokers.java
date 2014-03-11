package cr.gov.respiremossalud;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import cr.gov.respiremossalud.data.UserAdapter;
import cr.gov.respiremossalud.model.User;

public class HelpSmokers extends ListFragment implements OnClickListener {
	private ListView listFriends;
	private ParseUser currentUser;
	protected UserAdapter userAdapter;
	private ArrayList<User> friends = new ArrayList<User>();
	private ProgressDialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.help_smokers, null);
		
		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			getHelpingSmokersList();
		} else {
			startLoginActivity();
		}
		loadListHelpedFriends();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		listFriends = getListView();
		
	}

	private void getHelpingSmokersList() {
		 progressDialog = ProgressDialog.show(getActivity(), "", "Obteniendo usuarios...", true);

		ParseRelation<ParseObject> relation = currentUser
				.getRelation("helpingSmokers");

		relation.getQuery().findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				progressDialog.dismiss();
				if (e == null) {

					for (ParseObject user : objects) {
						// Log.d(RespiremosSalud.TAG, user.getObjectId());
						// try {
						User newUser = new User();
						// JSONObject responseObject =
						// user.getJSONObject("profile");
						newUser.setId(user.getObjectId());
						newUser.setNombre(user.getString("nombre"));
						newUser.setFacebookId(user.getString(User.FB_ID));
						newUser.setTipo(user.getInt(User.TYPE));
						Log.d("RS", user.getInt("points")+"");
						newUser.setPuntos(user.getInt("points"));
						// newUser.setSinFumar(user.getDate(User.SIN_FUMAR).toString());
						friends.add(newUser);
						// Log.d(RespiremosSalud.TAG,
						// user.getJSONObject("profile").getString("name")+", ");
						// Log.d(RespiremosSalud.TAG,
						// user.getString(User.FB_ID));
						// } catch (JSONException e1) {
						// // TODO Auto-generated catch block
						// e1.printStackTrace();
						// }
					}
					userAdapter = new UserAdapter(getActivity(), friends,
							false, true);
					listFriends.setAdapter(userAdapter);
				} else {

				}

			}
		});
	}

	private void startLoginActivity() {
		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void loadListHelpedFriends() {
		// TODO Auto-generated method stub

	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setCustomView(R.layout.helping_menu);
//		actionBar.setDisplayShowCustomEnabled(true);
//		
//		ImageButton AddSmokers = (ImageButton) findViewById(R.id.add_smokers);
//		
//		AddSmokers.setOnClickListener(this);
//				
//		return true;
//	
//	}

	private void showAddSmokersActivity() {
		Intent intent = new Intent(getActivity(), AddSmokers.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.add_smokers:
			showAddSmokersActivity();
			break;
		default:
			break;
		}
	}

}
