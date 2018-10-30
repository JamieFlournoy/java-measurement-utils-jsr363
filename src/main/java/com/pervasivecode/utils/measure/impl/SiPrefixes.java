package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;

class SiPrefixes {
  static final long KILO_FACTOR = 1000L;

  static final long MEGA_FACTOR = KILO_FACTOR * 1000;

  static final long GIGA_FACTOR = MEGA_FACTOR * 1000;

  static final long TERA_FACTOR = GIGA_FACTOR * 1000;

  static final long PETA_FACTOR = TERA_FACTOR * 1000;

  static final long EXA_FACTOR = PETA_FACTOR * 1000;

  private static final BigDecimal KILO_BIGDECIMAL = BigDecimal.valueOf(KILO_FACTOR);

  static final BigDecimal ZETTA_FACTOR = BigDecimal.valueOf(EXA_FACTOR).multiply(KILO_BIGDECIMAL);

  static final BigDecimal YOTTA_FACTOR = ZETTA_FACTOR.multiply(KILO_BIGDECIMAL);
}
