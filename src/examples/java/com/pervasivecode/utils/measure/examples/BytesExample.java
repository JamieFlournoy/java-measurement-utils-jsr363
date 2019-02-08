package com.pervasivecode.utils.measure.examples;

import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.PrintWriter;
import javax.measure.Quantity;
import com.pervasivecode.utils.measure.QuantityFormatter;
import com.pervasivecode.utils.measure.ScalingFormatters;
import systems.uom.quantity.Information;
import systems.uom.unicode.CLDR;
import tec.uom.se.quantity.Quantities;

/**
 * Print a series of quantities of data in terms of bytes, scaled with IEC binary prefixes and
 * formatted for the {@link Locale#US US} locale.
 *
 * @see {@code examples.feature} for expected output of this program.
 */
public class BytesExample implements ExampleApplication {
  public static void main(String[] args) {
    new BytesExample().runExample(new PrintWriter(System.out, true, UTF_8));
  }

  @Override
  public void runExample(PrintWriter output) {
    QuantityFormatter<Information> formatter = ScalingFormatters.dataAmountInIecBinaryBytesUs();

    for (int i = 0; i < 16; i++) {
      int exponent = 12 + i * 2;
      long value = 1L << exponent;
      Quantity<Information> bps = Quantities.getQuantity(value, CLDR.BYTE);
      output.println(formatter.format(bps));
    }
  }
}
