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

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.UUID;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class PersonEntityTest {

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
    PersonEntity person = new PersonEntity(id, firstName, lastName, age, address);

    // Then
    softly.assertThat(person.getId()).isEqualTo(id);
    softly.assertThat(person.getFirstName()).isEqualTo(firstName);
    softly.assertThat(person.getLastName()).isEqualTo(lastName);
    softly.assertThat(person.getAge()).isEqualTo(age);
    softly.assertThat(person.getAddress()).isEqualTo(address);
  }

  @Test
  void whenInstantiatingWithId() {
    // Given
    UUID id = UUID.randomUUID();
    String firstName = "Bruce";
    String lastName = "Layne";
    int age = 44;
    String address = "12 Knight St., London BC V5N 3M6, England";

    // When
    PersonEntity person = new PersonEntity(id, firstName, lastName, age, address);

    // Then
    assertThat(person.getId()).isNotNull();
  }

  @Test
  void equalsContract() {
    EqualsVerifier.forClass(PersonEntity.class)
        .usingGetClass()
        .suppress(Warning.SURROGATE_KEY)
        .verify();
  }

  @Test
  void toStringContract() {
    ToStringVerifier.forClass(PersonEntity.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }
}
