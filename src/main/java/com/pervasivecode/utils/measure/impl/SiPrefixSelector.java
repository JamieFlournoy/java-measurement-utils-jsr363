package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.QuantityPrefixSelector;
import tec.uom.se.unit.MetricPrefix;

/**
 * Instances select an appropriate SI prefix for a given {@link Quantity} and return the same
 * {@link Quantity} transformed to use that prefix.
 * <p>
 * Examples: given 10,000 meters, return 10 kilometers. Given 1,000,000 joules, return 1 megajoule.
 */
public class SiPrefixSelector implements QuantityPrefixSelector {

  /**
   * Apply an appropriate prefix from the SI set of prefixes for a given measurement.
   *
   * @param measure A value to scale with a prefix.
   * @param formatUnit The base "ones" unit of the provided measure.
   * @return The measurement, transformed with an appropriate prefix from the SI system.
   */
  @Override
  public <Q extends Quantity<Q>> Quantity<Q> selectBestPrefix(Quantity<Q> measure,
      Unit<Q> formatUnit) {
    BigDecimal asBig = BigDecimal.valueOf(measure.getValue().doubleValue());
    if (asBig.compareTo(SiPrefixes.YOTTA_FACTOR) >= 0) {
      return measure.to(MetricPrefix.YOTTA(formatUnit));
    }
    if (asBig.compareTo(SiPrefixes.ZETTA_FACTOR) >= 0) {
      return measure.to(MetricPrefix.ZETTA(formatUnit));
    }

    long asLong = measure.getValue().longValue();
    if (asLong >= SiPrefixes.EXA_FACTOR) {
      return measure.to(MetricPrefix.EXA(formatUnit));
    }
    if (asLong >= SiPrefixes.PETA_FACTOR) {
      return measure.to(MetricPrefix.PETA(formatUnit));
    }

    if (asLong >= SiPrefixes.TERA_FACTOR) {
      return measure.to(MetricPrefix.TERA(formatUnit));
    }

    if (asLong >= SiPrefixes.GIGA_FACTOR) {
      return measure.to(MetricPrefix.GIGA(formatUnit));
    }

    if (asLong >= SiPrefixes.MEGA_FACTOR) {
      return measure.to(MetricPrefix.MEGA(formatUnit));
    }

    if (asLong >= SiPrefixes.KILO_FACTOR) {
      return measure.to(MetricPrefix.KILO(formatUnit));
    }
    return measure.to(formatUnit);
  }
}
