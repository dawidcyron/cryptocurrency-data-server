package me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptocurrencyRepository extends CrudRepository<Cryptocurrency, String> {}
