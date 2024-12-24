IntelliJ Plugin for gvc0
========================

This is the IntelliJ plugin for the gradual verifier for the C0 subset of C.

Usage
-----

Tools > Symbolic Execution

Installation
------------

1. Install the following

- Eclipse Adoptium JDK 17

- Scala >= 2.12.18 (Coursier is recommended to install Scala)

- IntelliJ IDEA Ultimate or Community Edition 2022.3.3 with the Scala plugin

2. Set up `gvc0`

Clone the following repositories into the same parent directory.

```
git clone https://github.com/gradual-verification/silver-gv.git
git clone https://github.com/gradual-verification/silicon-gv.git
git clone -b position https://github.com/gradual-verification/gvc0.git
git clone https://github.com/advancingdragon/c0.git
```

3. Install [Z3](https://github.com/Z3Prover/z3/releases) and
[cc0](https://bitbucket.org/c0-lang/docs/wiki/Downloads). For macOS runnning
on Apple Silicon, install `cc0` from
[https://nguyen.bz/compiler-c0.zip](https://nguyen.bz/compiler-c0.zip).

Directories where Z3 and `cc0` binaries are located should be in `PATH`. Set
environment variable `Z3_PATH` to directory where Z3 binary is located.
`gmp` and `gnu-getopt` are also needed for the `cc0` compiler to work.

4. Build `gvc0`

```
cd ./silicon-gv
ln -s ../silver-gv silver
cd ../gvc0
ln -s ../silicon-gv silicon
sbt assembly
```

5. Set environment variable `GVC0_PATH` to `gvc0` directory.

6. Open project `c0` in IntelliJ IDEA.
