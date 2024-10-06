IntelliJ Plugin for gvc0
========================

This is the IntelliJ plugin for the gradual verifier for the C0 subset of C.

Usage
-----

Tools > Symbolic Execution

Installation
------------

1. Tested with the following versions, other versions may work.

- Eclipse Adoptium Java 17

- Scala >= 2.12.18 (Coursier is recommended to install Scala)

- IntelliJ IDEA Community Edition 2022.3.3

2. Set up `gvc0`

Clone the following repositories into the same parent directory.

```
git clone https://github.com/gradual-verification/silver-gv.git
git clone https://github.com/gradual-verification/silicon-gv.git
git clone -b position https://github.com/gradual-verification/gvc0.git
git clone https://github.com/advancingdragon/c0.git
```

Then

```
cd ./silicon-gv
ln -s ../silver-gv silver
cd ../gvc0
ln -s ../silicon-gv silicon
sbt assembly
```

3. Install [Z3](https://github.com/Z3Prover/z3/releases) and
[cc0](https://bitbucket.org/c0-lang/docs/wiki/Downloads).

Z3 and `cc0` binaries should be in `PATH`.

Set environment variable `Z3_PATH` to location of Z3 binary, and
environment variable `GVC0_PATH` to location of `gvc0` directory.
