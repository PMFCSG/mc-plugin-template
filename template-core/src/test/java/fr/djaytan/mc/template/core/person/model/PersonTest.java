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

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class PersonTest {

  @InjectSoftAssertions private SoftAssertions softly;

  @Test
  void whenInstantiatingWithNominalValues() {
    // Given
    UUID id = UUID.randomUUID();
    String firstName = "Bruce";
    String lastName = "Layne";
    int age = 44;
    String address = "12 Knight St., London BC V5N 3M6, England";

    // When
    Person person = new Person(id, firstName, lastName, age, address);

    // Then
    softly.assertThat(person.id()).isEqualTo(id);
    softly.assertThat(person.firstName()).isEqualTo(firstName);
    softly.assertThat(person.lastName()).isEqualTo(lastName);
    softly.assertThat(person.age()).isEqualTo(age);
    softly.assertThat(person.address()).isEqualTo(address);
  }

  @Test
  void whenInstantiatingWithNullId() {
    // Given
    String firstName = "Bruce";
    String lastName = "Layne";
    int age = 44;
    String address = "12 Knight St., London BC V5N 3M6, England";

    // When
    Person person = new Person(null, firstName, lastName, age, address);

    // Then
    assertThat(person.id()).isNull();
  }

  @Test
  void whoamiWithNominalValues() {
    Person person =
        new Person(
            UUID.fromString("998d8dda-4a8b-11ee-be56-0242ac120002"),
            "Bruce",
            "Layne",
            44,
            "12 Knight St., London BC V5N 3M6, England");

    assertThat(person.whoami())
        .isEqualTo(
            "I am Bruce Layne (ID: 998d8dda-4a8b-11ee-be56-0242ac120002), 44 years old "
                + "and living at 12 Knight St., London BC V5N 3M6, England");
  }

  @Test
  void whoamiShortlyWithNominalValues() {
    Person person =
        new Person(
            UUID.fromString("fe7390ec-4a8f-11ee-be56-0242ac120002"),
            "Bruce",
            "Layne",
            44,
            "12 Knight St., London BC V5N 3M6, England");

    assertThat(person.whoamiShortly())
        .isEqualTo("Bruce Layne (ID: fe7390ec-4a8f-11ee-be56-0242ac120002)");
  }
}
