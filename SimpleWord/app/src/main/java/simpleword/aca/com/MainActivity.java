package simpleword.aca.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static final String clientId = "eWBogsUUe9GhsNs7747O";
    private static final String clientSecret = "vLR36Xfcxr";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_hello = findViewById(R.id.text1);

        Translator tl = new Translator();
        String hello = tl.translate("만나서 반갑습니다.", tl.KO_TO_EN, clientId, clientSecret);
        tv_hello.setText(hello);

    }
}

