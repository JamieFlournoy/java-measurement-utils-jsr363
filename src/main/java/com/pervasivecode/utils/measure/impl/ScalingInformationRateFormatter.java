package com.pervasivecode.utils.measure.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.pervasivecode.utils.measure.impl.InformationRates.BITS_PER_SECOND;
import static com.pervasivecode.utils.measure.impl.InformationRates.BYTES_PER_SECOND;
import java.io.IOException;
import java.text.NumberFormat;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.UnitFormat;
import com.pervasivecode.utils.measure.api.QuantityFormatter;
import com.pervasivecode.utils.measure.api.SystemOfPrefixes;
import systems.uom.quantity.InformationRate;
import tec.uom.lib.common.BinaryPrefix;
import tec.uom.se.format.SimpleUnitFormat;
import tec.uom.se.unit.MetricPrefix;

class ScalingInformationRateFormatter implements QuantityFormatter<InformationRate> {
  @Deprecated
  public static ScalingInformationRateFormatter bitsPerSecondFormatter(SystemOfPrefixes prefixes,
      NumberFormat numberFormat) {
    return new ScalingInformationRateFormatter(prefixes, InformationRates.BITS_PER_SECOND,
        numberFormat);
  }

  @Deprecated
  public static ScalingInformationRateFormatter bytesPerSecondFormatter(SystemOfPrefixes prefixes,
      NumberFormat numberFormat) {
    return new ScalingInformationRateFormatter(prefixes, InformationRates.BYTES_PER_SECOND,
        numberFormat);
  }

  private final SystemOfPrefixes prefixes;
  private final Unit<InformationRate> baseUnit;
  private final NumberFormat numberFormat;

  public ScalingInformationRateFormatter(SystemOfPrefixes prefixes,
      Unit<InformationRate> baseInformationUnit, NumberFormat numberFormat) {
    this.prefixes = checkNotNull(prefixes);
    this.baseUnit = checkNotNull(baseInformationUnit);
    this.numberFormat = checkNotNull(numberFormat);
  }

  @Override
  public String format(Quantity<InformationRate> measure) {
    Quantity<InformationRate> m = QuantityAutoscaling.autoscale(measure, baseUnit, prefixes);

    // TODO don't reconfigure this on every call.
    UnitFormat fmt = SimpleUnitFormat.getInstance();

    // TODO localize these labels.
    fmt.label(BITS_PER_SECOND, "bps");
    fmt.label(MetricPrefix.KILO(BITS_PER_SECOND) , "Kbps");
    fmt.label(MetricPrefix.MEGA(BITS_PER_SECOND) , "Mbps");
    fmt.label(MetricPrefix.GIGA(BITS_PER_SECOND) , "Gbps");
    fmt.label(MetricPrefix.PETA(BITS_PER_SECOND), "Pbps");
    fmt.label(MetricPrefix.EXA(BITS_PER_SECOND), "Ebps");
    fmt.label(MetricPrefix.ZETTA(BITS_PER_SECOND), "Zbps");
    fmt.label(MetricPrefix.YOTTA(BITS_PER_SECOND), "Ybps");

    fmt.label(BinaryPrefix.KIBI(BITS_PER_SECOND) , "Kibps");
    fmt.label(BinaryPrefix.MEBI(BITS_PER_SECOND) , "Mibps");
    fmt.label(BinaryPrefix.GIBI(BITS_PER_SECOND) , "Gibps");
    fmt.label(BinaryPrefix.TEBI(BITS_PER_SECOND) , "Tibps");
    fmt.label(BinaryPrefix.PEBI(BITS_PER_SECOND), "Pibps");
    fmt.label(BinaryPrefix.EXBI(BITS_PER_SECOND), "Eibps");
    fmt.label(BinaryPrefix.ZEBI(BITS_PER_SECOND), "Zibps");
    fmt.label(BinaryPrefix.YOBI(BITS_PER_SECOND), "Yibps");

    fmt.label(BYTES_PER_SECOND, "Bps");
    fmt.label(MetricPrefix.KILO(BYTES_PER_SECOND) , "KBps");
    fmt.label(MetricPrefix.MEGA(BYTES_PER_SECOND) , "MBps");
    fmt.label(MetricPrefix.GIGA(BYTES_PER_SECOND) , "GBps");
    fmt.label(MetricPrefix.PETA(BYTES_PER_SECOND), "Pbps");
    fmt.label(MetricPrefix.EXA(BYTES_PER_SECOND), "Ebps");
    fmt.label(MetricPrefix.ZETTA(BYTES_PER_SECOND), "Zbps");
    fmt.label(MetricPrefix.YOTTA(BYTES_PER_SECOND), "Ybps");

    fmt.label(BinaryPrefix.KIBI(BYTES_PER_SECOND) , "KiBps");
    fmt.label(BinaryPrefix.MEBI(BYTES_PER_SECOND) , "MiBps");
    fmt.label(BinaryPrefix.GIBI(BYTES_PER_SECOND) , "GiBps");
    fmt.label(BinaryPrefix.TEBI(BYTES_PER_SECOND) , "TiBps");
    fmt.label(BinaryPrefix.PEBI(BYTES_PER_SECOND), "PiBps");
    fmt.label(BinaryPrefix.EXBI(BYTES_PER_SECOND), "EiBps");
    fmt.label(BinaryPrefix.ZEBI(BYTES_PER_SECOND), "ZiBps");
    fmt.label(BinaryPrefix.YOBI(BYTES_PER_SECOND), "YiBps");



    StringBuilder sb = new StringBuilder();
    try {
      fmt.format(m.getUnit(), sb);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
    final String formattedUnit = sb.toString();
    return String.format("%s %s", this.numberFormat.format(m.getValue().doubleValue()),
        formattedUnit);
  }
}
