import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YandexWeatheApi {
    public static void main(String[] args) {
        try {
            String lat = "52.426426";
            String lon = "37.608409";

            String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon;

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Yandex-API-Key", "4ad9aa59-c3e1-4537-9b03-df8442e018f2");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            System.out.println("Весь ответ с сервера " + jsonResponse);

            int tempStartIndex = jsonResponse.indexOf("\"temp\":") + 7;
            int tempEndIndex = jsonResponse.indexOf(",", tempStartIndex);
            String temp = jsonResponse.substring(tempStartIndex, tempEndIndex);
            System.out.println("Температура: " + temp);

            int limit = 5; // Example limit
            int sum = 0;
            for (int i = 0; i < limit; i++) {
                int tempValue = Integer.parseInt(jsonResponse.substring(jsonResponse.indexOf("\"temp\":") + 7,
                        jsonResponse.indexOf(",", jsonResponse.indexOf("\"temp\":") + 7)));

                sum += tempValue;
                jsonResponse = jsonResponse.substring(jsonResponse.indexOf(",", jsonResponse.indexOf("\"temp\":") + 7) + 1);
            }
            double averageTemp = sum / limit;

            System.out.println("Средняя температура за " + limit + " дня составляет " + averageTemp + " градусов.");


        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}