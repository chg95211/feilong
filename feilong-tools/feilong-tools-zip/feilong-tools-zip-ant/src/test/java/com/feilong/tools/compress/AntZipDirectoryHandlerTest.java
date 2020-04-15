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
package com.feilong.tools.compress;

import static com.feilong.core.date.DateUtil.nowTimestamp;

import org.junit.Test;

public class AntZipDirectoryHandlerTest{

    private final ZipHandler zipHandler      = new AntZipHandler();

    String                   tobeZipFilePath = "/Users/feilong/workspace/feilong/feilong/feilong-tools/feilong-tools-zip/feilong-tools-zip-api/src/test/resources/for-zip";

    String                   outputZipPath   = "/Users/feilong/feilong/logs/" + nowTimestamp() + "antzip.zip";

    @Test
    public void test(){
        zipHandler.zip(tobeZipFilePath, outputZipPath);
    }

}