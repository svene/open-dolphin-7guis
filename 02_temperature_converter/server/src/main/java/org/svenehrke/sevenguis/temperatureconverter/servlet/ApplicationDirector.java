package org.svenehrke.sevenguis.temperatureconverter.servlet;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class ApplicationDirector extends DolphinServerAction {

    public void registerIn(ActionRegistry registry) {
        // register all your actions here.
        getServerDolphin().register(new ApplicationAction());
    }
}
