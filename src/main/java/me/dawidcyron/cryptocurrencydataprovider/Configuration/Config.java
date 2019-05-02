package me.dawidcyron.cryptocurrencydataprovider.Configuration;

import me.dawidcyron.cryptocurrencydataprovider.Cryptocurrency.Cryptocurrency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class Config {

  @Value("${HOSTNAME:localhost}")
  private String hostname;
  @Value("${REDIS_PORT:6379}")
  private int redisPort;
  @Value("${REDIS_PASSWORD:}")
  private String redisPassword;

  @Bean
  LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(hostname);
    configuration.setPort(redisPort);
    configuration.setPassword(redisPassword);
    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisTemplate<String, Cryptocurrency> redisTemplate() {
    RedisTemplate<String, Cryptocurrency> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }
}
