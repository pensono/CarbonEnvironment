Compiler Operation
======

Compilation happens in several steps:

* Tokenization - Lexical
  - Results in list of strings (tokens)

* Parsing - Grammatical relationships established
  - Results in prototype expressions

* Linking - Semantic relationships established
  - Results in expressions arranged in an AST

* Reduction Evaluation / Optimization


Definitions
=======
An identifier is several labels in a row, separated by dots:
`This.IsAn.Identifier`

A member refers to a direct descendant. In the example above, `IsAn` is a member of `This`.
