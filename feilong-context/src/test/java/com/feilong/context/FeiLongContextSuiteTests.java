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
package com.feilong.context;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.context.codecreator.FeiLongCodeSuiteTests;
import com.feilong.context.condition.SimpleParamNameConfigConditionTest;
import com.feilong.context.format.JsonStringFormatterTest;
import com.feilong.context.format.XMLStringFormatterTest;
import com.feilong.context.invoker.http.HttpRequestUriResolverTest;
import com.feilong.context.valueloader.SimpleParamNameValueLoaderTest;

@RunWith(Suite.class)
@SuiteClasses({ //

                SimpleValueLoaderTest.class,

                SimpleParamNameValueLoaderTest.class,
                SimpleParamNameConfigConditionTest.class,

                HttpRequestUriResolverTest.class,
                ReturnResultBuilderTest.class,

                FeiLongCodeSuiteTests.class,

                JsonStringFormatterTest.class,
                XMLStringFormatterTest.class
        //
})
public class FeiLongContextSuiteTests{

}