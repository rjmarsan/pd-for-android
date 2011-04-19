/**
 * @author Martin Roth (mhroth@rjdj.me)
 * @author Peter Brinkmann (peter.brinkmann@gmail.com)
 * 
 * For information on usage and redistribution, and for a DISCLAIMER OF ALL
 * WARRANTIES, see the file, "LICENSE.txt," in this distribution.
 * 
 */

package org.puredata.android.scenes;

import java.io.File;

import org.puredata.android.scenes.SceneDataBase.RecordingColumn;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Simple adapter for displaying recordings.
 */
public class RecordingListCursorAdapter extends CursorAdapter {

  public RecordingListCursorAdapter(Context context, Cursor cursor) {
    super(context, cursor);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView textView = (TextView) view.findViewById(R.id.sceneInfo);
    textView.setText(SceneDataBase.getString(cursor, RecordingColumn.SCENE_TITLE));
    textView = (TextView) view.findViewById(R.id.timeInfo);
    long timestamp = SceneDataBase.getLong(cursor, RecordingColumn.RECORDING_TIMESTAMP);
    textView.setText(DateFormat.format("yyyy-MM-dd hh:mm", timestamp));
    textView = (TextView) view.findViewById(R.id.durationInfo);
    long duration = SceneDataBase.getLong(cursor, RecordingColumn.RECORDING_DURATION);
    textView.setText(DateFormat.format("m:ss", duration));
    ImageView imageView = (ImageView) view.findViewById(R.id.sceneIcon);
    String sceneFolder = SceneDataBase.getString(cursor, RecordingColumn.SCENE_DIRECTORY);
	File file = new File(sceneFolder, "thumb.jpg");
	Drawable icon = Drawable.createFromPath(file.getAbsolutePath());
    if (icon == null) {
    	file = new File(sceneFolder, "image.jpg");
    	icon = Drawable.createFromPath(file.getAbsolutePath());
    }
    if (icon != null) {
    	imageView.setImageDrawable(icon);
    } else {
    	imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.default_thumb));
    }
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View view = View.inflate(context, R.layout.recording_item, null);
    bindView(view, context, cursor);
    return view;
  }
}
