# Lua-Discord4J
Lua wrapper for the [Discord4J](https://github.com/austinv11/Discord4J) Discord Java API

## Why Lua?
Lua is a quite easy-to-learn scripting language and there are advantages over Java. The main reason why I wanted to use Lua instead of Java was, that I wanted to update the behavior of my bot on-the-fly without recompiling it over and over again. If I want to update it, I just reload the main script via `loadfile(<luamainfile>)` or restart the bot if it failed to load the lua main file before, because of errors.

## Building with Maven
This program uses [Logback](http://logback.qos.ch/). There is a default configuration included (`src/logback.xml`) which behaves like this:
- DEBUG: prints into `log/debug.log`
- INFO, WARN, ERROR: prints into terminal

However, if you want to use another logging framework you can remove the logback.xml, remove the following parts from the pom.xml and set up your own logging framework:
```XML
<dependency>
	<groupId>ch.qos.logback</groupId>
	<artifactId>logback-classic</artifactId>
	<version>1.1.7</version>
</dependency>
...
<resources>
	<resource>
        <directory>src/</directory>
        <includes>
            <include>logback.xml</include>
        </includes>
        <targetPath>.</targetPath>
    </resource>
</resources>
```
## How to use
To start with user account:
`java -jar lua-discord4j.jar -user <email> <password> <luamainfile> [port]`

To start with bot account:
`java -jar lua-discord4j.jar -bot <bottoken> <luamainfile> [port]`

The `<luamainfile>` parameter has to be the path (absolute or relative) to the lua main file which gets executed on start.
If a `[port]` parameter is specified, Lua-Discord4J will start a port listener on the given port to retrieve data over an external port.
If data arrives, it will trigger an `onPortData` event to pass the data to lua. You can use this for example to catch webhooks.

### Events
Lua-Discord4J implements all [Discord4J events](https://jitpack.io/com/github/austinv11/Discord4j/2.4.7/javadoc/sx/blah/discord/handle/impl/events/package-summary.html) and tries to call them in Lua if they get triggered in Java. You can then define functions inside Lua which have to be named like this to be called on event: `"on"+<EventClassName>`. For example like this:
```LUA
-- Called if Discord4J lost connection to discord
function onDiscordDisconnectedEvent(reason)
    print("Discord4J Disconnected: "..reason) -- Prints reason of disconnect into terminal
end
```
Most of the events also pass data to the event handler. If it's just a string, it gets passed directly to the lua function, like in the example above. If it's another object, a Lua equivalent of the object gets passed to the event handler (see [Objects](https://github.com/davue/Lua-Discord4J#objects)).

**Note:** at the time the lua main file gets executed, Discord4J was already initialized. So there is no need to wait for `onReadyEvent` or `onGuildCreateEvent`
### Objects
Almost every "Discord object" used in Discord4J has it's own wrapper inside Lua-Discord4J. So you should be able to use the java functions almost* one-to-one. There is one global object named `discord` inside the Lua Execution Environment representing the [DiscordClient object](https://jitpack.io/com/github/austinv11/Discord4j/2.4.7/javadoc/sx/blah/discord/api/IDiscordClient.html). Functions returning other objects, technically return a table containing all the functions this object has. But you can use it as usual. Example usage:
```Lua
-- Sends the message "Test" to the channel with ID "1234"
message = discord.getChannelByID("1234").sendMessage("Test") -- Returns a message object
-- Print the name of the author, which is the name of the bot, into terminal
print(message.getAuthor().getName())
```
\* There are changes to some functions, for example `IDiscordClient.changeAvatar(Image avatar)` which takes an Image as argument, where the Lua equivalent `discord.changeAvatar(filepath)` takes the **filepath** to the image instead of the Image object. You can remember that the Discord API functions only take Lua primitives as arguments and if they don't match to the Java equivalent, it takes a lua primitve argument somehow describing the Java argument. 
### Other Functions
Currently there is only one non-discord related function:
- `setTimer(number: delay, function: callback)`  
Delays the execution of the function `callback` by `delay` ms.

Example:
```LUA
-- Prints "Test" after 5 seconds
setTimer(5000, function()
    print("Test")
end)
```
### Examples
Responds "Pong!" to the message "!ping":
```LUA
-- Define onMessageReceivedEvent event handler
function onMessageReceivedEvent(msg)
	if(msg.getContent() == "!ping") then
	    msg.getChannel().sendMessage("Pong!")
	end
end
```
You can also take a look at [my personal bot](https://github.com/davue/luad4j-sbot), to see an example of how to use this wrapper.
