package com.pervasivecode.utils.measure;

import static com.google.common.truth.Truth.assertThat;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import org.junit.Test;
import com.google.common.collect.ImmutableMap;
import com.pervasivecode.utils.measure.QuantityFormatter;
import com.pervasivecode.utils.measure.ScalingFormatter;
import com.pervasivecode.utils.measure.SiPrefixSelector;
import com.pervasivecode.utils.measure.SimpleUnitLabelProvider;
import com.pervasivecode.utils.measure.UnitLabelProvider;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ScalingFormatterTest {
  //
  // Meters -------------------------------------------------------------------
  //

  private static Quantity<Length> measureOfMeters(BigDecimal val) {
    return Quantities.getQuantity(val, Units.METRE);
  }

  @Test
  public void format_meter_withFractionsOfMeters_shouldUseSiFractionPrefixes() {
    // US-locale unit abbreviations for meters are already supported by SimpleUnitFormat,
    // so we don't need to provide labels here.
    UnitLabelProvider<Length> dummyLabelProvider = new SimpleUnitLabelProvider<>(ImmutableMap.of());
    QuantityFormatter<Length> meterFmt = new ScalingFormatter<Length>(Units.METRE,
        new SiPrefixSelector(), NumberFormat.getInstance(Locale.US), dummyLabelProvider);

    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 1)))).isEqualTo("100 mm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 3)))).isEqualTo("1 mm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 5)))).isEqualTo("10 µm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 7)))).isEqualTo("100 nm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 9)))).isEqualTo("1 nm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 11)))).isEqualTo("10 pm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 13)))).isEqualTo("100 fm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 15)))).isEqualTo("1 fm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 17)))).isEqualTo("10 am");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 19)))).isEqualTo("100 zm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 21)))).isEqualTo("1 zm");
    assertThat(meterFmt.format(measureOfMeters(BigDecimal.valueOf(1, 23)))).isEqualTo("10 ym");
  }
}
