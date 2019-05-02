package me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
public class CryptocurrencyController {
  @Autowired CryptocurrencyRepository cryptocurrencyRepository;

  @GetMapping("/api")
  public ResponseEntity getSelectedCryptocurrencies(
      @RequestParam(value = "name", required = false) HashSet<String> cryptoList) {
    if (cryptoList == null) {
      return ResponseEntity.status(HttpStatus.OK).body(cryptocurrencyRepository.findAll());
    } else {
      if (cryptoList.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No value passed with parameter!");
      }
      return ResponseEntity.status(HttpStatus.OK).body(cryptocurrencyRepository.findAllById(cryptoList));
    }
  }
}
