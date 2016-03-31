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
