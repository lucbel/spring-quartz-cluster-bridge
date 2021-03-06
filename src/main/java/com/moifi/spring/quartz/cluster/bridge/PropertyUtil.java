package com.moifi.spring.quartz.cluster.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by rodrigo on 15/12/15.
 *
 */
@Component
public class PropertyUtil {


    public Integer getInteger(PropertyResolver propertyResolver, String key) throws ValidationException{
            String property = propertyResolver.getProperty(key);
            if (property != null) {
                // Not contains a non-numeric character
                if (property.matches("^[0-9]")) {
                    throw new ValidationException("The property with key " + key + " it should be a number.");
                }
                try{
                    return new Integer(property);
                } catch (NumberFormatException e){
                    throw new ValidationException("The property has and invalid format, it should be an integer",e);
                }
            } else {
                throw new ValidationException("The key cannot be null");
            }

    }

}
