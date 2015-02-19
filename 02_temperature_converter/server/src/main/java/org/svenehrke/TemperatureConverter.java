package org.svenehrke;

public class TemperatureConverter {

	public static Double fahrenheitFromCelsius(Double c) {
		return c * (9 / 5D) + 32D;

	}
	public static Double celsiusFromFahrenheit(Double f) {
		return (f - 32D) * (5/9D);

	}
}
