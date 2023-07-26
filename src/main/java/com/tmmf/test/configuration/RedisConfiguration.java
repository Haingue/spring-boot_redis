package com.tmmf.test.configuration;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value("${spring.database.redis.host}")
    private String redisHost;
    @Value("${spring.database.redis.port}")
    private int redisPort;
    @Value("${spring.database.redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration
                .builder()
//                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        serverConfig.setPassword(redisPassword);

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisClientConfiguration clientConfig = JedisClientConfiguration
//                .builder()
//                .build();
//        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
//        serverConfig.setPassword(redisPassword);
//
//        return new JedisConnectionFactory(serverConfig, clientConfig);
//    }
}
