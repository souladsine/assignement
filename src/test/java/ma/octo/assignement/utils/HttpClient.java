package ma.octo.assignement.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {
    public static <T> T post(String url,HttpHeaders headers, MultiValueMap<String, String> body, Class<T> tclass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, request, tclass);
        return response.getBody();
    }

    public static <T> T get(String url, Class<T> tClass){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, tClass);
    }
}
