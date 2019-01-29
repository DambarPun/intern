package com.example.android.counsellingrequest;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSourceRequest {
    public Handler handler;
    public String Url;
    HTTPExtract httpExtract;
    public HttpSourceRequest(Handler handler, String Url){
        this.handler = handler;
        this.Url = Url;
        //---------------------------->
        httpExtract = new HTTPExtract();
        httpExtract.execute();
    }


    private class HTTPExtract extends AsyncTask<Void,Void,Void> {
        String Source="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            Source = SourceCode(Url);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Message message = new Message();
            message.obj = Source;
            handler.sendMessage(message);
        }
        public String SourceCode(String urlString){
            String source ="";
            try{
                //SEt the URL first
                //Note* 10.0.3.2 (IP PC localhost)
                URL url = new URL(urlString.replace(" ", "%20"));
                /*
                An URLConnection for HTTP used to send and receive data over the web.Data may be of any type and length.
                This class may be used to send and receive streaming data whose length is not known in advance.
                 */
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                /*
                InputStream is a abstract different ways to input: whether the stream is a file, a web page.
                Receive information from the stream (or send information into that stream.)
                InputStream is used for many things that you read from.
               */
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                if(inputStream !=null) {
                    /*
                    BufferedReader:
                    Reads text from a character-input stream, buffering characters so as to provide for the efficient
                    reading of characters, arrays, and lines. The buffer size may be specified, or the default size
                    may be used. The default is large enough for most purposes.

                    InputStreamReader:
                    It is a bridge from byte streams to character streams.It reads bytes and
                    decodes them into characters using a specified charset.

                    International Organization for Standardization
                    ISO/IEC 8859-1:1998, Information technology — 8-bit single-byte coded graphic character sets
                     */
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    /*
                    StringBuilder:
                     A modifiable for use in creating strings.
                     */
                    StringBuilder stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {//Read All the line
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    source = stringBuilder.toString();
                }
                urlConnection.disconnect();
            }
            catch (Exception ex){
                Log.e("Error", ex.toString());
            }
            return source.trim();
        }
    }
}
