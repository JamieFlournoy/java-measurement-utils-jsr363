package com.pervasivecode.utils.measure;

import java.math.BigDecimal;

/**
 * Prefixes in the SI system (Kilo- = 1000, Mebi- = 1000*Kilo, milli- = 1/1000 etc.) that represent
 * 1000^x where x is an integer.
 */
class SiThousandPrefixes {
  private SiThousandPrefixes() {}

  //
  // Greater than one
  //

  static final BigDecimal KILO_FACTOR = BigDecimal.valueOf(1000L);

  static final BigDecimal MEGA_FACTOR = KILO_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal GIGA_FACTOR = MEGA_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal TERA_FACTOR = GIGA_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal PETA_FACTOR = TERA_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal EXA_FACTOR = PETA_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal ZETTA_FACTOR = EXA_FACTOR.multiply(KILO_FACTOR);

  static final BigDecimal YOTTA_FACTOR = ZETTA_FACTOR.multiply(KILO_FACTOR);

  //
  // Less than one
  //
  
  static final BigDecimal MILLI_FACTOR = BigDecimal.valueOf(1, 3);

  static final BigDecimal MICRO_FACTOR = MILLI_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal NANO_FACTOR = MICRO_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal PICO_FACTOR = NANO_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal FEMTO_FACTOR = PICO_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal ATTO_FACTOR = FEMTO_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal ZEPTO_FACTOR = ATTO_FACTOR.multiply(MILLI_FACTOR);

  static final BigDecimal YOCTO_FACTOR = ZEPTO_FACTOR.multiply(MILLI_FACTOR);
}
