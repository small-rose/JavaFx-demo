package cai.small.box;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import lombok.SneakyThrows;

/**
 * @Project : JavaFx-demo
 * @Author : zhangzongyuan
 * @Description : [ ChatClient ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2021/7/31 8:48
 * @Version ： 1.0
 **/
public class ChatClient {

    // 主机地址
    private String host ;

    // 链接端口
    private int port ;

    // 启动引导
    private Bootstrap client = null;

    //第1步 定义线程组，处理读写和链接事件，没有了accept事件
    private EventLoopGroup group = null;

    private ChannelFuture future = null;


    @SneakyThrows
    public void init(String host , int port){

        client = new Bootstrap();
        group = new NioEventLoopGroup();
        client.group(group);

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);


        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {  //通道是NioSocketChannel
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //找到他的管道 增加他的handler
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        future = client.connect(host, port).sync();
    }


    public void SendMsg(){

    }


    @SneakyThrows
    public void close(){

        if (future!=null){
            //关闭通道
            future.channel().closeFuture().sync();
        }
        if (group!=null){
            group.shutdownGracefully();
        }

    }
}
