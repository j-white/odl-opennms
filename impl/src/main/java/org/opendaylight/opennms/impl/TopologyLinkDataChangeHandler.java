/*
 * Copyright (c) 2016 The OpenNMS Group Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.opennms.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TopologyId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.TopologyKey;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * @author Jesse White (jesse@opennms.org)
 **/
public class TopologyLinkDataChangeHandler implements DataChangeListener {
    private final static Logger LOG = LoggerFactory.getLogger(TopologyLinkDataChangeHandler.class);
    private static final String DEFAULT_TOPOLOGY_ID = "flow:1";
    private final DataBroker dataBroker;

    public TopologyLinkDataChangeHandler(DataBroker dataBroker) {
        this.dataBroker = Preconditions.checkNotNull(dataBroker, "dataBroker argument");
    }

    @Override
    public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        LOG.info("Data changed!");
    }

    public Registration registerAsDataChangeListener() {
        InstanceIdentifier<Link> linkInstance = InstanceIdentifier.builder(NetworkTopology.class)
                .child(Topology.class, new TopologyKey(new TopologyId(DEFAULT_TOPOLOGY_ID)))
                .child(Link.class)
                .build();
        return dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL, linkInstance, this, AsyncDataBroker.DataChangeScope.BASE);
    }
}
