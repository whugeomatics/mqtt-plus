# sample-multi-broker

Spring Boot sample showing two brokers, wildcard broker listener registration, and explicit publish routing.

Notes:

- `broker="*"` means the listener is registered for every configured broker
- Multiple listeners can match the same topic; they are not mutually exclusive
- Payload type still matters during invocation
- `String` listeners can consume plain text payloads
- Typed listeners require payload bytes that can be converted to the declared type
- For example, a `DroneStatus` listener needs JSON if Jackson is used for conversion

By default, the app only starts the context. To publish demo messages on startup, run with:

```properties
samples.publish-on-startup=true
```