package API;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("your/api/url")
    Observable<String> getResponse();
}