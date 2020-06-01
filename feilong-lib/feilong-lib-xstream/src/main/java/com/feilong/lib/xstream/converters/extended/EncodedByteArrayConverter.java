/*
 * Copyright (C) 2004 Joe Walnes.
 * Copyright (C) 2006, 2007, 2010, 2017, 2018 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 03. March 2004 by Joe Walnes
 */
package com.feilong.lib.xstream.converters.extended;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.feilong.lib.xstream.converters.Converter;
import com.feilong.lib.xstream.converters.MarshallingContext;
import com.feilong.lib.xstream.converters.SingleValueConverter;
import com.feilong.lib.xstream.converters.UnmarshallingContext;
import com.feilong.lib.xstream.converters.basic.ByteConverter;
import com.feilong.lib.xstream.core.JVM;
import com.feilong.lib.xstream.core.StringCodec;
import com.feilong.lib.xstream.io.HierarchicalStreamReader;
import com.feilong.lib.xstream.io.HierarchicalStreamWriter;

/**
 * Converts a byte array by default to a single Base64 encoding string.
 *
 * @author Joe Walnes
 * @author J&ouml;rg Schaible
 */
public class EncodedByteArrayConverter implements Converter, SingleValueConverter {

    private static final ByteConverter byteConverter = new ByteConverter();
    private final StringCodec codec;

    /**
     * Constructs an EncodedByteArrayConverter. Initializes the converter with a Base64 codec.
     */
    public EncodedByteArrayConverter() {
        this(JVM.getBase64Codec());
    }

    /**
     * Constructs an EncodedByteArrayConverter with a provided string codec.
     *
     * @param stringCodec the codec to encode and decode the data as string
     * @since 1.4.11
     */
    public EncodedByteArrayConverter(final StringCodec stringCodec) {
        codec = stringCodec;
    }

    @Override
    public boolean canConvert(Class type) {
        return type != null && type.isArray() && type.getComponentType().equals(byte.class);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.setValue(toString(source));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String data = reader.getValue(); // needs to be called before hasMoreChildren.
        if (!reader.hasMoreChildren()) {
            return fromString(data);
        } else {
            // backwards compatibility ... try to unmarshal byte arrays that haven't been encoded
            return unmarshalIndividualByteElements(reader, context);
        }
    }

    private Object unmarshalIndividualByteElements(HierarchicalStreamReader reader, UnmarshallingContext context) {
        List bytes = new ArrayList(); // have to create a temporary list because don't know the size of the array
        boolean firstIteration = true;
        while (firstIteration || reader.hasMoreChildren()) { // hangover from previous hasMoreChildren
            reader.moveDown();
            bytes.add(byteConverter.fromString(reader.getValue()));
            reader.moveUp();
            firstIteration = false;
        }
        // copy into real array
        byte[] result = new byte[bytes.size()];
        int i = 0;
        for (Iterator iterator = bytes.iterator(); iterator.hasNext();) {
            Byte b = (Byte) iterator.next();
            result[i] = b.byteValue();
            i++;
        }
        return result;
    }

    @Override
    public String toString(final Object obj) {
        return codec.encode((byte[])obj);
    }

    @Override
    public Object fromString(final String str) {
        return codec.decode(str);
    }
}
