/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.constretto.internal.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.constretto.ValueConverter;
import org.constretto.exception.ConstrettoConversionException;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author trygvis
 */
public class InetAddressValueConverter implements ValueConverter<InetAddress> {
    private final Type listType = new TypeToken<List<String>>() {}.getType();
    private final Gson gson = new Gson();

    public InetAddress fromString(String value) throws ConstrettoConversionException {
        try {
            return InetAddress.getByName(value);
        } catch (UnknownHostException e) {
            throw new ConstrettoConversionException(value, InetAddress.class, e);
        }
    }

    public List<InetAddress> fromStrings(String value) throws ConstrettoConversionException {
        List<InetAddress> inetAddresses = new ArrayList<InetAddress>();
        List<String> inetAddrStrings = gson.fromJson(value,listType);
        for (String inetAddrString : inetAddrStrings) {
            inetAddresses.add(fromString(inetAddrString));
        }
        return inetAddresses;
    }
}
