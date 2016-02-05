/*
 * Copyright © 2015 The OpenNMS Group Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.opennms.impl.rev141210;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.opennms.impl.TopologyLinkDataChangeHandler;
import org.opendaylight.yangtools.concepts.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpennmsModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.opennms.impl.rev141210.AbstractOpennmsModule {
    private static final Logger LOG = LoggerFactory.getLogger(OpennmsModule.class);
    private TopologyLinkDataChangeHandler topologyLinkDataChangeHandler;
    private Registration listenerRegistration;

    public OpennmsModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public OpennmsModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.opennms.impl.rev141210.OpennmsModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        LOG.info("MOO3");
        /*DataBroker dataBroker = getDataBrokerDependency();
        Preconditions.checkNotNull(dataBroker, "dataBroker should not be null.");
        OpennmsProvider provider = new OpennmsProvider();
        LOG.info("MOO2");
        //getBrokerDependency().registerProvider(provider);
        return provider;
        */
        
        
        //NotificationService notificationService = getNotificationServiceDependency();
        DataBroker dataBroker = getDataBrokerDependency();
        // RpcProviderRegistry rpcRegistryDependency = getRpcRegistryDependency();
        //SalFlowService salFlowService = rpcRegistryDependency.getRpcService(SalFlowService.class);

        // Register Topology DataChangeListener
        this.topologyLinkDataChangeHandler = new TopologyLinkDataChangeHandler(dataBroker);
        listenerRegistration = topologyLinkDataChangeHandler.registerAsDataChangeListener();

        final class CloseResources implements AutoCloseable {
          @Override
          public void close() throws Exception {
            if(listenerRegistration != null) {
              listenerRegistration.close();
            }
            LOG.info("OpennmsModule (instance {}) torn down.", this);
          }
        }
        AutoCloseable ret = new CloseResources();
        LOG.info("OpennmsModule (instance {}) initialized.", ret);
        return ret;

    }

}
