package org.mobyman.scrumPoker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;


public class ShowMark extends Activity {

    private enum ACTIONS {

        REJECT {
            public String getFile() {
                return "reject.mp3";
            }
        },
        ACCEPT {
            public String getFile() {
                return "accept.mp3";
            }
        };

        public abstract String getFile();

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.markfull);
        Intent intent = getIntent();

        Float mark = intent.getFloatExtra("mark", 0);
        TextView markText = (TextView) findViewById(R.id.fullMarkText);

        mark = BigDecimal.valueOf(mark).setScale(3, BigDecimal.ROUND_HALF_DOWN).floatValue();
        markText.setText(mark.toString());





        Button reject = (Button) findViewById(R.id.reject);
        Button accept = (Button) findViewById(R.id.accept);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMedia(ACTIONS.REJECT);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMedia(ACTIONS.ACCEPT);
            }
        });
    }

    private void playMedia(ACTIONS action) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            Context context = getApplicationContext();
            AssetFileDescriptor afd = context.getAssets().openFd(action.getFile());
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
}