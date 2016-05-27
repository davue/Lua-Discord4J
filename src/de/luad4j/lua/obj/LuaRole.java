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

package de.luad4j.lua.obj;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import de.luad4j.lua.LuaHelper;

import java.awt.Color;
import java.util.EnumSet;

import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

public class LuaRole
{
	private final IRole 	mRole;		// Role object inside Java
	private final LuaValue 	mLuaRole;	// Lua implementation of Role
	
	public LuaRole(IRole role)
	{
		mRole = role;
		
		// Init Lua
		mLuaRole = LuaValue.tableOf();
		mLuaRole.set("changeColor", new ChangeColor());
		mLuaRole.set("changeHoist", new ChangeHoist());
		mLuaRole.set("changeName", new ChangeName());
		mLuaRole.set("changePermissions", new ChangePermissions());
		mLuaRole.set("delete", new Delete());
		mLuaRole.set("getColor", new GetColor());
		mLuaRole.set("getCreationDate", new GetCreationDate());
		mLuaRole.set("getGuild", new GetGuild());
		mLuaRole.set("getID", new GetID());
		mLuaRole.set("getName", new GetName());
		mLuaRole.set("getPermissions", new GetPermissions());
		mLuaRole.set("getPosition", new GetPosition());
		mLuaRole.set("isHoisted", new IsHoisted());
		mLuaRole.set("isManaged", new IsManaged());
	}
	
	private class ChangeColor extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue color)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mRole.changeColor(Color.decode(color.tojstring()));
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeHoist extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue hoist)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mRole.changeHoist(hoist.toboolean());
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangeName extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue name)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mRole.changeName(name.tojstring());
				return LuaValue.NIL;
			});
		}
	}
	
	private class ChangePermissions extends OneArgFunction
	{
		@Override
		public LuaValue call(LuaValue luaPermissions)
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				// Parse permissions from lua to java
				LuaValue k = LuaValue.NIL;
				EnumSet<Permissions> permissions = EnumSet.noneOf(Permissions.class);
				while (true)
				{
					Varargs n = luaPermissions.next(k);
					if ((k = n.arg1()).isnil())
						break;
					LuaValue v = n.arg(2);
					permissions.add(Permissions.valueOf(v.tojstring()));
				}
				mRole.changePermissions(permissions);
				return LuaValue.NIL;
			});
		}
	}
	
	private class Delete extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleRequestExceptions(this.getClass(), () -> {
				mRole.delete();
				return LuaValue.NIL;
			});
		}
	}
	
	private class GetColor extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.getColor().getRGB());
			});
		}
	}
	
	private class GetCreationDate extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.getCreationDate().toString());
			});
		}
	}
	
	private class GetGuild extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return (new LuaGuild(mRole.getGuild())).getTable();
			});
		}
	}
	
	private class GetID extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.getID());
			});
		}
	}
	
	private class GetName extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.getName());
			});
		}
	}
	
	private class GetPermissions extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				EnumSet<Permissions> permissions = mRole.getPermissions();
				LuaValue luaPermissions = LuaValue.tableOf();
				for (Permissions permission : permissions)
				{
					luaPermissions.set(luaPermissions.length() + 1, permission.name());
				}
				return luaPermissions;
			});
		}
	}
	
	private class GetPosition extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.getPosition());
			});
		}
	}
	
	private class IsHoisted extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.isHoisted());
			});
		}
	}
	
	private class IsManaged extends ZeroArgFunction
	{
		@Override
		public LuaValue call()
		{
			return LuaHelper.handleExceptions(this.getClass(), () -> {
				return LuaValue.valueOf(mRole.isManaged());
			});
		}
	}
	
	public LuaValue getTable()
	{
		return mLuaRole;
	}
}
