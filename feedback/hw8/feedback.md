## Program operation (correctness) and extra credit

### Correctness Score: 3/3

### Extra Credit Score: -/3

#### Program operation/extra credit feedback

Great work this week! Make sure to take a look at my couple comments below for next week! Keep it
up!

## Code quality and implementation

### Design: 3/3

### Code quality: 3/3

### Mechanics: 3/3

#### Code Review Feedback

- Make sure you fix compiler warnings in your code prior to submitting.

- It would have been better to take advantage of TypeScript's `interface` to make a new type
for your parsed edges. It would look something like this:
```
interface Edge {
  p1: [number, number],
  p2: [number, number],
  color: string,
}
```
