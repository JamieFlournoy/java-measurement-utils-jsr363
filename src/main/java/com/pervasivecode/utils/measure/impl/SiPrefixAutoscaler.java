package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.QuantityAutoscaler;
import tec.uom.se.unit.MetricPrefix;

public class SiPrefixAutoscaler implements QuantityAutoscaler {
  @Override
  public <Q extends Quantity<Q>> Quantity<Q> autoscale(Quantity<Q> measure, Unit<Q> formatUnit) {
    Quantity<Q> m = measure;

    BigDecimal asBig = BigDecimal.valueOf(m.getValue().doubleValue());

    if (asBig.compareTo(SiPrefixes.YOTTA_FACTOR) >= 0) {
      return m.to(MetricPrefix.YOTTA(formatUnit));
    }
    if (asBig.compareTo(SiPrefixes.ZETTA_FACTOR) >= 0) {
      return m.to(MetricPrefix.ZETTA(formatUnit));
    }

    long asLong = m.getValue().longValue();
    if (asLong >= SiPrefixes.EXA_FACTOR) {
      return m.to(MetricPrefix.EXA(formatUnit));
    }
    if (asLong >= SiPrefixes.PETA_FACTOR) {
      return m.to(MetricPrefix.PETA(formatUnit));
    }

    if (asLong >= SiPrefixes.TERA_FACTOR) {
      return m.to(MetricPrefix.TERA(formatUnit));
    }

    if (asLong >= SiPrefixes.GIGA_FACTOR) {
      return m.to(MetricPrefix.GIGA(formatUnit));
    }

    if (asLong >= SiPrefixes.MEGA_FACTOR) {
      return m.to(MetricPrefix.MEGA(formatUnit));
    }

    if (asLong >= SiPrefixes.KILO_FACTOR) {
      return m.to(MetricPrefix.KILO(formatUnit));
    }
    return m.to(formatUnit);
  }
}
