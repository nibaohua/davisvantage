package com.whizzosoftware.hobson.davisvantage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

import org.apache.commons.codec.binary.Hex;

import com.whizzosoftware.hobson.davisvantage.api.command.HexConvert;
import com.whizzosoftware.hobson.davisvantage.api.command.LoopResponse;

public class TestTCPReceive {

	public static void main(String[] args) throws ParseException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		String msg = "hello client,I am server..";
		System.out.println(msg);
		try {

			// 构造ServerSocket实例，指定端口监听客户端的连接请求
			serverSocket = new ServerSocket(8888);
			while (true) {
				socket = serverSocket.accept();
				// 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
				InputStream inputStream = socket.getInputStream();
				byte[] bytes = new byte[1024];
				int len;
				StringBuilder sb = new StringBuilder();
				while ((len = inputStream.read(bytes)) != -1) {
					// 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
					sb.append(new String(bytes, 0, len, "UTF-8"));
				}
				System.out.println("get message from client: " + sb);
				
				String hexString= sb.toString();
		        hexString =  hexString.replace( " ","" );//去除空格
		        
		        byte[] data = HexConvert.hexStringToBytes(hexString);
		        LoopResponse lr = new LoopResponse(data);
		        
		        //1、解析出数据
		        
		        //2、 存到数据库   root 123456
		        
		        
		        //3、 启动类一直处理socket
		        System.out.println("response:"+lr.toString());
				
				inputStream.close();
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * finally { //操作结束，关闭socket try { serverSocket.close(); socket.close(); } catch
		 * (IOException e) { e.printStackTrace(); } }
		 */
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	/**
	 * 
	 * 1、date
	 * 2、time
	 * 3、TempOut  outsideTemp
	 * 4、HiTemp  
	 * 5. LowTemp
	 * 6. outHum
	 * 7.dewpt
	 * 8.wind_speed  windSpeed
	 * 9.wind_dir  windDirection
	 * 10.wind_run
	 * 11.hi_speed
	 * 12.hi_dir
	 * 13.wind_chill
	 * 14.heat_index
	 * 15.thw_index
	 * 16.thsw_index
	 * 17.bar  barTrend
	 * 18.rain
	 * 19.rain_rate
	 * 20.solar_sad
	 * 21.solar_enery
	 * 22.hi_solar_rad
	 * 23.uv_index
	 * 24.uv_does
	 * 25.hi_uv
	 * 26.heat_dd
	 * 27.cool_dd
	 * 28.in_temp insideTemp
	 * 29.in_hum  insideHumidity
	 * 30.in_dew
	 * 31.in_heat
	 * 32.in_emc
	 * 33.in_air_density
	 * 34.et
	 * 35.wind_samp
	 * 36.wind_tx
	 * 37.iss_recept
	 * 38.arc_int
	 */
	public void save() {
		
	}

}
