package com.pervasivecode.utils.measure.api;

import javax.annotation.Nullable;
import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * Instances can provide the appropriate String label for a specified unit in a given locale. (A
 * given instance only knows how to provide labels for a single Locale.)
 * <p>
 * Example: for the unit KIBI * InformationRate, a US-locale instance would return "KiBps" or
 * "KiB/s".
 */
public interface UnitLabelProvider<Q extends Quantity<Q>> {

  /**
   * Get the label to use to use when formatting the specified unit.
   * @param unit The unit for which the label is needed.
   * @return The label, or null if the correct label is not known.
   */
  @Nullable
  public String getLabel(Unit<Q> unit);
}
