package Models;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServletTask extends AsyncTask<String, Void, String> {

    public String response;
    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String result = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // Здесь обрабатывайте полученный ответ
        response = result;
        if (result != null) {
            response = result;
            Log.d("ServletTask", "Response from the servlet: " + result);
        } else {
            // обработать ошибку
            Log.e("ServletTask", "Error fetching response from servlet");
        }
    }
}