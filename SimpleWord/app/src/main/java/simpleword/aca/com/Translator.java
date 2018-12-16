package simpleword.aca.com;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Handler;


public class Translator
{

    public final int KO_TO_EN = 0;
    public final int EN_TO_KO = 1;
    public final String error = "error";
    public static final String clientId = "eWBogsUUe9GhsNs7747O";
    public static final String clientSecret = "vLR36Xfcxr";
    TranslateThread tt;

    private String translate(String text, final int request)
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

        }

            Log.v("error_", "null returned");
            return error;
    }


    public class TranslateThread extends Thread
    {

        private int type; //번역 (한->영) (영->한)
        private  String translated; //translate 메소드 리턴을 받는 스트링
        private String str; //초기화를 위한 임시 스트링
        private String stringResult; //최종 결과값

        public TranslateThread(String text, int type)
        {
            this.type = type;
            str = text;
        }

        public void run()
        {
            try
            {
                if(type == KO_TO_EN)
                {
                    translated = translate(str, KO_TO_EN); //API 요청, 번역
                    JSONObject jsonObject = new JSONObject(translated).getJSONObject("message").getJSONObject("result"); //JSON String 형태로 들어오므로 원하는 값만 추출
                    stringResult = jsonObject.getString("translatedText");
                    Log.v("result_", "-----code = " + stringResult);
                }
                else if(type == EN_TO_KO)
                {
                    translated = translate(str, EN_TO_KO); //API 요청, 번역
                    JSONObject jsonObject = new JSONObject(translated).getJSONObject("message").getJSONObject("result"); //JSON String 형태로 들어오므로 원하는 값만 추출
                    stringResult = jsonObject.getString("translatedText");
                    Log.v("result_", "-----code = " + stringResult);
                }
                else
                {
                    Log.v("error_", "type 인식 오류");
                }


            }
            catch(Exception e)
            {
                Log.v("error_", "ERROR OCCUR");
                Log.v("error_", e.toString());
                e.printStackTrace();
            }
        }

        public String getStringResult()
        {
            return stringResult;
        }

    }


    public String Translate(String text, int type)
    {
        String result;
        tt = new TranslateThread(text, type);
        tt.start();

        try
        {
            tt.join(); // 끝날때까지 대기
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        result = tt.getStringResult();
        return result;
    }


}


