package com.pervasivecode.utils.measure;

import static com.google.common.truth.Truth.assertThat;
import static tec.uom.se.unit.MetricPrefix.MEGA;
import static tec.uom.se.unit.Units.KILOGRAM;
import java.math.BigInteger;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Mass;
import org.junit.Test;
import tec.uom.se.function.RationalConverter;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.MetricPrefix;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.unit.Units;

public class SiPrefixSelectorTest {

  @Test
  public void selectBestPrefix_withSmallerThanYocto_shouldUseYocto() {
    // 1.782662×10^−36 kg
    Unit<Mass> electronVoltPerCSquared =
        new TransformedUnit<Mass>(KILOGRAM,
            new RationalConverter(BigInteger.valueOf(1_782_662), BigInteger.TEN.pow(42)));

    Quantity<Mass> oneElectronVoltPerCSquared =
        Quantities.getQuantity(1, electronVoltPerCSquared).to(KILOGRAM);
    assertThat(oneElectronVoltPerCSquared.toString()).isEqualTo("1.782662E-36 kg");
    
    // Electron mass is 0.511 MeV/c2
    Quantity<Mass> massOfElectron = Quantities.getQuantity(0.511, MEGA(electronVoltPerCSquared));

    Quantity<Mass> massOfElectronInScaledGrams =
        new SiPrefixSelector().selectBestPrefix(massOfElectron, Units.GRAM);

    // 1 eV/c2 = 1.782662×10^−36 kg
    // Electron mass is 0.511 MeV/c2
    // 0.511 MeV/c2 = 5.11 x10^5 (1.782662×10^−36 kg)
    // = 9.10940282 ×10^−31 kg
    // = 9.10940282 ×10^−28 g
    // = 9.10940282 x10^-4 yg
    assertThat(massOfElectronInScaledGrams.getUnit()).isEqualTo(MetricPrefix.YOCTO(Units.GRAM));
    assertThat(massOfElectronInScaledGrams.getUnit().toString()).isEqualTo("yg");
    assertThat(massOfElectronInScaledGrams.getValue().doubleValue()).isEqualTo(9.10940282E-4);
  }
}
