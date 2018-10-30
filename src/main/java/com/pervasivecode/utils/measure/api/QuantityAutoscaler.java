package com.pervasivecode.utils.measure.api;

import javax.measure.Quantity;
import javax.measure.Unit;

public interface QuantityAutoscaler {
  public <Q extends Quantity<Q>> Quantity<Q> autoscale(Quantity<Q> measure, Unit<Q> whole);
}