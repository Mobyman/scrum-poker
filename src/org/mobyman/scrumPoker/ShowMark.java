package org.mobyman.scrumPoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ShowMark extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.markfull);
        Intent intent = getIntent();

        Float mark = intent.getFloatExtra("mark", 0);
        TextView markText = (TextView) findViewById(R.id.fullMarkText);

        markText.setText(mark.toString());
    }
}