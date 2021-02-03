### Written Answers: 12/26

- The loop in polynomial division needs to terminate when p becomes 0.  If you
don't have this condition in the loop, then an infinite loop can occur when the
degree of the divisor is 0, since the degree of p = 0 is also 0.
- In part 1a, the changes required are limited to the following methods:
`checkRep`, `toString`, `equals`, and `hashCode`.
- In part 2b, the changes required are limited to the following methods:
`checkRep`, `equals`, `toString`, `getExpt`, and `hashCode`.
- In part 2c, the changes required are limited to the following methods: the
constructor, and `checkRep`.
- In part 1b, the method specification lacks `@modifies this`, so it would violate the
specification to modify the abstract state of `this`.
- Immutability is a property of the specification, and `checkRep` does not assume
the specification was correctly implemented.  So, in general, regardless of
whether or not they are immutable, ADTs need calls to `checkRep` at the
beginning and end of all public methods.
- `checkRep` does not assume that methods were implemented properly, regardless of
how trivial or innocuous they seem.  So, in general, even observer methods need
calls to `checkRep` at their beginning and end.
- Final immutable fields cannot be modified after they are instantiated; the
compiler would complain about any attempt to do so.  Therefore, we can reason
that `RatNum` and `RatTerm` cannot contain any bugs with regard to the
representation invariant as long as we ensure the coherency of the data at
initialization.  Therefore, `RatNum` and `RatTerm` are special cases that do not
need calls to `checkRep` at the beginning and end of every public method, aside
from the constructor.

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback

### Specific Feedback

- RatTerm does not need checkRep() in the public methods other than the constructor.
- Needs loop invariants on non-trivial loops.