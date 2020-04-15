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
package com.feilong.xml.xstream;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.store.member.User;

public class FromXmlXStreamConfigTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FromXmlXStreamConfigTest.class);

    @Test
    public void test(){
        String xml = "<user><name>feilong</name><id>1</id></user>";

        XStreamConfig toXmlConfig = new XStreamConfig();
        toXmlConfig.getAliasMap().put("user", User.class);

        User user = XStreamUtil.fromXML(xml, toXmlConfig);

        assertThat(user, allOf(hasProperty("name", is("feilong")), hasProperty("id", is(1L))));
    }

    //---------------------------------------------------------------

    @Test
    public void testToXML(){
        User user = new User(1L);

        XStreamConfig toXmlConfig = new XStreamConfig();
        toXmlConfig.getAliasMap().put("user", User.class);

        LOGGER.debug(XStreamUtil.toXML(user, toXmlConfig));
        LOGGER.debug(XStreamUtil.toXML(user, null));
    }

}