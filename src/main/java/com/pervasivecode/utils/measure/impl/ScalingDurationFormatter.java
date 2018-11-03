package com.pervasivecode.utils.measure.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import static tec.uom.se.unit.MetricPrefix.MILLI;
import static tec.uom.se.unit.Units.SECOND;
import java.util.List;
import javax.measure.Quantity;
import javax.measure.quantity.Time;
import com.google.common.collect.ImmutableList;
import com.pervasivecode.utils.measure.api.QuantityFormatter;

/**
 * Instances format a @{code Quantity}&lt;{@code Time}&gt; value with a scale expressed in standard
 * units of time (seconds, minutes, hours, etc.). The maximum unit of time that is supported is the
 * week, since the size of larger units of time is not fixed.
 */
public class ScalingDurationFormatter implements QuantityFormatter<Time> {
  private static final ImmutableList<Integer> TIME_UNIT_RATIOS =
      ImmutableList.of(1000, 60, 60, 24, 7, 52);

  private final List<String> suffixes;

  /**
   * Get an instance that will use abbreviated US suffixes such as "s" for seconds and "m" for
   * minutes.
   * 
   * @return An instance that will format values in a form appropriate for the US locale.
   */
  public static ScalingDurationFormatter US() {
    return new ScalingDurationFormatter(ImmutableList.of("ms", "s", "m", "h", "d", "w"));
  }

  /**
   * Create a formatter with the specified list of time unit suffixes.
   *
   * @param suffixes The time unit suffixes. This list must contain exactly six elements, in the
   *        following order: milliseconds, seconds, minutes, hours, days, and weeks.
   */
  public ScalingDurationFormatter(List<String> suffixes) {
    this.suffixes = checkNotNull(suffixes, "Suffixes is required.");
    checkArgument(suffixes.size() == TIME_UNIT_RATIOS.size(), "Suffixes must have six elements.");
  }

  /**
   * Format the measure of time. The format will consist of space-separated tokens, each of which
   * represents a time unit, such as 3 hours or 5 seconds, and each of which uses the time unit
   * suffix specified in the constructor. Leading parts with a zero value will be skipped; trailing
   * parts with a zero value will be shown.
   * <p>
   * The value will be represented with either milliseconds precision, if the value is smaller than
   * 60 seconds and also has a fractional seconds component, or will be represented with seconds
   * precision. Values of less than one second are scaled to milliseconds; values larger than one
   * second are scaled to seconds and shown as a decimal value.
   * <p>
   * 
   * Examples: Given the suffixes from {@link ScalingDurationFormatter#US}: 12.345 seconds will be
   * represented as "12.345s". 0.345 seconds will be represented as "345 ms". 3600 seconds will be
   * represented as "1h 0m 0s". One day minus one second (that is, 86399 seconds) will be
   * represented as "23h 59m 59s".
   */
  @Override
  public String format(Quantity<Time> measure) {
    // Scale the measure to units of milliseconds.
    Quantity<Time> ms = measure.to(MILLI(SECOND));
    long millis = ms.getValue().longValue();

    ImmutableList.Builder<String> parts = ImmutableList.builder();

    long sec = millis / 1000;
    if (sec < 60) {
      long justMillis = millis % 1000L;
      if (millis < 1000) {
        return String.format("%d%s", justMillis, suffixes.get(0));
      }
      return String.format("%d.%03d%s", sec, justMillis, suffixes.get(1));
    }

    long val = sec;
    for (int i = 1; i < suffixes.size(); i++) {
      parts.add(String.valueOf(val % TIME_UNIT_RATIOS.get(i)) + suffixes.get(i));
      val = val / TIME_UNIT_RATIOS.get(i);
      if (val == 0) {
        break;
      }
    }
    StringBuilder sb = new StringBuilder();
    boolean onFirstPart = true;
    for (String part : parts.build().reverse()) {
      if (onFirstPart) {
        onFirstPart = false;
      } else {
        sb.append(' ');
      }
      sb.append(part);
    }
    return sb.toString();
  }
}
