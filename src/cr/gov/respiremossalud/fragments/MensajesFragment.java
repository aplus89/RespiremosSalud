package cr.gov.respiremossalud.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import cr.gov.respiremossalud.R;

public class MensajesFragment extends EFragment {
	
	private ViewPager mPager;
	private ScreenSlidePagerAdapter mPagerAdapter;
	private ArrayList<EFragment> fragmentos = new ArrayList<EFragment>();
	private HelpSmokers helpSmokers;
	private ReciveMessagesActivity reciveMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mensajes_fragment, null);
		
		helpSmokers = new HelpSmokers();
		reciveMessages = new ReciveMessagesActivity();
		
		fragmentos.add(helpSmokers);
		fragmentos.add(reciveMessages);
		
		mPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), fragmentos);
        mPager.setAdapter(mPagerAdapter);
        
        return view;
		
	}

	
	public ListView getListView(){
		return fragmentos.get(mPager.getCurrentItem()).getListView();
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        
		private ArrayList<EFragment> fragmentos;

		public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<EFragment> fragmentos) {
            super(fm);
            this.fragmentos = fragmentos;
        }

        @Override
        public Fragment getItem(int position) {
        		
        		return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }
    }
	

}
