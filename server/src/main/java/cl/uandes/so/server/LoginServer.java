/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.uandes.so.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodrigo
 */
public class LoginServer {
    private int port;
    private String multicastgroup;
    
    public LoginServer(int port, String multicastgroup) {
        this.port = port;
        this.multicastgroup = multicastgroup;
        
    }
    
    public void killServer(EventLoopGroup group) throws InterruptedException {
        Thread.sleep(60000);
        group.shutdownGracefully();
        
    }
    
    public void run() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup(); // (1)
        
        try {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("Login Server will stop in 60 seconds...");
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   group.shutdownGracefully();
                    
                }
            });  
            t1.start();
            Bootstrap b = new Bootstrap(); // (2)
            b.group(group)
             .channel(NioDatagramChannel.class) // (3)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new LoginServerHandler(multicastgroup));
             
             

            // Bind and start to accept incoming connections.
            b.bind(port).sync().channel().closeFuture().await(); // (7)

        } finally {
            group.shutdownGracefully();
        }    
    }
}
