Feature: params demo

  @severity=trivial
  Scenario: Data Table demo 1
    Given I print a String "my string to print"
    Given I print a String "my string to print" 5 times
    Given Data Table "my table" values:
      | asd1 |
    Given Data Table "my table as map" values as map:
      | key_1 | value_1 |

  @severity=trivial
  Scenario: Data Table demo 2
    Given I print a String "my string to print"
    Given I print a String "my string to print" 5 times
    Given Data Table "my table" values:
      | asd1 |
    Given Data Table "my table as map" values as map:
      | key_1 | value_1 |

  @severity=trivial
  Scenario: Data Table demo 3
    Given I print a String "my string to print"
    Given I print a String "my string to print" 5 times
    Given Data Table "my table" values:
      | asd1 |
    Given Data Table "my table as map" values as map:
      | key_1 | value_1 |

  @severity=critical
  Scenario: Data Table demo 4
    Given I print a String "my string to print"
    Given I print a String "my string to print" 5 times
    Given Data Table "my table" values:
      | asd1 |
    Given Data Table "my table as map" values as map:
      | key_1 | value_1 |
