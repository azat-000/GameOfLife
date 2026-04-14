## Matrix of boolean vs matrix of World.Cell
### advantages of matrix of World.Cell.
#### Cell is that they are better to understand by the programmers since they describe what they mean or do. However, since matrix of boolean uses true for living and false for death, they are a little confusing.
### disadvantages of matrix of World.Cell.
#### Matrix of World.Cell is less memory efficient that matrix of booleans since booleans use 1 byte of memory, and the reference to the object in a 64-bit system is obviously much more.
## For each class, name the methods in it that override an inherited method rather than being new definitions. Explain the purpose of having constructors in the abstract class World if we cannot create World objects. Explain why we don’t need a copy constructor for Pattern.
### World.java -- boolean equals(Object o), String toString()
### Pattern.java -- boolean equals(Object o), String toString()
### ArrayWorld.java --  Cell getCell(int col, int row), void setCell(int col, int row, Cell value), void nextGenerationImpl(), boolean equals(Object o)
### PackedWorld.java -- Cell getCell(int col, int row), void setCell(int col, int row, Cell value), void nextGenerationImpl(), boolean equals(Object o)
### The purpose of having constructors in the abstract class World is polymorphism and inheritance. Although we cannot call new World(...), we use its constructors in its child classes' constructors calling super(...), and avoid overwriting.
### We do not need a copy constructor in the class Pattern because Pattern is immutable and there is no risk of privacy leaks. Moreover, without an UNNECESSARY copy constructor the program becomes more memory efficient.
## See if you can work out what’s going on there and explain in your report why we can replace the former with the latter.
###  (line = b.readLine()) != null) does exactly the same thing, just in one line and is actually more convenient. It first b.readLine(), right of the assignment operator, the whole assignments evaluates to that String, or null. Then it does the comparison and if it is not null, it goes inside the loop.
## Explain in your report why all the methods in PatternStore declare an exception.
### PatternStore(String source) -- exception comes from void loadFromURL(String url) & void loadFromDisk(String filename), doesn't catch.
### public PatternStore(Reader source) -- exception comes from void load(Reader r), doesn't catch.
### void load(Reader r) -- exception comes from the readLine() method of BufferedReader, doesn't catch.
### void loadFromURL(String url) -- exception that is a descendant of IOException comes from the URL constructor, doesn't catch. Moreover, comes from other methods as well.
### void loadFromDisk(String filename) -- exception that is a descendant of IOException comes from the FileReader constructor, doesn't catch. Exceptions from other methods may also come.
### static void main(String args[]) -- exception comes from the PatternStore constructor, doesn't catch.
##  In your report, explain which method and with which parameters (number and types) you can use for this task.
### public static void sort(Object[] a) -- Since String is an object and String implements the Comparable interface, as required by the method, this methods can be used.