package com.pervasivecode.utils.measure.impl;

import static com.google.common.truth.Truth.assertThat;
import static tec.uom.se.unit.MetricPrefix.EXA;
import static tec.uom.se.unit.MetricPrefix.MILLI;
import static tec.uom.se.unit.MetricPrefix.NANO;
import static tec.uom.se.unit.Units.SECOND;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import javax.measure.Quantity;
import javax.measure.quantity.Time;
import org.junit.Before;
import org.junit.Test;
import com.pervasivecode.utils.time.DurationFormat;
import com.pervasivecode.utils.time.DurationFormats;
import com.pervasivecode.utils.time.DurationFormatter;
import tec.uom.se.quantity.Quantities;

public class ScalingDurationFormatterTest {
  private ScalingDurationFormatter millisFormatter;
  private ScalingDurationFormatter nanosFormatter;

  @Before
  public void setup() {
    DurationFormat usFormatToNanos = DurationFormats.getUsDefaultInstance();
    DurationFormat usFormatToMillis = DurationFormat.builder(usFormatToNanos) //
        .setSmallestUnit(ChronoUnit.MILLIS) //
        .build();
    nanosFormatter = new ScalingDurationFormatter(new DurationFormatter(usFormatToNanos));
    millisFormatter = new ScalingDurationFormatter(new DurationFormatter(usFormatToMillis));
  }

  @Test
  public void format_withHugeTimeQuantity_shouldWork() {
    Quantity<Time> nineExaSeconds = Quantities.getQuantity(new BigDecimal(9), EXA(SECOND));
    String expectedFormattedNineExaSeconds = "285,198,646,561y 3mo 2w 5d 7h 31m 30s";

    assertThat(millisFormatter.format(nineExaSeconds)).isEqualTo(expectedFormattedNineExaSeconds);
    assertThat(nanosFormatter.format(nineExaSeconds)).isEqualTo(expectedFormattedNineExaSeconds);

    Quantity<Time> maxLongSeconds = Quantities.getQuantity(Long.valueOf(Long.MAX_VALUE), SECOND);
    String expectedFormattedMaxLongSeconds = "292,277,024,626y 11mo 0w 4d 0h 50m 49s";

    assertThat(millisFormatter.format(maxLongSeconds)).isEqualTo(expectedFormattedMaxLongSeconds);
    assertThat(nanosFormatter.format(maxLongSeconds)).isEqualTo(expectedFormattedMaxLongSeconds);
  }

  @Test
  public void format_withMediumSizedHighPrecisionQuantity_shouldWork() {
    Quantity<Time> oneSecMilliNano = Quantities.getQuantity(1_001_000_001L, NANO(SECOND));
    assertThat(millisFormatter.format(oneSecMilliNano)).isEqualTo("1s 1ms");
    assertThat(nanosFormatter.format(oneSecMilliNano)).isEqualTo("1s 1ms 0µs 1ns");
  }

  @Test
  public void format_withMediumSizedDoubleQuantity_shouldWork() {
    Quantity<Time> oneDayOneMs = Quantities.getQuantity(86400.001d, SECOND);
    assertThat(millisFormatter.format(oneDayOneMs)).isEqualTo("1d 0h 0m 0s 1ms");
    assertThat(nanosFormatter.format(oneDayOneMs)).isEqualTo("1d 0h 0m 0s 1ms");

    Quantity<Time> oneDayOneMilliOneNano = Quantities.getQuantity(86400.0, SECOND) //
        .add(Quantities.getQuantity(1, MILLI(SECOND))) //
        .add(Quantities.getQuantity(1, NANO(SECOND)));
    assertThat(millisFormatter.format(oneDayOneMilliOneNano)).isEqualTo("1d 0h 0m 0s 1ms");
    assertThat(nanosFormatter.format(oneDayOneMilliOneNano)).isEqualTo("1d 0h 0m 0s 1ms 0µs 1ns");
  }

  @Test
  public void format_withTinyTimeQuantity_shouldWork() {
    Quantity<Time> oneNanosecond = Quantities.getQuantity(1L, NANO(SECOND));
    assertThat(millisFormatter.format(oneNanosecond)).isEqualTo("0ms");
    assertThat(nanosFormatter.format(oneNanosecond)).isEqualTo("1ns");
  }
}
