
INTRODUCTION:

This inventory software is designed to help track goods and manage your 
business's budget. There is a sample inventory.txt file provided to get
you started right away, but you can create your own inventory as well!

After learning C++, I delved into Java. This program is a culmination of
the more complex concepts I learned in C++ including object oriented
programming with nested class hierarchies, dynamic binding and abstract
classes. This program uses a doubly linked circular list to store data
read in from the external file and entered by the user.

RUNNING:

All files should be in the same directory.
From the command line, simply type "javac *.java" to compile and then
"java Main" to run.

If you would like to create your own inventory, simply delete 
'inventory.txt' or remove it from the directory and run the program which
will prompt you through the creation of a new business inventory.


REFLECTION:

For this project I pushed my understanding of Java a step further,
implementing a more complex data structure: a doubly-linked circular list.
My previous project read in data from an external file, however it was a
requirement of this iteration of the project. During the execution of this
assignment, I realized a fatal flaw in my last assignment. The ‘write’
function writes out data in the order it’s sorted into. Since the data
structure for the last assignment was a BST, what could have been a
somewhat balanced BST the first time the program ran became a linear
linked list with twice as many pointers as was necessary the next time the
file was read. With a circular linked list, this becomes less important,
however it does create different implications for our insert functions.
If the incoming data is being inserted in sorted order (perhaps as it is
being read in from a file), then it is most efficient to always insert at
the end, as the item being inserted is always bigger than the item at the
end of the list. However if the data is being inserted randomly (perhaps
as a single new item is entered by the user) then a more complex insert
function can be used to more efficiently recurse through the data
structure.

There are a few subtle and not-so-subtle differences between Java and C++,
but we may still implement class hierarchies with dynamic binding and
abstract classes. Implementing dynamic binding for this assignment was
particularly tricky. I had gotten used to the slick ability to derive a
node from another class (rather than containing data, the node is the
data), making the process of creating nodes, building data structures, and
accessing the data “within” the node simple and easy. However dynamic
binding complicates this greatly. A node can no longer be an item if that
item could be of a class derived from ‘item,’ so I had to fall back into
treating the node and the data in the node as two separate entities. Rather
than being able to simply call “root.display();” I had to code in an extra
layer of functions within the ‘node’ class to support dynamic binding.

Perhaps the most disappointing aspect of my program is that I made this
concession in code efficiency for a poor reason: I chose to created classes
‘bulk’ and ‘single’ derived from ‘item’ which became an abstract class --
this choice sufficed to give me practice in dynamic binding in Java, but
I felt that in real life, the distinction between bulk and single items
would not be sufficiently significant enough to justify the sacrifice of
clean coding to support dynamic binding.

Like C++, Java supports class hierarchy, dynamic binding, and abstract
classes and functions. This means that we can use the same power of dynamic
binding to manage multiple derived classes using much the same protocol as
we would use for their base class. Rather than using a derivation and
initialization list, in Java we have the “super” keyword. By calling
‘super();’ or ‘super(x);’ as the first statement within a derived class
constructor, we can invoke the base class’s constructor (either with or
without arguments). Using the keyword ‘abstract’ in the base class allows
us to use overriding. For example, there are two derived classes from
‘item’. Each of the derived classes has a ‘display,’ ‘read’ and ‘write’
function. All derived class objects are referenced with ‘item’ references
throughout the program, and as there are corresponding ‘abstract’
functions in the ‘item’ class, we are able to override the abstract
function to access the derived class functions.

Overall I feel like this was an effective first lesson in Java,
encompassing class hierarchies, dynamic binding, recursion and function
overriding. In future iterations of this program, now that the technical
aspect of it is fairly nailed down, I think that what would make it better
are improvements to what it does rather than how it does them. For
instance, accounting software probably should do much more than simply
track inventory. Essentially, I would like to make the scope of its
purpose more broad.

That's it!
Hope you enjoy my silly program.
-Amanda
