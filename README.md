# Lua-Discord4J
Lua wrapper for the [Discord4J](https://github.com/austinv11/Discord4J) Discord Java API

## Building
I will probably upload some binaries to github but it's better that you build them on your own, because they will be more up-to-date.
I will only commit changes which at least compile, so if you want to build the latest version, it should work.
To do that, you need to add 2 libraries to your project in your favorite IDE:

1. [Discord4J](https://github.com/austinv11/Discord4J) - Needed to get access to the Discord API in Java (make sure you get the shaded version)
  * Version used in my last build: 2.4.0
2. [LuaJ](http://www.luaj.org/luaj/3.0/README.html) - Needed to implement the lua wrapper
  * Version used in my last build: 3.0.1

## How to use
At this point, you have to start Lua-D4J with 3 arguments:

1. Email address of Discord user
2. Password of Discord user
3. Path of main lua file

Later, I will switch to or at least add an **botuser token authentication** instead of **email & password**, because this wrapper will be used mostly for bots.

### Events
Because Discord4J is very event driven and because it's nicer for bots, this wrapper also mainly works with events.
I will try to implement every event Discord4J has but now, only these events are called:

* `onDisconnected(string: reason)`

   Called if Discord4J lost connection to the API.

#### Messages

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
Here are the functions you can use inside Lua to interact with the Discord API:

* `sendMessage(string: channelid, string: message)`

   Sends a message to the given channel.   
   Returns: nil on success **or** error message

* `updateMessage(string: channelid, string: messageid, string: newtext)`

   Changes the text of the given message.   
   Returns: nil on success **or** error message

* `deleteMessage(string: channelid, string: messageid)`

   Deletes the given message.   
   Returns: nil on success **or** error message