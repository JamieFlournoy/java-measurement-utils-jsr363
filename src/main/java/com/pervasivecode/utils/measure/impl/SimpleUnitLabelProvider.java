package com.pervasivecode.utils.measure.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Map;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.pervasivecode.utils.measure.api.UnitLabelProvider;

/**
 * This class provides unit labels for a single locale by looking them up in a static mapping of
 * units to labels.
 */
public class SimpleUnitLabelProvider<Q extends Quantity<Q>> implements UnitLabelProvider<Q> {
  private final Map<Unit<Q>, String> unitLabels;

  /**
   * Set up a new instance with a static mapping of units to labels.
   *
   * @param unitLabels The mapping of units to labels.
   */
  public SimpleUnitLabelProvider(Map<Unit<Q>, String> unitLabels) {
    this.unitLabels = checkNotNull(unitLabels);
  }

  @Override
  public String getLabel(Unit<Q> unit) {
    return this.unitLabels.get(unit);
  }
}
