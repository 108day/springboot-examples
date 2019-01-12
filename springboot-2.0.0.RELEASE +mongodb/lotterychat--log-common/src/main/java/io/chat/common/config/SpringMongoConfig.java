package io.chat.common.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Configuration
public class SpringMongoConfig {

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
        MongoClientOptions opt = MongoClientOptions.builder()
                .writeConcern( WriteConcern.JOURNALED)
                .build();

        MongoClient my = new MongoClient(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()), opt);
        return my;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient,MongoProperties mongoProperties) {
        return new SimpleMongoDbFactory(mongoClient, mongoProperties.getDatabase());
    }
    
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,MappingMongoConverter mappingMongoConverter) throws Exception {
        MongoTemplate my = new MongoTemplate(mongoDbFactory,mappingMongoConverter);
        my.setWriteResultChecking( WriteResultChecking.EXCEPTION );
        return my;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory mongoDbFactory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }
 
        // Don't save _class to mongo
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
 
        return mappingConverter;
    }

}
