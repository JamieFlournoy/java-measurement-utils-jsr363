package com.pervasivecode.utils.measure.impl;

import static systems.uom.unicode.CLDR.BIT;
import static systems.uom.unicode.CLDR.BYTE;
import static tec.uom.se.unit.Units.SECOND;
import javax.measure.Unit;
import systems.uom.quantity.InformationRate;
import tec.uom.se.unit.ProductUnit;

/** Units to help with type-safe formatting of information rates. */
public class InformationRates {
  public final static Unit<InformationRate> BYTES_PER_SECOND =
      new ProductUnit<>(BYTE.divide(SECOND));

  public final static Unit<InformationRate> BITS_PER_SECOND = new ProductUnit<>(BIT.divide(SECOND));
}
