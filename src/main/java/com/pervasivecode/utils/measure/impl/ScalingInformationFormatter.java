package com.pervasivecode.utils.measure.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static systems.uom.unicode.CLDR.BIT;
import static systems.uom.unicode.CLDR.BYTE;
import java.io.IOException;
import java.text.NumberFormat;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.UnitFormat;
import com.pervasivecode.utils.measure.api.QuantityFormatter;
import com.pervasivecode.utils.measure.api.SystemOfPrefixes;
import systems.uom.quantity.Information;
import systems.uom.unicode.CLDR;
import tec.uom.lib.common.BinaryPrefix;
import tec.uom.se.format.SimpleUnitFormat;
import tec.uom.se.unit.MetricPrefix;

class ScalingInformationFormatter implements QuantityFormatter<Information> {
  @Deprecated
  public static ScalingInformationFormatter bitsFormatter(SystemOfPrefixes prefixes,
      NumberFormat numberFormat) {
    return new ScalingInformationFormatter(prefixes, BIT, numberFormat);
  }

  @Deprecated
  public static ScalingInformationFormatter bytesFormatter(SystemOfPrefixes prefixes,
      NumberFormat numberFormat) {
    return new ScalingInformationFormatter(prefixes, BYTE, numberFormat);
  }

  private final SystemOfPrefixes prefixes;
  private final Unit<Information> baseUnit;
  private final NumberFormat numberFormat;

  public ScalingInformationFormatter(SystemOfPrefixes prefixes, Unit<Information> baseUnit,
      NumberFormat numberFormat) {
    this.prefixes = checkNotNull(prefixes);
    this.baseUnit = checkNotNull(baseUnit);
    this.numberFormat = checkNotNull(numberFormat);
  }

  @Override
  public String format(Quantity<Information> measure) {
    Quantity<Information> m = QuantityAutoscaling.autoscale(measure, baseUnit, prefixes);

    // TODO don't reconfigure this on every call.
    UnitFormat fmt = SimpleUnitFormat.getInstance();

    // TODO localize these labels
    fmt.label(MetricPrefix.KILO(CLDR.BIT), "Kbits");
    fmt.label(MetricPrefix.MEGA(CLDR.BIT), "Mbits");
    fmt.label(MetricPrefix.GIGA(CLDR.BIT), "Gbits");
    fmt.label(MetricPrefix.TERA(CLDR.BIT), "Tbits");
    fmt.label(MetricPrefix.PETA(CLDR.BIT), "Pbits");
    fmt.label(MetricPrefix.EXA(CLDR.BIT), "Ebits");
    fmt.label(MetricPrefix.ZETTA(CLDR.BIT), "Zbits");
    fmt.label(MetricPrefix.YOTTA(CLDR.BIT), "Ybits");
    // TODO add IEC binary prefix labels for bits here

    fmt.label(CLDR.BYTE, "B");
    fmt.label(BinaryPrefix.KIBI(CLDR.BYTE), "KiB");
    fmt.label(BinaryPrefix.MEBI(CLDR.BYTE), "MiB");
    fmt.label(BinaryPrefix.GIBI(CLDR.BYTE), "GiB");
    fmt.label(BinaryPrefix.TEBI(CLDR.BYTE), "TiB");
    fmt.label(BinaryPrefix.PEBI(CLDR.BYTE), "PiB");
    fmt.label(BinaryPrefix.EXBI(CLDR.BYTE), "EiB");
    fmt.label(BinaryPrefix.ZEBI(CLDR.BYTE), "ZiB");
    fmt.label(BinaryPrefix.YOBI(CLDR.BYTE), "YiB");
    // TODO add SI prefix labels for bytes here

    StringBuilder sb = new StringBuilder();
    try {
      fmt.format(m.getUnit(), sb);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
    final String formattedUnit = sb.toString();
    return String.format("%s %s", this.numberFormat.format(m.getValue().doubleValue()),
        formattedUnit);

    // long scaledUp = m.multiply(roundingPrecision).getValue().longValue();
    // boolean useWholeNumber = scaledUp % roundingPrecision == 0;
    // final Number scalarValue = m.getValue();
    // return (useWholeNumber)
    // ? String.format("%s %s", numFmt.format(scalarValue.longValue()), formattedUnit)
    // : String.format("%s %s", numFmt.format(scalarValue.doubleValue()), formattedUnit);
  }
}
