package com.cognitiveadventures.note;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NoteListAdapter extends SimpleCursorAdapter {
	
	private Context mContext;
	private Cursor mCursor;
	private int titleIndex;
	private ArrayList<Long> deletePos;

	public NoteListAdapter(Context context, int layout, Cursor c, String from, int to) {
		
		super(context, layout, c, new String[] { from }, new int[] { to });
		
		mContext = context;
		
		mCursor = c;
		
		titleIndex = c.getColumnIndexOrThrow(from);
		
		deletePos = new ArrayList<Long>();
	}
	
	public ArrayList<Long> getSelectedItems() {
		
		return(deletePos);
	}
	
	public int getSelectedCount() {
		
		return(deletePos.size());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(mCursor.moveToPosition(position)) {
			
			ViewHolder vHolder;
			
			if(convertView == null) {
				
				convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loadnotes_item, null);
				
				vHolder = new ViewHolder();
				
				vHolder.noteTitleHolder = (TextView) convertView.findViewById(R.id.noteTitle);
				vHolder.noteCheckBoxHolder = (CheckBox) convertView.findViewById(R.id.noteCheckBox);
				
				vHolder.id = mCursor.getLong(mCursor.getColumnIndexOrThrow(SQLAdapter.KEY_ROWID));
				
				vHolder.noteCheckBoxHolder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if(isChecked)
							deletePos.add(mCursor.getLong(mCursor.getColumnIndexOrThrow(SQLAdapter.KEY_ROWID)));
						else
							deletePos.remove(mCursor.getLong(mCursor.getColumnIndexOrThrow(SQLAdapter.KEY_ROWID)));
					}
				});
				
				convertView.setTag(vHolder);
			}
			else
				vHolder = (ViewHolder) convertView.getTag();
			
			vHolder.noteTitleHolder.setText(mCursor.getString(titleIndex));
		}
		
		return(convertView);
	}
	
	public class ViewHolder {
		
		TextView noteTitleHolder;
		CheckBox noteCheckBoxHolder;
		long id;
	}

}