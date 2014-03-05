package cr.gov.respiremossalud.fragments;

import com.actionbarsherlock.app.SherlockFragment;

import cr.gov.respiremossalud.R;
import cr.gov.respiremossalud.R.layout;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends SherlockFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.info_fragment, null);
		return view;
	
	}
}
