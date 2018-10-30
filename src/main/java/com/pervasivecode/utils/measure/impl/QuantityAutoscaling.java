package com.pervasivecode.utils.measure.impl;

import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.SystemOfPrefixes;

public class QuantityAutoscaling {
  public static <Q extends Quantity<Q>> Quantity<Q> autoscale(Quantity<Q> measure, Unit<Q> whole,
      SystemOfPrefixes prefixSystem) {
    switch (prefixSystem) {
      case IEC_BINARY:
        return new IecBinaryPrefixAutoscaler().autoscale(measure, whole);
      case SI:
        return new SiPrefixAutoscaler().autoscale(measure, whole);
      default:
        String message = String.format("Unsupported SystemOfPrefixes %s", prefixSystem.toString());
        throw new IllegalArgumentException(message);
    }
  }
}
