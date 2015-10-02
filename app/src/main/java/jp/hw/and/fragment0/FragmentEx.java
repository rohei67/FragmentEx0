package jp.hw.and.fragment0;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentEx extends Activity {
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
	}

	public static class TitlesFragment extends ListFragment {
		@Override
		public void onActivityCreated(Bundle bundle) {
			super.onActivityCreated(bundle);

			String[] listText = {"ページ0", "ページ1", "ページ2"};
			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, listText));

			getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
			if (isTablet(getActivity()) || isLandscape(getActivity())) {
				showDetails(0);        // 詳細同時表示モードのデフォルトはページ0
			}
		}

		@Override
		public void onStart() {
			super.onStart();
			if (isTablet(getActivity()) || isLandscape(getActivity())) {
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				getListView().setItemChecked(0, true);
			}
		}

		@Override
		public void onListItemClick(ListView l, View v, int pos, long id) {
			showDetails(pos);
		}

		private void showDetails(int index) {
			Context context = getActivity().getApplication();

			if (isTablet(context) || isLandscape(context)) {
				updateDetailFragment(index);
			} else
				startDetailActivity(index, context);
		}

		private void startDetailActivity(int index, Context context) {
			Intent intent = new Intent(context, DetailActivity.class);
			intent.putExtra("index", index);
			getActivity().startActivity(intent);
		}

		private void updateDetailFragment(int index) {
			DetailActivity.DetailFragment fragment = DetailActivity.DetailFragment.newInstance(index);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);    //フラグメント表示時のアニメーションの設定
			ft.replace(R.id.detail, fragment);
			ft.commit();
		}

		public static boolean isLandscape(Context context) {
			return (getConfig(context).orientation & Configuration.ORIENTATION_LANDSCAPE)
					== Configuration.ORIENTATION_LANDSCAPE;
		}

		public static boolean isTablet(Context context) {
			return (getConfig(context).screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
					== Configuration.SCREENLAYOUT_SIZE_XLARGE;
		}

		private static Configuration getConfig(Context context) {
			return context.getResources().getConfiguration();
		}
	}
}
