package com.pervasivecode.utils.measure;

import java.text.NumberFormat;
import java.util.Locale;
import systems.uom.quantity.Information;
import systems.uom.quantity.InformationRate;
import systems.uom.unicode.CLDR;

/**
 * This class contains factory methods for formatters using commonly-used data size and data rate
 * formats, suitable for use in the {@link Locale#US US} locale.
 */
public class ScalingFormatters {
  private ScalingFormatters() {}

  /**
   * Get a formatter that presents data amounts measured in bytes scaled with IEC binary prefixes
   * and formatted as appropriate for the {@link Locale#US US} locale.
   *
   * @return The formatter.
   */
  public static ScalingFormatter<Information> dataAmountInIecBinaryBytesUs() {
    return new ScalingFormatter<Information>(CLDR.BYTE, new IecBinaryPrefixSelector(),
        NumberFormat.getInstance(Locale.US), SimpleUnitLabelProviders.getUsInformationLabeler());
  }

  /**
   * Get a formatter that presents data amounts measured in bits scaled with SI prefixes and
   * formatted as appropriate for the {@link Locale#US US} locale.
   *
   * @return The formatter.
   */
  public static ScalingFormatter<Information> dataAmountInSiBitsUs() {
    return new ScalingFormatter<Information>(CLDR.BIT, new SiPrefixSelector(),
        NumberFormat.getInstance(Locale.US), SimpleUnitLabelProviders.getUsInformationLabeler());
  }

  /**
   * Get a formatter that presents data rates measured in bytes per second scaled with IEC binary
   * prefixes and formatted as appropriate for the {@link Locale#US US} locale.
   *
   * @return The formatter.
   */
  public static ScalingFormatter<InformationRate> dataRateInIecBinaryBytesPerSecondUs() {
    return new ScalingFormatter<InformationRate>(InformationRateUnits.BYTES_PER_SECOND,
        new IecBinaryPrefixSelector(), NumberFormat.getInstance(Locale.US),
        SimpleUnitLabelProviders.getUsInformationRateLabeler());
  }

  /**
   * Get a formatter that presents data amounts measured in bits per second scaled with SI prefixes
   * and formatted as appropriate for the {@link Locale#US US} locale.
   *
   * @return The formatter.
   */
  public static ScalingFormatter<InformationRate> dataRateInSiBitsPerSecondUs() {
    return new ScalingFormatter<InformationRate>(InformationRateUnits.BITS_PER_SECOND,
        new SiPrefixSelector(), NumberFormat.getInstance(Locale.US),
        SimpleUnitLabelProviders.getUsInformationRateLabeler());
  }
}
