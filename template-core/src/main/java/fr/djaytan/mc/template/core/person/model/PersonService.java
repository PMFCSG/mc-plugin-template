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

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class PersonService {

  private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

  private final PersonRepository personRepository;

  @Autowired
  PersonService(@NonNull PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public @NonNull Optional<Person> findPerson(@NonNull UUID id) {
    return personRepository.findById(id).map(PersonMapper::fromEntity);
  }

  public @NonNull Person registerOrUpdate(@NonNull Person person) {
    PersonEntity personEntity = PersonMapper.toEntity(person);
    UUID personId = person.id();

    if (personId != null && personRepository.existsById(personId)) {
      return update(personEntity);
    } else {
      return register(personEntity);
    }
  }

  private @NonNull Person register(@NonNull PersonEntity personEntity) {
    LOG.debug("The person is not registered yet, registering him: {}", personEntity);
    PersonEntity registeredPersonEntity = personRepository.save(personEntity);
    LOG.debug("Registered person: {}", registeredPersonEntity);

    Person person = PersonMapper.fromEntity(registeredPersonEntity);
    LOG.atInfo().setMessage("Registered person: {}").addArgument(person::whoamiShortly).log();
    return person;
  }

  private @NonNull Person update(@NonNull PersonEntity personEntity) {
    LOG.debug(
        "The person is already registered, updating his information with the following ones: {}",
        personEntity);
    PersonEntity updatedPersonEntity = personRepository.save(personEntity);
    LOG.debug("Updated person: {}", updatedPersonEntity);

    Person person = PersonMapper.fromEntity(updatedPersonEntity);
    LOG.atInfo().setMessage("Updated person: {}").addArgument(person::whoamiShortly).log();
    return person;
  }

  public void unregisterPerson(@NonNull UUID id) {
    Optional<Person> personToRemove = findPerson(id);
    if (personToRemove.isEmpty()) {
      LOG.info("Trying to delete a non-registered person with ID '{}'", id);
      return;
    }
    personRepository.deleteById(id);
    LOG.atInfo()
        .setMessage("Deleted person: {}")
        .addArgument(() -> personToRemove.get().whoamiShortly())
        .log();
  }
}
