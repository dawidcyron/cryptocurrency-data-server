package me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Cryptocurrency")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cryptocurrency {
  @JsonProperty("FROMSYMBOL")
  @Id
  String fromSymbol;

  @JsonProperty("PRICE")
  Double price;

  @JsonProperty("LASTUPDATE")
  String lastUpdate;

  @JsonProperty("OPENDAY")
  String openDay;

  @JsonProperty("HIGHDAY")
  String highDay;

  @JsonProperty("LOWDAY")
  String lowDay;

  @JsonProperty("CHANGEPCTDAY")
  String changePercentageDay;
}
