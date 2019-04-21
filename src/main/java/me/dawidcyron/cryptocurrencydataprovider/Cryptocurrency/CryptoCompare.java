package me.dawidcyron.cryptocurrencydataprovider.CryptoCompare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.Cryptocurrency;
import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCompare {

  @JsonProperty("RAW")
  Map<String, Map<String, Cryptocurrency>> RAW;

  @Scheduled(initialDelay = 0, fixedRate = 30000)
  public void updateCrypto() {
    try {
      URL url =
          new URL("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,ETH&tsyms=USD");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      ObjectMapper mapper = new ObjectMapper();
      List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
      CryptoCompare cryptoCompare = mapper.readValue(content.toString(), CryptoCompare.class);
      for(String key : cryptoCompare.RAW.keySet()) {
        cryptocurrencies.add(cryptoCompare.RAW.get(key).get("USD"));
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
