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

package org.apache.cloudstack.api.command.admin.network;

import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.ApiErrorCode;
import org.apache.cloudstack.api.BaseAsyncCmd;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.cloudstack.api.response.DataCenterIpv4SubnetResponse;
import org.apache.cloudstack.datacenter.DataCenterIpv4GuestSubnet;

import com.cloud.event.EventTypes;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.user.Account;
import com.cloud.utils.exception.CloudRuntimeException;

@APICommand(name = "updateIpv4SubnetForZone",
        description = "Updates an existing IPv4 subnet for a zone.",
        responseObject = DataCenterIpv4SubnetResponse.class,
        since = "4.20.0",
        requestHasSensitiveInfo = false,
        responseHasSensitiveInfo = false,
        authorized = {RoleType.Admin})
public class UpdateIpv4SubnetForZoneCmd extends BaseAsyncCmd {

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name = ApiConstants.ID, type = CommandType.UUID, entityType = DataCenterIpv4SubnetResponse.class, required = true, description = "Id of the guest network IPv4 subnet")
    private Long id;

    @Parameter(name = ApiConstants.SUBNET,
            type = CommandType.STRING,
            required = true,
            description = "The new CIDR of the IPv4 subnet.")
    private String subnet;

    public Long getId() {
        return id;
    }

    public String getSubnet() {
        return subnet;
    }

    @Override
    public String getEventType() {
        return EventTypes.EVENT_ZONE_IP4_SUBNET_UPDATE;
    }

    @Override
    public String getEventDescription() {
        return "Updating zone IPv4 subnet " + getId();
    }

    @Override
    public void execute() {
        try {
            DataCenterIpv4GuestSubnet result = routedIpv4Manager.updateDataCenterIpv4GuestSubnet(this);
            if (result != null) {
                DataCenterIpv4SubnetResponse response = routedIpv4Manager.createDataCenterIpv4SubnetResponse(result);
                response.setResponseName(getCommandName());
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to update guest network IPv4 subnet:" + getId());
            }
        } catch (InvalidParameterValueException ex) {
            throw new ServerApiException(ApiErrorCode.PARAM_ERROR, ex.getMessage());
        } catch (CloudRuntimeException ex) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }

    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }
}