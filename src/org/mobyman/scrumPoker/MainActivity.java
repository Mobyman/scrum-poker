package org.mobyman.scrumPoker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ListActivity {
    public static final String APP_PREFERENCES = "scrumPoker";
    public static final String APP_PREFERENCES_MARKS = "marks";

    final String LOG_TAG = "SCRUM-POKER";

    public Log log;

    private ArrayList<ScrumMark>    markArrayList;
    private ListView                markListView;
    private MediaPlayer             mediaPlayer;


    private void initDefaultMarks() {
        markArrayList.add(new ScrumMark(Float.valueOf("0.1")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.2")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.25")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.3")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.4")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.5")));
        markArrayList.add(new ScrumMark(Float.valueOf("0.75")));
        markArrayList.add(new ScrumMark(Float.valueOf("1.0")));
        markArrayList.add(new ScrumMark(Float.valueOf("1.5")));
        markArrayList.add(new ScrumMark(Float.valueOf("2.0")));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markListView = this.getListView();
        markListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Context context = getApplicationContext();
                Adapter a = adapterView.getAdapter();
                ScrumMark currentScrumMark = (ScrumMark) a.getItem(position);
                play(context, "select.mp3");
                Intent intent = new Intent(getBaseContext(), ShowMark.class);
                intent.putExtra("mark", currentScrumMark.getValue());
                startActivity(intent);
            }
        });

        markListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                markArrayList.remove(position);
                generateData();
                return true;
            }
        });

        mediaPlayer = new MediaPlayer();

        markArrayList = new ArrayList<ScrumMark>();
        initDefaultMarks();
        generateData();
    }

    private void generateData(){
        MarkAdapter adapter = new MarkAdapter(this, markArrayList);
        this.setListAdapter(adapter);
    }


    private void play(Context context, String file) {
        try {

            AssetFileDescriptor afd = context.getAssets().openFd(file);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength()
            );
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.create_mark) {
            showDialog(R.layout.addmarklayout);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(getLayoutInflater().inflate(R.layout.addmarklayout, null));
        DialogInterface.OnClickListener createFunction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                EditText newMark = (EditText)((AlertDialog) dialog).findViewById(R.id.newMarkInput);
                if(newMark.getText().toString().length() != 0) {
                    markArrayList.add(new ScrumMark(Float.valueOf(newMark.getText().toString())));
                    newMark.setText("");
                    generateData();
                }
            }
        };

        adb.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        adb.setPositiveButton(R.string.create, createFunction);

        AlertDialog dialog = adb.create();
        dialog.getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
        return dialog;
    }
}
