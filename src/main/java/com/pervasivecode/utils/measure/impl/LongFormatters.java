package com.pervasivecode.utils.measure.impl;

import static com.pervasivecode.utils.measure.impl.InformationRates.BYTES_PER_SECOND;
import static systems.uom.unicode.CLDR.BYTE;
import static tec.uom.se.unit.MetricPrefix.MILLI;
import java.math.BigDecimal;
import java.text.NumberFormat;
import javax.measure.quantity.Time;
import com.pervasivecode.utils.measure.api.LongFormatter;
import com.pervasivecode.utils.measure.api.QuantityFormatter;
import com.pervasivecode.utils.measure.api.SystemOfPrefixes;
import si.uom.SI;
import systems.uom.quantity.Information;
import systems.uom.quantity.InformationRate;
import tec.uom.se.quantity.Quantities;

/**
 * Formatters for plain values represented as a long, which wraps that values in an appropriate
 * Quantity before formatting it.
 */
public class LongFormatters {
  /**
   * Get a formatter that will turn a Duration into a common representation of time, e.g. 3 hours 2
   * minutes 1 second and 45 milliseconds.
   */
  public static LongFormatter durationInSecondsFormatter() {
    QuantityFormatter<Time> sdf = ScalingDurationFormatter.US();
    return (v) -> sdf.format(Quantities.getQuantity(BigDecimal.valueOf(v), MILLI(SI.SECOND)));
  }

  /**
   * Get a formatter for long values representing amounts of data in bytes. The prefixes used depend
   * on the prefixSystem parameter.
   * <p>
   * Example: getting a formatter that uses the IEC_BINARY system of prefixes and passing it a value
   * of 1024L will result in a formatted representation of "1 KiB".
   * <p>
   * Getting a formatter that uses the SI system of prefixes and passing it a value of 1024L will
   * result in a formatted representation of "1.024 KB".
   */
  public static LongFormatter dataAmountInBytesFormatter(SystemOfPrefixes prefixSystem,
      NumberFormat numberFormat) {
    QuantityFormatter<Information> sdf =
        new ScalingInformationFormatter(prefixSystem, BYTE, numberFormat);
    return (v) -> sdf.format(Quantities.getQuantity(BigDecimal.valueOf(v), BYTE));
  }

  /**
   * Get a formatter for long values representing data rates in bytes per second (e.g. data transfer
   * rate, data processing rate). The prefixes used depend on the prefixSystem parameter.
   * <p>
   * Example: getting a formatter that uses the IEC_BINARY system of prefixes and passing it a value
   * of 1024L will result in a formatted representation of "1 KiBps".
   * <p>
   * Getting a formatter that uses the SI system of prefixes and passing it a value of 1024L will
   * result in a formatted representation of "1.024 KBps".
   */
  public static LongFormatter dataRateInBytesPerSecondFormatter(SystemOfPrefixes prefixSystem,
      NumberFormat numberFormat) {
    QuantityFormatter<InformationRate> sdf =
        new ScalingInformationRateFormatter(prefixSystem, BYTES_PER_SECOND, numberFormat);
    return (v) -> sdf.format(Quantities.getQuantity(BigDecimal.valueOf(v), BYTES_PER_SECOND));
  }
}
