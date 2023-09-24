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
package fr.djaytan.mc.template.core;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Random;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {

  private final PersonService personService;
  private final Random random;

  @Autowired
  PersonController(@NonNull PersonService personService, @NonNull Random random) {
    this.personService = personService;
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
}
