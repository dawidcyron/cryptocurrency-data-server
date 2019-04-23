package me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptocurrencyController {
  @Autowired CryptocurrencyRepository cryptocurrencyRepository;

  @GetMapping("/")
  public ResponseEntity<Iterable<Cryptocurrency>> getCrypots() {
    return new ResponseEntity<>(cryptocurrencyRepository.findAll(), HttpStatus.OK);
  }
}
