/*
 * Copyright 2014 by Cloudsoft Corporation Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package brooklyn.entity.container.sdn;

import java.net.InetAddress;

import brooklyn.config.ConfigKey;
import brooklyn.entity.annotation.Effector;
import brooklyn.entity.annotation.EffectorParam;
import brooklyn.entity.basic.ConfigKeys;
import brooklyn.entity.basic.MethodEffector;
import brooklyn.entity.basic.SoftwareProcess;
import brooklyn.entity.container.docker.DockerHost;
import brooklyn.event.AttributeSensor;
import brooklyn.event.basic.AttributeSensorAndConfigKey;
import brooklyn.event.basic.Sensors;
import brooklyn.networking.subnet.SubnetTier;
import brooklyn.util.flags.SetFromFlag;
import brooklyn.util.net.Cidr;

/**
 * An SDN agent process.
 */
public interface SdnAgent extends SoftwareProcess {


    @SetFromFlag("host")
    AttributeSensorAndConfigKey<DockerHost,DockerHost> DOCKER_HOST = ConfigKeys.newSensorAndConfigKey(DockerHost.class, "sdn.agent.docker.host", "Docker host we are running on");

    @SetFromFlag("provider")
    AttributeSensorAndConfigKey<SdnProvider,SdnProvider> SDN_PROVIDER = ConfigKeys.newSensorAndConfigKey(SdnProvider.class, "sdn.provider", "SDN provider entity");
 
    AttributeSensor<InetAddress> SDN_AGENT_ADDRESS = Sensors.newSensor(InetAddress.class, "sdn.agent.address", "IP address of SDFN agent service");
    AttributeSensor<SdnAgent> SDN_AGENT = Sensors.newSensor(SdnAgent.class, "sdn.agent.entity", "SDN agent entity");

    DockerHost getDockerHost();

    MethodEffector<InetAddress> ATTACH_NETWORK = new MethodEffector<InetAddress>(SdnAgent.class, "attachNetwork");

    /**
     * Attach a container to the network.
     *
     * @param containerId the container ID
     * @return the {@link SubnetTier} IP address
     */
    @Effector(description="Attach a container to the network")
    InetAddress attachNetwork(
            @EffectorParam(name="containerId", description="Container ID") String containerId);

}
