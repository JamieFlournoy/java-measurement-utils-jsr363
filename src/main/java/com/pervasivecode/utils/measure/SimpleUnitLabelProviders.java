package com.pervasivecode.utils.measure;

import static com.pervasivecode.utils.measure.InformationRateUnits.BITS_PER_SECOND;
import static com.pervasivecode.utils.measure.InformationRateUnits.BYTES_PER_SECOND;
import static systems.uom.unicode.CLDR.BIT;
import static systems.uom.unicode.CLDR.BYTE;
import javax.measure.Unit;
import com.google.common.collect.ImmutableMap;
import systems.uom.quantity.Information;
import systems.uom.quantity.InformationRate;
import systems.uom.unicode.CLDR;
import tec.uom.lib.common.BinaryPrefix;
import tec.uom.se.unit.MetricPrefix;

/**
 * Factory methods for instances of SimpleUnitLabelProvider, providing prefixed unit labels for
 * information units {@link CLDR#BIT BIT}, {@link CLDR#BYTE BYTE},
 * {@link InformationRateUnits#BYTES_PER_SECOND BYTES_PER_SECOND} and
 * {@link InformationRateUnits#BITS_PER_SECOND BITS_PER_SECOND}.
 */
public class SimpleUnitLabelProviders {
  private SimpleUnitLabelProviders() {}

  private static final ImmutableMap<Unit<Information>, String> US_INFORMATION_LABELS =
      buildUsInformationLabels();

  private static final ImmutableMap<Unit<InformationRate>, String> US_INFORMATION_RATE_LABELS =
      buildUsInformationRateLabels();

  private static ImmutableMap<Unit<Information>, String> buildUsInformationLabels() {
    ImmutableMap.Builder<Unit<Information>, String> labels = ImmutableMap.builder();

    // (UnitFormatter already knows about BIT, "bit" since it's the fundamental unit)
    // SI bits
    labels.put(MetricPrefix.KILO(BIT), "kbits");
    labels.put(MetricPrefix.MEGA(BIT), "Mbits");
    labels.put(MetricPrefix.GIGA(BIT), "Gbits");
    labels.put(MetricPrefix.TERA(BIT), "Tbits");
    labels.put(MetricPrefix.PETA(BIT), "Pbits");
    labels.put(MetricPrefix.EXA(BIT), "Ebits");
    labels.put(MetricPrefix.ZETTA(BIT), "Zbits");
    labels.put(MetricPrefix.YOTTA(BIT), "Ybits");
    // IEC Binary bits
    labels.put(BinaryPrefix.KIBI(BIT), "Kibits");
    labels.put(BinaryPrefix.MEBI(BIT), "Mibits");
    labels.put(BinaryPrefix.GIBI(BIT), "Gibits");
    labels.put(BinaryPrefix.TEBI(BIT), "Tibits");
    labels.put(BinaryPrefix.PEBI(BIT), "Pibits");
    labels.put(BinaryPrefix.EXBI(BIT), "Eibits");
    labels.put(BinaryPrefix.ZEBI(BIT), "Zibits");
    labels.put(BinaryPrefix.YOBI(BIT), "Yibits");
    // Bytes
    labels.put(BYTE, "B");
    // SI bytes
    labels.put(MetricPrefix.KILO(BYTE), "kB");
    labels.put(MetricPrefix.MEGA(BYTE), "MB");
    labels.put(MetricPrefix.GIGA(BYTE), "GB");
    labels.put(MetricPrefix.TERA(BYTE), "TB");
    labels.put(MetricPrefix.PETA(BYTE), "PB");
    labels.put(MetricPrefix.EXA(BYTE), "EB");
    labels.put(MetricPrefix.ZETTA(BYTE), "ZB");
    labels.put(MetricPrefix.YOTTA(BYTE), "YB");
    // IEC Binary bytes
    labels.put(BinaryPrefix.KIBI(BYTE), "KiB");
    labels.put(BinaryPrefix.MEBI(BYTE), "MiB");
    labels.put(BinaryPrefix.GIBI(BYTE), "GiB");
    labels.put(BinaryPrefix.TEBI(BYTE), "TiB");
    labels.put(BinaryPrefix.PEBI(BYTE), "PiB");
    labels.put(BinaryPrefix.EXBI(BYTE), "EiB");
    labels.put(BinaryPrefix.ZEBI(BYTE), "ZiB");
    labels.put(BinaryPrefix.YOBI(BYTE), "YiB");

    return labels.build();
  }

  private static ImmutableMap<Unit<InformationRate>, String> buildUsInformationRateLabels() {
    ImmutableMap.Builder<Unit<InformationRate>, String> labels = ImmutableMap.builder();

    labels.put(BITS_PER_SECOND, "bps");
    labels.put(MetricPrefix.KILO(BITS_PER_SECOND), "kbps");
    labels.put(MetricPrefix.MEGA(BITS_PER_SECOND), "Mbps");
    labels.put(MetricPrefix.GIGA(BITS_PER_SECOND), "Gbps");
    labels.put(MetricPrefix.TERA(BITS_PER_SECOND), "Tbps");
    labels.put(MetricPrefix.PETA(BITS_PER_SECOND), "Pbps");
    labels.put(MetricPrefix.EXA(BITS_PER_SECOND), "Ebps");
    labels.put(MetricPrefix.ZETTA(BITS_PER_SECOND), "Zbps");
    labels.put(MetricPrefix.YOTTA(BITS_PER_SECOND), "Ybps");

    labels.put(BinaryPrefix.KIBI(BITS_PER_SECOND), "Kibps");
    labels.put(BinaryPrefix.MEBI(BITS_PER_SECOND), "Mibps");
    labels.put(BinaryPrefix.GIBI(BITS_PER_SECOND), "Gibps");
    labels.put(BinaryPrefix.TEBI(BITS_PER_SECOND), "Tibps");
    labels.put(BinaryPrefix.PEBI(BITS_PER_SECOND), "Pibps");
    labels.put(BinaryPrefix.EXBI(BITS_PER_SECOND), "Eibps");
    labels.put(BinaryPrefix.ZEBI(BITS_PER_SECOND), "Zibps");
    labels.put(BinaryPrefix.YOBI(BITS_PER_SECOND), "Yibps");

    labels.put(BYTES_PER_SECOND, "Bps");
    labels.put(MetricPrefix.KILO(BYTES_PER_SECOND), "kBps");
    labels.put(MetricPrefix.MEGA(BYTES_PER_SECOND), "MBps");
    labels.put(MetricPrefix.GIGA(BYTES_PER_SECOND), "GBps");
    labels.put(MetricPrefix.TERA(BYTES_PER_SECOND), "TBps");
    labels.put(MetricPrefix.PETA(BYTES_PER_SECOND), "PBps");
    labels.put(MetricPrefix.EXA(BYTES_PER_SECOND), "EBps");
    labels.put(MetricPrefix.ZETTA(BYTES_PER_SECOND), "ZBps");
    labels.put(MetricPrefix.YOTTA(BYTES_PER_SECOND), "YBps");

    labels.put(BinaryPrefix.KIBI(BYTES_PER_SECOND), "KiBps");
    labels.put(BinaryPrefix.MEBI(BYTES_PER_SECOND), "MiBps");
    labels.put(BinaryPrefix.GIBI(BYTES_PER_SECOND), "GiBps");
    labels.put(BinaryPrefix.TEBI(BYTES_PER_SECOND), "TiBps");
    labels.put(BinaryPrefix.PEBI(BYTES_PER_SECOND), "PiBps");
    labels.put(BinaryPrefix.EXBI(BYTES_PER_SECOND), "EiBps");
    labels.put(BinaryPrefix.ZEBI(BYTES_PER_SECOND), "ZiBps");
    labels.put(BinaryPrefix.YOBI(BYTES_PER_SECOND), "YiBps");

    return labels.build();
  }

  /**
   * Get a UnitLabelProvider that can provide labels for {@link Information} units {@link CLDR#BIT
   * BIT} and {@link CLDR#BYTE BYTE} with SI and IEC binary prefixes for the
   * {@link java.util.Locale#US US} locale.
   *
   * @return The UnitLabelProvider described above.
   */
  public static UnitLabelProvider<Information> getUsInformationLabeler() {
    return new SimpleUnitLabelProvider<>(US_INFORMATION_LABELS);
  }


  /**
   * Get a UnitLabelProvider that can provide labels for {@link InformationRate} units
   * {@link InformationRateUnits#BYTES_PER_SECOND BYTES_PER_SECOND} and
   * {@link InformationRateUnits#BITS_PER_SECOND BITS_PER_SECOND} with SI and IEC binary prefixes
   * for the {@link java.util.Locale#US US} locale.
   *
   * @return The UnitLabelProvider described above.
   */
  public static UnitLabelProvider<InformationRate> getUsInformationRateLabeler() {
    return new SimpleUnitLabelProvider<>(US_INFORMATION_RATE_LABELS);
  }
}
