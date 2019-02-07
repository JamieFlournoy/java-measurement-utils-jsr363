package com.pervasivecode.utils.measure.impl;

import static tec.uom.se.unit.MetricPrefix.NANO;
import static tec.uom.se.unit.Units.SECOND;
import java.time.Duration;
import java.util.Objects;
import javax.measure.Quantity;
import javax.measure.quantity.Time;
import com.pervasivecode.utils.measure.api.QuantityFormatter;
import com.pervasivecode.utils.time.DurationFormatter;
import tec.uom.se.quantity.Quantities;

/**
 * Format a {@code Quantity}&lt;{@code Time}&gt; value using a {@link DurationFormatter}, for a
 * representation that uses common units of time rather than metric prefixes (ex. "1 day" rather
 * than "86.4 Ks").
 */
public class ScalingDurationFormatter implements QuantityFormatter<Time> {
  private final DurationFormatter formatter;

  public ScalingDurationFormatter(DurationFormatter formatter) {
    this.formatter = Objects.requireNonNull(formatter);
  }

  @Override
  public String format(Quantity<Time> quantity) {
    long secondsPart = quantity.to(SECOND).getValue().longValue();

    Quantity<Time> justSecondsPart = Quantities.getQuantity(secondsPart, SECOND);
    Quantity<Time> justNanosPart = quantity.to(NANO(SECOND)).subtract(justSecondsPart); 
    
    long nanosPart = justNanosPart.getValue().longValue();

    return formatter.format(Duration.ofSeconds(secondsPart, nanosPart));
  }
}
