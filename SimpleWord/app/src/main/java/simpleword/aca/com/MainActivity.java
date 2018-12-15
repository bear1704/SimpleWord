package simpleword.aca.com;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private static final String clientId = "eWBogsUUe9GhsNs7747O";
    private static final String clientSecret = "vLR36Xfcxr";
    TextView tv_transResult;
    Translator tl;
    private String translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_transResult = findViewById(R.id.text1);
        tl = new Translator();

        translatedText = tl.Translate("고구마", tl.KO_TO_EN);
        tv_transResult.setText(translatedText);

    }



}

