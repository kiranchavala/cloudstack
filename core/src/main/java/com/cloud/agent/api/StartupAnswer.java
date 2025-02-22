//
// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//

package com.cloud.agent.api;

public class StartupAnswer extends Answer {
    long hostId;
    String hostName;
    String hostUuid;
    int pingInterval;

    protected StartupAnswer() {
    }

    public StartupAnswer(StartupCommand cmd, long hostId, String hostUuid, String hostName, int pingInterval) {
        super(cmd);
        this.hostId = hostId;
        this.hostUuid = hostUuid;
        this.hostName = hostName;
        this.pingInterval = pingInterval;
    }

    public StartupAnswer(StartupCommand cmd, String details) {
        super(cmd, false, details);
    }

    public long getHostId() {
        return hostId;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPingInterval() {
        return pingInterval;
    }
}
