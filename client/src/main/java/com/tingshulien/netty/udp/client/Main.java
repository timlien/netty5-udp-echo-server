package com.tingshulien.netty.udp.client;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        InetSocketAddress remoteAddress = new InetSocketAddress(host, port);

        NettyUdpClient client = new NettyUdpClient(host, port);

        try {
            ChannelFuture channelFuture = client.start();

            String text = "test";
            System.out.println("Client sending message " + text + " to server");
            ByteBuf byteBuf = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
            client.write(new DatagramPacket(byteBuf, remoteAddress));

            // Wait until the connection is closed.
            channelFuture.channel().closeFuture().sync();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
