package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;

/**
 * Prefixes in the SI system (Kilo- = 1000, Mebi- = 1000*Kilo, milli- = 1/1000 etc.) that represent
 * 1000^x where x is an integer.
 */
class SiThousandPrefixes {
  static final long KILO_FACTOR = 1000L;

  static final long MEGA_FACTOR = KILO_FACTOR * 1000L;
  
  static final long GIGA_FACTOR = MEGA_FACTOR * 1000L;
  
  static final long TERA_FACTOR = GIGA_FACTOR * 1000L;
  
  static final long PETA_FACTOR = TERA_FACTOR * 1000L;
  
  static final long EXA_FACTOR = PETA_FACTOR * 1000L;

  private static final BigDecimal KILO_BIGDECIMAL = BigDecimal.valueOf(KILO_FACTOR);
  
  static final BigDecimal ZETTA_FACTOR = BigDecimal.valueOf(EXA_FACTOR).multiply(KILO_BIGDECIMAL);
  
  static final BigDecimal YOTTA_FACTOR = ZETTA_FACTOR.multiply(KILO_BIGDECIMAL);

  static final BigDecimal MILLI_FACTOR = BigDecimal.valueOf(1).divide(KILO_BIGDECIMAL);
  
  static final BigDecimal MICRO_FACTOR = MILLI_FACTOR.divide(KILO_BIGDECIMAL);
  
  static final BigDecimal NANO_FACTOR = MICRO_FACTOR.divide(KILO_BIGDECIMAL);
  
  static final BigDecimal PICO_FACTOR = NANO_FACTOR.divide(KILO_BIGDECIMAL);

  static final BigDecimal FEMTO_FACTOR = PICO_FACTOR.divide(KILO_BIGDECIMAL);

  static final BigDecimal ATTO_FACTOR = FEMTO_FACTOR.divide(KILO_BIGDECIMAL);
  
  static final BigDecimal ZEPTO_FACTOR = ATTO_FACTOR.divide(KILO_BIGDECIMAL);
  
  static final BigDecimal YOCTO_FACTOR = ZEPTO_FACTOR.divide(KILO_BIGDECIMAL);
}
