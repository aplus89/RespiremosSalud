package cr.gov.respiremossalud.data;

import java.util.ArrayList;
import java.util.List;


import com.facebook.widget.ProfilePictureView;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import cr.gov.respiremossalud.HelpSmokers;
import cr.gov.respiremossalud.R;
import cr.gov.respiremossalud.RespiremosSalud;
import cr.gov.respiremossalud.SendMessagesActivity;
import cr.gov.respiremossalud.model.Message;
import cr.gov.respiremossalud.model.User;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User> implements OnClickListener, OnCheckedChangeListener {

	private ArrayList<User> data;
	private Context c;
	private LayoutInflater inflater;
	private boolean showSelect;
	private boolean showDelete;

	public UserAdapter(Context context,	ArrayList<User> objects, boolean show_select, boolean show_delete) {
		super(context, -1, objects);
		showSelect = show_select;
		showDelete = show_delete;
		data = objects;
		c = context;
		inflater = LayoutInflater.from(c);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		User friend = data.get(position);
	    if (convertView == null){
	        convertView = inflater.inflate(R.layout.user_row, null);
	        holder = new ViewHolder();
	        holder.selected = (CheckBox) convertView.findViewById(R.id.selected);
	        holder.deleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);
	        holder.nombre = (TextView) convertView.findViewById(R.id.nombre);
	        holder.puntos = (TextView) convertView.findViewById(R.id.puntos);
	        holder.profilePicture = (ProfilePictureView) convertView.findViewById(R.id.userProfilePicture);
	        holder.row = (LinearLayout) convertView.findViewById(R.id.user_row);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.nombre.setText(friend.getNombre());
	    holder.puntos.setText(friend.getPuntos()+" Puntos");
	    holder.selected.setTag(position);
	    holder.deleteButton.setTag(position);
	    holder.row.setTag(position);
//	    holder.position = position;
	    if(!showSelect){
	    		holder.selected.setVisibility(View.GONE);
	    }
	    if(!showDelete){
    			holder.deleteButton.setVisibility(View.GONE);
	    }
	    holder.deleteButton.setOnClickListener(this);
	    holder.row.setOnClickListener(this);
	    holder.selected.setOnCheckedChangeListener(this);
//	    if (holder.profilePicture != null && friend.getFacebookId() != null){
	    		holder.profilePicture.setProfileId(friend.getFacebookId());
//	    }
//	    convertView.setOnClickListener(this);
	    return convertView;
	
	}
	
	private static class ViewHolder{
		public TextView nombre, puntos;
		public ProfilePictureView profilePicture;
		public CheckBox selected;
		public ImageButton deleteButton;
		public LinearLayout row;
	}

	@Override
	public void onClick(View v) {
		User clickedUser = new User();
		int position = -1;
		try{
			position =  (Integer) v.getTag();
		}catch (Exception e){
		}
		switch (v.getId()) {
		case R.id.deleteButton:
//			Log.d(RespiremosSalud.TAG, "no me gusta");
			clickedUser = data.get(position);
			remove(clickedUser);
			notifyDataSetChanged();
			deleteFriendHelp(clickedUser.getId());
			break;
		case	 R.id.user_row:
			clickedUser = data.get(position);
//			int position = (Integer) v.getTag().position;
			showSendMessagesActiviy(clickedUser.getId());
			break;
		default:
			break;
		}
	}
	
	protected void showSendMessagesActiviy(String id) {
		Intent intent = new Intent(getContext(), SendMessagesActivity.class);
		intent.putExtra("toId", id);
		getContext().startActivity(intent);
	}

private void deleteFriendHelp(String id) {

	ParseUser currentUser = ParseUser.getCurrentUser();

		
		ParseRelation<ParseObject> relation = currentUser.getRelation("helpingSmokers");
		ParseUser friendToDelete = (ParseUser) ParseObject.createWithoutData("_User", id);
		
		relation.remove(friendToDelete);
		currentUser.saveInBackground();
//		relation.
		
	}

	@Override
	public void onCheckedChanged(CompoundButton v, boolean check) {
		User clickedUser = new User();
		try{
			int position =  (Integer) v.getTag();
			clickedUser = data.get(position);
			clickedUser.selected = check;
		}catch (Exception e){
		}
		
		
		
	}

}
