package com.kingston.net.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingston.logic.chat.message.req.ReqChatToGroupPacket;
import com.kingston.logic.chat.message.req.ReqChatToUserPacket;
import com.kingston.logic.chat.message.resp.ResChatToUserPacket;
import com.kingston.logic.friend.message.ResFriendListPacket;
import com.kingston.logic.login.message.ReqHeartBeatPacket;
import com.kingston.logic.login.message.ReqUserLoginPacket;
import com.kingston.logic.login.message.ResHeartBeatPacket;
import com.kingston.logic.login.message.ResUserLoginPacket;
import com.kingston.logic.user.message.ReqUserRegisterPacket;
import com.kingston.logic.user.message.ResUserRegisterPacket;

public enum PacketType {

	//业务上行数据包

	//----------------------模块号申明------------------------------
	//----------------请求协议id格式为 模块号_000 起--------------------
	//----------------推送协议id格式为 模块号_200 起--------------------
	//------------------基础服务1-----------------------------------
	//------------------http协议2----------------------------------
	//------------------用户3----------------------------------
	//------------------聊天4----------------------------------
	//------------------好友5----------------------------------

	/** 请求--链接心跳包 */
	ReqHeartBeat(1_000, ReqHeartBeatPacket.class),
	/** 推送--新用户注册  */
	ResHeartBeat(1_200, ResHeartBeatPacket.class),

	/** 请求--新用户注册  */
	ReqUserRegister(3_000, ReqUserRegisterPacket.class),
	/** 请求--请求--用户登陆  */
	ReqUserLogin(3_001, ReqUserLoginPacket.class),

	/** 请求--新用户注册  */
	ResUserRegister(3_200, ResUserRegisterPacket.class),
    /** 推送--用户登录  */
	RespUserLogin(3_201, ResUserLoginPacket.class),

	/** 请求--单聊  */
	ReqChatToUser(4_000, ReqChatToUserPacket.class),
    /** 请求--群聊  */
	ReqChatToGroup(4_001, ReqChatToGroupPacket.class),

	/** 推送--单聊 */
	ResChatToUser(4_200, ResChatToUserPacket.class),
	/** 推送--群聊 */
	ResChatToGroup(4_201, ReqChatToGroupPacket.class),

	/** 推送--好友列表 */
	ResFriendList(5_200, ResFriendListPacket.class),

	;

	private int type;
	private Class<? extends AbstractPacket> packetClass;
	private static Map<Integer,Class<? extends AbstractPacket>> PACKET_CLASS_MAP = new HashMap<>();

	public static void initPackets() {
		Set<Integer> typeSet = new HashSet<>();
		Set<Class<?>> packets = new HashSet<>();
		for(PacketType p:PacketType.values()){
			int type = p.getType();
			if(typeSet.contains(type)){
				throw new IllegalStateException("packet type 协议类型重复"+type);
			}
			Class<?> packet = p.getPacketClass();
			if (packets.contains(packet)) {
				throw new IllegalStateException("packet定义重复"+p);
			}
			PACKET_CLASS_MAP.put(type,p.getPacketClass());
			typeSet.add(type);
			packets.add(packet);
		}
	}

	PacketType(int type,Class<? extends AbstractPacket> packetClass){
		this.setType(type);
		this.packetClass = packetClass;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Class<? extends AbstractPacket> getPacketClass() {
		return packetClass;
	}

	public void setPacketClass(Class<? extends AbstractPacket> packetClass) {
		this.packetClass = packetClass;
	}


	public static Class<? extends AbstractPacket> getPacketClassBy(int packetType){
		return PACKET_CLASS_MAP.get(packetType);
	}

	public static void main(String[] args) {
		for(PacketType p:PacketType.values()){
			System.err.println(p.getPacketClass().getSimpleName());
		}
	}

}
