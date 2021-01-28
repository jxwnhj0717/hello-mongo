package com.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.convert.MappingContextTypeInformationMapper;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class MongoConfig extends MongoConfigurationSupport {

    @Autowired
    private Environment env;

    @Override
    protected Set<Class<?>> scanForEntities(String basePackage) throws ClassNotFoundException {
        if (!StringUtils.hasText(basePackage)) {
            return Collections.emptySet();
        }

        Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();

        if (StringUtils.hasText(basePackage)) {

            ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(
                    false);
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Document.class));
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(TypeAlias.class));

            for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {

                initialEntitySet
                        .add(ClassUtils.forName(candidate.getBeanClassName(), MongoConfigurationSupport.class.getClassLoader()));
            }
        }

        return initialEntitySet;
    }

    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context,
                                                       MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);

        MappingContextTypeInformationMapper mapper = new MappingContextTypeInformationMapper(context);
        DefaultMongoTypeMapper defaultMongoTypeMapper = new DefaultMongoTypeMapper(
                DefaultMongoTypeMapper.DEFAULT_TYPE_KEY,
                Collections.singletonList(mapper));
        mappingConverter.setTypeMapper(defaultMongoTypeMapper);
        return mappingConverter;
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
        super.configureConverters(converterConfigurationAdapter);
        converterConfigurationAdapter.registerConverter(new EnumWithIdToIntegerConverter());
        converterConfigurationAdapter.registerConverterFactory(new EnumWithIdConverterFactory());
    }
}
