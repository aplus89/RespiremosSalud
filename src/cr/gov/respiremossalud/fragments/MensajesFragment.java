package cr.gov.respiremossalud.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import cr.gov.respiremossalud.HelpSmokers;
import cr.gov.respiremossalud.R;
import cr.gov.respiremossalud.ReciveMessagesActivity;

public class MensajesFragment extends SherlockFragmentActivity {
	
	private ViewPager mPager;
	private ScreenSlidePagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.mensajes_fragment);
		
		mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
		
	}
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        		
        		switch (position) {
				case 1:
					return new HelpSmokers();
				case 2:
					return new ReciveMessagesActivity();
				}
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
	

}
