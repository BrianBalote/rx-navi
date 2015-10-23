package org.balote.drugsearch.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ustg.drugsearch.R;

public class MySimpleListBaseAdapter extends BaseAdapter {

	private int viewResourceId;
	private Context context;
	private ArrayList<String> list;

	public MySimpleListBaseAdapter(int viewResourceId, Context context,
			ArrayList<String> list) {

		this.viewResourceId = viewResourceId;
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		MyPlaceHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(viewResourceId, parent, false);

			holder = new MyPlaceHolder();
			holder.listItemNameText = (TextView) row
					.findViewById(R.id.list_item_name_text);

			row.setTag(holder);

		} else {

			holder = (MyPlaceHolder) row.getTag();
		}

		holder.listItemNameText.setText(list.get(position));

		if (position % 2 == 0) {

			row.setBackgroundColor(context.getResources().getColor(
					R.color.light_list_item_bg));

		} else {

			row.setBackgroundColor(context.getResources().getColor(
					R.color.dark_list_item_bg));
		}
		
		return row;
	}

	class MyPlaceHolder {
		TextView listItemNameText;
	}

}
