package cr.gov.respiremossalud;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.facebook.widget.ProfilePictureView;
import com.nineoldandroids.view.ViewHelper;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import cr.gov.respiremossalud.fragments.MensajesFragment;
import cr.gov.respiremossalud.model.User;
import cr.gov.respiremossalud.ui.UIScrollView;
import cr.gov.respiremossalud.ui.UIScrollView.OnScrollChangedListener;
import cr.gov.respiremossalud.ui.UIScrollView.OnScrollStoppedListener;

public class RedisenoActivity extends SherlockFragmentActivity implements OnChronometerTickListener {

	private Button loginButton;
	private Dialog progressDialog;
	private Button buttonNo;
	private Button buttonEx;
	private Button buttonSi;
	private Button buttonDej;
	private ParseUser currentUser;
	
	
	private TextView tiempoText;
	private TextView puntosText;
	private int userTipo;
	private Date sinFumar;
	private Chronometer cronometro;
	private ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private Button logoutButton;
	private FrameLayout mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rediseno);
		
		getSupportActionBar().hide();
		
		tiempoText = (TextView) findViewById(R.id.tiempo_text);
		puntosText = (TextView) findViewById(R.id.puntos_text);
		cronometro = (Chronometer) findViewById(R.id.cronometro);
		cronometro.setOnChronometerTickListener(this);
		
		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
		userNameView = (TextView) findViewById(R.id.userName);
		
		logoutButton = (Button) findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogoutButtonClicked();
			}
		});
		
		mainView = (FrameLayout) findViewById(R.id.mainView);
		
		MensajesFragment mensajesFragment = new MensajesFragment();
		BenefitsActivity beneficiosFragment = new BenefitsActivity();
//		MensajesFragment mensajesFragment = new MensajesFragment();
//		MensajesFragment mensajesFragment = new MensajesFragment();
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fts = fragmentManager.beginTransaction();
		
		fts.add(mainView.getId(), beneficiosFragment);
		fts.add(mainView.getId(), mensajesFragment);
		
		fts.commit()	;
		
//		final ScrollView scrollChild = (ScrollView)findViewById(R.id.scrollChild);  // your listview inside scrollview
		
		final UIScrollView scrollMain = (UIScrollView)findViewById(R.id.scrollMain);  // your listview inside scrollview
		
		final LinearLayout layoutMenu = (LinearLayout)findViewById(R.id.layoutMenu);
		final LinearLayout layoutPerfil = (LinearLayout)findViewById(R.id.layoutPerfil);
		
		
		final ViewTreeObserver observer= scrollMain.getViewTreeObserver();
	       observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	            @SuppressWarnings("deprecation")
				@Override
	            public void onGlobalLayout() {
	            	 Log.d("Alto Scroll Principal:", ""+scrollMain.getHeight());
	            	 ViewGroup.LayoutParams paramsChild = (ViewGroup.LayoutParams) mainView.getLayoutParams();	              
	            	 int actionBarHeight = (int) getResources().getDimension(R.dimen.abs__action_bar_default_height);
	            	 paramsChild.height = scrollMain.getHeight()-actionBarHeight;
	            	 mainView.setLayoutParams(paramsChild);
	            	 scrollMain.scrollTo(0, layoutMenu.getHeight());
	            	 scrollMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	            }
	        });
		
//		scrollChild.setOnTouchListener(new OnTouchListener() {
//			float prevY=0;
//			float prevRawY = 0;
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				int theTop = v.getTop();
//				int scrollY = scrollMain.getScrollY();
//				int viewTop = theTop-scrollY;
//				int actionBarHeight = (int) getResources().getDimension(R.dimen.abs__action_bar_default_height);
//				int action = event.getAction();
//				int scrollYChild = v.getScrollY();
//				textScrollChild.setText("getScrollY: "+v.getScrollY());
//				if(viewTop <= actionBarHeight){
//						switch (action) {
//						case MotionEvent.ACTION_DOWN:
//							v.getParent().requestDisallowInterceptTouchEvent(true);
//							break;
//						case MotionEvent.ACTION_UP:
//							v.getParent().requestDisallowInterceptTouchEvent(false);
//							break;
//						case MotionEvent.ACTION_MOVE:
//							if(scrollYChild<=0){
//								float direction = event.getY()-prevY;
//								prevY = event.getY();
//								prevRawY = event.getRawY();
//								if(direction>0){
//									v.getParent().requestDisallowInterceptTouchEvent(false);
//									return true;
//								}
//							}
//							break;
//						}
//					return false;
//				}
//				else{
//					return true;
//				}
//			}
//		});
		
		scrollMain.setSmoothScrollingEnabled(true);
		
		scrollMain.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int menuHeight = layoutMenu.getHeight();
				int perfilHeight = layoutPerfil.getHeight();
				int scrollY = v.getScrollY();
				int actionBarHeight = (int) getResources().getDimension(R.dimen.abs__action_bar_default_height);
				int viewAndBarTop = menuHeight+perfilHeight-actionBarHeight;
				int action = event.getAction();
				if(action == MotionEvent.ACTION_UP){
					scrollMain.startScrollerTask();
				}
				if(viewAndBarTop <= scrollY){
//					scrollChild.onInterceptTouchEvent(event);
//					scrollChild.dispatchTouchEvent(event);
				}else{
				}
				return false;
			}
		});
		
		scrollMain.setOnScrollStoppedListener(new OnScrollStoppedListener() {
			@Override
			public void onScrollStopped() {
				Log.i("SSS", "stopped");
				int menuHeight = layoutMenu.getHeight();
				int perfilHeight = layoutPerfil.getHeight();
				int scrollY = scrollMain.getScrollY();
				int actionBarHeight = (int) getResources().getDimension(R.dimen.abs__action_bar_default_height);
				int perfilTop = menuHeight;
				int viewAndBarTop = menuHeight+perfilHeight-actionBarHeight;
				int firstStep = menuHeight/2;
				int secondStep = perfilTop+((perfilHeight-actionBarHeight)/2);
				if(scrollY < firstStep){
					scrollMain.easingScrollTo(0, 0);
				}else if( scrollY >= firstStep && scrollY < secondStep){
					scrollMain.easingScrollTo(0, perfilTop);
				}else if(scrollY >= secondStep){
					scrollMain.easingScrollTo(0, viewAndBarTop);
				}
			}
		});
		
		scrollMain.setOnScrollChangedListener( new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(UIScrollView v, int l, int t, int oldl, int oldt) {
				int menuHeight = layoutMenu.getHeight();
				int perfilHeight = layoutPerfil.getHeight();
				int actionBarHeight = (int) getResources().getDimension(R.dimen.abs__action_bar_default_height);
				int completeScale = perfilHeight-actionBarHeight;
				int scrollWithoutMenu = scrollMain.getScrollY()-menuHeight;
				if(scrollWithoutMenu>0){
					float percentScale = ((((float)scrollWithoutMenu)/completeScale)/10)+0.9f;
					Log.d("CHANGED", ""+scrollWithoutMenu+"/"+completeScale+"="+percentScale);
					ViewHelper.setScaleX(mainView, percentScale);
					ViewHelper.setScaleY(mainView, percentScale);
				}
			}
		});
		
		currentUser = ParseUser.getCurrentUser();
		userTipo = currentUser.getInt(User.TYPE);
		
		if(currentUser!= null && ParseFacebookUtils.isLinked(currentUser)){
			getHomeInfo();
		}else {
//			startLoginActivity();
		}
		
		
//		PushService.setDefaultPushCallback(this, RedisenoActivity.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();
//		
//		ParseAnalytics.trackAppOpened(getIntent());
//		
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		
	}
	
	@Override
	public void onResume() {
		super.onResume();

//		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
//			updateViewsWithProfileInfo();
			getHomeInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
	}
	
	private void onLogoutButtonClicked() {
		// Log the user out
		ParseFacebookUtils.getSession().closeAndClearTokenInformation();
		ParseUser.logOut();
		// ParseFacebookUtils.
		// Go to the login view
		startLoginActivity();
	}
	
	private void startLoginActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private void getHomeInfo() {
		getUserPoints();
		
		String facebookId = currentUser.getString("facebookId");
		userProfilePictureView.setProfileId(facebookId);
		userNameView.setText(currentUser.getString("nombre"));
		puntosText.setText(currentUser.getInt("points") + " Puntos");
		
		
//		getHelpingFriends();
		Log.d("RS", userTipo+"");
		if(userTipo == User.TYPE_NO){
//			beneficiosSection.setVisibility(View.GONE);
		}else{
			getUserTime();
		}
//		if(userTipo == User.TYPE_EX && userTipo == User.TYPE_NO){
//			ayudadoSection.setVisibility(View.GONE);
//		}else{
//			getHelpMeFriends();
//		}
	}
	
	private void getUserTime() {
		if (currentUser.get(User.SIN_FUMAR) != null) {
					
					sinFumar = currentUser.getDate(User.SIN_FUMAR);
					Log.d(RespiremosSalud.TAG+" sinFumar: ", sinFumar.toString());
					
					long lastSuccess = sinFumar.getTime(); //Some Date object
					long elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime();
					cronometro.setBase(lastSuccess - elapsedRealtimeOffset);
					cronometro.start();
		}
				
			}
	
	private void getUserPoints(){
		puntosText.setText(currentUser.getInt("points")+"");
	}
	
	@Override
	public void onChronometerTick(Chronometer crono) {
		Date past=new Date(crono.getBase());
		Date today=new Date(SystemClock.elapsedRealtime());
		
		int days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays();
		int hours = Hours.hoursBetween(new DateTime(past), new DateTime(today)).getHours();
		int minutes = Minutes.minutesBetween(new DateTime(past), new DateTime(today)).getMinutes();
		
		int hoursOutDays = hours - (days*24);
		int minutesOutHours = minutes - (hours*60);
		String tiempoFormateado = days+"d "+hoursOutDays+"hr "+minutesOutHours+"min";
		tiempoText.setText(tiempoFormateado);
		
		Log.d(RespiremosSalud.TAG+"contando dias: ", tiempoFormateado);
		
	}

}
