package me.dawidcyron.cryptocurrencydataprovider.Configuration;

import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.Cryptocurrency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class Config {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Cryptocurrency> redisTemplate() {
    RedisTemplate<String, Cryptocurrency> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
