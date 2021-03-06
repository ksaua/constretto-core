package org.constretto.internal.converter;

import org.constretto.ValueConverter;
import org.constretto.exception.ConstrettoConversionException;
import org.constretto.model.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author <a href="mailto:thor.aage.eldby@arktekk.no">Thor Åge Eldby (teldby)</a>
 */
public class PropertyFileValueConverter implements ValueConverter<Properties> {

    public Properties fromString(String resourceName) throws ConstrettoConversionException {
        try {
            Properties properties = new Properties();
            InputStream stream = Resource.create(resourceName).getInputStream();
            if (resourceName.endsWith(".xml")) {
                properties.loadFromXML(stream);
            } else {
                properties.load(stream);
            }
            return properties;
        } catch (IOException e) {
            throw new ConstrettoConversionException(resourceName, Properties.class, e);
        }
    }
}