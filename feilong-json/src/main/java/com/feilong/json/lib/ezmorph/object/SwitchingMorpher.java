/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.feilong.json.lib.ezmorph.object;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.feilong.json.lib.ezmorph.MorphException;
import com.feilong.json.lib.ezmorph.MorpherRegistry;
import com.feilong.json.lib.ezmorph.ObjectMorpher;

/**
 * An all-purpose Morpher that can morph to several classes.<br>
 * Because this Morpher accepts any class and morphs to Object it should not be
 * added to a MorpherRegistry as it may be too generic for some cases and may
 * result in unwanted transformations.
 *
 * @author <a href="mailto:aalmiray@users.sourceforge.net">Andres Almiray</a>
 */
public class SwitchingMorpher implements ObjectMorpher{

    /** The class map. */
    private final Map             classMap = new HashMap();

    /** The morpher registry. */
    private final MorpherRegistry morpherRegistry;

    /**
     * Instantiates a new switching morpher.
     *
     * @param classMap
     *            the class map
     * @param morpherRegistry
     *            the morpher registry
     */
    public SwitchingMorpher(Map classMap, MorpherRegistry morpherRegistry){
        this.morpherRegistry = morpherRegistry;
        if (classMap == null || classMap.isEmpty()){
            throw new MorphException("Must specify at least one mapping");
        }
        this.classMap.putAll(classMap);
    }

    /**
     * Equals.
     *
     * @param obj
     *            the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }

        if (!(obj instanceof NumberMorpher)){
            return false;
        }

        SwitchingMorpher other = (SwitchingMorpher) obj;
        if (classMap.size() != other.classMap.size()){
            return false;
        }
        for (Iterator entries = classMap.entrySet().iterator(); entries.hasNext();){
            Map.Entry entry = (Map.Entry) entries.next();
            if (!other.classMap.containsKey(entry.getKey())){
                return false;
            }
            if (!entry.getValue().equals(other.classMap.get(entry.getKey()))){
                return false;
            }
        }
        return true;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode(){
        HashCodeBuilder builder = new HashCodeBuilder();
        for (Iterator entries = classMap.entrySet().iterator(); entries.hasNext();){
            Map.Entry entry = (Map.Entry) entries.next();
            builder.append(entry.getKey());
            builder.append(entry.getValue());
        }
        return builder.toHashCode();
    }

    /**
     * Morph.
     *
     * @param value
     *            the value
     * @return the object
     */
    @Override
    public Object morph(Object value){
        if (value == null){
            return null;
        }

        Class target = (Class) classMap.get(value.getClass());
        return morpherRegistry.morph(target, value);
    }

    /**
     * Morphs to.
     *
     * @return the class
     */
    @Override
    public Class morphsTo(){
        return Object.class;
    }

    /**
     * Supports.
     *
     * @param clazz
     *            the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class clazz){
        return true;
    }
}