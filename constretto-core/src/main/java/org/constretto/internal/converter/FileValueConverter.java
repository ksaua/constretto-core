/*
 * Copyright 2011 the original author or authors.
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

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author trygvis
 */
public class FileValueConverter implements ValueConverter<File> {
    private final Type listType = new TypeToken<List<String>>() {}.getType();
    private final Gson gson = new Gson();

    private final File basedir;
    private final boolean convertToAbsolute;

    public FileValueConverter() {
        this(null);
    }

    public FileValueConverter(File basedir) {
        this(basedir, false);
    }

    public FileValueConverter(File basedir, boolean convertToAbsolute) {
        this.basedir = basedir;
        this.convertToAbsolute = convertToAbsolute;
    }

    public File fromString(String value) throws ConstrettoConversionException {
        File f = new File(value);

        if (basedir == null) {
            return convertToAbsolute(f);
        }

        if (f.isAbsolute()) {
            return f;
        }

        return convertToAbsolute(new File(basedir, value));
    }

    public List<File> fromStrings(String value) throws ConstrettoConversionException {
        List<File> filez = new ArrayList<File>();
        List<String> fileNames = gson.fromJson(value,listType);
        for (String fileName : fileNames) {
            filez.add(fromString(fileName));
        }
        return filez;
    }

    private File convertToAbsolute(File f) {
        return convertToAbsolute ? f.getAbsoluteFile() : f;
    }
}