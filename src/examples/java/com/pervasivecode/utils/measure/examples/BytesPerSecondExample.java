package com.pervasivecode.utils.measure.examples;

import static com.pervasivecode.utils.measure.InformationRateUnits.BYTES_PER_SECOND;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.PrintWriter;
import java.util.Locale;
import javax.measure.Quantity;
import com.pervasivecode.utils.measure.QuantityFormatter;
import com.pervasivecode.utils.measure.ScalingFormatters;
import systems.uom.quantity.InformationRate;
import tec.uom.se.quantity.Quantities;

/**
 * Print a series of quantities of data rate in terms of bytes per second, scaled with IEC binary
 * prefixes and formatted for the {@link Locale#US US} locale.
 *
 * @see {@code examples.feature} for expected output of this program.
 */
public class BytesPerSecondExample implements ExampleApplication {
  public static void main(String[] args) {
    new BytesPerSecondExample().runExample(new PrintWriter(System.out, true, UTF_8));
  }

  @Override
  public void runExample(PrintWriter output) {
    QuantityFormatter<InformationRate> formatter =
        ScalingFormatters.dataRateInIecBinaryBytesPerSecondUs();

    for (int i = 0; i < 16; i++) {
      int exponent = 12 + i * 2;
      long value = 1L << exponent;
      Quantity<InformationRate> bps = Quantities.getQuantity(value, BYTES_PER_SECOND);
      output.println(formatter.format(bps));
    }
  }
}

