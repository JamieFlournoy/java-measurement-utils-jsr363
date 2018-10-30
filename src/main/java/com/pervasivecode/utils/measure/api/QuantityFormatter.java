package com.pervasivecode.utils.measure.api;

import javax.measure.Quantity;

/**
 * This object can format a Quantity of a given type.
 * @param <Q> The kind of Quantity that this object can format.
 */
public interface QuantityFormatter<Q extends Quantity<Q>> {
  public String format(Quantity<Q> quantity);
}
