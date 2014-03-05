package cr.gov.respiremossalud.data;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import cr.gov.respiremossalud.R;
import cr.gov.respiremossalud.RespiremosSalud;
import cr.gov.respiremossalud.model.Message;
import cr.gov.respiremossalud.model.User;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessagesAdapter extends ArrayAdapter<Message> implements OnClickListener {

	private LayoutInflater inflater;
	private Context c;
	private ArrayList<Message> data;
	private boolean showName;
	private boolean showTools;
	
	public MessagesAdapter(Context context, ArrayList<Message> objects, boolean showNameFrom, boolean showToolbar) {
		super(context, -1, objects);
		showName = showNameFrom;
		showTools = showToolbar;
		data = objects;
		c = context;
		inflater = LayoutInflater.from(c);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Message currentMessage = data.get(position);
	    if (convertView == null){
	        convertView = inflater.inflate(R.layout.message_adapter, null);
	        holder = new ViewHolder();
	        holder.textMessage = (TextView) convertView.findViewById(R.id.mensaje);
	        holder.dislike = (ImageButton) convertView.findViewById(R.id.dislike);
	        holder.like = (ImageButton) convertView.findViewById(R.id.like);
	        holder.toolbar = (LinearLayout) convertView.findViewById(R.id.toolbar);
	        holder.nombre = (TextView) convertView.findViewById(R.id.nombre);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.textMessage.setText(currentMessage.getText());
	    holder.dislike.setTag(position);
	    holder.like.setTag(position);
	    
	    
	    if(currentMessage.isAceptado()){	    		
	    		holder.toolbar.setVisibility(View.GONE);
	    }
	    
	    
	    if(showName){	    		
	    		holder.nombre.setText(currentMessage.getName());
	    }else{
	    		holder.nombre.setVisibility(View.GONE);
	    }
	    if(showTools){
	    	
	    }else{
	    		holder.toolbar.setVisibility(View.GONE);
	    }
	    holder.dislike.setOnClickListener(this);
	    holder.like.setOnClickListener(this);
	    return convertView;
	}
	
	private static class ViewHolder{
		public ImageButton like;
		public TextView textMessage, nombre;
		public ImageButton dislike;
		public LinearLayout toolbar;
	}

	@Override
	public void onClick(View v) {
		
		Message clickedMessage = new Message();
		int position = -1;
		try{
			position =  (Integer) v.getTag();
		}catch (Exception e){
			
		}
		
		clickedMessage = data.get(position);
		
		switch (v.getId()) {
		case R.id.dislike:
			Log.d(RespiremosSalud.TAG, "no me gusta");
			
			remove(clickedMessage);
			notifyDataSetChanged();
			setAceptado(clickedMessage.getId(), false);
			
			break;
		case R.id.like:
			Log.d(RespiremosSalud.TAG, "me gusta");
			
			View toolbar = (View) v.getParent();
			toolbar.setVisibility(View.GONE);
			
			setAceptado(clickedMessage.getId(), true);

			break;
		default:
			break;
		}
	}
	
	private void setAceptado(String id, boolean aceptado) {
		ParseObject message = ParseObject.createWithoutData(Message.CLASS, id);
		message.put("aceptado", aceptado);
		message.saveInBackground();
	}
}
