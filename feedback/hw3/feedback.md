### Written Answers: 4/6
- For 2, testBaseCase failed b/c it threw an IllegalArgumentException for n = 0
- For 3, it failed b/c it returned 1 for n=2, and another fix you had to make was to change the (n <= 2) to (n > 2)
### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback
- Great work, especially on how you implemented the add/remove in BallContainer! Make sure to clean up your code a bit and address my feedback!
### Specific Feedback
- When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.
- Missing documentation for the new fields in `BallContainer.java` and/or `Box.java`.
Make sure to document new additions in the future!
- Make sure to remove useless comments, like TODOs and commented out code, to
make your code as clear as possible.
- A better way to write ball comparison is to use `Double.compare`.  Check
the Java documentation of these methods to see how you might use them.
