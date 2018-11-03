package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.QuantityPrefixSelector;
import tec.uom.lib.common.BinaryPrefix;

/**
 * Instances select an appropriate IEC binary prefix for a given {@link Quantity} and return the
 * same {@link Quantity} transformed to use that prefix.
 * <p>
 * Examples: Given 4,096 bytes per second, return 4 kibibits per second. Given 1,048,576 mebibytes,
 * return 1 tebibyte.
 */
public class IecBinaryPrefixSelector implements QuantityPrefixSelector {

  /**
   * Apply an appropriate prefix from the IEc binary set of prefixes for a given measurement.
   *
   * @param measure A value to scale with a prefix.
   * @param formatUnit The base "ones" unit of the provided measure.
   * @return The measurement, transformed with an appropriate prefix from the IEC binary set of
   *         prefixes.
   */
  @Override
  public <Q extends Quantity<Q>> Quantity<Q> selectBestPrefix(Quantity<Q> measure,
      Unit<Q> formatUnit) {
    BigDecimal asBig = BigDecimal.valueOf(measure.getValue().doubleValue());
    if (asBig.compareTo(IecBinaryPrefixes.YOBI_FACTOR) >= 0) {
      return measure.to(BinaryPrefix.YOBI(formatUnit));
    }
    if (asBig.compareTo(IecBinaryPrefixes.ZEBI_FACTOR) >= 0) {
      return measure.to(BinaryPrefix.ZEBI(formatUnit));
    }

    long asLong = measure.getValue().longValue();
    if (asLong >= IecBinaryPrefixes.EXBI_FACTOR) {
      return measure.to(BinaryPrefix.EXBI(formatUnit));
    }
    if (asLong >= IecBinaryPrefixes.PEBI_FACTOR) {
      return measure.to(BinaryPrefix.PEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.TEBI_FACTOR) {
      return measure.to(BinaryPrefix.TEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.GIBI_FACTOR) {
      return measure.to(BinaryPrefix.GIBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.MEBI_FACTOR) {
      return measure.to(BinaryPrefix.MEBI(formatUnit));
    }

    if (asLong >= IecBinaryPrefixes.KIBI_FACTOR) {
      return measure.to(BinaryPrefix.KIBI(formatUnit));
    }
    return measure.to(formatUnit);
  }
}
