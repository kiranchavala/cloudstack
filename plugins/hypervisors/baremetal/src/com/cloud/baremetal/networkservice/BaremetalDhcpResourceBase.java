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
// Automatically generated by addcopyright.py at 01/29/2013
// Apache License, Version 2.0 (the "License"); you may not use this
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//
// Automatically generated by addcopyright.py at 04/03/2012
package com.cloud.baremetal.networkservice;

import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;


import com.cloud.agent.IAgentControl;
import com.cloud.agent.api.Answer;
import com.cloud.agent.api.Command;
import com.cloud.agent.api.HostVmStateReportEntry;
import com.cloud.agent.api.PingCommand;
import com.cloud.agent.api.PingRoutingCommand;
import com.cloud.agent.api.ReadyAnswer;
import com.cloud.agent.api.ReadyCommand;
import com.cloud.agent.api.StartupCommand;
import com.cloud.agent.api.StartupExternalDhcpCommand;
import com.cloud.host.Host.Type;
import com.cloud.resource.ServerResource;
import com.cloud.utils.component.ManagerBase;

public class BaremetalDhcpResourceBase extends ManagerBase implements ServerResource {
    String _name;
    String _guid;
    String _username;
    String _password;
    String _ip;
    String _zoneId;
    String _dns;

    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        _name = name;
        _guid = (String)params.get("guid");
        _ip = (String)params.get("ip");
        _username = (String)params.get("username");
        _password = (String)params.get("password");
        _zoneId = (String)params.get("zone");
        _dns = (String)params.get("dns");

        if (_guid == null) {
            throw new ConfigurationException("No Guid specified");
        }

        if (_zoneId == null) {
            throw new ConfigurationException("No Zone specified");
        }

        if (_ip == null) {
            throw new ConfigurationException("No IP specified");
        }

        if (_username == null) {
            throw new ConfigurationException("No username specified");
        }

        if (_password == null) {
            throw new ConfigurationException("No password specified");
        }

        if (_dns == null) {
            throw new ConfigurationException("No dns specified");
        }

        return true;
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public Type getType() {
        return Type.BaremetalDhcp;
    }

    @Override
    public StartupCommand[] initialize() {
        StartupExternalDhcpCommand cmd = new StartupExternalDhcpCommand();
        cmd.setName(_name);
        cmd.setDataCenter(_zoneId);
        cmd.setPrivateIpAddress(_ip);
        cmd.setStorageIpAddress("");
        cmd.setVersion("");
        cmd.setGuid(_guid);
        return new StartupCommand[] {cmd};
    }

    @Override
    public PingCommand getCurrentStatus(long id) {
        //TODO: check server
        return new PingRoutingCommand(getType(), id, new HashMap<String, HostVmStateReportEntry>());
    }

    protected ReadyAnswer execute(ReadyCommand cmd) {
        logger.debug("External DHCP resource " + _name + " is ready");
        return new ReadyAnswer(cmd);
    }

    @Override
    public Answer executeRequest(Command cmd) {
        if (cmd instanceof ReadyCommand) {
            return execute((ReadyCommand)cmd);
        } else {
            return Answer.createUnsupportedCommandAnswer(cmd);
        }
    }

    @Override
    public void disconnected() {
    }

    @Override
    public IAgentControl getAgentControl() {
        return null;
    }

    @Override
    public void setAgentControl(IAgentControl agentControl) {
    }

}
