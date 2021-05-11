/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.p3achb0t.client.cache.protocol.handshake;

import com.p3achb0t.client.cache.protocol.api.handshake.HandshakeType;
import com.p3achb0t.client.cache.protocol.api.handshake.LoginHandshakePacket;
import com.p3achb0t.client.cache.protocol.api.handshake.UpdateHandshakePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HandshakeDecoder extends ByteToMessageDecoder
{
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception
	{
		buf.markReaderIndex();
		byte handshakeOpcode = buf.readByte();

		HandshakeType handshakeType = HandshakeType.of(handshakeOpcode);
		if (handshakeType == null)
		{
			log.warn("Unknown handshake type {} from {}",
				handshakeOpcode, ctx.channel().remoteAddress());
			ctx.close();
			return;
		}

		switch (handshakeType)
		{
			case LOGIN:
			{
				LoginHandshakePacket packet = new LoginHandshakePacket();
				out.add(packet);
				break;
			}
			case UPDATE:
			{
				if (buf.readableBytes() < 4)
				{
					buf.resetReaderIndex();
					return;
				}

				int revision = buf.readInt();
				UpdateHandshakePacket packet = new UpdateHandshakePacket(revision);
				out.add(packet);
				break;
			}
		}
	}

}
