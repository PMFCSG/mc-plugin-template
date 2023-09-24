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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
final class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Nullable // Required for UUID generation post-instantiation
  private UUID id;

  @Column(nullable = false)
  @NonNull
  private String firstName = "";

  @Column(nullable = false)
  @NonNull
  private String lastName = "";

  @Column(nullable = false)
  private int age;

  @Column(nullable = false)
  @NonNull
  private String address = "";

  protected PersonEntity() {}

  PersonEntity(
      @NonNull String firstName, @NonNull String lastName, int age, @NonNull String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.address = address;
  }

  PersonEntity(
      @Nullable UUID id,
      @NonNull String firstName,
      @NonNull String lastName,
      int age,
      @NonNull String address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.address = address;
  }

  @Nullable
  UUID getId() {
    return id;
  }

  @NonNull
  String getFirstName() {
    return firstName;
  }

  @NonNull
  String getLastName() {
    return lastName;
  }

  int getAge() {
    return age;
  }

  @NonNull
  String getAddress() {
    return address;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonEntity that = (PersonEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public @NonNull String toString() {
    return new StringJoiner(", ", PersonEntity.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("age=" + age)
        .add("address='" + address + "'")
        .toString();
  }
}
