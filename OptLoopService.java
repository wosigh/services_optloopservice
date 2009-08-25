// Put me in /usr/lib/luna/java

package org.webosinternals.optloopservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
// import java.util.Iterator;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
import com.palm.luna.message.ErrorMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class OptLoopService extends LunaServiceThread {

	/* Variables */
	private Runtime runtime;
	private String name = "Opt Loop Service";
	private String version = "0.0.3";

	/* An object to hold the return value and stdout of the executed command. */
	private class ReturnResult {
		int returnValue;
		ArrayList<String> stdOut;
		public ReturnResult(int returnValue, ArrayList<String> stdOut) {
			this.returnValue = returnValue;
			this.stdOut = stdOut;
		}
	}

	/* Create a new OptLoopService -- This is an unnecessary constructor. */
	public OptLoopService() {
		runtime = Runtime.getRuntime();
	}

	/**
	 * A function to execute system commands.
	 * 
	 * @param command The system command to execute
	 * @return A ReturnResult object containing the return value and stdout of
	 * of the executed command.
	 */
	private ReturnResult executeCMD(String command) {
		int ret = 1;
		ArrayList<String> output = new ArrayList<String>();
		if (true) {
			try {
				Process p = runtime.exec(command);
				InputStream in = p.getInputStream();
				BufferedInputStream buf = new BufferedInputStream(in);
				InputStreamReader inread = new InputStreamReader(buf);
				BufferedReader bufferedreader = new BufferedReader(inread);
				
				String line;
				while ((line = bufferedreader.readLine()) != null) {
					output.add(line);
				}
				
				try {
					if (p.waitFor() != 0)
						System.err.println("exit value = " + p.exitValue());
					else
						ret = 0;
				} catch (InterruptedException e) {
					System.err.println(e);
				} finally {
					bufferedreader.close();
					inread.close();
					buf.close();
					in.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return new ReturnResult(ret, output);
	}

	/**
	 * Needs to be able to execute for loops and other bash scripting elements. This will involve knowing how to use the Runtime object runtime, which is declared as private in this file.
	 */

	private JSONObject mount()
	throws JSONException, LSException {
		ReturnResult ret = executeCMD("/bin/mount /opt");
		if (ret.returnValue==0) {
			JSONObject reply = new JSONObject();
			reply.put("return",ret.returnValue);
			return reply;
		} else {
			return null;
		}
	}
	
	private JSONObject unmount()
	throws JSONException, LSException {
		ReturnResult ret = executeCMD("/bin/umount /opt");
		if (ret.returnValue==0) {
			JSONObject reply = new JSONObject();
			reply.put("return",ret.returnValue);
			return reply;
		} else {
			return null;
		}
	}
	
	/* DBUS Methods */

	@LunaServiceThread.PublicMethod
    public void version(ServiceMessage msg) {
        try {
            StringBuilder sb = new StringBuilder(8192);
			sb.append("{\"general\":[");
			sb.append("{name:" + JSONObject.quote(this.name) + "},");
			sb.append("{version:" + JSONObject.quote(this.version) + "}");
			sb.append("]}");
            msg.respond(sb.toString());
        }
		catch (LSException e) {
            this.logger.severe("", e);
        }
    }

	@LunaServiceThread.PublicMethod
	public void mount(ServiceMessage msg) throws JSONException, LSException {
	    JSONObject reply = mount();
	    if (reply!=null)
			msg.respond(reply.toString());
	    else
			msg.respondError(ErrorMessage.ERROR_CODE_METHOD_EXCEPTION, "You fail!");
	}

	@LunaServiceThread.PublicMethod
	public void unmount(ServiceMessage msg) throws JSONException, LSException {
	    JSONObject reply = unmount();
	    if (reply!=null)
			msg.respond(reply.toString());
	    else
			msg.respondError(ErrorMessage.ERROR_CODE_METHOD_EXCEPTION, "You fail!");
	}

	/*
	public static void main(String[] args) {
		try {
			OptLoopService ipkg =  new OptLoopService();
			System.out.println(ipkg.mount().toString());
			System.out.println(ipkg.unmount().toString());
		} catch (JSONException e) {
			System.err.println(e);
		} catch (LSException e) {
			System.err.println(e);
		}
	} */
}
