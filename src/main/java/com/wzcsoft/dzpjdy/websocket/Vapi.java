package com.wzcsoft.dzpjdy.websocket;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.UnsupportedEncodingException;

public class Vapi {
	public interface Vdll extends Library {
		Vdll INSTANCE = (Vdll) Native.loadLibrary("E:\\dzpjdy\\dzpjdy\\dzpjdy\\dzpjdy\\libvbar", Vdll.class);
		
		public PointerByReference vbar_open(String addr, long parm);

		public boolean vbar_is_connected(PointerByReference vbar_device);

		public boolean vbar_backlight(PointerByReference vbar_device, boolean on);

		public boolean vbar_beep(PointerByReference vbar_device, int time);

		public boolean vbar_add_symbol_type(PointerByReference vbar_device, int type, boolean on);

		public boolean vbar_scan(PointerByReference vbar_device, IntByReference symbol_type, byte[] result_buffer, IntByReference result_size);
		
		public boolean vbar_close(PointerByReference vbar_device);
	}
		
	PointerByReference device = null;
	    //�����豸 ���� 
		public boolean vbarOpen(){
			device = Vdll.INSTANCE.vbar_open("1", 1);
		    if(device != null)	
		    {
		    	return true;
		    }
		    else
		    {
		    	return false;
		    }
	}
			
		//����������
		public boolean vbarBeep(){
			if(device != null)	
		    {
				 if(Vdll.INSTANCE.vbar_is_connected(device))
					{
						if(Vdll.INSTANCE.vbar_beep(device,20))
						{
							return true;
						}
						else
						{
							return false;
						}
						
					}
					else
					{
						return false;
					}
		    }
			 else
			 {
				 return false;
			 }
			
		}
		//�������
		public boolean vbarBacklight(boolean bool){
			if(device != null)	
		    {
				if(Vdll.INSTANCE.vbar_is_connected(device))
				{
					if(Vdll.INSTANCE.vbar_backlight(device,bool))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
		    }
			else
			{
				return false;
			}
			
			
		}
		
		//�������
		/*
		 * 	VBAR_SYM_QRCODE         = 1,
		    VBAR_SYM_EAN8           = 2,
		    VBAR_SYM_EAN13          = 3,
		    VBAR_SYM_CODE39         = 5,
		    VBAR_SYM_CODE93         = 6,
		    VBAR_SYM_CODE128        = 7,
		    VBAR_SYM_PDF417         = 10,
		    VBAR_SYM_DATAMATRIX     = 11,
		    VBAR_SYM_ITF            = 12,
		    VBAR_SYM_UPCE           = 14,
		    VBAR_SYM_UPCA           = 15,
		    VBAR_SYM_INDUSTRIAL25   = 16,      ������ ����25�� */ 
		 
		public boolean vbarAddSymbolType(int symbol_type,boolean enable){
			if(device != null)
			{
				if(Vdll.INSTANCE.vbar_add_symbol_type(device,symbol_type,enable))
				{
					System.out.println("add success");
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		public boolean vbarIsConnect()
		{
			if(device != null)
			{
				if(Vdll.INSTANCE.vbar_is_connected(device))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		//ɨ��
		byte [] result_buffer = new byte[1024];
		IntByReference symbol_type = new IntByReference(256);
		IntByReference result_size = new IntByReference(1024);
		public String vbarScan() throws UnsupportedEncodingException
		{
			if(device != null)
			{
				String decode = null;
				boolean state = Vdll.INSTANCE.vbar_is_connected(device);
				if(state)
				{
					if(Vdll.INSTANCE.vbar_scan(device,symbol_type,result_buffer,result_size))
					{
						decode = new String(result_buffer, 0, result_size.getValue());
						
						return decode;
					}
					
					else
					{
						return null;
					}
				}
				else
				{
					System.out.println("disconnect");
					return null;
				}
			}
			else
			{
				return null;
			}	
		}	
		public void vbarClose()
		{
			if(device != null)
			{
				Vdll.INSTANCE.vbar_close(device);
			}
		}
	}
		
		

