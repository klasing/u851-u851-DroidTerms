package com.example.droidterms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.droidtermsprovider.DroidTermsExampleContract;

public class MainActivity extends AppCompatActivity {
    private Cursor mData;
    private int mCurrentState;
    private int mDefCol, mWordCol;
    private TextView mWordTextView, mDefinitionTextView;
    private Button mButton;
    private final int STATE_HIDDEN = 0;
    private final int STATE_SHOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWordTextView = (TextView) findViewById(R.id.text_view_word);
        mDefinitionTextView = (TextView) findViewById(R.id.text_view_definition);
        mButton = (Button) findViewById(R.id.button_next);

        new WordFetchTask().execute();
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

        //mButton.setText(getString(R.string.show_definition));

        //mCurrentState = STATE_HIDDEN;

        if (mData != null) {
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }

            mDefinitionTextView.setVisibility(View.INVISIBLE);

            mButton.setText(getString(R.string.show_definition));

            mWordTextView.setText(mData.getString(mWordCol));
            mDefinitionTextView.setText(mData.getString(mDefCol));

            mCurrentState = STATE_HIDDEN;
        }

    }

    public void showDefinition() {

        //mButton.setText(getString(R.string.next_word));

        //mCurrentState = STATE_SHOWN;

        if (mData != null) {
            mDefinitionTextView.setVisibility(View.VISIBLE);

            mButton.setText(getString(R.string.next_word));

            mCurrentState = STATE_SHOWN;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mData.close();
    }

    public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {

            ContentResolver resolver = getContentResolver();

            Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            mData = cursor;

            mDefCol = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
            mWordCol = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);

            nextWord();
        }
    }
}
