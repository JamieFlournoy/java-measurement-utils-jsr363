package com.pervasivecode.utils.measure;

import static com.google.common.truth.Truth.assertThat;
import java.math.BigInteger;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import org.junit.Test;
import si.uom.NonSI;
import systems.uom.quantity.Information;
import systems.uom.unicode.CLDR;
import tec.uom.se.function.RationalConverter;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.MetricPrefix;
import tec.uom.se.unit.ProductUnit;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.unit.Units;

/**
 * Tests for branches in SiPrefixSelector that weren't covered by other tests.
 */
public class SiPrefixSelectorTest {
  private interface Impulse extends Quantity<Impulse> {};

  @Test
  public void selectBestPrefix_withSmallerThanYocto_shouldUseYocto() {
    // "Electron Volt per C squared" is a very, very small unit of mass used in particle physics.
    // In the SI system it's a derived unit. Here is its SI definition in terms of Kilograms:
    // 1 eV/c2 = 1.782662×10^−36 kg
    Unit<Mass> electronVoltPerCSquared = new TransformedUnit<Mass>(Units.KILOGRAM,
        new RationalConverter(BigInteger.valueOf(1_782_662), BigInteger.TEN.pow(42)));

    Quantity<Mass> oneElectronVoltPerCSquared =
        Quantities.getQuantity(1, electronVoltPerCSquared).to(Units.KILOGRAM);
    assertThat(oneElectronVoltPerCSquared.toString()).isEqualTo("1.782662E-36 kg");

    // An electron has a mass of 0.511 MeV/c2.
    Quantity<Mass> massOfElectron =
        Quantities.getQuantity(0.511, MetricPrefix.MEGA(electronVoltPerCSquared));

    // 10^-36 of a gram is very small. Can we make that a little easier to write with an SI prefix?
    Quantity<Mass> massOfElectronInScaledGrams =
        new SiPrefixSelector().selectBestPrefix(massOfElectron, Units.GRAM);

    // Given: 1 eV/c2 = 1.782662×10^−36 kg
    // Given: An electron's mass is 0.511 MeV/c2
    // We want the mass in grams rather than eV/c2.
    // 0.511 MeV/c2 = 5.11 x10^5 eV/c2
    // = 5.11 x10^5 (1.782662×10^−36 kg)
    // = 9.10940282 ×10^−31 kg
    // = 9.10940282 ×10^−28 g
    // = 9.10940282 x10^-4 yg
    // So, we expect a value of about 9.1E-4 in units of yoctograms.

    double expectedMagnitude = 9.10940282E-4;
    Unit<Mass> expectedUnit = MetricPrefix.YOCTO(Units.GRAM);
    String expectedUnitSuffix = "yg";

    assertThat(massOfElectronInScaledGrams.getUnit()).isEqualTo(expectedUnit);
    assertThat(massOfElectronInScaledGrams.getUnit().toString()).isEqualTo(expectedUnitSuffix);
    assertThat(massOfElectronInScaledGrams.getValue().doubleValue()).isEqualTo(expectedMagnitude);
  }

  @Test
  public void selectBestPrefix_withZettaSizedValue_shouldUseZetta() {
    Quantity<AmountOfSubstance> oneHalfMole = Quantities.getQuantity(0.5, Units.MOLE);
    Quantity<AmountOfSubstance> numAtomsInOneHalfMole = oneHalfMole.to(NonSI.ATOM);

    // Avogadro's number: 6.023 × 10^23
    // Zetta = 10^21
    // So 1/2 mole should be around (6.023 x 10^2) / 2 atoms, or 301.15 Zetta-atoms.

    double expectedMagnitude = 301.15d;
    Unit<AmountOfSubstance> expectedUnits = MetricPrefix.ZETTA(NonSI.ATOM);

    Quantity<AmountOfSubstance> scaledNumAtomsInOneHalfMole =
        new SiPrefixSelector().selectBestPrefix(numAtomsInOneHalfMole, NonSI.ATOM);

    assertThat(scaledNumAtomsInOneHalfMole.getValue().doubleValue()).isWithin(0.1d)
        .of(expectedMagnitude);
    assertThat(scaledNumAtomsInOneHalfMole.getUnit()).isEqualTo(expectedUnits);
  }

  @Test
  public void selectBestPrefix_withPetaSizedValue_shouldUsePeta() {
    Unit<Impulse> newtonSecond =
        new ProductUnit<Impulse>(Units.NEWTON.multiply(Units.SECOND)).alternate("N·s");

    // According to Wikipedia, there are 900 million dogs and 7.7 billion people in the world as of
    // 2018. Petting a dog involves a variable amount of force pressing on the dog, but we will
    // estimate the petting force at 2 newtons. So, if every human pets every dog once for a
    // duration of one second, how many newton-seconds of impulse is that?
    long numDogs = 900_000_000L;
    long numHumans = 7_700_000_000L;
    float likelihoodOfLikingDogs = 0.74f;
    float accessToFriendlyDog = 0.05f;

    // Only people who have access to friendly dogs, and who like dogs, would participate in this
    // worldwide dog-petting challenge.
    double expectedNumPettingEvents =
        (numDogs * numHumans) * likelihoodOfLikingDogs * accessToFriendlyDog;

    // How hard someone presses their hand on the dog multiplied by the duration of the petting
    // event.
    Quantity<Impulse> impulsePerPettingEvent = Quantities.getQuantity(2, newtonSecond);

    // How hard all hands press on all dogs, adjusting for access to friendly dogs and for people
    // who don't like dogs.
    Quantity<Impulse> impulseForWorldwideDogPetting = impulsePerPettingEvent.multiply(numDogs)
        .multiply(numHumans).multiply(likelihoodOfLikingDogs).multiply(accessToFriendlyDog);

    // Scale this very large number using an SI prefix.
    Quantity<Impulse> scaledImpulse =
        new SiPrefixSelector().selectBestPrefix(impulseForWorldwideDogPetting, newtonSecond);

    double expectedMagnitude =
        (expectedNumPettingEvents * impulsePerPettingEvent.getValue().intValue())
            / SiThousandPrefixes.PETA_FACTOR.doubleValue();
    Unit<Impulse> expectedUnit = MetricPrefix.PETA(newtonSecond);
    String expectedSuffix = "P(N·s)";

    assertThat(scaledImpulse.getValue().doubleValue()).isWithin(1E-3).of(expectedMagnitude);
    assertThat(scaledImpulse.getUnit()).isEqualTo(expectedUnit);
    assertThat(scaledImpulse.getUnit().toString()).isEqualTo(expectedSuffix);
  }

  @Test
  public void selectBestPrefix_withTeraSizedValue_shouldUseTera() {
    // The Seagate BarraCuda Pro model ST14000DM001 has a stated capacity of 14TB.
    // Its data sheet says it has 27,344,764,928 sectors when formatted, and 512 bytes/sector.
    // So, how precise is the 14TB figure?
    Quantity<Information> bytesPerSector = Quantities.getQuantity(512, CLDR.BYTE);
    long sectorCount = 27_344_764_928L;

    Quantity<Information> bytesPerDisk = bytesPerSector.multiply(sectorCount);

    long expectedBytesPerDisk = 14_000_519_643_136L;
    assertThat(bytesPerDisk.getValue().longValue()).isEqualTo(expectedBytesPerDisk);

    Quantity<Information> scaledDiskCapacity =
        new SiPrefixSelector().selectBestPrefix(bytesPerDisk, CLDR.BYTE);
    double expectedMagnitude = 14.000_519_643_136d;
    Unit<Information> expectedScaledUnit = MetricPrefix.TERA(CLDR.BYTE);
    String expectedUnitSuffix = "Tbyte";

    assertThat(scaledDiskCapacity.getUnit()).isEqualTo(expectedScaledUnit);
    assertThat(scaledDiskCapacity.getUnit().toString()).isEqualTo(expectedUnitSuffix);
    assertThat(scaledDiskCapacity.getValue().doubleValue()).isEqualTo(expectedMagnitude);
  }

  @Test
  public void selectBestPrefix_withGigaSizedValue_shouldUseGiga() {
    // This is the amount of power the time machine in "Back to the Future" needs in order to work.
    Quantity<Power> fluxCapacitorPowerInWatts = Quantities.getQuantity(1_210_000_000L, Units.WATT);

    Quantity<Power> fluxCapacitorPowerScaled =
        new SiPrefixSelector().selectBestPrefix(fluxCapacitorPowerInWatts, Units.WATT);

    double expectedMagnitude = 1.21d;
    Unit<Power> expectedUnit = MetricPrefix.GIGA(Units.WATT);
    String expectedUnitSuffix = "GW";

    assertThat(fluxCapacitorPowerScaled.getUnit()).isEqualTo(expectedUnit);
    assertThat(fluxCapacitorPowerScaled.getUnit().toString()).isEqualTo(expectedUnitSuffix);
    assertThat(fluxCapacitorPowerScaled.getValue().doubleValue()).isEqualTo(expectedMagnitude);
  }
}
