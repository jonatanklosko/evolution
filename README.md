# Evolution

A simple evolution simulator as an OOP project. See [this description](https://github.com/apohllo/obiektowe-lab/tree/0e41ebc703ef48af98d8f6e1c55f6ba300de0364/lab8) for more details.

## Build

```sh
mvn package
```

## Usage

The application is configured via a JSON file, see [parameters.json](parameters.json) for an example.
The file should either be located in the directory where the program is executed or passed as an argument.

```sh
java -jar <path-to-jar> [path-to-parameters]
```

See [releases](https://github.com/jonatanklosko/evolution/releases) for already compiled versions.
