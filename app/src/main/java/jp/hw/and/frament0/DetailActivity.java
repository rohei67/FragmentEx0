package jp.hw.and.frament0;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		createLayout();
		int index = fetchIndex();
		replaceFragment(index);
	}

	private void replaceFragment(int index) {
		DetailsFragment fragment = DetailsFragment.newInstance(index);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.details, fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	private int fetchIndex() {
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			return extras.getInt("index");
		return 0;
	}

	private void createLayout() {
		FrameLayout layout = new FrameLayout(this);
		layout.setId(R.id.details);
		setContentView(layout);
	}

	public static class DetailsFragment extends Fragment
	{
		public static DetailsFragment newInstance(int index) {
			Bundle bundle = new Bundle();
			bundle.putInt("index", index);

			DetailsFragment fragment = new DetailsFragment();
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
			if (container == null)
				return null;

			TextView textView = new TextView(getActivity());
			textView.setText("ページ" + getArguments().getInt("index", 0 /*:default*/) + "の詳細");
			textView.setTextSize(24);
			return textView;
		}
	}
}

