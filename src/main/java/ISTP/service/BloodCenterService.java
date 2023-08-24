package ISTP.service;

import ISTP.dtos.bloodCenter.BloodCenterDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodCenterService {
    public List<BloodCenterDTO> API(String region) throws Exception {
        String apiKey = "iLym7e7dn3giI%2BmdG330sFyy4RnUHHqDIFfthi7pYXmL9y1sP1DBmODFnyAMr5NxVTsltnvwVXPU7n9irOrADA%3D%3D";
        String uddi = "d70c42b0-ffe2-4ad0-9373-c2d37a0947f3";
            StringBuilder baseURL = new StringBuilder("http://api.odcloud.kr/api/15050729/v1/uddi:")
                .append(uddi)
                .append("?").append("page").append("=").append("1")
                .append("&").append("perPage").append("=").append("10")
                .append("&").append("serviceKey=").append(apiKey);
        // url 매핑해서 데이터 가져오는 것

        URL url = new URL(baseURL.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Conetnt-type", "application/json");

        BufferedReader reader;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        conn.disconnect();

        // 여기까지 JSON으로 파싱

        String jsonStr = builder.toString();
        JSONObject jsonObject = new JSONObject(jsonStr);
        // String jsonData = jsonObject.toString(2); // 들여쓰기 2칸

        // JSON 데이터 정렬

        JSONArray dataArray = jsonObject.getJSONArray("data");
        List<BloodCenterDTO> result = new ArrayList<>();
        int currentPage = 1;

        while (true) {
            List<BloodCenterDTO> pageData = fetchDataByPage(currentPage);
            if (currentPage == 16) { // 여기 수정
                break; // 페이지 데이터가 없으면 종료
            }
            result.addAll(pageData);
            currentPage++;
        }

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            BloodCenterDTO centerDTO = new BloodCenterDTO();
            centerDTO.setPhoneNumber(dataObject.getString("전화번호"));
            centerDTO.setAddress(dataObject.getString("주소지"));
            centerDTO.setDonationCenter(dataObject.getString("헌혈의 집"));
            result.add(centerDTO);
        }

        List<BloodCenterDTO> resultData = result.subList(0, Math.min(result.size(), 150));

        List<BloodCenterDTO> regionData = new ArrayList<>();
        for(int k = 0; k < 150; k++) {
            if(resultData.get(k).getAddress().contains(region)) {
                regionData.add(resultData.get(k));
            }
        }

        return regionData;
    }

    public List<BloodCenterDTO> fetchDataByPage(int page) throws Exception {
        String apiKey = "iLym7e7dn3giI%2BmdG330sFyy4RnUHHqDIFfthi7pYXmL9y1sP1DBmODFnyAMr5NxVTsltnvwVXPU7n9irOrADA%3D%3D";
        String uddi = "d70c42b0-ffe2-4ad0-9373-c2d37a0947f3";
        StringBuilder baseURL = new StringBuilder("http://api.odcloud.kr/api/15050729/v1/uddi:")
                .append(uddi)
                .append("?").append("page").append("=").append(page)
                .append("&").append("perPage").append("=").append("10")
                .append("&").append("serviceKey=").append(apiKey);

        URL url = new URL(baseURL.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Conetnt-type", "application/json");

        BufferedReader reader;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        conn.disconnect();

        List<BloodCenterDTO> pageData = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(builder.toString());
        JSONArray dataArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            BloodCenterDTO centerDTO = new BloodCenterDTO();
            centerDTO.setPhoneNumber(dataObject.getString("전화번호"));
            centerDTO.setAddress(dataObject.getString("주소지"));
            centerDTO.setDonationCenter(dataObject.getString("헌혈의 집"));
            pageData.add(centerDTO);
        }

        return pageData;
    }
}
