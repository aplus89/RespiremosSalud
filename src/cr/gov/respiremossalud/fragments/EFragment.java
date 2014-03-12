package cr.gov.respiremossalud.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.ScrollView;
import cr.gov.respiremossalud.R;

public abstract class EFragment extends Fragment {
	
	public ScrollView getScrollView(){
		return (ScrollView) getView().findViewById(R.id.scrollChild);
	}
	public ListView getListView(){
		return (ListView) getView().findViewById(R.id.listChild);
	}
	
}