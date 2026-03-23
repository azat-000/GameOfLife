Access modifiers choice explanation.


Simply saying all the methods that are mainly used for the algorithm
of the game engine and may cause bugs or other unnecessary computation
if used manually are private, because their purpose is that they are help
methods.

What about public methods, they are the ones that are safe to use and are
for people to use freely and do experiments in the game without only
calling the play() method and playing the game. They are also used in other
methods of the class.


Mutable and immutable classes.

Pattern is immutable because its instance vars are private and there are
no mutator methods to change its instance vars from outside.

ArrayLife is mutable because it contains mutator methods, such as setCell,
which mutates its array instance var's specific cell to the given value.
