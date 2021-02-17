### Written Answers: 8/10
- Looking up edges in a collection of edges would be O(1) average time
- Removing edges in an adjacency list would be average O(1)

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF): 3/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback

- Great work! Be sure to address all the feedback!

#### More Details

- Your class comment should be in javadoc format and should mention that the graph is mutable
- Have comments explaining your fields
- Missing documentation for DLEdge
- DLEdge should have equals and hashCode implemented for it
- Another part of  your RI should be that all child nodes are in the Graph
- If you don't allow duplicate edges dlgraph field should be a Map<String, Set<DLEdge>>
- Since there aren't duplicates listNodes/listChildren/getLabels should all return Sets, not Lists
- listNodes spec shouldn't mention anything about order
- O(1) checks should be outside of if (DEBUG) statement
