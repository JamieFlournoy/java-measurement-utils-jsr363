Feature: Usage Examples

  Scenario: Bytes Per Second Example
    Given I am running the Bytes Per Second Example
    When I run the program
    Then I should see the output
    """
    4 KiBps
    16 KiBps
    64 KiBps
    256 KiBps
    1 MiBps
    4 MiBps
    16 MiBps
    64 MiBps
    256 MiBps
    1 GiBps
    4 GiBps
    16 GiBps
	64 GiBps
	256 GiBps
	1 TiBps
	4 TiBps

    """

  Scenario: Bits Per Second Example
    Given I am running the Bits Per Second Example
    When I run the program
    Then I should see the output
    """
    3 bps
    30 bps
    300 bps
    3 kbps
    30 kbps
    300 kbps
    3 Mbps
    30 Mbps
    300 Mbps
    3 Gbps
    30 Gbps
    300 Gbps
	3 Tbps
	30 Tbps
	300 Tbps
	3 Pbps
	30 Pbps
	300 Pbps

    """

  Scenario: Duration Example
    Given I am running the Duration Example
    When I run the program
    Then I should see the output
    """
	1ms (1 ms)
	4ms (4 ms)
	16ms (16 ms)
	64ms (64 ms)
	256ms (256 ms)
	1.024s (1.024 s)
	4.096s (4.096 s)
	16.384s (16.384 s)
	1m 5s (65.536 s)
	4m 22s (262.144 s)
	17m 28s (1.049 ks)
	1h 9m 54s (4.194 ks)
	4h 39m 37s (16.777 ks)
	18h 38m 28s (67.109 ks)
	3d 2h 33m 55s (268.435 ks)
	1w 5d 10h 15m 41s (1.074 Ms)

    """
