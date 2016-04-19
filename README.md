# Lua-Discord4J
Lua wrapper for the [Discord4J](https://github.com/austinv11/Discord4J) Discord Java API

## Building
I will probably upload some binaries to github but it's better that you build them on your own, because they will be more up-to-date.
I will only commit changes which at least compile, so if you want to build the latest version, it should work.
To do that, you need to add 2 libraries to your project in your favorite IDE:

1. [Discord4J](https://github.com/austinv11/Discord4J) - Needed to get access to the Discord API in Java (make sure you get the shaded version)
  * Version used in my last build: 2.4.4
2. [LuaJ](http://www.luaj.org/luaj/3.0/README.html) - Needed to implement the lua wrapper
  * Version used in my last build: 3.0.1

## How to use
To start with user account:
`java -jar Lua-Discord4J.jar -user <email> <password> <luamainfile> [port]`

To start with bot account:
`java -jar Lua-Discord4J.jar -bot <bottoken> <luamainfile> [port]`

The `<luamainfile>` parameter has to be the path (absolute or relative) to the lua main file which gets executed on start.
If a `[port]` parameter is specified, Lua-Discord4J will start a port listener on the given port to retrieve data over an external port.
If data arrives, it will trigger an onPortData event to pass the data to lua.

### Events
Because Discord4J is very event driven and because it's nicer for bots, this wrapper also mainly works with events.
I will try to implement every event Discord4J has but now, only these events are called:

* `onDisconnected(string: reason)`

   Called if Discord4J lost connection to the API.

#### Messages
**Note**: The message table should contain every function, the java [IMessage](https://jitpack.io/com/github/austinv11/Discord4j/2.4.4/javadoc/sx/blah/discord/handle/obj/IMessage.html) has. So you should be able to use the java functions almost one-to-one.


* `onMessageReceived(table: message)`

   Called if a new message was received.

* `onMessageDeleted(table: message)`

   Called if a message gets deleted

* `onMessageUpdated(table: message)`

   Called if a message gets updated

* `onMessageAcknowledged(table: message)`

   Called if a message was acknowledged (read)

* `onMention(table: message)`

   Called if you have been mentioned via @username

### Functions
I'm currently restructuring the whole wrapper to directly use the java objects instead of defining every function one by one.
You can access the Discord client object via `discordClient` inside lua, which is basically a table full of functions based on the [java object](https://jitpack.io/com/github/austinv11/Discord4j/2.4.4/javadoc/sx/blah/discord/api/IDiscordClient.html). You should be able to use these functions in lua almost one-to-one. Functions which return another object for example a Channel object, return a table containing all the functions this object has and so on.