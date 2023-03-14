package sixman.helfit.utils.explorer;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class FoodApiExplorer {

    @PostConstruct
    void getDataFromOpenApi() throws IOException {
        // getData();
    }

    void getData() throws IOException {
        final String END_POINT = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
        final String SERVICE_KEY_ENCODE = "";

        StringBuilder urlBuilder = new StringBuilder(END_POINT);

        // Service Key
        urlBuilder
            .append("?")
            .append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8))
            .append("=")
            .append(SERVICE_KEY_ENCODE);

        // 식품이름 (Optional)
        // urlBuilder
        //     .append("&")
        //     .append(URLEncoder.encode("desc_kor", StandardCharsets.UTF_8))
        //     .append("=")
        //     .append(URLEncoder.encode("바나나칩", StandardCharsets.UTF_8));

        // 페이지번호 (Optional)
        // urlBuilder
        //     .append("&")
        //     .append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8))
        //     .append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8));

        // 한 페이지 결과 수 (Optional)
        // urlBuilder
        //     .append("&")
        //     .append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8))
        //     .append("=")
        //     .append(URLEncoder.encode("100", StandardCharsets.UTF_8));

        // 구축년도 (Optional)
        // urlBuilder
        //     .append("&")
        //     .append(URLEncoder.encode("bgn_year", StandardCharsets.UTF_8))
        //     .append("=")
        //     .append(URLEncoder.encode("2017", StandardCharsets.UTF_8));

        // 가공업체 (Optional)
        // urlBuilder
        //     .append("&")
        //     .append(URLEncoder.encode("animal_plant", StandardCharsets.UTF_8))
        //     .append("=")
        //     .append(URLEncoder.encode("(유)돌코리아", StandardCharsets.UTF_8));

        // 응답데이터 형식(xml/json) Default: xml - (Optional)
        urlBuilder
            .append("&")
            .append(URLEncoder.encode("type", StandardCharsets.UTF_8))
            .append("=")
            .append(URLEncoder.encode("json", StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        else
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) sb.append(line);

        br.close();
        conn.disconnect();

        System.out.println(sb);
    }
}
