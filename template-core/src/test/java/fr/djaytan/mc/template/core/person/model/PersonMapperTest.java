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

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class PersonMapperTest {

  @InjectSoftAssertions private SoftAssertions softly;

  @Test
  void whenConvertingToEntity() {
    // Given
    Person person =
        new Person(
            UUID.randomUUID(), "Bruce", "Layne", 44, "12 Knight St., London BC V5N 3M6, England");

    // When
    PersonEntity personEntity = PersonMapper.toEntity(person);

    // Then
    softly.assertThat(personEntity.getId()).isEqualTo(person.id());
    softly.assertThat(personEntity.getFirstName()).isEqualTo(person.firstName());
    softly.assertThat(personEntity.getLastName()).isEqualTo(person.lastName());
    softly.assertThat(personEntity.getAge()).isEqualTo(person.age());
    softly.assertThat(personEntity.getAddress()).isEqualTo(person.address());
  }

  @Test
  void whenConvertingFromEntity() {
    // Given
    PersonEntity personEntity =
        new PersonEntity(
            UUID.randomUUID(), "Bruce", "Layne", 44, "12 Knight St., London BC V5N 3M6, England");

    // When
    Person person = PersonMapper.fromEntity(personEntity);

    // Then
    softly.assertThat(person.id()).isEqualTo(personEntity.getId());
    softly.assertThat(person.firstName()).isEqualTo(personEntity.getFirstName());
    softly.assertThat(person.lastName()).isEqualTo(personEntity.getLastName());
    softly.assertThat(person.age()).isEqualTo(personEntity.getAge());
    softly.assertThat(person.address()).isEqualTo(personEntity.getAddress());
  }
}
