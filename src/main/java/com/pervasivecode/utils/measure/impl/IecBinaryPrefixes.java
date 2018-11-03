package com.pervasivecode.utils.measure.impl;

import java.math.BigDecimal;

/**
 * Prefixes in the IEC binary system (Kibi- = 1024, Mebi- = 1024*Kibi, etc.).
 */
class IecBinaryPrefixes {
  static final long KIBI_FACTOR = 1024L;

  static final long MEBI_FACTOR = KIBI_FACTOR * 1024L;

  static final long GIBI_FACTOR = MEBI_FACTOR * 1024L;

  static final long TEBI_FACTOR = GIBI_FACTOR * 1024L;

  static final long PEBI_FACTOR = TEBI_FACTOR * 1024L;

  static final long EXBI_FACTOR = PEBI_FACTOR * 1024L;

  private static final BigDecimal KIBI_BIGDECIMAL = BigDecimal.valueOf(KIBI_FACTOR);

  static final BigDecimal ZEBI_FACTOR = BigDecimal.valueOf(EXBI_FACTOR).multiply(KIBI_BIGDECIMAL);

  static final BigDecimal YOBI_FACTOR = ZEBI_FACTOR.multiply(KIBI_BIGDECIMAL);
}
