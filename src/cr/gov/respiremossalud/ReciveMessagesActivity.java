package cr.gov.respiremossalud;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cr.gov.respiremossalud.data.MessagesAdapter;
import cr.gov.respiremossalud.model.Locationh;
import cr.gov.respiremossalud.model.Message;
import cr.gov.respiremossalud.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class ReciveMessagesActivity extends SherlockActivity implements
		OnClickListener {
	private ListView list_messages;
	private EditText edit_message;
	private Button send_message;
	private ArrayList<Message> messagesData = new ArrayList<Message>();
	private MessagesAdapter messageAdapter;
	private ParseUser currentUser;
	private ParseUser friendTo;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recive_messages);
//		getSupportActionBar().setTitle(getString(R.string.title_mensajes_apoyo));
		currentUser = ParseUser.getCurrentUser();
		
		// friendTo = (ParseUser) ParseObject.createWithoutData("_User",
		// intent.getStringExtra("toId"));

		list_messages = (ListView) findViewById(R.id.list_messages);

		messageAdapter = new MessagesAdapter(this, messagesData, true, true);
		list_messages.setAdapter(messageAdapter);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Recive Mensajes Positivos");

		getMessages();

	}

	private void getMessages() {
		 progressDialog = ProgressDialog.show(this, "", "Obteniendo mensajes...", true);
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Message.CLASS);
		query.whereEqualTo("to", currentUser);
		query.whereNotEqualTo("aceptado", false);
		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> list, ParseException e) {
				progressDialog.dismiss();
				if (e == null) {

					for (ParseObject message : list) {

						Message newMessage = new Message();

						ParseUser fromFriend = message.getParseUser("from");
						newMessage.setText(message.getString(Message.TEXT));
						newMessage.setId(message.getObjectId());
						
						if(message.getBoolean("aceptado")){
							newMessage.setAceptado(message.getBoolean("aceptado"));
						}
						
						messageAdapter.add(newMessage);

						messageAdapter.notifyDataSetChanged();
						try {
							fromFriend.fetchIfNeeded();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if (fromFriend.getString(User.NAME)!=null){
							newMessage.setName(fromFriend.getString(User.NAME));
							messageAdapter.notifyDataSetChanged();
						}
					}

				} else {
					Log.e("Parse", "Error: " + e.getMessage());
				}
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_message:
			sendMessage();
			break;

		default:
			break;
		}

	}

	private void sendMessage() {

		String newMessageText = edit_message.getText().toString();
		Message newMessage = new Message();
		newMessage.setText(newMessageText);
		messageAdapter.add(newMessage);
		// messagesData.add(newMessage);
		messageAdapter.notifyDataSetChanged();

		edit_message.setText("");

		ParseObject message = new ParseObject("Message");
		message.put("text", newMessageText);
		ParseACL acl = new ParseACL(currentUser);
		ParseACL aclTo = new ParseACL();
		aclTo.setReadAccess(friendTo, true);
		// acl.setReadAccess(user, true);
		message.setACL(acl);
		message.setACL(aclTo);
		message.put("from", currentUser);
		message.put("to", friendTo);
		message.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				Log.d(RespiremosSalud.TAG,
						"Mensaje guardado -> enviar notificacion");

			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setCustomView(R.layout.recivido_menu);
		actionBar.setDisplayShowCustomEnabled(true);
				
		return true;

	}
	
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// ActionBar actionBar = getSupportActionBar();
	// actionBar.setDisplayShowTitleEnabled(false);
	// actionBar.setDisplayShowHomeEnabled(false);
	// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	// actionBar.setCustomView(R.layout.home_menu);
	// actionBar.setDisplayShowCustomEnabled(true);
	//
	// btnLista = (Button) findViewById(R.id.btnLista);
	// btnMapa = (Button) findViewById(R.id.btnMapa);
	// ImageButton btnParquearme = (ImageButton)
	// findViewById(R.id.btnParquearme);
	// btnLista.setOnClickListener(this);
	// btnMapa.setOnClickListener(this);
	// btnParquearme.setOnClickListener(this);
	// btnLista.setSelected(showList);
	// btnMapa.setSelected(showMapa);
	// return true;
	//
	// }

}