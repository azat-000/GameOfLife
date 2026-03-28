### ArrayLife vs PackedLife
In every situation where width * height of
the board is smaller than or equal to 64 I
would use the PackedLife class. This is because
it is more memory efficient and faster. It
uses 1 bit for each cell, although ArrayLife
uses 1 byte(more for the other arrays as well).
It is faster because it uses bitwise operators 
which interact with CPU directly. However, it has 
a huge limitation that I can only have no more 
than 64 cell. Thanks to ArrayLife I can have a 
huge board of any size (not actually ANY size)

### Why the overloaded method cannot have the heading public void initialise(long world) but rather has the heading public long initialise()?
This is because world is of primitive type long,
and it cannot be mutated in a void method like 
that. So it should be returned and reassigned to
the variable world.

### Which of the classes are mutable and which are not?
PackedLife and ArrayLife classes are mutable
since both have mutator methods. E.g. both have setCell(...). 
The class GameOfLife is immutable since it doesn't have mutators
because it is only used for starting the game. Finally, pattern 
is immutable because it doesn't have mutators.
