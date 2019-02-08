package com.pervasivecode.utils.measure;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Mass;

/**
 * Instances can automatically determine the appropriate scale prefix (e.g. kilo or mega) that
 * should be used to present a value of any size in a human-friendly form.
 * <p>
 * Prefixes are represented by a {@link Unit} instance that that contains the base unit and the
 * prefix, so the output of a QuantityPrefixSelector is just the input {@link Quantity} transformed
 * to an appropriately-scaled {@link Unit}.
 */
public interface QuantityPrefixSelector {

  /**
   * Transform a value by applying the most appropriate unit prefix.
   * <p>
   * Example: A value representing 50000 meters would be converted to a value representing 50
   * kilometers.
   *
   * @param <Q> The kind of value represented by the measure. Example: {@link Mass}
   * @param measure The value to scale with a unit prefix.
   * @param whole The base "ones" unit for the type of thing being represented.
   * @return The input value, transformed to a unit that includes the appropriate unit prefix.
   */
  public <Q extends Quantity<Q>> Quantity<Q> selectBestPrefix(Quantity<Q> measure, Unit<Q> whole);
}
