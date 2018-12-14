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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_transResult = findViewById(R.id.text1);
        tl = new Translator();

        new Thread()
        {
            public void run()
            {
                try
                {
                    String translated = tl.translate("만나서 반갑습니다.", tl.KO_TO_EN, clientId, clientSecret);
                    JSONObject jsonObject = new JSONObject(translated).getJSONObject("message").getJSONObject("result");
                    String translatedFinal = jsonObject.getString("translatedText");
                    Log.v("error_", "-----code = " + translatedFinal);
                }
                catch(Exception e)
                {
                    Log.v("error_", "ERROR OCCUR");
                    Log.v("error_", e.toString());
                    e.printStackTrace();
                }
            }


        }.start();



    }





}

