Java Agentを使用して `class NewRelic` の処理をlog4jに差し替えます。  

```sh
java -javaagent:newrelic_log4j_agent-1.0.jar -jar test-1.0.jar
```