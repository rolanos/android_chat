package API;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {
    private final OkHttpClient client;

    public ApiService() {
        client = new OkHttpClient.Builder().build();
    }

    public Single<String> makeRequest(String url) {
        return Single.fromCallable(() -> {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("Failed to make request: " + response);
                    }
                    return response.body().string();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}