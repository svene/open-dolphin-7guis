package org.svenehrke;

import org.opendolphin.core.server.ServerAttribute;

import java.util.function.Function;

public class AttributeValidator<T> {
	private final ServerAttribute valueAttribute;
	private final ServerAttribute sa;
	private final Function<T, Boolean> validator;

	AttributeValidator(ServerAttribute valueAttribute, ServerAttribute sa, Function<T, Boolean> validator) {
		this.valueAttribute = valueAttribute;
		this.sa = sa;
		this.validator = validator;
	}

	public ServerAttribute getAttribute() {
		return valueAttribute;
	}

	public T getValue() {
		return (T) valueAttribute.getValue();
	}

	public void setOK(boolean ok) {
		sa.setValue(ok);
	}
	public boolean isOK() {
		return (boolean) sa.getValue();
	}

	public void bind() {
		getAttribute().addPropertyChangeListener(evt -> {
			setOK(validator.apply(getValue()));
		});
	}
}
