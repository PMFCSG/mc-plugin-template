/*
 * The MIT License
 * Copyright © 2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.djaytan.mc.template.core.person.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(SoftAssertionsExtension.class)
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = PersonService.class)
class PersonServiceTest {

  private static final UUID UNKNOWN_UUID = UUID.randomUUID();

  @InjectSoftAssertions private SoftAssertions softly;

  @Autowired private TestEntityManager entityManager;
  @Autowired private PersonService personService;

  private UUID testUuid;

  @BeforeEach
  void beforeEach() {
    PersonEntity personEntity =
        new PersonEntity("Bruce", "Layne", 44, "12 Knight St., London BC V5N 3M6, England");
    entityManager.persistAndFlush(personEntity);
    testUuid = personEntity.getId();
  }

  @AfterEach
  void afterEach() {
    PersonEntity personEntity = entityManager.find(PersonEntity.class, testUuid);
    if (personEntity != null) {
      entityManager.remove(personEntity);
      entityManager.flush();
    }
  }

  @Test
  void whenSearchingRegisteredPersonFromPseudo_thenShallReturnIt() {
    Assertions.assertThat(personService.findPerson(testUuid))
        .as("Existing person shall be found")
        .hasValueSatisfying(person -> assertThat(person.id()).isEqualTo(testUuid));
  }

  @Test
  void whenSearchingUnregisteredPersonFromPseudo_thenShallReturnEmptyValue() {
    Assertions.assertThat(personService.findPerson(UNKNOWN_UUID))
        .as("Unknown person shall not be found")
        .isEmpty();
  }

  @Test
  void whenRegisteringPerson_thenShallBeRegistered() {
    // Given
    Person person = new Person(null, "aFirstName", "aLastName", 26, "anAddress");

    // When
    Person registeredPerson = personService.registerOrUpdate(person);

    // Then
    Assertions.assertThat(personService.findPerson(registeredPerson.id()))
        .as("Initially non-registered person shall have been registered")
        .isPresent();
  }

  @Test
  void whenUpdatingPersonAddress_thenShallBeUpdated() {
    // Given
    String newAddress = "New address";
    Person alreadyRegisteredPerson = personService.findPerson(testUuid).orElse(null);
    assumeThat(alreadyRegisteredPerson).isNotNull();

    // When
    personService.registerOrUpdate(
        new Person(
            alreadyRegisteredPerson.id(),
            alreadyRegisteredPerson.firstName(),
            alreadyRegisteredPerson.lastName(),
            alreadyRegisteredPerson.age(),
            newAddress));

    // Then
    Assertions.assertThat(personService.findPerson(testUuid))
        .as("The new address shall have been persisted")
        .hasValueSatisfying(person -> assertThat(person.address()).isEqualTo(newAddress));
  }

  @Test
  void whenUnregisteringPerson_thenShallNoLongerBeRegistered() {
    // Given
    Assumptions.assumeThat(personService.findPerson(testUuid)).isPresent();

    // When
    personService.unregisterPerson(testUuid);

    // Then
    Assertions.assertThat(personService.findPerson(testUuid))
        .as("A deleted person shall no longer exist")
        .isEmpty();
  }

  @Test
  void whenUnregisteringNonExistingPerson_thenShallDoNothing() {
    // Given
    Assumptions.assumeThat(personService.findPerson(UNKNOWN_UUID)).isEmpty();

    // When
    ThrowingCallable throwingCallable = () -> personService.unregisterPerson(UNKNOWN_UUID);

    // Then
    softly
        .assertThatCode(throwingCallable)
        .as("Unregistering non-existing person shall not throw")
        .doesNotThrowAnyException();
    softly
        .assertThat(personService.findPerson(UNKNOWN_UUID))
        .as("Non-existing person shall still not be found after un-registration attempt")
        .isEmpty();
  }
}
