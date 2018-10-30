package com.pervasivecode.utils.measure.impl;

import static com.google.common.truth.Truth.assertThat;
import static com.pervasivecode.utils.measure.api.SystemOfPrefixes.IEC_BINARY;
import static com.pervasivecode.utils.measure.api.SystemOfPrefixes.SI;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.EXBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.GIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.KIBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.MEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.PEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.TEBI_FACTOR;
import static com.pervasivecode.utils.measure.impl.IecBinaryPrefixes.YOBI_FACTOR;
import static systems.uom.unicode.CLDR.BIT;
import static systems.uom.unicode.CLDR.BYTE;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.measure.Quantity;
import org.junit.Test;
import systems.uom.quantity.Information;
import systems.uom.unicode.CLDR;
import tec.uom.se.quantity.Quantities;

public class ScalingInformationFormatterTest {
  // TODO test with other locales than US, for example France.
  
  private static final NumberFormat US_NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
  private static final NumberFormat FR_NUMBER_FORMAT = NumberFormat.getInstance(Locale.FRANCE);
  private static final NumberFormat DE_NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMANY);

  //
  // Bits ---------------------------------------------------------------------
  //

  private static Quantity<Information> measureOfBits(long bytesValue) {
    return Quantities.getQuantity(bytesValue, CLDR.BIT);
  }
  
  private static void checkBitsFormat(Quantity<Information> bitsValue, String expectedFormat) {
    checkBitsFormat(bitsValue, expectedFormat, US_NUMBER_FORMAT);
  }

  // Bits are usually used as a prefixless unit in small quantities to refer to storage, or in
  // larger quantities with SI prefixes to talk about data communications. So we will test with
  // SI prefixes.
  private static void checkBitsFormat(Quantity<Information> bitsValue, String expectedFormat,
      NumberFormat numberFormat) {
    ScalingInformationFormatter siBitFormatter =
        new ScalingInformationFormatter(SI, BIT, numberFormat);
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
    checkBitsFormat(measureOfBits(1_001), "1.001 Kbits");
    checkBitsFormat(measureOfBits(2_002), "2.002 Kbits");
    checkBitsFormat(measureOfBits(19_009), "19.009 Kbits");
  }

  @Test
  public void format_bit_withMillionsOfBits_shouldUseMegabits() {
    checkBitsFormat(measureOfBits(1_752_309), "1.752 Mbits");
    checkBitsFormat(measureOfBits(17_523_094), "17.523 Mbits");

    checkBitsFormat(measureOfBits(1_752_309), "1,752 Mbits", FR_NUMBER_FORMAT);
    checkBitsFormat(measureOfBits(1_752_309), "1,752 Mbits", DE_NUMBER_FORMAT);
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

  private static void checkBytesFormat(Quantity<Information> bytesValue, String expectedFormat) {
    checkBytesFormat(bytesValue, expectedFormat, US_NUMBER_FORMAT);
  }

  // Bytes are usually used as a measure of storage. In RAM, bytes are usually prefixed with IEC
  // binary prefixes; in bulk storage (hard disks, SSDs, etc.) bytes are usually prefixed with SI
  // prefixes. We will test byte quantities formatted with IEC binary prefixes in this test.
  private static void checkBytesFormat(Quantity<Information> bytesValue, String expectedFormat,
      NumberFormat numberFormat) {
    ScalingInformationFormatter bytesIecFormatter =
        new ScalingInformationFormatter(IEC_BINARY, BYTE, numberFormat);
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

    checkBytesFormat(oneKibibyte.multiply(17.112), "17,112 KiB", FR_NUMBER_FORMAT);
    checkBytesFormat(oneKibibyte.multiply(17.112), "17,112 KiB", DE_NUMBER_FORMAT);

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
}
