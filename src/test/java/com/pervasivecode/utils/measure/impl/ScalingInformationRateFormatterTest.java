package com.pervasivecode.utils.measure.impl;

import static com.google.common.truth.Truth.assertThat;
import static com.pervasivecode.utils.measure.api.SystemOfPrefixes.IEC_BINARY;
import static com.pervasivecode.utils.measure.api.SystemOfPrefixes.SI;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.KIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.MEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.GIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.TEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.EXBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.PEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.YOBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.ZEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.InformationRates.BITS_PER_SECOND;
import static com.pervasivecode.utils.measure.impl.InformationRates.BYTES_PER_SECOND;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.measure.Quantity;
import org.junit.Test;
import systems.uom.quantity.InformationRate;
import tec.uom.se.quantity.Quantities;

public class ScalingInformationRateFormatterTest {
  // TODO test with other locales than US, for example France.

  private static final NumberFormat US_NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);

  //
  // Bits per second ----------------------------------------------------------
  //

  private static Quantity<InformationRate> measureOfBitsPerSecond(long bitsPerSecValue) {
    return Quantities.getQuantity(BigDecimal.valueOf(bitsPerSecValue), BITS_PER_SECOND);
  }

  private static Quantity<InformationRate> measureOfBitsPerSecond(double bitsPerSecValue) {
    return Quantities.getQuantity(BigDecimal.valueOf(bitsPerSecValue), BITS_PER_SECOND);
  }

  // Bits per second is a measurement used in data communications, and usually is presented
  // using the SI prefix system.
  private static void checkBitsPerSecondFormat(Quantity<InformationRate> bitsPerSec,
      String expectedFormat) {
    assertThat(new ScalingInformationRateFormatter(SI, BITS_PER_SECOND, US_NUMBER_FORMAT)
        .format(bitsPerSec)).isEqualTo(expectedFormat);
  }

  @Test
  public void format_bitsPerSecond_withSeveralBitsPerSec_shouldUseBitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(17), "17 bps");
  }

  @Test
  public void format_bitsPerSecond_withThousandsOfBitsPerSec_shouldUseKiloBitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(1_752), "1.752 Kbps");
    checkBitsPerSecondFormat(measureOfBitsPerSecond(17_523), "17.523 Kbps");
    checkBitsPerSecondFormat(measureOfBitsPerSecond(524_288), "524.288 Kbps");
    checkBitsPerSecondFormat(measureOfBitsPerSecond(1_048_576), "1.049 Mbps");
  }

  @Test
  public void format_bitsPerSecond_withQuintillionsOfBitsPerSec_shouldUseExabitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(1_000_000_000_000_000_000L * 4), "4 Ebps");
  }


  @Test
  public void format_bitsPerSecond_withSeptillionsOfBitsPerSec_shouldUseYottabitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(1000000000000000000000000d * 4), "4 Ybps");
  }

  //
  // Bytes per second ---------------------------------------------------------
  //

  private static Quantity<InformationRate> measureOfBytesPerSecond(long bytesPerSecValue) {
    return Quantities.getQuantity(BigDecimal.valueOf(bytesPerSecValue), BYTES_PER_SECOND);
  }

  private static Quantity<InformationRate> measureOfBytesPerSecond(BigDecimal bigValue) {
    return Quantities.getQuantity(bigValue, BYTES_PER_SECOND);
  }

  // Bytes per second is a measurement used in data processing, and usually is presented
  // using the IEC binary prefix system. So we'll test with IEC binary prefixes.
  private static void checkBytesPerSecondFormat(Quantity<InformationRate> bytesPerSec,
      String expectedFormat) {
    assertThat(new ScalingInformationRateFormatter(IEC_BINARY, BYTES_PER_SECOND, US_NUMBER_FORMAT)
        .format(bytesPerSec)).isEqualTo(expectedFormat);
  }

  @Test
  public void format_bytesPerSecond_withSeveralBps_shouldUseBytesPerSecond() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(17), "17 Bps");
  }

  @Test
  public void format_bytesPerSecond_withThousandsOfBps_shouldUseKibiBytesPerSecond() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(KIBI_FACTOR).multiply(1.711), "1.711 KiBps");
    checkBytesPerSecondFormat(measureOfBytesPerSecond(KIBI_FACTOR).multiply(17.112),
        "17.112 KiBps");
    checkBytesPerSecondFormat(measureOfBytesPerSecond(KIBI_FACTOR).multiply(512), "512 KiBps");
  }

  @Test
  public void format_bytesPerSecond_withMillionsOfBps_shouldUseMebiBytesPerSecond() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(MEBI_FACTOR), "1 MiBps");
    checkBytesPerSecondFormat(measureOfBytesPerSecond(MEBI_FACTOR).multiply(1.671), "1.671 MiBps");
    checkBytesPerSecondFormat(measureOfBytesPerSecond(MEBI_FACTOR).multiply(16.711),
        "16.711 MiBps");
  }

  @Test
  public void format_bytesPerSecond_withBillionsOfBps_shouldUseGibiBytesPerSecond() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(GIBI_FACTOR).multiply(1.632), "1.632 GiBps");
  }

  @Test
  public void format_bytesPerSecond_withTrillionsOfBytes_shouldUseTebibytes() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(TEBI_FACTOR), "1 TiBps");
    checkBytesPerSecondFormat(measureOfBytesPerSecond(TEBI_FACTOR).multiply(1.632f), "1.632 TiBps");
  }

  @Test
  public void format_bytesPerSecond_withQuadrillionsOfBytes_shouldUsePebibytes() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(PEBI_FACTOR).multiply(4), "4 PiBps");
  }

  @Test
  public void format_bytesPerSecond_withQuintillionsOfBytes_shouldUseExbibytes() {
    checkBytesPerSecondFormat(measureOfBytesPerSecond(EXBI_FACTOR).multiply(4), "4 EiBps");
  }

  @Test
  public void format_bytesPerSecond_withSextillionsOfBytes_shouldUseZebibytes() {
    BigDecimal fourZebi = ZEBI_FACTOR.multiply(BigDecimal.valueOf(4));
    checkBytesPerSecondFormat(measureOfBytesPerSecond(fourZebi), "4 ZiBps");
  }

  @Test
  public void format_bytesPerSecond_withSeptillionsOfBytes_shouldUseYobibytes() {
    BigDecimal fourYobi = YOBI_FACTOR.multiply(BigDecimal.valueOf(4));
    checkBytesPerSecondFormat(measureOfBytesPerSecond(fourYobi), "4 YiBps");
  }
}
