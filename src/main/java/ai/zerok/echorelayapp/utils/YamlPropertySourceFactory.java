package ai.zerok.echorelayapp.utils;

import com.mongodb.lang.Nullable;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties loadedProperties = this.loadYamlIntoProperties(resource.getResource());

        return new PropertiesPropertySource((StringUtils.isNotBlank(name)) ? name : resource.getResource().getFilename(), loadedProperties);
    }

    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}

//public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {
//    @Override
//    public PropertySource createPropertySource(String name, EncodedResource resource) throws IOException {
//        if (resource == null) {
//            return super.createPropertySource(name, resource);
//        }
//        List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
//        if (!propertySourceList.isEmpty()) {
//            return propertySourceList.iterator().next();
//        }
//        return super.createPropertySource(name, resource);
//    }
//}

//public class YamlPropertySourceFactory implements PropertySourceFactory {
//
//    @Override
//    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
//            throws IOException {
//        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//        factory.setResources(encodedResource.getResource());
//
//        Properties properties = factory.getObject();
//
//        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
//    }
//}



//
//public class YamlPropertySourceFactory implements PropertySourceFactory {
//
//    /**
//     * Create a {@link PropertySource} that wraps the given resource.
//     *
//     * @param name     the name of the property source
//     * @param resource the resource (potentially encoded) to wrap
//     * @return the new {@link PropertySource} (never {@code null})
//     * @throws IOException if resource resolution failed
//     */
//    @Override
//    public PropertySource<?> createPropertySource(String name, EncodedResource resource)
//            throws IOException {
//        Properties properties = load(resource);
//        return new PropertiesPropertySource(name != null ? name :
//                Objects.requireNonNull(resource.getResource().getFilename(), "Some error message"),
//                properties);
//    }
//
//    /**
//     * Load properties from the YAML file.
//     *
//     * @param resource Instance of {@link EncodedResource}
//     * @return instance of properties
//     */
//    private Properties load(EncodedResource resource) throws FileNotFoundException {
//        try {
//            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//            factory.setResources(resource.getResource());
//            factory.afterPropertiesSet();
//
//            return factory.getObject();
//        } catch (IllegalStateException ex) {
//            /*
//             * Ignore resource not found.
//             */
//            Throwable cause = ex.getCause();
//            if (cause instanceof FileNotFoundException) throw (FileNotFoundException) cause;
//            throw ex;
//        }
//    }
//}




//
//public class YamlPropertySourceFactory implements PropertySourceFactory {
//
//    @Override
//    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
//            throws IOException {
//        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//        factory.setResources(encodedResource.getResource());
//
//        Properties properties = factory.getObject();
//
//        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
//    }
//}