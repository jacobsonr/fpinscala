# Functional Programming in Scala

Skeleton code and tests for the exercises in the book "Functional Programming in Scala".

---
# How to Use

### Clone the repository
### Install Scala / SBT support
Add scala support to IntelliJ, or install SBT, or install metals
### Open an SBT shell
In IntelliJ, click "sbt shell" at the bottom of your screen.

Or from a command prompt in your project's directory, execute "sbt" (if you've installed sbt on your system).
### Switch to the desired chapter project
SBT command: project chapter1
### In SBT, enter "~test"
The test command runs all tests.  The ~ makes it execute "test" every time a source file is changed.

You should see near the end of the test output something like:

[info] ScalaCheck
[info] Failed: Total 9, Failed 0, Errors 9, Passed 0

And the individual ScalaCheck tests will have errors like this:

Exception: scala.NotImplementedError: an implementation is missing

### Edit the chapter*/src/main/scala/chapter* file
Replace the ???'s with your solutions until all tests pass.

Make sure you're using an IDE that will highlight type mismatches and other errors!
