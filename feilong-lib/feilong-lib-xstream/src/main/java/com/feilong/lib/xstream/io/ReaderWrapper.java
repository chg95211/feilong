/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2011 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 10. April 2005 by Joe Walnes
 */
package com.feilong.lib.xstream.io;

import java.util.Iterator;

import com.feilong.lib.xstream.converters.ErrorWriter;

/**
 * Base class to make it easy to create wrappers (decorators) for HierarchicalStreamReader.
 *
 * @author Joe Walnes
 */
public abstract class ReaderWrapper implements ExtendedHierarchicalStreamReader {

    protected HierarchicalStreamReader wrapped;

    protected ReaderWrapper(HierarchicalStreamReader reader) {
        this.wrapped = reader;
    }

    @Override
    public boolean hasMoreChildren() {
        return wrapped.hasMoreChildren();
    }

    @Override
    public void moveDown() {
        wrapped.moveDown();
    }

    @Override
    public void moveUp() {
        wrapped.moveUp();
    }

    @Override
    public String getNodeName() {
        return wrapped.getNodeName();
    }

    @Override
    public String getValue() {
        return wrapped.getValue();
    }

    @Override
    public String getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    @Override
    public String getAttribute(int index) {
        return wrapped.getAttribute(index);
    }

    @Override
    public int getAttributeCount() {
        return wrapped.getAttributeCount();
    }

    @Override
    public String getAttributeName(int index) {
        return wrapped.getAttributeName(index);
    }

    @Override
    public Iterator getAttributeNames() {
        return wrapped.getAttributeNames();
    }

    @Override
    public void appendErrors(ErrorWriter errorWriter) {
        wrapped.appendErrors(errorWriter);
    }

    @Override
    public void close() {
        wrapped.close();
    }

    @Override
    public String peekNextChild() {
        if (! (wrapped instanceof ExtendedHierarchicalStreamReader)) {
            throw new UnsupportedOperationException("peekNextChild");
        }
        return ((ExtendedHierarchicalStreamReader)wrapped).peekNextChild();
    }

    @Override
    public HierarchicalStreamReader underlyingReader() {
        return wrapped.underlyingReader();
    }
}
