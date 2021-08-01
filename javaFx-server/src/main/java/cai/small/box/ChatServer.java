package cai.small.box;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import java.nio.channels.SocketChannel;

/**
 * @Project : JavaFx-demo
 * @Author : zhangzongyuan
 * @Description : [ ChatServer ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2021/7/31 9:54
 * @Version ： 1.0
 **/
public class ChatServer {

    public static void main(String[] args) throws InterruptedException {

        // 首先，netty通过ServerBootstrap启动服务端
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup =new NioEventLoopGroup();
        //第1步定义两个线程组，用来处理客户端通道的accept和读写事件
        //parentGroup用来处理accept事件，childgroup用来处理通道的读写事件
        //parentGroup获取客户端连接，连接接收到之后再将连接转发给childgroup去处理
        server.group(parentGroup, childGroup);

        //用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
        //用来初始化服务端可连接队列
        //服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
        server.option(ChannelOption.SO_BACKLOG, 128);

        //第2步绑定服务端通道
        server.channel(NioServerSocketChannel .class);

        //第3步绑定handler，处理读写事件，ChannelInitializer是给通道初始化
        ServerBootstrap serverBootstrap = server.childHandler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {
                //解码器，接收的数据进行解码，一定要加在SimpleServerHandler 的上面
                //maxFrameLength表示这一贞最大的大小
                //delimiter表示分隔符，我们需要先将分割符写入到ByteBuf中，然后当做参数传入；
                //需要注意的是，netty并没有提供一个DelimiterBasedFrameDecoder对应的编码器实现(笔者没有找到)，因此在发送端需要自行编码添加分隔符，如 \r \n分隔符
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //把传过来的数据 转换成byteBuf
                ch.pipeline().addLast(new SimpleServerHandler());
            }
        });

        //第4步绑定8080端口
        ChannelFuture future = server.bind(8080).sync();
        //当通道关闭了，就继续往下走
        future.channel().closeFuture().sync();
    }



}
