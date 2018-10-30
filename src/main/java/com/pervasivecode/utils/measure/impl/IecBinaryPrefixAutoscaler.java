package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.QuantityAutoscaler;
import tec.uom.lib.common.BinaryPrefix;

public class IecBinaryPrefixAutoscaler implements QuantityAutoscaler {
  @Override
  public <Q extends Quantity<Q>> Quantity<Q> autoscale(Quantity<Q> measure, Unit<Q> formatUnit) {
    Quantity<Q> m = measure;

    BigDecimal asBig = BigDecimal.valueOf(m.getValue().doubleValue());
    if (asBig.compareTo(IecBinaryPrefixes.YOBI_FACTOR) >= 0) {
      return m.to(BinaryPrefix.YOBI(formatUnit));
    }
    if (asBig.compareTo(IecBinaryPrefixes.ZEBI_FACTOR) >= 0) {
      return m.to(BinaryPrefix.ZEBI(formatUnit));
    }

    long asLong = m.getValue().longValue();
    if (asLong >= IecBinaryPrefixes.EXBI_FACTOR) {
      return m.to(BinaryPrefix.EXBI(formatUnit));
    }
    if (asLong >= IecBinaryPrefixes.PEBI_FACTOR) {
      return m.to(BinaryPrefix.PEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.TEBI_FACTOR) {
      return m.to(BinaryPrefix.TEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.GIBI_FACTOR) {
      return m.to(BinaryPrefix.GIBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.MEBI_FACTOR) {
      return m.to(BinaryPrefix.MEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.KIBI_FACTOR) {
      return m.to(BinaryPrefix.KIBI(formatUnit));
    }
    return m.to(formatUnit);
  }
}
