package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jwp on 2017/1/24.
 * 网络消息编码
 */
public class NetMessageEncoderFactory implements  INetMessageEncoderFactory{

    public ByteBuf createByteBuf(NetMessage netMessage){
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NetMessageHead netMessageHead = netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //编写body
        NetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
//        int skip = (2+4);
        int skip = 6;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;
    }
}
