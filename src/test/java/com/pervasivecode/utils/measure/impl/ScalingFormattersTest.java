package com.pervasivecode.utils.measure.impl;

import static com.google.common.truth.Truth.assertThat;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.EXBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.GIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.KIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.MEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.PEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.TEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.YOBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.ZEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.InformationRateUnits.BITS_PER_SECOND;
import static com.pervasivecode.utils.measure.impl.InformationRateUnits.BYTES_PER_SECOND;
import static systems.uom.unicode.CLDR.BYTE;
import java.math.BigDecimal;
import javax.measure.Quantity;
import org.junit.Test;
import systems.uom.quantity.Information;
import systems.uom.quantity.InformationRate;
import systems.uom.unicode.CLDR;
import tec.uom.se.quantity.Quantities;

public class ScalingFormattersTest {
  //
  // Bits ---------------------------------------------------------------------
  //

  private static Quantity<Information> measureOfBits(long bytesValue) {
    return Quantities.getQuantity(bytesValue, CLDR.BIT);
  }

  // Bits are usually used as a prefixless unit in small quantities to refer to storage, or in
  // larger quantities with SI prefixes to talk about data communications. So we will test with
  // SI prefixes.
  private static void checkBitsFormat(Quantity<Information> bitsValue, String expectedFormat) {
    ScalingFormatter<Information> siBitFormatter = ScalingFormatters.dataAmountInSiBitsUs();
    assertThat(siBitFormatter.format(bitsValue)).isEqualTo(expectedFormat);
  }

  @Test
  public void format_bit_withSeveralBits_shouldUseWholeBits() {
    checkBitsFormat(measureOfBits(1), "1 bit");
    checkBitsFormat(measureOfBits(2), "2 bit");
    checkBitsFormat(measureOfBits(10), "10 bit");
  }

  @Test
  public void format_bit_withThousandsOfBits_shouldUseKilobits() {
    checkBitsFormat(measureOfBits(1_001), "1.001 kbits");
    checkBitsFormat(measureOfBits(2_002), "2.002 kbits");
    checkBitsFormat(measureOfBits(19_009), "19.009 kbits");
  }

  @Test
  public void format_bit_withMillionsOfBits_shouldUseMegabits() {
    checkBitsFormat(measureOfBits(1_752_309), "1.752 Mbits");
    checkBitsFormat(measureOfBits(17_523_094), "17.523 Mbits");
  }

  //
  // Bytes --------------------------------------------------------------------
  //

  private static Quantity<Information> measureOfBytes(long bytesValue) {
    return Quantities.getQuantity(bytesValue, BYTE);
  }

  private static Quantity<Information> measureOfBytes(BigDecimal bigValue) {
    return Quantities.getQuantity(bigValue, BYTE);
  }

  // Bytes are usually used as a measure of storage. In RAM, bytes are usually prefixed with IEC
  // binary prefixes; in bulk storage (hard disks, SSDs, etc.) bytes are usually prefixed with SI
  // prefixes. We will test byte quantities formatted with IEC binary prefixes in this test.
  private static void checkBytesFormat(Quantity<Information> bytesValue, String expectedFormat) {
    ScalingFormatter<Information> bytesIecFormatter =
        ScalingFormatters.dataAmountInIecBinaryBytesUs();
    assertThat(bytesIecFormatter.format(bytesValue)).isEqualTo(expectedFormat);
  }

  @Test
  public void format_byte_withSeveralBits_shouldUseFractionalBytes() {
    checkBytesFormat(measureOfBits(1), "0.125 B");
    checkBytesFormat(measureOfBits(2), "0.25 B");
    checkBytesFormat(measureOfBits(10), "1.25 B");
  }

  @Test
  public void format_byte_withSeveralBytes_shouldUseWholeBytes() {
    checkBytesFormat(measureOfBytes(17), "17 B");
  }

  @Test
  public void format_byte_withHundredsOfBytes_shouldUseBytes() {
    checkBytesFormat(measureOfBytes(317), "317 B");
  }

  @Test
  public void format_byte_withThousandsOfBytes_shouldUseKibibytes() {
    Quantity<Information> oneKibibyte = measureOfBytes(KIBI_FACTOR);
    checkBytesFormat(oneKibibyte.multiply(1.711), "1.711 KiB");
    checkBytesFormat(oneKibibyte.multiply(17.112), "17.112 KiB");
    checkBytesFormat(oneKibibyte.multiply(1023), "1,023 KiB");
    checkBytesFormat(oneKibibyte.multiply(1024), "1 MiB");
  }

  @Test
  public void format_byte_withMillionsOfBytes_shouldUseMebibytes() {
    Quantity<Information> oneMebibyte = measureOfBytes(MEBI_FACTOR);
    checkBytesFormat(oneMebibyte, "1 MiB");
    checkBytesFormat(oneMebibyte.multiply(1.671), "1.671 MiB");
    checkBytesFormat(oneMebibyte.multiply(16.711), "16.711 MiB");
  }

  @Test
  public void format_byte_withBillionsOfBytes_shouldUseGibibytes() {
    checkBytesFormat(measureOfBytes(GIBI_FACTOR).multiply(1.632), "1.632 GiB");
  }

  @Test
  public void format_byte_withTrillionsOfBytes_shouldUseTebibytes() {
    Quantity<Information> oneTebibyte = measureOfBytes(TEBI_FACTOR);
    checkBytesFormat(oneTebibyte, "1 TiB");
    checkBytesFormat(oneTebibyte.multiply(1.632), "1.632 TiB");
  }

  @Test
  public void format_byte_withQuadrillionsOfBytes_shouldUsePebibytes() {
    checkBytesFormat(measureOfBytes(PEBI_FACTOR).multiply(4), "4 PiB");
  }

  @Test
  public void format_byte_withQuintillionsOfBytes_shouldUseExbibytes() {
    checkBytesFormat(measureOfBytes(EXBI_FACTOR).multiply(4), "4 EiB");
  }

  @Test
  public void format_byte_withSextillionsOfBytes_shouldUseZebibytes() {
    checkBytesFormat(measureOfBytes(EXBI_FACTOR), "1 EiB");
    checkBytesFormat(measureOfBytes(EXBI_FACTOR).multiply(4), "4 EiB");
  }

  @Test
  public void format_byte_withSeptillionsOfBytes_shouldUseYobibytes() {
    checkBytesFormat(measureOfBytes(YOBI_FACTOR).multiply(4), "4 YiB");
  }


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
    ScalingFormatter<InformationRate> formatter = ScalingFormatters.dataRateInSiBitsPerSecondUs();
    assertThat(formatter.format(bitsPerSec)).isEqualTo(expectedFormat);
  }

  @Test
  public void format_bitsPerSecond_withSeveralBitsPerSec_shouldUseBitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(17), "17 bps");
  }

  @Test
  public void format_bitsPerSecond_withThousandsOfBitsPerSec_shouldUseKiloBitsPerSecond() {
    checkBitsPerSecondFormat(measureOfBitsPerSecond(1_752), "1.752 kbps");
    checkBitsPerSecondFormat(measureOfBitsPerSecond(17_523), "17.523 kbps");
    checkBitsPerSecondFormat(measureOfBitsPerSecond(524_288), "524.288 kbps");
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
    ScalingFormatter<InformationRate> formatter =
        ScalingFormatters.dataRateInIecBinaryBytesPerSecondUs();
    assertThat(formatter.format(bytesPerSec)).isEqualTo(expectedFormat);
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
