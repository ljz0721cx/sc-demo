/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.thclouds.agent.context;

import com.thclouds.agent.conf.Constants;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.io.Serializable;

/**
 * {@link ContextCarrier} is a data carrier of {@link TracingContext}. It holds the snapshot (current state) of {@link
 * TracingContext}.
 * <p>
 */
@Data
@Builder
public class ContextCarrier implements Serializable {

    private String traceId;
    /**
     * The segment id of the parent.
     */
    private String traceSegmentId;
    /**
     * The span id in the parent segment.
     */
    private int spanId = -1;

    private String parentService = Constants.EMPTY_STRING;

    private String parentServiceInstance = Constants.EMPTY_STRING;
    /**
     * The endpoint(entrance URI/method signature) of the parent service.
     */

    private String parentEndpoint;
    /**
     * The network address(ip:port, hostname:port) used in the parent service to access the current service.
     */

    private String addressUsedAtClient;

}
