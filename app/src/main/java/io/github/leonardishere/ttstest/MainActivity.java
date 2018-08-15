package io.github.leonardishere.ttstest;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private EditText editText = null;
    private TextView textView = null;
    private TextToSpeech ttobj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext_1);
        textView = findViewById(R.id.textview_1);
        addStatus("please wait");
        //ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        ttobj = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.ERROR){
                    addStatus("TTS failure on init");
                }else if(status == TextToSpeech.SUCCESS) {
                    addStatus("TTS ready");
                }
                ttobj.setLanguage(Locale.US);
                ttobj.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        addStatus("TTS start");
                    }

                    @Override
                    public void onDone(String s) {
                        addStatus("TTS done");
                    }

                    @Override
                    public void onError(String s) {
                        addStatus("TTS error");
                    }
                });
            }
        });
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://test.aleonard.corp.he.net/spoken_tutor/select_all_subjects.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                addStatus("http success: " + new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                addStatus("http failure: " + throwable.toString());
            }
        });
    }

    public void click(View view){
        addStatus("Click");
        String toSpeak = editText.getText().toString();

        /*
        Bundle bundle = new Bundle();
        bundle.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, bundle, "uniqueId2");
        */

        /*
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, map);
        */

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttobj.shutdown();
    }

    public void addStatus(String message){
        String str = String.format(Locale.US, "%s\n%s", message, textView.getText());
        textView.setText(str);
    }
}
