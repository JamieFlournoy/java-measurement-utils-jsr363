package com.pervasivecode.utils.measure.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static tec.uom.se.unit.MetricPrefix.MILLI;
import static tec.uom.se.unit.Units.SECOND;
import java.util.List;
import javax.measure.Quantity;
import javax.measure.quantity.Time;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.pervasivecode.utils.measure.api.QuantityFormatter;

class ScalingDurationFormatter implements QuantityFormatter<Time> {
  private final List<String> suffixes;
  private final List<Integer> ratios;

  public static ScalingDurationFormatter US() {
    return new ScalingDurationFormatter(ImmutableList.of(1000, 60, 60, 24, 7, 52),
        ImmutableList.of("ms", "s", "m", "h", "d", "w"));
  }

  public ScalingDurationFormatter(List<Integer> ratios, List<String> suffixes) {
    this.ratios = checkNotNull(ratios, "Ratios is required.");
    this.suffixes = checkNotNull(suffixes, "Suffixes is required.");
    checkArgument(ratios.size() > 0, "Ratios can't be empty.");
    checkArgument(suffixes.size() > 0, "Suffixes can't be empty.");
    checkArgument(suffixes.size() == ratios.size());
  }

  @Override
  public String format(Quantity<Time> measure) {
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
      parts.add(String.valueOf(val % ratios.get(i)) + suffixes.get(i));
      val = val / ratios.get(i);
      if (val == 0) {
        break;
      }
    }
    return Joiner.on(' ').join(parts.build().reverse());
  }
}
