/*
 * Lua-Discord4J - Lua wrapper for Discord4J Discord API
 * Copyright (C) 2016
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.luad4j.events;

import sx.blah.discord.api.Event;

public class PortDataEvent extends Event
{
	/*
	 * The received data.
	 */
	private final String mData;
	
	public PortDataEvent(String data)
	{
		mData = data;
	}
	
	/*
	 * Returns the data received.
	 * 
	 * @return The data.
	 */
	public String getData()
	{
		return mData;
	}
}
