package com.feilong.lib.digester3.binder;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Thrown when errors occur while creating a {@link com.feilong.lib.digester3.Digester}.
 *
 * Includes a list of encountered errors. Clients should catch this exception, log it, and stop execution.
 */
public final class DigesterLoadingException extends RuntimeException{

    /**
     * The typical serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public DigesterLoadingException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Constructs a new Digester exception with the specified detail message.
     *
     * @param message
     *            the detail message.
     */
    public DigesterLoadingException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause
     *            the cause.
     */
    public DigesterLoadingException(Throwable cause){
        super(cause);
    }

}
