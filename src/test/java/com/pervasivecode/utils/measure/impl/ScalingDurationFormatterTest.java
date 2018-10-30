package com.pervasivecode.utils.measure.impl;

import static com.google.common.truth.Truth.assertThat;
import static tec.uom.se.unit.MetricPrefix.MILLI;
import static tec.uom.se.unit.Units.SECOND;
import javax.measure.Quantity;
import javax.measure.quantity.Time;
import org.junit.Test;
import tec.uom.se.quantity.Quantities;

public class ScalingDurationFormatterTest {

  private static Quantity<Time> measureOfMillis(long millisValue) {
    return Quantities.getQuantity(Long.valueOf(millisValue), MILLI(SECOND));
  }

  private static void checkDurationFormat(Quantity<Time> duration, String expected) {
    assertThat(ScalingDurationFormatter.US().format(duration)).isEqualTo(expected);
  }

  @Test
  public void format_duration_withSeveralMillis_shouldUseMillis() {
    checkDurationFormat(measureOfMillis(17), "17ms");
  }

  @Test
  public void format_duration_withHundredsOfMillis_shouldUseMillis() {
    checkDurationFormat(measureOfMillis(137), "137ms");
  }

  @Test
  public void format_duration_withThousandsOfMillis_shouldUseSeconds() {
    checkDurationFormat(measureOfMillis(1370), "1.370s");
  }

  @Test
  public void format_duration_withMillionsOfMillis_shouldUseMinutesAndSeconds() {
    checkDurationFormat(measureOfMillis(1_370_223), "22m 50s");
  }

  @Test
  public void format_duration_withBillionsOfMillis_shouldUseHoursMinutesAndSeconds() {
    checkDurationFormat(measureOfMillis(2_521_370_223L), "4w 1d 4h 22m 50s");
  }
}
