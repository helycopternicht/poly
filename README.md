How to run:

```bash
./gradlew fatJar
java -jar build/libs/poly-test-task-all-1.0-SNAPSHOT.jar "name=Bob" '[{"name":"Bobby","age":25},{"name":"Rob","age":35}]'
```