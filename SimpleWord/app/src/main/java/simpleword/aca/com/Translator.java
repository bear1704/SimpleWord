package simpleword.aca.com;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Translator
{

    public final int KO_TO_EN = 0;
    public final int EN_TO_KO = 1;


    public String translate(String text, final int request, final String clientId, final String clientSecret)
    {
        try
        {
            String inText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            String postParams = null;

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            if(request == KO_TO_EN)
            {postParams = "source=ko&target=en&text=" + inText;}

            else if(request == EN_TO_KO)
            {postParams = "source=en&target=ko&text=" + inText;}

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200)
            { br = new BufferedReader(new InputStreamReader(con.getInputStream()));}
            else
            { br = new BufferedReader(new InputStreamReader(con.getErrorStream()));}

            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = br.readLine()) != null)
            {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        }
        catch(UnsupportedEncodingException ue)
        {
            Log.v("error_", "error1");
        }
        catch(Exception e)
        {
            Log.v("error_", "error2");
            e.printStackTrace();
            Log.v("error_", e.toString());
            Log.v("error_", e.getMessage());
        }

            Log.v("error", "null returned");
            return null;
    }

}


