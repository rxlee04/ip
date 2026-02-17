# AI-Assisted Coding Log (A-AiAssisted)

This file records how I used AI tools to support development for this project.

## Tools Used
- ChatGPT (OpenAI)

## How AI was used

### 1) Expanding Existing JUnit Test Cases

For several methods (e.g., deleteTask, markTask, parser-related methods), I first designed and implemented the basic test cases myself (e.g., normal valid input).

After that, I asked ChatGPT to help generate additional test cases covering:
- different valid inputs
- boundary conditions (e.g., index 0, last index)
- invalid inputs (e.g., negative index, out-of-range index)
- empty list scenarios
- missing command arguments

AI was mainly used to brainstorm edge cases and variations for the same method to improve coverage.

Example (representative snippet):

```java
@Test
public void deleteTaskTest_validIndex_taskDeleted() throws WooperException {
    tm.addToDoTask("read book");
    tm.deleteTask(0);
    assertEquals(0, tm.getTaskListSize());
}
