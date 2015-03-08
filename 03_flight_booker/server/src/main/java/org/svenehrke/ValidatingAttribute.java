package org.svenehrke;

import org.opendolphin.core.server.ServerAttribute;

public class ValidatingAttribute {
	private final ServerAttribute valueAttribute;
	private final ServerAttribute sa;

	ValidatingAttribute(ServerAttribute valueAttribute, ServerAttribute sa) {
		this.valueAttribute = valueAttribute;
		this.sa = sa;
	}

	public ServerAttribute getAttribute() {
		return valueAttribute;
	}

	public void setOK(boolean ok) {
		sa.setValue(ok);
	}
	public boolean isOK() {
		return (boolean) sa.getValue();
	}
}
