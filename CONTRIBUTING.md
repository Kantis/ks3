## Adding features
Please raise an issue to discuss and assess support for new features before starting work on them.

## Code style

This project uses Ktlint to ensure that code is consistently formatted and doesn't change based on automated formatting settings of
individual contributors.

Please follow [the official recommended Ktlint setup](https://pinterest.github.io/ktlint/latest/install/setup/]) to setup your environment.

## API Changes

This project uses Kotlin Binary Compatibity Validator to ensure that changes to the public API are done in a backwards compatible way.

Breaking changes are reserved for major version updates, and must be preceded by deprecations.
