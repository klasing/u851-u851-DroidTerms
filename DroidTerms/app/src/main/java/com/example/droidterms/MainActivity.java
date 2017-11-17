package com.example.droidterms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private int mCurrentState;
    private Button mButton;
    private final int STATE_HIDDEN = 0;
    private final int STATE_SHOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button_next);
    }

    public void onButtonClick(View view) {

        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {

        mButton.setText(getString(R.string.show_definition));

        mCurrentState = STATE_HIDDEN;

    }

    public void showDefinition() {

        mButton.setText(getString(R.string.next_word));

        mCurrentState = STATE_SHOWN;

    }
}
