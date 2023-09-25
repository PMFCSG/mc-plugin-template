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
package fr.djaytan.mc.template.core.person.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import fr.djaytan.mc.template.core.commons.Utils;
import fr.djaytan.mc.template.core.person.model.Person;
import fr.djaytan.mc.template.core.person.model.PersonService;
import fr.djaytan.mc.template.core.person.view.PersonView;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

@Controller
public final class PersonController {

  private final PersonService personService;
  private final PersonView personView;
  private final Random random;

  @Autowired
  PersonController(
      @NonNull PersonService personService,
      @NonNull PersonView personView,
      @NonNull Random random) {
    this.personService = personService;
    this.personView = personView;
    this.random = random;
  }

  public void generatePerson(@NonNull Audience audience) {
    String firstName = randomAlphabetic(3, 12);
    String lastName = randomAlphabetic(3, 12);
    int age = random.nextInt(5, 120);
    String address = randomAlphabetic(8, 30);
    Person person = personService.registerOrUpdate(new Person(firstName, lastName, age, address));
    audience.sendMessage(
        Component.text(
            String.format(
                "You have been registered into the database with the following identity: %s",
                person.whoami())));
  }

  public void whoishe(
      @NonNull String strPersonUuid, boolean shortPresentation, @NonNull Audience audience) {
    Optional<UUID> personUuid = Utils.parseUuid(strPersonUuid);

    if (personUuid.isEmpty()) {
      personView.sendInvalidUuidErrorMessage(audience, strPersonUuid);
      return;
    }

    Optional<Person> person = personService.findPerson(personUuid.get());

    if (person.isEmpty()) {
      personView.sendNoPersonFoundErrorMessage(audience, personUuid.get().toString());
      return;
    }

    String whoishe = shortPresentation ? person.get().whoamiShortly() : person.get().whoami();

    personView.sendWhoisheMessage(audience, whoishe);
  }
}
