### Written Answers: 12/20

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Testing (test suite quality & implementation): 3/3

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Your TA forgot to give feedback!  Shame on them.

#### More Details

IntQueue1: entries[entries.size()-1] is the back of the queue

Missing entries != null for representation invariants

The abstraction function for IntQueue2 ought to be:
AF(this) = [..., entries[(i + front) % entries.length], ...]
           for front <= i < size

The representation invariant for IntQueue2 ought to be:
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length

The answer is [A, D, G] [B, H] [C, F] [E]

-0.5 3a: No, because int primitives are immutable.
-1 3b: Yes, because String[] is mutable.
-0.5 3d: No, because String is immutable.

## Code, Design and Documentation
need @modifies for all methods that have @effects.
need to specify in the description whether duplicate nodes/edges are allowed to be added

.
