botReady = false;

function onReady()
	botReady = true;
end

function onDisconnected(reason)
	if(botReady) then
		
	end
end

function onMessageReceived(msg)
	if(botReady) then
		sendMessage(msg.channel.id, "It works!")
	end
end

function onMessageDeleted(msg)
	if(botReady) then
		sendMessage(msg.channel.id, msg.author.name .." deleted message: ".. msg.text)
	end
end

function onMention(msg)
	if(botReady) then
		sendMessage(msg.channel.id, msg.author.name .." mentioned me")
	end
end
