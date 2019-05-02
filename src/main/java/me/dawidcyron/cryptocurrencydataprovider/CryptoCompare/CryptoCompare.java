package me.dawidcyron.cryptocurrencydataprovider.CryptoCompare;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.Cryptocurrency;
import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class CryptoCompare {

  @Value("${CRYPTOCOMPARE_KEY:}")
  private String apiKey;

  @JsonIgnore
  private final String CRYPTOCOMPARE_URL =
      "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,BTH,ETH,XRP,LTC,DASH,ZEC,SALT,TRON,XLM,NEO,ADA,XMR,EOS,DOGE&tsyms=USD";

  @JsonIgnore private final int THIRTY_SECONDS = 30000;

  @JsonProperty("RAW")
  // Part of CryptoCompare API response
  Map<String, Map<String, Cryptocurrency>> RAW;

  @Autowired private CryptocurrencyRepository cryptocurrencyRepository;

  @Scheduled(initialDelay = 0, fixedRate = THIRTY_SECONDS)
  // Process CryptoCompare response body and save cryptocurrency info to Redis, executing every 30 seconds.
  public void updateCryptocurrencies() {
    try {
      StringBuffer responseBody = getCryptocurrencyJSON();
      ObjectMapper mapper = new ObjectMapper();
      List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
      CryptoCompare cryptoCompare = mapper.readValue(responseBody.toString(), CryptoCompare.class);
      for (String key : cryptoCompare.RAW.keySet()) {
        cryptocurrencies.add(cryptoCompare.RAW.get(key).get("USD"));
      }
      cryptocurrencyRepository.saveAll(cryptocurrencies);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Make GET request to CryptoCompare API, then read and return the body
  private StringBuffer getCryptocurrencyJSON() throws IOException {
    URL url = new URL(CRYPTOCOMPARE_URL);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("authorization", "Apikey " + apiKey);
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuffer requestBody = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      requestBody.append(inputLine);
    }
    in.close();
    return requestBody;
  }
}
