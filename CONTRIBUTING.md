# Contributing

When contributing to this repository, please first discuss the change you wish to make via issues,
discussions, or any other method with the owners of this repository before making a change.

Please note we have a [code of conduct](CODE_OF_CONDUCT.md), please follow it in all your
interactions with the project.

## How To Contribute

A contribution can be as simple as opening a discussion or reporting us an issue, an idea of
enhancement or a code improvement.

No matter of your capabilities or how important is your wish to contribute to this project, your
help will be welcome and very appreciated!

### :mega: Discussions / :question: Support

Discussions are where we have conversations.

If you'd like to help troubleshooting a PR you're working on, have a great new idea, or want to
share something amazing you've experimented with our product, join us in
[discussions](https://github.com/Cosmageia/mc-spring-plugin-template/discussions).

The same is possible if you have any question or struggle making something work.
The community will try to find the best way to help you! :Sparkles:

### :lady_beetle: Issues

[Issues](https://docs.github.com/en/github/managing-your-work-on-github/about-issues) are used to
track tasks that contributors can help with. If an issue doesn't have any label, this means
we haven't reviewed it yet, and you shouldn't begin work on it.

If you've found a bug, a weird behavior or an exploit, search open issues to see if someone else has
reported the same thing. If it's something new, open an issue using
a [template](https://github.com/Cosmageia/mc-spring-plugin-template/issues/new/choose). We'll use
the issue to have a conversation about the problem you want to fix.

### :hammer_and_wrench: Pull requests

A [pull request](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests)
is a way to suggest changes in our repository. When we merge those changes, they would be deployed
in the next release by the maintainer. :earth_africa:

## First Contribution

If this is your first contribution, we recommend you to get familiar with the process through
this [GitHub repository](https://github.com/firstcontributions/first-contributions).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

### Prerequisites

Working on this project requires the following dependencies installed in your local environment:

* JDK 17 ([Download Link](https://adoptium.net/en-GB/temurin/releases/?version=11))
* Maven
  3.8+ ([Download Link](https://maven.apache.org/download.cgi) | [Install Guide](https://maven.apache.org/install.html))

### Installing

We suppose you know how to install a Minecraft server.

To build the project and run unit and integration tests, you can simply build the project by typing
this command at the root project:

    $ mvn clean install

You will find the packaged JAR file of the PaperMC plugin at `template-plugin/target`.
If you want to test it, copy it into your local PaperMC server by taking care that the server
version is compatible with the plugin.

### Documentation & Help

If you want to work on PaperMC related aspects, you will find the API JavaDoc
[here](https://jd.papermc.io/paper/1.20/).

A [wiki](https://docs.papermc.io/paper/dev) exists as well to get familiar with PaperMC core
concepts.

Anyway, if you have any question, don't hesitate to
[ask us](https://github.com/Cosmageia/mc-spring-plugin-template/discussions)!
We are here to help you for getting started.

## Pull Requests

1. Try to open several smaller PRs instead of a big one, it will make the job of the reviewers
   easier.
2. Give a summary of the changes provided by your PR. Link any related issue, discussion or
   documentation that could help the reviewer understand your work, the impacts and the plus-value.
3. You will need at least one approval of a reviewer before being able to merge the PR.
4. All automatic jobs must pass (GitHub actions, SonarQube analysis, formatting verification, ...)
   before merging.

## Code Formatting

The only thing we ask when contributing to the code is to apply
[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

To help respect this, an automatic formatter has been configured through Maven to be dispatched
automatically when building the project and running tests.

### IntelliJ IDEA plugin

If you are using IntelliJ IDEA, you can install the
[google-java-format plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format) which will
replace the default IDE code formatting behavior.
