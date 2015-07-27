/**
 * 
 */
package fragment;

import com.example.myandroidlibrary.R;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author CSH 2015-7-24
 */
@SuppressLint("NewApi")
public class ContactsFragment extends Fragment {

	/**
	 * 
	 */
	public ContactsFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.contacts_layout, container, false);
	}
}
